package terra.graphics.g3d;

import arc.graphics.*;
import arc.graphics.gl.Shader;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.struct.FloatSeq;
import arc.util.Log;
import arc.util.Time;
import mindustry.graphics.g3d.GenericMesh;
import mindustry.graphics.g3d.PlanetMesh;
import mindustry.graphics.g3d.PlanetParams;
import mindustry.type.Planet;

public class PulsarMesh extends PlanetMesh{
    public GenericMesh surface;

    public Color beamColor = Color.valueOf("7f9bff");
    public float intensity = 1.6f;

    public float pulseRate = 1.5f;
    public float pulseDepth = 0.5f;
    public float pulseSharpness = 2f;

    public float spinSpeed = 0.2f;
    public float beamTilt = 35f;
    public float beamLength = 6f;
    public float beamWidth = 0.7f;
    public float beamFalloff = 1.5f;
    public float beamAlpha = 0.5f;
    public int beamLayers = 3;

    public boolean beamsOverPlanets = true;
    public boolean deferBeams = false;
    public boolean hideBehindStar = false;

    public boolean debug = false;

    private boolean ready;
    private Mesh beamMesh;
    private Shader beamShader;
    private float flash;
    private int dbg;
    private final Vec3 axis = new Vec3(), right = new Vec3(), fwd = new Vec3();
    private final Mat3D spinTrans = new Mat3D(), clip = new Mat3D();

    public PulsarMesh(Planet planet){
        this.planet = planet;
    }

    private void init(){
        float[] v = buildBeams(32);
        beamMesh = new Mesh(true, v.length / 3, 0, VertexAttribute.position3);
        beamMesh.setVertices(v);
        beamShader = new Shader(BEAM_VERT, BEAM_FRAG);

        this.mesh = beamMesh;
        this.shader = beamShader;
        this.ready = true;
    }

    private void update(Mat3D transform){
        float time = Time.globalTime / 60f;

        spinTrans.set(transform).rotate(Vec3.Y, time * spinSpeed * 360f);

        float tilt = beamTilt * Mathf.degRad;
        axis.set(Mathf.sin(tilt), Mathf.cos(tilt), 0f).nor();
        right.set(Vec3.Y).crs(axis);
        if(right.len2() < 0.0001f) right.set(Vec3.X);
        right.nor();
        fwd.set(axis).crs(right).nor();

        float s = 0.5f + 0.5f * Mathf.sin(time * pulseRate * Mathf.PI2);
        flash = (1f - pulseDepth) + pulseDepth * Mathf.pow(s, pulseSharpness);
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform){
        if(!ready) init();
        update(transform);

        if(surface != null) surface.render(params, projection, spinTrans);
        if(!deferBeams) drawBeams(projection);
    }

    public void renderBeams(PlanetParams params, Mat3D projection, Mat3D transform){
        if(!ready) init();
        update(transform);
        drawBeams(projection);
    }

    private float clipW(float x, float y, float z){
        return clip.val[3] * x + clip.val[7] * y + clip.val[11] * z + clip.val[15];
    }

    private void drawBeams(Mat3D projection){
        float len = planet.radius * beamLength;

        if(debug && dbg++ % 120 == 0){
            clip.set(projection).mul(spinTrans);
            Log.info("[pulsar] w(+tip)=@ w(-tip)=@ w(center)=@",
                    clipW(axis.x * len, axis.y * len, axis.z * len),
                    clipW(-axis.x * len, -axis.y * len, -axis.z * len),
                    clipW(0f, 0f, 0f));
        }

        Gl.depthMask(false);
        if(beamsOverPlanets) Gl.disable(Gl.depthTest);
        Gl.enable(Gl.blend);
        Gl.blendFunc(Gl.srcAlpha, Gl.one);

        beamShader.bind();
        beamShader.setUniformMatrix4("u_proj", projection.val);
        beamShader.setUniformMatrix4("u_trans", spinTrans.val);
        beamShader.setUniformf("u_axis", axis.x, axis.y, axis.z);
        beamShader.setUniformf("u_right", right.x, right.y, right.z);
        beamShader.setUniformf("u_fwd", fwd.x, fwd.y, fwd.z);
        beamShader.setUniformf("u_length", len);
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
        Gl.enable(Gl.depthTest);
        Gl.depthMask(true);
    }

    @Override
    public void dispose(){
        if(surface != null) surface.dispose();
        if(!ready) return;
        super.dispose();
        beamShader.dispose();
    }


    private static void vert(FloatSeq v, float x, float y, float z){
        v.add(x); v.add(y); v.add(z);
    }

    private static void tri(FloatSeq v, float[] a, float[] b, float[] c){
        vert(v, a[0], a[1], a[2]); vert(v, b[0], b[1], b[2]); vert(v, c[0], c[1], c[2]);
        vert(v, a[0], a[1], a[2]); vert(v, c[0], c[1], c[2]); vert(v, b[0], b[1], b[2]);
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