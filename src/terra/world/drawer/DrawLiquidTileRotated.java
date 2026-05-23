package terra.world.drawer;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.draw.DrawBlock;

public class DrawLiquidTileRotated extends DrawBlock {
    public Liquid drawLiquid;
    public float padding;
    public float padLeft = -1, padRight = -1, padTop = -1, padBottom = -1;
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
        
        switch(build.rotation) {
            case 1:
                realRight = pb; realTop = pr; realLeft = pt; realBottom = pl;
                break;
            case 2:
                realRight = pl; realTop = pb; realLeft = pr; realBottom = pt;
                break;
            case 3:
                realRight = pt; realTop = pl; realLeft = pb; realBottom = pr;
                break;
            default:
                break;
        }

        LiquidBlock.drawTiledFrames(
            build.block.size, build.x + xOffset, build.y + yOffset, 
            realLeft, realRight, realTop, realBottom, 
            drawn, build.liquids.get(drawn) / build.block.liquidCapacity * alpha
        );
    }

    @Override
    public void load(Block block) {
        if(padLeft < 0) padLeft = padding;
        if(padRight < 0) padRight = padding;
        if(padTop < 0) padTop = padding;
        if(padBottom < 0) padBottom = padding;
    }
}
