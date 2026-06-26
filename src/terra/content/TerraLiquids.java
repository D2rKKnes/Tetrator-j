package terra.content;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.graphics.Pal;

public class TerraLiquids{
    public static Liquid
  
    carbonDioxide, fissilePlasma;

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
    }
}
