package terra.world.blocks;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.ui.*;
import mindustry.content.*;
import mindustry.world.blocks.*;
import terra.ai.*;

public class DroneCentre extends Block {
    public Seq<DroneEntry> drones = new Seq<>();
    public boolean individualConstructTime = false;
    public float droneConstructTime = 210f;
    public int droneMax = 3;

    public static class DroneEntry {
        public UnitType type;
        public float buildTime;
        public DroneEntry(UnitType type) { this.type = type; }
        public DroneEntry(UnitType type, float buildTime) { this.type = type; this.buildTime = buildTime; }
    }

    public DroneCentre(String name) {
        super(name);
        update = true;
        configurable = true;
        hasPower = true;
        drawUnitPlan = false;
        consumePower(3f);
        
        config(UnitType.class, (DroneCentreBuild build, UnitType type) -> build.changeType(type));
    }

   public class DroneCentreBuild extends Building {
        public UnitType currentType = null;
        public float progress = 0;
        public Seq<Unit> spawnedDrones = new Seq<>();

        public void changeType(UnitType type) {
            if (currentType == type) return;
            currentType = type;
            spawnedDrones.each(u -> {
                mindustry.content.Fx.unitDespawn.at(u.x, u.y, 0, u.type);
                u.remove();
            });
            spawnedDrones.clear();
            progress = 0;
        }

        @Override
        public void updateTile() {
            spawnedDrones.removeAll(u -> !u.isValid());

            if (currentType != null && spawnedDrones.size < droneMax && enabled) {
                float finalTime = droneConstructTime;
                if (individualConstructTime) {
                    DroneEntry entry = drones.find(e -> e.type == currentType);
                    if (entry != null && entry.buildTime > 0) finalTime = entry.buildTime;
                }

                progress += edelta() / finalTime;
                if (progress >= 1f) {
                    spawnDrone();
                    progress = 0;
                }
            }
        }

        public void spawnDrone() {
            Unit unit = currentType.create(team);
            unit.set(x, y);
            unit.controller(new DroneAI(this));
            unit.add();
            spawnedDrones.add(unit);
            mindustry.content.Fx.spawn.at(x, y);
        }

        @Override
        public void buildConfiguration(Table table) {
            Seq<UnitType> types = new Seq<>();
            drones.each(e -> types.add(e.type));
            ItemSelection.buildTable(DroneCentre.this, table, types, () -> currentType, this::changeType);
        }

        @Override
        public void draw() {
            super.draw();
            if (currentType != null && progress > 0) {
                Draw.draw(Layer.blockOver, () -> {
                    Shaders.build.region = currentType.fullIcon;
                    Shaders.build.progress = progress;
                    Shaders.build.color.set(Pal.accent);
                    Draw.shader(Shaders.build);
                    Draw.rect(currentType.fullIcon, x, y, currentType.hitSize, currentType.hitSize, -90);
                    Draw.shader();
                });
            }
        }
    }
}
