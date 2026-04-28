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
        TerraSounds.load();
        TerraStatusEffects.load();
        TerraItems.load();
        TerraEnvironmentBlocks.load();
        TerraUnitTypes.load();
        TerraBlocks.load();
    }
}
