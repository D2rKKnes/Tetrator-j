package example;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.type.Item;
import mindustry.world.blocks.distribution.Sorter;

public class RouterSorter extends Sorter {

    public RouterSorter(String name) {
        super(name);
        configurable = true;
        drawDynamic = true;
        drawCached = false;
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
        public Building getTileTarget(Item item, Building source, boolean flip) {
            int dir = source.relativeTo(tile.x, tile.y);
            if (dir == -1) return null;

            if (sortItem == null) {
                int len = proximity.size;
                if (len == 0) return null;

                for (int i = 0; i < len; i++) {
                    Building other = proximity.get((cdump + i) % len);
                    if (other != source && other.acceptItem(this, item) && other.team == team) {
                        if (flip) {
                            cdump = (cdump + i + 1) % len;
                        }
                        return other;
                    }
                }
                return null;
            }

            if (((item == sortItem) != invert) == enabled) {
                if (isSame(source) && isSame(nearby(dir))) {
                    return null;
                }
                return nearby(dir);
            } else {
                Building a = nearby((dir + 3) % 4);
                Building b = nearby((dir + 1) % 4);
                boolean ac = a != null && !(a.block.instantTransfer && source.block.instantTransfer) && a.acceptItem(this, item);
                boolean bc = b != null && !(b.block.instantTransfer && source.block.instantTransfer) && b.acceptItem(this, item);

                if (ac && !bc) {
                    return a;
                } else if (bc && !ac) {
                    return b;
                } else if (!bc) {
                    return null;
                } else {
                    Building to = (rotation & (1 << dir)) == 0 ? a : b;
                    if (flip) rotation ^= (1 << dir);
                    return to;
                }
            }
        }

        @Override
        public void draw() {
            var router = Core.atlas.find(name);
            Draw.rect(router, x, y);

            if (sortItem != null) {
                var top = Core.atlas.find(name + "-top");
                Draw.color(sortItem.color);
                Draw.rect(top, x, y);
                Draw.color();
            }

            if (invert && sortItem != null) {
                var invertTex = Core.atlas.find(name + "-invert");
                Draw.color();
                Draw.rect(invertTex, x, y);
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
