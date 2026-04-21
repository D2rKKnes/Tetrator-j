package terra.content;


import terra.content.TerraItems;
import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;

public class ExoEnvironmentBlocks {
    public static Block

        largeTree, iceBoulder, iceRift, 
        carbonizedThermoxite, thermoxiteCrystal, oreRawThermoxite, oreThermoxite;

    public static void load() {
        largeTree = new TreeBlock("large-tree");
        iceBoulder = new Prop("ice-boulder") {{
            variants = 3;
            Blocks.ice.asFloor().decoration = this;
        }};
        iceRift = new SteamVent("ice-rift") {{
            variants = 2;
            parent = blendGroup = Blocks.ice;
            effect = Fx.none;
        }};
        carbonizedThermoxite = new Floor("carbonized-thermoxite") {{
            variants = 4;
        }};
        thermoxiteCrystal = new Floor("thermoxite-crystal") {{
            variants = 1;
        }};
        oreRawThermoxite = new OreBlock(TerraItems.rawThermoxite) {{
            variants = 3;
        }};
        oreThermoxite = new OreBlock(TerraItems.thermoxite) {{
            variants = 3;
        }};
    }
}
