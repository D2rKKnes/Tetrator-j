package terra.content;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.type.*;

import static mindustry.content.Items.*;

public class TerraItems{
    public static Item

            carbon, diamondDust, cryonite, darkSteel, rawThermoxite, thermoxite;

    public static void load(){
        carbon = new Item("carbon", Color.valueOf("3c4448")){{
            flammability = 1.25f;
            hardness = 2;
        }};
        diamondDust = new Item("diamond-dust", Color.valueOf("ffffff")){{
            cost = 0.75f;
            hardness = 4;
        }};
        cryotite = new Item("cryotite", Color.valueOf("b1f6fa")){{
            flammability = -1f;
            explosiveness = 1f;
            hardness = 2;
        }};
        darkSteel = new Item("dark-steel", Color.valueOf("6e7080")){{
            cost = 2f;
            hardness = 5;
        }};
        rawThermoxite = new Item("raw-thermoxite", Color.valueOf("ff7163")){{
            cost = 0.8f;
            hardness = 4;
        }};
        thermoxite = new Item("thermoxite", Color.valueOf("e13131")){{
            cost = 1.5f;
            hardness = 5;
        }};
    }
}
