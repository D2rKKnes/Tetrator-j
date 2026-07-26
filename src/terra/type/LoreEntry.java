package terra.type;

import arc.*;
import arc.scene.ui.layout.*;
import mindustry.ctype.*;
import mindustry.game.*;

/** This class is only for displaying lore in the content database. */
public class LoreEntry extends UnlockableContent{
    private static final String bundleContentPrefix = "lore";

    public LoreEntry(String name){
        super(name);
        allDatabaseTabs = true;
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
    }

    @Override
    public void loadIcon(){
        super.loadIcon();
        if(!fullIcon || !fullIcon.found()) fullIcon = Core.atlas.find("terra-book");
        if(!uiIcon || !uiIcon.found()) uiIcon = Core.atlas.find("terra-book");
    }

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }
}
