package terra.content;


import mindustry.type.*;
import mindustry.content.Planets;

import static mindustry.content.Planets.*;

public class TerraSectorPresets {
    public static SectorPreset
            verilus;

    public static void load(){
        //region Serpulo & Verilus

        verilus = new SectorPreset("verilus", Planets.verilus, 0){{
            alwaysUnlocked = true;
            addStartingItems = true;
            //captureWave = 180;
            difficulty = 8;
            allowLaunchSchematics = true;
            overrideLaunchDefaults = true;
        }};
        //endregion
    }
}
