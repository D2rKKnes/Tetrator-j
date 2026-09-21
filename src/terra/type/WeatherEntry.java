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

/** This class is only for displaying weather in the content database. */
public class WeatherEntry extends UnlockableContent{
    private static final String bundleContentPrefix = "weather";
    public Weather weather = Weathers.rain;
    static final Stat
        twstatus = new Stat("terraweatherstatus", StatCat.function),
        twwind = new Stat("terraweatherwind", StatCat.function),
        twliquid = new Stat("terraweatherliquid", StatCat.function);

    public WeatherEntry(String name){
        super(name);
        allDatabaseTabs = true;
        hideDetails = false;
        alwaysUnlocked = true;
        databaseCategory = "weather";
    }

    public WeatherEntry(String name, Weather weather){
        this(name);
        this.weather = weather;
    }

    @Override
    public void setStats(){
        if (weather.status != StatusEffects.none) {
            stats.add(twstatus, weather.status.emoji() + weather.status.localizedName);
            stats.add(Stat.targetsAir,   weather.statusAir);
            stats.add(Stat.targetsGround, weather.statusGround);
        }

        if (weather instanceof ParticleWeather pw && pw.force > 0) {
            stats.add(twwind, pw.force, StatUnit.tilesSecond);
        }

        if (weather instanceof RainWeather rw && rw.liquid != null) {
            stats.add(twliquid, rw.liquid.emoji() + rw.liquid.localizedName);
        }

        AdvancedDataBase.attri(this.stats, weather.attrs);
    }

    // @Override
    // public void loadIcon(){
    //     super.loadIcon();
    //     if(fullIcon == null || !fullIcon.found()) fullIcon = Core.atlas.find("terra-book");
    //     if(uiIcon == null || !uiIcon.found()) uiIcon = Core.atlas.find("terra-book");
    // }

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }
}
