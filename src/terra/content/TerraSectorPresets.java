package terra.content;

import mindustry.content.Planets;
import mindustry.maps.generators.FileMapGenerator;
import mindustry.type.*;
import mindustry.world.*;

public class TerraSectorPresets {
    public static SectorPreset verilus;

    public static void load() {
        verilus = new SectorPreset("verilus", Planets.verilus, 0) {{
            alwaysUnlocked = true;
            addStartingItems = true;
            difficulty = 8;
            allowLaunchSchematics = true;
            overrideLaunchDefaults = true;
            allowLaunchLoadout = true;
            
            generator = new PlanetMapGenerator(this);
        }};
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
