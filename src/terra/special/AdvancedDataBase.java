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
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import terra.type.*;
import static mindustry.Vars.*;

public class AdvancedDataBase {
    static final Stat
        twattrs = new Stat("terraweatherattrs", StatCat.function);

    public static final Seq<WeatherEntry> entries = new Seq<>();

    public static void initStats() {
        entries.clear();

        for (var c : content.getContentMap()[ContentType.weather.ordinal()]) {
            Weather w = (Weather) c;

            WeatherEntry entry = new WeatherEntry("entry-" + w.name, w);
            entries.add(entry);

            if (w.fullIcon == null || !w.fullIcon.found()) {
                entry.fullIcon = Icon.rainSmall.getRegion();
            } else {
                entry.fullIcon = w.fullIcon;
            }
            if (w.uiIcon == null || !w.uiIcon.found()) {
                entry.uiIcon = Icon.rainSmall.getRegion();
            } else {
                entry.uiIcon = w.uiIcon;
            }
            
            entry.localizedName = w.localizedName;
            entry.description = w.description;
            entry.details = w.details;
            entry.credit = w.credit;
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
