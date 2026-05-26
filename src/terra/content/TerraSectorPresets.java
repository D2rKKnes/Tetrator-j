package terra.content;

import mindustry.content.Planets;
import mindustry.mod.Mods.LoadedMod;
import mindustry.type.*;

public class TerraSectorPresets {
    public static SectorPreset verilus;

    public static void load() {
        verilus = new NoFileSectorPreset("verilus", Planets.verilus, 0) {{
            alwaysUnlocked = true;
            addStartingItems = true;
            difficulty = 8;
            overrideLaunchDefaults = true;
            allowLaunchLoadout = true;
        }};
    }

    private static class NoFileSectorPreset extends SectorPreset {
        public NoFileSectorPreset(String name, Planet planet, int sector) {
            super(name, (LoadedMod) null);

            this.generator = null;
            this.planet = planet;
            this.originalPosition = sector;
            sector %= planet.sectors.size;
            this.sector = planet.sectors.get(sector == -1 ? 0 : sector);
            planet.preset(sector, this);
        }
    }
}
