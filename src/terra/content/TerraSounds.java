package terra.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import arc.files.Fi;
import arc.struct.ObjectMap;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Sounds;
import mindustry.mod.Mods;

import java.lang.reflect.Field;

public class TerraSounds {
    public static ObjectMap<String, Sound> sounds = new ObjectMap<>();

    public static Sound
            railGunCharge, acceleratinglaserloop, jumpIn, shockwave,
            shootHeavy, shootBlackhole, shootFastLaser, shootLaunch, shootAlt2;

    public static void load() {
        try {
            for (Field field : TerraSounds.class.getFields()) {
                if (field.getType().equals(Sound.class)) {
                    field.set(null, loadSound(field.getName()));
                }
            }
        } catch (IllegalAccessException e) {
            Log.err(e);
        }
    }

    private static Sound loadSound(String soundName) {
        Sound sound = new Sound();
        if (Vars.headless) return sound;

        String path = "sounds/" + soundName;
        String filePath = Vars.tree.get(path + ".ogg").exists() ? path + ".ogg" : path + ".mp3";

        AssetDescriptor<?> desc = Core.assets.load(filePath, Sound.class, new SoundLoader.SoundParameter(sound));
        desc.errored = Throwable::printStackTrace;
        return sound;
    }
}
