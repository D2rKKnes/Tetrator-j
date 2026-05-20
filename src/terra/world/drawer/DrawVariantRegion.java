package terra.world.drawer;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

public class DrawVariantRegion extends DrawBlock {
    public String suffix = "-variant";
    public int variants = 0; 
    public boolean randOffset = false;

    protected TextureRegion[] variantRegions;

    public DrawVariantRegion(String suffix, int variants) {
        this.suffix = suffix;
        this.variants = variants;
    }
    public DrawVariantRegion(String suffix, int variants, int randOffset) {
        this.suffix = suffix;
        this.variants = variants;
        this.randOffset = randOffset;
    }

    @Override
    public void load(Block block) {
        super.load(block);
        
        if (variants > 0) {
            variantRegions = new TextureRegion[variants];
            for (int i = 0; i < variants; i++) {
                variantRegions[i] = Core.atlas.find(block.name + suffix + (i + 1), block.region);
            }
        }
    }

    @Override
    public void draw(Building build) {
        TextureRegion regionToDraw;
        
        if (variants == 0 || variantRegions == null) {
            regionToDraw = build.block.region;
        } else {
            int index = Mathf.randomSeed(build.tile.pos(), 0, Math.max(0, variantRegions.length - 1));
            regionToDraw = variantRegions[index];
        }

        if (randOffset) {
            float xOffset = Mathf.randomSeed(build.tile.pos() + 1, -0.5f, 0.5f);
            float yOffset = Mathf.randomSeed(build.tile.pos() + 2, -0.5f, 0.5f);
            Draw.rect(regionToDraw, build.x + xOffset, build.y + yOffset, build.rotation - 90);
        } else {
            Draw.rect(regionToDraw, build.x, build.y, build.rotation - 90);
        }
    }

    @Override
    public TextureRegion[] icons(Block block) {
        return new TextureRegion[]{variants > 0 && variantRegions != null ? variantRegions[0] : block.region};
    }
}
