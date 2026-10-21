package terra;

import arc.*;
import arc.struct.*;
import arc.util.*;
import arc.Events;
import mindustry.*;
import mindustry.type.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.Links;
import mindustry.ui.dialogs.*;
import mindustry.ui.dialogs.JoinDialog.*;
import mindustry.ui.fragments.*;
import mindustry.content.*;
import terra.content.*;
import terra.world.blocks.RouterSorter;
import terra.world.blocks.multiblock.InnerBlock;
import terra.type.units.EntityRegister;
import terra.maps.planets.*;
import terra.util.*;
import terra.special.*;
import terra.graphics.menu.*;
import terra.graphics.shaders.*;

public class TerraMod extends Mod{
    public static Links.LinkEntry[] links;
    public TerraMod(){
        Events.on(ClientLoadEvent.class, e -> {
            try{
                Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", new TerraMenuRenderer());
            }catch(Exception ex){
                Log.err("Failed to replace renderer", ex);
            }

            for (UnitType unit : Vars.content.units()) {
                if (unit == TerraUnitTypes.myDoom) continue;
                unit.immunities.remove(TerraStatusEffects.instantDeath);
            }
            
            Planets.verilus.generator = new VerilusAsteroidGenerator();
            Liquids.gallium.uiIcon = Core.atlas.find("liquid-gallium");
            Liquids.gallium.fullIcon = Core.atlas.find("liquid-gallium");
            Time.runTask(60, () -> {
                AdvancedDataBase.initStats();
            });
        });

        Events.on(FileTreeInitEvent.class, e -> Core.app.post(TerraCacheLayer::load));

        Events.on(DisposeEvent.class, e -> {
            TerraCacheLayer.dispose();
        });
    }
    
    @Override
    public void init(){
        TerraShaders.load();
        TerraIcons.init();
        TerraSettings.init();
        UnitsRarity.init();
    }
    
    @Override
    public void loadContent(){
        Utils.init();
        
        EntityRegister.load();
        InnerBlock.load();
        TerraLore.load();
        TerraSounds.load();
        //TerraFx.load();
        TerraStatusEffects.load();
        TerraItems.load();
        TerraLiquids.load();
        TerraEnvironmentBlocks.load();
        TerraUnitTypes.load();
        TerraBlocks.load();
        TerraLoadouts.load();
        TerraSectorPresets.load();
        TerraPlanets.load();
        TerraVanillaOverride.load();
        TerraVanilaTree.load();
        TerraVerilusTree.load();
    }
}
