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
import mindustry.ui.dialogs.PlanetDialog;
import mindustry.world.meta.*;
import mindustry.content.Blocks;
import terra.type.*;

import static arc.Core.atlas;

public class TerraPlanets{

    public static Planet RXS, testification;
    public static void load(){
        PlanetDialog.debugSelect = true;
        RXS = new PulsarPlanet("1RXS", null, 0.7f){{
            bloom = true;
            accessible = false;
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
        testification = new Planet("testification", RXS, 0.5f ,3){{
            meshLoader = () -> new MultiMesh(
                    new HexMesh(this, 4),
                    new HexSkyMesh(this, 11, 0.95f, 0.11f, 6, Color.valueOf("c2c2e2").a(0.75f), 8, 0.45f, 1.6f, 0.5f),
                    new HexSkyMesh(this, 1, 1.3f, 0.15f, 6, Color.valueOf("c2c2e2").a(0.75f), 6, 0.45f, 0.6f, 0.21f)
            );
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
            alwaysUnlocked = true;
            ruleSetter = r -> {
                r.waveTeam = Team.crux;
                r.placeRangeCheck = false;
                r.showSpawns = false;
            };
        }};
    }
}
