package terra.ai;

import arc.math.geom.*;
import mindustry.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.core.*;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.*;

public class FlyingSuicideAI extends FlyingAI {

    private final Vec2 vec = new Vec2();
    private boolean blockedByBlock = false;

    @Override
    public void updateMovement() {
        unloadPayloads();

        if (Units.invalidateTarget(target, unit.team, unit.x, unit.y, Float.MAX_VALUE)) {
            target = null;
        }

        if (retarget()) {
            target = target(unit.x, unit.y, unit.range(), unit.type.targetAir, unit.type.targetGround);
        }

        Building core = unit.closestEnemyCore();
        if (target == null) {
            target = core;
        }

        boolean rotate = false;
        boolean shoot = false;
        boolean moveToTarget = false;

        if (!Units.invalidateTarget(target, unit, unit.range()) && unit.hasWeapons()) {
            rotate = true;
            shoot = unit.within(target, unit.type.weapons.first().bullet.range +
                    (target instanceof Building b ? b.block.size * tilesize / 2f : ((Hitboxc) target).hitSize() / 2f));

            if (!(target instanceof Building build &&
                    !(build.block instanceof CoreBlock) &&
                    (build.block.group == BlockGroup.walls ||
                            build.block.group == BlockGroup.liquids ||
                            build.block.group == BlockGroup.transportation))) {

                blockedByBlock = false;

                boolean blocked = world.raycast(unit.tileX(), unit.tileY(), target.tileX(), target.tileY(), (x, y) -> {
                    for (Point2 p : Geometry.d4c) {
                        Tile tile = world.tile(x + p.x, y + p.y);
                        if (tile != null && tile.build == target) return false;
                        if (tile != null && tile.build != null && tile.build.team != unit.team()) {
                            blockedByBlock = true;
                            return true;
                        } else {
                            return tile == null || tile.solid();
                        }
                    }
                    return false;
                });

                if (blockedByBlock) {
                    shoot = true;
                }

                if (!blocked) {
                    moveToTarget = true;
                }
            }
        }

        if (moveToTarget) {
            vec.set(target).sub(unit).limit(unit.speed());
            unit.movePref(vec);
        } else {
            boolean move = true;

            if (core == null && state.rules.waves && unit.team == state.rules.defaultTeam) {
                Tile spawner = getClosestSpawner();
                if (spawner != null && unit.within(spawner, state.rules.dropZoneRadius + 120f)) {
                    move = false;
                }
            }

            if (move) {
                if (core != null) {
                    vec.set(core).sub(unit).limit(unit.speed());
                    unit.movePref(vec);
                }
            }
        }

        unit.controlWeapons(rotate, shoot);
        faceTarget();
    }

    @Override
    public Teamc target(float x, float y, float range, boolean air, boolean ground) {
        return Units.closestTarget(unit.team, x, y, range,
                u -> u.checkTarget(air, ground),
                t -> ground && !(t.block instanceof Conveyor || t.block instanceof Conduit));
    }

    private Tile getClosestSpawner() {
        Tile closest = null;
        float minDist = Float.MAX_VALUE;
        for (Tile tile : state.rules.spawns) {
            if (tile != null && tile.team() == unit.team) {
                float dist = unit.dst2(tile);
                if (dist < minDist) {
                    minDist = dist;
                    closest = tile;
                }
            }
        }
        return closest;
    }
}
