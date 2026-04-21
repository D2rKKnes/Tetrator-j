package terra;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import terra.content.TerraItems;
import terra.content.TerraEnvironmentBlocks;

public class TerraMod extends Mod{
    @Override
    public void loadContent(){
        TerraItems.load();
        TerraEnvironmentBlocks.load();
    }
}
