package mindustry.ai.types;

import arc.math.geom.*;
import mindustry.*;
import mindustry.ai.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.meta.*;

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

        boolean rotate = false;
        boolean shoot = false;
        boolean moveToTarget = false;

        if (target == null) {
            target = core;
        }

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

                boolean blocked = World.raycast(unit.tileX(), unit.tileY(), target.tileX(), target.tileY(), (x, y) -> {
                    for (Point2 p : Geometry.d4c) {
                        Tile tile = Vars.world.tile(x + p.x, y + p.y);
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
            unit.movePref(vec.set(target).sub(unit).limit(unit.speed()));
        } else {
            boolean move = true;

            if (core == null && state.rules.waves && unit.team == state.rules.defaultTeam) {
                Tile spawner = getClosestSpawner();
                if (spawner != null && unit.within(spawner, state.rules.dropZoneRadius + 120f)) {
                    move = false;
                }
            }

            if (move) {
                pathfind(Pathfinder.fieldCore);
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
}
