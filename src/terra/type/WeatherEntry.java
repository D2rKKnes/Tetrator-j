package terra.type;

import arc.*;
import arc.scene.ui.layout.*;
import mindustry.ctype.*;
import mindustry.game.*;
import terra.special.*;

/** This class is only for displaying weather in the content database. */
public class WeatherEntry extends UnlockableContent{
    private static final String bundleContentPrefix = "weather";
    public Weather weather = Weathers.rain;

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
            stats.add(AdvancedDataBase.twstatus, weather.status.emoji() + weather.status.localizedName);
            stats.add(Stat.targetsAir,   weather.statusAir);
            stats.add(Stat.targetsGround, weather.statusGround);
        }

        if (weather instanceof ParticleWeather pw && pw.force > 0) {
            stats.add(AdvancedDataBase.twwind, pw.force, StatUnit.tilesSecond);
        }

        if (weather instanceof RainWeather rw && rw.liquid != null) {
            stats.add(AdvancedDataBase.twliquid, rw.liquid.emoji() + rw.liquid.localizedName);
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
