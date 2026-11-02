package terra.graphics.g3d;

import arc.graphics.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.noise.*;
import arc.struct.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.type.Planet;
import java.nio.*;

import java.util.Random;

public class AsteroidBeltMesh extends PlanetMesh{
    public float ringRadius = 4.5f;
    public float ringWidth = 2f;
    public float ringThickness = 0.3f;
    public float tilt = 0f;

    public int count = 1500;

    public int divisions = 1;
    public int octaves = 2;
    public float persistence = 0.6f;
    public float noiseScale = 0.38f;
    public float noiseMag = 20f;

    public int tintOctaves = 3;
    public float tintPersist = 0.6f;
    public float tintScale = 0.38f;
    public float tintThresh = 0.6f;

    public float minSize = 0.022f;
    public float maxSize = 0.061f;

    public int seed = 1;
    public float orbitSpeed = 0.02f;

    public Color color = Color.valueOf("3a3a3a");
    public Color tint = Color.valueOf("7a6a5a");

    private boolean ready;
    private GenericMesh belt;
    private final Mat3D mat = new Mat3D();

    public AsteroidBeltMesh(Planet planet){
        super(planet, null, Shaders.planet);
        this.planet = planet;
    }

    private void init(){
        Rand rand = new Rand(seed);
        Color tinted = tint.cpy().a(1f - tint.a);

        Seq<GenericMesh> rocks = new Seq<>(count);

        for(int i = 0; i < count; i++){
            float ang  = rand.nextFloat() * Mathf.PI2;
            float dist = ringRadius + (rand.nextFloat() + rand.nextFloat() - 1f) * ringWidth * 0.5f;
            float y = ((rand.nextFloat() + rand.nextFloat()) - 1f) * ringThickness * 0.5f;
            float size = minSize + rand.nextFloat() * (maxSize - minSize);

            float cx = Mathf.cos(ang) * dist * planet.radius;
            float cy = y * planet.radius;
            float cz = Mathf.sin(ang) * dist * planet.radius;

            GenericMesh rock = new NoiseMesh(
                    planet,
                    seed + i + 1,
                    divisions,
                    size,
                    octaves,
                    persistence,
                    noiseScale,
                    noiseMag,
                    color, tinted,
                    tintOctaves, tintPersist, tintScale, tintThresh
            );

            Mat3D place = new Mat3D()
                    .translate(cx, cy, cz)
                    .rotate(Vec3.Y, rand.nextFloat() * 360f)
                    .rotate(Vec3.X, rand.nextFloat() * 360f);

            rocks.add(new MatMesh(rock, place));
        }

        belt = new MultiMesh(rocks.toArray(GenericMesh.class));
        ready = true;
    }

    @Override
    public void preRender(PlanetParams params){
        if(!ready) init();
        if(belt != null){
            belt.preRender(params);
        }
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform){
        if(!ready) init();
    
        mat.set(transform)
           .rotate(Vec3.X, tilt)
           .rotate(Vec3.Y, Time.globalTime / 60f * orbitSpeed * Mathf.PI2);
    
        belt.render(params, projection, mat);
    }

    @Override
    public void dispose(){
        if(!ready) return;
        if(belt != null){
            belt.dispose();
            belt = null;
        }
        ready = false;
    }
}
