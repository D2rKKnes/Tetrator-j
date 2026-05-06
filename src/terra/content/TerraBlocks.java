package terra.content;

import terra.type.bullet.*;
import terra.world.blocks.*;
import terra.world.drawer.*;
import terra.world.meta.*;
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
    //walls
    scrapWallColossol, metaglassWall, metaglassWallLarge, metaglassWallHuge, darkSteelWall, darkSteelWallLarge,
    //distrubution

    //power

    //crafters

    //production
    mechanicalWell, electricalWell, plasmaDrill, beamMiningFacility,
    //turrets

    //units
    droneCentre;
    //other
    public static void load(){
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

        mechanicalWell = new AttributeSeparator("mechanical-well"){{
            requirements(Category.production, with(Items.graphite, 25, Items.titanium, 40));
            size = 2;
            craftTime = 135f;
            minEfficiency = 0.1f;
            baseEfficiency = 0f;
            updateEffect = Fx.pulverizeSmall;
            updateEffectChance = 0.02f;
            attribute = TerraAttributes.carbon;
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
            requirements(Category.production, with(Items.graphite, 65, Items.thorium, 80, Items.silicon, 65, Items.metaglass, 32));
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

        /*droneCentre = new DroneCentre("drone-centre"){{
            requirements(Category.units, with(Items.titanium, 135, Items.lead, 190, Items.silicon, 160));
            health = 480;
            size = 2;
        }};*/
    }
}
