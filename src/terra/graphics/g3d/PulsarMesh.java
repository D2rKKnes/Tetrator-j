package terra.graphics.g3d;

import arc.graphics.*;
import arc.graphics.gl.Shader;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.struct.FloatSeq;
import arc.util.Time;
import mindustry.graphics.g3d.GenericMesh;
import mindustry.graphics.g3d.PlanetMesh;
import mindustry.graphics.g3d.PlanetParams;
import mindustry.type.Planet;

public class PulsarMesh extends PlanetMesh{
    public GenericMesh surface;

    public Color coreColor = Color.valueOf("5d47ff");
    public Color hotColor  = Color.valueOf("d8ccff");
    public Color beamColor = Color.valueOf("7f9bff");

    public float intensity = 1.6f;

    public float pulseRate = 1.5f;
    public float pulseDepth = 0.5f;
    public float pulseSharpness = 2f;

    public float noiseScale = 3f;
    public float noiseSpeed = 0.05f;
    public float noiseContrast = 1.5f;
    public float noiseAmount = 0.8f;
    public boolean drawCore = true;

    public float spinSpeed = 0.2f;
    public float beamTilt = 35f;
    public float beamLength = 6f;
    public float beamWidth = 0.7f;
    public float beamFalloff = 1.5f;
    public float beamAlpha = 0.5f;
    public int beamLayers = 3;

    private boolean ready;
    private Mesh beamMesh;
    private Shader coreShader, beamShader;
    private final Vec3 axis = new Vec3(), right = new Vec3(), fwd = new Vec3();
    private final Mat3D spinTrans = new Mat3D();

    public PulsarMesh(Planet planet){
        this.planet = planet;
    }

    private void init(){
        this.mesh = makeMesh(buildSphere(32, 16));
        this.beamMesh = makeMesh(buildBeams(32));
        this.coreShader = new Shader(CORE_VERT, CORE_FRAG);
        this.beamShader = new Shader(BEAM_VERT, BEAM_FRAG);
        this.shader = coreShader;
        this.ready = true;
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform){
        if(!ready) init();

        float time = Time.globalTime / 60f;

        spinTrans.set(transform).rotate(Vec3.Y, time * spinSpeed * 360f);

        float tilt = beamTilt * Mathf.degRad;
        axis.set(Mathf.sin(tilt), Mathf.cos(tilt), 0f).nor();
        right.set(Vec3.Y).crs(axis);
        if(right.len2() < 0.0001f) right.set(Vec3.X);
        right.nor();
        fwd.set(axis).crs(right).nor();

        float s = 0.5f + 0.5f * Mathf.sin(time * pulseRate * Mathf.PI2);
        float flash = (1f - pulseDepth) + pulseDepth * Mathf.pow(s, pulseSharpness);

        if(surface != null){
            surface.render(params, projection, spinTrans);
        }else if(drawCore){
            coreShader.bind();
            coreShader.setUniformMatrix4("u_proj", projection.val);
            coreShader.setUniformMatrix4("u_trans", spinTrans.val);
            coreShader.setUniformf("u_radius", planet.radius);
            coreShader.setUniformf("u_axis", axis.x, axis.y, axis.z);
            coreShader.setUniformf("u_coreColor", coreColor.r, coreColor.g, coreColor.b);
            coreShader.setUniformf("u_hotColor", hotColor.r, hotColor.g, hotColor.b);
            coreShader.setUniformf("u_brightness", intensity * flash);
            coreShader.setUniformf("u_time", time);
            coreShader.setUniformf("u_noiseScale", noiseScale);
            coreShader.setUniformf("u_noiseSpeed", noiseSpeed);
            coreShader.setUniformf("u_noiseContrast", noiseContrast);
            coreShader.setUniformf("u_noiseAmount", noiseAmount);
            mesh.render(coreShader, Gl.triangles);
        }

        Gl.depthMask(false);
        Gl.enable(Gl.blend);
        Gl.blendFunc(Gl.srcAlpha, Gl.one);

        beamShader.bind();
        beamShader.setUniformMatrix4("u_proj", projection.val);
        beamShader.setUniformMatrix4("u_trans", spinTrans.val);
        beamShader.setUniformf("u_axis", axis.x, axis.y, axis.z);
        beamShader.setUniformf("u_right", right.x, right.y, right.z);
        beamShader.setUniformf("u_fwd", fwd.x, fwd.y, fwd.z);
        beamShader.setUniformf("u_length", planet.radius * beamLength);
        beamShader.setUniformf("u_falloff", beamFalloff);
        beamShader.setUniformf("u_color", beamColor.r * intensity, beamColor.g * intensity, beamColor.b * intensity);

        int layers = Math.max(1, beamLayers);
        for(int i = 1; i <= layers; i++){
            float k = i / (float)layers;
            beamShader.setUniformf("u_width", planet.radius * beamWidth * k);
            beamShader.setUniformf("u_alpha", beamAlpha * flash / layers);
            beamMesh.render(beamShader, Gl.triangles);
        }

        Gl.blendFunc(Gl.srcAlpha, Gl.oneMinusSrcAlpha);
        Gl.depthMask(true);
    }

