package terra.content;

import java.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.func.Cons;
import arc.*;
import terra.ai.*;
import terra.content.*;
import terra.type.*;
import terra.type.bullet.*;
import terra.type.weapons.*;
import terra.type.ability.*;
import terra.type.units.*;
import mindustry.Vars;
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
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;
import blackhole.entities.bullet.*;
import blackhole.entities.part.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class TerraVanillaUnitsOverride {
    public static void load() {
        UnitTypes.corvus.weapons.add(
            new Weapon("terra-heal-charger"){{
                reload = 100f;
                x = 57f / 4f;
                y = -10f / 4f;
                shootY = 16f / 4f;
                rotate = true;
                rotateSpeed = 3f;
                recoilTime = reload * 0.8f;
                minWarmup = 0.9f;
                shoot.firstShotDelay = 50;
                shootSound = TerraSounds.shootElectro;
                chargeSound = Sounds.chargeVela;
                parts.addAll(
                    new RegionPart("-blade") {{
                        mirror = true;
                        under = true;
                        moves.add(new PartMove(PartProgress.warmup, 0.75f, 0f, 0f));
                        x = 0;
                        y = 0;
                        moveRot = -25;
                        progress = PartProgress.recoil;
                    }},
                    new RegionPart("-none") {{
                        mirror = false;
                        progress = PartProgress.charge.curve(Interp.fastSlow);
                        y = 0f;
                        moveY = 16f / 4f;
                        children.addAll(
                            new FlarePart(){{
                                progress = PartProgress.charge.curve(Interp.fastSlow);
                                color1 = Pal.heal;
                                radius = 0;
                                radiusTo = 9;
                                stroke = 3f;
                            }}
                        );
                        under = false;
                    }}
                );
            }};
        );
        // UnitTypes.omura.description = Core.bundle.getOrNull("unit.omura.new-description");
        // UnitTypes.omura.uiIcon = Core.atlas.find("new-omura-full");
        // UnitTypes.omura.fullIcon = Core.atlas.find("new-omura-full");
        UnitTypes.omura.weapons.get(0).layerOffset = 0.003f;
        UnitTypes.omura.weapons.add(
            new ConstructWeapon("terra-omura-drone"){{
                x = 0f;
                y = -141.5f / 4f;
                shootY = 0f;
                shootSound = Sounds.shootMissileSmall;
                shootCone = 360f;
                reload = 8f * 60f;
                rotate = false;
                //rotateSpeed = 0.4f;
                mirror = false;
                useAttackRange = false;
                bullet = new BulletType(){{
                    shootEffect = smokeEffect = Fx.none;
                    //shake = 1f;
                    speed = 0.001f;
                    keepVelocity = false;
                    spawnUnit = TerraUnitTypes.omuraDrone;
                    rangeOverride = 500f;
                }};
            }},
            new Weapon("terra-heavy-gun"){{
                reload = 158f + (3f * 18f);
                x = -77f / 4f;
                y = -121.5f / 4f;
                shootY = 33f / 4f;
                rotate = true;
                rotateSpeed = 2f;
                shootSound = Sounds.shootScepterSecondary;
                shootSoundVolume = 1.1f;
                chargeSound = Sounds.chargeVela;
                parentizeEffects = true;
                
                mirror = false;
                recoils = 2;
                recoil = 0f;
                recoilTime = 6f;
                layerOffset = 0.001f;
                cooldownTime = 90f;
                shoot = new ShootBarrel(){{
                    shots = 18;
                    shotDelay = 3f;
                    firstShotDelay = 40f;
                    barrels = new float[]{
                        10f / 4, 11f / 4, 0f, 
                        -10f / 4, 11f / 4, 0f
                    };
                }};
                parts.add(
                    new RegionPart("-barrel-l") {{
                        mirror = false;
                        under = true;
                        recoilIndex = 0;
                        progress = heatProgress = PartProgress.recoil;
                        moveY = -1.25f;
                    }},
                    new RegionPart("-barrel-r") {{
                        mirror = false;
                        under = true;
                        recoilIndex = 1;
                        progress = heatProgress = PartProgress.recoil;
                        moveY = -1.25f;
                    }}
                );
                bullet = new LaserBoltBulletType(3.7f, 26){{
                    scaleKeepVelocity = true;
                    inaccuracy = 3f;
                    width = 2.5f;
                    height = 6f;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    chargeEffect = new Effect(41f, 100f, e -> {
                        color(Pal.stat);
                        stroke(e.fin() * 2f);
                
                        Fill.circle(e.x, e.y, e.fin() * 6);
                
                        randLenVectors(e.id, 20, 40f * e.fout(), (x, y) -> {
                            Fill.circle(e.x + x, e.y + y, e.fin() * 1.5f);
                            Drawf.light(e.x + x, e.y + y, e.fin() * 3f, Pal.stat, 0.7f);
                        });
                
                        color();
                
                        Fill.circle(e.x, e.y, e.fin() * 3);
                        Drawf.light(e.x, e.y, e.fin() * 6f, Pal.stat, 0.7f);
                    }).followParent(true).rotWithParent(true);
                    
                    trailWidth = 1.6f;
                    trailLength = 4;
                    shootEffect = Fx.shootSmallColor;
                    smokeEffect = Fx.hitLaserColor;
                    backColor = trailColor = Pal.yellowBoltFront;
                    hitColor = Pal.yellowBoltFront;
                    frontColor = Color.white;
                    lightColor = Pal.yellowBoltFront;

                    lifetime = 70f;
                    shake = 1.3f;
                    buildingDamageMultiplier = 0.4f;
                    shieldDamageMultiplier = 1.6f;
                    armorMultiplier = 0.2f;

                    fragBullets = 1;
                    fragRandomSpread = 30f;
                    fragLifeMax = 2f;
                    fragOffsetMax = 2f;
                    fragBullet = new LaserBulletType(){{
                        damage = 18f;
                        buildingDamageMultiplier = 0.5f;
                        shieldDamageMultiplier = 1.4f;
                        armorMultiplier = 0.3f;
                        sideAngle = 25f;
                        sideWidth = 1.5f;
                        sideLength = 14f;
                        width = 10f;
                        length = 40f;
                        lifetime = 20f;
                        colors = new Color[]{Pal.yellowBoltFront.cpy().a(0.4f), Pal.yellowBoltFront, Color.white};
                    }};
                }};
            }},
            new Weapon("terra-heavy-rockets"){{
                reload = 70f + (4f * 7f);
                x = 77f / 4f;
                y = -121.5f / 4f;
                shootY = 0f;
                rotate = true;
                rotateSpeed = 2.6f;
                mirror = false;
                shoot = new ShootBarrel(){{
                    shots = 7;
                    shotDelay = 4f;
                    barrels = new float[]{
                        0f, 20f / 4, 0f, 
                        -9f / 4, 25f / 4, 0f, 
                        0f, 29f / 4, 0f, 
                        9f / 4, 25f / 4, 0f
                    };
                }};
                shootSound = Sounds.shootMissileLong;
                bullet = new BulletType(){{
                    inaccuracy = 10f;
                    shake = 1.3f;
                    speed = 0.001f;
                    keepVelocity = false;
                    spawnUnit = TerraUnitTypes.omuraMissile;
                }};
            }}
        );
    }
}
