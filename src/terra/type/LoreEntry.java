package terra.type;

import arc.scene.ui.layout.*;
import mindustry.ctype.*;
import mindustry.game.*;

/** This class is only for displaying lore in the content database. */
public class LoreEntry extends UnlockableContent{

    public LoreEntry(String name){
        super(name);
        allDatabaseTabs = true;
        hideDetails = false;
        alwaysUnlocked = false;
        databaseCategory = "lore";
        //allDatabaseTabs = true;
    }

    public LoreEntry(String name, boolean alwaysUnlocked){
        super(name);
        this.alwaysUnlocked = alwaysUnlocked;
    }

    @Override
    public void loadIcon(){
        super.loadIcon();
        if(fullIcon == null || !fullIcon.found()){
            fullIcon = Core.atlas.find("book");
        }
        if(uiIcon == null || !uiIcon.found()){
            uiIcon = Core.atlas.find("book");
        }
    }
}
