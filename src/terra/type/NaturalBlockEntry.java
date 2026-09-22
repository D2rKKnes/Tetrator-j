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
import mindustry.world.blocks.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import terra.type.*;
import terra.special.*;

/** This class is only for displaying natural (static) blocks in the content database. */
public class NaturalBlockEntry extends UnlockableContent{
    public Block block = null;
    static final Stat
        twstatus = new Stat("terraweatherstatus", StatCat.function),
        twwind = new Stat("terraweatherwind", StatCat.function),
        twliquid = new Stat("terraweatherliquid", StatCat.function);

    public NaturalBlockEntry(String name){
        super(name);
        //allDatabaseTabs = true;
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
        

        AdvancedDataBase.attri(this.stats, block.attrs);
    }

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }
}
