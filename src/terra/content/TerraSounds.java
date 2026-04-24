package terra.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import arc.struct.Seq;
import mindustry.Vars;

import java.lang.reflect.Field;

public class TerraSounds {
    public static Sound
        shootLaunch = new Sound(), railGunCharge = new Sound(), shootHeavy = new Sound(), acceleratinglaserloop = new Sound(), shootBlackhole = new Sound();
    public static void load(){
        shootLaunch = loadSound("shootLaunch"); 
        railGunCharge = loadSound("railGunCharge");
        shootHeavy = loadSound("shootHeavy");
        acceleratinglaserloop = loadSound("acceleratinglaserloop");
        shootBlackhole = loadSound("shootBlackhole");
    }

    private static Sound loadSound(String soundName){
        if(!Vars.headless){
            String name = "sounds/" + soundName;
            String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

            Sound sound = new Sound();

            AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
            desc.errored = Throwable::printStackTrace;

            return sound;

        }else{
            return new Sound();
        }
    }
}
