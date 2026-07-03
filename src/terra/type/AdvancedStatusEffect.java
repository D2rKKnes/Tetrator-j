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
    public float percentDamage;
    public float removeDamage;

    public AdvancedStatusEffect(String name){
        super(name);
        allDatabaseTabs = true;
    }

    @Override
    public void setStats(){
        if(percentDamage > 0) stats.add(Stat.damage, percentDamage, StatUnit.perSecond);
        if(percentDamage < 0) stats.add(Stat.healing, -percentDamage, StatUnit.perSecond);
    }
}
