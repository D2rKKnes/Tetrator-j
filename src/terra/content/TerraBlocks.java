package terra.content;

import terra.type.bullet.*;
import terra.world.blocks.*;
import terra.world.drawer.*;
import terra.world.meta.*;
import arc.*;
import arc.util.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.*;
import mindustry.core.*;
import mindustry.entities.effect.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.units.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.*;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;

public class TerraBlocks{
    public static Block
    //VERILUS & SERPULO =---
    //walls
    scrapWallColossol,
    metaglassWall, metaglassWallLarge, metaglassWallHuge,
    darkSteelWall, darkSteelWallLarge,
    //distrubution
    
    //power
    photonPanel, photonPanelLarge,
    //crafters
    bisiliconOven, darkSteelWorkshop,
    //production
    mechanicalWell, electricalWell,
    plasmaDrill, beamMiningFacility,
    //turrets
    flight,
    //units
    droneCentre;
    //other
    //NOTVA =---
    //GIER =---
    //TATNTROS =---
    //EREKIR & COPIS =---
    //OTHER =---
    public static void load(){
        //VERILUS & SERPULO =---
        //walls
        scrapWallColossol = new Wall("scrap-wall-colossol"){{
            requirements(Category.defense, with(Items.scrap, 6 * 25));
            health = 60 * 25 * 4;
            size = 5;
            buildCostMultiplier = 4f;
        }};
        metaglassWall = new AdvancedWall("metaglass-wall"){{
            requirements(Category.defense, with(Items.metaglass, 6));
            health = 380;
            size = 1;
            hitBullet = new BasicBulletType(){{
                damage = 21f;
                lifetime = 20f;
                width = 8f;
                height = 14f;
                shrinkY = 0.4f;
                shrinkX = 1f;
                speed = 2f;
                backColor = hitColor = trailColor = Pal.glassAmmoBack;
                frontColor = Pal.glassAmmoFront;
                despawnEffect = Fx.none;
                ammoMultiplier = 1f;
            }};
            hitBulletAmount = 9;
            hitBulletAmountRand = 3;
            hitBulletSpawnChance = 0.12f;
            hitBulletEffect = new WaveEffect(){{
                sizeFrom = strokeTo = 0f;
                sizeTo = 20f;
                strokeFrom = 2f;
                lifetime = 20f;
                colorFrom = Pal.glassAmmoFront;
                colorTo = Pal.glassAmmoBack;
            }};
            hitBulletSound = Sounds.explosionDull;
            hitBulletSoundPitchMin = 3.8f;
            hitBulletSoundPitchMax = 4f;
            hitBulletLifeRandScl = 0.3f;
            hitBulletSpeedRandScl = 0.4f;
        }};
        metaglassWallLarge = new AdvancedWall("metaglass-wall-large"){{
            requirements(Category.defense, with(Items.metaglass, 6 * 4));
            health = 380 * 4;
            size = 2;
            hitBullet = new BasicBulletType(){{
                damage = 21f;
                lifetime = 25f;
                width = 10f;
                height = 18f;
                shrinkY = 0.4f;
                shrinkX = 1f;
                speed = 2f;
                backColor = hitColor = trailColor = Pal.glassAmmoBack;
                frontColor = Pal.glassAmmoFront;
                despawnEffect = Fx.none;
                ammoMultiplier = 1f;
            }};
            hitBulletAmount = 11;
            hitBulletAmountRand = 3;
            hitBulletSpawnChance = 0.12f;
            hitBulletEffect = new WaveEffect(){{
                sizeFrom = strokeTo = 0f;
                sizeTo = 30f;
                strokeFrom = 2.5f;
                lifetime = 20f;
                colorFrom = Pal.glassAmmoFront;
                colorTo = Pal.glassAmmoBack;
            }};
            hitBulletSound = Sounds.explosionDull;
            hitBulletSoundPitchMin = 3.8f;
            hitBulletSoundPitchMax = 4f;
            hitBulletLifeRandScl = 0.3f;
            hitBulletSpeedRandScl = 0.4f;
        }};
        metaglassWallHuge = new AdvancedWall("metaglass-wall-huge"){{
            requirements(Category.defense, with(Items.metaglass, 6 * 9));
            health = 380 * 9;
            size = 3;
            hitBullet = new BasicBulletType(){{
                damage = 21f;
                lifetime = 30f;
                width = 12f;
                height = 22f;
                shrinkY = 0.4f;
                shrinkX = 1f;
                speed = 2f;
                backColor = hitColor = trailColor = Pal.glassAmmoBack;
                frontColor = Pal.glassAmmoFront;
                despawnEffect = Fx.none;
                ammoMultiplier = 1f;
            }};
            hitBulletAmount = 13;
            hitBulletAmountRand = 3;
            hitBulletSpawnChance = 0.12f;
            hitBulletEffect = new WaveEffect(){{
                sizeFrom = strokeTo = 0f;
                sizeTo = 40f;
                strokeFrom = 3f;
                lifetime = 20f;
                colorFrom = Pal.glassAmmoFront;
                colorTo = Pal.glassAmmoBack;
            }};
            hitBulletSound = Sounds.explosionDull;
            hitBulletSoundPitchMin = 3.8f;
            hitBulletSoundPitchMax = 4f;
            hitBulletLifeRandScl = 0.3f;
            hitBulletSpeedRandScl = 0.4f;
        }};
        darkSteelWall = new AdvancedWall("dark-steel-wall"){{
            requirements(Category.defense, with(TerraItems.darkSteel, 6 * 4, Items.metaglass, (6 * 4) / 2));
            health = 930 * 4;
            size = 2;
            absorbLasers = true;
            autoRegeneration = true;
            regenAmount = 0.012f / 60f;
            regenStartDelay = 150f;
            regenDamageStop = true;
        }};
        darkSteelWallLarge = new AdvancedWall("dark-steel-wall-large"){{
            requirements(Category.defense, with(TerraItems.darkSteel, 6 * 9, Items.metaglass, (6 * 9) / 2));
            health = 930 * 9;
            size = 3;
            absorbLasers = true;
            autoRegeneration = true;
            regenAmount = 0.012f / 60f;
            regenStartDelay = 150f;
            regenDamageStop = true;
        }};

        //distrubution

        
        //power
        photonPanel = new SolarGenerator("photon-solar-panel"){{
            requirements(Category.power, with(Items.lead, 35, Items.silicon, 40, TerraItems.diamondDust, 15));
            size = 2;
            powerProduction = 0.88f;
            drawer = new DrawMulti(
                new DrawVariantRegion("-bottom", 3),
                new DrawVariantRegion("-shine", 4, 2),
                new DrawRegion("-top")
            );
        }};
        photonPanelLarge = new SolarGenerator("photon-solar-panel-large"){{
            requirements(Category.power, with(TerraItems.titaniumPlate, 80, TerraItems.darkSteel, 65, TerraItems.diamondDust, 40));
            size = 4;
            powerProduction = 5.6f;
            drawer = new DrawMulti(
                new DrawVariantRegion("-bottom", 3),
                new DrawVariantRegion("-shine", 4, 2),
                new DrawRegion("-top")
            );
        }};
        
        //crafters
        bisiliconOven = new AttributeCrafter("bisilicon-oven"){{
            requirements(Category.crafting, with(Items.lead, 485, Items.graphite, 275, Items.titanium, 360));
            consumeItems(with(Items.lead, 15, Items.sand, 23, TerraItems.carbon, 18));
            consumePower(8.25f);
            outputItems = with(Items.silicon, 10, Items.metaglass, 7);
            size = 4;
            hasPower = true;
            hasItems = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"), 
                new DrawCustomWarmupRegion() {{
                    sinMag = 0.3f;
                    sinScl = 16f;
                    suffix = "-glass";
                }},
                new DrawRegion() {{
                    suffix = "-part2";
                    spinSprite = true;
                    rotateSpeed = 3f;
                    x = 7.5f;
                }},
                new DrawRegion() {{
                    suffix = "-part3";
                    spinSprite = true;
                    rotateSpeed = -1.4f;
                    x = 3.75f;
                    y = -10.75f;
                }},
                new DrawDefault(),
                new DrawTeamTop(),
                new DrawPistons() {{
                    suffix = "-part1";
                    sides = 1;
                    angleOffset = 90f;
                    sinMag = 5f;
                    sinScl = 3.6f;
                    lenOffset = 0f;
                }},
                new DrawWarmupRegion()
            );
            craftTime = 120;
            itemCapacity = 50;
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 1.3f;
            researchCostMultiplier = 0.25f;
            attribute = TerraAttributes.ice;
            maxBoost = 1.25f;
            boostScale = 0.0625f;

            buildType = () -> new AttributeCrafterBuild() {
                @Override
                public void updateTile() {
                    super.updateTile();
                    if (this.efficiency > 0 && Mathf.chanceDelta(0.8f * this.efficiency * warmup)) {
                        float rand1 = Mathf.range(2f), rand2 = Mathf.range(2f);
                        TerraFx.arcSmoke.at(x - 7.5f - rand1, y + 0.5f + rand1);
                        TerraFx.arcSmoke.at(x - 8.5f - rand2, y - 8.5f + rand2);
                    }
                }
            };
        }};

