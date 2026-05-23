package terra.world.drawer;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.draw.DrawBlock;

public class DrawLiquidTileRotated extends DrawBlock {
    public Liquid drawLiquid;
    public float padding;
    private float auto = -129;
    public float padLeft = auto, padRight = auto, padTop = auto, padBottom = auto;
    public float alpha = 1f;
    public float xOffset = 0, yOffset = 0;

    public DrawLiquidTileRotated(Liquid drawLiquid, float padding){
        this.drawLiquid = drawLiquid;
        this.padding = padding;
    }

    public DrawLiquidTileRotated(Liquid drawLiquid, float xOffset, float yOffset){
        this.drawLiquid = drawLiquid;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public DrawLiquidTileRotated(Liquid drawLiquid){
        this.drawLiquid = drawLiquid;
    }

    public DrawLiquidTileRotated(){
    }

    @Override
    public void draw(Building build) {
        Liquid drawn = drawLiquid != null ? drawLiquid : build.liquids.current();
        float pl = padLeft, pr = padRight, pt = padTop, pb = padBottom;
        float realLeft = pl, realRight = pr, realTop = pt, realBottom = pb;
        float rx = xOffset;
        float ry = yOffset;
        
        switch(build.rotation) {
            case 1:
                realRight = pb; realTop = pr; realLeft = pt; realBottom = pl;
                rx = -yOffset;
                ry = xOffset;
                break;
            case 2:
                realRight = pl; realTop = pb; realLeft = pr; realBottom = pt;
                rx = -xOffset;
                ry = -yOffset;
                break;
            case 3:
                realRight = pt; realTop = pl; realLeft = pb; realBottom = pr;
                rx = yOffset;
                ry = -xOffset;
                break;
            default:
                break;
        }

        LiquidBlock.drawTiledFrames(
            build.block.size, build.x + rx, build.y + ry, 
            realLeft, realRight, realTop, realBottom, 
            drawn, build.liquids.get(drawn) / build.block.liquidCapacity * alpha
        );
    }

    @Override
    public void load(Block block) {
        if(padLeft <= auto) padLeft = padding;
        if(padRight <= auto) padRight = padding;
        if(padTop <= auto) padTop = padding;
        if(padBottom <= auto) padBottom = padding;
    }
}
