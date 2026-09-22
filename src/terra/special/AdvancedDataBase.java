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
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import terra.type.*;
import static mindustry.Vars.*;

public class AdvancedDataBase {
    static final Stat
        twattrs = new Stat("terraweatherattrs", StatCat.function);

    public static final Seq<WeatherEntry> entries = new Seq<>();
    public static final Seq<NaturalBlockEntry> bentries = new Seq<>();

    public static void initStats() {
        entries.clear();
        bentries.clear();

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
            if (b instanceof OverlayFloor || b instanceof Floor || b instanceof StaticWall || b instanceof TallBlock || b instanceof TreeBlock || b instanceof Prop) {
                NaturalBlockEntry entry = new NaturalBlockEntry("entry-" + b.name, b);
                bentries.add(entry);

                entry.fullIcon = b.fullIcon;
                entry.uiIcon = b.uiIcon;
                
                entry.localizedName = b.localizedName;
                entry.description = b.description;
                entry.details = b.details;
                entry.credit = b.credit;
                
                if (b instanceof OverlayFloor) { entry.databaseTag = "overlays";}
                else if (b instanceof Floor) { entry.databaseTag = "floors";}
                else if (b instanceof StaticWall || b instanceof TallBlock || b instanceof TreeBlock) { entry.databaseTag = "staticWalls";}
                else if (b instanceof Prop) { entry.databaseTag = "props";}
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