        darkSteelWorkshop = new GenericCrafter("dark-steel-production-workshop"){{
            requirements(Category.crafting, with(Items.thorium, 600, Items.silicon, 385, TerraItems.titaniumPlate, 340, Items.metaglass, 225));
            consumeItems(with(Items.lead, 6, Items.titanium, 3, Items.thorium, 5, TerraItems.carbon, 8));
            consumeLiquid(Liquids.cryofluid, 42f / 60f);
            consumePower(12.5f);
            outputItem = new ItemStack(TerraItems.darkSteel, 4);
            size = 5;
            hasPower = true;
            hasItems = true;
            hasLiquids = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidRegion(Liquids.cryofluid){{drawLiquidLight = true;}},
                new DrawArcSmelt() {{
                    x = y = -43f / 4f;
                    particleRad = 5.6f;
                    particleLife = 30f;
                    
                }},
                new DrawDefault(),
                new DrawTeamTop(),
                new DrawPistons() {{
                    suffix = "-cap";
                    sides = 1;
                    angleOffset = 0f;
                    sinMag = 0.3f;
                    sinScl = 0.5f;
                    lenOffset = 0f;
                }},
                new DrawWarmupRegion()
            );
            craftTime = 190;
            itemCapacity = 24;
            researchCostMultiplier = 0.25f;
            lightLiquid = Liquids.cryofluid;
        }};

        //production
        mechanicalWell = new AttributeSeparator("mechanical-well"){{
            requirements(Category.production, with(Items.graphite, 25, Items.titanium, 40));
            size = 2;
            craftTime = 135f;
            minEfficiency = 0.1f;
            baseEfficiency = 0f;
            updateEffect = Fx.pulverizeSmall;
            updateEffectChance = 0.02f;
            attribute = TerraAttributes.carbon;
            hasLiquids = false;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawRegion("-rotator"){{
                    spinSprite = true;
                    rotateSpeed = -1.25f;
                }},
                new DrawRegion("-rotator"){{
                    spinSprite = true;
                    rotateSpeed = 1.75f;
                }},
                new DrawDefault(),
                new DrawRegion("-top")
            );
            results = with(
                TerraItems.carbon, 17,
                Items.graphite, 3
            );
        }};

        electricalWell = new AttributeSeparator("electrical-well"){{
            requirements(Category.production, with(Items.graphite, 75, Items.thorium, 90, Items.silicon, 120));
            size = 3;
            itemCapacity = 30;
            craftTime = 35f;
            minEfficiency = 0.1f;
            baseEfficiency = 0f;
            updateEffect = Fx.pulverizeSmall;
            updateEffectChance = 0.02f;
            attribute = TerraAttributes.carbon;
            hasLiquids = false;
            boostScale = 4f / 9;
            consumePower(1.4f);
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawRegion("-rotator"){{
                    spinSprite = true;
                    rotateSpeed = -2f;
                }},
                new DrawRegion("-rotator"){{
                    spinSprite = true;
                    rotateSpeed = 2.75f;
                }},
                new DrawDefault(),
                new DrawRegion("-top")
            );
            results = with(
                TerraItems.carbon, 80,
                Items.graphite, 17,
                Items.thorium, 3
            );
        }};

        plasmaDrill = new SmartDrill("plasma-drill"){{
            requirements(Category.production, with(Items.graphite, 65, Items.titanium, 80, Items.silicon, 65, Items.metaglass, 32));
            size = 3;
            tier = 4;
            itemCapacity = 20;
            drillTime = 145f;
            hardnessDrillMultiplier = 25f;
            liquidBoostIntensity = 1.3f;
            canOverdrive = false;
            consumePower(1.85f);
            consumeLiquid(Liquids.cryofluid, 0.2f).boost();
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawDrillMineBeam(){{
                    laserScl = 0.3f;
                    particles = 10;
                    particleRad = 6f;
                    particleLen = 4f;
                }},
                new DrawRegion("-top"),
                new DrawTeamTop()
                //new DrawDrillOreTop()
            );
        }};

        beamMiningFacility = new SmartDrill("beam-mining-facility"){{
            requirements(Category.production, with(Items.phaseFabric, 65, TerraItems.darkSteel, 185, TerraItems.diamondDust, 110));
            size = 4;
            tier = 5;
            itemCapacity = 75;
            drillTime = 35f;
            hardnessDrillMultiplier = 4f;
            liquidBoostIntensity = 1f;
            canOverdrive = false;
            consumePower(5.25f);
            consumeLiquid(Liquids.cryofluid, 0.6f);
            updateEffect = new Effect(30f, e -> {
                Draw.color(e.color, Color.white, e.fout() * 0.66f);
                Draw.alpha(0.55f * e.fout() + 0.5f);
                Angles.randLenVectors(e.id, 2, 4f + e.finpow() * 17f, (x, y) -> {
                    Fill.square(e.x + x, e.y + y, e.fout() * new Rand(e.id).random(2.5f, 4), 45);
                });
            });
            updateEffectChance = 0.06f;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawDrillMineBeam(),
                new DrawRegion("-top"),
                new DrawTeamTop()
                //new DrawDrillOreTop()
            );
        }};

        //turrets
        flight = new ItemTurret("flight"){{
            requirements(Category.turret, with(Items.lead, 35, Items.graphite, 10));
            ammo(
                Items.lead,  new BulletType(){{
                    damage = speed = 0f;
                    ammoMultiplier = 2;
                    shootEffect = smokeEffect = Fx.none;
                    spawnUnit = new MissileUnitType("flight-lead-missile"){{
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
                }},
                Items.titanium, new BulletType(){{
                    damage = speed = 0f;
                    ammoMultiplier = 1;
                    reloadMultiplier = 0.4f;
                    shootEffect = smokeEffect = Fx.none;
                    spawnUnit = new MissileUnitType("flight-titanium-missile"){{
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
                }},
                Items.metaglass, new BulletType(){{
                    damage = speed = 0f;
                    ammoMultiplier = 1;
                    reloadMultiplier = 0.85f;
                    shootEffect = smokeEffect = Fx.none;
                    spawnUnit = new MissileUnitType("flight-metaglass-missile"){{
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
                }}
            );

            drawer = new DrawTurret(){{
                parts.add(new RegionPart("-missile")){{
                    progress = PartProgress.reload;
                    colorTo = mixColor = Color.valueOf("ffffff00");
                    color = Color.valueOf("ffffffff");
                    mixColorTo = Pal.stat;
                    outline = false;
                }};
            }};

            shootSound = Sounds.shootMissileSmall;
            shootY = 0f;
            reload = 50f;
            range = 160;
            shootCone = 15f;
            health = 200;
            rotateSpeed = 10f;
            coolant = consumeCoolant(0.1f);
            coolantMultiplier = 5f;
            researchCostMultiplier = 0.05f;
            depositCooldown = 2.0f;

            limitRange(5f);
        }};
        
        //units
        /*droneCentre = new DroneCentre("drone-centre"){{
            requirements(Category.units, with(Items.titanium, 135, Items.lead, 190, Items.silicon, 160));
            health = 480;
            size = 2;
        }};*/

        //other

        
        //NOTVA =---


        
        //GIER =---


        
        //TATNTROS =---


        
        //EREKIR & COPIS =---


        
        //OTHER =---


        
    }
}
