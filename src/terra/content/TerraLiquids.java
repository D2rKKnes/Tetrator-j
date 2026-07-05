package terra.content;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.content.StatusEffects;
import mindustry.graphics.Pal;

public class TerraLiquids{
    public static Liquid
  
    carbonDioxide, fissilePlasma, magma;

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

        magma = new CellLiquid("magma-liquid", Color.valueOf("d73532")){{
            temperature = 0.8f;
            viscosity = 0.9f;
            capPuddles = false;
            cells = 3;
            colorFrom = Color.valueOf("d74e46");
            colorTo = Color.valueOf("d73532");
            lightColor = Color.red.cpy().a(0.4f);
            effect = StatusEffects.melting;
            incinerable = false;
        }};
    }
}
