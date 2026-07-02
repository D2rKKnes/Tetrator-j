package terra.util;

import arc.func.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.TextField.*;
import arc.scene.ui.layout.*;
import arc.scene.utils.*;
import arc.util.*;
import blui.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.SettingsMenuDialog.*;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.*;
import mindustry.world.meta.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class TerraSettings{
    public static void init(){
        ui.settings.addCategory(bundle.get("setting.tu-title"), "terra-eye-bleeding", t -> {
            //t.pref(new Banner("test-utils-settings-banner", -1));
            //t.pref(new Separator(8));
            t.checkPref("unitsquality", false);
        });
    }

    /** Not a setting, but rather adds an image to the settings menu. */
    static class Banner extends Setting{
        float width;

        public Banner(String name, float width){
            super(name);
            this.width = width;
        }

        @Override
        public void add(SettingsTable table){
            Image i = new Image(new TextureRegionDrawable(atlas.find(name)), Scaling.fit);
            Cell<Image> ci = table.add(i).padTop(3f);

            if(width > 0){
                ci.width(width);
            }else{
                ci.grow();
            }

            table.row();
        }
    }

    /** Not a setting, but rather a space between settings. */
    static class Separator extends Setting{
        float height;

        public Separator(float height){
            super("");
            this.height = height;
        }

        @Override
        public void add(SettingsTable table){
            table.image(Tex.clear).height(height).padTop(3f);
            table.row();
        }
    }
}
