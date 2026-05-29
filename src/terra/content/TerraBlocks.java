package terra.content;

import terra.type.bullet.*;
import terra.world.blocks.*;
import terra.world.blocks.multiblock.*;
import terra.world.drawer.*;
import terra.world.meta.*;
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
    scrapWallColossol, doorHuge, doorGigantic, plastaniumWallHuge, phaseWallHuge,
    metaglassWall, metaglassWallLarge, metaglassWallHuge,
    darkSteelWall, darkSteelWallLarge,
    //distrubution
    graphiteConveyor, 
    graphiteJunction, graphiteRouter, graphiteGate,
    graphiteBridge, smallDriver,
    //power
    photonPanel, photonPanelLarge,
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
    flight, dynamics, electricShock, fracture,
    //units
    basicAssembler, advancedAssembler, droneCentre, debugAssembler,
    //other
    //NOTVA =---
    //GIER =---
    //TATNTROS =---
    //EREKIR & COPIS =---
    //walls
    blastGate, blastDoorLarge, blastDoorHuge,
    shieldedWallLarge,
    //power
    beamBeacon, reinforcedPanel;
    //OTHER =---
    public static void load(){
        //VERILUS & SERPULO =---
        //walls
        int wallHealthMultiplier = 4;
        
        scrapWallColossol = new Wall("scrap-wall-colossol"){{
            requirements(Category.defense, with(Items.scrap, 6 * 25));
            health = 60 * 25 * 4;
            size = 5;
            buildCostMultiplier = 4f;
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
        plastaniumWallHuge = new Wall("plastanium-wall-huge"){{
            requirements(Category.defense, with(Items.plastanium, 5 * 9, Items.metaglass, 2 * 9));
            health = 125 * wallHealthMultiplier * 9;
            size = 3;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
        phaseWallHuge = new Wall("phase-wall-huge"){{
            requirements(Category.defense, with(Items.phaseFabric, 6 * 9));
            health = 150 * 9 * wallHealthMultiplier;
            size = 3;
            chanceDeflect = 10f;
            flashHit = true;
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
            requirements(Category.effect, with(Items.copper, 3500, Items.lead, 5000, Items.silicon, 6000, Items.titanium, 5500, Items.phaseFabric, 3000));
            //alwaysUnlocked = true;
            unitType = UnitTypes.beta;
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
        
            @Override
            public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
                Draw.rect(this.fullRegion, plan.drawx(), plan.drawy());
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
        
        //units
        basicAssembler = new UnitCrafter("basic-assembler"){{
            requirements(Category.units, with(Items.lead, 280, Items.silicon, 420, Items.titanium, 380, TerraItems.titaniumPlate, 165));
            size = 4;
            //core
            addPlan(UnitTypes.beta, 60f * 20f).item(ItemStack.with(Items.silicon, 40, TerraItems.titaniumPlate, 20));
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
            consumePower(3.4f);
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
            consumePower(8.7f);
        }};
        //using only for cost param in units
        debugAssembler = new UnitFactory("debug-assembler"){{
            requirements(Category.units, BuildVisibility.debugOnly, with(TerraItems.tesseract, 1));
            size = 2;
            plans = Seq.with(
                new UnitPlan(TerraUnitTypes.wick, 60f * 15f, with(Items.silicon, 30, TerraItems.carbon, 20)),
                new UnitPlan(TerraUnitTypes.dynamite, 60f * 30f, with(Items.silicon, 90, Items.graphite, 35, TerraItems.carbon, 55)),
                new UnitPlan(TerraUnitTypes.incident, 60f * 60f, with(Items.silicon, 225, Items.graphite, 120, TerraItems.diamondDust, 75)),
                new UnitPlan(TerraUnitTypes.catastrophe, 60f * 160f, with(Items.silicon, 850, Items.thorium, 700, Items.phaseFabric, 215, TerraItems.diamondGlass, 355)),
                new UnitPlan(TerraUnitTypes.inevitability, 60f * 300f, with(Items.silicon, 1900, TerraItems.thermoxite, 780, TerraItems.darkSteel, 1500, TerraItems.diamondGlass, 500)),
                new UnitPlan(TerraUnitTypes.eternity, 60f * 1000f, with(TerraItems.tesseract, 10, TerraItems.darkSteel, 4800, TerraItems.thermoxite, 1800, TerraItems.diamondGlass, 3000, TerraItems.radium, 100, TerraItems.titaniumPlate, 2780))
            );
            consumePower(0.1f);
        }};
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
        //walls
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
        
        //OTHER =---


        
    }
}
