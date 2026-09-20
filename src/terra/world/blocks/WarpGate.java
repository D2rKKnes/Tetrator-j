package terra.world.blocks;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.game.EventType.WorldLoadEvent;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.Env;

public class WarpGate extends Block {
    public static final String[] colorNames = new String[]{
        "purple", "pink", "red", "orange", "yellow", "green", "cyan", "blue", "white", "black"
    };

    public static final Color[] gateColors = new Color[]{
        Color.valueOf("8a73c6"),
        Color.valueOf("fc81de"),
        Color.valueOf("f25555"),
        Color.valueOf("ea8878"),
        Color.valueOf("eab678"),
        Color.valueOf("3a8f64"),
        Color.valueOf("86aeca"),
        Color.valueOf("6f80e8"),
        Color.valueOf("d1d1df"),
        Color.valueOf("515151")
    };

    public TextureRegion[] topRegions = new TextureRegion[colorNames.length];
    public static final ObjectMap<String, ObjectSet<WarpGateBuild>> gateNetworks = new ObjectMap<>();

    public float energyRequirement = 10f;

    static {
        Events.on(WorldLoadEvent.class, event -> {
            gateNetworks.clear();
        });
    }

    public static ObjectSet<WarpGateBuild> getNetworkGates(Team team, int colorIndex) {
        String key = team.id + "_" + colorIndex;
        if (!gateNetworks.containsKey(key)) {
            gateNetworks.put(key, new ObjectSet<>());
        }
        return gateNetworks.get(key);
    }

    public WarpGate(String name) {
        super(name);

        update = true;
        solid = true;
        destructible = true;
        hasItems = true;
        hasPower = true;
        configurable = true;
        saveConfig = true;

        itemCapacity = 111;
        envEnabled |= Env.any;
        size = 3;
        unloadable = true;

        consumePower(energyRequirement / 60f);

        config(Integer.class, (WarpGateBuild build, Integer val) -> {
            if (val >= 100) {
                build.isOutput = true;
                build.setColorIndex(val - 100);
            } else {
                build.isOutput = false;
                build.setColorIndex(val);
            }
        });
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("mode", (WarpGateBuild build) -> new Bar(
                () -> build.isOutput ? "Режим: Вывод" : "Режим: Ввод",
                () -> build.isOutput ? Pal.remove : Pal.accent,
                () -> 1f
        ));

        addBar("cooldown", (WarpGateBuild build) -> new Bar(
                () -> build.isOutput ? "Перезарядка: Отсутствует" : "Перезарядка " + Strings.fixed(Math.max(0f, build.cooldown / 60f), 2) + " сек",
                () -> Pal.power,
                () -> build.isOutput ? 0f : build.cooldown / 60f
        ));
    }

    @Override
    public void load() {
        super.load();
        for (int i = 0; i < colorNames.length; i++) {
            topRegions[i] = Core.atlas.find(name + "-top-" + colorNames[i]);
        }
    }

    public class WarpGateBuild extends Building {
        public int colorIndex = 0;
        public boolean isOutput = false;
        public float cooldown = 0f;

        @Override
        public void created() {
            super.created();
            colorIndex = Mathf.rand.nextFloat() > 0.5f ? 1 : 0;
        }

        @Override
        public void add() {
            super.add();
            getNetworkGates(team, colorIndex).add(this);
        }

        @Override
        public void remove() {
            super.remove();
            getNetworkGates(team, colorIndex).remove(this);
        }

        public void setColorIndex(int index) {
            if (index < 0 || index >= colorNames.length) return;
            getNetworkGates(team, this.colorIndex).remove(this);
            this.colorIndex = index;
            getNetworkGates(team, this.colorIndex).add(this);
        }

        @Override
        public void updateTile() {
            super.updateTile();

            if (isOutput) {
                if (items.total() > 0) {
                    dump();
                }
                cooldown = 0f;
            } else {
                if (cooldown > 0f) {
                    float powerFactor = power != null ? power.status : 1f;
                    cooldown -= edelta() * powerFactor;
                    if (cooldown < 0f) cooldown = 0f;
                }

                if (cooldown <= 0f && items.total() > 0 && (power == null || power.status > 0f)) {
                    teleportItems();
                }
            }
        }

        public void teleportItems() {
            if (isOutput) return;

            Seq<WarpGateBuild> validTargets = new Seq<>();
            ObjectSet<WarpGateBuild> allGates = getNetworkGates(team, colorIndex);

            for (WarpGateBuild gate : allGates) {
                if (gate != this && gate.isOutput && gate.isValidTarget()) {
                    validTargets.add(gate);
                }
            }

            if (validTargets.isEmpty()) return;

            boolean transferredAny = false;

            for (Item item : Vars.content.items()) {
                int amount = items.get(item);
                if (amount <= 0) continue;

                int perTarget = amount / validTargets.size;
                int remainder = amount % validTargets.size;

                for (int i = 0; i < validTargets.size; i++) {
                    WarpGateBuild target = validTargets.get(i);
                    int toGive = perTarget + (i < remainder ? 1 : 0);
                    if (toGive <= 0) continue;

                    int space = target.block.itemCapacity - target.items.total();
                    int actual = Math.min(toGive, space);

                    if (actual > 0) {
                        target.items.add(item, actual);
                        this.items.remove(item, actual);
                        transferredAny = true;
                    }
                }
            }

            if (transferredAny) {
                cooldown = 60f;
            }
        }

        public boolean isValidTarget() {
            return (power == null || power.status > 0f) && items.total() < block.itemCapacity;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return !isOutput && items.total() < block.itemCapacity;
        }

        @Override
        public void draw() {
            super.draw();
            if (topRegions[colorIndex] != null && topRegions[colorIndex].found()) {
                Draw.rect(topRegions[colorIndex], x, y);
            }
        }

        @Override
        public void buildConfiguration(Table table) {
            table.background(Styles.black6);

            Table colorsTable = new Table();
            for (int i = 0; i < gateColors.length; i++) {
                final int idx = i;
                colorsTable.button(b -> {
                    b.image(Tex.whiteui).size(28f).color(gateColors[idx]);
                }, () -> {
                    configure(idx + (isOutput ? 100 : 0));
                    deselect();
                }).size(44f).pad(4f);

                if ((i + 1) % 5 == 0) colorsTable.row();
            }
            table.add(colorsTable).row();

            table.button(b -> {
                b.add(isOutput ? "Режим: Вывод" : "Режим: Ввод");
            }, () -> {
                configure(colorIndex + (!isOutput ? 100 : 0));
                deselect();
            }).size(220f, 44f).pad(4f);
        }

        @Override
        public Integer config() {
            return colorIndex + (isOutput ? 100 : 0);
        }

        @Override
        public void write(Writes write) {
            write.b((byte) colorIndex);
            write.bool(isOutput);
            write.f(cooldown);
            super.write(write);
        }

        @Override
        public void read(Reads read, byte revision) {
            colorIndex = read.b();
            isOutput = read.bool();
            cooldown = read.f();
            super.read(read, revision);
        }
    }
}
