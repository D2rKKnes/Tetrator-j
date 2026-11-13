package terra.world.blocks.multiblock;

import terra.world.blocks.multiblock.PlaceholderBlock;

/**
 * blocks that are never supposed to used by player.
 */
public class InnerBlock {
    public static PlaceholderBlock placeholder;

    public static void load() {
        placeholder = new PlaceholderBlock("construction-placeholder");
    }
}
