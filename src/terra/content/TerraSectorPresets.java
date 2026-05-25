package terra.content;

import mindustry.Vars;
import mindustry.game.Rules;
import mindustry.maps.generators.FileMapGenerator;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.Planets;

public class TerraSectorPresets {
    public static SectorPreset verilus;

    private static class PlanetFileMapGenerator extends FileMapGenerator {
        private final Planet planet;
        private final SectorPreset preset;

        public PlanetFileMapGenerator(Planet planet, SectorPreset preset) {
            super(preset);
            this.planet = planet;
        }

        @Override
        public void generate(Tiles tiles, WorldParams params) {
            for (int x = 0; x < tiles.width; x++) {
                for (int y = 0; y < tiles.height; y++) {
                    if (tiles.get(x, y) == null) {
                        tiles.set(x, y, new Tile(x, y));
                    }
                }
            }

            Sector sector = params.sector;
            if (sector == null && preset != null && preset.sector != null) {
                sector = preset.sector;
            }
            if (sector == null) {
                throw new RuntimeException("No sector available for generation");
            }

            Rules rules = Vars.state.rules;
            Sector oldSector = rules != null ? rules.sector : null;
            try {
                if (rules == null) {
                    Vars.state.rules = new Rules();
                }
                Vars.state.rules.sector = sector;

                if (planet.generator != null) {
                    planet.generator.generate(tiles, params);
                } else {
                    throw new RuntimeException("Planet has no generator");
                }
            } finally {
                if (rules != null) {
                    Vars.state.rules.sector = oldSector;
                } else {
                    Vars.state.rules = null;
                }
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
