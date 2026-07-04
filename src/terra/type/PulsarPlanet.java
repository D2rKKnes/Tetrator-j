package terra.type;

import arc.graphics.*;
import arc.graphics.g3d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;

public class PulsarPlanet extends BetterPlanet {

    public float rotationSpeed = 3f;
    public Color beamColor = Color.white.cpy();
    public float beamAngleDeg = 30f;
    public float beamLength = 7f;
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

        Vec3 start = Tmp.v33.set(up).scl(radius * 1.01f);
        Vec3 end = Tmp.v34.set(dir).scl(beamLength).add(start);

        VertexBatch3D batch = new VertexBatch3D();
        batch.proj(projection);
        batch.proj().mul(transform);

        Blending.additive.apply();

        Color col = beamColor.cpy();
        Vec3 side = Tmp.v31.set(dir).crs(up).nor();
        if (side.len() < 0.001f) {
            side.set(Vec3.X).crs(dir).nor();
        }
        float halfWidth = beamWidth * 0.5f;

        Vec3 p1 = new Vec3();
        Vec3 p2 = new Vec3();
        Vec3 p1l = new Vec3();
        Vec3 p1r = new Vec3();
        Vec3 p2l = new Vec3();
        Vec3 p2r = new Vec3();

        for (int i = 0; i < beamSegments; i++) {
            float t1 = (float) i / beamSegments;
            float t2 = (float) (i + 1) / beamSegments;
            float alpha1 = 1f - t1;
            float alpha2 = 1f - t2;

            p1.set(start).lerp(end, t1);
            p2.set(start).lerp(end, t2);

            p1l.set(p1).add(side, -halfWidth);
            p1r.set(p1).add(side,  halfWidth);
            p2l.set(p2).add(side, -halfWidth);
            p2r.set(p2).add(side,  halfWidth);

            Color c1 = col.cpy().a(alpha1);
            Color c2 = col.cpy().a(alpha2);

            batch.color(c1);
            batch.vertex(p1l);
            batch.color(c1);
            batch.vertex(p1r);
            batch.color(c2);
            batch.vertex(p2l);

            batch.color(c1);
            batch.vertex(p1r);
            batch.color(c2);
            batch.vertex(p2r);
            batch.color(c2);
            batch.vertex(p2l);
        }

        batch.flush(Gl.triangles);
        Blending.normal.apply();
    }
}
