package terra.content;

import arc.*;
import mindustry.*;

public class SettingAdder {

    public static void addGraphicSetting(String key){
        Vars.ui.settings.graphics.checkPref(key, Core.settings.getBool(key));
    }

    public static void addGameSetting(String key){
        Vars.ui.settings.game.checkPref(key, Core.settings.getBool(key));
    }

    public static void init(){
        addGameSetting("unitsquality");
    }
}
