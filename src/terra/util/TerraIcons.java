package terra.util;

import arc.*;
import arc.scene.style.*;
import mindustry.content.*;

public class TerraIcons{
    public static TextureRegionDrawable
    eyeBleeding, eyeMelting;

    public static void init(){
        eyeBleeding = get("eye-bleeding");
        eyeMelting = get("eye-melting");
        //alpha = new TextureRegionDrawable(UnitTypes.alpha.uiIcon);
    }

    static TextureRegionDrawable get(String name){
        return new TextureRegionDrawable(Core.atlas.find("terra-" + name));
    }

    public static TextureRegionDrawable get(TextureRegionDrawable icon){
        return new TextureRegionDrawable(icon);
    }
}
