package terra.content;
import arc.func.Prov;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.noise.Simplex;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.geom.Vec3;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.maps.planet.*;
import mindustry.ui.dialogs.PlanetDialog;
import mindustry.world.meta.*;
import mindustry.content.Blocks;
import terra.type.*;
import terra.maps.planets.*;
import terra.maps.generators.*;
import terra.graphics.g3d.*;

import static arc.Core.atlas;

public class TerraPlanets{

    public static Planet nebula, deltaOmega, copis, RXS, testification;
    public static void load(){
        PlanetDialog.debugSelect = true;
        nebula = new BetterPlanet("nebula", Planets.tantros, 0.5f, 1){{
            generator = new NebulaMoonGenerator();
            meshLoader = () -> new MultiMesh(
                new BetterPlanet.AtmosphereHexMesh(4),
                new HexMesh(this, 4),
                new QuadMesh(this, "terra-nebula-ring1"){{
                    radius = 0.75f;
                    this.normal = new Vec3(Vec3.Y).rotate(Vec3.X, -20f);
                    stroke = 0.1f;
                    updateMesh();
                }}
            );
            accessible = false;
            alwaysUnlocked = false;
            orbitSpacing = 1;
            orbitRadius = 5.8f;
            orbitOffset = 180;
            drawOrbit = true;
            bloom = true;
            minZoom = 1.7f;
            maxZoom = 3;
            iconColor = Color.valueOf("c2bffb");
            hasAtmosphere = true;
            atmosphereColor = Color.white;
            atmosphereRadIn = -0.025f;
            atmosphereRadOut = 0.1f;
        }};

        deltaOmega = new BetterPlanet("delta-omega", null, 40f){{
            bloom = true;
            accessible = false;
            iconColor = Color.valueOf("ff2222");
            solarSystem = this;
            meshLoader = () -> new SunMesh(
                this, 7,
                5, 0.3, 2.4, 1.3, 1,
                1.5f,
                Color.valueOf("ca0808"),
                Color.valueOf("db1313"),
                Color.valueOf("ff2222"),
                Color.valueOf("ff4135"),
                Color.valueOf("fc5853"),
                Color.valueOf("ff6556")
            );
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 5, 1f, 1.1f, 5, Color.valueOf("db1313").a(0.25f), 3, 0.42f, 1f, 0.15f),
                new HexSkyMesh(this, 8, 1.4f, 1.15f, 6, Color.valueOf("ca0808").a(0.25f), 3, 0.42f, 1.2f, 0.14f),
                new HexSkyMesh(this, 11, 2f, 1.2f, 7, Color.valueOf("a90606").a(0.25f), 3, 0.42f, 1.4f, 0.13f)
            );
        }};
        
        RXS = new BetterPlanet("1RXS", null, 0.7f){{
            bloom = true;
            accessible = false;
            iconColor = Color.valueOf("5d47ff");
            solarSystem = this;
            meshLoader = () -> new SunMesh(
                this, 6,
                5, 0.3, 2.7, 1.2, 1,
                1.6f,
                Color.valueOf("451cff"),
                Color.valueOf("3f3fff"),
                Color.valueOf("5d47ff"),
                Color.valueOf("6d47ff"),
                Color.valueOf("9771ff"),
                Color.valueOf("b7a0ff")
            );
        }};
        testification = new Planet("testification", TerraPlanets.RXS, 1f ,3){{
            generator = new TantrosPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 4);
            atmosphereColor = Color.valueOf("021042");
            iconColor = Color.valueOf("1a1f73");
            allowWaves = true;
            allowSectorInvasion = true;
            allowLaunchSchematics = true;
            enemyCoreSpawnReplace = true;
            allowLaunchLoadout = true;
            orbitRadius = 5;
            startSector = 10;
            atmosphereRadIn = -0.01f;
            atmosphereRadOut = 0.3f;
            defaultEnv = Env.underwater | Env.terrestrial;
            alwaysUnlocked = accessible = true;
            ruleSetter = r -> {
                r.waveTeam = Team.crux;
                r.placeRangeCheck = false;
            };
        }};
    }
}
