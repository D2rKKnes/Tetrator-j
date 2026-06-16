package terra;

import arc.*;
import arc.util.*;
import arc.Events;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.Links;
import mindustry.content.Planets;
import terra.content.*;
import terra.world.blocks.multiblock.InnerBlock;
import terra.type.units.EntityRegister;
import terra.maps.planets.*;
import terra.util.*;

public class TerraMod extends Mod{
    public static Links.LinkEntry[] links;
    public TerraMod(){
        Events.on(ClientLoadEvent.class, e -> {
            Planets.verilus.generator = new VerilusAsteroidGenerator();
        });
    }
    
    @Override
    public void loadContent(){
        Utils.init();
        EntityRegister.load();
        InnerBlock.load();
        TerraSounds.load();
        //TerraFx.load();
        TerraStatusEffects.load();
        TerraItems.load();
        TerraLiquids.load();
        TerraEnvironmentBlocks.load();
        TerraUnitTypes.load();
        TerraBlocks.load();
        TerraSectorPresets.load();
        TerraVanilaTree.load();
        TerraVerilusTree.load();
    }
}
