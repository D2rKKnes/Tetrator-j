package terra.type;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.graphics.MultiPacker.*;
import mindustry.world.meta.*;

public class AdvancedStatusEffect extends StatusEffect{
    public static final Stat dragStat = new Stat("terra-drag");
    public static final Stat removeDamag = new Stat("removedamage", StatCat.function);
    public static final Stat removeHeal = new Stat("removeheal", StatCat.function);
    public static final Stat shieldDamag = new Stat("shielddamage", StatCat.function);
    public static final Stat shieldHeal = new Stat("shieldheal", StatCat.function);
    public static final Stat shieldCap = new Stat("shieldhealcap", StatCat.function);
    public static final Stat movementDamag = new Stat("movementdamage", StatCat.function);
    public static final Stat movementHeal = new Stat("movementheal", StatCat.function);
    public static final Stat disarmStat = new Stat("terra-disarm", StatCat.general);
    public static final Stat permanentStat = new Stat("terra-permanent", StatCat.general);
    public static final Stat instantDeath = new Stat("instantdeath", StatCat.general);
    //Damage in percent from unit max health per second
    public float percentDamage;
    //Damage dealt when status removed
    public float removeDamage;
    //Shield damage per tick
    public float shieldDamage;
    //Damage dealt when moving
    public float movementDamage;
    //Incresing or decreasing effectChance when moving
    public float effectChanceMoveIncrease;
    //How much can shield regenerate
    public float shieldHealCap = 500f;
    //Effect when status removed
    public Effect removeEffect = Fx.none;
    //When true, any unit will die instantly
    public boolean instakill = false;
    
    protected ObjectFloatMap<Unit> shieldTimer = new ObjectFloatMap<>();

    public AdvancedStatusEffect(String name){
        super(name);
        allDatabaseTabs = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        if(dragMultiplier != 1) stats.addMultModifier(dragStat, dragMultiplier);
        if(percentDamage > 0) stats.add(Stat.damage, percentDamage + "%" + StatUnit.perSecond.localized());
        if(percentDamage < 0) stats.add(Stat.healing, -percentDamage + "%" + StatUnit.perSecond.localized());
        if(removeDamage > 0) stats.add(removeDamag, removeDamage);
        if(removeDamage < 0) stats.add(removeHeal, -removeDamage);
        if(shieldDamage > 0) stats.add(shieldDamag, shieldDamage * 60f, StatUnit.perSecond);
        if(shieldDamage < 0){
            stats.add(shieldHeal, -shieldDamage * 60f, StatUnit.perSecond);
            stats.add(shieldCap, shieldHealCap);
        }
        if(movementDamage > 0) stats.add(movementDamag, movementDamage * 60f, StatUnit.perSecond);
        if(movementDamage < 0) stats.add(movementHeal, -movementDamage * 60f, StatUnit.perSecond);
        if(disarm) stats.add(disarmStat, disarm);
        if(permanent) stats.add(permanentStat, permanent);
        if(instakill) stats.add(instantDeath, instakill);
    }

    @Override
    public void update(Unit unit, StatusEntry entry){
        float oldChance = effectChance;
        if(effectChanceMoveIncrease != 0f && unit.vel.len() > 0.1f){
            effectChance += effectChanceMoveIncrease;
        }
        super.update(unit, entry);
        effectChance = oldChance;
        if(percentDamage > 0){
            unit.damageContinuousPierce(unit.maxHealth * ((percentDamage / 100f) / 60));
        }else if(percentDamage < 0){ //heal unit
            unit.heal(unit.maxHealth * (-1f * ((percentDamage / 100f) / 60)) * Time.delta);
        }
        if(shieldDamage > 0 && unit.shield > 0){
            unit.shield = Math.max(0, unit.shield - shieldDamage);
        }else if(shieldDamage < 0 && unit.shield < shieldHealCap){
            if(unit.shield > 0){
                shieldTimer.remove(unit, 0f);
                unit.shield = Math.min(shieldHealCap, unit.shield + (-shieldDamage));
            }else{
                if(!shieldTimer.containsKey(unit)){
                    shieldTimer.put(unit, 1f / 3f);
                }

                float t = shieldTimer.get(unit, 0f);
                if(t > 0f){
                    t -= Time.delta;
                    if(t <= 0f){
                        unit.shield = Math.min(shieldHealCap, unit.shield + (-shieldDamage));
                        shieldTimer.remove(unit);
                    }else{
                        shieldTimer.put(unit, t);
                    }
                }
            }
        }
        if(movementDamage > 0 && unit.vel.len() > 0.1f){
            unit.damageContinuousPierce(movementDamage);
        }else if(movementDamage < 0 && unit.vel.len() > 0.1f){ //heal unit
            unit.heal(-1 * movementDamage * Time.delta);
        }
        if(instakill) unit.kill();
    }

    @Override
    public void onRemoved(Unit unit){
        super.onRemoved(unit);
        if(removeDamage > 0){
            unit.damageContinuousPierce(removeDamage);
        }else if(removeDamage < 0){ //heal unit
            unit.heal(-1f * removeDamage);
        }
        if(!Vars.headless && removeEffect != Fx.none && !unit.inFogTo(Vars.player.team())){
            removeEffect.at(unit.x, unit.y, 0, color, parentizeEffect ? unit : null);
        }
    }
}
