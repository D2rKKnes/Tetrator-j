package terra.graphics.g3d;

import arc.graphics.*;
import arc.graphics.gl.Shader;
import arc.math.Mathf;
import arc.math.geom.Mat3D;
import arc.struct.FloatSeq;
import arc.util.Time;
import mindustry.graphics.g3d.PlanetMesh;
import mindustry.graphics.g3d.PlanetParams;
import mindustry.type.Planet;

public class PulsarDiskMesh extends PlanetMesh{
    public float ringRadius = 2.2f;
    public float ringWidth = 1.2f;
    public float tilt = 0f;

    public Color color = Color.valueOf("7f9bff");
    public float intensity = 1.4f;
    public float alpha = 0.6f;
    public float bands = 6f;
    public float bandContrast = 0.35f;
    public float swirlSpeed = 0.5f;
    public float swirlArms = 3f;

    private boolean ready;
    private Shader diskShader;

    public PulsarDiskMesh(Planet planet){
        this.planet = planet;
    }

    private void init(){
        FloatSeq v = new FloatSeq();
        float[] a = {-1, 0, -1}, b = {1, 0, -1}, c = {1, 0, 1}, d = {-1, 0, 1};
        tri(v, a, b, c);
        tri(v, a, c, d);
        float[] arr = v.toArray();
        mesh = new Mesh(true, arr.length / 3, 0, VertexAttribute.position3);
        mesh.setVertices(arr);

        diskShader = new Shader(VERT, FRAG);
        shader = diskShader;
        ready = true;
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform){
        if(!ready) init();

        float meshRadius = ringRadius + ringWidth / 2f;
        float t = tilt * Mathf.degRad;

        diskShader.bind();
        diskShader.setUniformMatrix4("u_proj", projection.val);
        diskShader.setUniformMatrix4("u_trans", transform.val);
        diskShader.setUniformf("u_right", 1f, 0f, 0f);
        diskShader.setUniformf("u_fwd", 0f, -Mathf.sin(t), Mathf.cos(t));
        diskShader.setUniformf("u_size", planet.radius * meshRadius);
        diskShader.setUniformf("u_in", (ringRadius - ringWidth / 2f) / meshRadius);
        diskShader.setUniformf("u_out", 1f);
        diskShader.setUniformf("u_color", color.r * intensity, color.g * intensity, color.b * intensity);
        diskShader.setUniformf("u_alpha", alpha);
        diskShader.setUniformf("u_bands", bands);
        diskShader.setUniformf("u_contrast", bandContrast);
        diskShader.setUniformf("u_arms", swirlArms);
        diskShader.setUniformf("u_swirl", Time.globalTime / 60f * swirlSpeed);

        Gl.depthMask(false);
        Gl.enable(Gl.blend);
        Gl.blendFunc(Gl.srcAlpha, Gl.one);

        mesh.render(diskShader, Gl.triangles);

        Gl.blendFunc(Gl.srcAlpha, Gl.oneMinusSrcAlpha);
        Gl.depthMask(true);
    }

    @Override
    public void dispose(){
        if(!ready) return;
        super.dispose();
        diskShader.dispose();
    }

    private static void tri(FloatSeq v, float[] a, float[] b, float[] c){
        v.add(a[0]); v.add(a[1]); v.add(a[2]);
        v.add(b[0]); v.add(b[1]); v.add(b[2]);
        v.add(c[0]); v.add(c[1]); v.add(c[2]);
        v.add(a[0]); v.add(a[1]); v.add(a[2]);
        v.add(c[0]); v.add(c[1]); v.add(c[2]);
        v.add(b[0]); v.add(b[1]); v.add(b[2]);
    }

    private static final String VERT =
            "attribute vec3 a_position;\n" +
                    "uniform mat4 u_proj;\n" +
                    "uniform mat4 u_trans;\n" +
                    "uniform vec3 u_right;\n" +
                    "uniform vec3 u_fwd;\n" +
                    "uniform float u_size;\n" +
                    "varying vec2 v_pos;\n" +
                    "void main(){\n" +
                    "    v_pos = a_position.xz;\n" +
                    "    vec3 p = (u_right * a_position.x + u_fwd * a_position.z) * u_size;\n" +
                    "    gl_Position = u_proj * u_trans * vec4(p, 1.0);\n" +
                    "}\n";

    private static final String FRAG =
            "uniform vec3 u_color;\n" +
                    "uniform float u_alpha;\n" +
                    "uniform float u_in;\n" +
                    "uniform float u_out;\n" +
                    "uniform float u_bands;\n" +
                    "uniform float u_contrast;\n" +
                    "uniform float u_arms;\n" +
                    "uniform float u_swirl;\n" +
                    "varying vec2 v_pos;\n" +
                    "void main(){\n" +
                    "    float d = length(v_pos);\n" +
                    "    float mid = (u_in + u_out) * 0.5;\n" +
                    "    float hw = max((u_out - u_in) * 0.5, 0.0001);\n" +
                    "    float edge = 1.0 - smoothstep(0.0, 1.0, abs(d - mid) / hw);\n" +
                    "    float ang = atan(v_pos.y, v_pos.x);\n" +
                    "    float band = 1.0 - u_contrast + u_contrast * (0.5 + 0.5 * sin(d * u_bands * 6.2831));\n" +
                    "    float swirl = 0.8 + 0.2 * sin(ang * u_arms + d * 10.0 - u_swirl * 6.2831);\n" +
                    "    gl_FragColor = vec4(u_color, edge * band * swirl * u_alpha);\n" +
                    "}\n";
}