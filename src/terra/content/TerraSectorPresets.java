package terra.content;

import mindustry.Vars;
import mindustry.maps.generators.FileMapGenerator;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.Planets;

public class TerraSectorPresets {
    public static SectorPreset verilus;

    private static class PlanetFileMapGenerator extends FileMapGenerator {
        private final Planet planet;

        public PlanetFileMapGenerator(Planet planet, SectorPreset preset) {
            super(preset);
            this.planet = planet;
        }

        @Override
        public void generate(Tiles tiles, WorldParams params) {
            if (planet.generator != null) {
                planet.generator.generate(tiles, params);
            } else {
                throw new RuntimeException("Planet has no generator");
            }
        }
    }

    private static class PlanetSectorPreset extends SectorPreset {
        public PlanetSectorPreset(String name, Planet planet, int sectorId) {
            super(name, null, Vars.mods.getMod("terra"));
            this.generator = new PlanetFileMapGenerator(planet, this);
            initialize(planet, sectorId);
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
