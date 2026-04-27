package terra.content;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.type.*;
import blackhole.entities.bullet.BlackHoleBulletType;

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
            color = Color.valueOf("ffffff");
            cost = 40f;
            healthScaling = 5f;
            threat = 0.8f;
            spawnBulletOnDestroy = true;
            spawnBulletStatsScale = true;
            spawnBullet = new BlackHoleBulletType(0f, 16f){{
                lifetime = 80f;
                color = Pal.suppress;
                damageRadius = 11f;
                suctionRadius = 33f;
                growTime = 20f;
                shrinkTime = 20f;
                status = TerraStatusEffects.singularEvaporation;
                loopSoundVolume = 0.8f;
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
                    splashDamageRadius = 11f;
                    status = TerraStatusEffects.singularEvaporation;
                    statusDuration = 180f;
                }};
            }};
        }};
    }
}
