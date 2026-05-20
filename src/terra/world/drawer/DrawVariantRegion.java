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
    public int randOffset = 0;

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
        
        variantRegions = new TextureRegion[Math.max(1, variants)];
        for (int i = 0; i < variantRegions.length; i++) {
            variantRegions[i] = Core.atlas.find(block.name + suffix + (i + 1), block.region);
        }
    }

    @Override
    public void draw(Building build) {
        int index = Mathf.randomSeed(build.tile.pos() + randOffset, 0, Math.max(0, variantRegions.length - 1));
        
        Draw.rect(variantRegions[index], build.x, build.y);
    }

    @Override
    public TextureRegion[] icons(Block block) {
        return new TextureRegion[]{variantRegions != null && variantRegions.length > 0 ? variantRegions[0] : block.region};
    }
}
