package terra.type;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureAtlas.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.type.weather.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

/** This class is only for displaying lore in the content database. */
public class LoreEntry extends UnlockableContent{
    private static final String bundleContentPrefix = "lore";

    public LoreEntry(String name){
        super(name);
        hideDetails = false;
        alwaysUnlocked = false;
        databaseCategory = "lore";
        //allDatabaseTabs = true;

        this.localizedName = Core.bundle.get(bundleContentPrefix + "." + this.name + ".name", this.name);
        this.description = Core.bundle.getOrNull(bundleContentPrefix + "." + this.name + ".description");
        this.details = Core.bundle.getOrNull(bundleContentPrefix + "." + this.name + ".details");
        this.credit = Core.bundle.getOrNull(bundleContentPrefix + "." + this.name + ".credit");
    }

    public LoreEntry(String name, boolean alwaysUnlocked){
        this(name);
        this.alwaysUnlocked = alwaysUnlocked;
        this.allDatabaseTabs = true;
    }

    @Override
    public void loadIcon(){
        super.loadIcon();
        if(fullIcon == null || !fullIcon.found()) fullIcon = Core.atlas.find("terra-book");
        if(uiIcon == null || !uiIcon.found()) uiIcon = Core.atlas.find("terra-book");
    }

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }
}
