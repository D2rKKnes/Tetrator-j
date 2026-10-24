package terra.util;

import arc.*;
import arc.scene.style.*;
import mindustry.content.*;
import mindustry.gen.*;

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

        if (sunIcon != null) {
            Icon.icons.put("terra-sun", sunIcon);
        }
        if (pulsarIcon != null) {
            Icon.icons.put("terra-pulsar", pulsarIcon);
        }
        if (moonIcon != null) {
            Icon.icons.put("terra-satellite", moonIcon);
        }
        if (nanPlanetIcon != null) {
            Icon.icons.put("terra-unknown-planet", nanPlanetIcon);
        }
    }

    static TextureRegionDrawable get(String name){
        return new TextureRegionDrawable(Core.atlas.find("terra-" + name));
    }

    public static TextureRegionDrawable get(TextureRegionDrawable icon){
        return new TextureRegionDrawable(icon);
    }
}
