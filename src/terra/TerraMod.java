package terra;

import arc.*;
import arc.util.*;
import arc.Events;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import mindustry.content.Planets;
import terra.content.*;
import terra.maps.planets.*;
import terra.util.*;

public class TerraMod extends Mod{
    public TerraMod(){
        Events.on(ClientLoadEvent.class, e -> {
            Planets.verilus.generator = new VerilusAsteroidGenerator();
            Planets.verilus.rebuild();
        });
    }
    
    @Override
    public void loadContent(){
        Utils.init();
        TerraSounds.load();
        TerraStatusEffects.load();
        TerraItems.load();
        TerraEnvironmentBlocks.load();
        TerraUnitTypes.load();
        TerraBlocks.load();
    }
}
