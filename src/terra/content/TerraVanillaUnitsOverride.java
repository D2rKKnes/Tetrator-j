package terra.content;

import java.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.func.Cons;
import arc.*;
import terra.ai.*;
import terra.content.*;
import terra.type.*;
import terra.type.bullet.*;
import terra.type.weapons.*;
import terra.type.ability.*;
import terra.type.units.*;
import mindustry.Vars;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;
import blackhole.entities.bullet.*;
import blackhole.entities.part.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class TerraVanillaUnitsOverride {
    public static void load() {
        UnitTypes.scepter.immunities = ObjectSet.with(StatusEffects.blasted);
        UnitTypes.reign.immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.melting, StatusEffects.blasted, TerraStatusEffects.shieldDamage);
        UnitTypes.reign.abilities.add(new SappingFieldAbility(TerraStatusEffects.shieldDamage, 3 * 60f, 5 * 60f, UnitTypes.reign.hitSize * 2.5f, Pal.negativeStat, new Effect(40, e -> {
            color(e.color);
            stroke(e.fout());
            Lines.circle(e.x, e.y, e.fin(Interp.circle) * e.rotation);
        })));
        UnitTypes.reign.abilities.add(
            new StatusFieldAbility(TerraStatusEffects.shieldRegen, 4 * 60f, 10 * 60f, UnitTypes.reign.hitSize * 2.5f){{
                color = Pal.stat; 
                activeEffect = new Effect(40, e -> {
                    color(e.color);
                    stroke(e.fout());
                    Lines.circle(e.x, e.y, e.fin(Interp.circle) * e.rotation);
                    stroke(e.fout() * 3f);
                    Lines.circle(e.x, e.y, UnitTypes.reign.hitSize * 2.5f);
                });
            }}
        );
        UnitTypes.reign.weapons.add(
            new Weapon("terra-heavy-artillery"){{
                x = -41f / 4f;
                y = -9f / 4f;
                shootY = 8f;
                recoil = 1f;
                recoilTime = 30f;
                reload = 3f;
                mirror = false;
                shootSound = Sounds.shootFlame;
                rotate = true;
                rotateSpeed = 2.4f;
                rotationLimit = 30f;
                parentizeEffects = true;
                shake = 0.4f;
                ejectEffect = Fx.none;
                bullet = new BulletType(4.2f, 18f){{
                    ammoMultiplier = 3f;
                    hitSize = 7.3f;
                    lifetime = 18f;
                    pierce = true;
                    pierceBuilding = pierceArmor = true;
                    pierceCap = 3;
                    statusDuration = 60f * 7;
                    shootEffect = new Effect(33f, 100f, e -> {
                        color(Pal.lightFlame, Pal.darkFlame, Color.gray, e.fin());
                
                        randLenVectors(e.id, 14, e.finpow() * 90f, e.rotation, 11f, (x, y) -> {
                            Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.5f);
                        });
                    }).followParent(false);
                    hitEffect = Fx.hitFlameSmall;
                    despawnEffect = Fx.none;
                    status = StatusEffects.burning;
                    keepVelocity = false;
                    hittable = false;
                    buildingDamageMultiplier = 1.15f;
                }};
            }},
            new Weapon("terra-medium-red-weapon"){{
                x = 41f / 4f;
                y = -9f / 4f;
                shootY = 6f;
                reload = 120f;
                mirror = false;
                recoil = 3f;
                shake = 2f;
                rotate = true;
                rotateSpeed = 4f;
                ejectEffect = Fx.casing3Double;
                shootSound = Sounds.shootRipple;
                bullet = new ArtilleryBulletType(3f, 50, "shell"){{
                    knockback = 3f;
                    lifetime = 65f;
                    maxRange = 260f;
                    width = height = 14f;
                    collides = true;
                    collidesTiles = true;
                    splashDamageRadius = 5.5f * 8f;
                    splashDamage = 125f;
                    armorMultiplier = 0.4f;
                    backColor = Pal.bulletYellowBack;
                    frontColor = Pal.bulletYellow;
                    despawnHit = true;
                    hitShake = 5f;
                    hitSound = Sounds.explosionReactor;
                    hitSoundVolume = 2f;
                    hitEffect = TerraFx.smoothColorCircle(Pal.bulletYellow, 5.5f * 8f, 60f);
                    status = StatusEffects.blasted;
                    incendAmount = 15;
                    incendChance = 0.9;
                    incendSpread = 6f * 8f;
                }};
            }}
        );
        UnitTypes.vela.immunities = ObjectSet.with(StatusEffects.shocked);
        UnitTypes.vela.weapons.add(
            new Weapon("terra-big-heal-gun"){{
                x = 46f / 4f;
                y = 30f / 4f;
                shootY = 5f;
                reload = 40f;
                mirror = true;
                shootSound = Sounds.shootLancer;
                rotate = true;
                rotateSpeed = 2.1f;
                rotationLimit = 40f;
                shootCone = 15f;
                recoil = 2f;
                layerOffset = -0.001f;
    
                bullet = new EmpBulletType() {{
                    sprite = "circle-bullet";
                    damage = 45f;
                    splashDamage = 25f;
                    splashDamageRadius = 3f * 8f;
                    radius = 3f * 8f;
                    timeIncrease = 1.5f;
                    timeDuration = 60f * 5f;
                    powerSclDecrease = 0.7f;
                    speed = 4f;
                    lifetime = 50f;
                    width = 5f;
                    height = 5f;
                    backColor = Pal.heal;
                    hitColor = Pal.heal;
                    trailColor = Pal.heal;
                    frontColor = Color.white;
                    trailWidth = 3f;
                    trailLength = 16;
                    hitSound = Sounds.explosionNavanax;
                    homingPower = 0.25f;
                    homingRange = 28f;
                    collidesTeam = true;
                    healPercent = 1.5f;
        
                    hitEffect = new ExplosionEffect() {{
                        waveColor = smokeColor = sparkColor = Pal.heal;
                        waveRad = 3f * 8f;
                        sparkStroke = 2f;
                        sparks = 5;
                    }};
        
                    despawnEffect = new ExplosionEffect() {{
                        waveColor = smokeColor = Pal.heal;
                        waveRad = 3f * 8f;
                        sparks = 0;
                    }};
        
                    intervalBullets = 1;
                    intervalRandomSpread = 360f;
                    bulletInterval = 15f;
                    fragBullets = 1;
                    fragRandomSpread = 45f;
                    intervalBullet = fragBullet = new LaserBulletType() {{
                        length = 24f;
                        width = 16f;
                        lifetime = 30f;
                        colors = new Color[]{Pal.heal, Pal.heal, Color.white};
                        damage = 20f;
                        collidesTeam = true;
                        collidesAir = true;
                        healPercent = 0.5f;
                        sideWidth = 0f;
                    }};
                }};
            }}
        );
        UnitTypes.corvus.immunities = ObjectSet.with(StatusEffects.shocked, StatusEffects.electrified, TerraStatusEffects.zapped);
        UnitTypes.corvus.weapons.get(0).bullet.status = StatusEffects.electrified;
        UnitTypes.corvus.weapons.get(0).bullet.statusDuration = 4f * 60f;
        UnitTypes.corvus.weapons.add(
            new Weapon("terra-heal-charger"){{
                reload = cooldownTime = 120f;
                x = 57f / 4f;
                y = -10f / 4f;
                shootY = 16f / 4f;
                layerOffset = 0.001f;
                rotate = true;
                rotateSpeed = 3f;
                recoilTime = reload * 0.8f;
                minWarmup = 0.9f;
                shoot.firstShotDelay = 44;
                shootSound = TerraSounds.shootElectro;
                shootSoundVolume = 1.3f;
                chargeSound = Sounds.chargeVela;
                heatColor = Pal.heal;
                parts.addAll(
                    new RegionPart("-blade") {{
                        mirror = true;
                        under = true;
                        moves.add(new PartMove(PartProgress.warmup, 0.75f, 0f, 0f));
                        x = 0;
                        y = 0;
                        moveRot = -25;
                        progress = PartProgress.recoil.curve(Interp.fastSlow);
                    }},
                    new RegionPart("-aglow") {{
                        mirror = under = drawRegion = false;
                        x = y = 0;
                        heatColor = Pal.heal;
                        heatProgress = PartProgress.charge.curve(Interp.fastSlow);
                    }},
                    new RegionPart("-none") {{
                        mirror = under = drawRegion = false;
                        progress = PartProgress.charge.curve(Interp.fastSlow);
                        y = 0f;
                        moveY = 16f / 4f;
                        children.addAll(
                            new FlarePart(){{
                                progress = PartProgress.charge.curve(Interp.fastSlow);
                                color1 = Pal.heal;
                                radius = 0;
                                radiusTo = 9;
                                stroke = 3f;
                            }}
                        );
                    }}
                );
                bullet = new FancyLaserBulletType() {{
                    damage = 105f;
                    // sideAngle = 5f;
                    // sideWidth = 1f;
                    // sideLength = 50f;
                    healPercent = 6f;
                    collidesTeam = true;
                    width = 24f;
                    length = 175f;
                    hitColor = lightningColor = Pal.heal;
                    shootEffect = Fx.colorSpark;
                    status = StatusEffects.electrified;
                    statusDuration = 90f;
                    colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};

                    int count = 4;
                    for(int j = 0; j < count; j++){
                        int s = j;
                        for(int i : Mathf.signs){
                            float fin = 0.05f + (j + 1) / (float)count;
                            float spd = 7f;
                            float life = lifetime / Mathf.lerp(fin, 1f, 0.5f);
                            boolean show = j == 0 && i > 0;
                            spawnBullets.add(new BasicBulletType(spd * fin, 30){{
                                drag = 0.002f;
                                width = 9f;
                                height = 8f;
                                lifetime = life + 5f;
                                weaveRandom = false;
                                hitSize = 5f;
                                showStats = show;
                                hitColor = backColor = trailColor = Pal.heal;
                                frontColor = Color.white;
                                trailWidth = 2.5f;
                                trailLength = 5;
                                weaveScale = (3f + s/2f) / 1.2f;
                                weaveMag = i * (4f - fin * 2f);

                                splashDamage = 35f;
                                splashDamageRadius = 30f;
                                healPercent = 0.5f;
                                collidesTeam = true;
                                despawnEffect = hitEffect = new ExplosionEffect(){{
                                    lifetime = 50f;
                                    waveStroke = 4f;
                                    waveColor = sparkColor = trailColor;
                                    waveRad = 30f;
                                    smokeSize = 7f;
                                    smokes = 6;
                                    smokeSizeBase = 0f;
                                    smokeColor = trailColor;
                                    sparks = 5;
                                    sparkRad = 30f;
                                    sparkLen = 3f;
                                    sparkStroke = 1.5f;
                                }};
                            }});
                        }
                    }
                }};
            }},
            new Weapon("terra-corvus-launcher"){{
                reload = 125f + (6f * 7f);
                shootY = 0f;
                x = 51 / 4f;
                y = -52f / 4;
                rotate = false;
                shootCone = 360f;
                baseRotation = 180f + 10;
                mirror = true;
                alternate = false;
                layerOffset = -0.001f;
                minWarmup = 0.96f;
                inaccuracy = 5f;
                recoil = 1f;
                shootSound = TerraSounds.shootLaunch;
                shootSoundVolume = 0.7f;
                shoot = new ShootBarrel(){{
                    shots = 6;
                    shotDelay = 7f;
                    barrels = new float[]{
                        13.5f / 4f, 2f, 0f, 
                        0f, 2f, 0f, 
                        -13.5f / 4f, 2f, 0f
                    };
                }};
                parts.addAll(
                    new RegionPart("") {{
                        name = "terra-heal-hidden-launcher";
                        mirror = false;
                        under = true;
                        //moves.add(new PartMove(PartProgress.warmup, 0f, 7f, 0f));
                        x = 0;
                        y = -7f;
                        moveY = 7f;
                        progress = PartProgress.warmup;
                    }}
                );
                bullet = new BasicBulletType(4f, 77){{
                    lifetime = 85f;
                    rangeOverride = 250f;
                    splashDamage = damage * 0.6f;
                    splashDamageRadius = 5f * 8f;
                    scaledSplashDamage = true;
                    status = StatusEffects.electrified;
                    statusDuration = 45f;
                    healPercent = 0.75f;
                    collidesTeam = true;
                    sprite = "terra-strike";
                    drag = -0.004f;
                    followAimSpeed = 5f;
                    width = 12f;
                    height = 16f;
                    shrinkY = 0.1f;
                    hitColor = lightColor = trailColor = backColor = Pal.heal;
                    frontColor = Color.white;
                    trailWidth = 1.7f;
                    trailLength = 8;
                    despawnHit = true;
                    despawnEffect = hitEffect = new MultiEffect(TerraFx.circleFadeBig, new WrapEffect(Fx.shootQuellPulse, hitColor));
                    despawnSound = Sounds.unitExplode1;
                }};
            }}
        );
        UnitTypes.spiroct.immunities = ObjectSet.with(StatusEffects.sapped);
        UnitTypes.arkyid.immunities = ObjectSet.with(StatusEffects.sapped);
        UnitTypes.toxopid.immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.melting, StatusEffects.sapped);
        UnitTypes.zenith.immunities = ObjectSet.with(StatusEffects.blasted);
        UnitTypes.antumbra.immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.melting, StatusEffects.blasted);
        UnitTypes.eclipse.immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.melting, StatusEffects.blasted, TerraStatusEffects.zapped);
        UnitTypes.mega.immunities = ObjectSet.with(StatusEffects.shocked, TerraStatusEffects.zapped);
        UnitTypes.quad.immunities = ObjectSet.with(StatusEffects.shocked, StatusEffects.electrified, TerraStatusEffects.zapped);
        UnitTypes.oct.immunities = ObjectSet.with(StatusEffects.shocked, StatusEffects.electrified, TerraStatusEffects.zapped, TerraStatusEffects.energyOverload, TerraStatusEffects.shieldDamage);
        UnitTypes.omura.weapons.get(0).layerOffset = 0.003f;
        UnitTypes.omura.weapons.add(
            new ConstructWeapon("terra-omura-drone"){{
                x = 0f;
                y = -141.5f / 4f;
                shootY = 0f;
                shootSound = Sounds.shootMissileSmall;
                shootCone = 360f;
                reload = 8f * 60f;
                rotate = false;
                //rotateSpeed = 0.4f;
                mirror = false;
                useAttackRange = false;
                bullet = new BulletType(){{
                    shootEffect = smokeEffect = Fx.none;
                    //shake = 1f;
                    speed = 0.001f;
                    keepVelocity = false;
                    spawnUnit = TerraUnitTypes.omuraDrone;
                    rangeOverride = 500f;
                }};
            }},
            new Weapon("terra-heavy-gun"){{
                reload = 158f + (3f * 18f);
                x = -77f / 4f;
                y = -121.5f / 4f;
                shootY = 33f / 4f;
                rotate = true;
                rotateSpeed = 2f;
                shootSound = Sounds.shootScepterSecondary;
                shootSoundVolume = 1.1f;
                chargeSound = Sounds.chargeVela;
                parentizeEffects = true;
                
                mirror = false;
                recoils = 2;
                recoil = 0f;
                recoilTime = 6f;
                layerOffset = 0.001f;
                cooldownTime = 90f;
                shoot = new ShootBarrel(){{
                    shots = 18;
                    shotDelay = 3f;
                    firstShotDelay = 40f;
                    barrels = new float[]{
                        10f / 4, 11f / 4, 0f, 
                        -10f / 4, 11f / 4, 0f
                    };
                }};
                parts.add(
                    new RegionPart("-barrel-l") {{
                        mirror = false;
                        under = true;
                        recoilIndex = 0;
                        progress = heatProgress = PartProgress.recoil;
                        moveY = -1.25f;
                    }},
                    new RegionPart("-barrel-r") {{
                        mirror = false;
                        under = true;
                        recoilIndex = 1;
                        progress = heatProgress = PartProgress.recoil;
                        moveY = -1.25f;
                    }}
                );
                bullet = new LaserBoltBulletType(3.7f, 26){{
                    scaleKeepVelocity = true;
                    inaccuracy = 3f;
                    width = 2.5f;
                    height = 6f;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    chargeEffect = new Effect(41f, 100f, e -> {
                        color(Pal.stat);
                        stroke(e.fin() * 2f);
                
                        Fill.circle(e.x, e.y, e.fin() * 6);
                
                        randLenVectors(e.id, 20, 40f * e.fout(), (x, y) -> {
                            Fill.circle(e.x + x, e.y + y, e.fin() * 1.5f);
                            Drawf.light(e.x + x, e.y + y, e.fin() * 3f, Pal.stat, 0.7f);
                        });
                
                        color();
                
                        Fill.circle(e.x, e.y, e.fin() * 3);
                        Drawf.light(e.x, e.y, e.fin() * 6f, Pal.stat, 0.7f);
                    }).followParent(true).rotWithParent(true);
                    
                    trailWidth = 1.6f;
                    trailLength = 4;
                    shootEffect = Fx.shootSmallColor;
                    smokeEffect = Fx.hitLaserColor;
                    backColor = trailColor = Pal.yellowBoltFront;
                    hitColor = Pal.yellowBoltFront;
                    frontColor = Color.white;
                    lightColor = Pal.yellowBoltFront;

                    lifetime = 70f;
                    shake = 1.3f;
                    buildingDamageMultiplier = 0.4f;
                    shieldDamageMultiplier = 1.6f;
                    armorMultiplier = 0.2f;

                    fragBullets = 1;
                    fragRandomSpread = 30f;
                    fragLifeMax = 2f;
                    fragOffsetMax = 2f;
                    fragBullet = new LaserBulletType(){{
                        damage = 18f;
                        buildingDamageMultiplier = 0.5f;
                        shieldDamageMultiplier = 1.4f;
                        armorMultiplier = 0.3f;
                        sideAngle = 25f;
                        sideWidth = 1.5f;
                        sideLength = 14f;
                        width = 10f;
                        length = 40f;
                        lifetime = 20f;
                        colors = new Color[]{Pal.yellowBoltFront.cpy().a(0.4f), Pal.yellowBoltFront, Color.white};
                    }};
                }};
            }},
            new Weapon("terra-heavy-rockets"){{
                reload = 70f + (4f * 7f);
                x = 77f / 4f;
                y = -121.5f / 4f;
                shootY = 0f;
                rotate = true;
                rotateSpeed = 2.6f;
                mirror = false;
                shoot = new ShootBarrel(){{
                    shots = 7;
                    shotDelay = 4f;
                    barrels = new float[]{
                        0f, 20f / 4, 0f, 
                        -9f / 4, 25f / 4, 0f, 
                        0f, 29f / 4, 0f, 
                        9f / 4, 25f / 4, 0f
                    };
                }};
                shootSound = Sounds.shootMissileLong;
                bullet = new BulletType(){{
                    inaccuracy = 10f;
                    shake = 1.3f;
                    speed = 0.001f;
                    keepVelocity = false;
                    spawnUnit = TerraUnitTypes.omuraMissile;
                }};
            }}
        );
        UnitTypes.oxynoe.immunities = ObjectSet.with(StatusEffects.burning);
        UnitTypes.cyerce.immunities = ObjectSet.with(StatusEffects.shocked, TerraStatusEffects.zapped);
        UnitTypes.aegires.immunities = ObjectSet.with(StatusEffects.shocked, StatusEffects.electrified, TerraStatusEffects.zapped);
        UnitTypes.navanax.immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.melting, StatusEffects.shocked, StatusEffects.electrified, TerraStatusEffects.zapped, TerraStatusEffects.energyOverload);
    }
}
