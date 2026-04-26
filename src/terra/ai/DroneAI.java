package terra.ai;

import arc.math.geom.*;
import arc.util.*;
import arc.math.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.entities.units.*;
import java.util.Random;

public class DroneAI extends AIController {
    public Building parent;
    public Unit targetUnit;
    public Player targetPlayer;
    public float timer = 0;
    private static final Random rand = new Random();

    public DroneAI(Building parent) {
        this.parent = parent;
    }

    @Override
    public void updateUnit() {
        if (parent == null || !parent.isValid()) {
            unit.remove();
            return;
        }

        timer += Time.delta;
        if (timer >= 10f || targetPlayer == null || (targetUnit != null && !targetUnit.isValid())) {
            findTargets();
            timer = 0;
        }

        if (unit.type.buildSpeed > 0) {
            handleBuilderLogic();
        } else {
            handleSupportLogic();
        }
    }

    void findTargets() {
        int pSize = Groups.player.size();
        if (pSize > 0) {
            int targetIndex = rand.nextInt(pSize);
            int current = 0;
            for (Player p : Groups.player) {
                if (current == targetIndex) {
                    targetPlayer = p;
                    break;
                }
                current++;
            }
        } else {
            targetPlayer = null;
        }
        
        targetUnit = null;
        float maxHealth = -1;
        for (Unit u : Groups.unit) {
            if (u.team == unit.team && u != unit && u.health > maxHealth) {
                maxHealth = u.health;
                targetUnit = u;
            }
        }
    }

    void handleBuilderLogic() {
        if (targetPlayer != null && targetPlayer.unit() != null) {
            float orbitRadius = unit.type.buildRange / 2f;
            circle(targetPlayer.unit(), orbitRadius);
            
            if(targetPlayer.unit().plans().size > 0){
                for(BuildPlan plan : targetPlayer.unit().plans()){
                    unit.plans().addFirst(plan);
                }
            }
            
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

    @Override
    public void circle(Position target, float radius) {
        float angle = Time.time * 2f;
        moveTo(Tmp.v1.trns(angle, radius).add(target.getX(), target.getY()), 0f);
    }
}
