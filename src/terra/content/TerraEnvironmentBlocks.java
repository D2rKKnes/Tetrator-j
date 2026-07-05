package terra.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
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
        carbonizedThermoxite, thermoxiteCrystal, carbonizedThermoxiteWall, thermoxiteWall, carbonizedThermoxiteCluster, thermoxiteCluster, carbonizedThermoxiteSmallCluster, thermoxiteSmallCluster,
        obsidian, obsidianMagmatic, magma, obsidianWall,
        metalTilesSpace, metalWall4, 
        oreRawThermoxite, oreThermoxite, silver;

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
        Blocks.carbonWall.attributes.set(TerraAttributes.graphite, 0.1f);
        Blocks.graphiticWall.attributes.set(TerraAttributes.graphite, 1f);
        Blocks.sand.attributes.set(Attribute.sand, 1f);
        Blocks.darksand.attributes.set(Attribute.sand, 1f);
        Blocks.stone.attributes.set(Attribute.sand, 1f / 4);
        Blocks.craters.attributes.set(Attribute.sand, 1f / 4);
        Blocks.charr.attributes.set(Attribute.sand, 1f / 5);
        Blocks.ferricStone.attributes.set(Attribute.sand, 1f / 2);
        Blocks.ferricCraters.attributes.set(Attribute.sand, 1f / 2);
        
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

        obsidian = new Floor("obsidian") {{
            variants = 9;
            attributes.set(Attribute.water, -0.25f);
        }};
        obsidianMagmatic = new Floor("obsidian-magmatic") {{
            variants = 9;
            attributes.set(Attribute.heat, 0.75f);
            attributes.set(Attribute.water, -1.5f);
            speedMultiplier = 0.9f;
            emitLight = true;
            lightRadius = 30f;
            lightColor = Color.red.cpy().a(0.25f);
        }};
        magma = new Floor("magma"){{
            drownTime = 200f;
            status = StatusEffects.melting;
            statusDuration = 240f;
            speedMultiplier = 0.12f;
            variants = 0;
            liquidDrop = Liquids.slag;
            isLiquid = true;
            cacheLayer = CacheLayer.slag;
            attributes.set(Attribute.heat, 1f);
            attributes.set(Attribute.water, -2f);

            emitLight = true;
            lightRadius = 40f;
            lightColor = Color.red.cpy().a(0.4f);
            obstructsLight = true;
            forceDrawLight = true;
        }};
        obsidianWall = new StaticWall("obsidian-wall"){{
            variants = 4;
            obsidian.asFloor().wall = this;
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

        oreRawThermoxite = new OreBlock(TerraItems.rawThermoxite) {{
            variants = 3;
        }};
        oreThermoxite = new OreBlock(TerraItems.thermoxite) {{
            variants = 3;
        }};
        silver = new OreBlock(TerraItems.silver) {{
            variants = 3;
        }};
    }
}
