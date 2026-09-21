package terra.special;

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
import mindustry.type.*;
import mindustry.type.weather.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import terra.type.*;
import static mindustry.Vars.*;

public class AdvancedDataBase {
    static final Stat
        twattrs = new Stat("terraweatherattrs", StatCat.function),
        tbore = new Stat("terrablockore", StatCat.function),
        tborewall = new Stat("terrablockorewall", StatCat.function),
        tbcore = new Stat("terrablockcore", StatCat.function),
        tbspeed = new Stat("terrablockspeed", StatCat.function),
        tbdrag = new Stat("terrablockdrag", StatCat.function),
        tbliquid = new Stat("terraweatherliquid", StatCat.function),
        tbstatus = new Stat("terraweatherstatus", StatCat.function);

    public static final Seq<WeatherEntry> entries = new Seq<>();

    public static void initStats() {
        entries.clear();

        for (var c : content.getContentMap()[ContentType.weather.ordinal()]) {
            Weather w = (Weather) c;

            WeatherEntry entry = new WeatherEntry("entry-" + w.name, w);
            entries.add(entry);

            if (w.fullIcon == null || !w.fullIcon.found()) {
                entry.fullIcon = Icon.rain.getRegion();
            } else {
                entry.fullIcon = w.fullIcon;
            }
            if (w.uiIcon == null || !w.uiIcon.found()) {
                entry.uiIcon = Icon.rain.getRegion();
            } else {
                entry.uiIcon = w.uiIcon;
            }
            
            entry.localizedName = w.localizedName;
            entry.description = w.description;
            entry.details = w.details;
            entry.credit = w.credit;
        }

        for (var b : content.blocks()) {
            if (b instanceof Floor fb) {
                fb.databaseCategory = "naturalBlocks";
                fb.databaseTag = "floors";
                if (fb.speedMultiplier != 1f) {
                    fb.stats.addMultModifier(tbspeed, fb.speedMultiplier);
                }
                if (fb.dragMultiplier != 1f) {
                    fb.stats.addMultModifier(tbdrag, fb.dragMultiplier);
                }
                if (fb.liquidDrop != null) {
                    fb.stats.add(tbliquid, b.liquidDrop.emoji() + fb.liquidDrop.localizedName);
                }
                if (fb.status != StatusEffects.none) {
                    fb.stats.add(tbstatus, b.status.emoji() + fb.status.localizedName);
                }
                if (fb.allowCorePlacement == true) {
                    fb.stats.add(tbcore, fb.allowCorePlacement);
                }
                if (fb.itemOre != null) {
                    fb.stats.add(tbore, b.itemOre.emoji() + fb.itemOre.localizedName);
                }
                attri(fb.stats, fb.attributes);
            } else if (b instanceof StaticWall wb) {
                wb.databaseCategory = "naturalBlocks";
                wb.databaseTag = "staticWalls";
                if (wb.itemOre != null) {
                    wb.stats.add(tbore, wb.itemOre.emoji() + wb.itemOre.localizedName);
                }
                attri(wb.stats, wb.attributes);
            //idk why TallBlock is not a StaticWall but just a Block
            } else if (b instanceof TallBlock tb) {
                tb.databaseCategory = "naturalBlocks";
                tb.databaseTag = "staticWalls";
                if (tb.itemOre != null) {
                    tb.stats.add(tbore, tb.itemOre.emoji() + tb.itemOre.localizedName);
                }
                attri(tb.stats, tb.attributes);
            } else if (b instanceof Prop pb) {
                pb.databaseCategory = "naturalBlocks";
                pb.databaseTag = "props";
            } else if (b instanceof OverlayFloor ob) {
                ob.databaseCategory = "naturalBlocks";
                ob.databaseTag = "overlays";
                if (ob.itemOre != null) {
                    ob.stats.add(tbore, ob.itemOre.emoji() + ob.itemOre.localizedName);
                    ob.stats.add(tborewall, ob.wallOre);
                }
            }
        }
    }
    public static void attri(Stats stats, Attributes at) {
        var s = "";
        for (var a : Attribute.all) {
            var g = at.get(a);
            if (g != 0) {
                s += arc.Core.bundle.get("attribute." + a.name) + ": [accent]";
                if (g == (int) g)
                    s += (int) g;
                else
                    s += g;
                s += "[] | ";
            }
        }
        if (!s.isEmpty())
            stats.add(twattrs, s.substring(0, s.length() - 3));
    }
}
