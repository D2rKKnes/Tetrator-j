package terra.world.meta;

import arc.*;
import arc.func.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.world.meta.*;

public class TerraBuildVisibility{
    public static final BuildVisibility
    
    specialContent = new BuildVisibility(() -> Core.settings.getBool("enableblocks", false) || (Vars.state == null || Vars.state.rules.infiniteResources));
}
