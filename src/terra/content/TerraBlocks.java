package terra.content;

import terra.type.bullet.*;
import terra.world.blocks.*;
import terra.world.blocks.multiblock.*;
import terra.world.drawer.*;
import terra.world.meta.*;
import terra.world.consumers.*;
import terra.util.*;
import arc.*;
import arc.util.*;
import arc.struct.*;
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
    scrapWallColossol, copperWallHuge, copperWallGigantic, 
    titaniumWallHuge, titaniumWallGigantic, plastaniumWallHuge, plastaniumWallGigantic, 
    thoriumWallHuge, thoriumWallGigantic, surgeWallHuge, surgeWallGigantic, 
    doorHuge, doorGigantic, phaseWallHuge, phaseWallGigantic,
    metaglassWall, metaglassWallLarge, metaglassWallHuge, metaglassWallGigantic,
    leadWall, leadWallLarge, leadWallHuge, leadWallGigantic,
    darkSteelWall, darkSteelWallLarge, darkSteelWallHuge, darkSteelWallSmall,
    //distrubution
    graphiteConveyor, 
    graphiteJunction, graphiteRouter, graphiteGate,
    graphiteBridge, smallDriver,
    //power
    photonPanel, photonPanelLarge,
    multicellBattery,
    sapt, antimatterCollider,
    //crafters
    sandExtractor, iceMelter, crystalIncubator,
    bisiliconOven, darkSteelWorkshop, titaniumPress, 
    diamondCrusher, diamondCoverer,
    multiMixer,
    //production
    graphiteMiner,
    mechanicalWell, electricalWell,
    pulseDrill, plasmaDrill, beamMiningFacility,
    //storage
    coreSolaris,
    //turrets
    flight, dynamics, electricShock, ejection, fracture, aircraft,
    //units
    basicAssembler, advancedAssembler, coreFactory, droneCentre, debugAssembler,
    largePayloadConveyor,
    //other
    //NOTVA =---
    //GIER =---
    //TATNTROS =---
    //EREKIR & COPIS =---
    //walls
    berylliumWallHuge, berylliumWallGigantic, reinforcedSurgeWallHuge, reinforcedSurgeWallGigantic,
    tungstenWallHuge, tungstenWallGigantic, blastGate, blastDoorLarge, blastDoorHuge,
    carbideWallHuge, carbideWallGigantic, shieldedWallLarge, shieldedWallHuge, shieldedWallSmall,
    //power
    beamBeacon, reinforcedPanel,
    //crafters
    inductionFurnace, hydrogenReductor,
    //turrets
    split,
    //units
    boatFabricator, basicFabricator, boatRefabricator, basicRefabricator, boatAssembler,
    largeReinforcedPayloadConveyor,
    //logic
    primeProcessor;
    //OTHER =---
    public static void load(){
        //VERILUS & SERPULO =---
        //walls
        int wallHealthMultiplier = 4;
        
        copperWallHuge = new Wall("copper-wall-huge"){{
            requirements(Category.defense, with(Items.copper, 6 * 9));
            health = 80 * 9 * wallHealthMultiplier;
            size = 3;
        }};
        copperWallGigantic = new Wall("copper-wall-gigantic"){{
            requirements(Category.defense, with(Items.copper, 6 * 15));
            health = 80 * 15 * wallHealthMultiplier;
            size = 4;
        }};
        leadWall = new AdvancedWall("lead-wall"){{
            requirements(Category.defense, ItemStack.with(Items.lead, 5));
            health = 60 * wallHealthMultiplier;
            envDisabled |= Env.scorching;

            hitBulletEffect = new ExplosionEffect(){{
                waveColor = Items.lead.color.cpy();
                smokeColor = Items.lead.color.cpy().a(0.4f);
                smokes = 18;
                sparks = 0;
                sparkStroke = 0f;
                waveLife = 12f;
                lifetime = 58f;
                waveRad = 4.5f * 8f;
                smokeRad = 4.2f * 8f;
                smokeSize = 8f;
            }};
            hitBullet = new BulletType(){{
                damage = 0f;
                lifetime = 50f;
                speed = 0f;
                despawnEffect = Fx.none;
                intervalBullets = 1;
                bulletInterval = 1f;
                intervalBullet = BulletType(){{
                    damage = splashDamage = 0f;
                    splashDamageRadius = 4 * 8f;
                    
                }};
            }};
            hitBulletAmount = 1;
            hitBulletAmountRand = 0;
            hitBulletSpawnChance = 0f;
        }};
        leadWallLarge = new AdvancedWall("lead-wall-large"){{
            requirements(Category.defense, ItemStack.with(Items.lead, 20));
            health = 60 * wallHealthMultiplier * 4;
            size = 2;
            envDisabled |= Env.scorching;
        }};
        leadWallHuge = new AdvancedWall("lead-wall-huge"){{
            requirements(Category.defense, ItemStack.with(Items.lead, 45));
            health = 60 * wallHealthMultiplier * 9;
            size = 3;
            envDisabled |= Env.scorching;
        }};
        leadWallGigantic = new AdvancedWall("lead-wall-gigantic"){{
            requirements(Category.defense, ItemStack.with(Items.lead, 80));
            health = 60 * wallHealthMultiplier * 16;
            size = 4;
            envDisabled |= Env.scorching;
        }};
        titaniumWallHuge = new Wall("titanium-wall-huge"){{
            requirements(Category.defense, with(Items.titanium, 6 * 9));
            health = 110 * 9 * wallHealthMultiplier;
            size = 3;
        }};
        titaniumWallGigantic = new Wall("titanium-wall-gigantic"){{
            requirements(Category.defense, with(Items.titanium, 6 * 15));
            health = 110 * 15 * wallHealthMultiplier;
            size = 4;
        }};
        plastaniumWallHuge = new Wall("plastanium-wall-huge"){{
            requirements(Category.defense, with(Items.plastanium, 5 * 9, Items.metaglass, 2 * 9));
            health = 125 * wallHealthMultiplier * 9;
            size = 3;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
        plastaniumWallGigantic = new Wall("plastanium-wall-gigantic"){{
            requirements(Category.defense, with(Items.plastanium, 5 * 15, Items.metaglass, 2 * 15));
            health = 125 * wallHealthMultiplier * 15;
            size = 4;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
        thoriumWallHuge = new Wall("thorium-wall-huge"){{
            requirements(Category.defense, with(Items.thorium, 6 * 9));
            health = 200 * 9 * wallHealthMultiplier;
            size = 3;
        }};
        thoriumWallGigantic = new Wall("thorium-wall-gigantic"){{
            requirements(Category.defense, with(Items.thorium, 6 * 15));
            health = 200 * 15 * wallHealthMultiplier;
            size = 4;
        }};
        phaseWallHuge = new Wall("phase-wall-huge"){{
            requirements(Category.defense, with(Items.phaseFabric, 6 * 9));
            health = 150 * 9 * wallHealthMultiplier;
            size = 3;
            chanceDeflect = 10f;
            flashHit = true;
        }};
        phaseWallGigantic = new Wall("phase-wall-gigantic"){{
            requirements(Category.defense, with(Items.phaseFabric, 6 * 15));
            health = 150 * 15 * wallHealthMultiplier;
            size = 4;
            chanceDeflect = 10f;
            flashHit = true;
        }};
        surgeWallHuge = new Wall("surge-wall-huge"){{
            requirements(Category.defense, with(Items.surgeAlloy, 6 * 9));
            health = 230 * 9 * wallHealthMultiplier;
            size = 3;
            lightningChance = 0.05f;
        }};
        surgeWallGigantic = new Wall("surge-wall-gigantic"){{
            requirements(Category.defense, with(Items.surgeAlloy, 6 * 15));
            health = 230 * 15 * wallHealthMultiplier;
            size = 4;
            lightningChance = 0.05f;
        }};
        doorHuge = new Door("door-huge"){{
            requirements(Category.defense, with(Items.titanium, 6 * 9, Items.silicon, 4 * 9));
            openfx = TerraFx.dooropenHuge;
            closefx = TerraFx.doorcloseHuge;
            health = 100 * 9 * wallHealthMultiplier;
            size = 3;
        }};
        doorGigantic = new Door("door-gigantic"){{
            requirements(Category.defense, with(Items.titanium, 6 * 16, Items.silicon, 4 * 16));
            openfx = TerraFx.dooropenGiga;
            closefx = TerraFx.doorcloseGiga;
            health = 100 * 16 * wallHealthMultiplier;
            size = 4;
        }};
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
        metaglassWallGigantic = new AdvancedWall("metaglass-wall-gigantic"){{
            requirements(Category.defense, with(Items.metaglass, 6 * 15));
            health = 380 * 15;
            size = 4;
            hitBullet = new BasicBulletType(){{
                damage = 21f;
                lifetime = 35f;
                width = 14f;
                height = 26f;
                shrinkY = 0.4f;
                shrinkX = 1f;
                speed = 2f;
                backColor = hitColor = trailColor = Pal.glassAmmoBack;
                frontColor = Pal.glassAmmoFront;
                despawnEffect = Fx.none;
                ammoMultiplier = 1f;
            }};
            hitBulletAmount = 15;
            hitBulletAmountRand = 3;
            hitBulletSpawnChance = 0.12f;
            hitBulletEffect = new WaveEffect(){{
                sizeFrom = strokeTo = 0f;
                sizeTo = 50f;
                strokeFrom = 3.5f;
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
            requirements(Category.defense, with(TerraItems.darkSteel, 6 * 4, TerraItems.diamondGlass, (6 * 4) / 2));
            health = 930 * 4;
            size = 2;
            absorbLasers = true;
            autoRegeneration = true;
            regenAmount = 0.012f / 60f;
            regenStartDelay = 150f;
            regenDamageStop = true;
        }};
        darkSteelWallLarge = new AdvancedWall("dark-steel-wall-large"){{
            requirements(Category.defense, with(TerraItems.darkSteel, 6 * 9, TerraItems.diamondGlass, (6 * 9) / 2));
            health = 930 * 9;
            size = 3;
            absorbLasers = true;
            autoRegeneration = true;
            regenAmount = 0.012f / 60f;
            regenStartDelay = 150f;
            regenDamageStop = true;
        }};

        //distrubution
        graphiteConveyor = new CappedConveyor("graphite-conveyor"){{
            requirements(Category.distribution, with(Items.lead, 1, Items.graphite, 1));
            health = 85;
            speed = 0.042f;
            displayedSpeed = 6f;
            buildCostMultiplier = 2f;
            pushUnits = false;
            researchCost = with(Items.lead, 20, Items.graphite, 20);
        }};
        graphiteJunction = new Junction("graphite-junction"){{
            requirements(Category.distribution, with(Items.lead, 3, Items.graphite, 2));
            speed = 26;
            capacity = 6;
            health = 70;
            buildCostMultiplier = 6f;
        }};
        graphiteRouter = new Router("graphite-router"){{
            requirements(Category.distribution, with(Items.lead, 3, Items.graphite, 2));
            buildCostMultiplier = 4f;
            health = 85;
        }};
        graphiteGate = new OverflowGate("graphite-gate"){{
            requirements(Category.distribution, with(Items.lead, 3, Items.graphite, 2));
            buildCostMultiplier = 3f;
            health = 85;
        }};
        graphiteBridge = new BufferedItemBridge("graphite-bridge"){{
            requirements(Category.distribution, with(Items.lead, 6, Items.graphite, 8, Items.titanium, 3));
            fadeIn = moveArrows = false;
            range = 6;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 16;
            crushFragile = true;
        }};
        smallDriver = new MassDriver("small-driver"){{
            requirements(Category.distribution, with(Items.titanium, 50, Items.graphite, 85, Items.lead, 100));
            size = 2;
            itemCapacity = 65;
            reload = 340f;
            range = 260f;
            hasPower = false;
            health = 485;
        }};
        
        //power
        photonPanel = new SolarGenerator("photon-solar-panel"){{
            requirements(Category.power, with(Items.lead, 35, Items.silicon, 40, TerraItems.diamondGlass, 15));
            size = 2;
            powerProduction = 0.88f;
            drawer = new DrawMulti(
                new DrawVariantRegion("-bottom", 3),
                new DrawVariantRegion("-shine", 4, 2),
                new DrawRegion("-top")
            );
        }};
        photonPanelLarge = new SolarGenerator("photon-solar-panel-large"){{
            requirements(Category.power, with(TerraItems.titaniumPlate, 80, TerraItems.darkSteel, 65, TerraItems.diamondGlass, 50));
            size = 4;
            powerProduction = 5.6f;
            drawer = new DrawMulti(
                new DrawVariantRegion("-bottom", 3),
                new DrawVariantRegion("-shine", 4, 2),
                new DrawRegion("-top")
            );
        }};
        multicellBattery = new Battery("multi-cell-battery"){{
            requirements(Category.power, with(Items.titanium, 15, Items.lead, 35));
            size = 2;
            consumePowerBuffered(20000f);
            baseExplosiveness = 3f;
        }};
        sapt = new ConsumeGenerator("sapt"){{ //A setup for an experiment on extraterrestrial artificial photosynthesis technology > S.A.P.T
            requirements(Category.power, with(Items.lead, 90, Items.titanium, 150, Items.silicon, 65, Items.metaglass, 50));
            powerProduction = 8f;
            hasLiquids = true;
            hasItems = false;
            squareSprite = false;
            consumesPower = true;
            size = 3;
            ambientSound = Sounds.loopDifferential;
            ambientSoundVolume = 0.12f;
            liquidCapacity = 30f * 4;

            drawer = new DrawMulti(
                new DrawRegion("-bottom"), 
                new DrawLiquidTile(Liquids.water){{padRight = 12f; padLeft = 2f;}},
                new DrawLiquidTile(TerraLiquids.carbonDioxide){{padRight = 2f; padLeft = 12f;}},
                //new DrawRegion("-rot", 5f, true),
                new DrawDefault()
            );

            consumePower(1.4f);
            consumeLiquids(LiquidStack.with(Liquids.water, 15f / 60f, TerraLiquids.carbonDioxide, 30f / 60f));
        }};
        antimatterCollider = new ImpactCollider("antimatter-collider"){{
            requirements(Category.power, with(Items.lead, 3000, Items.thorium, 1280, TerraItems.diamondGlass, 880, TerraItems.darkSteel, 2200, TerraItems.thermoxite, 700));
            size = 7;
            researchCostMultiplier = 0.1f;
            squareSprite = false;
            health = 7850;
            powerProduction = 325f;
            itemDuration = 20f;
            ambientSound = Sounds.loopPulse;
            ambientSoundVolume = 0.17f;
            liquidCapacity = 10000f;
            itemCapacity = 40;
            outputLiquid = new LiquidStack(TerraLiquids.fissilePlasma, 1150f / 60);
            explodeOnFull = true;
            explosionShake = 26f;
            explosionShakeDuration = 240f;
            explosionRadius = 23;
            explosionDamage = 8400;

            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawPlasma(){{
                    plasma1 = Color.valueOf("bfb1ff");
                    plasma2 = Color.valueOf("836cf4");
                }},
                new DrawRegion("-mid"),
                new DrawLiquidTile(Liquids.cryofluid, 18),
                new DrawGlowRegion(-1f){{
                    suffix = "-rot";
                    glowIntensity = 0.3f;
                    rotateSpeed = 9f;
                    alpha = 0.4f;
                    color = new Color(0.8f, 0.8f, 1f, 1f);
                }},
                new DrawDefault(),
                new DrawGlowRegion("-glow"){{
                    color = Color.valueOf("70170b");
                    alpha = 0.7f;
                }}
            );

            consumePower(38f);
            consumeLiquid(Liquids.cryofluid, 500.0001f / 60);
            ObjectFloatMap<Item> fuelMap = new ObjectFloatMap<>();
                fuelMap.put(Items.thorium, 0.75f);
                fuelMap.put(TerraItems.rawThermoxite, 0.75f);
                fuelMap.put(Items.phaseFabric, 0.85f);
                fuelMap.put(TerraItems.thermoxite, 1f);
                fuelMap.put(Items.fissileMatter, 1f);
                fuelMap.put(TerraItems.gammaCell, 1.5f);
            consume(new ConsumeItemEfficiencyList(fuelMap));
        }};
        
        //crafters
        sandExtractor = new AttributeCrafter("sand-extractor"){{
            requirements(Category.crafting, with(Items.graphite, 20, Items.titanium, 35));
            outputItem = new ItemStack(Items.sand, 2);
            size = 2;
            hasPower = false;
            hasItems = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"), 
                new DrawRegion("-rotator1", 4.2f, true) {{
                    x = y = 3f;
                }},
                new DrawRegion("-rotator2", 2.6f, true) {{
                    x = 4f;
                    y = -4f;
                }},
                new DrawRegion("-rotator3", -1.8f, true) {{
                    x = y = -3.5f;
                }},
                new DrawDefault()
            );
            craftTime = 110;
            itemCapacity = 20;
            updateEffect = Fx.pulverizeSmall;
            updateEffectChance = 0.03f;
            attribute = Attribute.sand;
            maxBoost = 4f;
            minEfficiency = 0.1f;
            baseEfficiency = 0f;
        }
            public TextureRegion fullRegion;
                                                                                 
            @Override
            public void load() {
                super.load();
                this.fullRegion = Core.atlas.find(this.name + "-full");
            }
        
            @Override
            public TextureRegion[] icons() {
                return new TextureRegion[]{this.fullRegion};
            }
        
            @Override
            public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
                Draw.rect(this.fullRegion, plan.drawx(), plan.drawy());
            }
        };
        iceMelter = new AttributeCrafter("ice-melter"){{
            requirements(Category.crafting, with(Items.graphite, 25, Items.titanium, 30, Items.silicon, 45));
            outputLiquid = new LiquidStack(Liquids.water, 0.2f);
            liquidCapacity = 36;
            consumePower(0.4f);
            size = 2;
            hasPower = true;
            hasLiquids = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"), 
                new DrawLiquidTile(Liquids.water),
                new DrawCultivator() {{
                    plantColor = Color.valueOf("596ab866");
                    plantColorLight = Color.valueOf("7090ea");
                    bottomColor = Color.valueOf("596ab800");
                    timeScl = 25;
                    strokeMin = 0f;
                    radius = 2f;
                    spread = recurrence = 3.5f;
                    sides = 75;
                    bubbles = 18;
                }},
                new DrawDefault(),
                new DrawRegion("-top")
            );
            attribute = TerraAttributes.ice;
            minEfficiency = 0.1f;
            baseEfficiency = 0f;
        }};
        bisiliconOven = new AttributeCrafter("bisilicon-oven"){{
            requirements(Category.crafting, with(Items.lead, 485, Items.graphite, 275, Items.titanium, 360));
            consumeItems(with(Items.lead, 15, Items.sand, 23, TerraItems.carbon, 18));
            consumePower(8.25f);
            outputItems = with(Items.silicon, 10, Items.metaglass, 7);
            outputLiquid = new LiquidStack(TerraLiquids.carbonDioxide, 18 / 60f);
            size = 4;
            hasPower = true;
            hasItems = true;
            hasLiquids = true;
            ignoreLiquidFullness = true;
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
                new DrawLiquidRegion(TerraLiquids.carbonDioxide),
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
            liquidCapacity = 80;
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
                    if (this.liquids.get(TerraLiquids.carbonDioxide) >= this.block.liquidCapacity - 0.001f && this.efficiency > 0 && Mathf.chanceDelta(0.8f * this.efficiency * warmup)) {
                        float rand1 = Mathf.range(2f), rand2 = Mathf.range(2f);
                        TerraFx.arcSmoke.at(x - 7.5f - rand1, y + 0.5f + rand1);
                        TerraFx.arcSmoke.at(x - 8.5f - rand2, y - 8.5f + rand2);
                    }
                }
            };
        }                                                
            public TextureRegion fullRegion;
                                                                                 
            @Override
            public void load() {
                super.load();
                this.fullRegion = Core.atlas.find(this.name + "-full");
            }
        
            @Override
            public TextureRegion[] icons() {
                return new TextureRegion[]{this.fullRegion};
            }
        
            @Override
            public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
                Draw.rect(this.fullRegion, plan.drawx(), plan.drawy());
            }
        };

        darkSteelWorkshop = new GenericCrafter("dark-steel-production-workshop"){{
            requirements(Category.crafting, with(Items.thorium, 600, Items.silicon, 385, TerraItems.titaniumPlate, 340, Items.metaglass, 225, TerraItems.rawThermoxite, 180));
            consumeItems(with(Items.lead, 6, Items.titanium, 3, Items.thorium, 5, TerraItems.carbon, 8));
            consumeLiquid(Liquids.cryofluid, 42f / 60f);
            consumePower(12.5f);
            outputItem = new ItemStack(TerraItems.darkSteel, 7);
            size = 5;
            hasPower = true;
            hasItems = true;
            hasLiquids = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                //new DrawLiquidRegion(Liquids.cryofluid){{drawLiquidLight = true;}},
                new DrawLiquidTile(Liquids.cryofluid){{drawLiquidLight = true; padRight = 2f; padLeft = 24f; padTop = 2f; padBottom = 24f;}},
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
        }
            public TextureRegion fullRegion;
                                                                                 
            @Override
            public void load() {
                super.load();
                this.fullRegion = Core.atlas.find(this.name + "-full");
            }
        
            @Override
            public TextureRegion[] icons() {
                return new TextureRegion[]{this.fullRegion};
            }
        
            @Override
            public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
                Draw.rect(this.fullRegion, plan.drawx(), plan.drawy());
            }
        };

        crystalIncubator = new RecipeGenericCrafter("crystal-incubator") {{
            requirements(Category.crafting, ItemStack.with(Items.phaseFabric, 80, TerraItems.rawThermoxite, 40, TerraItems.darkSteel, 115));

            size = 2;
            hasLiquids = true;
            hasItems = true;
            hasPower = true;
            scaledHealth = 100f;
            itemCapacity = 10;
            liquidCapacity = 100;

            consumePower(1.3f);
            recipes.addAll(
                new Recipe() {{
                    inputItem = ItemStack.list(TerraItems.carbon, 1);
                    inputLiquid = LiquidStack.list(Liquids.water, 6f / 60f);
                    outputItem = ItemStack.list(TerraItems.rawThermoxite, 1);
                    craftTime = 40f;
                }},
                new Recipe() {{
                    inputItem = ItemStack.list(TerraItems.carbon, 2);
                    inputLiquid = LiquidStack.list(Liquids.cryofluid, 6f / 60f);
                    outputItem = ItemStack.list(TerraItems.thermoxite, 1);
                    craftTime = 100f;
                }}
            );
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                //TODO: effects
                new DrawRegion("-top")
            );
        }};

        titaniumPress = new GenericCrafter("titanium-press"){{
            requirements(Category.crafting, with(Items.titanium, 180, Items.silicon, 45, Items.lead, 100, Items.graphite, 50));

            craftEffect = Fx.pulverizeMedium;
            outputItem = new ItemStack(TerraItems.titaniumPlate, 3);
            craftTime = 40f;
            itemCapacity = 15;
            size = 3;
            hasItems = true;
            hasLiquids = true;
            hasPower = true;
            squareSprite = false;

            consumePower(2.2f);
            consumeItems(with(Items.silicon, 1, Items.titanium, 3));
            consumeLiquid(Liquids.water, 0.3f);
        }};
        diamondCrusher = new RecipeGenericCrafter("diamond-crusher") {{
            requirements(Category.crafting, ItemStack.with(Items.silicon, 175, TerraItems.titaniumPlate, 90, Items.lead, 225, Items.thorium, 145));
            //addLink(2, 0, 1, 2, 1, 1, 0, 2, 1, 1, 2, 1, -1, 0, 1, -1, 1, 1, 0, -1, 1, 1, -1, 1);

            size = 2;
            hasLiquids = true;
            hasItems = true;
            hasPower = true;
            scaledHealth = 100f;
            itemCapacity = 20;
            liquidCapacity = 50;
            squareSprite = false;

            consumePower(3.333f);
            recipes.addAll(
                new Recipe() {{
                    inputItem = ItemStack.list(TerraItems.carbon, 4);
                    inputLiquid = LiquidStack.list(Liquids.water, 16f / 60f);
                    outputItem = ItemStack.list(TerraItems.diamondDust, 1);
                    craftTime = 40f;
                }},
                new Recipe() {{
                    inputItem = ItemStack.list(Items.graphite, 6);
                    inputLiquid = LiquidStack.list(Liquids.water, 12f / 60f);
                    outputItem = ItemStack.list(TerraItems.diamondDust, 3);
                    craftTime = 90f;
                }}
            );
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawPistons() {{
                    suffix = "-piston-1";
                    sides = 1;
                    angleOffset = 90f;
                    sinMag = 1f;
                    sinScl = 11f;
                    sinOffset = 0f;
                    lenOffset = 0f;
                }},
                new DrawPistons() {{
                    suffix = "-piston-2";
                    sides = 1;
                    angleOffset = 180f;
                    sinMag = 1f;
                    sinScl = 11f;
                    sinOffset = 0f;
                    lenOffset = 0f;
                }},
                new DrawPistons() {{
                    suffix = "-piston-3";
                    sides = 1;
                    angleOffset = 270f;
                    sinMag = 1f;
                    sinScl = 11f;
                    sinOffset = 0f;
                    lenOffset = 0f;
                }},
                new DrawPistons() {{
                    suffix = "-piston-4";
                    sides = 1;
                    sinMag = 1f;
                    sinScl = 11f;
                    sinOffset = 0f;
                    lenOffset = 0f;
                }},
                new DrawRegion("-top")
            );
        }
            public TextureRegion fullRegion;
                                                                                 
            @Override
            public void load() {
                super.load();
                this.fullRegion = Core.atlas.find(this.name);
            }
        
            @Override
            public TextureRegion[] icons() {
                return new TextureRegion[]{this.fullRegion};
            }
        
            @Override
            public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
                Draw.rect(this.fullRegion, plan.drawx(), plan.drawy());
            }
        };
        diamondCoverer = new GenericCrafter("diamond-coverer"){{
            requirements(Category.crafting, with(Items.silicon, 80, TerraItems.titaniumPlate, 145, TerraItems.diamondDust, 65));

            outputItem = new ItemStack(TerraItems.diamondGlass, 1);
            craftTime = 35f;
            itemCapacity = 15;
            size = 2;
            hasItems = true;
            hasLiquids = true;
            hasPower = true;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water), new DrawDefault());

            consumePower(1.3f);
            consumeItems(with(Items.metaglass, 1, TerraItems.diamondDust, 3));
            consumeLiquid(Liquids.water, 0.2f);
        }};
        multiMixer = new MultiBlockCrafter("multi-mixer") {{
            requirements(Category.crafting, ItemStack.with(Items.phaseFabric, 75, TerraItems.titaniumPlate, 180, Items.metaglass, 225));
            addLink(2, 0, 1, 2, 1, 1);

            size = 2;
            hasLiquids = true;
            hasItems = true;
            scaledHealth = 100f;
            itemCapacity = 20;
            liquidCapacity = 200;
            craftTime = 60f;


            consumePower(3.4f);
            consumeItems(ItemStack.with(Items.titanium, 4, TerraItems.diamondDust, 1)); consumeLiquids(LiquidStack.with(Liquids.water, 80 / 60f));
            outputLiquid = new LiquidStack(Liquids.cryofluid, 80 / 60f);
            lightLiquid = Liquids.cryofluid;

            drawer = new DrawMulti(
                new DrawRotation(){{
                    suffix = "-bottom";
                    xOffset = 4f;
                    //layer = Layer.block - 0.001f;
                }},
                new DrawLiquidTileRotated(Liquids.water),
                new DrawLiquidTileRotated(Liquids.water, 16f, 0f){{padRight = 8f;}},
                new DrawLiquidTileRotated(Liquids.cryofluid){{drawLiquidLight = true;}},
                new DrawLiquidTileRotated(Liquids.cryofluid, 16f, 0f){{padRight = 8f; drawLiquidLight = true;}},
                new DrawRotation() {{
                    suffix = "-top-rot";
                    xOffset = 4f;
                    drawType = DRAW_FULL;
                }}
            );

            enableRotate();
        }};

        //production
        graphiteMiner = new WallCrafter("graphite-miner"){{
            requirements(Category.production, with(Items.lead, 12));
            output = Items.graphite;
            size = 2;
            envEnabled = Env.any;
            drillTime = 270f;
            fogRadius = 2;
            ambientSound = Sounds.loopDrill;
            ambientSoundVolume = 0.04f;
            itemCapacity = 20;
            attribute = TerraAttributes.graphite;
        }};
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

        pulseDrill = new BurstDrill("pulse-drill"){{
            requirements(Category.production, with(Items.graphite, 22, Items.lead, 18));
            size = 2;
            tier = 3;
            itemCapacity = 20;
            liquidBoostIntensity = 1.75f;
            consumeLiquid(Liquids.water, 0.075f).boost();
            drillTime = 320f;
            drillMultipliers.put(Items.titanium, 0.7f);
            drillEffect = new MultiEffect(Fx.mine, Fx.drillSteam, new WaveEffect(){{
                sizeFrom = strokeTo = 0f;
                sizeTo = 40f;
                strokeFrom = 3f;
                lifetime = 20f;
                colorFrom = Color.valueOf("ffffff");
                colorTo = Color.valueOf("ffffff00");
            }});
            buildType = () -> new BurstDrillBuild() {
                @Override
                public void draw() {
                    float dTime = getDrillTime(this.dominantItem);
                    float p = Mathf.clamp(this.progress / dTime);
                    float s = Math.max(1.0f, 1.0f + (p * 0.3f));
        
                    Draw.z(Layer.block - 0.03f);
                    Draw.rect(region, this.x, this.y);
                    if (this.dominantItem != null && itemRegion.found()) {
                        Draw.z(Layer.block - 0.02f);
                        Draw.color(this.dominantItem.color);
                        Draw.rect(itemRegion, this.x, this.y);
                        Draw.color();
                    }
                    this.drawDefaultCracks();
            
                    Draw.z(Layer.block - 0.01f);
                    if (topRegion.found()) {
                        Draw.rect(topRegion, this.x, this.y, topRegion.width * s * Draw.scl, topRegion.height * s * Draw.scl);
                    }
            
                    if (this.dominantItem != null && itemTopRegion.found()) {
                        Draw.z(Layer.block + 0.01f);
                        Draw.color(this.dominantItem.color);
                        Draw.rect(itemTopRegion, this.x, this.y, itemTopRegion.width * s * Draw.scl, itemTopRegion.height * s * Draw.scl);
                        Draw.color();
                    }
                }
            };
        }
            public TextureRegion itemTopRegion;
            @Override
            public void load() {
                super.load();
                itemTopRegion = Core.atlas.find(name + "-top-item");
            }
        };

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
            requirements(Category.production, with(Items.phaseFabric, 65, TerraItems.darkSteel, 185, TerraItems.diamondGlass, 110));
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

        //storage
        coreSolaris = new SolarGeneratorCore("core-solaris"){{
            requirements(Category.effect, with(Items.metaglass, 2500, Items.lead, 5000, Items.silicon, 6000, Items.titanium, 5500, Items.phaseFabric, 3000));
            //alwaysUnlocked = true;
            unitType = TerraUnitTypes.tau;
            health = 3800;
            itemCapacity = 7500;
            size = 4;
            thrusterLength = 34/4f;

            unitCapModifier = 20;
            researchCostMultiplier = 0.07f;

            baseExplosiveness = 10;
            powerProduction = 2.8f;
        }
            public TextureRegion fullRegion;
                                                                                 
            @Override
            public void load() {
                super.load();
                this.fullRegion = Core.atlas.find(this.name + "-full");
            }
        
            @Override
            public TextureRegion[] icons() {
                return new TextureRegion[]{this.fullRegion};
            }
        };

        //turrets
        flight = new ItemTurret("flight"){{
            requirements(Category.turret, with(Items.lead, 35, Items.graphite, 10));
            predictTarget = false;
            ammo(
                Items.lead,  new BulletType(){{
                    damage = speed = 0f;
                    ammoMultiplier = 2;
                    shootEffect = smokeEffect = Fx.none;
                    spawnUnit = TerraUnitTypes.flightLeadMissile;
                }},
                Items.titanium, new BulletType(){{
                    damage = speed = 0f;
                    ammoMultiplier = 1;
                    reloadMultiplier = 0.4f;
                    shootEffect = smokeEffect = Fx.none;
                    spawnUnit = TerraUnitTypes.flightTitaniumMissile;
                }},
                Items.metaglass, new BulletType(){{
                    damage = speed = 0f;
                    ammoMultiplier = 1;
                    reloadMultiplier = 0.85f;
                    shootEffect = smokeEffect = Fx.none;
                    spawnUnit = TerraUnitTypes.flightMetaglassMissile;
                }}
            );

            drawer = new DrawTurret(){{
                parts.add(new RegionPart(""){{
                    progress = PartProgress.constant(0f);
                    color = colorTo = mixColor = mixColorTo = new Color(1f, 1f, 1f, 0f);
                    outline = false;
                    layerOffset = -60f;
                }});
                setAmmoParts(
                    Items.lead, Seq.with(new RegionPart("-missile"){{
                        progress = PartProgress.reload;
                        colorTo = new Color(1f, 1f, 1f, 0f);
                        color = Color.white;
                        mixColorTo = Pal.accent;
                        mixColor = new Color(1f, 1f, 1f, 0f);
                        outline = false;
                    }}),
                    Items.titanium, Seq.with(new RegionPart("-missile-titanium"){{
                        progress = PartProgress.reload;
                        colorTo = new Color(1f, 1f, 1f, 0f);
                        color = Color.white;
                        mixColorTo = Pal.accent;
                        mixColor = new Color(1f, 1f, 1f, 0f);
                        outline = false;
                    }}),
                    Items.metaglass, Seq.with(new RegionPart("-missile-metaglass"){{
                        progress = PartProgress.reload;
                        colorTo = new Color(1f, 1f, 1f, 0f);
                        color = Color.white;
                        mixColorTo = Pal.accent;
                        mixColor = new Color(1f, 1f, 1f, 0f);
                        outline = false;
                    }})
                );
            }};

            shootSound = Sounds.shootMissileSmall;
            shootY = 0f;
            reload = 50f;
            range = 160;
            shootCone = 15f;
            health = 200;
            rotateSpeed = 10f;
            maxAmmo = 6;
            coolant = consumeCoolant(0.1f);
            coolantMultiplier = 5f;
            researchCostMultiplier = 0.05f;
            depositCooldown = 2.0f;

            limitRange(5f);
        }};

        dynamics = new ItemTurret("dynamics"){{
            requirements(Category.turret, with(Items.titanium, 55, Items.graphite, 22));
            ammo(
                Items.lead, new ShrapnelBulletType(){{
                    length = 70;
                    damage = 17f;
                    ammoMultiplier = 5f;
                    armorMultiplier = 1.2f;
                    width = 9f;
                    reloadMultiplier = 1f;
                    toColor = hitColor = Color.valueOf("ab99d3");
                    shootEffect = smokeEffect = TerraFx.fuseShoot;
                }},
                Items.titanium, new ShrapnelBulletType(){{
                    length = 70;
                    damage = 24f;
                    ammoMultiplier = 3f;
                    armorMultiplier = 0.8f;
                    width = 9f;
                    reloadMultiplier = 0.85f;
                }},
                TerraItems.rawThermoxite, new ShrapnelBulletType(){{
                    rangeChange = 12;
                    length = 70 + rangeChange;
                    damage = 41f;
                    ammoMultiplier = 2f;
                    armorMultiplier = 0.5f;
                    width = 9f;
                    reloadMultiplier = 0.7f;
                    toColor = hitColor = Color.valueOf("ff7163");
                    shootEffect = smokeEffect = TerraFx.fuseShoot;
                }}
            );

            shootSound = TerraSounds.shootHeavy;
            shootSoundVolume = 0.3f;
            shoot = new ShootSpread(2, 12f);
            reload = 72f;
            /*minFiringSpeed = 0.3f;
            windupSpeed = 0.0017f;
            windDownSpeed = 0.0042f;
            logicSpeedScl = 0.4f;
            maxSpeed = 2.25f;*/
            range = 60;
            shootCone = 15f;
            health = 400;
            rotateSpeed = 10f;
            maxAmmo = 10;
            coolant = consumeCoolant(0.25f);
            depositCooldown = 1.0f;
        }};

        electricShock = new PowerTurret("electric-shock"){{
            requirements(Category.turret, with(Items.lead, 50, Items.metaglass, 40, Items.titanium, 30));
            range = 95f;
            shootY = 2f;
            recoil = 2f;
            reload = 52f;
            cooldownTime = reload * 0.8f;
            shake = 1.4f;
            shootEffect = Fx.lancerLaserShoot;
            smokeEffect = Fx.none;
            heatColor = Color.red;
            size = 1;
            scaledHealth = 240;
            coolant = consumeCoolant(0.2f);

            consumePower(3.7f);

            drawer = new DrawTurret(){{
                parts.add(new RegionPart("-part"){{
                    progress = PartProgress.reload;
                    moveX = 0.5f;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.warmup, 0f, -0.5f, 0f));
                }});
            }};

            shootType = new LaserBulletType(78){{
                colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};

                buildingDamageMultiplier = 0.25f;
                armorMultiplier = 3f;
                hitEffect = Fx.hitLancer;
                hitSize = 3;
                lifetime = 16f;
                drawSize = 300f;
                collidesAir = true;
                length = 103f;
                ammoMultiplier = 1f;
                pierceCap = 4;
                despawnSound = Sounds.shootArc;
                fragBullets = 2;
                fragRandomSpread = 0f;
                fragOnHit = false;
                fragBullet = new LightningBulletType(){{
                    damage = 20;
                    lightningLength = 11;
                    lightningLengthRand = 2;
                    collidesAir = true;
                    ammoMultiplier = 1f;
    
                    //for visual stats only.
                    buildingDamageMultiplier = 0.25f;
    
                    lightningType = new BulletType(0.0001f, 0f){{
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.shocked;
                        hittable = false;
                        lightColor = Color.white;
                        collidesAir = true;
                        buildingDamageMultiplier = 0.25f;
                        shieldDamageMultiplier = 0.2f;
                    }};
                }};
            }};
        }};

        fracture = new ItemTurret("fracture"){{
            requirements(Category.turret, with(Items.lead, 100, Items.silicon, 120, TerraItems.titaniumPlate, 85));
            ammo(
                Items.graphite, new BasicBulletType(3.5f, 22){{
                    width = 7.5f;
                    height = 11f;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    hitColor = backColor = trailColor = Pal.graphiteAmmoBack;
                    frontColor = Pal.graphiteAmmoFront;

                    ammoMultiplier = 5;
                    armorMultiplier = 0.8f;

                    splashDamage = 15f;
                    splashDamageRadius = 22f;
                    lifetime = 70f;
                }},
                Items.thorium, new BasicBulletType(4f, 28, "bullet"){{
                    width = 8f;
                    height = 13f;
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootBigSmoke;
                    reloadMultiplier = 1.1f;
                    ammoMultiplier = 4;
                    splashDamage = 17f;
                    splashDamageRadius = 27f;
                    lifetime = 70f;
                    armorMultiplier = 0.65f;

                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    backColor = hitColor = trailColor = Pal.thoriumAmmoBack;
                    frontColor = Pal.thoriumAmmoFront;
                }},
                TerraItems.darkSteel, new BasicBulletType(3.8f, 47, "bullet"){{
                    width = 8.5f;
                    height = 15f;
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootBigSmoke;
                    reloadMultiplier = 0.8f;
                    ammoMultiplier = 3;
                    splashDamage = 26f;
                    splashDamageRadius = 24f;
                    lifetime = 70f;
                    armorMultiplier = 0.3f;

                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    backColor = hitColor = trailColor = Pal.darkMetal;
                    frontColor = Color.white;
                }}
            );

            recoils = 2;
            drawer = new DrawTurret(){{
                for(int i = 0; i < 2; i ++){
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")){{
                        progress = PartProgress.recoil;
                        recoilIndex = f;
                        moveY = -1.5f;
                    }});
                }
                parts.add(new RegionPart("-side"){{
                    progress = PartProgress.recoil;
                    moveY = 0.6f;
                    mirror = under = true;
                }});
            }};

            size = 2;
            range = 210f;
            reload = 33f;
            consumeAmmoOnce = false;
            ammoEjectBack = 1.2f;
            recoil = 0.5f;
            shake = 1f;
            shoot = new ShootMulti(new ShootAlternate(5f), new ShootPattern() {{
                shots = 2;
                shotDelay = 4.5f;
            }});

            ammoUseEffect = Fx.casing2;
            scaledHealth = 240;
            shootSound = Sounds.shootSalvo;

            limitRange();
            coolant = consumeCoolant(0.2f);
            depositCooldown = 2.0f;
        }};

        ejection = new SpeedupEnergyTurret("ejection"){{
            requirements(Category.turret, with(Items.titanium, 280, TerraItems.diamondGlass, 115, Items.silicon, 300));
            range = 195f;
            shootY = 3.5f;
            //rotateSpeed = 4f;
            recoil = 2f;
            recoilTime = 35f;
            reload = 170f;
            maxSpeedupScl = 0.85f;
            overheatTime = 500f;
            cooldownTime = reload * 0.8f;
            speedupPerShoot = 0.05f;
            shake = 4f;
            smokeEffect = shootEffect = Fx.none;
            heatColor = Color.red;
            size = 3;
            shoot.firstShotDelay = 30f;
            minWarmup = 0.8f;
            coolant = consumeCoolant(0.4f);
            targetUnderBlocks = false;
            chargeSound = Sounds.chargeLancer;
            moveWhileCharging = false;
            canOverdrive = false;

            consumePower(15f);

            drawer = new DrawTurret(){{
                parts.add(new RegionPart("-side"){{
                    progress = PartProgress.recoil;
                    moveX = -1.5f;
                    moveY = 1.5f;
                    mirror = under = true;
                }},
                new RegionPart("-blade"){{
                    moves.add(new PartMove(PartProgress.charge.curve(Interp.circleIn), 0, 0, -40));
                    moves.add(new PartMove(PartProgress.recoil.curve(Interp.pow2In), 0, 0, -40));
                    mirror = under = true;
                }});
            }};

            shootType = new BasicBulletType(){{
                sprite = "terra-plasma";
                speed = 5.6f;
                drag = 0.03f;
                damage = 139;
                buildingDamageMultiplier = 0.6f;
                shieldDamageMultiplier = 1.1f;
                width = 11f;
                height = 11f;
                shrinkX = shrinkY = -1.4f;
                shrinkInterp = Interp.smooth2;
                hitSound = Sounds.explosionTitan;
                shootSound = Sounds.shootAfflict;
                despawnHit = true;
                hittable = false;
                reflectable = false;
                keepVelocity = false;
                splashDamageRadius = 40f;
                splashDamage = 99f;
                lifetime = 120f;
                lightRadius = 40f;
                lightOpacity = 0.7f;
                trailColor = backColor = hitColor = lightColor = Color.valueOf("8db0ff");
                frontColor = Color.white;
                hitEffect = despawnEffect = new MultiEffect(TerraFx.circleFadeBig, TerraFx.circleFade);
                shootEffect = new Effect(26f, e -> {
                    color(Pal.suppress);
                    Drawf.tri(e.x, e.y, 9f * e.fout(), 80f - (20f * e.fin()), e.rotation);
                    for (int i = 0; i < 2; i++) {
                        Drawf.tri(e.x, e.y, 3f * e.fout(), 25f, e.rotation + (5f + (e.fin(Interp.circleOut) * 30f)) * Mathf.signs[i]);
                    }
                });
                chargeEffect = new WrapEffect(TerraFx.smallCharge, Color.valueOf("8db0ff"));
                status = StatusEffects.shocked;
                intervalBullets = 2;
                bulletInterval = 9f;
                intervalDelay = 40f;
                fragBullets = 9;
                intervalBullet = fragBullet = new LaserBoltBulletType(5.2f, 19){{
                    lifetime = 40f;
                    rotateSpeed = 6.5f;
                    backColor = lightColor = trailColor = Color.valueOf("8db0ff");
                    frontColor = Color.white;
                    hitEffect = despawnEffect = smokeEffect = trailEffect = new Effect(8, e -> {
                        color(Color.white, Color.valueOf("8db0ff"), e.fin());
                        stroke(0.5f + e.fout());
                        Lines.circle(e.x, e.y, e.fin() * 5f);
                
                        Drawf.light(e.x, e.y, 23f, Color.valueOf("8db0ff"), e.fout() * 0.7f);
                    });
                    despawnSound = Sounds.shootElude;
                    trailInterval = lifetime / 3 + 0.01f;
                    status = StatusEffects.shocked;
                }};
            }};
        }};

        aircraft = new ItemTurret("aircraft"){{
            requirements(Category.turret, with(Items.thorium, 165, Items.graphite, 300, TerraItems.titaniumPlate, 280, TerraItems.diamondDust, 50));
            predictTarget = false;
            ammo(
                Items.thorium,  new BulletType(){{
                    damage = speed = 0f;
                    ammoMultiplier = 3;
                    rangeChange = 24;
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootBigSmoke2;
                    spawnUnit = TerraUnitTypes.aircraftThoriumMissile;
                }},
                TerraItems.thermoxite,  new BulletType(){{
                    damage = speed = 0f;
                    ammoMultiplier = 2;
                    reloadMultiplier = 0.2f;
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootBigSmoke2;
                    spawnUnit = TerraUnitTypes.aircraftThermoxiteMissile;
                }},
                TerraItems.fissileCrystals,  new BulletType(){{
                    damage = speed = 0f;
                    ammoMultiplier = 1;
                    reloadMultiplier = 0.33f;
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootBigSmoke2;
                    spawnUnit = TerraUnitTypes.aircraftFissileMissile;
                }}
            );

            drawer = new DrawTurret(){{
                parts.add(new RegionPart("-blade"){{
                    progress = PartProgress.warmup;
                    heatProgress = PartProgress.warmup;
                    heatColor = Color.red;
                    moveRot = -16f;
                    moveX = 0f;
                    moveY = -3f;
                    mirror = true;
                }});
                setAmmoParts(
                    Items.thorium, Seq.with(new RegionPart("-thorium-missile"){{
                        progress = PartProgress.reload;
                        colorTo = new Color(1f, 1f, 1f, 0f);
                        color = Color.white;
                        mixColorTo = Pal.accent;
                        mixColor = new Color(1f, 1f, 1f, 0f);
                        outline = false;
                        under = true;
                        layerOffset = -0.01f;

                        moves.add(new PartMove(PartProgress.warmup, 0f, 2f, 0f));
                    }}),
                    TerraItems.thermoxite, Seq.with(new RegionPart("-thermoxite-missile"){{
                        progress = PartProgress.reload;
                        colorTo = new Color(1f, 1f, 1f, 0f);
                        color = Color.white;
                        mixColorTo = Pal.accent;
                        mixColor = new Color(1f, 1f, 1f, 0f);
                        outline = false;
                        under = true;
                        layerOffset = -0.01f;

                        moves.add(new PartMove(PartProgress.warmup, 0f, 2f, 0f));
                    }}),
                    TerraItems.fissileCrystals, Seq.with(new RegionPart("-fissile-missile"){{
                        progress = PartProgress.reload;
                        colorTo = new Color(1f, 1f, 1f, 0f);
                        color = Color.white;
                        mixColorTo = Pal.accent;
                        mixColor = new Color(1f, 1f, 1f, 0f);
                        outline = false;
                        under = true;
                        layerOffset = -0.01f;

                        moves.add(new PartMove(PartProgress.warmup, 0f, 2f, 0f));
                    }})
                );
            }};

            size = 3;
            shootSound = Sounds.shootScathe;
            shootSoundVolume = 0.5f;
            shootY = 0f;
            reload = 90f;
            minWarmup = 0.96f;
            range = 340;
            shootCone = 15f;
            newTargetInterval = 20f;
            rotateSpeed = 4f;
            maxAmmo = 10;
            coolant = consumeCoolant(0.1f);
            coolantMultiplier = 5f;
            researchCostMultiplier = 0.5f;
            depositCooldown = 2.0f;

            limitRange(5f);
        }};
        
        //units
        basicAssembler = new UnitCrafter("basic-assembler"){{
            requirements(Category.units, with(Items.lead, 280, Items.silicon, 420, Items.titanium, 380, TerraItems.titaniumPlate, 165));
            size = 4;
            //core
            addPlan(TerraUnitTypes.tau, 60f * 27.5f).item(ItemStack.with(Items.silicon, 90, Items.graphite, 35, TerraItems.titaniumPlate, 50, TerraItems.diamondDust, 30));
            //attack
            addPlan(UnitTypes.flare, 60f * 15f).item(ItemStack.with(Items.silicon, 20));
            addPlan(UnitTypes.horizon, 60f * 25f).item(ItemStack.with(Items.silicon, 85, Items.graphite, 40));
            addPlan(UnitTypes.zenith, 60f * 50f).item(ItemStack.with(Items.silicon, 210, Items.titanium, 135, TerraItems.titaniumPlate, 80)).liquid(LiquidStack.with(Liquids.water, 20/60f));
            //support
            addPlan(UnitTypes.mono, 60f * 25f).item(ItemStack.with(Items.silicon, 35, Items.lead, 20));
            addPlan(UnitTypes.poly, 60f * 35f).item(ItemStack.with(Items.silicon, 110, Items.metaglass, 50));
            addPlan(UnitTypes.mega, 60f * 70f).item(ItemStack.with(Items.silicon, 240, Items.titanium, 110, Items.metaglass, 125)).liquid(LiquidStack.with(Liquids.water, 20/60f));
            //special
            addPlan(TerraUnitTypes.wick, 60f * 15f).item(ItemStack.with(Items.silicon, 30, TerraItems.carbon, 20));
            addPlan(TerraUnitTypes.dynamite, 60f * 30f).item(ItemStack.with(Items.silicon, 90, Items.graphite, 35, TerraItems.carbon, 55));
            addPlan(TerraUnitTypes.incident, 60f * 60f).item(ItemStack.with(Items.silicon, 225, Items.graphite, 120, TerraItems.diamondDust, 75)).liquid(LiquidStack.with(Liquids.water, 20/60f));
            
            researchCostMultiplier = 0.5f;
            liquidCapacity = 200;
            createSound = Sounds.unitCreate;
            areaSize = 6;
            droneType = TerraUnitTypes.basicAssemblyDrone;
            consumePower(200f / 60);
        }};
        advancedAssembler = new UnitCrafter("advanced-assembler"){{
            requirements(Category.units, with(Items.thorium, 780, Items.silicon, 1420, Items.phaseFabric, 400, TerraItems.diamondGlass, 540));
            size = 6;
            //attack
            addPlan(UnitTypes.antumbra, 60f * 140f).item(ItemStack.with(Items.silicon, 850, Items.thorium, 635, TerraItems.titaniumPlate, 420, TerraItems.diamondDust, 220)).liquid(LiquidStack.with(Liquids.cryofluid, 1f));
            addPlan(UnitTypes.eclipse, 60f * 280f).item(ItemStack.with(Items.silicon, 1600, Items.phaseFabric, 840, TerraItems.darkSteel, 1150, TerraItems.diamondGlass, 740)).liquid(LiquidStack.with(Liquids.cryofluid, 3f));
            //support
            addPlan(UnitTypes.quad, 60f * 155f).item(ItemStack.with(Items.silicon, 850, Items.thorium, 580, TerraItems.rawThermoxite, 385, TerraItems.diamondGlass, 260)).liquid(LiquidStack.with(Liquids.cryofluid, 1f));
            addPlan(UnitTypes.oct, 60f * 320f).item(ItemStack.with(Items.silicon, 2100, Items.phaseFabric, 1300, TerraItems.darkSteel, 1150, TerraItems.thermoxite, 320)).liquid(LiquidStack.with(Liquids.cryofluid, 3f));
            //special
            addPlan(TerraUnitTypes.catastrophe, 60f * 160f).item(ItemStack.with(Items.silicon, 850, Items.thorium, 700, Items.phaseFabric, 215, TerraItems.diamondGlass, 355)).liquid(LiquidStack.with(Liquids.cryofluid, 1f));
            addPlan(TerraUnitTypes.inevitability, 60f * 300f).item(ItemStack.with(Items.silicon, 1900, TerraItems.thermoxite, 780, TerraItems.darkSteel, 1500, TerraItems.diamondGlass, 500)).liquid(LiquidStack.with(Liquids.cryofluid, 3f));
            
            researchCostMultiplier = 0.65f;
            liquidCapacity = 1800;
            areaSize = 15;
            droneType = TerraUnitTypes.basicAssemblyDrone;
            consumePower(2000f / 60);
        }};
        //using only for cost param in units
        debugAssembler = new UnitFactory("debug-assembler"){{
            requirements(Category.units, BuildVisibility.debugOnly, with(TerraItems.tesseract, 1));
            size = 2;
            plans = Seq.with(
                //purple
                new UnitPlan(TerraUnitTypes.wick, 60f * 15f, with(Items.silicon, 30, TerraItems.carbon, 20)),
                new UnitPlan(TerraUnitTypes.dynamite, 60f * 30f, with(Items.silicon, 90, Items.graphite, 35, TerraItems.carbon, 55)),
                new UnitPlan(TerraUnitTypes.incident, 60f * 60f, with(Items.silicon, 225, Items.graphite, 120, TerraItems.diamondDust, 75)),
                new UnitPlan(TerraUnitTypes.catastrophe, 60f * 160f, with(Items.silicon, 850, Items.thorium, 700, Items.phaseFabric, 215, TerraItems.diamondGlass, 355)),
                new UnitPlan(TerraUnitTypes.inevitability, 60f * 300f, with(Items.silicon, 1900, TerraItems.thermoxite, 780, TerraItems.darkSteel, 1500, TerraItems.diamondGlass, 500)),
                new UnitPlan(TerraUnitTypes.eternity, 60f * 1000f, with(TerraItems.tesseract, 10, TerraItems.darkSteel, 4800, TerraItems.thermoxite, 1800, TerraItems.diamondGlass, 3000, TerraItems.gammaCell, 800, TerraItems.titaniumPlate, 2780))
            );
            consumePower(20f / 60);
        }};
        /*coreFactory = new UnitFactory("core-factory"){{
            requirements(Category.units, with(Items.silicon, 120, Items.copper, 80, Items.lead, 90, Items.titanium, 60));
            size = 3;
            plans = Seq.with(
                new UnitPlan(UnitTypes.alpha, 60f * 25f, with(Items.silicon, 40, Items.copper, 50, Items.lead, 20)),
                new UnitPlan(UnitTypes.beta, 60f * 50f, with(Items.silicon, 70, Items.copper, 140, Items.graphite, 75, Items.titanium, 80)),
                new UnitPlan(UnitTypes.gamma, 60f * 80f, with(Items.silicon, 150, Items.copper, 180, Items.plastanium, 60, Items.thorium, 90)),
                new UnitPlan(TerraUnitTypes.tau, 60f * 90f, with(Items.silicon, 200, Items.titanium, 160, Items.plastanium, 90, Items.phaseFabric, 40))
            );
            consumePower(3.6f);
        }};*/
        ((UnitFactory) Blocks.airFactory).plans.addAll(new UnitFactory.UnitPlan(UnitTypes.alpha, 60f * 50, with(Items.silicon, 40, Items.copper, 50, Items.lead, 20)));
        ((Reconstructor) Blocks.additiveReconstructor).upgrades.addAll(new UnitType[]{UnitTypes.alpha, UnitTypes.beta});
        ((Reconstructor) Blocks.multiplicativeReconstructor).upgrades.addAll(
            new UnitType[]{UnitTypes.beta, UnitTypes.gamma},
            new UnitType[]{UnitTypes.alpha, TerraUnitTypes.tau}
        );
        /*droneCentre = new DroneCentre("drone-centre"){{
            requirements(Category.units, with(Items.titanium, 135, Items.lead, 190, Items.silicon, 160));
            health = 480;
            size = 2;
        }};*/

        largePayloadConveyor = new PayloadConveyor("large-payload-conveyor"){{
            requirements(Category.units, with(Items.thorium, 15, Items.titanium, 15));
            moveTime = 50f;
            canOverdrive = false;
            underBullets = true;
            size = 5;
            payloadLimit = 5f;
        }};

        //other

        
        //NOTVA =---


        
        //GIER =---


        
        //TATNTROS =---


        
        //EREKIR & COPIS =---
        //walls
        berylliumWallHuge = new Wall("beryllium-wall-huge"){{
            requirements(Category.defense, with(Items.beryllium, 6 * 9));
            health = 130 * 9 * wallHealthMultiplier;
            armor = 2f;
            buildCostMultiplier = 2f;
            size = 3;
        }};
        berylliumWallGigantic = new Wall("beryllium-wall-gigantic"){{
            requirements(Category.defense, with(Items.beryllium, 6 * 15));
            health = 130 * 15 * wallHealthMultiplier;
            armor = 2f;
            size = 4;
        }};
        tungstenWallHuge = new Wall("tungsten-wall-huge"){{
            requirements(Category.defense, with(Items.tungsten, 6 * 9));
            health = 180 * 9 * wallHealthMultiplier;
            armor = 14f;
            buildCostMultiplier = 2f;
            size = 3;
        }};
        tungstenWallGigantic = new Wall("tungsten-wall-gigantic"){{
            requirements(Category.defense, with(Items.tungsten, 6 * 15));
            health = 180 * 15 * wallHealthMultiplier;
            armor = 14f;
            buildCostMultiplier = 2f;
            size = 4;
        }};
        blastGate = new AutoDoor("blast-gate"){{
            requirements(Category.defense, with(Items.tungsten, 6, Items.silicon, 6));
            health = 175 * wallHealthMultiplier;
            armor = 14f;
        }};
        blastDoorLarge = new AutoDoor("blast-door-large"){{
            requirements(Category.defense, with(Items.tungsten, 6 * 9, Items.silicon, 6 * 9));
            health = 175 * wallHealthMultiplier * 9;
            armor = 14f;
            size = 3;
        }};
        blastDoorHuge = new AutoDoor("blast-door-huge"){{
            requirements(Category.defense, with(Items.tungsten, 6 * 16, Items.silicon, 6 * 16));
            health = 175 * wallHealthMultiplier * 16;
            armor = 14f;
            size = 4;
        }};
        reinforcedSurgeWallHuge = new Wall("reinforced-surge-wall-huge"){{
            requirements(Category.defense, with(Items.surgeAlloy, 6 * 9, Items.tungsten, 2 * 9));
            health = 250 * wallHealthMultiplier * 9;
            lightningChance = 0.05f;
            lightningDamage = 30f;
            armor = 20f;
            size = 3;
        }};
        reinforcedSurgeWallGigantic = new Wall("reinforced-surge-wall-gigantic"){{
            requirements(Category.defense, with(Items.surgeAlloy, 6 * 15, Items.tungsten, 2 * 15));
            health = 250 * wallHealthMultiplier * 15;
            lightningChance = 0.05f;
            lightningDamage = 30f;
            armor = 20f;
            size = 4;
        }};
        carbideWallHuge = new Wall("carbide-wall-huge"){{
            requirements(Category.defense, with(Items.thorium, 6 * 9, Items.carbide, 6 * 9));
            health = 270 * 9 * wallHealthMultiplier;
            armor = 16f;
            size = 3;
        }};
        carbideWallGigantic = new Wall("carbide-wall-gigantic"){{
            requirements(Category.defense, with(Items.thorium, 6 * 15, Items.carbide, 6 * 15));
            health = 270 * 15 * wallHealthMultiplier;
            armor = 16f;
            size = 4;
        }};
        shieldedWallLarge = new ShieldWall("shielded-wall-large"){{
            requirements(Category.defense, ItemStack.with(Items.phaseFabric, 5 * 9, Items.surgeAlloy, 3 * 9, Items.beryllium, 3 * 9));
            consumePower(((3f / 4f) * 9f) / 60f);

            outputsPower = false;
            hasPower = true;
            consumesPower = true;
            conductivePower = true;

            chanceDeflect = 8f;

            health = 260 * wallHealthMultiplier * 9;
            shieldHealth = (900f / 4f) * 9;
            armor = 15f;
            size = 3;
        }};

        //power
        beamBeacon = new BeamNode("beam-beacon"){{
            requirements(Category.power, with(Items.beryllium, 200, Items.oxide, 80, Items.silicon, 150, Items.surgeAlloy, 135, Items.fissileMatter, 120));
            size = 5;
            consumesPower = outputsPower = true;
            baseExplosiveness = 8;
            range = 68;
            scaledHealth = 35;
            fogRadius = 3;

            consumePowerBuffered(125000f);
        }};
        reinforcedPanel = new SolarGenerator("reinforced-solar-panel"){{
            requirements(Category.power, with(Items.graphite, 85, Items.silicon, 65, Items.tungsten, 20));
            size = 2;
            armor = 8;
            health = 450;
            powerProduction = 0.72f;
        }};

        //crafters
        inductionFurnace = new GenericCrafter("silicon-induction-furnace"){{
            requirements(Category.crafting, with(Items.beryllium, 130, Items.graphite, 180, Items.tungsten, 200, Items.thorium, 80));
            craftEffect = Fx.none;
            outputItem = new ItemStack(Items.silicon, 9);
            outputLiquid = new LiquidStack(Liquids.slag, 22f / 60f);
            craftTime = 40f;
            size = 4;
            hasPower = true;
            hasLiquids = false;
            envEnabled |= Env.underwater;
            envDisabled = Env.none;
            itemCapacity = 50;
            liquidCapacity = 80;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.slag, 2f), new DrawArcSmelt(), new DrawDefault());
            fogRadius = 4;
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.3f;
            squareSprite = false;

            consumeItems(with(Items.graphite, 3, Items.sand, 9, Items.tungsten, 2));
            consumePower(11f);
        }};
        hydrogenReductor = new HeatCrafter("hydrogen-reductor"){{
            requirements(Category.crafting, with(Items.silicon, 120, Items.tungsten, 200, Items.oxide, 140));
            craftEffect = Fx.none;
            outputItem = new ItemStack(TerraItems.sodium, 2);
            craftTime = 90f;
            size = 3;
            hasPower = true;
            hasLiquids = true;
            itemCapacity = 50;
            liquidCapacity = 80;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.hydrogen, 2f), new DrawCircles(){{
                color = TerraItems.sodium.color.cpy().a(0.24f);
                strokeMax = 2.5f;
                radius = 10f;
                amount = 3;
            }}, new DrawDefault(), new DrawHeatInput(), new DrawGlowRegion(){{
                color = Color.valueOf("d1efff");
                alpha = 0.5f;
            }});
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.1f;

            consumeItem(Items.silicon, 3);
            consumeLiquid(Liquids.hydrogen, 6f / 60f);
            heatRequirement = 5f;
            maxEfficiency = 4f;
            consumePower(2.4f);
        }};

        //turrets
        float cap = 8f;
        split = new ItemTurret("split"){{
            requirements(Category.turret, with(Items.silicon, 55, Items.tungsten, 55));
            range = 178f;
            ammo(
                Items.tungsten, new BasicBulletType(4.1f, 45){{
                    lifetime = ((178 + cap + 10f) / 4.1f) / 3;
                    width = 8f;
                    height = 11f;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                    smokeEffect = Fx.shootBigSmoke;
                    hitColor = backColor = trailColor = Pal.tungstenShot;
                    frontColor = Color.white;

                    ammoMultiplier = 2;
                    armorMultiplier = 0.75f;
                    buildingDamageMultiplier = 0.5f;
                    trailWidth = 1.7f;
                    trailLength = 6;
                    status = StatusEffects.slow;
                    statusDuration = 90f;
                    fragBullets = 2;
                    fragRandomSpread = 0f;
                    fragVelocityMin = 0.9f;
                    fragOffsetMax = 1f;
                    fragSpread = 15f;
                    fragBullet = new BasicBulletType(4.1f, 33){{
                        lifetime = (((178 + cap + 10f) / 4.1f) / 3) * 2;
                        width = 6f;
                        height = 9f;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                        smokeEffect = Fx.shootBigSmoke;
                        hitColor = backColor = trailColor = Pal.tungstenShot;
                        frontColor = Color.white;
    
                        ammoMultiplier = 2;
                        armorMultiplier = 0.85f;
                        buildingDamageMultiplier = 0.5f;
                        trailWidth = 1.4f;
                        trailLength = 5;
                    }};
                }},
                Items.thorium, new BasicBulletType(4.3f, 66){{
                    rangeChange = 3f * 8;
                    reloadMultiplier = 0.8f;
                    lifetime = ((178 + cap + rangeChange + 10f) / 4.3f) / 3;
                    width = 9f;
                    height = 13f;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                    smokeEffect = Fx.shootBigSmoke;
                    hitColor = backColor = trailColor = Color.valueOf("e89dbd");
                    frontColor = Color.white;

                    ammoMultiplier = 2;
                    armorMultiplier = 0.5f;
                    buildingDamageMultiplier = 0.5f;
                    trailWidth = 1.7f;
                    trailLength = 6;
                    status = StatusEffects.slow;
                    statusDuration = 60f;
                    fragBullets = 2;
                    fragRandomSpread = 0f;
                    fragVelocityMin = 0.9f;
                    fragOffsetMax = 1f;
                    fragSpread = 15f;
                    fragBullet = new BasicBulletType(4.3f, 48){{
                        rangeChange = 3f * 8;
                        lifetime = (((178 + cap + rangeChange + 10f) / 4.3f) / 3) * 2;
                        width = 7f;
                        height = 10.5f;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                        smokeEffect = Fx.shootBigSmoke;
                        hitColor = backColor = trailColor = Color.valueOf("e89dbd");
                        frontColor = Color.white;
    
                        ammoMultiplier = 2;
                        armorMultiplier = 0.6f;
                        buildingDamageMultiplier = 0.5f;
                        pierce = true;
                        pierceCap = 2;
                        trailWidth = 1.4f;
                        trailLength = 5;
                    }};
                }},
                TerraItems.sodium, new BasicBulletType(4f, 48){{
                    rangeChange = -2f * 8;
                    reloadMultiplier = 0.55f;
                    lifetime = ((178 + cap + rangeChange + 10f) / 4f) / 3;
                    width = 10f;
                    height = 15f;
                    hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletColor, new ExplosionEffect(){{
                        waveColor = smokeColor = Color.valueOf("d1e4ff");
                        sparkColor = Color.white;
                        waveLife = 10f;
                        waveRad = 3.5f * 8;
                        smokeRad = 3f * 8;
                        smokes = 9;
                        lifetime = 30f;
                    }});
                    shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                    smokeEffect = Fx.shootBigSmoke;
                    hitColor = backColor = trailColor = Color.valueOf("d1e4ff");
                    frontColor = Color.white;

                    splashDamage = damage * 0.75f;
                    splashDamageRadius = 3.5f * 8;

                    ammoMultiplier = 2;
                    armorMultiplier = 0.8f;
                    buildingDamageMultiplier = 0.25f;
                    trailWidth = 1.7f;
                    trailLength = 6;
                    fragBullets = 4;
                    fragRandomSpread = 0f;
                    fragVelocityMin = 0.7f;
                    fragOffsetMax = 1f;
                    fragSpread = 12f;
                    fragBullet = new BasicBulletType(4.3f, 17){{
                        rangeChange = -2f * 8;
                        lifetime = (((178 + cap + rangeChange + 10f) / 4f) / 3) * 2;
                        width = 6f;
                        height = 10f;
                        hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletColor, new ExplosionEffect(){{
                            waveColor = smokeColor = Color.valueOf("d1e4ff");
                            sparkColor = Color.white;
                            waveLife = 10f;
                            waveRad = 2.5f * 8;
                            smokeRad = 2f * 8;
                            smokes = 6;
                            lifetime = 20f;
                        }});
                        shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                        smokeEffect = Fx.shootBigSmoke;
                        hitColor = backColor = trailColor = Color.valueOf("d1e4ff");
                        frontColor = Color.white;

                        splashDamage = damage * 0.75f;
                        splashDamageRadius = 2.5f * 8;
    
                        ammoMultiplier = 2;
                        armorMultiplier = 0.95f;
                        buildingDamageMultiplier = 0.25f;
                        trailWidth = 1.3f;
                        trailLength = 5;
                    }};
                }}
            );

            drawer = new DrawTurret("reinforced-"){{
                parts.add(new RegionPart("-shooter"){{
                    progress = PartProgress.recoil;
                    moveY = -0.75f;
                    mirror = false;
                    under = false;
                }});
            }};

            size = 2;
            reload = 25f;
            shootY = 4.5f;

            health = 1200;
            shootSound = TerraSounds.shootAlt2;
            outlineColor = Pal.darkOutline;
            heatColor = Liquids.nitrogen.color;

            //limitRange(cap);
            coolantMultiplier = 10f;
            coolant = consume(new ConsumeLiquid(Liquids.water, 10f / 60f));
        }};

        //units
        boatFabricator = new UnitFactory("boat-fabricator"){{
            requirements(Category.units, with(Items.silicon, 200, Items.beryllium, 250, Items.tungsten, 50));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(TerraUnitTypes.flow, 60f * 45f, with(Items.beryllium, 70, Items.silicon, 60, Items.tungsten, 10)));
            regionSuffix = "-dark";
            fogRadius = 3;
            researchCostMultiplier = 0.65f;
            consumePower(1.5f);
        }};
        basicFabricator = new UnitFactory("basic-fabricator"){{
            requirements(Category.units, with(Items.silicon, 300, Items.beryllium, 250, Items.graphite, 150, Items.tungsten, 200));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(UnitTypes.evoke, 60f * 90f, with(Items.beryllium, 170, Items.silicon, 140, Items.tungsten, 100)));
            regionSuffix = "-dark";
            fogRadius = 3;
            researchCostMultiplier = 0.75f;
            consumePower(3f);
        }};
        boatRefabricator = new Reconstructor("boat-refabricator"){{
            requirements(Category.units, with(Items.beryllium, 300, Items.tungsten, 175, Items.silicon, 125, Items.oxide, 85));
            regionSuffix = "-dark";

            size = 3;
            consumePower(2.5f);
            consumeLiquid(Liquids.hydrogen, 3f / 60f);
            consumeItems(with(Items.silicon, 70, Items.tungsten, 40));

            constructTime = 60f * 55f;
            researchCostMultiplier = 0.75f;

            upgrades.addAll(
            new UnitType[]{TerraUnitTypes.flow, TerraUnitTypes.threshold}
            );
        }};
        basicRefabricator = new Reconstructor("basic-refabricator"){{
            requirements(Category.units, with(Items.beryllium, 400, Items.tungsten, 275, Items.graphite, 150, Items.silicon, 425, Items.oxide, 185));
            regionSuffix = "-dark";

            size = 3;
            consumePower(5f);
            consumeLiquid(Liquids.ozone, 6f / 60f);
            consumeItems(with(Items.silicon, 200, Items.tungsten, 140, Items.oxide, 100));

            constructTime = 60f * 110f;
            researchCostMultiplier = 0.75f;

            upgrades.addAll(
            new UnitType[]{UnitTypes.evoke, UnitTypes.incite}
            );
        }};
        ((Reconstructor) Blocks.primeRefabricator).upgrades.addAll(
            new UnitType[]{TerraUnitTypes.threshold, TerraUnitTypes.turn},
            new UnitType[]{UnitTypes.incite, UnitTypes.emanate}
        );
        boatAssembler = new UnitAssembler("boat-assembler"){{
            requirements(Category.units, with(Items.carbide, 250, Items.thorium, 600, Items.oxide, 300, Items.beryllium, 750, Items.silicon, 1000));
            regionSuffix = "-dark";
            size = 5;
            plans.add(
            new AssemblerUnitPlan(TerraUnitTypes.movement, 60f * 80f, PayloadStack.list(TerraUnitTypes.flow, 5, Blocks.reinforcedSurgeWallLarge, 10)),
            new AssemblerUnitPlan(TerraUnitTypes.consequence, 60f * 60f * 3f, PayloadStack.list(TerraUnitTypes.threshold, 6, Blocks.carbideWallLarge, 20))
            );
            areaSize = 13;

            consumePower(3f);
            consumeLiquid(Liquids.cyanogen, 12f / 60f);
        }};

        largeReinforcedPayloadConveyor = new PayloadConveyor("large-reinforced-payload-conveyor"){{
            requirements(Category.units, with(Items.tungsten, 20, Items.carbide, 10));
            moveTime = 40f;
            canOverdrive = false;
            health = 1500;
            researchCostMultiplier = 4f;
            underBullets = true;
            size = 5;
            payloadLimit = 5f;
        }};

        //logic
        primeProcessor = new LogicBlock("prime-processor"){{
            requirements(Category.logic, with(Items.oxide, 80, Items.silicon, 100, Items.thorium, 25));

            consumeLiquid(Liquids.nitrogen, 0.1f);
            hasLiquids = true;
            squareSprite = false;

            /*buildType = () -> new LogicBuild() {
                private DrawBlock drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.nitrogen), new DrawDefault());
                
                @Override
                public void draw(){
                    drawer.draw(this);
                }
            };*/
            
            instructionsPerTick = 12;
            range = 8 * 28;
            size = 2;
        }};
        
        //OTHER =---


        
    }
}
