package terra.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.*;
import terra.ai.*;
import terra.content.*;
import terra.type.bullet.*;
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
import mindustry.type.ammo.*;
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
    //drones
    healDrone;

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
            ammoType = new ItemAmmoType(TerraItems.carbon);
            createWreck = false;
            constructor = UnitEntity::create;
            healColor = Pal.suppress;

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
            ammoType = new ItemAmmoType(TerraItems.carbon);
            createWreck = false;
            useUnitCap = false;
            hidden = true;
            constructor = TimedKillUnit::create;
            healColor = Pal.suppress;
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
            //range = 90f;
            itemCapacity = 15;
            ammoType = new PowerAmmoType(1000);
            lowAltitude = true;
            constructor = UnitEntity::create;
            healColor = Pal.suppress;

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
            engineSize = 3.5f;
            engineOffset = 7f;
            //range = 120f;
            itemCapacity = 40;
            ammoType = new PowerAmmoType(2000);
            lowAltitude = true;
            constructor = UnitEntity::create;
            immunities = ObjectSet.with(StatusEffects.sapped);
            healColor = Pal.suppress;
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
            engineSize = 6f;
            engineOffset = 14f;
            //range = 120f;
            itemCapacity = 60;
            ammoType = new PowerAmmoType(4000);
            lowAltitude = true;
            constructor = UnitEntity::create;
            immunities = ObjectSet.with(StatusEffects.sapped);
            healColor = Pal.suppress;
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
                reload = 170f;
                rotate = true;
                rotateSpeed = 1.2f;
                mirror = false;
                shoot = new ShootAlternate() {{
                    shots = 5;
                    shotDelay = 8f;
                    barrels = 3;
                    spread = 4f;
                }};
                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.suppress;
                    shake = 1f;
                    speed = 0f;
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
            engineSize = 1f;
            engineOffset = 0f;
            range = 80f;
            itemCapacity = 0;
            ammoType = new PowerAmmoType(50000);
            useUnitCap = false;
            hidden = false;
            constructor = TimedKillUnit::create;
            immunities = ObjectSet.with(StatusEffects.sapped);
            healColor = Pal.suppress;
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
            engineSize = 5.75f;
            engineOffset = 14f;
            setEnginesMirror(
                new UnitEngine(17f, -9f, 5f, -45f)
            );
            //range = 120f;
            itemCapacity = 90;
            ammoType = new PowerAmmoType(6000);
            lowAltitude = true;
            constructor = UnitEntity::create;
            immunities = ObjectSet.with(StatusEffects.sapped, TerraStatusEffects.impactStun);
            healColor = Pal.suppress;
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
                    speed = 0f;
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
            ammoType = new PowerAmmoType(9000);
            lowAltitude = true;
            constructor = UnitEntity::create;
            immunities = ObjectSet.with(
                StatusEffects.sapped, StatusEffects.electrified, StatusEffects.shocked, 
                TerraStatusEffects.impactStun, TerraStatusEffects.energyOverload, TerraStatusEffects.singularEvaporation
            );
            healColor = Pal.suppress;
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

            weapons.add(
            new Weapon("terra-eternity-mount"){{
                x = 128.5f / 4f;
                y = 94.5f / 4f;
                shootY = 8f;
                rotate = true;
                rotateSpeed = 1.8f;
                shootSound = TerraSounds.acceleratinglaserloop;
                shootSoundVolume = 0.6f;
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
                    speed = 0f;
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
                    sizeTo = 14f;
                    edge = 0f;
                    edgeTo = 14f * 1.2f;
                    progress = growProgress = PartProgress.reload.inv().delay(0.2f);
                }});
                bullet = new BlackHoleBulletType(1.1f, 168f){{
                    lifetime = 300f;
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
                    color = Pal.suppress;
                    damageRadius = 30f;
                    suctionRadius = 300f;
                    growTime = 40f;
                    shrinkTime = 60f;
                    status = TerraStatusEffects.singularEvaporation;
                    loopSoundVolume = 5f;
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
                        splashDamageRadius = 30f;
                        status = TerraStatusEffects.singularEvaporation;
                        statusDuration = 180f;
                    }};
                }};
            }});
        }};

        healDrone = new UnitType("healDrone"){{
            flying = true;
            aiController = DroneAI::new;
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
    }
}
