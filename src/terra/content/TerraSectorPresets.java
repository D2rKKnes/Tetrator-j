package terra.content;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.WorldParams;
import mindustry.maps.generators.FileMapGenerator;
import mindustry.type.*;
import mindustry.world.Tiles;

import static mindustry.content.Planets.*;

public class TerraSectorPresets {
    public static SectorPreset verilus;

    private static class ProceduralFileMapGenerator extends FileMapGenerator {
        private final Planet planet;
        private final SectorPreset preset;

        public ProceduralFileMapGenerator(Planet planet, SectorPreset preset) {
            super(preset);
            this.planet = planet;
            this.preset = preset;
        }

        @Override
        public void generate(Tiles tiles, WorldParams params) {
            Sector sector = Vars.state.rules.sector;
            if (sector == null && preset.sector != null) {
                sector = preset.sector;
            }
            if (sector == null) {
                throw new RuntimeException("No sector available for procedural generation");
            }
            if (planet.generator != null) {
                planet.generator.generate(tiles, sector);
            } else {
                throw new RuntimeException("Planet has no generator");
            }
        }
    }

    private static class PlanetSectorPreset extends SectorPreset {
        public PlanetSectorPreset(String name, Planet planet, int sectorId) {
            super(name, null, Vars.mods.getMod("terra"));
            this.generator = new ProceduralFileMapGenerator(planet, this);
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