    @Override
    public void dispose(){
        if(surface != null) surface.dispose();
        if(!ready) return;
        super.dispose();
        beamMesh.dispose();
        coreShader.dispose();
        beamShader.dispose();
    }

    private static Mesh makeMesh(float[] v){
        Mesh m = new Mesh(true, v.length / 3, 0, VertexAttribute.position3);
        m.setVertices(v);
        return m;
    }

    private static void vert(FloatSeq v, float x, float y, float z){
        v.add(x); v.add(y); v.add(z);
    }

    private static void tri(FloatSeq v, float[] a, float[] b, float[] c){
        vert(v, a[0], a[1], a[2]); vert(v, b[0], b[1], b[2]); vert(v, c[0], c[1], c[2]);
        vert(v, a[0], a[1], a[2]); vert(v, c[0], c[1], c[2]); vert(v, b[0], b[1], b[2]);
    }

    private static float[] sp(float a, float b){
        return new float[]{Mathf.sin(a) * Mathf.cos(b), Mathf.cos(a), Mathf.sin(a) * Mathf.sin(b)};
    }

    private static float[] buildSphere(int seg, int rings){
        FloatSeq v = new FloatSeq();
        for(int r = 0; r < rings; r++){
            float a0 = Mathf.PI * r / rings, a1 = Mathf.PI * (r + 1) / rings;
            for(int s = 0; s < seg; s++){
                float b0 = Mathf.PI2 * s / seg, b1 = Mathf.PI2 * (s + 1) / seg;
                float[] p00 = sp(a0, b0), p01 = sp(a0, b1), p10 = sp(a1, b0), p11 = sp(a1, b1);
                tri(v, p00, p10, p11);
                tri(v, p00, p11, p01);
            }
        }
        return v.toArray();
    }

    private static float[] buildBeams(int sides){
        FloatSeq v = new FloatSeq();
        float[] apex = {0, 0, 0};
        for(int sign = -1; sign <= 1; sign += 2){
            for(int i = 0; i < sides; i++){
                float a0 = Mathf.PI2 * i / sides, a1 = Mathf.PI2 * (i + 1) / sides;
                float[] p0 = {Mathf.cos(a0), sign, Mathf.sin(a0)};
                float[] p1 = {Mathf.cos(a1), sign, Mathf.sin(a1)};
                tri(v, apex, p0, p1);
            }
        }
        return v.toArray();
    }

    private static final String CORE_VERT =
            "attribute vec3 a_position;\n" +
                    "uniform mat4 u_proj;\n" +
                    "uniform mat4 u_trans;\n" +
                    "uniform float u_radius;\n" +
                    "varying vec3 v_dir;\n" +
                    "void main(){\n" +
                    "    v_dir = normalize(a_position);\n" +
                    "    gl_Position = u_proj * u_trans * vec4(a_position * u_radius, 1.0);\n" +
                    "}\n";

    private static final String CORE_FRAG =
            "uniform vec3 u_axis;\n" +
                    "uniform vec3 u_coreColor;\n" +
                    "uniform vec3 u_hotColor;\n" +
                    "uniform float u_brightness;\n" +
                    "uniform float u_time;\n" +
                    "uniform float u_noiseScale;\n" +
                    "uniform float u_noiseSpeed;\n" +
                    "uniform float u_noiseContrast;\n" +
                    "uniform float u_noiseAmount;\n" +
                    "varying vec3 v_dir;\n" +

