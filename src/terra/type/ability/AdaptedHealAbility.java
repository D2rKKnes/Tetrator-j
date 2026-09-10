package terra.type.ability;

import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.entities.Effect;
import arc.util.*;
import arc.audio.*;
import arc.math.*;
import mindustry.entities.*;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import java.util.*;

public class AdaptedHealAbility extends RepairFieldAbility {
    public Color applyColor = Pal.heal;
    public boolean ignoreHealthMultiplier = true;
    //Percent per tick
    public float selfHealAmount = 0.0005f;
    public float selfHealReloadTime = -1;
    protected float lastHealth = 0;
    protected float selfHealReload = 0;

    public AdaptedHealAbility(float amount, float reload, float range, Color applyColor) {
        this(amount, reload, range);
        this.applyColor = applyColor;
    }

    public AdaptedHealAbility(float amount, float reload, float range) {
        super(amount, reload, range);

        healEffect = new Effect(11.0F, (e) -> {
            Draw.color(e.color);
            Lines.stroke(e.fout() * 1.667F);
            Lines.circle(e.x, e.y, 2.0F + e.finpow() * 7.0F);
        });
        activeEffect = new Effect(22.0F, (e) -> {
            Draw.color(e.color);
            Lines.stroke(e.fout() * 2.0F);
            Lines.circle(e.x, e.y, e.finpow() * e.rotation);
        });
    }

    public AdaptedHealAbility modify(Cons<AdaptedHealAbility> modifier) {
        modifier.get(this);
        return this;
    }

    public void update(Unit unit) {
        healTimer += Time.delta;
        downTimer = smartHeal && healthChange >= healthMissing && healthMissing > 0f ? downTimer + Time.delta : 0f;

        if(healTimer >= reload){
            targets.clear();
            hasHealed = healNow = false;
            healthChange = healthMissing;
            healthMissing = sumMaxHealth = sumTypeMult = 0f;

            boolean limitTargets = maxTargets >= 0;
            float healPercentMult = healPercent / 100f;

            Units.nearby(unit.team, unit.x, unit.y, range, other -> {
                //check for 2 more targets just in case
                if(limitTargets && targets.size >= maxTargets + 2) return;

                if(other.damaged()){
                    targets.add(other);
                    if(smartHeal){
                        float maxHealth = other.maxHealth();
                        healthMissing += maxHealth - other.health();
                        sumMaxHealth += maxHealth;
                        sumTypeMult += unit.type == other.type ? sameTypeHealMult : 1f;
                        if(other.healthf() < smartHealPercent) healNow = true;
                    }
                }
            });
            int targetCount = targets.size;

            //mixed approach, care both about groups and single low hp units
            float ratio = amount + healPercentMult * sumMaxHealth * (sumTypeMult / targetCount);
            float requiredHeals = (healthMissing * 0.7f + healthMissing / (limitTargets ? maxTargets : targetCount) * 0.3f) / smartHealStrength / ratio;

            if(requiredHeals >= 1f || !smartHeal || healNow || downTimer >= smartDowntime){

                //sort closest if number of targets is limited
                if(limitTargets){
                    boolean isSameType = sameTypeHealMult < 1f;
                    targets.sort(u -> u.dst2(unit.x, unit.y) + (isSameType && u.type() == unit.type ? 6400f : 0f));
                }

                int len = limitTargets ? Math.min(targetCount, maxTargets) : targetCount;
                for(int i = 0; i < len; i++){
                    Unit other = targets.get(i);
                    if(other.damaged()){
                        float maxHealth = other.maxHealth();
                        float healMult = unit.type == other.type ? sameTypeHealMult : 1f;
                        other.heal((amount + healPercentMult * maxHealth) * healMult);
                        healEffect.at(other.x, other.y, 0, applyColor, parentizeEffects ? other : null);
                        hasHealed = true;
                    }
                }
                if(hasHealed){
                    healTimer = 0f;
                    activeEffect.at(unit.x, unit.y, range, applyColor);
                    sound.at(unit, 1f + Mathf.range(0.1f), soundVolume);
                }

            //increase how often this checks if there are damaged units but still below the healing threshold
            }else if(smartHeal && targetCount > 0){
                healTimer = reload >= (2f * smartInterval) ? reload - smartInterval : smartInterval;
            }else if(randDesync > 0){
                healTimer = Mathf.random(randDesync) * reload;
            }else{
                healTimer = 0;
            }
        }

        if (selfHealReloadTime < 0) return;

        if (lastHealth <= unit.health && unit.damaged()) {
            selfHealReload += Time.delta;

            if (selfHealReload > selfHealReloadTime) {
                unit.healFract(selfHealAmount * (ignoreHealthMultiplier ? 1 : 1 / unit.healthMultiplier));
            }
        } else {
            selfHealReload = 0;
        }

        lastHealth = unit.health;
    }
}
