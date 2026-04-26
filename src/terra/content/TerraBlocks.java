package terra.content;

import terra.type.bullet.*;
import terra.world.blocks.*;
import arc.util.Tmp;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.entities.effect.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.entities.effect.MultiEffect;
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
                lifetime = 30f;
                width = 12f;
                height = 26f;
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
                lifetime = 30f;
                width = 12f;
                height = 26f;
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
                height = 26f;
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
            regenStartDelay = 150f;
            regenDamageStop = true;
        }};
        darkSteelWallLarge = new AdvancedWall("dark-steel-wall-large"){{
            requirements(Category.defense, with(TerraItems.darkSteel, 6 * 9, Items.metaglass, (6 * 9) / 2));
            health = 930 * 9;
            size = 3;
            absorbLasers = true;
            autoRegeneration = true;
            regenStartDelay = 150f;
            regenDamageStop = true;
        }};

        droneCentre = new DroneCentre("drone-centre"){{
            requirements(Category.defense, with(Items.titanium, 135, Items.lead, 190, Items.silicon, 160));
            size = 2;
            droneTypes.addAll(TerraUnitTypes.healDrone);
        }};
    }
}
