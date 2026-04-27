package terra.content;

import terra.type.*;
import arc.graphics.*;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;
import blackhole.entities.bullet.*;

import static mindustry.content.Items.*;

public class TerraItems{
    public static Item

            carbon, diamondDust, cryotite, darkSteel, rawThermoxite, thermoxite, tesseract;

    public static void load(){
        carbon = new Item("carbon", Color.valueOf("3c4448")){{
            flammability = 1.25f;
            hardness = 2;
        }};
        diamondDust = new Item("diamond-dust", Color.valueOf("ffffff")){{
            cost = 0.75f;
            hardness = 4;
        }};
        cryotite = new Item("cryotite", Color.valueOf("b1f6fa")){{
            flammability = -1f;
            explosiveness = 1f;
            hardness = 2;
        }};
        darkSteel = new Item("dark-steel", Color.valueOf("6e7080")){{
            cost = 2f;
            hardness = 5;
        }};
        rawThermoxite = new Item("raw-thermoxite", Color.valueOf("ff7163")){{
            cost = 0.8f;
            hardness = 4;
        }};
        thermoxite = new Item("thermoxite", Color.valueOf("e13131")){{
            cost = 1.5f;
            hardness = 5;
        }};
        tesseract = new AdvancedItem("tesseract"){{
            color = Color.valueOf("000000");
            cost = 40f;
            healthScaling = 5f;
            threat = 0.6f;
            
            spawnBulletOnDestroy = true;
            spawnBulletStatsScale = true;
            spawnBulletChance = 0.1f;
            spawnBullet = new BlackHoleBulletType(0f, 16f){{
                lifetime = 140f;
                color = Pal.suppress;
                damageRadius = 9f;
                suctionRadius = 27f;
                growTime = 14f;
                shrinkTime = 14f;
                status = TerraStatusEffects.singularEvaporation;
                loopSoundVolume = 0.8f;
                statusDuration = 120f;
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
                    splashDamageRadius = 9f;
                    status = TerraStatusEffects.singularEvaporation;
                    statusDuration = 120f;
                }};
            }};
            damageContainer = true;
            damage = 12.3f;
            damageRand = 6.7f;
            damagePercent = false;
        }};
    }
}
