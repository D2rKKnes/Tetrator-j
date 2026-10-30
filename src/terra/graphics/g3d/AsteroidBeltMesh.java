package terra.graphics.g3d;

import arc.graphics.*;
import arc.graphics.gl.Shader;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.util.Time;
import mindustry.graphics.g3d.PlanetMesh;
import mindustry.graphics.g3d.PlanetParams;
import mindustry.type.Planet;

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

    public Color colorDark = Color.valueOf("3a3550");
    public Color colorLight = Color.valueOf("8a84a8");
    public float ambient = 0.25f;
    public float lightPower = 1.3f;
    public boolean lightFromCenter = true;

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

    private static final int[][] FACES = {
            {0, 11, 5}, {0, 5, 1}, {0, 1, 7}, {0, 7, 10}, {0, 10, 11},
            {1, 5, 9}, {5, 11, 4}, {11, 10, 2}, {10, 7, 6}, {7, 1, 8},
            {3, 9, 4}, {3, 4, 2}, {3, 2, 6}, {3, 6, 8}, {3, 8, 9},
            {4, 9, 5}, {2, 4, 11}, {6, 2, 10}, {8, 6, 7}, {9, 8, 1}
    };

    private static Vec3[] icoBase(){
        float t = (1f + Mathf.sqrt(5f)) / 2f;
        float[][] v = {
                {-1, t, 0}, {1, t, 0}, {-1, -t, 0}, {1, -t, 0},
                {0, -1, t}, {0, 1, t}, {0, -1, -t}, {0, 1, -t},
                {t, 0, -1}, {t, 0, 1}, {-t, 0, -1}, {-t, 0, 1}
        };
        Vec3[] out = new Vec3[12];
        for(int i = 0; i < 12; i++) out[i] = new Vec3(v[i][0], v[i][1], v[i][2]).nor();
        return out;
    }

    private float[] buildRocks(){
        Random rnd = new Random(seed);
        int n = Math.max(1, count);
        float[] out = new float[n * FACES.length * 3 * FLOATS];
        int idx = 0;

        Vec3[] base = icoBase();
        Vec3[] pv = new Vec3[12];
        for(int i = 0; i < 12; i++) pv[i] = new Vec3();
        Vec3 a = new Vec3(), b = new Vec3(), c = new Vec3(), nor = new Vec3(), tmp = new Vec3();

        float R = planet.radius;

        for(int r = 0; r < n; r++){
            float ang = rnd.nextFloat() * Mathf.PI2;
            float dist = ringRadius + (rnd.nextFloat() + rnd.nextFloat() - 1f) * ringWidth * 0.5f;
            float y = Mathf.clamp((float)rnd.nextGaussian() * ringThickness * 0.5f, -ringThickness, ringThickness);
            float size = minSize + (float)Math.pow(rnd.nextFloat(), 3.0) * (maxSize - minSize);

            float sx = 1f + stretch * rnd.nextFloat();
            float sy = 1f - 0.5f * stretch * rnd.nextFloat();
            float rx = rnd.nextFloat() * 360f, ry = rnd.nextFloat() * 360f, rz = rnd.nextFloat() * 360f;

            float cx = Mathf.cos(ang) * dist * R;
            float cy = y * R;
            float cz = Mathf.sin(ang) * dist * R;

            float orbit = orbitSpeed * Mathf.PI2 * (float)Math.pow(ringRadius / Math.max(dist, 0.01f), 1.5f * kepler);
            float shade = rnd.nextFloat();

            for(int i = 0; i < 12; i++){
                float jag = 1f + jaggedness * (rnd.nextFloat() - 0.5f);
                pv[i].set(base[i]).scl(jag).scl(sx, sy, 1f)
                        .rotate(Vec3.X, rx).rotate(Vec3.Y, ry).rotate(Vec3.Z, rz)
                        .scl(size * R).add(cx, cy, cz);
            }

            for(int[] f : FACES){
                a.set(pv[f[0]]);
                b.set(pv[f[1]]);
                c.set(pv[f[2]]);

                nor.set(b).sub(a).crs(c.x - a.x, c.y - a.y, c.z - a.z);
                tmp.set(a).add(b).add(c).scl(1f / 3f).sub(cx, cy, cz);
                if(nor.dot(tmp) < 0f){
                    tmp.set(b); b.set(c); c.set(tmp);
                    nor.scl(-1f);
                }
                nor.nor();

                idx = put(out, idx, a, nor, orbit, shade);
                idx = put(out, idx, b, nor, orbit, shade);
                idx = put(out, idx, c, nor, orbit, shade);
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