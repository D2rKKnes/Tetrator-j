package terra.content;

import arc.Events;
import arc.graphics.*;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.game.EventType;
import mindustry.type.*;
import mindustry.entities.effect.*;

import static mindustry.Vars.*;
import static mindustry.content.StatusEffects.sapped;

public class TerraStatusEffects{
    public static StatusEffect 

    energyOverload, singularEvaporation;
    
    public static void load(){
        energyOverload = new StatusEffect("energy-overload"){{
            color = Color.valueOf("bf92f9");
            speedMultiplier = 0.7f;
            reloadMultiplier = 0.4f;
            damage = 0.6f;
            transitionDamage = 17f;
            effect = Fx.circleColorSpark;
            init(() -> {
                affinity(StatusEffects.shocked, (unit, result, time) -> {
                    unit.damage(transitionDamage);

                    if(unit.team == state.rules.waveTeam){
                        Events.fire(EventType.Trigger.shock);
                    }
                });
            });
        }};
        singularEvaporation = new StatusEffect("singular-evaporation"){{
            color = Color.valueOf("3d1f7a");
            speedMultiplier = 0.5f;
            reloadMultiplier = 0.15f;
            damage = 7.8f;
        }};
    }
}
