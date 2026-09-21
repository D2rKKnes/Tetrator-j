package terra.special;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.g2d.TextureAtlas.AtlasRegion;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.ai.types.AssemblerAI;
import mindustry.content.*;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.part.DrawPart;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.type.Weapon;
import mindustry.type.Weather;
import mindustry.type.weather.*;
import mindustry.world.blocks.Attributes;
import mindustry.world.draw.DrawBlock;
import mindustry.world.meta.*;
import terra.type.*;
import static mindustry.Vars.*;

public class AdvancedDataBase {
    static final Stat
        twstatus = new Stat("terraweatherstatus", StatCat.function),
        twwind = new Stat("terraweatherwind", StatCat.function),
        twliquid = new Stat("terraweatherliquid", StatCat.function),
        twattrs = new Stat("terraweatherattrs", StatCat.function);

    public static final Seq<WeatherEntry> entries = new Seq<>();

    public static void initStats() {
        entries.clear();

        for (var c : content.getContentMap()[ContentType.weather.ordinal()]) {
            Weather w = (Weather) c;

            WeatherEntry entry = new WeatherEntry(w.name);
            entries.add(entry);
            entry.fullIcon = w.fullIcon;
            entry.uiIcon = w.uiIcon;

            attri(entry.stats, w.attrs);

            if (w.status != StatusEffects.none) {
                entry.stats.add(twstatus, w.status.emoji() + w.status.localizedName);
                entry.stats.add(Stat.targetsAir,   w.statusAir);
                entry.stats.add(Stat.targetsGround, w.statusGround);
            }

            if (w instanceof ParticleWeather pw && pw.force > 0) {
                entry.stats.add(twwind, pw.force, StatUnit.tilesSecond);
            }

            if (w instanceof RainWeather rw && rw.liquid != null) {
                entry.stats.add(twliquid, rw.liquid.emoji() + rw.liquid.localizedName);
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
