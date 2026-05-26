package terra.content;

import mindustry.content.Planets;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.*;

public class TerraSectorPresets {
    public static SectorPreset verilus;

    public static void load() {
        verilus = new NoFileSectorPreset("verilus", Planets.verilus, 0);
        // свойства можно задать и здесь, и внутри класса
        verilus.alwaysUnlocked = true;
        verilus.addStartingItems = true;
        verilus.difficulty = 8;
        verilus.allowLaunchSchematics = true;
        verilus.overrideLaunchDefaults = true;
        verilus.allowLaunchLoadout = true;       // обязательно, чтобы не упасть в findLaunchCandidate
    }

    /** Пресет, использующий генератор планеты вместо .msav файла. */
    private static class NoFileSectorPreset extends SectorPreset {
        public NoFileSectorPreset(String name, Planet planet, int sectorId) {
            // Вызываем конструктор, который НЕ запускает initialize и не создаёт FileMapGenerator
            super(name, (mindustry.mod.Mods.LoadedMod) null);

            // Привязываем планету и сектор вручную (как это делает обычный initialize)
            this.planet = planet;
            this.originalPosition = sectorId;
            sectorId %= planet.sectors.size;
            this.sector = planet.sectors.get(sectorId == -1 ? 0 : sectorId);
            planet.preset(sectorId, this);

            // Генератор-заглушка: при вызове generate() переадресует работу на planet.generator
            this.generator = new PlanetMapGenerator(this);
        }
    }

    /** Генератор-пустышка, подменяющий файловую карту на процедурную генерацию планеты. */
    private static class PlanetMapGenerator extends FileMapGenerator {
        public PlanetMapGenerator(SectorPreset preset) {
            // Чтобы не было FileNotFoundException, передаём ЛЮБУЮ существующую карту (она не будет использоваться)
            super("serpulo/groundZero", preset);
        }

        @Override
        public void generate(Tiles tiles, WorldParams params) {
            // Вместо чтения из файла – используем генератор планеты (например, VerilusAsteroidGenerator)
            if (preset != null && preset.planet != null && preset.planet.generator != null) {
                preset.planet.generator.generate(tiles, preset.sector, params);
            }
        }
    }
}
