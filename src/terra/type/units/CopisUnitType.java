package terra.type.units;

import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import terra.content.*;
import terra.graphics.*;

/** Config class for special Copis unit properties. */
public class CopisUnitType extends UnitType{

    public CopisUnitType(String name){
        super(name);
        outlineColor = TerraPal.copisOutlime;
        immunities.addAll(StatusEffects.burning, StatusEffects.melting, TerraStatusEffects.sunBurn, StatusEffects.corroded, TerraStatusEffects.radited);
        envDisabled = Env.none;
        healColor = TerraPal.pinkFront;
        researchCostMultiplier = 10f;
    }
}
