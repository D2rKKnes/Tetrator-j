package terra.ai;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.entities.units.*;
import mindustry.content.*;

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
            Fx.unitDespawn.at(unit.x, unit.y, 0f, unit.type);
            unit.remove();
            return;
        }

        timer += Time.delta;
        if (timer >= 300f || !isTargetValid()) {
            findTargets();
            timer = 0;
        }

        if (unit.type.buildSpeed > 0) {
            handleBuilderLogic();
        } else {
            handleSupportLogic();
        }
        
        unit.lookAt(unit.vel.angle());
    }

    boolean isTargetValid() {
        if (unit.type.buildSpeed > 0) return targetPlayer != null && targetPlayer.unit() != null && targetPlayer.unit().isValid();
        return targetUnit != null && targetUnit.isValid();
    }

    void findTargets() {
        targetPlayer = Groups.player.size() > 0 ? Groups.player.first() : null;

        targetUnit = null;
        float maxH = -1;
        for(Unit u : Groups.unit){
            if(u.team == unit.team && u != unit && u.maxHealth > maxH){
                maxH = u.maxHealth;
                targetUnit = u;
            }
        }
    }

    void handleBuilderLogic() {
        if (targetPlayer != null && targetPlayer.unit() != null) {
            Unit pUnit = targetPlayer.unit();
            
            if (pUnit.plans().size > 0) {
                BuildPlan plan = pUnit.plans().first();
                moveTo(plan.worldx(), plan.worldy(), unit.type.buildRange * 0.8f);
                unit.addBuild(plan);
            } else {
                circle(pUnit, unit.type.buildRange / 2f);
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
        if (targetUnit != null) {
            circle(targetUnit, targetUnit.hitSize + 40f);
        } else {
            orbitParent();
        }
    }

    void orbitParent() {
        circle(parent, 40f);
    }

    @Override
    public void circle(Position target, float radius) {
        float angle = (Time.time * 2f) + (unit.id * 50); 
        moveTo(Tmp.v1.trns(angle, radius).add(target.getX(), target.getY()), 0f);
    }
}
