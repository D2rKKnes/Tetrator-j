package terra.type;

import arc.graphics.*;
import arc.graphics.g3d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;

public class PulsarPlanet extends BetterPlanet {

    public float rotationSpeed = 1f;
    public Color beamColor = Color.white.cpy();
    public float beamAngleDeg = 45f;
    public float beamLength = 10f;
    public float beamWidth = 0.5f;
    public int beamSegments = 20;

    public PulsarPlanet(String name, Planet parent, float radius) {
        super(name, parent, radius);
    }

    public PulsarPlanet(String name, Planet parent, float radius, int sectorSize) {
        super(name, parent, radius, sectorSize);
    }

    @Override
    public void draw(PlanetParams params, Mat3D projection, Mat3D transform) {
        super.draw(params, projection, transform);
        drawBeam(params, projection, transform);
    }

    protected void drawBeam(PlanetParams params, Mat3D projection, Mat3D transform) {
        float time = Time.globalTime;
        float azimuth = time * rotationSpeed * Mathf.PI2;
        float beamAngle = Mathf.degRad * beamAngleDeg;

        Vec3 up = Tmp.v31.set(Vec3.Y);
        Vec3 dir = Tmp.v32.set(up).rotate(Vec3.X, beamAngle).rotate(Vec3.Y, azimuth).nor();

        Vec3 start = Tmp.v33.set(up).scl(radius);
        Vec3 end = Tmp.v34.set(dir).scl(beamLength).add(start);

        VertexBatch3D batch = new VertexBatch3D(beamSegments * 6);
        batch.proj(projection);
        batch.trans(transform);

        Blending blend = Blending.additive;
        blend.apply();

        Color col = beamColor.cpy();

        for (int i = 0; i < beamSegments; i++) {
            float t1 = i / (float) beamSegments;
            float t2 = (i + 1) / (float) beamSegments;

            float alpha1 = 1f - t1;
            float alpha2 = 1f - t2;

            Vec3 p1 = Tmp.v35.set(start).lerp(end, t1);
            Vec3 p2 = Tmp.v36.set(start).lerp(end, t2);

            Vec3 side = Tmp.v37.set(dir).crs(up).nor();
            if (side.len() < 0.001f) {
                side.set(Vec3.X).crs(dir).nor();
            }
            float halfWidth = beamWidth * 0.5f;

            Vec3 p1l = Tmp.v38.set(p1).add(side, -halfWidth);
            Vec3 p1r = Tmp.v39.set(p1).add(side,  halfWidth);
            Vec3 p2l = Tmp.v40.set(p2).add(side, -halfWidth);
            Vec3 p2r = Tmp.v41.set(p2).add(side,  halfWidth);

            Color c1 = col.cpy().a(alpha1);
            Color c2 = col.cpy().a(alpha2);

            batch.tri(p1l, p1r, p2l, c1, c1, c2);
            batch.tri(p1r, p2r, p2l, c1, c2, c2);
        }

        batch.flush(Gl.triangles);

        Blending.normal.apply();
    }
}
