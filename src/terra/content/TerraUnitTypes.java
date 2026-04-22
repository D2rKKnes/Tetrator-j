package terra.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import terra.ai.*;
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

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class TerraUnitTypes {
    public static UnitType
    //flying special units
    wick, wickC, dynamite, incident, catastrophe, inevitability, inevitabilityCore, inevitabilityMissile;

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
            range = 90f;
            itemCapacity = 15;
            ammoType = new PowerAmmoType(1000);
            lowAltitude = true;

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
            new Weapon("dynamite-weapon"){{
                x = 13.5f / 4f;
                y = -5.5f / 4f;
                reload = 13f;
                rotate: true;
                rotateSpeed: 5f;
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
    }
}
