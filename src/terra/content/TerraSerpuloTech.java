package terra.content;

import arc.func.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;

import static terra.content.TerraBlocks.*;

import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static mindustry.content.SectorPresets.*;
import static mindustry.content.TechTree.*;
import static mindustry.content.UnitTypes.*;

public class TerraSerpuloTech{
    public static void load(){
        // Serpulo
        vanillaNode(titaniumWall, () -> {
            node(metaglassWall, Seq.with(new SectorComplete(ruinousShores)), () -> {
                node(metaglassWallLarge, () -> {
                    node(metaglassWallHuge, () -> {
                    });
                });
            });
        });
        vanillaNode(plastanumWallLarge, () -> {
            node(plastanumWallHuge, () -> {
            });
        });
        vanillaNode(phaseWallLarge, () -> {
            node(phaseWallHuge, () -> {
            });
        });
        vanillaNode(scrapWallGigantic, () -> {
            node(scrapWallColossol, () -> {
            });
        });
        vanillaNode(doorLarge, () -> {
            node(doorHuge, () -> {
                node(doorGigantic, () -> {
                });
            });
        });
    }
    private static void vanillaNode(UnlockableContent parent, Runnable children){
        vanillaNode("serpulo", parent, children);
    }

    private static void vanillaNode(String tree, UnlockableContent parent, Runnable children){
        context = findNode(TechTree.roots.find(r -> r.name.equals(tree)), n -> n.content == parent);
        children.run();
    }

    private static TechNode findNode(TechNode root, Boolf<TechNode> filter){
        if(filter.get(root)) return root;
        for(TechNode node : root.children){
            TechNode search = findNode(node, filter);
            if(search != null) return search;
        }
        return null;
    }
}
