package terra.content;

import terra.type.*;

public class TerraLore {
    public static LoreEntry test, spatialAnomaly;

    public static void load() {
        test = new LoreEntry("test", true);
        spatialAnomaly = new LoreEntry("spatialAnomaly");
    }
}
