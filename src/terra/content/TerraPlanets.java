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

    public static Planet RXS;
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
    }
}
