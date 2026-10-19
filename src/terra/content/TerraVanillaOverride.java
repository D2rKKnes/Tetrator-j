package terra.content;

import mindustry.*;
import mindustry.io.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.type.unit.*;  
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.meta.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.gen.*;
import arc.struct.*;
import arc.util.*;
import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import terra.content.*;

import static mindustry.type.ItemStack.with;

public class TerraVanillaOverride {
    public static void load() {
        Liquids.gallium.hidden = false;
    
        Items.fissileMatter.hidden = false;
        Items.dormantCyst.hidden = false;

        StatusEffects.invincible.alwaysUnlocked = true;
        StatusEffects.invincible.show = true;
        StatusEffects.invincible.color = Color.valueOf("bf92f9");
        StatusEffects.shielded.show = true;
        StatusEffects.muddy.alwaysUnlocked = true;
        StatusEffects.muddy.show = true;
        StatusEffects.slow.show = true;
        StatusEffects.disarmed.show = true;

        Weathers.suspendParticles.hidden = false;

        Planets.sun.iconColor = Color.valueOf("ffc64c");
        
        Planets.tantros.alwaysUnlocked = true;
        Planets.tantros.accessible = true;
        Planets.tantros.visible = true;
        Planets.tantros.atmosphereColor = Color.valueOf("143d33");
        Planets.tantros.ruleSetter = r -> {
            r.waveTeam = Team.blue;
        };
        Planets.tantros.cloudMeshLoader = () -> new MultiMesh(
            new HexSkyMesh(Planets.tantros, 5, 0.15f, 0.14f, 5, Color.valueOf("96c0e3").a(0.75f), 2, 0.45f, 0.9f, 0.42f),
            new HexSkyMesh(Planets.tantros, 8, 0.6f, 0.15f, 5, Color.valueOf("bcd7e6").a(0.75f), 2, 0.45f, 1.1f, 0.44f)
        );
        Planets.gier.alwaysUnlocked = Planets.notva.alwaysUnlocked = Planets.verilus.alwaysUnlocked = true;
        Planets.gier.accessible = Planets.notva.accessible = Planets.verilus.accessible = true;
        Planets.gier.drawOrbit = true;
        Planets.gier.defaultEnv = Planets.notva.defaultEnv = Planets.verilus.defaultEnv = Env.space | Env.terrestrial;
        Planets.gier.clearSectorOnLose = Planets.notva.clearSectorOnLose = Planets.verilus.clearSectorOnLose = true;
    
        Blocks.thruster.buildVisibility = BuildVisibility.shown;
        Blocks.launchPad.buildVisibility = BuildVisibility.sandboxOnly;
        Blocks.heatReactor.buildVisibility = BuildVisibility.shown;
        Blocks.slagCentrifuge.buildVisibility = BuildVisibility.shown;
        Blocks.shieldProjector.buildVisibility = BuildVisibility.shown;
        Blocks.shieldProjector.requirements = with(Items.graphite, 6200, Items.thorium, 3000, Items.silicon, 5000, Items.phaseFabric, 3100, Items.surgeAlloy, 2200, Items.beryllium, 3800, Items.tungsten, 3800, Items.oxide, 2500, Items.carbide, 2200, Items.fissileMatter, 1200);
        Blocks.shieldProjector.researchCostMultiplier = 0.03f;
        Blocks.largeShieldProjector.buildVisibility = BuildVisibility.shown;
        Blocks.largeShieldProjector.requirements = with(Items.graphite, 6200 * 3, Items.thorium, 3000 * 3, Items.silicon, 5000 * 3, Items.phaseFabric, 3100 * 3, Items.surgeAlloy, 2200 * 3, Items.beryllium, 3800 * 3, Items.tungsten, 3800 * 3, Items.oxide, 2500 * 3, Items.carbide, 2200 * 3, Items.fissileMatter, 1200 * 3);
        Blocks.largeShieldProjector.researchCostMultiplier = 0.03f;
        Blocks.powerNode.requirements = with(Items.graphite, 2, Items.lead, 6);
        Blocks.fuse.requirements = with(Items.lead, 225, Items.graphite, 225, Items.thorium, 100);
        ((ItemTurret) Blocks.duo).ammoTypes.put(
            Items.titanium, new BasicBulletType(3.5f, 26){{
                width = 9f;
                height = 12f;
                ammoMultiplier = 3;
                lifetime = 60f;
                reloadMultiplier = 0.75f;
                rangeChange = 20f;
                
                hitEffect = despawnEffect = Fx.hitBulletColor;
                hitColor = backColor = trailColor = Color.valueOf("919fe7");
                frontColor = Color.valueOf("a4b8fa");
            }});
        ((ItemTurret) Blocks.hail).ammoTypes.put(
            Items.titanium, new ArtilleryBulletType(3f, 40){{
                knockback = 0.8f;
                lifetime = 80f;
                width = height = 13f;
                collidesTiles = false;
                splashDamageRadius = 25f * 0.75f;
                splashDamage = 66f;
                reloadMultiplier = 0.75f;
                ammoMultiplier = 3f;

                hitColor = backColor = trailColor = Color.valueOf("919fe7");
                frontColor = Color.valueOf("a4b8fa");
                despawnEffect = Fx.hitBulletColor;
            }});
        ((ItemTurret) Blocks.fuse).ammoTypes.put(
            Items.surgeAlloy, new ShrapnelBulletType(){{
                length = 130;
                rangeChange = 30f;
                damage = 245f;
                ammoMultiplier = 6f;
                reloadMultiplier = 0.7f;
                width = 25f;
                status = StatusEffects.shocked;
                toColor = Pal.surgeAmmoFront;
                shootEffect = smokeEffect = TerraFx.fuseShoot;
                spawnBulletRandomSpread = 60f;
                spawnBullets = new Seq.with(
                    new MultiBulletType(4, new LightningBulletType(){{
                        shootEffect = smokeEffect = Fx.none;
                        damage = 8;
                        lightningLength = 8;
                        lightningLengthRand = 5;
                        lightningColor = Pal.surgeAmmoFront;
                        collidesAir = true;
                        buildingDamageMultiplier = 0.4f;
                        shieldDamageMultiplier = 0.3f;
                    }})
                );
            }});
        ((ItemTurret) Blocks.breach).ammoTypes.put(
            Items.phaseFabric, new BasicBulletType(10f, 165){{
                width = 14f;
                height = 20f;
                hitSize = 7f;
                lifetime = 30f;
                rangeChange = 72f;
                shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                smokeEffect = Fx.shootBigSmoke;
                hitEffect = despawnEffect = Fx.hitBulletColor;
                ammoMultiplier = 2;
                reloadMultiplier = 0.5f;
                pierce = true;
                pierceBuilding = true;
                buildingDamageMultiplier = 0.3f;
                hitColor = backColor = trailColor = Color.valueOf("ffd59e");
                frontColor = Color.white;
                
                trailEffect = Fx.disperseTrail;
                trailInterval = 2;
                trailRotation = true;
                trailWidth = 2.2f;
                trailLength = 11;
                
                despawnHit = fragOnHit = true;
                fragBullets = 1;
                fragRandomSpread = 0f;
                fragAngle = 180f;
                fragVelocityMin = 1;
                fragOffsetMin = fragOffsetMax = 0;
                fragBullet = new BasicBulletType(10f, 130){{
                    width = 14f;
                    height = 20f;
                    hitSize = 7f;
                    lifetime = 28f;
                    rangeChange = 72f;
                    shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                    smokeEffect = Fx.shootBigSmoke;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    ammoMultiplier = 1;
                    reloadMultiplier = 1f;
                    pierce = true;
                    pierceBuilding = true;
                    buildingDamageMultiplier = 0.3f;
                    hitColor = backColor = trailColor = Color.valueOf("ffd59e");
                    frontColor = Color.white;
                    
                    trailEffect = Fx.disperseTrail;
                    trailInterval = 2;
                    trailRotation = true;
                    trailWidth = 2.2f;
                    trailLength = 11;
                }};
            }});

        ((Floor) Blocks.slag).supportsOverlay = true;
        ((Floor) Blocks.tar).supportsOverlay = true;
        ((Floor) Blocks.arkyciteFloor).supportsOverlay = true;
    
        UnitTypes.renale.hidden = false;
        UnitTypes.latum.hidden = false;
        UnitTypes.manifold.hidden = false;
        UnitTypes.assemblyDrone.hidden = false;
        UnitTypes.mono.mineWalls = true;
        ((ErekirUnitType) UnitTypes.evoke).controller = u -> u.team.isAI() ? new BuilderAI(true, 500f) : new CommandAI();
        ((ErekirUnitType) UnitTypes.incite).controller = u -> u.team.isAI() ? new BuilderAI(true, 500f) : new CommandAI();
        ((ErekirUnitType) UnitTypes.emanate).controller = u -> u.team.isAI() ? new BuilderAI(true, 500f) : new CommandAI();
        Vars.content.unit("anthicus-missile").hidden = false;
        Vars.content.unit("quell-missile").hidden = false;
        Vars.content.unit("disrupt-missile").hidden = false;
        Vars.content.unit("scathe-missile").hidden = false;
        Vars.content.unit("scathe-missile-phase").hidden = false;
        Vars.content.unit("scathe-missile-surge").hidden = false;
        Vars.content.unit("scathe-missile-surge-split").hidden = false;
    }
}
