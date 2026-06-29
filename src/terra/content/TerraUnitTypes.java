package terra.content;

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

public class TerraUnitTypes {
    public static UnitType
    //flying special units
    wick, wickC, dynamite, incident, catastrophe, sapEnergyMissile, inevitability, inevitabilityCore, eternityMissile, eternity,
    //drones & core units
    healDrone, basicAssemblyDrone, tau,
    //green erekir
    flow, greenMissile, threshold, turn, movement, consequence,
    //from New Horizon
    endSpawn, endGuard, end,
    //missiles
    flightLeadMissile, flightTitaniumMissile, flightMetaglassMissile, 
    aircraftThoriumMissile, aircraftThermoxiteMissile, aircraftFissileMissile, 
    //other
    latumFAKE, myDoom;

    public static void load() {
        wick = new UnitType("wick"){{
            flying = true;
            aiController = FlyingSuicideAI::new;
            speed = 3.1f;
            drag = 0.04f;
            accel = 0.08f;
            hitSize = 8f;
            health = 95;
            engineSize = 2.25f;
            engineOffset = 5.25f;
            range = 40f;
            itemCapacity = 10;
            //ammoType = new ItemAmmoType(TerraItems.carbon);
            createWreck = false;
            constructor = UnitEntity::create;
            healColor = Pal.suppress;
            outlineColor = Pal.darkerMetal;

            weapons.add(new Weapon(){{
                shootOnDeath = true;
                targetUnderBlocks = false;
                reload = 24f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosionCrawler;
                shootSoundVolume = 0.4f;
                x = shootY = 0f;
                mirror = false;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;

                    rangeOverride = 25f;
                    hitEffect = Fx.pulverize;
                    speed = 0f;
                    splashDamageRadius = 44f;
                    instantDisappear = true;
                    splashDamage = 70f;
                    buildingDamageMultiplier = 0.68f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};
        wickC = new UnitType("wick-c"){{
            playerControllable = false;
            logicControllable = false;
            flying = true;
            aiController = FlyingSuicideAI::new;
            speed = 3.1f;
            drag = 0.04f;
            accel = 0.08f;
            hitSize = 8f;
            health = 95;
            engineSize = 2.25f;
            engineOffset = 5.25f;
            range = 40f;
            itemCapacity = 0;
            createWreck = false;
            useUnitCap = false;
            hidden = true;
            constructor = TimedKillUnit::create;
            healColor = Pal.suppress;
            outlineColor = Pal.darkerMetal;
            lifetime = 2400f;

            weapons.add(new Weapon(){{
                shootOnDeath = true;
                targetUnderBlocks = false;
                reload = 24f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosionCrawler;
                shootSoundVolume = 0.4f;
                x = shootY = 0f;
                mirror = false;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;

                    rangeOverride = 25f;
                    hitEffect = Fx.pulverize;
                    speed = 0f;
                    splashDamageRadius = 44f;
                    instantDisappear = true;
                    splashDamage = 70f;
                    buildingDamageMultiplier = 0.68f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};
        
        dynamite = new UnitType("dynamite"){{
            flying = true;
            speed = 2.1f;
            drag = 0.04f;
            accel = 0.058f;
            hitSize = 12f;
            health = 420;
            engineSize = 2.75f;
            engineOffset = 7.7f;
            range = 90f;
            itemCapacity = 15;
            lowAltitude = true;
            constructor = UnitEntity::create;
            healColor = Pal.suppress;
            outlineColor = Pal.darkerMetal;

            weapons.add(
            new Weapon(){{
                shootOnDeath = true;
                controllable = false;
                reload = 60f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosionCrawler;
                shootSoundVolume = 0.7f;
                x = shootY = 0f;
                mirror = false;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;
                    hitEffect = Fx.pulverize;
                    speed = 0f;
                    splashDamageRadius = 60f;
                    instantDisappear = true;
                    splashDamage = 187f;
                    buildingDamageMultiplier = 0.68f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }},
            new Weapon("terra-dynamite-weapon"){{
                x = 13.5f / 4f;
                y = -5.5f / 4f;
                reload = 13f;
                rotate = true;
                rotateSpeed = 5f;
                shootSound = Sounds.shootSap;
                bullet = new SapBulletType(){{
                    sapStrength = 0.5f;
                    length = 95f;
                    damage = 19f;
                    shootEffect = Fx.shootSmall;
                    hitColor = color = Color.valueOf("bf92f9");
                    despawnEffect = Fx.none;
                    width = 0.4f;
                    lifetime = 25f;
                    knockback = -0.4f;
                }};
            }});
        }};
                
        incident = new UnitType("incident"){{
            flying = true;
            speed = 1.6f;
            drag = 0.04f;
            accel = 0.042f;
            hitSize = 19f;
            health = 1150;
            armor = 6;
            targetFlags = new BlockFlag[]{BlockFlag.generator, BlockFlag.battery, BlockFlag.core, null};
            engineSize = 3.5f;
            engineOffset = 7f;
            range = 70f;
            itemCapacity = 40;
            lowAltitude = true;
            constructor = UnitEntity::create;
            immunities = ObjectSet.with(StatusEffects.sapped);
            healColor = Pal.suppress;
            outlineColor = Pal.darkerMetal;
            abilities.add(new SpawnDeathAbility(wick, 3, 11f));

            weapons.add(
            new Weapon(){{
                shootOnDeath = true;
                controllable = false;
                reload = 60f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosionCrawler;
                shootSoundVolume = 0.7f;
                x = shootY = 0f;
                mirror = false;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;
                    hitEffect = Fx.pulverize;
                    speed = 0f;
                    splashDamageRadius = 60f;
                    instantDisappear = true;
                    splashDamage = 250f;
                    buildingDamageMultiplier = 0.68f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }},
            new Weapon("terra-incident-mount"){{
                x = 24.5f / 4f;
                y = -1.5f / 4f;
                shootY = 4f;
                shootSound = Sounds.shootAvert;
                reload = 28f;
                rotate = true;
                shoot = new ShootSpread() {{
                    shots = 3;
                    spread = 10f;
                }};
                bullet = new SapBulletType(){{
                    sapStrength = 0.4f;
                    length = 75f;
                    damage = 22f;
                    shootEffect = Fx.shootSmall;
                    hitColor = color = Color.valueOf("bf92f9");
                    despawnEffect = Fx.none;
                    width = 0.5f;
                    lifetime = 25f;
                    knockback = -0.4f;
                }};
            }},
            new Weapon("terra-incident-weapon"){{
                x = 31.5f / 4f;
                y = 13.5f / 4f;
                shootY = 1f;
                shootSound = Sounds.shootAfflict;
                reload = 135f;
                recoil = 0.8f;
                rotate = true;
                rotateSpeed = 1.2f;
                rotationLimit = 35f;
                layerOffset = -0.001f;
                parts.addAll(
                    new RegionPart("-part") {{
                        mirror = true;
                        progress = PartProgress.recoil;
                        moveX = 0.75f;
                        outline = false;
                        under = true;
                    }},
                    new RegionPart("-part-outline") {{
                        mirror = true;
                        progress = PartProgress.recoil;
                        moveX = 0.75f;
                        outline = false;
                        under = true;
                    }}
                );
                bullet = new BasicBulletType(){{
                    rangeOverride = 140f;
                    speed = 3f;
                    damage = 115f;
                    splashDamage = 43f;
                    splashDamageRadius = 2.6f * 8;
                    drag = 0.02f;
                    width = 4f;
                    height = 16f;
                    shrinkX = -1f;
                    shrinkY = 0.5f;
                    shrinkInterp = Interp.circleIn;
                    sprite = "circle-bullet";
                    backColor = Pal.sapBulletBack;
                    frontColor = lightningColor = Pal.sapBullet;
                    lifetime = 115f;
                    despawnSound = hitSound = Sounds.explosionAfflict;
                    status = StatusEffects.sapped;
                    statusDuration = 300f;
                    buildingDamageMultiplier = 0.5f;
                    hitEffect = despawnEffect = new WaveEffect(){{
                        colorFrom = colorTo = Pal.sapBulletBack;
                        sizeTo = 11f;
                        strokeFrom = 6f;
                        lifetime = 13f;
                    }};
                    bulletInterval = 8f;
                    intervalBullets = 1;
                    fragBullets = 7;
                    intervalBullet = fragBullet = new LightningBulletType(){{
                        damage = 12f;
                        collidesAir = false;
                        lightningLength = 5;
                        lightningColor = Pal.sapBulletBack;
                        hitSoundVolume = 0.3f;
                        hitSoundPitch = 0.7f;
                        lightningLengthRand = 3;
                        buildingDamageMultiplier = 0.15f;
                    }};
                }};
            }});
        }};
        
        sapEnergyMissile = new MissileUnitType("sap-energy-missile"){{
            speed = 4.6f;
            rotateSpeed = 8f;
            hitSize = 6f;
            health = 68f;
            maxRange = 4f;
            outlines = false;
            engineOffset = 1f;
            engineSize = 4f;
            engineLayer = 109.9f;
            engineColor = trailColor = Pal.sapBullet;
            trailLength = 12;
            flyingLayer = 110f;
            hidden = false;
            targetable = false;
            drawSoftShadow = false;
            constructor = TimedKillUnit::create;
            lifetime = 70f;
            shadowElevation = 0f;

            weapons.add(new Weapon() {{
                shootSound = Sounds.none;
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                shootOnDeath = true;
                shootOnDeathEffect = Fx.massiveExplosion;
                bullet = new ExplosionBulletType(78f, 33f){{
                    shootEffect = new WrapEffect(Fx.shootQuellPulse, Pal.suppress);
                    collidesAir = true;
                    status = TerraStatusEffects.energyOverload;
                    statusDuration = 80f;
                    buildingDamageMultiplier = 0.5f;
                }};
            }});
        }};

        catastrophe = new UnitType("catastrophe"){{
            flying = true;
            speed = 1.2f;
            rotateSpeed = 2.1f;
            drag = 0.04f;
            accel = 0.042f;
            hitSize = 31f;
            softShadowScl = 0.8f;
            health = 8600;
            armor = 10;
            targetFlags = new BlockFlag[]{BlockFlag.reactor, BlockFlag.core, null};
            engineSize = 6f;
            engineOffset = 14f;
            //range = 120f;
            itemCapacity = 60;
            lowAltitude = true;
            constructor = UnitEntity::create;
            immunities = ObjectSet.with(StatusEffects.sapped);
            healColor = Pal.suppress;
            outlineColor = Pal.darkerMetal;
            abilities.add(new UnitSpawnAbility(wickC, 480f, 19.125f, -8.375f), new UnitSpawnAbility(wickC, 480f, -19.125f, -8.375f), 
            new ShieldArcAbility(){{
                region = "terra-catastrophe-shield";
                radius = 32f;
                angle = 146f;
                regen = 1.4f;
                cooldown = 60f * 4f;
                max = 4000f;
                width = 8f;
                whenShooting = false;
                chanceDeflect = 0.25f;
                missileUnitMultiplier = 0.5f;
            }});

            BulletType sapper = new LaserBulletType(){{
                damage = 28f;
                recoil = 0f;
                sideAngle = 90f;
                sideWidth = 0.8f;
                sideLength = 30f;
                status = StatusEffects.sapped;
                statusDuration = 90f;
                length = 130f;
                colors = new Color[]{Pal.sapBullet.cpy().a(0.4f), Pal.sapBullet, Color.white};
                knockback = -0.1f;
            }};

            weapons.add(
            new Weapon("terra-dynamite-weapon"){{
                x = 40.5f / 4f;
                y = -34.5f / 4f;
                shootSound = Sounds.shootScepterSecondary;
                reload = 16f;
                rotate = true;
                inaccuracy = 4f;
                bullet = sapper;
            }},
            new Weapon("terra-dynamite-weapon"){{
                x = 49.5f / 4f;
                y = -3.5f / 4f;
                shootSound = Sounds.shootScepterSecondary;
                reload = 18f;
                rotate = true;
                inaccuracy = 4f;
                bullet = sapper;
            }},
            new Weapon("terra-sap-launcher"){{
                x = 0f;
                y = -12.5f / 4f;
                shootY = 5f;
                shootSound = TerraSounds.shootLaunch;
                reload = 190f;
                rotate = true;
                rotateSpeed = 1.2f;
                mirror = false;
                shoot = new ShootAlternate() {{
                    shots = 5;
                    shotDelay = 9f;
                    barrels = 3;
                    spread = 4f;
                }};
                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.suppress;
                    shake = 1f;
                    speed = 0.001f;
                    keepVelocity = false;

                    spawnUnit = sapEnergyMissile;
                }};
            }});
        }};

        inevitabilityCore = new UnitType("inevitability-core"){{
            playerControllable = false;
            logicControllable = false;
            flying = true;
            speed = 4f;
            drag = 0.04f;
            accel = 0.08f;
            hitSize = 8f;
            health = 4000f;
            targetFlags = new BlockFlag[]{BlockFlag.core, BlockFlag.turret, null};
            engineSize = 1f;
            engineOffset = 0f;
            range = 80f;
            itemCapacity = 0;
            //useUnitCap = false;
            hidden = false;
            constructor = TimedKillUnit::create;
            immunities = ObjectSet.with(StatusEffects.sapped);
            healColor = Pal.suppress;
            outlineColor = Pal.darkerMetal;
            lifetime = 7500f;
            setEnginesMirror(
                new UnitEngine(4f, 4f, 2f, 45f),
                new UnitEngine(4f, -4f, 2f, 315f)
            );

            weapons.add(new Weapon(){{
                mirror = false;
                x = 0f;
                y = 0f;
                shootY = 0f;
                shootSound = Sounds.shootAvert;
                shootSoundVolume = 0.3f;
                shootCone = 361f;
                inaccuracy = 360f;
                reload = 4f;
                bullet = new BasicBulletType(){{
                    speed = 5.6f;
                    lifetime = 30f;
                    damage = 42f;
                    homingPower = 0.2f;
                    width = 4.4f;
                    height = 7f;
                    shrinkY = 0.2f;
                    hitColor = backColor = trailColor = Pal.sapBulletBack;
                    frontColor = Pal.sapBullet;
                    trailWidth = 1f;
                    trailLength = 4;
                    despawnEffect = hitEffect = Fx.none;
                    fragRandomSpread = 20f;
                    fragBullets = 1;
                    fragBullet = new SapBulletType(){{
                        sapStrength = 0.68f;
                        width = 0.7f;
                        length = 52f;
                        damage = 31f;
                        shootEffect = smokeEffect = despawnEffect = Fx.none;
                        color = hitColor = Pal.sapBulletBack;
                        lifetime = 18f;
                        knockback = -0.3f;
                    }};
                }};
            }});
        }};

        inevitability = new UnitType("inevitability"){{
            flying = true;
            speed = 0.7f;
            rotateSpeed = 1.3f;
            drag = 0.04f;
            accel = 0.03f;
            hitSize = 36f;
            softShadowScl = 0.7f;
            health = 24000;
            armor = 21;
            targetFlags = new BlockFlag[]{BlockFlag.core, BlockFlag.turret, null};
            engineSize = 5.75f;
            engineOffset = 14f;
            setEnginesMirror(
                new UnitEngine(17f, -9f, 5f, -45f)
            );
            //range = 120f;
            itemCapacity = 90;
            lowAltitude = true;
            constructor = UnitEntity::create;
            immunities = ObjectSet.with(StatusEffects.sapped, TerraStatusEffects.impactStun);
            healColor = Pal.suppress;
            outlineColor = Pal.darkerMetal;
            abilities.addAll(
                new ShieldRegenFieldAbility(1500f, 6000f, 1200f, 120f), 
                new SuppressionFieldAbility(){{
                    range = 180f;
                    maxDelay = 60f;
                    layer = 89f;
                    reload = 600f;
                }}, 
                new SpawnDeathAbility(inevitabilityCore, 1, 11f));
            weapons.add(
            new Weapon("terra-inevitability-mount"){{
                x = 44.5f / 4f;
                y = 24.5f / 4f;
                shootSound = Sounds.shootAvert;
                reload = 24f;
                rotate = true;
                inaccuracy = 6f;
                shoot = new ShootPattern() {{
                    shots = 4;
                    shotDelay = 2f;
                }};
                bullet = new BasicBulletType(){{
                    speed = 5.6f;
                    lifetime = 30f;
                    damage = 42f;
                    width = 4.4f;
                    height = 7f;
                    shrinkY = 0.2f;
                    hitColor = backColor = trailColor = Pal.sapBulletBack;
                    frontColor = Pal.sapBullet;
                    trailWidth = 1f;
                    trailLength = 4;
                    despawnEffect = hitEffect = Fx.none;
                    fragRandomSpread = 20f;
                    fragBullets = 1;
                    fragBullet = new SapBulletType(){{
                        sapStrength = 0.34f;
                        width = 0.7f;
                        length = 52f;
                        damage = 31f;
                        shootEffect = smokeEffect = despawnEffect = Fx.none;
                        color = hitColor = Pal.sapBulletBack;
                        lifetime = 18f;
                        knockback = -0.3f;
                    }};
                }};
            }},
            new Weapon("terra-small-sap-launcher"){{
                x = -62.5f / 4f;
                y = -15.5f / 4f;
                shootY = 2.5f;
                shootSound = TerraSounds.shootLaunch;
                reload = 87f;
                rotate = true;
                rotateSpeed = 3.2f;
                shoot = new ShootAlternate() {{
                    shots = 3;
                    shotDelay = 9f;
                    barrels = 2;
                    spread = 3.5f;
                }};
                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    hitColor = Pal.suppress;
                    shake = 0.4f;
                    speed = 0.001f;
                    keepVelocity = false;

                    spawnUnit = sapEnergyMissile;
                }};
            }},
            new Weapon("terra-railgun"){{
                x = 0f;
                y = -7.5f / 4f;
                shootY = 5f;
                layerOffset = 0.002f;
                shootSound = Sounds.shootSmite;
                chargeSound = TerraSounds.railGunCharge;
                soundPitchMin = 0.93f;
                reload = 246f;
                recoilTime = 173f;
                minWarmup = 0.8f;
                rotate = true;
                rotateSpeed = 0.6f;
                shake = 3f;
                mirror = false;
                shoot = new ShootPattern() {{
                    firstShotDelay = 148f;
                }};
                parts.addAll(
                    new RegionPart("-antenna-charge-line-l") {{
                        mirror = true;
                        progress = PartProgress.charge;
                        y = 30f;
                        color = Color.valueOf("ffd37f00");
                        colorTo = Color.valueOf("ffd37fff");
                        under = true;
                        outline = false;
                        moves.addAll(
                            new PartMove(){{
                                progress = PartProgress.warmup;
                                x = -1;
                            }},
                            new PartMove(){{
                                progress = PartProgress.recoil;
                                y = -7;
                            }}
                        );
                    }},
                    new RegionPart("-antenna-l") {{
                        mirror = true;
                        progress = PartProgress.warmup;
                        y = 5.75f;
                        moveX = -1f;
                        under = true;
                        moves.addAll(
                            new PartMove(){{
                                progress = PartProgress.recoil;
                                y = -7;
                            }}
                        );
                    }},
                    new RegionPart("-arrow") {{
                        mirror = false;
                        progress = PartProgress.charge;
                        y = 17.5f;
                        color = Color.valueOf("ffd37f00");
                        colorTo = Color.valueOf("ffd37fff");
                        outline = false;
                    }},
                    new RegionPart("-arrow") {{
                        mirror = false;
                        progress = PartProgress.charge.delay(0.333f);
                        y = 27.5f;
                        color = Color.valueOf("ffd37f00");
                        colorTo = Color.valueOf("ffd37fff");
                        outline = false;
                    }},
                    new RegionPart("-arrow") {{
                        mirror = false;
                        progress = PartProgress.charge.delay(0.666f);
                        y = 37.5f;
                        color = Color.valueOf("ffd37f00");
                        colorTo = Color.valueOf("ffd37fff");
                        outline = false;
                    }}
                );
                bullet = new BasicBulletType(){{
                    sprite = "missile-large";
                    hitColor = trailColor = backColor = Color.valueOf("ffd37f");
                    speed = 8.4f;
                    lifetime = 53f;
                    damage = 840f;
                    buildingDamageMultiplier = 0.85f;
                    knockback = 9f;
                    impact = true;
                    targetMissiles = false;
                    reflectable = false;
                    pierceArmor = true;
                    despawnShake = 8f;
                    hitShake = 2.3f;
                    hitSize = 14f;
                    width = 18f;
                    height = 22f;
                    trailWidth = 5f;
                    trailLength = 26;
                    shrinkY = 0.2f;
                    pierce = true;
                    pierceCap = 5;
                    pierceBuilding = true;
                    status = TerraStatusEffects.impactStun;
                    statusDuration = 40f;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitSound = despawnSound = Sounds.explosionTitan;
                    hitEffect = despawnEffect = new ParticleEffect(){{
                        lifetime = 128f;
                        layer = 122f;
                        particles = 17;
                        cone = 360f;
                        length = 90f;
                        interp = Interp.circleOut;
                        colorFrom = Color.valueOf("ffd37f");
                        colorTo = Color.valueOf("ffd37f00");
                        sizeFrom = 42f;
                        region = "circle-shadow";
                    }};
                }};
            }});
        }};

        eternityMissile = new MissileUnitType("eternity-missile"){{
            targetAir = true;
            speed = 7.6f;
            rotateSpeed = 4;
            maxRange = 5f;
            health = 70;
            homingDelay = 3f;
            lowAltitude = true;
            outlineColor = Pal.darkerMetal;
            engineSize = 3f;
            engineColor = trailColor = Pal.sapBulletBack;
            engineLayer = Layer.effect;
            deathExplosionEffect = Fx.none;
            loopSoundVolume = 0.1f;
            hidden = false;
            softShadowScl = 0.8f;
            immunities = ObjectSet.with(
                StatusEffects.sapped, StatusEffects.electrified, StatusEffects.shocked, StatusEffects.unmoving, 
                TerraStatusEffects.impactStun, TerraStatusEffects.energyOverload, TerraStatusEffects.singularEvaporation, TerraStatusEffects.crystalization
            );

            parts.add(new ShapePart(){{
                layer = Layer.effect;
                circle = true;
                y = -0.25f;
                radius = 1.5f;
                color = Pal.suppress;
                colorTo = Color.white;
                progress = PartProgress.life.curve(Interp.pow5In);
            }});

            weapons.add(new Weapon(){{
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                shootOnDeath = true;
                bullet = new ExplosionBulletType(100f, 45f){{
                    collidesAir = true;
                    suppressionRange = 140f;
                    shootEffect = new ExplosionEffect(){{
                        lifetime = 50f;
                        waveStroke = 5f;
                        waveLife = 8f;
                        waveColor = Color.white;
                        sparkColor = smokeColor = Pal.suppress;
                        waveRad = 40f;
                        smokeSize = 4f;
                        smokes = 7;
                        smokeSizeBase = 0f;
                        sparks = 10;
                        sparkRad = 40f;
                        sparkLen = 6f;
                        sparkStroke = 2f;
                    }};
                }};
            }});
        }};
        
        eternity = new UnitType("eternity"){{
            flying = true;
            speed = 0.46f;
            rotateSpeed = 0.8f;
            drag = 0.04f;
            accel = 0.03f;
            hitSize = 100f;
            softShadowScl = 1f;
            health = 88000;
            armor = 56;
            targetFlags = new BlockFlag[]{BlockFlag.reactor, BlockFlag.core, BlockFlag.turret, null};
            engineSize = 0f;
            engineOffset = 0f;
            outlineRadius = 6;
            crashDamageMultiplier = 10;
            targetPriority = 4f;
            fallSpeed = 0.01f;
            faceTarget = false;
            setEnginesMirror(
                new UnitEngine(168f / 4f, 0.25f, 13.5f, 315f),
                new UnitEngine(100f / 4f, -55f / 4f, 11f, 315f)
            );
            range = 210f;
            itemCapacity = 200;
            lowAltitude = true;
            constructor = UnitEntity::create;
            immunities = ObjectSet.with(
                StatusEffects.sapped, StatusEffects.electrified, StatusEffects.shocked, StatusEffects.unmoving, 
                TerraStatusEffects.impactStun, TerraStatusEffects.energyOverload, TerraStatusEffects.singularEvaporation, TerraStatusEffects.crystalization
            );
            healColor = Pal.suppress;
            outlineColor = Pal.darkerMetal;
            abilities.addAll(
                new SuppressionFieldAbility(){{
                    range = 450f;
                    maxDelay = 60f;
                    reload = 1200f;
                    y = 39.25f;
                    orbRadius = 13f;
                    orbMidScl = 0.4f;
                    orbSinScl = 4f;
                    particleSize = 6;
                }},
                new RegenAbility() {{
                    amount = 3.2f;
                }}
            );

            Weapon sapLauncher = new Weapon("terra-sap-launcher-2"){{
                shootY = 4.5f;
                rotate = true;
                rotateSpeed = 2.5f;
                shootSound = Sounds.shootSalvo;
                ejectEffect = Fx.none;
                reload = 92f;
                shake = 2f;
                
                shoot = new ShootAlternate(){{
                    shots = 12;
                    shotDelay = 1.3f;
                    spread = 4f;
                    barrels = 3;
                }};

                bullet = new MissileBulletType(){{
                    homingPower = 0.2f;
                    speed = 8.2f;
                    damage = 33;
                    width = 7f;
                    height = 15f;
                    shrinkX = shrinkY = 0f;
                    drag = -0.003f;
                    keepVelocity = false;
                    splashDamageRadius = 35f;
                    splashDamage = 42f;
                    lifetime = 42f;
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = 2;
                    trailColor = backColor = hitColor = Pal.suppress;
                    frontColor = Color.white;
                    hitEffect = despawnEffect = Fx.blastExplosion;
                }};
            }};
            Weapon sapPlasma = new Weapon("terra-eternity-plasma-gun"){{
                shootY = 4f;
                rotate = true;
                rotateSpeed = 3.3f;
                shootSound = TerraSounds.shootHeavy;
                ejectEffect = Fx.none;
                reload = 188f;
                cooldownTime = 90f;
                shake = 4f;
                parentizeEffects = true;

                bullet = new BasicBulletType(){{
                    sprite = "terra-plasma";
                    speed = 19.2f;
                    velocityScaleRandMin = 0.4f;
                    damage = 226;
                    buildingDamageMultiplier = 0.9f;
                    shieldDamageMultiplier = 1.8f;
                    width = 11f;
                    height = 11f;
                    shrinkX = shrinkY = -2f;
                    shrinkInterp = Interp.smooth2;
                    hitSound = Sounds.explosionTitan;
                    despawnHit = true;
                    hittable = false;
                    reflectable = false;
                    drag = 0.05f;
                    keepVelocity = false;
                    splashDamageRadius = 50f;
                    splashDamage = 99f;
                    lifetime = 142f;
                    lightRadius = 88f;
                    lightOpacity = 0.5f;
                    trailColor = backColor = hitColor = lightColor = Pal.suppress;
                    frontColor = Color.white;
                    hitEffect = despawnEffect = new MultiEffect(TerraFx.circleFadeBig, new WrapEffect(Fx.shootQuellPulse, Pal.suppress));
                    shootEffect = new Effect(26f, e -> {
                        color(Pal.suppress);
                        Drawf.tri(e.x, e.y, 9f * e.fout(), 80f - (20f * e.fin()), e.rotation);
                        for (int i = 0; i < 2; i++) {
                            Drawf.tri(e.x, e.y, 3f * e.fout(), 25f, e.rotation + (5f + (e.fin(Interp.circleOut) * 30f)) * Mathf.signs[i]);
                        }
                    });
                    status = TerraStatusEffects.energyOverload;
                    statusDuration = 90f;
                    intervalBullets = 3;
                    bulletInterval = 7.5f;
                    intervalDelay = 30f;
                    fragBullets = 12;
                    intervalBullet = fragBullet = new LaserBoltBulletType(5.2f, 26){{
                        lifetime = 45f;
                        rotateSpeed = 6f;
                        backColor = lightColor = trailColor = Pal.suppress;
                        frontColor = Color.white;
                        hitEffect = despawnEffect = smokeEffect = trailEffect = new Effect(8, e -> {
                            color(Color.white, Pal.suppress, e.fin());
                            stroke(0.5f + e.fout());
                            Lines.circle(e.x, e.y, e.fin() * 5f);
                    
                            Drawf.light(e.x, e.y, 23f, Pal.suppress, e.fout() * 0.7f);
                        });
                        despawnSound = Sounds.shootElude;
                        trailInterval = lifetime / 4 + 0.01f;
                        status = TerraStatusEffects.energyOverload;
                        statusDuration = 20f;
                    }};
                }};
            }};
            Weapon sapLasers = new Weapon("terra-dual-mount-purple"){{
                shootY = 10f;
                rotate = true;
                mirror = false;
                layerOffset = 0.001f;
                rotateSpeed = 2.25f;
                shootSound = TerraSounds.shootFastLaser;
                ejectEffect = Fx.none;
                reload = 200f;
                recoil = 0.4f;
                recoilTime = 90f;
                shake = 1.2f;

                shoot = new ShootMulti(
                    new ShootAlternate() {{
                        spread = 10f;
                        shots = 2;
                        barrels = 2;
                    }}, new ShootPattern(), 
                    new ShootPattern() {{
                        shots = 8;
                        shotDelay = 3f;
                        firstShotDelay = 20f;
                    }}, new ShootPattern() {{
                        shots = 3;
                        shotDelay = 20f;
                        firstShotDelay = 40f + 8 * 3;
                    }}
                );
                parts.add(
                    new RegionPart("-barrels") {{
                        mirror = false;
                        recoilIndex = 1;
                        progress = PartProgress.recoil;
                        moveY = -2f;
                    }}
                );

                bullet = new RailBulletType() {{
                    length = 333f;
                    damage = 168f;
                    buildingDamageMultiplier = 1.4f;
                    shieldDamageMultiplier = 1.2f;
                    armorMultiplier = 0.2f;
                    hitColor = Pal.suppress;
                    shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSpark);
                    smokeEffect = TerraFx.shootRing2;
                    hitEffect = Fx.hitBulletColor;
                    pierceDamageFactor = 0.85f;
                    pierce = true;
                    status = StatusEffects.sapped;
                    statusDuration = 75f;
                    endEffect = new Effect(14f, e -> {
                        color(e.color);
                        Drawf.tri(e.x, e.y, e.fout() * 3f, 5f, e.rotation);
                        color(Color.white);
                        Drawf.tri(e.x, e.y, e.fout() * 3f, 5f, e.rotation);
                    });
                    lineEffect = new Effect(20f, e -> {
                        if (!(e.data instanceof Vec2 v)) return;

                        color(e.color);
                        stroke(e.fout() * 1.1f + 0.6f);

                        Fx.rand.setSeed(e.id);
                        for (int i = 0; i < 7; i++) {
                            Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                            Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                        }

                        e.scaled(14f, b -> {
                            stroke(b.fout() * 3f);
                            color(e.color);
                            Lines.line(e.x, e.y, v.x, v.y);
                        });
                        e.scaled(14f, b -> {
                            stroke(b.fout() * 1.5f);
                            color(Color.white);
                            Lines.line(e.x, e.y, v.x, v.y);
                        });
                    });
                }};
            }};

            weapons.add(copyAndMove(sapPlasma, 136f / 4f, -17f / 4f));
            weapons.add(copyAndMoveAnd(sapPlasma, 218f / 4f, 26f / 4f, w -> {w.reload = 222f;}));
            weapons.add(copyAndMove(sapLauncher, 67f / 4f, -8f / 4f));
            weapons.add(copyAndMove(sapLasers, 0f, -70f / 4f));
            weapons.add(
            new Weapon("terra-eternity-mount"){{
                x = 128f / 4f;
                y = 94f / 4f;
                shootY = 8f;
                rotate = true;
                rotateSpeed = 1.8f;
                shootSound = TerraSounds.acceleratinglaserloop;
                shootSoundVolume = activeSoundVolume = 0.6f;
                reload = 126f;
                continuous = true;
                parentizeEffects = true;
                shake = 0.6f;
                alternate = false;
                
                bullet = new AcceleratingLaserBulletType(90f){{
                    maxLength = 230f;
                    maxRange = 230f;
                    oscOffset = 0.3f;
                    lifetime = 200;
                    width = 20f;
                    collisionWidth = 10f;
                    status = TerraStatusEffects.energyOverload;
                    statusDuration = 120f;
                    colors = new Color[]{Pal.suppress.cpy().a(0.3f), Pal.suppress, Color.white};
                    pierceCap = 3;
                    pierceBuilding = true;
                    hitColor = Pal.suppress;
                    shootEffect = hitEffect = new Effect(27f, e ->
                    Angles.randLenVectors(e.id, 8, 90f * e.fin(), e.rotation, 80f, (x, y) -> {
                        float angle = Mathf.angle(x, y);
                        color(Pal.suppress, e.fin());
                        Lines.stroke(1.5f);
                        Lines.lineAngleCenter(e.x + x, e.y + y, angle, e.fslope() * 13f);
                    }));
                }};
            }},
            new Weapon("terra-rocket-launcher") {{
                reload = 130f;
                mirror = true;
                rotate = false;
                x = 204f / 4f;
                y = 104f / 4f;
                shootSound = TerraSounds.shootLaunch;
                shootSoundVolume = 0.4f;
                baseRotation = -45;
                shootY = 2;
                shoot = new ShootMulti(new ShootAlternate() {{
                    spread = 7;
                    shots = 3;
                    barrels = 3;
                }}, new ShootPattern() {{
                    shots = 5;
                    shotDelay = 3.5f;
                }});
                velocityRnd = 0.2f;
                shootCone = 165;
                inaccuracy = 6;
                layerOffset = -0.001f;
                recoil = 1;
                shake = 0.5f;
                bullet = new BulletType(){{
                    shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.suppress;
                    shake = 1f;
                    speed = 0.001f;
                    keepVelocity = false;
                    collidesAir = true;

                    spawnUnit = eternityMissile;
                }};
            }},
            new Weapon("terra-eternity-singularity") {{
                reload = 1124f;
                mirror = false;
                rotate = true;
                x = 0f;
                y = 39.25f;
                shootSound = TerraSounds.shootBlackhole;
                shootY = 0;
                recoil = 0;
                shake = 15f;
                parts.add(new BlackHolePart(){{
                    color = Pal.suppress;
                    size = 0f;
                    sizeTo = 10f;
                    edge = 0f;
                    edgeTo = 14f * 1.2f;
                    progress = growProgress = PartProgress.reload.inv().delay(0.2f);
                }});
                bullet = new BlackHoleBulletType(1.1f, 168f){{
                    lifetime = 300f;
                    shieldDamageMultiplier = 4f;
                    armorMultiplier = 0f;
                    color = Pal.suppress;
                    damageRadius = 14f * 1.2f;
                    growTime = 0f;
                    shrinkTime = 20f;
                    status = TerraStatusEffects.singularEvaporation; //somehow didn't work...
                    statusDuration = 180f;
                    keepVelocity = false;
                    intervalBullets = 1;
                    bulletInterval = 1f;
                    intervalBullet = new BulletType(){{
                        speed = 0f;
                        damage = 0f;
                        lifetime = 0f;
                        instantDisappear = true;
                        shootEffect = despawnEffect = hitEffect = smokeEffect = Fx.none;
                        splashDamage = 0.001f;
                        splashDamageRadius = 14f * 1.2f;
                        status = TerraStatusEffects.singularEvaporation;
                        statusDuration = 180f;
                    }};
                }};
            }},
            new Weapon("terra-last") {{
                reload = 60f;
                mirror = false;
                shootOnDeath = true;
                controllable = false;
                display = false;
                shootCone = 360f;
                x = 0f;
                y = 0f;
                shootY = 0;
                recoil = 0;
                bullet = new BlackHoleBulletType(0f, 142f){{
                    lifetime = 500f;
                    shieldDamageMultiplier = 4f;
                    armorMultiplier = 0f;
                    color = Pal.suppress;
                    damageRadius = 30f;
                    suctionRadius = 300f;
                    growTime = 40f;
                    shrinkTime = 60f;
                    status = TerraStatusEffects.singularEvaporation;
                    loopSoundVolume = 5f;
                    statusDuration = 240f;
                    keepVelocity = false;
                    intervalBullets = 1;
                    bulletInterval = 1f;
                    intervalBullet = new BulletType(){{
                        speed = 0f;
                        damage = 0f;
                        lifetime = 0f;
                        instantDisappear = true;
                        shootEffect = despawnEffect = hitEffect = smokeEffect = Fx.none;
                        splashDamage = 0.001f;
                        splashDamageRadius = 30f;
                        status = TerraStatusEffects.singularEvaporation;
                        statusDuration = 240f;
                    }};
                }};
            }});
        }};

        healDrone = new UnitType("heal-drone"){{
            playerControllable = false;
            logicControllable = false;
            flying = true;
            hidden = true; //not done yet
            speed = 3.6f;
            drag = 0.02f;
            accel = 0.03f;
            hitSize = 8f;
            health = 600;
            engineSize = 1.75f;
            engineOffset = 5.5f;
            itemCapacity = 0;
            fallSpeed = 0.06f;
            constructor = UnitEntity::create;
            outlineColor = Pal.darkerMetal;

            weapons.add(new RepairBeamWeapon(){{
                mirror = false;
                shootY = x = y = 0f;
                beamWidth = 0.6f;
                repairSpeed = 0.7f;

                bullet = new BulletType(){{
                    maxRange = 7.5f * 8;
                }};
            }});
        }};
        basicAssemblyDrone = new UnitType("basic-assembly-drone"){{
            controller = u -> new AssemblerAI();

            flying = true;
            drag = 0.06f;
            accel = 0.11f;
            speed = 1.5f;
            health = 75;
            engineSize = 2f;
            engineOffset = 4.5f;
            payloadCapacity = 0f;
            targetable = false;
            bounded = false;

            outlineColor = Pal.darkerMetal;
            isEnemy = false;
            hidden = false;
            useUnitCap = false;
            logicControllable = false;
            playerControllable = false;
            allowedInPayloads = false;
            createWreck = false;
            envEnabled = Env.any;
            envDisabled = Env.none;
            constructor = BuildingTetherPayloadUnit::create;
        }};
        tau = new UnitType("tau"){{
            controller = u -> u.team.isAI() ? new BuilderAI(true, 400f) : new CommandAI();
            flying = true;
            targetBuildingsMobile = false;
            isEnemy = false;
            speed = 3.67f;
            rotateSpeed = 12f;
            drag = 0.035f;
            accel = 0.045f;
            mineSpeed = 7f;
            mineTier = 2;
            mineWalls = true;
            buildSpeed = 1f;
            buildBeamOffset = 22f / 4;
            hitSize = 15f;
            health = 260;
            engineSize = 0f;
            itemCapacity = 100;
            fogRadius = 0f;
            lowAltitude = false;
            researchCostMultiplier = 0f;
            
            constructor = UnitEntity::create;
            outlineColor = Pal.darkerMetal;
            faceTarget = false;
            setEnginesMirror(
                new UnitEngine(20.5f / 4f, 26f / 4, 1.6f, 90f),
                new UnitEngine(20.5f / 4f, -19f / 4f, 1.6f, 90f)
            );

            weapons.add(new Weapon("terra-weird-weapon"){{
                top = false;
                reload = 25f;
                x = 0f;
                y = 0f;
                shootY = 4f;
                layerOffset = -0.002f;
                rotate = true;
                mirror = false;
                rotateSpeed = 9f;
                recoil = 1f;
                shoot = new ShootSpread(){{
                    shots = 3;
                    shotDelay = 5f;
                    spread = 6f;
                }};
                shootSound = Sounds.shootAlpha;

                bullet = new LaserBoltBulletType(4.5f, 19){{
                    keepVelocity = false;
                    width = 1.5f;
                    height = 6.5f;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    trailWidth = 1.2f;
                    trailLength = 4;
                    shootEffect = Fx.shootSmallColor;
                    smokeEffect = Fx.hitLaserColor;
                    backColor = trailColor = Pal.yellowBoltFront;
                    hitColor = Pal.yellowBoltFront;
                    frontColor = Color.white;
                    lightColor = Pal.yellowBoltFront;

                    lifetime = 55f;
                    buildingDamageMultiplier = 0.01f;
                    homingPower = 0.02f;
                    armorMultiplier = 0.9f;
                    healPercent = 1.6f;
                    collidesTeam = true;
                }};
            }});
        }};

        latumFAKE = new NeoplasmUnitType("latum"){{
            health = 20000;
            armor = 12;
            hitSize = 48f;
            omniMovement = false;
            rotateSpeed = 1.7f;
            segments = 4;
            drawBody = false;
            hidden = true;
            crushDamage = 2f;
            aiController = HugAI::new;
            constructor = CrawlUnit::create;
            targetAir = false;

            segmentScl = 4f;
            segmentPhase = 5f;
            speed = 1f;

            abilities.add(new SpawnDeathAbility(UnitTypes.renale, 5, 11f));
        }};
        myDoom = new UnitType("my-doom"){{ //MyDoom.exe Imput command > execute Doomsday.js
            flying = true;
            drawCell = false;
            isEnemy = false;
            killable = hittable = targetable = physics = false;
            logicControllable = false;
            hidden = true;
            speed = 10f;
            rotateSpeed = 20f;
            drag = 1f;
            accel = 1f;
            mineSpeed = Float.POSITIVE_INFINITY;
            mineTier = 1000;
            mineWalls = true;
            mineHardnessScaling = false;
            buildSpeed = Float.POSITIVE_INFINITY;
            hitSize = buildBeamOffset = 12f;
            lightRadius = 12f * 3;
            health = armor = Float.POSITIVE_INFINITY;
            targetFlags = new BlockFlag[]{BlockFlag.core, null};
            engineSize = 3f;
            engineOffset = 0f;
            trailLength = 5;
            itemCapacity = 2147483647;
            fogRadius = Float.POSITIVE_INFINITY;
            lowAltitude = false;
            researchCostMultiplier = 0f;
            healColor = engineColor = trailColor = lightColor = Color.valueOf("f53036");
            deathExplosionEffect = new WrapEffect(TerraFx.circleFade, engineColor);
            envDisabled = Env.none;
            envEnabled = Env.any;
            
            constructor = UnitEntity::create;
            outlines = false;
            faceTarget = false;

            abilities.add(new AdaptedHealAbility(Float.POSITIVE_INFINITY, 30f, 30f * 8, healColor){{selfHealReloadTime = 0f;}});

            weapons.add(new Weapon(){{
                top = false;
                reload = 1f;
                x = 0f;
                y = 0f;
                shootY = 0f;
                rotate = true;
                rotateSpeed = Float.POSITIVE_INFINITY;
                mirror = false;
                recoil = 1f;
                inaccuracy = 30f;
                shoot = new ShootPattern(){{
                    shots = 3;
                    shotDelay = 0.2f;
                }};
                shootCone = inaccuracy + 5f;
                shootSound = Sounds.shootAlpha;

                bullet = new BasicBulletType(15f, Float.POSITIVE_INFINITY){{
                    sprite = "terra-star";
                    keepVelocity = false;
                    width = 16f;
                    height = 16f;
                    shrinkX = shrinkY = 0f;
                    trailWidth = 1.2f;
                    trailLength = 2;
                    shootEffect = smokeEffect = Fx.none;
                    hitEffect = despawnEffect = TerraFx.circleFadeSmall;
                    backColor = trailColor = hitColor = Color.valueOf("f53036");
                    frontColor = lightColor = Color.valueOf("ff786e");

                    lifetime = 50f;
                    armorMultiplier = 0f;
                    pierce = true;
                    homingPower = 0.014f;
                    status = StatusEffects.unmoving;
                    statusDuration = Float.POSITIVE_INFINITY;
                }};
            }},
            new RepairBeamWeapon(){{
                mirror = false;
                shootY = x = y = 0f;
                beamWidth = 1f;
                repairSpeed = Float.POSITIVE_INFINITY;
                targetBuildings = true;
                shootCone = 360f;
                healColor = laserColor = Color.valueOf("f53036");
                bullet = new BulletType(){{
                    maxRange = 30f * 8;
                }};
            }});
        }                     
            @Override
            public void init() {
                super.init();
        
                immunities = new ObjectSet<>();
                for (StatusEffect effect : Vars.content.statusEffects()) {
                    if (effect == null) continue;
                    immunities.add(effect); //yes ALL effects, including buffs
                }
            }
        };

        Color greenLight = Color.valueOf("92dd7e");

        flow = new ErekirUnitType("flow"){{
            speed = 1.3f;
            drag = 0.13f;
            hitSize = 8f;
            health = 800;
            armor = 5f;
            accel = 0.4f;
            rotateSpeed = 3.5f;
            faceTarget = false;

            trailLength = 16;
            waveTrailX = 2.25f;
            trailScl = 1.1f;

            moveSoundVolume = 0.3f;
            moveSound = Sounds.shipMove;

            constructor = UnitWaterMove::create;
            immunities = ObjectSet.with(
                StatusEffects.burning, StatusEffects.melting, StatusEffects.wet
            );

            weapons.add(new Weapon("terra-green-micro-mount"){{
                reload = 18f;
                x = 0f;
                shootY = 3f;
                y = -9f / 4;
                rotate = true;
                mirror = false;
                inaccuracy = 8f;
                bullet = new BasicBulletType(2.7f, 26){{
                    sprite = "terra-plasma";
                    velocityScaleRandMin = 0.7f;
                    width = height = 5f;
                    shrinkX = shrinkY = -0.3f;
                    despawnHit = true;
                    reflectable = false;
                    drag = 0.01f;
                    splashDamageRadius = 30f;
                    splashDamage = 30f;
                    lifetime = 48f;
                    lightOpacity = 0.5f;
                    trailWidth = 1.1f;
                    trailLength = 7;
                    trailColor = backColor = hitColor = lightColor = greenLight;
                    frontColor = Color.white;
                    hitEffect = despawnEffect = TerraFx.circleFadeSmall;
                }};
            }});
        }};
        greenMissile = new MissileUnitType("green-micro-missile"){{
            speed = 2.4f;
            maxRange = 4f;
            lifetime = 90f;
            hitSize = 3f;
            outlineColor = Pal.darkOutline;
            engineColor = trailColor = lightColor = greenLight;
            engineLayer = Layer.effect;
            engineSize = 1.5f;
            engineOffset = 3f;
            rotateSpeed = 5f;
            trailLength = 6;
            missileAccelTime = 20f;
            lowAltitude = true;
            loopSound = Sounds.loopMissileTrail;
            loopSoundVolume = 0.1f;
            //deathSound = Sounds.explosionMissile;
            targetAir = true;
            targetUnderBlocks = false;

            fogRadius = 3f;

            health = 30;
            armor = 1;
            hidden = false;

            weapons.add(new Weapon(){{
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                shootOnDeath = true;
                shake = 1.5f;
                bullet = new ExplosionBulletType(48f, 44f){{
                    hitColor = greenLight;
                    collidesAir = true;
                }};
            }});
        }};
        threshold = new ErekirUnitType("threshold"){{
            speed = 1.1f;
            drag = 0.15f;
            hitSize = 13f;
            health = 1500;
            armor = 8f;
            accel = 0.3f;
            rotateSpeed = 3f;
            faceTarget = false;

            trailLength = 20;
            waveTrailX = 11f / 4;
            trailScl = 1.5f;

            moveSoundVolume = 0.55f;
            moveSoundPitchMin = moveSoundPitchMax = 0.9f;
            moveSound = Sounds.shipMove;

            constructor = UnitWaterMove::create;
            immunities = ObjectSet.with(
                StatusEffects.burning, StatusEffects.melting, StatusEffects.wet
            );

            weapons.add(new Weapon("terra-missile-void"){{
                reload = 40f;
                x = 17f / 4;
                shootY = 0f;
                y = -1f;
                rotate = true;
                rotateSpeed = 5f;
                shootSound = Sounds.shootMissileLarge;
                parts.add(new RegionPart("-preview"){{
                    progress = PartProgress.reload;
                    colorTo = new Color(1f, 1f, 1f, 0f);
                    color = Color.white;
                    mixColorTo = Pal.accent;
                    mixColor = new Color(1f, 1f, 1f, 0f);
                    outline = false;
                    under = false;
                }});
                bullet = new BulletType(){{
                    speed = 0f;
                    keepVelocity = false;
                    smokeEffect = shootEffect = Fx.none;
                    spawnUnit = greenMissile;
                }};
            }});
        }};
        turn = new ErekirUnitType("turn"){{
            speed = 0.75f;
            drag = 0.17f;
            hitSize = 25f;
            health = 4300;
            armor = 14f;
            accel = 0.2f;
            rotateSpeed = 2f;
            faceTarget = false;

            trailLength = 25;
            waveTrailX = 20f / 4;
            waveTrailY = -5f;
            trailScl = 1.7f;

            moveSoundVolume = 0.7f;
            moveSoundPitchMin = moveSoundPitchMax = 0.75f;
            moveSound = Sounds.shipMove;

            constructor = UnitWaterMove::create;
            immunities = ObjectSet.with(
                StatusEffects.burning, StatusEffects.melting, StatusEffects.wet, StatusEffects.slow
            );

            weapons.add(new Weapon("terra-green-launcher"){{
                reload = 18f;
                shootY = 3f;
                x = 43f / 4
                y = -16f / 4;
                rotate = true;
                rotateSpeed = 9f;
                mirror = true;
                bullet = new BasicBulletType(2.7f, 26){{
                    sprite = "terra-plasma";
                    velocityScaleRandMin = 0.7f;
                    width = height = 5f;
                    shrinkX = shrinkY = -0.3f;
                    despawnHit = true;
                    reflectable = false;
                    drag = 0.01f;
                    splashDamageRadius = 30f;
                    splashDamage = 30f;
                    lifetime = 48f;
                    lightOpacity = 0.5f;
                    trailWidth = 1.1f;
                    trailLength = 7;
                    trailColor = backColor = hitColor = lightColor = greenLight;
                    frontColor = Color.white;
                    hitEffect = despawnEffect = TerraFx.circleFadeSmall;
                }};
            }});
        }};

        endSpawn = new UnitType("end-spawn"){{
            flying = true;
            speed = 1.9f;
            rotateSpeed = 4f;
            drag = 0.05f;
            accel = 0.03f;
            hitSize = 51f;
            softShadowScl = 0.6f;
            health = 40000;
            armor = 30;
            engineSize = 8.3f;
            engineOffset = 70f / 4;
            outlineRadius = 6;
            outlineColor = Color.valueOf("36363c");
            envDisabled = Env.none;
            lowAltitude = true;
            constructor = UnitEntity::create;
            healColor = Color.valueOf("e13131");

            weapons.add(
            new Weapon("terra-end-cannon"){{
                x = 107f / 4f;
                y = 69f / 4f;
                shootY = 24f;
                rotate = false;
                shootSound = TerraSounds.shootHeavy;
                reload = 108f;
                cooldownTime = 78f;
                shake = 0.6f;
                layerOffset = -0.001f;
                minWarmup = 0.75f;
                recoil = 4f;
                shake = 2f;
                
                bullet = new ArtilleryBulletType(6.5f, 185){{
                    collidesTiles = collides = true;
                    lifetime = 60f;
                    shootEffect = Fx.shootBigColor;
                    smokeEffect = Fx.shootSmokeSquareBig;
                    frontColor = Color.white;
                    trailEffect = new MultiEffect(Fx.artilleryTrail, Fx.artilleryTrailSmoke);
                    hitSound = Sounds.none;
                    width = 18f;
                    height = 24f;
                    rangeOverride = 385f;

                    lightColor = trailColor = hitColor = backColor = Color.valueOf("e13131");
                    lightRadius = 40f;
                    lightOpacity = 0.7f;

                    trailWidth = 4.5f;
                    trailLength = 19;
                    trailChance = -1f;

                    despawnEffect = Fx.none;
                    despawnSound = Sounds.explosionDull;

                    hitEffect = despawnEffect = new ExplosionEffect(){{
                        lifetime = 50f;
                        waveStroke = 5f;
                        waveColor = sparkColor = trailColor;
                        waveRad = 45f;
                        smokeSize = 0f;
                        smokeSizeBase = 0f;
                        sparks = 10;
                        sparkRad = 25f;
                        sparkLen = 8f;
                        sparkStroke = 3f;
                    }};

                    splashDamage = 120f;
                    splashDamageRadius = 36f;

                    fragBullets = 15;
                    fragVelocityMin = 0.5f;
                    fragRandomSpread = 130f;
                    fragLifeMin = 0.3f;
                    despawnShake = 5f;

                    collidesAir = true;

                    fragBullet = new BasicBulletType(5.5f, 37){{
                        pierceCap = 2;
                        pierceBuilding = true;

                        homingPower = 0.09f;
                        homingRange = 150f;

                        lifetime = 40f;
                        shootEffect = Fx.shootBigColor;
                        smokeEffect = Fx.shootSmokeSquareBig;
                        frontColor = Color.white;
                        hitSound = Sounds.none;
                        width = 12f;
                        height = 20f;

                        lightColor = trailColor = hitColor = backColor = Color.valueOf("e13131");
                        lightRadius = 40f;
                        lightOpacity = 0.7f;

                        trailWidth = 2.2f;
                        trailLength = 7;
                        trailChance = -1f;

                        collidesAir = true;

                        despawnEffect = Fx.none;
                        splashDamage = 35f;
                        splashDamageRadius = 30f;

                        hitEffect = despawnEffect = new MultiEffect(new ExplosionEffect(){{
                            lifetime = 30f;
                            waveStroke = 2f;
                            waveColor = sparkColor = trailColor;
                            waveRad = 5f;
                            smokeSize = 0f;
                            smokeSizeBase = 0f;
                            sparks = 5;
                            sparkRad = 20f;
                            sparkLen = 6f;
                            sparkStroke = 2f;
                        }}, Fx.blastExplosion);
                    }};
                }};
            }},
            new SpeedUpWeapon("terra-end-small-mount"){{
                x = 62f / 4f;
                y = 15f / 4f;
                rotate = true;
                rotateSpeed = 4;
                shootY = 10;
                reload = 62f;
                cooldownTime = 60f;
                accelPerShot = 0.5f;
                minReload = reload / 2f;
                accelCooldownWaitTime = reload * 3f;

                bullet = new ShrapnelBulletType() {{
                    length = 178;
                    damage = 75f;
                    status = TerraStatusEffects.extinction;
                    statusDuration = 60f;
                    width = 9f;
                    fromColor = Color.valueOf("ffb59f");
                    hitColor = lightColor = lightningColor = toColor = Color.valueOf("e13131");
                    shootEffect = smokeEffect = TerraFx.fuseShoot;
                }};

                shoot = new ShootSpread(){{
                    shots = 3;
                    spread = 10f;
                }};
                shootSound = Sounds.shootFuse;
                shake = 2f;
            }},
            new PointDefenseWeapon("terra-end-smaller-mount"){{
                x = 117f / 4f;
                y = -17f / 4f;
                shootY = 3.3f;
                reload = 7f;
                targetInterval = 4f;
                targetSwitchInterval = 8f;
                
                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    hitEffect = Fx.pointHit;
                    maxRange = 220f;
                    damage = 43f;
                }};
            }});
        }
            @Override
            public void init() {
                super.init();
        
                immunities = new ObjectSet<>();
                for (StatusEffect effect : Vars.content.statusEffects()) {
                    if (effect == null || effect == StatusEffects.none || effect == StatusEffects.overdrive || effect == TerraStatusEffects.warped) continue;
        
                    if (effect.damage > 0
                        || effect.healthMultiplier < 1f
                        || effect.speedMultiplier < 1f
                        || effect.damageMultiplier < 1f
                        || effect.disarm
                        || effect.reloadMultiplier < 1f) {
                        immunities.add(effect);
                    }
                }
            }
        };

        endGuard = new UnitType("end-guard"){{
            flying = true;
            speed = 1.1f;
            rotateSpeed = 1.7f;
            drag = 0.05f;
            accel = 0.03f;
            hitSize = 94f;
            softShadowScl = 0.6f;
            health = 280000;
            armor = 70;
            fallSpeed = 0.01f;
            faceTarget = false;
            engineSize = 10f;
            engineOffset = 270f / 4;
            setEnginesMirror(
                new UnitEngine(90f / 4f, -270f / 4f, 8f, -90f)
            );
            outlineRadius = 6;
            outlineColor = Color.valueOf("36363c");
            envDisabled = Env.none;
            lowAltitude = true;
            constructor = UnitEntity::create;
            healColor = Color.valueOf("e13131");

            abilities.add(new AdaptedHealAbility(200f, 90f, hitSize * 2f, healColor){{selfHealReloadTime = 400f;}});
            abilities.add(new EnergyFieldAbility(75f, 45f, hitSize * 2.75f){{color = healColor; status = StatusEffects.melting; statusDuration = 60f; y = -81f / 4f; hitBuildings = false; healPercent = 0.003f;}});

            Weapon smallerIIMount = new Weapon("terra-end-smaller-II-mount"){{
                shootY = 3f;
                reload = 90f;
                rotate = true;
                rotateSpeed = 5f;
                predictTarget = false;
                alternate = false;
                continuous = true;
                parentizeEffects = true;
                shootSound = Sounds.beamLustre;
                bullet = new DelayedPointBulletType(){{
                    width = 10f;
                    damage = 340;
                    buildingDamageMultiplier = 0.8f;
                    rangeOverride = 330;
                    trailEffect = Fx.none;
                    lightColor = lightningColor = trailColor = hitColor = Color.valueOf("e13131");
                    status = TerraStatusEffects.extinction;
                    statusDuration = 100f;
                    despawnShake = hitShake = 2f;
                    collidesAir = collidesGround = true;
                    hitEffect = despawnEffect = new MultiEffect(Fx.hitSquaresColor, Fx.squareWaveEffect);
                }};
            }};
            Weapon staticCannon = new Weapon("terra-end-cannon"){{
                shootY = 24f;
                reload = cooldownTime = 78f;
                rotate = false;
                baseRotation = -30f;
                predictTarget = false;
                alternate = false;
                inaccuracy = 40f;
                layerOffset = -0.001f;
                flipSprite = true;
                shootCone = 361f;
                shootSound = TerraSounds.shootLaunch;
                shoot = new ShootPattern(){{
                    shots = 2;
                    firstShotDelay = 0f;
                }};
                bullet = new BasicBulletType(8f, 518){{
                    lifetime = 98f;
                    splashDamage = damage / 2f;
                    splashDamageRadius = 25f;
                    scaledSplashDamage = true;
                    sprite = "missile-large";
                    drag = 0.005f;
                    followAimSpeed = 4.5f;
                    width = 6f;
                    height = 22f;
                    shrinkY = 0.1f;
                    hitColor = lightColor = trailColor = backColor = Color.valueOf("e13131");
                    frontColor = Color.valueOf("ffb59f");
                    trailWidth = 2f;
                    trailLength = 12;
                    pierce = true;
                    hitEffect = TerraFx.hitSparkLarge;
                    despawnEffect = new MultiEffect(hitEffect, Fx.massiveExplosion);
                    despawnSound = Sounds.unitExplode1;
                }};
            }};

            weapons.add(copyAndMove(smallerIIMount, 113f / 4f, 2f / 4f));
            weapons.add(copyAndMoveAnd(smallerIIMount, 46f / 4f, 179f / 4f, w -> {w.reload = 110f;}));
            weapons.add(copyAndMove(staticCannon, 91f / 4f, 69f / 4f));
            weapons.add(copyAndMoveAnd(staticCannon, 119f / 4f, 12f / 4f, w -> {w.shoot = new ShootPattern(){{shots = 2; firstShotDelay = 20f;}}; w.baseRotation = -45f;}));
            weapons.add(copyAndMoveAnd(staticCannon, 137f / 4f, -50f / 4f, w -> {w.shoot = new ShootPattern(){{shots = 2; firstShotDelay = 40f;}}; w.baseRotation = -60f;}));
            weapons.add(
            new Weapon("terra-end-big-mount"){{
                x = 87f / 4f;
                y = -87f / 4f;
                shootY = 4f;
                rotate = true;
                rotateSpeed = 2.8f;
                shootSound = TerraSounds.shootHeavy;
                reload = 163f;
                shake = 3f;
                bullet = new BasicBulletType(11f, 420){{
                    lifetime = 40f;
                    width = height = 28f;
                    shrinkY = 0f;
                    trailWidth = 9f;
                    trailLength = 24;
                    hitColor = trailColor = backColor = Color.valueOf("e13131");
                    frontColor = lightColor = lightningColor = Color.valueOf("ffb59f");
                    sprite = "circle-bullet";
                    lightning = 7;
                    lightningDamage = damage * 0.25f;
                    lightningLength = 10;
                    lightningLengthRand = 9;
                    splashDamageRadius = 45f;
                    splashDamage = damage / 3f;
                    status = TerraStatusEffects.extinction;
                    statusDuration = 180f;
                    smokeEffect = Fx.shootSmokeSquareBig;
                    hitEffect = despawnEffect = new MultiEffect(
                        TerraFx.hitSparkLarge,
                        new WaveEffect(){{
                            colorFrom = lightColor = frontColor;
                            colorTo = hitColor;
                            lifetime = 20f;
                            sizeTo = 50f;
                            strokeFrom = 4f;
                        }}
                    );
                    fragBullets = 5;
                    fragBullet = new BasicBulletType(19f, 210){{
                        lifetime = 12f;
                        width = height = 10f;
                        shrinkY = 0f;
                        trailWidth = 5f;
                        trailLength = 75;
                        hitColor = trailColor = backColor = Color.valueOf("e13131");
                        frontColor = lightColor = lightningColor = Color.valueOf("ffb59f");
                        sprite = "circle-bullet";
                        lightning = 4;
                        lightningDamage = damage * 0.25f;
                        lightningLength = 5;
                        lightningLengthRand = 4;
                        splashDamageRadius = 20f;
                        splashDamage = damage / 2f;
                        status = TerraStatusEffects.extinction;
                        statusDuration = 40f;
                        smokeEffect = Fx.shootSmokeSquareBig;
                        hitEffect = despawnEffect = new MultiEffect(
                            TerraFx.hitSpark,
                            new WaveEffect(){{
                                colorFrom = lightColor = frontColor;
                                colorTo = hitColor;
                                lifetime = 13f;
                                sizeTo = 30f;
                                strokeFrom = 3f;
                            }}
                        );
                    }};
                }};
            }},
            new Weapon("terra-end-heavy-blaster"){{
                x = 0f;
                y = 60f / 4f;
                shootY = 10f;
                rotate = true;
                rotateSpeed = 1.5f;
                shootSound = TerraSounds.acceleratinglaserloop;
                shootSoundVolume = 0.5f;
                reload = 228f;
                cooldownTime = 140f;
                recoil = 4f;
                continuous = true;
                parentizeEffects = true;
                mirror = false;
                shake = 1.2f;
                layerOffset = 0.001f;
                parts.add(new RegionPart("-blade") {{
                    outline = mirror = true;
                    x = y = 0;
                    moveX = 5;
                    moveRot = 10f;
                    progress = PartProgress.recoil;
                }});
                bullet = new AcceleratingLaserBulletType(148f){{
                    maxLength = 338f;
                    maxRange = 338f;
                    oscOffset = 0.3f;
                    lifetime = 275;
                    width = 30f;
                    collisionWidth = 15f;
                    status = TerraStatusEffects.extinction;
                    statusDuration = 120f;
                    colors = new Color[]{Color.valueOf("e13131").cpy().a(0.3f), Color.valueOf("e13131"), Color.white};
                    pierceCap = 4;
                    pierceBuilding = true;
                    hitColor = Color.valueOf("e13131");
                    shootEffect = hitEffect = new Effect(27f, e ->
                    Angles.randLenVectors(e.id, 8, 90f * e.fin(), e.rotation, 80f, (x, y) -> {
                        float angle = Mathf.angle(x, y);
                        color(Color.valueOf("e13131"), e.fin());
                        Lines.stroke(1.5f);
                        Lines.lineAngleCenter(e.x + x, e.y + y, angle, e.fslope() * 13f);
                    }));
                }};
            }},
            new RepairBeamWeapon("terra-end-small-mount"){{
                shootY = 7f;
                x = 160f / 4f;
                y = -187f / 4f;
                beamWidth = 0.8f;
                repairSpeed = 12.7f;
                targetBuildings = true;
                laserColor = healColor = Color.valueOf("e13131");

                bullet = new BulletType(){{
                    maxRange = 26f * 8;
                }};
            }});
        }
            @Override
            public void init() {
                super.init();
        
                immunities = new ObjectSet<>();
                for (StatusEffect effect : Vars.content.statusEffects()) {
                    if (effect == null || effect == StatusEffects.none || effect == StatusEffects.overdrive || effect == TerraStatusEffects.warped) continue;
        
                    if (effect.damage > 0
                        || effect.healthMultiplier < 1f
                        || effect.speedMultiplier < 1f
                        || effect.damageMultiplier < 1f
                        || effect.disarm
                        || effect.reloadMultiplier < 1f) {
                        immunities.add(effect);
                    }
                }
            }
        };

        end = new UnitType("end"){{
            hideDetails = false;
            flying = true;
            speed = 0.22f;
            rotateSpeed = 0.4f;
            drag = 0.035f;
            accel = 0.04f;
            hitSize = 150f;
            softShadowScl = 0.6f;
            health = 1250000;
            armor = 260;
            engineSize = engineOffset = 0f;
            drawCell = false;
            targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.core, null};

            float o = Mathf.random(5);
            for (float i = -50f / 4f; i <= 50 / 4f; i += 5f) {
                engines.add(new AncientEngine(i, -320f / 4f, 7f, -90, o));
            }
            for (float i = -388f / 4f; i <= -304 / 4f; i += 4f) {
                engines.add(new AncientEngine(i, -360f / 4f, 7f, -90, o));
            }
            for (float i = 304f / 4f; i <= 388 / 4f; i += 4f) {
                engines.add(new AncientEngine(i, -360f / 4f, 7f, -90, o));
            }
            outlineRadius = 6;
            outlineColor = Color.valueOf("36363c");
            envDisabled = Env.none;
            deathExplosionEffect = Fx.none;
            deathSound = TerraSounds.jumpIn;
            crashDamageMultiplier = 0;
            createScorch = false;
            createWreck = false;
            //fallSpeed = Float.POSITIVE_INFINITY;
            targetPriority = 4f;
            faceTarget = false;
            range = 380f;
            itemCapacity = 1000;
            lowAltitude = true;
            constructor = EndEntity::new;
            healColor = Color.valueOf("e13131");

            abilities.add(new AdaptedHealAbility(3250f, 120f, hitSize * 2f, healColor){{
                selfHealReloadTime = 200f;
            }});
            abilities.add(new Ability() {
                {
                    display = false;
                }
                @Override
                public void death(Unit unit) {
                    Effect.shake(unit.hitSize / 10f, unit.hitSize / 8f, unit.x, unit.y);
                    TerraFx.circleOut.at(unit.x, unit.y, unit.hitSize, unit.team.color);
                    TerraFx.jumpTrailOut.at(unit.x, unit.y, unit.rotation, unit.team.color, unit.type);
                    TerraSounds.jumpIn.at(unit.x, unit.y, 1, 3);
                }
            });

            Weapon smallerMount = new Weapon("terra-end-smaller-mount"){{
                shootY = 3.3f;
                reload = 75f;
                shootCone = 5f;
                rotate = true;
                predictTarget = false;
                alternate = false;
                
                bullet = new DelayedPointBulletType(){{
                    width = 10f;
                    damage = 1700;
                    buildingDamageMultiplier = 0.8f;
                    rangeOverride = 770;
                    trailEffect = Fx.none;
                    lightColor = lightningColor = trailColor = hitColor = Color.valueOf("e13131");
                    status = TerraStatusEffects.extinction;
                    statusDuration = 200f;
                    despawnShake = hitShake = 2f;
                    collidesAir = collidesGround = true;
                    hitEffect = despawnEffect = new MultiEffect(Fx.hitSquaresColor, Fx.squareWaveEffect);
                    shootEffect = TerraFx.shootRing;
                    smokeEffect = Fx.none;
                }};
            }};
            Weapon smallMount = new SpeedUpWeapon("terra-end-small-mount"){{
                rotate = true;
                rotateSpeed = 9;
                shootY = 10;
                recoil = 2f;
                reload = 49f;
                cooldownTime = 60f;
                accelPerShot = 0.4f;
                minReload = reload / 2f;
                accelCooldownWaitTime = reload * 3f;
                ejectEffect = Fx.casing1;
                shoot = new ShootMulti(new ShootHelix(), new ShootPattern() {{
                    shots = 3;
                    shotDelay = 6f;
                }});
                bullet = new BasicBulletType(8.9f, 421) {{
                    lifetime = 98f;
                    drag = 0.01f;
                    sprite = "missile";
                    width = 6f;
                    height = 18f;
                    trailWidth = 1.3f;
                    trailLength = 7;
                    pierce = true;
                    pierceCap = 2;
                    armorMultiplier = 0.5f;
                    homingPower = 0.2f;
                    status = StatusEffects.slow;
                    statusDuration = 60f;
                    hitColor = lightColor = trailColor = backColor = lightningColor = Color.valueOf("e13131");
                    frontColor = Color.valueOf("ffb59f");
                    lightning = 3;
                    lightningDamage = damage * 0.3f;
                    lightningLength = 5;
                    lightningLengthRand = 4;
                    fragBullets = 2;
                    fragLifeMin = 0.7f;
                    fragBullet = new DelayedPointBulletType(){{
                        width = 6f;
                        damage = 90;
                        rangeOverride = 70;
                        buildingDamageMultiplier = 0.8f;
                        trailEffect = Fx.none;
                        lightColor = lightningColor = trailColor = hitColor = Color.valueOf("e13131");
                        despawnShake = hitShake = 1f;
                        collidesAir = collidesGround = true;
                        hitEffect = despawnEffect = new MultiEffect(Fx.hitSquaresColor, Fx.squareWaveEffect);
                    }};
                }};
                shootSound = TerraSounds.shootFastLaser;
                shootSoundVolume = 0.4f;
                shake = 1.4f;
            }};
            Weapon accelLaser = new Weapon("terra-end-laser"){{
                shootY = 17f;
                rotate = true;
                rotateSpeed = 1.2f;
                shootSound = TerraSounds.acceleratinglaserloop;
                shootSoundVolume = 0.2f;
                reload = 190f;
                cooldownTime = 160f;
                recoil = 4f;
                continuous = true;
                parentizeEffects = true;
                alternate = false;
                shake = 1.2f;
                bullet = new AcceleratingLaserBulletType(428f){{
                    maxLength = 420f;
                    maxRange = 420f;
                    oscOffset = 0.3f;
                    lifetime = 275;
                    width = 25f;
                    collisionWidth = 12f;
                    status = TerraStatusEffects.energyOverload;
                    statusDuration = 120f;
                    colors = new Color[]{Color.valueOf("e13131").cpy().a(0.3f), Color.valueOf("e13131"), Color.white};
                    pierceCap = 9;
                    pierceBuilding = true;
                    hitColor = Color.valueOf("e13131");
                    shootEffect = hitEffect = new Effect(27f, e ->
                    Angles.randLenVectors(e.id, 8, 90f * e.fin(), e.rotation, 80f, (x, y) -> {
                        float angle = Mathf.angle(x, y);
                        color(Color.valueOf("e13131"), e.fin());
                        Lines.stroke(1.5f);
                        Lines.lineAngleCenter(e.x + x, e.y + y, angle, e.fslope() * 13f);
                    }));
                }};
            }};
            Weapon blackCannon = new Weapon("terra-end-cannon"){{
                shootY = 24f;
                rotate = true;
                rotateSpeed = 2.6f;
                shootSound = TerraSounds.shootBlackhole;
                shootSoundVolume = 0.5f;
                reload = 150f;
                cooldownTime = 220f;
                recoil = 4f;
                shake = 2f;
                bullet = new BasicBulletType(26.8f, 955f){{
                    lifetime = 26f;
                    shieldDamageMultiplier = 4f;
                    armorMultiplier = 0f;
                    width = 19f;
                    height = 19f;
                    shrinkY = 0f;
                    frontColor = Color.valueOf("ffb59f");
                    hitColor = lightColor = backColor = trailColor = Color.valueOf("e13131");
                    trailWidth = 1.3f;
                    trailLength = 4;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitEffect = despawnEffect = Fx.titanSmoke;
                    sprite = "large-orb";
                    fragBullets = 1;
                    targetMissiles = absorbable = reflectable = false;
                    fragBullet = new BlackHoleBulletType(0f, 376f){{
                        lifetime = 200f;
                        shieldDamageMultiplier = 4f;
                        armorMultiplier = 0f;
                        color = Color.valueOf("e13131");
                        damageRadius = 33f;
                        suctionRadius = 240f;
                        growTime = 40f;
                        shrinkTime = 70f;
                        status = TerraStatusEffects.singularEvaporation;
                        loopSoundVolume = 1.2f;
                        statusDuration = 150f;
                        keepVelocity = false;
                        intervalBullets = 1;
                        bulletInterval = 1f;
                        intervalBullet = new BulletType(){{
                            speed = 0f;
                            damage = 0f;
                            lifetime = 0f;
                            instantDisappear = true;
                            shootEffect = despawnEffect = hitEffect = smokeEffect = Fx.none;
                            splashDamage = 0.001f;
                            splashDamageRadius = 33f;
                            status = TerraStatusEffects.singularEvaporation;
                            statusDuration = 150f;
                        }};
                    }};
                }};
            }};

            weapons.add(copyAndMove(smallerMount, 184f / 4f, 210f / 4f));
            weapons.add(copyAndMove(smallerMount, 400f / 4f, -34f / 4f));
            weapons.add(copyAndMove(smallerMount, 630f / 4f, -250f / 4f));
            weapons.add(copyAndMove(smallerMount, 754f / 4f, -99f / 4f));
            weapons.add(copyAndMove(smallerMount, 817f / 4f, -148f / 4f));
            
            weapons.add(copyAndMove(smallMount, 233f / 4f, 161f / 4f));
            weapons.add(copyAndMoveAnd(smallMount, 507f / 4f, -284f / 4f, w -> {w.reload = 46f;}));
            weapons.add(copyAndMoveAnd(smallMount, 585f / 4f, -202f / 4f, w -> {w.reload = 43f;}));
            
            weapons.add(copyAndMove(accelLaser, 354.5f / 4f, 74.5f / 4f));
            weapons.add(copyAndMoveAnd(accelLaser, 460f / 4f, -150f / 4f, w -> {w.reload = 160f;}));
            weapons.add(copyAndMoveAnd(accelLaser, 538f / 4f, 35f / 4f, w -> {w.reload = 130f;}));

            weapons.add(copyAndMove(blackCannon, 328f / 4f, -154f / 4f));
            weapons.add(copyAndMoveAnd(blackCannon, 730f / 4f, 28f / 4f, w -> {w.reload = 190f;}));

            weapons.add(
            new Weapon("terra-end-multi-turret"){{
                x = 201f / 4f;
                y = -86f / 4f;
                shootY = 20f;
                rotate = true;
                rotateSpeed = 1.8f;
                shootSound = Sounds.shootSmite;
                reload = 146f;
                cooldownTime = 60f;
                shake = 0.6f;
                layerOffset = 0.002f;
                minWarmup = 0.75f;

                shoot = new ShootMulti(new ShootAlternate() {{
                    spread = 12.3f;
                    shots = 2;
                    barrels = 2;
                }}, new ShootPattern() {{
                    shots = 3;
                    shotDelay = 12f;
                }});

                parts.add(new RegionPart("-side") {{
                    outline = mirror = under = true;
                    x = y = 0;
                    moveX = -2;
                    //moveRot = -6f;
                    //moveY = -2f;
                    progress = PartProgress.warmup;
                }}, new RegionPart("-barrels") {{
                    under = outline = true;
                    x = y = 0;
                    moveY = -5f;
                    progress = PartProgress.recoil;
                }});
                
                bullet = new BasicBulletType(7f, 720) {{
                    status = TerraStatusEffects.impactStun;
                    statusDuration = 120f;
                    sprite = "missile-large";
        
                    pierceArmor = true;
                    lightOpacity = 0.7f;
                    reflectable = false;
                    knockback = 3f;
                    impact = true;
                    drag = -0.04f;
                    pierce = pierceBuilding = true;
                    buildingDamageMultiplier = 3f;
                    pierceCap = 7;
        
                    lightningColor = backColor = trailColor = hitColor = lightColor = Color.valueOf("e13131");
                    lightRadius = 70f;
                    smokeEffect = Fx.shootSmokeSquareBig;
                    lifetime = 40f;
                    frontColor = Color.white;
        
                    lightning = 2;
                    lightningDamage = damage * 0.4f;
                    lightningLength = 7;
                    lightningLengthRand = 16;
                    splashDamageRadius = 36f;
                    splashDamage = damage / 2f;
        
                    width = 13f;
                    height = 35f;
                    trailLength = 20;
                    trailWidth = 2.3f;
                    hitShake = 5f;
                    hitEffect = despawnEffect = new MultiEffect(Fx.hitSquaresColor, Fx.squareWaveEffect, 
                        new ExplosionEffect(){{
                            waveColor = sparkColor = Color.valueOf("e13131");
                            smokeColor = Color.valueOf("ffb59f");
                            smokes = 9;
                            waveRad = 40f;
                            waveLife = 9f;
                        }}
                    );
                }};
            }});
        }
            @Override
            public void init() {
                super.init();
        
                immunities = new ObjectSet<>();
                for (StatusEffect effect : Vars.content.statusEffects()) {
                    if (effect == null || effect == StatusEffects.none || effect == StatusEffects.overdrive || effect == TerraStatusEffects.warped) continue;
        
                    if (effect.damage > 0
                        || effect.healthMultiplier < 1f
                        || effect.speedMultiplier < 1f
                        || effect.damageMultiplier < 1f
                        || effect.disarm
                        || effect.reloadMultiplier < 1f) {
                        immunities.add(effect);
                    }
                }
            }
        };

        flightLeadMissile = new MissileUnitType("flight-lead-missile"){{
            speed = 3f;
            maxRange = 4f;
            lifetime = 60f;
            hitSize = 4f;
            outlineColor = Pal.darkerMetal;
            engineColor = trailColor = Pal.stat;
            engineLayer = Layer.effect;
            engineSize = 1.4f;
            engineOffset = 3.5f;
            rotateSpeed = 5f;
            trailLength = 6;
            //missileAccelTime = 50f;
            lowAltitude = true;
            loopSound = Sounds.loopMissileTrail;
            loopSoundVolume = 0.1f;
            //deathSound = Sounds.explosionMissile;
            targetAir = true;
            targetUnderBlocks = false;

            fogRadius = 3f;

            health = 45;
            hidden = false;

            weapons.add(new Weapon(){{
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                //deathExplosionEffect = Fx.massiveExplosion;
                shootOnDeath = true;
                shake = 1.5f;
                bullet = new ExplosionBulletType(18f, 24f){{
                    hitColor = Pal.stat;
                    collidesAir = true;
                    buildingDamageMultiplier = 0.4f;
                    reloadMultiplier = 1f;
                    ammoMultiplier = 2f;
                }};
            }});
        }};
        flightTitaniumMissile = new MissileUnitType("flight-titanium-missile"){{
            speed = 3f;
            maxRange = 4f;
            lifetime = 60f;
            hitSize = 4f;
            outlineColor = Pal.darkerMetal;
            engineColor = trailColor = Pal.stat;
            engineLayer = Layer.effect;
            engineSize = 1.4f;
            engineOffset = 3.5f;
            rotateSpeed = 5f;
            trailLength = 6;
            //missileAccelTime = 50f;
            lowAltitude = true;
            loopSound = Sounds.loopMissileTrail;
            loopSoundVolume = 0.1f;
            //deathSound = Sounds.explosionMissile;
            targetAir = true;
            targetUnderBlocks = false;

            fogRadius = 3f;

            health = 85;
            hidden = false;

            weapons.add(new Weapon(){{
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                //deathExplosionEffect = Fx.massiveExplosion;
                shootOnDeath = true;
                shake = 1.5f;
                bullet = new ExplosionBulletType(53f, 19f){{
                    hitColor = Pal.stat;
                    collidesAir = true;
                    buildingDamageMultiplier = 0.4f;
                    reloadMultiplier = 1f;
                    ammoMultiplier = 0.4f;
                }};
            }});
        }};
        flightMetaglassMissile = new MissileUnitType("flight-metaglass-missile"){{
            speed = 3f;
            maxRange = 4f;
            lifetime = 60f;
            hitSize = 4f;
            outlineColor = Pal.darkerMetal;
            engineColor = trailColor = Pal.stat;
            engineLayer = Layer.effect;
            engineSize = 1.4f;
            engineOffset = 3.5f;
            rotateSpeed = 5f;
            trailLength = 6;
            //missileAccelTime = 50f;
            lowAltitude = true;
            loopSound = Sounds.loopMissileTrail;
            loopSoundVolume = 0.1f;
            //deathSound = Sounds.explosionMissile;
            targetAir = true;
            targetUnderBlocks = false;

            fogRadius = 3f;

            health = 60;
            hidden = false;

            weapons.add(new Weapon(){{
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                //deathExplosionEffect = Fx.massiveExplosion;
                shootOnDeath = true;
                shake = 1.5f;
                bullet = new ExplosionBulletType(26f, 36f){{
                    hitColor = Pal.glassAmmoBack;
                    collidesAir = true;
                    buildingDamageMultiplier = 0.4f;
                    reloadMultiplier = 1f;
                    ammoMultiplier = 0.85f;
                    fragBullets = 6;
                    fragBullet = new BasicBulletType(){{
                        speed = 3f;
                        damage = 5f;
                        width = 5f;
                        height = 12f;
                        shrinkY = 1f;
                        lifetime = 20f;
                        backColor = trailColor = Pal.glassAmmoBack;
                        hitColor = frontColor = Pal.glassAmmoFront;
                        despawnEffect = Fx.none;
                    }};
                }};
            }});
        }};

        aircraftThoriumMissile = new MissileUnitType("aircraft-thorium-missile"){{
            speed = 5f;
            maxRange = 6f;
            lifetime = 105f;
            hitSize = 6f;
            outlineColor = Pal.darkerMetal;
            engineColor = trailColor = Pal.stat;
            engineLayer = Layer.effect;
            engineSize = 1.4f;
            engineOffset = 7f;
            rotateSpeed = 2.5f;
            trailLength = 9;
            missileAccelTime = 20f;
            lowAltitude = true;
            loopSound = Sounds.loopMissileTrail;
            loopSoundVolume = 0.2f;
            deathSound = Sounds.explosionMissile;
            targetAir = true;
            targetUnderBlocks = false;

            fogRadius = 6f;

            health = 160;
            armor = 3;
            hidden = false;

            weapons.add(new Weapon(){{
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                //deathExplosionEffect = Fx.massiveExplosion;
                shootOnDeath = true;
                shake = 2.5f;
                bullet = new ExplosionBulletType(112f, 5f * 8){{
                    hitColor = Items.thorium.color;
                    collidesAir = true;
                    buildingDamageMultiplier = 0.3f;
                    ammoMultiplier = 3f;
                    rangeChange = 24;
                    status = StatusEffects.blasted;
                }};
            }});
        }};
        aircraftThermoxiteMissile = new MissileUnitType("aircraft-thermoxite-missile"){{
            speed = 3f;
            maxRange = 6f;
            lifetime = 140f;
            hitSize = 6f;
            outlineColor = Pal.darkerMetal;
            engineColor = trailColor = TerraItems.rawThermoxite.color;
            engineLayer = Layer.effect;
            engineSize = 1.4f;
            engineOffset = 7f;
            rotateSpeed = 3f;
            trailLength = 9;
            missileAccelTime = 20f;
            lowAltitude = true;
            loopSound = Sounds.loopMissileTrail;
            loopSoundVolume = 0.2f;
            deathSound = Sounds.explosionMissile;
            targetAir = true;
            targetUnderBlocks = false;

            fogRadius = 6f;

            health = 400;
            armor = 3;
            hidden = false;

            weapons.add(new Weapon(){{
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                deathExplosionEffect = Fx.massiveExplosion;
                shootOnDeath = true;
                shake = 3f;
                bullet = new ExplosionBulletType(188f, 7f * 8){{
                    hitColor = TerraItems.rawThermoxite.color;
                    shootEffect = new WrapEffect(Fx.shootQuellPulse, TerraItems.rawThermoxite.color);
                    collidesAir = true;
                    buildingDamageMultiplier = 0.2f;
                    ammoMultiplier = 2f;
                    reloadMultiplier = 0.2f;
                    status = TerraStatusEffects.crystalization;
                    statusDuration = 90f;
                }};
            }});
        }};
        aircraftFissileMissile = new MissileUnitType("aircraft-fissile-missile"){{
            speed = 3.6f;
            maxRange = 6f;
            lifetime = 120f;
            hitSize = 6f;
            outlineColor = Pal.darkerMetal;
            engineColor = trailColor = TerraItems.gammaCell.color;
            engineLayer = Layer.effect;
            engineSize = 1.4f;
            engineOffset = 7f;
            rotateSpeed = 4.5f;
            trailLength = 9;
            missileAccelTime = 20f;
            lowAltitude = true;
            loopSound = Sounds.loopMissileTrail;
            loopSoundVolume = 0.2f;
            deathSound = Sounds.explosionMissile;
            targetAir = true;
            targetUnderBlocks = false;

            fogRadius = 6f;

            health = 300;
            armor = 3;
            hidden = false;

            weapons.add(new Weapon(){{
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                deathExplosionEffect = Fx.massiveExplosion;
                shootOnDeath = true;
                shake = 3f;
                bullet = new ExplosionBulletType(88f, 14f * 8){{
                    hitColor = TerraItems.rawThermoxite.color;
                    shootEffect = new WrapEffect(Fx.titanExplosionSmall, TerraItems.gammaCell.color);
                    smokeEffect = new WrapEffect(Fx.titanSmokeSmall, TerraItems.gammaCell.color);
                    collidesAir = true;
                    buildingDamageMultiplier = 0.25f;
                    ammoMultiplier = 1f;
                    reloadMultiplier = 0.33f;
                    status = TerraStatusEffects.radited;
                    statusDuration = 200f;
                }};
            }});
        }};
    }

    public static Weapon copyAndMove(Weapon weapon, float x, float y) {
        Weapon n = weapon.copy();
        n.x = x;
        n.y = y;
        return n;
    }

    public static Weapon copyAndMoveAnd(Weapon weapon, float x, float y, Cons<Weapon> modifier) {
        Weapon n = weapon.copy();
        n.x = x;
        n.y = y;
        modifier.get(n);
        return n;
    }
}
