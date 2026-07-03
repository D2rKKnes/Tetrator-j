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
    public static final Stat removeDamag = new Stat("removedamage", StatCat.function);
    public static final Stat removeHeal = new Stat("removeheal", StatCat.function);
    public float percentDamage;
    public float removeDamage;
    public Effect removeEffect = Fx.none;

    public AdvancedStatusEffect(String name){
        super(name);
        allDatabaseTabs = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        if(percentDamage > 0) stats.add(Stat.damage, percentDamage, StatUnit.percent);
        if(percentDamage < 0) stats.add(Stat.healing, -percentDamage, StatUnit.percent);
        if(removeDamage > 0) stats.add(removeDamag, removeDamage);
        if(removeDamage < 0) stats.add(removeHeal, -removeDamage);
    }

    @Override
    public void update(Unit unit, StatusEntry entry){
        super.update(unit, entry);
        if(percentDamage > 0){
            unit.damageContinuousPierce(unit.maxHealth * ((percentDamage / 100f) / 60));
        }else if(percentDamage < 0){ //heal unit
            unit.heal(unit.maxHealth * (-1f * ((percentDamage / 100f) / 60)) * Time.delta);
        }
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
            effect.at(unit.x, unit.y, 0, color, null);
        }
    }
}
