package terra.graphics.g3d;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;

public class TerraSunMesh extends HexMesh{
    public float offX = 500f, offY = 0f, offZ = 500f;
    public float rotationScl = -100f;
    public TerraSunMesh(Planet planet, int divisions, double octaves, double persistence, double scl, double pow, double mag, float colorScale, Color... colors){
        this(planet, divisions, octaves, persistence, scl, pow, mag, colorScale, -100f, 500f, 0f, 500f, colors);
    }
    public TerraSunMesh(Planet planet, int divisions, double octaves, double persistence, double scl, double pow, double mag, float colorScale, float rotationScl, float offX, float offY, float offZ, Color... colors){
        super(planet, new HexMesher(){

            @Override
            public float getHeight(Vec3 position){
                return 0;
            }

            @Override
            public void getColor(Vec3 position, Color out){
                // double height = Math.pow(Simplex.noise3d(0, octaves, persistence, scl, position.x, position.y, position.z), pow) * mag;
                // out.set(colors[Mathf.clamp((int)(height * colors.length), 0, colors.length - 1)]).mul(colorScale);
                Vec3 transformed = new Vec3(position).rotate(Vec3.Y, position.y * rotationScl).add(offX, offY, offZ);
                double height = Math.pow(Simplex.noise3d(0, octaves, persistence, scl, transformed.x, transformed.y, transformed.z), pow) * mag;
                out.set(colors[Mathf.clamp((int)(height * colors.length), 0, colors.length - 1)]).mul(colorScale);
            }
        }, divisions, Shaders.unlit);
    }
}
