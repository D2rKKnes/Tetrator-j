package terra.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.type.Item;
import mindustry.world.blocks.distribution.OverflowGate;

public class ManualGate extends OverflowGate {

    public ManualGate(String name) {
        super(name);
        configurable = true;
        saveConfig = true;
        drawDynamic = true;
        drawCached = false;

        config(Boolean.class, (ManualGateBuild build, Boolean b) -> {
            build.invert = b;
        });
    }

    public class ManualGateBuild extends OverflowGateBuild {
        public boolean invert = false;
        public boolean fx = false;

        @Override
        public void buildConfiguration(Table table) {
            table.button(Icon.refresh, () -> {
                configure(!invert);
                Sounds.click.at(this);
                fx = true;
            }).size(40f).checked(b -> invert);
        }

        @Override
        public Boolean config() {
            return invert;
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            return true;
        }

        @Override
        public Building getTileTarget(Item item, Building source, boolean flip) {
            int dir = source.relativeTo(tile.x, tile.y);
            if (dir == -1) return null;

            Building to = nearby(dir);
            boolean canForward = to != null && !(to.block.instantTransfer && source.block.instantTransfer) && to.acceptItem(this, item);

            Building a = nearby((dir + 3) % 4);
            Building b = nearby((dir + 1) % 4);
            boolean ac = a != null && !(a.block.instantTransfer && source.block.instantTransfer) && a.acceptItem(this, item);
            boolean bc = b != null && !(b.block.instantTransfer && source.block.instantTransfer) && b.acceptItem(this, item);

            if (!invert) {
                if (canForward) return to;
                if (ac && !bc) return a;
                if (bc && !ac) return b;
                if (!bc) return null;

                Building target = (rotation & (1 << dir)) == 0 ? a : b;
                if (flip) rotation ^= (1 << dir);
                return target;
            }
            else {
                if (ac && !bc) return a;
                if (bc && !ac) return b;
                if (ac && bc) {
                    Building target = (rotation & (1 << dir)) == 0 ? a : b;
                    if (flip) rotation ^= (1 << dir);
                    return target;
                }
                if (canForward) return to;
                return null;
            }
        }

        @Override
        public void draw() {
            var base = Core.atlas.find(name);
            Draw.rect(base, x, y);

            if (invert) {
                var invertTex = Core.atlas.find(name + "-invert");
                Draw.color();
                Draw.rect(invertTex, x, y);
            }

            if (fx) {
                Draw.color();
                Fx.placeBlock.at(this, 1.7f);
                fx = false;
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.bool(invert);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            invert = read.bool();
        }
    }
}
