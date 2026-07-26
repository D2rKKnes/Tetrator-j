package terra.content;

import terra.type.*;

public class TerraLore {
    public static LoreEntry test;

    public static void load() {
        test = new LoreEntry("test", true){{
            alwaysUnlocked = false;
        }}; 
    }
}
