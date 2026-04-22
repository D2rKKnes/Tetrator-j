package terra.ai;

import arc.math.geom.Vec2;
import mindustry.ai.types.FlyingAI;
import mindustry.gen.Building;
import mindustry.gen.Hitboxc;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.state;
import static mindustry.Vars.world;

public class FlyingSuicideAI extends FlyingAI {

    private final Vec2 vec = new Vec2();

    @Override
    public void updateMovement() {)
        unloadPayloads();

        if (Units.invalidateTarget(target, unit.team, unit.x, unit.y, Float.MAX_VALUE)) {
            target = null;
        }

        if (retarget()) {
            target = target(unit.x, unit.y, unit.range(), unit.type.targetAir, unit.type.targetGround);
        }

        if (target == null) {
            target = unit.closestEnemyCore();
        }

        boolean shoot = false;

        if (target != null && !Units.invalidateTarget(target, unit, unit.range()) && unit.hasWeapons()) {
            float range = unit.type.weapons.first().bullet.range;
            float targetSize = target instanceof Building b ? b.block.size * world.tilesize / 2f
                    : ((Hitboxc) target).hitSize() / 2f;
            shoot = unit.within(target, range + targetSize);
        }

        if (target != null) {
            vec.set(target).sub(unit).limit(unit.speed());
            unit.movePref(vec);
        }

        unit.controlWeapons(true, shoot);
        faceTarget();
    }

    @Override
    public Teamc target(float x, float y, float range, boolean air, boolean ground) {
        return Units.closestTarget(unit.team, x, y, range,
                u -> u.checkTarget(air, ground),
                t -> ground && !(t.block instanceof Conveyor || t.block instanceof Conduit));
    }
}
