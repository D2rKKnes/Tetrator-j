package terra.util;

import arc.*;
import arc.scene.style.*;
import mindustry.content.*;
import mindustry.graphics.*;

public class TerraIcons{
    public static TextureRegionDrawable
    eyeBleeding, eyeMelting, sunIcon, moonIcon, pulsarIcon, nanPlanetIcon;

    public static void init(){
        eyeBleeding = get("eye-bleeding");
        eyeMelting = get("eye-melting");
        sunIcon = get("sun");
        pulsarIcon = get("pulsar");
        moonIcon = get("satellite");
        nanPlanetIcon = get("unknown-planet");
        //alpha = new TextureRegionDrawable(UnitTypes.alpha.uiIcon);
    }

    static TextureRegionDrawable get(String name){
        return new TextureRegionDrawable(Core.atlas.find("terra-" + name));
    }

    public static TextureRegionDrawable get(TextureRegionDrawable icon){
        return new TextureRegionDrawable(icon);
    }
}
