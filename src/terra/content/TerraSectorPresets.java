package terra.content;

import mindustry.content.Planets;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.*;

public class TerraSectorPresets {
    public static SectorPreset verilus;

    public static void load() {
        verilus = new NoFileSectorPreset("verilus", Planets.verilus, 0); 
        verilus.alwaysUnlocked = false;
        verilus.addStartingItems = true;
        verilus.difficulty = 8;
        verilus.allowLaunchSchematics = true;
        verilus.overrideLaunchDefaults = true;
        verilus.allowLaunchLoadout = true;
    }

    private static class NoFileSectorPreset extends SectorPreset {
        public NoFileSectorPreset(String name, Planet planet, int sectorId) {
            super(name, (mindustry.mod.Mods.LoadedMod) null);

            this.planet = planet;
            this.originalPosition = sectorId;
            sectorId %= planet.sectors.size;
            this.sector = planet.sectors.get(sectorId == -1 ? 0 : sectorId);
            planet.preset(sectorId, this);

            this.generator = new PlanetMapGenerator(this);
        }
    }

    private static class PlanetMapGenerator extends FileMapGenerator {
        public PlanetMapGenerator(SectorPreset preset) {
            super("serpulo/groundZero", preset);
        }

        @Override
        public void generate(Tiles tiles, WorldParams params) {
            if (preset != null && preset.planet != null && preset.planet.generator != null) {
                preset.planet.generator.generate(tiles, preset.sector, params);
            }
        }
    }
}
