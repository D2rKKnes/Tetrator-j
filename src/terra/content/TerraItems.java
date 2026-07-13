package terra.content;

import terra.type.*;
import arc.graphics.*;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;
import blackhole.entities.bullet.*;

import static mindustry.content.Items.*;

public class TerraItems{
    public static Item

            carbon, diamondDust, diamondGlass, cryotite, titaniumPlate, darkSteel, rawThermoxite, thermoxite, uranium, plutonium, fissileCrystals, gammaCell, cesium, tesseract,
            sodium,
            tantalium, ruthenium, obsidianPowder, tenirite,
            silver, fish;

    public static void load(){
        carbon = new Item("carbon", Color.valueOf("3c4448")){{
            flammability = 1.25f;
            explosiveness = 0.2f;
            hardness = 2;
        }};
        diamondDust = new Item("diamond-dust", Color.valueOf("ffffff")){{
            cost = 0.75f;
            hardness = 4;
            healthScaling = 2f;
        }};
        diamondGlass = new Item("diamond-glass", Color.valueOf("e1e9f0")){{
            cost = 1.5f;
            hardness = 4;
            healthScaling = 1.3f;
        }};
        /*cryotite = new Item("cryotite", Color.valueOf("b1f6fa")){{
            flammability = -1f;
            explosiveness = 1f;
            healthScaling = -0.1f;
        }};*/
        titaniumPlate = new Item("titanium-plate", Color.valueOf("787ac9")){{
            cost = 1.2f;
            healthScaling = 0.6f;
        }};
        rawThermoxite = new Item("raw-thermoxite", Color.valueOf("ff7163")){{
            cost = 0.8f;
            hardness = 4;
            radioactivity = 0.2f;
        }};
        thermoxite = new Item("thermoxite", Color.valueOf("e13131")){{
            cost = 1.5f;
            hardness = 5;
            radioactivity = 0.6f;
            charge = 0.3f;
        }};
        uranium = new Item("uranium"){{
            color = Color.valueOf("75ab77");
            cost = 1.7f;
            hardness = 5;
            radioactivity = 3f;
            explosiveness = 0.3f;
        }};
        plutonium = new AdvancedItem("plutonium"){{
            color = Color.valueOf("83a8ad");
            cost = 5.8f;
            hardness = 6;
            healthScaling = -0.1f;
            radioactivity = 57.5f;
            explosiveness = 0.5f;
            threat = 0.18f;
        }};
        /*radium = new AdvancedItem("radium"){{
            color = Color.valueOf("bcff73");
            cost = 16f;
            hardness = 6;
            healthScaling = -0.1f;
            radioactivity = 900f;
            explosiveness = 1.2f;
            charge = 1f;
            threat = 0.46f;
            frames = 2;
            transitionFrames = 10;
            //hidden = true;
        }};*/
        fissileCrystals = new Item("fissile-crystals"){{
            color = Color.valueOf("50a354");
            cost = 10f;
            hardness = 6;
            healthScaling = -0.1f;
            radioactivity = 2f;
            explosiveness = 0.4f;
            charge = 0.2f;
        }};
        gammaCell = new Item("gamma-cell"){{
            color = Color.valueOf("bcff73");
            cost = 10f;
            hardness = 6;
            radioactivity = 3f;
            charge = 1f;
            frames = 2;
            transitionFrames = 10;
        }};
        cesium = new AdvancedItem("cesium"){{
            color = Color.valueOf("bebdc3");
            cost = 20f;
            healthScaling = -0.3f;
            radioactivity = 7850f;
            threat = 1f;
            hidden = true;
        }};
        darkSteel = new Item("dark-steel", Color.valueOf("6e7080")){{
            cost = 2f;
            hardness = 5;
            healthScaling = 0.5f;
        }};
        tesseract = new AdvancedItem("tesseract"){{
            color = Color.valueOf("010101");
            cost = 60f;
            healthScaling = 5f;
            //charge = 10f;
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
            damageEffect = Fx.circleColorSpark;
        }};

        sodium = new Item("sodium", Liquids.hydrogen.color){{
            flammability = 1.75f;
            explosiveness = 1.5f;
        }};

        obsidianPowder = new Item("obsidian-powder", Color.valueOf("292638")){{
            cost = 1.1f;
            hardness = 1;
        }};
        tenirite = new Item("tenirite", Color.valueOf("9681fb")){{
            cost = 2.4f;
            hardness = 6;
            healthScaling = 1.5f;
            charge = 0.1f;
        }};

        silver = new Item("silver", Color.valueOf("b1b4cc")){{
            cost = 1.4f;
            hardness = 3;
            healthScaling = 1.1f;
        }};
        fish = new AdvancedItem("fish", Color.valueOf("807e7f")){{
            cost = 0.1f;
            hardness = 0;
            flammability = 0.03f;
            threat = 0f;
        }};
    }
}
