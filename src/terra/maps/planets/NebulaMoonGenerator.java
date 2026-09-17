package terra.maps.planets;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.ai.*;
import mindustry.ai.BaseRegistry.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import terra.graphics.g3d.*;

public class NebulaMoonGenerator extends PlanetGenerator {
    public double octaves = 4, persistence = 0.8, scl = 1.6, pow = 1.7, mag = 2;
    float heightYOffset = 41.3f;
    float sclh = 5f;
    float waterOffset = 0.04f;
    float heightScl = 1.01f;
    Vec3[] craters = new Vec3[4];
    float[] craterSize = new float[craters.length];
    float craterScl = 2.2f;
    public float rotationScl = -100;
    //Color c1 = Color.valueOf("5057a6"), c2 = Color.valueOf("272766");
    public Color[] colors = new Color[]{
            Color.valueOf("827de9"),
            Color.valueOf("c2bffb"),
            Color.valueOf("6b698a"),
            Color.valueOf("9192a6"),
            Color.valueOf("a1b1d0"),
            Color.valueOf("e1e9f0"),
            Color.valueOf("ffffff")
    };

    void initCraters(){
        if(craters[0] != null) return;
        for(int i = 0; i < craters.length; i++){
            rand.setSeed(seed + i + 66);
            craters[i] = new Vec3(craterScl, 0, 0)
                .setToRandomDirection(rand)
                .setLength2(craterScl * craterScl);
            craterSize[i] = rand.nextFloat() * 0.15f + 0.03f;
        }
    }
    
    float craterNoise(Vec3 position, boolean smooth){
        initCraters();
        float d = craterScl * craterScl * 4f, s = 0.3f;
        for(int i = 0; i < craters.length; i++){
            float dt = craters[i].dst2(position);
            if(dt < d){
                d = dt;
                s = craterSize[i];
            }
        }
        d /= craterScl * craterScl;
        if(d <= s){
            if(smooth) return d / s * 1.3f - 0.7f;
            return -0.7f;
        }
        if(d >= s + 0.15f) return 0.2f;
        float a = (d - s) / 0.15f;
        return (1f - Mathf.sqrt(a)) * 0.38f + 0.22f;
    }

    float rawHeight(Vec3 position){
        Vec3 cratPos = Tmp.v33.set(position).scl(craterScl);
        float base = Mathf.pow(
            Simplex.noise3d(seed, 7, 0.5f, 1f/3f,
                position.x * sclh,
                position.y * sclh + heightYOffset,
                position.z * sclh) * heightScl,
            2.3f
        );
        float crater = craterNoise(cratPos, true) * 0.3f;
        return (base + crater + waterOffset) / (1f + waterOffset);
    }

    @Override
    public float getHeight(Vec3 position) {
        float height = rawHeight(position);
        return Math.max(height, 0.1538f);
    }

    @Override
    public void getColor(Vec3 position, Color out) {
        Tmp.v31.set(position).rotate(Vec3.Y, position.x * rotationScl).add(850f, 0f, 500f);
        double height = Math.pow(Simplex.noise3d(0, octaves, persistence, scl, Tmp.v31.x, Tmp.v31.y, Tmp.v31.z), pow) * mag;
        //out.set(c1).lerp(c2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(1f - 0.2f).toFloatBits();
        out.set(colors[Mathf.clamp((int) (height * colors.length), 0, colors.length - 1)]);
    }
}
