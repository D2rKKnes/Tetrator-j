package terra.world.blocks;

import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Items;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.blocks.distribution.Sorter;

public class RouterSorter extends Sorter {

    public static RouterSorter routerSorter;

    public static void setup() {
        routerSorter = new RouterSorter("router-sorter");
    }

    public RouterSorter(String name) {
        super(name);
        configurable = true;

        requirements(Category.distribution, ItemStack.with(Items.lead, 3, Items.graphite, 2));
        buildCostMultiplier = 4f;
        health = 85;
    }

    public class RouterSorterBuild extends SorterBuild {
        public boolean invert = false;
        public int cdump = 0;

        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);

            if (sortItem != null) {
                table.row();
                table.button(Icon.refresh, () -> {
                    invert = !invert;
                }).size(40f).checked(b -> invert);
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            if (sortItem == null) {
                return team == source.team;
            }
            return super.acceptItem(source, item);
        }

        @Override
        public Building getTileTarget(Item item, Building source, boolean inv) {
            if (sortItem == null) {
                int len = proximity.size;
                if (len == 0) return null;

                for (int i = 0; i < len; i++) {
                    Building other = proximity.get((cdump + i) % len);
                    if (other != source && other.acceptItem(this, item)) {
                        cdump = (cdump + i + 1) % len;
                        return other;
                    }
                }
                return null;
            }
            int dir = relativeTo(source);
            if (dir == -1) return null;

            boolean matches = (item == sortItem);
            boolean passStraight = invert ? !matches : matches;

            if (passStraight) {
                return nearby(dir ^ 2);
            } else {
                Building left = nearby((dir + 1) % 4);
                Building right = nearby((dir + 3) % 4);
                boolean leftValid = left != null && left.acceptItem(this, item);
                boolean rightValid = right != null && right.acceptItem(this, item);

                if (leftValid && rightValid) {
                    cdump = (cdump + 1) % 2;
                    return cdump == 0 ? left : right;
                } else if (leftValid) {
                    return left;
                } else if (rightValid) {
                    return right;
                }
            }

            return null;
        }

        @Override
        public void handleItem(Building source, Item item) {
            if (sortItem == null) {
                Building target = getTileTarget(item, source, false);
                if (target != null) {
                    target.handleItem(this, item);
                }
            } else {
                super.handleItem(source, item);
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.bool(invert);
            write.i(cdump);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            invert = read.bool();
            cdump = read.i();
        }
    }
}
