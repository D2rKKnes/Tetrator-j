package terra.graphics.g3d;

import arc.graphics.*;
import arc.graphics.gl.Shader;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.util.Time;
import arc.util.noise.*;
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
    public float minSize = 0.01f;
    public float maxSize = 0.06f;
    public float jaggedness = 0.6f;
    public float stretch = 0.5f;
    public int seed = 1;

    public float orbitSpeed = 0.02f;
    public float kepler = 1f;

    public Color colorDark = Color.valueOf("000000");
    public Color colorLight = Color.valueOf("ffffff");
    public float ambient = 0f;
    public float lightPower = 1f;
    public boolean lightFromCenter = true;
    public int hexDivisions = 1;

    private boolean ready;
    private Shader beltShader;
    private final Mat3D beltTrans = new Mat3D();
    private final Vec3 light = new Vec3();

    public AsteroidBeltMesh(Planet planet){
        this.planet = planet;
    }

    private void init(){
        float[] data = buildRocks();
        VertexAttribute[] attrs = {
                VertexAttribute.position3,
                new VertexAttribute(3, GL20.GL_FLOAT, false, "a_normal"),
                new VertexAttribute(2, GL20.GL_FLOAT, false, "a_orbit")
        };
        mesh = new Mesh(true, data.length / FLOATS, 0, attrs);
        mesh.setVertices(data);

        beltShader = new Shader(VERT, FRAG);
        shader = beltShader;
        ready = true;
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform){
        if(!ready) init();

        beltTrans.set(transform).rotate(Vec3.X, tilt);

        if(!lightFromCenter){
            light.set(planet.solarSystem.position).sub(planet.position)
                    .rotate(Vec3.Y, planet.getRotation())
                    .rotate(Vec3.X, -tilt)
                    .nor();
        }

        beltShader.bind();
        beltShader.setUniformMatrix4("u_proj", projection.val);
        beltShader.setUniformMatrix4("u_trans", beltTrans.val);
        beltShader.setUniformf("u_time", Time.globalTime / 60f);
        beltShader.setUniformf("u_colorDark", colorDark.r, colorDark.g, colorDark.b);
        beltShader.setUniformf("u_colorLight", colorLight.r, colorLight.g, colorLight.b);
        beltShader.setUniformf("u_lightDir", light.x, light.y, light.z);
        beltShader.setUniformf("u_center", lightFromCenter ? 1f : 0f);
        beltShader.setUniformf("u_ambient", ambient);
        beltShader.setUniformf("u_lightPower", lightPower);

        mesh.render(beltShader, Gl.triangles);
    }

    @Override
    public void dispose(){
        if(!ready) return;
        super.dispose();
        beltShader.dispose();
    }

    private static final int FLOATS = 8;

    private float[] buildRocks(){
        PlanetGrid grid = PlanetGrid.create(hexDivisions);

        Vec3[] dv = new Vec3[grid.corners.length];
        for(int i = 0; i < dv.length; i++) dv[i] = new Vec3();

        int vertsPerRock = 0;
        for(PlanetGrid.Ptile t : grid.tiles) vertsPerRock += (t.corners.length - 2) * 3;

        Random rnd = new Random(seed);
        int n = Math.max(1, count);
        float[] out = new float[n * vertsPerRock * FLOATS];
        int idx = 0;

        Vec3 nor = new Vec3(), tmp = new Vec3(), cen = new Vec3(), dir = new Vec3(), pos = new Vec3();
        float R = planet.radius;

        for(int r = 0; r < n; r++){
            float ang = rnd.nextFloat() * Mathf.PI2;
            float dist = ringRadius + (rnd.nextFloat() + rnd.nextFloat() - 1f) * ringWidth * 0.5f;
            float y = Mathf.clamp((float)rnd.nextGaussian() * ringThickness * 0.5f, -ringThickness, ringThickness);
            float size = minSize + (float)Math.pow(rnd.nextFloat(), 3.0) * (maxSize - minSize);

            float sx = 1f + stretch * rnd.nextFloat();
            float sy = 1f - 0.5f * stretch * rnd.nextFloat();
            float sz = 1f + stretch * (rnd.nextFloat() - 0.5f);
            float rx = rnd.nextFloat() * 360f, ry = rnd.nextFloat() * 360f, rz = rnd.nextFloat() * 360f;

            float cx = Mathf.cos(ang) * dist * R;
            float cy = y * R;
            float cz = Mathf.sin(ang) * dist * R;

            float orbit = orbitSpeed * Mathf.PI2 * (float)Math.pow(ringRadius / Math.max(dist, 0.01f), 1.5f * kepler);

            int shapeSeed = seed * 7919 + r;
            float noiseScale = 1.2f + rnd.nextFloat() * 1.8f;
            for(PlanetGrid.Corner cr : grid.corners){
                tmp.set(cr.v).nor();
                double nz = Simplex.noise3d(shapeSeed, 3, 0.5, noiseScale, tmp.x, tmp.y, tmp.z);
                float bump = Math.max(0.25f, 1f + jaggedness * ((float)nz - 0.5f) * 2f);
                dv[cr.id].set(tmp).scl(bump).scl(sx, sy, sz)
                        .rotate(Vec3.X, rx).rotate(Vec3.Y, ry).rotate(Vec3.Z, rz)
                        .scl(size * R);
            }

            for(PlanetGrid.Ptile tile : grid.tiles){
                PlanetGrid.Corner[] c = tile.corners;
                Vec3 a = dv[c[0].id];

                nor.set(0f, 0f, 0f);
                cen.set(0f, 0f, 0f);
                for(int i = 0; i < c.length; i++) cen.add(dv[c[i].id]);
                cen.scl(1f / c.length);

                for(int i = 1; i < c.length - 1; i++){
                    Vec3 b = dv[c[i].id], d = dv[c[i + 1].id];
                    tmp.set(b).sub(a).crs(d.x - a.x, d.y - a.y, d.z - a.z);
                    nor.add(tmp);
                }
                boolean flip = nor.dot(cen) < 0f;
                nor.nor();
                if(flip) nor.scl(-1f);

                dir.set(tile.v).nor();
                double noise = Simplex.noise3d(8 + seed + r, 3, 0.5, 2, dir.x, dir.y, dir.z);
                float shade = noise > 0.5 ? 1f : 0f;

                for(int i = 1; i < c.length - 1; i++){
                    Vec3 b = dv[c[i].id], d = dv[c[i + 1].id];
                    Vec3 p1 = flip ? d : b, p2 = flip ? b : d;

                    pos.set(a).add(cx, cy, cz);  idx = put(out, idx, pos, nor, orbit, shade);
                    pos.set(p1).add(cx, cy, cz); idx = put(out, idx, pos, nor, orbit, shade);
                    pos.set(p2).add(cx, cy, cz); idx = put(out, idx, pos, nor, orbit, shade);
                }
            }
        }
        return out;
    }

    private static int put(float[] out, int i, Vec3 p, Vec3 n, float orbit, float shade){
        out[i++] = p.x; out[i++] = p.y; out[i++] = p.z;
        out[i++] = n.x; out[i++] = n.y; out[i++] = n.z;
        out[i++] = orbit; out[i++] = shade;
        return i;
    }

    private static final String VERT =
            "attribute vec3 a_position;\n" +
                    "attribute vec3 a_normal;\n" +
                    "attribute vec2 a_orbit;\n" +
                    "uniform mat4 u_proj;\n" +
                    "uniform mat4 u_trans;\n" +
                    "uniform float u_time;\n" +
                    "varying vec3 v_normal;\n" +
                    "varying vec3 v_pos;\n" +
                    "varying float v_shade;\n" +
                    "vec3 rotY(vec3 v, float c, float s){\n" +
                    "    return vec3(c * v.x + s * v.z, v.y, -s * v.x + c * v.z);\n" +
                    "}\n" +
                    "void main(){\n" +
                    "    float a = u_time * a_orbit.x;\n" +
                    "    float c = cos(a);\n" +
                    "    float s = sin(a);\n" +
                    "    vec3 p = rotY(a_position, c, s);\n" +
                    "    v_pos = p;\n" +
                    "    v_normal = rotY(a_normal, c, s);\n" +
                    "    v_shade = a_orbit.y;\n" +
                    "    gl_Position = u_proj * u_trans * vec4(p, 1.0);\n" +
                    "}\n";

    private static final String FRAG =
            "uniform vec3 u_colorDark;\n" +
                    "uniform vec3 u_colorLight;\n" +
                    "uniform vec3 u_lightDir;\n" +
                    "uniform float u_center;\n" +
                    "uniform float u_ambient;\n" +
                    "uniform float u_lightPower;\n" +
                    "varying vec3 v_normal;\n" +
                    "varying vec3 v_pos;\n" +
                    "varying float v_shade;\n" +
                    "void main(){\n" +
                    "    vec3 n = normalize(v_normal);\n" +
                    "    vec3 l = normalize(mix(u_lightDir, -v_pos, u_center));\n" +
                    "    float diff = max(dot(n, l), 0.0);\n" +
                    "    vec3 base = mix(u_colorDark, u_colorLight, v_shade);\n" +
                    "    gl_FragColor = vec4(base * (u_ambient + diff * u_lightPower), 1.0);\n" +
                    "}\n";
        }
