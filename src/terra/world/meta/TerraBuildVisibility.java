package terra.world.meta;

import arc.func.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.world.meta.*;

public class TerraBuildVisibility{
    specialContent = new BuildVisibility(() -> Core.settings.getBool("enableblocks", false) ? true : (Vars.state == null || Vars.state.rules.infiniteResources));
}
