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
import terra.type.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class TerraStatusEffects{
    public static StatusEffect 

    energyOverload, singularEvaporation, impactStun, radited, extinction, crystalization, 
    warped, warpPower, shockwaveImpact, hyperdrive, delta32, deltaImmunized, purification,
    regeneration, superRegeneration, shieldRegen, shieldDamage, instantDeath,
    
    common, uncommon, rare, epic, legendary;
    
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

        impactStun = new AdvancedStatusEffect("impact-stun"){{
            color = Color.valueOf("d46a36");
            speedMultiplier = 0.3f;
            buildSpeedMultiplier = 0f;
            disarm = true;
        }};

        radited = new StatusEffect("radited"){{
            color = Color.valueOf("bcff73");
            healthMultiplier = 0.9f;
            damageMultiplier = 0.7f;
            reloadMultiplier = 0.8f;
            intervalDamage = 36.4f;
            intervalDamageTime = 15f;
            transitionDamage = 20f;
            effectChance = 0.1f;
            effect = Fx.corrosionVapor;
            init(() -> {
                affinity(StatusEffects.corroded, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    result.set(radited, Math.min(time + result.time, 300f));
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

        crystalization = new StatusEffect("crystalization"){{
            color = Color.valueOf("ff7163");
            healthMultiplier = 1.15f;
            speedMultiplier = 0.6f;
            reloadMultiplier = 0.5f;
            intervalDamage = 88f;
            intervalDamageTime = 90f;
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

        shockwaveImpact = new AdvancedStatusEffect("shockwave-impact"){{
            color = Color.valueOf("cbcbcb");
            speedMultiplier = 0f;
            buildSpeedMultiplier = 0f;
            dragMultiplier = 0.2f;
            disarm = true;
        }};

        hyperdrive = new AdvancedStatusEffect("hyperdrive"){{
            color = Pal.lancerLaser;
            healthMultiplier = 0.75f;
            damageMultiplier = 1.7f;
            speedMultiplier = 1.3f;
            reloadMultiplier = 1.2f;
            buildSpeedMultiplier = 0.5f;
            damage = -0.03f;
            permanent = true;
            effectChance = 0.05f;
            effect = new Effect(20f, 20f, e -> {
                Draw.color(Color.white, color, e.fin() + 0.35f);
                Lines.stroke(1.5f * e.fout(Interp.pow3Out));
                Lines.square(e.x, e.y, Mathf.randomSeed(e.id, 2f, 8f) * e.fin(Interp.pow2Out) + 6f, 45);
            });
        }};

        regeneration = new AdvancedStatusEffect("regeneration"){{
            color = Pal.heal;
            speedMultiplier = 1.1f;
            percentDamage = -3f;
            effectChance = 0.075f;
            effect = new Effect(35f, e -> {
                Draw.color(color, Color.white, e.fin() - 0.5f);
                randLenVectors(e.id, 2, 1f + e.fin() * 2f, (x, y) -> {
                    Fill.square(e.x + x, e.y + y, e.fslope() * 1.1f, 45f);
                });
            });
        }};
        superRegeneration = new AdvancedStatusEffect("super-regeneration"){{
            color = Pal.heal;
            speedMultiplier = 0.8f;
            buildSpeedMultiplier = 0.75f;
            percentDamage = -9f;
            effectChance = 0.05f;
            effect = new Effect(20f, 20f, e -> {
                Draw.color(Color.white, color, e.fin() + 0.35f);
                Lines.stroke(1.5f * e.fout(Interp.pow3Out));
                Lines.square(e.x, e.y, Mathf.randomSeed(e.id, 2f, 8f) * e.fin(Interp.pow2Out) + 6f, 45);
            });
        }};

        shieldRegen = new AdvancedStatusEffect("shield-restoration"){{
            color = Pal.accent;
            shieldDamage = -0.25f;
            shieldHealCap = 1000f;
            effectChance = 0.05f;
        }};
        shieldDamage = new AdvancedStatusEffect("shield-destruction"){{
            color = Pal.negativeStat;
            shieldDamage = 0.25f;
            effectChance = 0.05f;
        }};

        delta32 = new AdvancedStatusEffect("delta32"){{
            color = Pal.negativeStat;
            healthMultiplier = 0.05f;
            damageMultiplier = 0.1f;
            speedMultiplier = 0.3f;
            reloadMultiplier = 0.3f;
            buildSpeedMultiplier = 0.5f;
            //intervalDamage = 386.4f;
            //intervalDamageTime = 95f;
            percentDamage = 1.5f;
            permanent = true;
            init(() -> opposite(deltaImmunized));
        }};
        deltaImmunized = new AdvancedStatusEffect("delta-immunized"){{
            color = Pal.heal;
            permanent = true;
            init(() -> opposite());
        }};

        purification = new AdvancedStatusEffect("purification"){{
            color = Color.valueOf("f8d22d");
            speedMultiplier = 0.6f;
            reloadMultiplier = 0.5f;
            damage = 0.7f;
            removeDamage = 500f;
            effectChance = 0.14f;
            effect = new Effect(50f, 20f, e -> {
                color(e.color, Color.white, e.fin());
                stroke(e.fout() * 1.3f + 0.7f);
        
                randLenVectors(e.id, 1, 32f * e.fin(), 90, 7.5f, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 6f + 0.5f);
                });
            });
            removeEffect = new WaveEffect(){{
                sizeTo = 200f;
                colorFrom = Color.white;
                colorTo = color;
                strokeFrom = 8f;
                lifetime = 90f;
            }};
        }};

        instantDeath = new AdvancedStatusEffect("instant-death"){{
            color = Color.white;
            instakill = true;
            show = false;
            permanent = true;
        }};

        common = new RarityStatusEffect("quality-common"){{
            color = tintColor = Color.valueOf("abb1bf");
            permanent = true;
            //show = false;
            drawAura = false;
            alwaysUnlocked = true;
            outline = false;
            init(() -> opposite(uncommon, rare, epic, legendary));
        }};
        uncommon = new RarityStatusEffect("quality-uncommon"){{
            color = tintColor = Color.valueOf("3eec57");
            damageMultiplier = 1.2f;
            healthMultiplier = 1.15f;
            reloadMultiplier = 1.1f;
            permanent = true;
            //show = false;
            alwaysUnlocked = true;
            outline = false;
            sides = 3;
            init(() -> opposite(common, rare, epic, legendary));
        }};
        rare = new RarityStatusEffect("quality-rare"){{
            color = tintColor = Color.valueOf("2495ff");
            damageMultiplier = 1.5f;
            healthMultiplier = 1.3f;
            reloadMultiplier = 1.25f;
            speedMultiplier = 1.2f;
            permanent = true;
            //show = false;
            alwaysUnlocked = true;
            outline = false;
            sides = 4;
            init(() -> opposite(common, uncommon, epic, legendary));
        }};
        epic = new RarityStatusEffect("quality-epic"){{
            color = tintColor = Color.valueOf("c400ff");
            damageMultiplier = 1.8f;
            healthMultiplier = 1.6f;
            reloadMultiplier = 1.5f;
            speedMultiplier = 1.4f;
            buildSpeedMultiplier = 1.25f;
            permanent = true;
            //show = false;
            alwaysUnlocked = true;
            outline = false;
            sides = 6;
            init(() -> opposite(common, uncommon, rare, legendary));
        }};
        legendary = new RarityStatusEffect("quality-legendary"){{
            color = tintColor = Color.valueOf("ff9500");
            damageMultiplier = 2.6f;
            healthMultiplier = 2.2f;
            reloadMultiplier = 2f;
            speedMultiplier = 1.75f;
            buildSpeedMultiplier = 1.5f;
            permanent = true;
            //show = false;
            alwaysUnlocked = true;
            outline = false;
            init(() -> opposite(common, uncommon, rare, epic));
        }};
    }
}
