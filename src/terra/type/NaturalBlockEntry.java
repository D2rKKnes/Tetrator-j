package terra.type;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureAtlas.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.type.weather.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import terra.type.*;
import terra.special.*;

/** This class is only for displaying natural (static) blocks in the content database. */
public class NaturalBlockEntry extends UnlockableContent{
    public Block block = null;
    static final Stat
        tbore = new Stat("terrablockore", StatCat.function),
        tborewall = new Stat("terrablockorewall", StatCat.function),
        tbcore = new Stat("terrablockcore", StatCat.function),
        tbspeed = new Stat("terrablockspeed", StatCat.function),
        tbdrag = new Stat("terrablockdrag", StatCat.function),
        tbliquid = new Stat("terraweatherliquid", StatCat.function),
        tbstatus = new Stat("terraweatherstatus", StatCat.function);

    public NaturalBlockEntry(String name){
        super(name);
        allDatabaseTabs = true;
        hideDetails = false;
        alwaysUnlocked = true;
        databaseCategory = "naturalBlocks";
    }

    public NaturalBlockEntry(String name, Block block){
        this(name);
        this.block = block;
    }

    @Override
    public void setStats(){
        if (block instanceof OverlayFloor ob) {
            //databaseTag = "overlays";
            if (ob.itemDrop != null) {
                stats.add(tbore, ob.itemDrop.emoji() + ob.itemDrop.localizedName);
                stats.add(tborewall, ob.wallOre);
            }
        } else if (block instanceof Floor fb) {
            //databaseTag = "floors";
            if (fb.speedMultiplier != 1f) {
                stats.addMultModifier(tbspeed, fb.speedMultiplier);
            }
            if (fb.dragMultiplier != 1f) {
                stats.addMultModifier(tbdrag, fb.dragMultiplier);
            }
            if (fb.liquidDrop != null) {
                stats.add(tbliquid, fb.liquidDrop.emoji() + fb.liquidDrop.localizedName);
            }
            if (fb.status != StatusEffects.none) {
                stats.add(tbstatus, fb.status.emoji() + fb.status.localizedName);
            }
            if (fb.allowCorePlacement == true) {
                stats.add(tbcore, fb.allowCorePlacement);
            }
            if (fb.itemDrop != null) {
                stats.add(tbore, fb.itemDrop.emoji() + fb.itemDrop.localizedName);
            }
            AdvancedDataBase.attri(this.stats, fb.attributes);
        //idk why TallBlock is not a StaticWall but just a Block.. Same to the TreeBlock
        } else if (block instanceof StaticWall || block instanceof TallBlock || block instanceof TreeBlock) {
            //databaseTag = "staticWalls";
            if (block.itemDrop != null) {
                stats.add(tbore, block.itemDrop.emoji() + block.itemDrop.localizedName);
            }
            AdvancedDataBase.attri(this.stats, block.attributes);
        } else if (block instanceof Prop pb) {
            //databaseTag = "props";
        }
    }

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }
}
