package terra.content;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Geometry;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import arc.Events;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.Vars;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Env;
import mindustry.entities.effect.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.type.*;
import mindustry.type.unit.*;

import static mindustry.Vars.*;
import static mindustry.content.StatusEffects.sapped;

public class TerraStatusEffects{
    public static StatusEffect 

    energyOverload, singularEvaporation, impactStun;
    
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
        impactStun = new StatusEffect("impact-stun"){{
            color = Color.valueOf("d46a36");
            speedMultiplier = 0.3f;
            disarm = true;
        }};
        singularEvaporation = new StatusEffect("singular-evaporation"){{
            color = Color.valueOf("3d1f7a");
            speedMultiplier = 0.5f;
            reloadMultiplier = 0.15f;
            damage = 17.8f;
            effectChance = 0.075f;
            effect = new Effect(20f, 20f, e -> {
                Draw.color(Color.white, color, e.fin() + 0.35f);
                Lines.stroke(1.5f * e.fout(Interp.pow3Out));
                Lines.square(e.x, e.y, Mathf.randomSeed(e.id, 2f, 8f) * e.fin(Interp.pow2Out) + 6f, 45);
            });
        }};
    }
}
