package terra.content;


import terra.content.TerraItems;
import terra.world.meta.*;
import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;

public class TerraEnvironmentBlocks {
    public static Block

        largeTree, iceBoulder, iceRift, 
        carbonizedThermoxite, thermoxiteCrystal, carbonizedThermoxiteWall, thermoxiteWall, carbonizedThermoxiteCluster, thermoxiteCluster, carbonizedThermoxiteSmallCluster, thermoxiteSmallCluster, oreRawThermoxite, oreThermoxite,
        metalTilesSpace, metalWall4;

    public static void load() {
        Blocks.ice.attributes.set(TerraAttributes.ice, 0.25f);
        Blocks.redIce.attributes.set(TerraAttributes.ice, 0.25f);
        //largeTree = new TreeBlock("large-tree");
        iceBoulder = new Prop("ice-boulder") {{
            variants = 3;
            Blocks.ice.asFloor().decoration = this;
        }};
        iceRift = new SteamVent("ice-rift") {{
            variants = 2;
            parent = blendGroup = Blocks.ice;
            effect = Fx.none;
            dragMultiplier = 0.35f;
            speedMultiplier = 0.9f;
            attributes.set(Attribute.water, 0.4f);
            albedo = 0.65f;
        }};
        
        Blocks.carbonStone.attributes.set(TerraAttributes.carbon, 0.25f);
        Blocks.carbonStone.attributes.set(Attribute.sand, 1f / 8);
        carbonizedThermoxite = new Floor("carbonized-thermoxite") {{
            variants = 4;
            attributes.set(TerraAttributes.carbon, 0.1f);
        }};
        thermoxiteCrystal = new Floor("thermoxite-crystal") {{
            variants = 1;
        }};
        carbonizedThermoxiteWall = new StaticWall("carbonized-thermoxite-wall"){{
            variants = 3;
            carbonizedThermoxite.asFloor().wall = this;
        }};
        thermoxiteWall = new StaticWall("thermoxite-wall"){{
            variants = 3;
            thermoxiteCrystal.asFloor().wall = this;
        }};
        carbonizedThermoxiteCluster = new TallBlock("carbonized-thermoxite-cluster"){{
            variants = 3;
            clipSize = 128f;
        }};
        thermoxiteCluster = new TallBlock("thermoxite-cluster"){{
            variants = 3;
            clipSize = 128f;
        }};
        carbonizedThermoxiteSmallCluster = new Prop("carbonized-thermoxite-small-cluster"){{
            variants = 2;
            customShadow = true;
            carbonizedThermoxite.asFloor().decoration = this;
            obstructsLight = false;
        }};
        thermoxiteSmallCluster = new Prop("thermoxite-small-cluster"){{
            variants = 2;
            customShadow = true;
            thermoxiteCrystal.asFloor().decoration = this;
            obstructsLight = false;
        }};
        oreRawThermoxite = new OreBlock(TerraItems.rawThermoxite) {{
            variants = 3;
        }};
        oreThermoxite = new OreBlock(TerraItems.thermoxite) {{
            variants = 3;
        }};
        
        metalTilesSpace = new Floor("metal-tiles-space"){{
            //cacheLayer = CacheLayer.space;
            placeableOn = false;
            solid = true;
            canShadow = false;
            autotile = true;
            drawEdgeOut = false;
            drawEdgeIn = false;
        }};
        metalWall4 = new StaticWall("metal-wall-4"){{
            autotile = true;
        }};
    }
}
