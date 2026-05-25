package terra.content;


import mindustry.type.*;
import mindustry.content.Planets;
import mindustry.Vars;

import static mindustry.content.Planets.*;

public class TerraSectorPresets {
    public static SectorPreset
            verilus;
    private static class PlanetSectorPreset extends SectorPreset {
        public PlanetSectorPreset(String name, Planet planet, int sector) {
            super(name, null, Vars.mods.getMod("terra"));
            this.generator = planet.generator;
            initialize(planet, sector);
        }
    }
    public static void load(){
        //region Serpulo & Verilus

        verilus = new PlanetSectorPreset("verilus", Planets.verilus, 0){{
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