                    "float hash(vec3 p){\n" +
                    "    p = fract(p * 0.3183099 + 0.1);\n" +
                    "    p *= 17.0;\n" +
                    "    return fract(p.x * p.y * p.z * (p.x + p.y + p.z));\n" +
                    "}\n" +

                    "float noise(vec3 x){\n" +
                    "    vec3 i = floor(x);\n" +
                    "    vec3 f = fract(x);\n" +
                    "    f = f * f * (3.0 - 2.0 * f);\n" +
                    "    return mix(\n" +
                    "        mix(mix(hash(i + vec3(0.0,0.0,0.0)), hash(i + vec3(1.0,0.0,0.0)), f.x),\n" +
                    "            mix(hash(i + vec3(0.0,1.0,0.0)), hash(i + vec3(1.0,1.0,0.0)), f.x), f.y),\n" +
                    "        mix(mix(hash(i + vec3(0.0,0.0,1.0)), hash(i + vec3(1.0,0.0,1.0)), f.x),\n" +
                    "            mix(hash(i + vec3(0.0,1.0,1.0)), hash(i + vec3(1.0,1.0,1.0)), f.x), f.y),\n" +
                    "        f.z);\n" +
                    "}\n" +

                    "float fbm(vec3 p){\n" +
                    "    float v = 0.0;\n" +
                    "    float a = 0.5;\n" +
                    "    for(int i = 0; i < 4; i++){\n" +
                    "        v += a * noise(p);\n" +
                    "        p *= 2.0;\n" +
                    "        a *= 0.5;\n" +
                    "    }\n" +
                    "    return v;\n" +
                    "}\n" +

                    "void main(){\n" +
                    "    vec3 d = normalize(v_dir);\n" +
                    "    vec3 p = d * u_noiseScale;\n" +
                    "    float t = u_time * u_noiseSpeed;\n" +
                    "    float n1 = fbm(p + vec3(0.0, t, 0.0));\n" +
                    "    float n2 = fbm(p * 2.0 - vec3(t * 0.7, 0.0, t));\n" +
                    "    float plasma = pow(clamp(n1 * 0.9 + n2 * 0.5, 0.0, 1.0), u_noiseContrast);\n" +
                    "    plasma = mix(1.0, plasma, u_noiseAmount);\n" +
                    "    float pole = pow(abs(dot(d, u_axis)), 4.0);\n" +
                    "    vec3 dark = u_coreColor * 0.35;\n" +
                    "    vec3 bright = mix(u_coreColor, u_hotColor, 0.4);\n" +
                    "    vec3 c = mix(dark, bright, plasma);\n" +
                    "    c = mix(c, u_hotColor, pole * 0.8);\n" +
                    "    gl_FragColor = vec4(c * u_brightness, 1.0);\n" +
                    "}\n";

    private static final String BEAM_VERT =
            "attribute vec3 a_position;\n" +
                    "uniform mat4 u_proj;\n" +
                    "uniform mat4 u_trans;\n" +
                    "uniform vec3 u_axis;\n" +
                    "uniform vec3 u_right;\n" +
                    "uniform vec3 u_fwd;\n" +
                    "uniform float u_length;\n" +
                    "uniform float u_width;\n" +
                    "varying float v_t;\n" +
                    "void main(){\n" +
                    "    float t = abs(a_position.y);\n" +
                    "    v_t = t;\n" +
                    "    vec3 pos = u_right * (a_position.x * u_width * t)\n" +
                    "             + u_fwd * (a_position.z * u_width * t)\n" +
                    "             + u_axis * (a_position.y * u_length);\n" +
                    "    gl_Position = u_proj * u_trans * vec4(pos, 1.0);\n" +
                    "}\n";

    private static final String BEAM_FRAG =
            "uniform vec3 u_color;\n" +
                    "uniform float u_alpha;\n" +
                    "uniform float u_falloff;\n" +
                    "varying float v_t;\n" +
                    "void main(){\n" +
                    "    float a = pow(clamp(1.0 - v_t, 0.0, 1.0), u_falloff) * u_alpha;\n" +
                    "    gl_FragColor = vec4(u_color, a);\n" +
                    "}\n";
}
