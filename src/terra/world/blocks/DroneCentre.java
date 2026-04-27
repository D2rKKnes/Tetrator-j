package terra.world.blocks;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.ItemSelection;
import mindustry.entities.*;
import mindustry.annotations.Annotations.*;
import terra.ai.*;

import static mindustry.Vars.*;

public class DroneCentre extends Block {
    public Seq<DronePlan> plans = new Seq<>();
    public int droneMax = 3;
    public float droneConstructTime = 210f;
    public boolean individualConstructTime = false;

    public DroneCentre(String name) {
        super(name);
        update = true;
        configurable = true;
        hasPower = true;
        solid = true;
        ambientSound = Sounds.loopUnitBuilding;

        config(Integer.class, (DroneCentreBuild build, Integer i) -> {
            build.currentPlan = i < 0 || i >= plans.size ? -1 : i;
            build.progress = 0;
            build.despawnDrones();
        });
    }

    @Remote(called = Loc.server)
    public static void unitTetherBlockSpawned(Tile tile, int id){
        if(tile == null || !(tile.build instanceof DroneCentreBuild build)) return;
        build.spawned(id);
    }

    public static class DronePlan {
        public UnitType unit;
        public float time;
        public DronePlan(UnitType unit, float time) { this.unit = unit; this.time = time; }
    }

    public class DroneCentreBuild extends Building implements UnitTetherBlock {
        public int currentPlan = -1;
        public float progress, totalProgress;
        public Seq<Unit> spawnedDrones = new Seq<>();
        public int readUnitId = -1;

        public void despawnDrones() {
            spawnedDrones.each(u -> {
                if (u instanceof BuildingTetherc bt) {
                    bt.building(null);
                    u.capLimit();
                }
            });
            spawnedDrones.clear();
        }

        @Override
        public void updateTile() {
            spawnedDrones.removeAll(u -> !u.isValid());

            if(readUnitId != -1){
                Unit u = Groups.unit.getByID(readUnitId);
                if(u != null || !net.client()) readUnitId = -1;
            }

            if (currentPlan != -1 && spawnedDrones.size < droneMax && enabled) {
                float time = individualConstructTime ? plans.get(currentPlan).time : droneConstructTime;
                
                progress += edelta() / time;
                totalProgress += edelta();

                if (progress >= 1f) {
                    if(!net.client()){
                        Unit unit = plans.get(currentPlan).unit.create(team);
                        unit.set(x, y);
                        unit.controller(new DroneAI(this));
                        unit.add();
                        spawnedDrones.add(unit);
                        
                        Call.unitTetherBlockSpawned(tile, unit.id);
                    }
                    progress = 0;
                }
            }
        }

        public void spawned(int id){
            mindustry.content.Fx.spawn.at(x, y);
            if(unit instanceof BuildingTetherc bt) bt.building(this);
        }

        @Override
        public void draw() {
            super.draw();
            if (currentPlan != -1 && progress > 0) {
                DronePlan plan = plans.get(currentPlan);
                Draw.draw(Layer.blockOver, () -> {
                    Drawf.construct(this, plan.unit.fullIcon, 0f, progress, efficiency, totalProgress);
                });
            }
        }

        @Override
        public void buildConfiguration(Table table) {
            Seq<UnitType> units = Seq.with(plans).map(p -> p.unit).retainAll(u -> u.unlockedNow());
            if (units.any()) {
                ItemSelection.buildTable(DroneCentre.this, table, units, 
                    () -> currentPlan == -1 ? null : plans.get(currentPlan).unit, 
                    unit -> configure(plans.indexOf(p -> p.unit == unit))
                );
            }
        }

        @Override public float progress() { return progress; }
        @Override public float totalProgress() { return totalProgress; }
    }
}
