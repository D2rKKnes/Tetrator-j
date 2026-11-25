package terra.type.part;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.part.*;
import mindustry.graphics.*;

public class RingPart extends DrawPart {
    public int sides = 16;
    public float radius = 10f, stroke = 2f;
    public float x = 0f, y = 0f, angle = 90f;
    public boolean clockwise = true;
    public boolean rotate = false;
    public float layer = Layer.effect;
    public float layerOffset = 0f;

    public Color color = Pal.stat, colorTo = Pal.negativeStat;
    public PartProgress progress = PartProgress.reload;

    @Override
    public void draw(PartParams params) {
        float prog = Mathf.clamp(progress.get(params));
        float remaining = 1f - prog;

        if (remaining <= 0.001f || stroke <= 0f || sides < 3) return;
        float z = Draw.z();
        if (layer > 0) {
            Draw.z(layer + layerOffset);
        } else {
            Draw.z(z + layerOffset);
        }

        Tmp.v1.set(x, y).rotate(params.rotation - 90f);
        float rx = params.x + Tmp.v1.x;
        float ry = params.y + Tmp.v1.y;

        Draw.color(color, colorTo, prog);
        float totalAngle = 360f * remaining;
        int activeSides = Mathf.ceil(sides * remaining);
        float stepAngle = 360f / sides;
        float dir = clockwise ? -1f : 1f;
        float baseAngle = angle + (rotate ? params.rotation - 90f : 0f);

        float innerR = Math.max(0f, radius - stroke / 2f);
        float outerR = radius + stroke / 2f;
        for (int i = 0; i < activeSides; i++) {
            float a1 = baseAngle + dir * (i * stepAngle);
            float currentStep = Math.min(stepAngle, totalAngle - i * stepAngle);
            float a2 = a1 + dir * currentStep;

            float cos1 = Mathf.cosDeg(a1), sin1 = Mathf.sinDeg(a1);
            float cos2 = Mathf.cosDeg(a2), sin2 = Mathf.sinDeg(a2);

            Fill.quad(
                rx + cos1 * innerR, ry + sin1 * innerR,
                rx + cos1 * outerR, ry + sin1 * outerR,
                rx + cos2 * outerR, ry + sin2 * outerR,
                rx + cos2 * innerR, ry + sin2 * innerR
            );
        }

        Draw.reset();
        Draw.z(z);
    }

    @Override
    public void load(String name) {

    }
}
