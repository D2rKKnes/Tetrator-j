package terra.world.drawer;

import arc.graphics.g2d.Draw;
import mindustry.gen.Building;
import mindustry.world.blocks.production.Drill;
import mindustry.world.draw.DrawBlock;

public class DrawDrillOreTop extends DrawBlock {
    @Override
    public void draw(Building build) {
        if (build instanceof Drill.DrillBuild drill && drill.dominantItem != null) {
            TextureRegion region = ((Drill)drill.block).itemRegion;
            if (region != null && region.found()) {
                Draw.color(drill.dominantItem.color);
                Draw.rect(((Drill)drill.block).itemRegion, drill.x, drill.y);
                Draw.color();
            }
        }
    }
}
