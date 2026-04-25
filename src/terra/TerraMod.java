package terra;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import terra.content.*;
import terra.util.*;

public class TerraMod extends Mod{
    @Override
    public void loadContent(){
        Utils.init();
        TerraItems.load();
        TerraSounds.load();
        TerraEnvironmentBlocks.load();
        TerraStatusEffects.load();
        TerraUnitTypes.load();
        TerraBlocks.load();
    }
}
