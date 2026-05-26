package terra.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;

public class RouterSorter extends Block {
    public TextureRegion topRegion;
    public float speed = 4f;

    public RouterSorter(String name) {
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        configurable = true;
        saveConfig = true;
        copyConfig = true;
        itemCapacity = 1;
        group = BlockGroup.transportation;
    }

    @Override
    public void load() {
        super.load();
        topRegion = Core.atlas.find(name + "-top", Core.atlas.find("clear"));
    }

    public class RouterSorterBuild extends Building {
        public Item currentItem = null;
        public float progress = 0f;
        public Item sortItem = null;
        public int lastInputDirection = -1;

        @Override
        public void updateTile() {
            if (currentItem != null) {
                progress += delta() * speed;
                if (progress >= 1f) {
                    attemptOutput();
                    currentItem = null;
                    progress = 0f;
                }
            } else if (canPickup()) {
                Building input = getInputBuilding();
                if (input != null && input.items != null && input.items.total() > 0) {
                    for (Item item : Vars.content.items()) {
                        if (input.items.get(item) > 0 && acceptItem(input, item)) {
                            input.items.remove(item, 1);
                            this.currentItem = item;
                            break;
                        }
                    }
                }
            }
        }

        public boolean canPickup() {
            return currentItem == null && enabled;
        }

        public Building getInputBuilding() {
            if (lastInputDirection == -1) {
                for (int i = 0; i < 4; i++) {
                    Building build = tile.nearbyBuild(i);
                    if (build != null && build.items != null && build.items.total() > 0) {
                        return build;
                    }
                }
                return null;
            } else {
                for (int i = 0; i < 4; i++) {
                    if (i == lastInputDirection) continue;
                    Building build = tile.nearbyBuild(i);
                    if (build != null && build.items != null && build.items.total() > 0) {
                        return build;
                    }
                }
                return null;
            }
        }

        public void attemptOutput() {
            if (currentItem == null) return;
            boolean outputted = false;
            int forwardDir = rotation;

            if (sortItem != null && currentItem == sortItem) {
                Building target = tile.nearbyBuild(forwardDir);
                if (target != null && target.acceptItem(this, currentItem)) {
                    target.handleItem(this, currentItem);
                    outputted = true;
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    if (i == lastInputDirection) continue;
                    Building target = tile.nearbyBuild(i);
                    if (target != null && target.acceptItem(this, currentItem)) {
                        target.handleItem(this, currentItem);
                        outputted = true;
                        break;
                    }
                }
            }

            if (outputted) {
                lastInputDirection = -1;
            } else {
                progress = 0.99f;
            }
        }

        @Override
        public void handleItem(Building source, Item item) {
            if (currentItem != null || !enabled) return;
            int dir = source.tile.relativeTo(tile.x, tile.y);
            lastInputDirection = dir;
            this.currentItem = item;
            this.progress = 0.01f;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return currentItem == null && enabled;
        }

        public void configured(Object value) {
            if (value instanceof Item) {
                this.sortItem = (Item) value;
            } else {
                this.sortItem = null;
            }
        }

        @Override
        public void draw() {
            super.draw();
            if (sortItem != null && topRegion.found()) {
                Draw.rect(topRegion, x, y);
                Draw.rect(sortItem.uiIcon, x, y, 4f, 4f);
            }
        }
    }
}
