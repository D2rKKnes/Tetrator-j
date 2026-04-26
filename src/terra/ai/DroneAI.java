package terra.ai;

import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.*;

public class DroneAI extends AIController {
    public Building parent;
    public Unit targetUnit;
    public Player targetPlayer;
    public float timer = 0;

    public DroneAI(Building parent) {
        this.parent = parent;
    }

    @Override
    public void updateUnit() {
        if (parent == null || !parent.isValid()) {
            unit.despawn();
            return;
        }

        timer += Time.delta;
        if (timer >= 10f || targetPlayer == null || targetUnit == null) {
            findTargets();
            timer = 0;
        }

        if (unit.type.canBuild) {
            handleBuilderLogic();
        } else {
            handleSupportLogic();
        }
    }

    void findTargets() {
        targetPlayer = Groups.player.size() > 0 ? Groups.player.random() : null;
        
        targetUnit = Groups.unit.select(u -> u.team == unit.team && u != unit)
                                .max(u -> u.maxHealth);
    }

    void handleBuilderLogic() {
        if (targetPlayer != null && targetPlayer.unit() != null) {
            float orbitRadius = unit.type.buildRange / 2f;
            circle(targetPlayer.unit(), orbitRadius);
            
            unit.plans().addFirst(targetPlayer.unit().plans());
            
            if (unit.hasWeapons()) {
                Unit enemy = Units.closestEnemy(unit.team, unit.x, unit.y, unit.range(), u -> true);
                if (enemy != null) {
                    unit.lookAt(enemy);
                    unit.controlWeapons(true);
                }
            }
        } else {
            orbitParent();
        }
    }

    void handleSupportLogic() {
        if (targetUnit != null && targetUnit.isValid()) {
            float orbitRadius = targetUnit.hitSize + 40f; 
            circle(targetUnit, orbitRadius);
        } else {
            orbitParent();
        }
    }

    void orbitParent() {
        circle(parent, 40f);
    }

    void circle(Position target, float radius) {
        Vec2 vec = new Vec2().trns(Time.time * 2f, radius).add(target);
        moveTo(vec, 10f);
    }
}
