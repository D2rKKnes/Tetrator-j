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
import mindustry.world.blocks.Attributes;
import mindustry.world.draw.DrawBlock;
import mindustry.world.meta.*;
import static mindustry.Vars.*;

public class AdvancedDataBase {
    static final Stat
        twstatus = new Stat("terraweatherstatus", StatCat.function),
        twwind = new Stat("terraweatherwind", StatCat.function),
        twliquid = new Stat("terraweatherliquid", StatCat.function),
        twattrs = new Stat("terraweatherattrs", StatCat.function);

    public static void initStats() {
        for (var weather : content.getContentMap()[ContentType.weather.ordinal()]) {
            var w = (Weather) weather;
            attri(w.stats, w.attrs);
            if (w.status != StatusEffects.none) {
                w.stats.add(twstatus, w.status.emoji() + w.status.localizedName);
                w.stats.add(Stat.targetsAir, w.statusAir);
                w.stats.add(Stat.targetsGround, w.statusGround);
            }
            if (w instanceof ParticleWeather && w.force > 0) {
                w.stats.add(twwind, w.force, StatUnit.tilesSecond);
            }
            if (w instanceof RainWeather && w.liquid != null) {
                w.stats.add(twliquid, w.liquid.emoji() + w.liquid.localizedName);
            }
        }
    }
    public static void attri(Stats stats, Attributes at) {
        var s = "";
        for (var a : Attribute.all) {
            var g = at.get(a);
            if (g != 0) {
                s += arc.Core.bundle.get("attribute." + a.name) + attrs.get(a, "") + ": [accent]";
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
