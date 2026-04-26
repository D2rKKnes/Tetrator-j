package terra.ai;

import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.entities.units.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class DroneAI extends AIController {
    public static float moveRange = 6f, moveSmoothing = 20f;
    
    public @Nullable Entityc target;
    public float timer = Mathf.random(60f);

    @Override
    public void updateMovement() {
        if (!(unit instanceof BuildingTetherc tether) || tether.building() == null) {
            unit.despawn();
            return;
        }

        var build = tether.building();

        if ((timer += Time.delta) >= 5f || target == null) {
            findTarget();
            timer = 0;
        }

        if (unit.type.buildSpeed > 0 && target instanceof Player p) {
            handleBuild(p, build);
        } else if (target instanceof Unit u) {
            circle(u, u.hitSize + 40f);
        } else {
            circle(build, 48f);
        }

        if (!unit.vel.isZero()) {
            unit.lookAt(unit.vel.angle());
        }
    }

    void findTarget() {
        if (unit.type.buildSpeed > 0) {
            target = Groups.player.size() > 0 ? Groups.player.random() : null;
        } else {
            target = Groups.unit.select(u -> u.team == unit.team && u.type != unit.type)
                               .max(u -> u.maxHealth);
        }
    }

    void handleBuild(Player p, Building build) {
        Unit pUnit = p.unit();
        if (pUnit != null && pUnit.plans().size > 0) {
            BuildPlan plan = pUnit.plans().first();
            moveTo(Tmp.v1.set(plan.x * tilesize, plan.y * tilesize), unit.type.buildRange * 0.7f, moveSmoothing);
            unit.addBuild(plan);
            
            if (Mathf.chanceDelta(0.1)) {
                mindustry.content.Fx.buildBeam.at(unit.x, unit.y, unit.angleTo(plan.x * tilesize, plan.y * tilesize), pUnit);
            }
        } else {
            circle(pUnit, 40f);
        }
    }

    @Override
    public void circle(Position target, float radius) {
        float angle = (Time.time * 1.2f) + (unit.id * 50f);
        moveTo(Tmp.v1.trns(angle, radius).add(target), moveRange, moveSmoothing);
    }
}
