package terra.content;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.content.StatusEffects;
import mindustry.graphics.Pal;
import terra.type.*;

public class TerraLiquids{
    public static Liquid
  
    carbonDioxide, fissilePlasma, magm, neon;

    public static void load(){
        carbonDioxide = new Liquid("carbon-dioxide", Pal.darkerGray){{
            gas = true;
            heatCapacity = 0.8f;
            viscosity = 0f;
            vaporEffect = TerraFx.arcVapor;
        }};

        fissilePlasma = new Liquid("fissile-plasma", Color.valueOf("bcff73")){{
            gas = true;
            temperature = 3f;
            heatCapacity = 1f;
            lightColor = Color.valueOf("bcff73").a(0.4f);
        }};

        magma = new CellLiquid("magma", Color.valueOf("ff4f49")){{
            temperature = 0.925f;
            viscosity = 0.9f;
            capPuddles = false;
            cells = 3;
            colorFrom = Color.valueOf("ff4f49");
            colorTo = Color.valueOf("d9423e");
            lightColor = Color.red.cpy().a(0.4f);
            effect = StatusEffects.melting;
            incinerable = false;
        }};

        Color[] neonColors = {
            Color.valueOf("fcb4cd"),
            Color.valueOf("b2a4ff"),
            Color.valueOf("a8e0e8")
        };
        neon = new ColorfulFluid("neon", Color.valueOf("a8e0e8"), neonColors){{
            gas = true;
            flammability = 1f;
            viscosity = 0f;
        }};
    }
}
