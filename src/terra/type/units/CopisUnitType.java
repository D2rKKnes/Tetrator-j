package terra.type.unit;

import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import terra.content.*;

/** Config class for special Copis unit properties. */
public class CopisUnitType extends UnitType{

    public CopisUnitType(String name){
        super(name);
        outlineColor = Color.valueOf("36334d");
        immunities.addAll(StatusEffects.burning, StatusEffects.melting, TerraStatusEffects.sunBurn, StatusEffects.corroded, TerraStatusEffects.radited);
        envDisabled = Env.none;
        healColor = Color.valueOf("fa88d6");
        researchCostMultiplier = 10f;
    }
}
