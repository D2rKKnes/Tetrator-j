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
    scrapWallColossol;
    //distrubution

    //power

    //crafters

    //turrets

    //units

    //other
    public static void load(){
        scrapWallColossol = new Wall("scrap-wall-colossol"){{
            requirements(Category.defense, with(Items.scrap, 6 * 25));
            health = 60 * 25 * 4;
            size = 5;
            buildCostMultiplier = 4f;
        }};
        glassWave = new WaveEffect(){{
            sizeFrom = strokeTo = 0f;
            sizeTo = 40f;
            strokeFrom = 3f;
            lifetime = 20f;
            colorFrom = pal.glassAmmoFront;
            colorTo = pal.glassAmmoBack;
        }};
        glassShard = new BasicBulletType(){{
            damage = 21f;
            lifetime = 30f;
            width = 12f;
            length = 26f;
            shrinkY = 0.4f;
            shrinkX = 1f;
            speed = 2f;
            backColor = hitColor = trailColor = pal.glassAmmoBack;
            frontColor = pal.glassAmmoFront;
            despawnEffect = Fx.none;
            ammoMultiplier = 1f;
        }};
        metaglassWall = new AdvancedWall("metaglass-wall"){{
            requirements(Category.defense, with(Items.metaglass, 6));
            health = 380;
            size = 1;
            hitBullet = glassShard;
            hitBulletAmount = 9;
            hitBulletAmountRand = 3;
            hitBulletSpawnChance = 0.08f;
            hitBulletEffect = glassWave;
            hitBulletSound = Sounds.explosionDull;
            hitBulletSoundPitchMin = 3.8;
            hitBulletSoundPitchMax = 4f;
        }};
    }
}
