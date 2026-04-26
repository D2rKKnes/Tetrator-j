package terra.world.blocks;

import terra.ai.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;

public class DroneCentre extends Block {
    public Seq<UnitType> droneTypes = new Seq<>();
    public float droneConstructTime = 210f;
    public int droneMax = 3;

    public DroneCentre(String name) {
        super(name);
        update = true;
        solid = true;
        configurable = true;
        hasPower = true;
        consumePower(2f); 

        config(Integer.class, (DroneCentreBuild build, Integer index) -> {
            build.changeType(index);
        });
    }

    public class DroneCentreBuild extends Building {
        public int currentTypeIndex = 0;
        public float progress = 0;
        public Seq<Unit> spawnedDrones = new Seq<>();

        public void changeType(int index) {
            if (index < 0 || index >= droneTypes.size) return;
            currentTypeIndex = index;
            spawnedDrones.each(u -> {
                if (u != null) u.despawn();
            });
            spawnedDrones.clear();
            progress = 0;
        }

        @Override
        public void updateTile() {
            spawnedDrones.removeAll(u -> !u.isValid());

            if (enabled && spawnedDrones.size < droneMax && power.status >= 1f) {
                progress += edelta();
                if (progress >= droneConstructTime) {
                    spawnDrone();
                    progress = 0;
                }
            }
        }

        public void spawnDrone() {
            UnitType type = droneTypes.get(currentTypeIndex);
            Unit unit = type.create(team);
            unit.set(x, y);
            unit.controller(new DroneAI(this));
            unit.add();
            spawnedDrones.add(unit);
        }

        @Override
        public void draw() {
            super.draw();
            if (progress > 0 && currentTypeIndex < droneTypes.size) {
                UnitType type = droneTypes.get(currentTypeIndex);
                float alpha = progress / droneConstructTime;
                Draw.color(Color.white, alpha);
                Draw.rect(type.fullIcon, x, y, type.hitSize * 1.5f, type.hitSize * 1.5f);
                Draw.reset();
            }
        }
    }
}
