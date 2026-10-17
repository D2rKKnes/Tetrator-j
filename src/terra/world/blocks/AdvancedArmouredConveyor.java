package terra.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.Conveyor;

public class AdvancedArmouredConveyor extends Conveyor {
    public static boolean lastArmoredState = false;

    public AdvancedArmouredConveyor(String name) {
        super(name);
        configurable = true;
        rotate = true;
        drawDynamic = true;
        drawCached = false;
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        AdvancedArmouredConveyorBuild build = tile != null && tile.build instanceof AdvancedArmouredConveyorBuild b ? b : null;
        boolean isArmored = build != null ? build.armored : lastArmoredState;

        if(isArmored){
            return (otherblock.outputsItems() && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock)) ||
                    (lookingAt(tile, rotation, otherx, othery, otherblock) && otherblock.hasItems);
        }
        return super.blends(tile, rotation, otherx, othery, otherrot, otherblock);
    }

    public boolean blendsArmored(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return Point2.equals(tile.x + Geometry.d4(rotation).x, tile.y + Geometry.d4(rotation).y, otherx, othery)
                || ((!otherblock.rotatedOutput(otherx, othery, tile) && Edges.getFacingEdge(otherblock, otherx, othery, tile) != null &&
                Edges.getFacingEdge(otherblock, otherx, othery, tile).relativeTo(tile) == rotation) ||
                (otherblock instanceof Conveyor && otherblock.rotatedOutput(otherx, othery, tile) && Point2.equals(otherx + Geometry.d4(otherrot).x, othery + Geometry.d4(otherrot).y, tile.x, tile.y)));
    }

    public class AdvancedArmouredConveyorBuild extends ConveyorBuild {
        public boolean armored = lastArmoredState;

        @Override
        public void placed() {
            super.placed();
            armored = lastArmoredState;
            wakeSurrounding();
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            wakeSurrounding();
        }

        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);

            table.row();
            table.button(Icon.defense, () -> {
                armored = !armored;
                lastArmoredState = armored;
                Sounds.click.at(this);
                Fx.placeBlock.at(this, 1.5f);
                onProximityUpdate();
                wakeSurrounding();
            }).size(40f).checked(b -> armored);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            if(armored){
                return super.acceptItem(source, item) && (source.block instanceof Conveyor || Edges.getFacingEdge(source.tile, tile).relativeTo(tile) == rotation);
            }
            return super.acceptItem(source, item);
        }

        @Override
        public void updateTile() {
            super.updateTile();

            if (items.total() > 0) {
                noSleep();
                Building target = front();
                if (target != null) {
                    target.noSleep();
                }
            }
        }

        public void wakeSurrounding() {
            noSleep();
            for (Building b : proximity) {
                if (b != null) {
                    b.noSleep();
                }
            }
            Building target = front();
            if (target != null) {
                target.noSleep();
            }
        }

        @Override
        public void draw() {
            super.draw();
            if (armored) {
                TextureRegion region = Core.atlas.find(name + "-invert" + blendbits);
                if (!region.found()) {
                    region = Core.atlas.find(name + "-invert-" + blendbits);
                }
                if (!region.found()) {
                    region = Core.atlas.find(name + "-invert");
                }

                if (region.found()) {
                    Draw.rect(region, x, y, tilesize * blendsclx, tilesize * blendscly, rotation * 90);
                }
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.bool(armored);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            armored = read.bool();
            lastArmoredState = armored;
            onProximityUpdate();
        }
    }
}
