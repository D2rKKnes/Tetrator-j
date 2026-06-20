package terra.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
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

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class TerraStatusEffects{
    public static StatusEffect 

    energyOverload, singularEvaporation, impactStun, radited, extinction, warped, warpPower, shockwaveImpact, delta32, deltaImmunized;
    
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
            buildSpeedMultiplier = 0f;
            disarm = true;
        }};

        radited = new StatusEffect("radited"){{
            color = Color.valueOf("bcff73");
            healthMultiplier = 0.9f;
            damageMultiplier = 0.8f;
            speedMultiplier = 0.8f;
            reloadMultiplier = 0.8f;
            intervalDamage = 19.4f;
            intervalDamageTime = 15f;
            damage = 0.4f;
            init(() -> {
                affinity(corroded, (unit, result, time) -> {
                    unit.damagePierce(20);
                    Fx.burning.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(burning, Math.min(time + result.time, 300f));
                });
            });
        }};

        singularEvaporation = new StatusEffect("singular-evaporation"){{
            color = Color.valueOf("3d1f7a");
            speedMultiplier = 0.5f;
            reloadMultiplier = 0.15f;
            buildSpeedMultiplier = 0.5f;
            damage = 17.8f;
            effectChance = 0.075f;
            effect = new Effect(20f, 20f, e -> {
                Draw.color(Color.white, color, e.fin() + 0.35f);
                Lines.stroke(1.5f * e.fout(Interp.pow3Out));
                Lines.square(e.x, e.y, Mathf.randomSeed(e.id, 2f, 8f) * e.fin(Interp.pow2Out) + 6f, 45);
            });
        }};

        extinction = new StatusEffect("extinction"){{
            color = Color.valueOf("e13131");
            speedMultiplier = 0.4f;
            healthMultiplier = 0.2f;
            reloadMultiplier = 0.6f;
            damageMultiplier = 0.5f;
            damage = 3.7f;
            effect = new Effect(30f, e -> {
                color(Color.valueOf("e13131"));

                randLenVectors(e.id, 2, 1f + e.fin() * 2f, (x, y) -> {
                    Fill.square(e.x + x, e.y + y, e.fslope() * 1.1f, 45f);
                });
            });
            effectChance = 0.1f;
        }};

        warped = new StatusEffect("warped"){{
            color = Pal.accent;
            speedMultiplier = 0.5f;
            healthMultiplier = 5f;
            buildSpeedMultiplier = 0.2f;
            effect = Fx.overdriven;
            effectChance = 0.008f;
        }};

        warpPower = new StatusEffect("warp-power"){{
            color = Pal.accent;
            healthMultiplier = 2f;
            damageMultiplier = 1.3f;
            effect = Fx.overdriven;
            effectChance = 0.008f;
        }};

        shockwaveImpact = new StatusEffect("shockwave-impact"){{
            color = Color.valueOf("cbcbcb");
            speedMultiplier = 0f;
            buildSpeedMultiplier = 0f;
            dragMultiplier = 0.2f;
            disarm = true;
        }};

        delta32 = new StatusEffect("delta32"){{
            color = Pal.negativeStat;
            healthMultiplier = 0.05f;
            damageMultiplier = 0.1f;
            speedMultiplier = 0.3f;
            reloadMultiplier = 0.3f;
            buildSpeedMultiplier = 0.5f;
            intervalDamage = 386.4f;
            intervalDamageTime = 95f;
            //damage = 28.2f;
            permanent = true;
            init(() -> opposite(deltaImmunized));
        }};

        deltaImmunized = new StatusEffect("delta-immunized"){{
            color = Pal.heal;
            permanent = true;
            init(() -> opposite());
        }};
    }
}
