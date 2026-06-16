package terra.content;

import arc.func.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;
import terra.content.*;

import static terra.content.TerraBlocks.*;

import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static mindustry.content.SectorPresets.*;
import static mindustry.content.TechTree.*;
import static mindustry.content.UnitTypes.*;

public class TerraVanilaTree{
    static TechTree.TechNode context = null;
    public static void load(){
        // Serpulo
        vanillaNode(batteryLarge, () -> {
            node(multicellBattery, () -> {
            });
        });
        vanillaNode(coreFoundation, () -> {
            node(coreSolaris, () -> {
            });
        });
        vanillaNode(copperWallLarge, () -> {
            node(metaglassWall, Seq.with(new OnSector(ruinousShores)), () -> {
                node(metaglassWallLarge, () -> {
                    node(metaglassWallHuge, () -> {
                    });
                });
            });
        });
        vanillaNode(plastaniumWallLarge, () -> {
            node(plastaniumWallHuge, () -> {
            });
        });
        vanillaNode(phaseWallLarge, () -> {
            node(phaseWallHuge, () -> {
            });
        });
        vanillaNode(scrapWallGigantic, () -> {
            node(scrapWallColossol, Seq.with(new SectorComplete(facility32m)), () -> {
            });
        });
        vanillaNode(doorLarge, () -> {
            node(doorHuge, Seq.with(new SectorComplete(overgrowth)), () -> {
                node(doorGigantic, () -> {
                });
            });
        });
        vanillaNode(impact0078, () -> {
            node(TerraSectorPresets.verilus, Seq.with(new Research(coreSolaris)), () -> {});
        });
        // Erekir
        String erekir = "erekir";
        vanillaNode(erekir, blastDoor, () -> {
            node(blastGate, Seq.with(new SectorComplete(intersect)), () -> {
            });
            node(blastDoorLarge, () -> {
                node(blastDoorHuge, Seq.with(new SectorComplete(atlas)), () -> {
                });
            });
        });
        vanillaNode(erekir, shieldedWall, () -> {
            node(shieldedWallLarge, () -> {});
        });
        vanillaNode(erekir, beamTower, () -> {
            node(beamBeacon, () -> {});
        });
        vanillaNode(erekir, turbineCondenser, () -> {
            node(reinforcedPanel, Seq.with(new SectorComplete(lake)), () -> {});
        });
        vanillaNode(erekir, oxide, () -> {
            nodeProduce(fissileMatter, () -> {});
        });
        vanillaNode(erekir, neoplasm, () -> {
            nodeProduce(dormantCyst, () -> {});
        });
        vanillaNode(erekir, carbide, () -> {
            nodeProduce(gallium, () -> {});
        });
    }
    //from prog-mats
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

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children){
        TechNode node = new TechNode(context, content, requirements);
        if(objectives != null) node.objectives = objectives;

        TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives){
        node(content, requirements, objectives, () -> {});
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives){
        node(content, content.researchRequirements(), objectives, () -> {});
    }

    private static void node(UnlockableContent content, ItemStack[] requirements){
        node(content, requirements, Seq.with(), () -> {});
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, children);
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives, children);
    }

    private static void node(UnlockableContent content, Runnable children){
        node(content, content.researchRequirements(), children);
    }

    private static void node(UnlockableContent block){
        node(block, () -> {});
    }
}
