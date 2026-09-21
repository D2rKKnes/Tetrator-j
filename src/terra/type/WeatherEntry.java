package terra.type;

import arc.*;
import arc.scene.ui.layout.*;
import mindustry.ctype.*;
import mindustry.game.*;

/** This class is only for displaying weather in the content database. */
public class WeatherEntry extends UnlockableContent{
    private static final String bundleContentPrefix = "weather";

    public WeatherEntry(String name){
        super(name);
        allDatabaseTabs = true;
        hideDetails = false;
        alwaysUnlocked = true;
        databaseCategory = "weather";

        this.localizedName = Core.bundle.get(bundleContentPrefix + "." + this.name + ".name", this.name);
        this.description = Core.bundle.getOrNull(bundleContentPrefix + "." + this.name + ".description");
        this.details = Core.bundle.getOrNull(bundleContentPrefix + "." + this.name + ".details");
        this.credit = Core.bundle.getOrNull(bundleContentPrefix + "." + this.name + ".credit");
    }

    public WeatherEntry(String name, boolean alwaysUnlocked){
        this(name);
        this.alwaysUnlocked = alwaysUnlocked;
    }

    // @Override
    // public void loadIcon(){
    //     super.loadIcon();
    //     if(fullIcon == null || !fullIcon.found()) fullIcon = Core.atlas.find("terra-book");
    //     if(uiIcon == null || !uiIcon.found()) uiIcon = Core.atlas.find("terra-book");
    // }

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }
}
