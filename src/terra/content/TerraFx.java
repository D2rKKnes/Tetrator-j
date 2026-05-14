package terra.content;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Scl;
import arc.struct.IntMap;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.pooling.Pools;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;
import mindustry.type.UnitType;
import mindustry.ui.Fonts;

import java.util.Arrays;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.graphics.g2d.Fill.*;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class TerraFx {
    private static final Rand rand = new Rand();
    public static final Effect
    arcSmoke = new Effect(100f, 80f, e -> {
        rand.setSeed(e.id);

        Draw.color(Pal.darkerGray, Pal.darkishGray, e.fin(Interp.sineIn));
        Draw.alpha(e.fout() * 0.7f);
        float angle = 135f + (Mathf.sin(e.fin(Interp.pow2Out) * 2f) * rand.range(20f)) + rand.range(10f);
        float len = e.fin(Interp.pow2Out) * 40f * rand.random(0.8f, 1.3f);
        Fill.circle(e.x + Angles.trnsx(angle, len), e.y + Angles.trnsy(angle, len), (1.5f + e.fin(Interp.sineIn) * 5f) * rand.random(0.8f, 1.3f));
    }).layer(111f),
    
    hitSpark = new Effect(45, e -> {
        color(e.color, Color.white, e.fout() * 0.3f);
        stroke(e.fout() * 1.6f);

        rand.setSeed(e.id);
        randLenVectors(e.id, 8, e.finpow() * 20f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * rand.random(1.95f, 4.25f) + 1f);
        });
    }),
    
    hitSparkLarge = new Effect(40, e -> {
        color(e.color, Color.white, e.fout() * 0.3f);
        stroke(e.fout() * 1.6f);

        rand.setSeed(e.id);
        randLenVectors(e.id, 18, e.finpow() * 27f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * rand.random(4, 8) + 2f);
        });
    }),
    
    circleOut = new Effect(60f, 500f, e -> {
        Lines.stroke(2.5f * e.fout(), e.color);
        Lines.circle(e.x, e.y, e.rotation * e.fin(Interp.pow3Out));
    }),

    crossBlastArrow45 = new Effect(65, 140, e -> {
        color(e.color, Color.white, e.fout() * 0.55f);
        Drawf.light(e.x, e.y, e.fout() * 70, e.color, 0.7f);

        e.scaled(10f, i -> {
            stroke(1.35f * i.fout());
            Fill.circle(e.x, e.y, 49 * i.finpow());
        });

        rand.setSeed(e.id);
        float sizeDiv = 138;
        float randL = rand.random(sizeDiv);

        float f = Mathf.curve(e.fin(), 0, 0.05f);

        for (int i = 0; i < 4; i++) {
            Tmp.v1.trns(45 + i * 90, 66);
            arrow(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 27.5f * (e.fout() * 3f + 1) / 4 * e.fout(Interp.pow3In), (sizeDiv + randL) * f * e.fout(Interp.pow3), -randL / 6f * f, i * 90 + 45);
        }
    }),
    
    jumpTrail = new Effect(120f, 5000, e -> {
        if (!(e.data instanceof UnitType)) return;
        UnitType type = e.data();
        color(type.engineColor == null ? e.color : type.engineColor);

        if (type.engineLayer > 0) Draw.z(type.engineLayer);
        else Draw.z((type.lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) - 0.001f);

        for (int index = 0; index < type.engines.size; index++) {
            UnitType.UnitEngine engine = type.engines.get(index);

            if (Angles.angleDist(engine.rotation, -90) > 75) return;
            float ang = Mathf.slerp(engine.rotation, -90, 0.75f);

            //noinspection SuspiciousNameCombination
            Tmp.v1.trns(e.rotation, engine.y, -engine.x);

            e.scaled(80, i -> {
                tri(i.x + Tmp.v1.x, i.y + Tmp.v1.y, engine.radius * 1.5f * i.fout(Interp.slowFast), 3000 * engine.radius / (type.engineSize + 4), i.rotation + ang - 90);
                Fill.circle(i.x + Tmp.v1.x, i.y + Tmp.v1.y, engine.radius * 1.5f * i.fout(Interp.slowFast));
            });

            randLenVectors(e.id + index, 22, 400 * engine.radius / (type.engineSize + 4), e.rotation + ang - 90, 0f, (x, y) -> lineAngle(e.x + x + Tmp.v1.x, e.y + y + Tmp.v1.y, Mathf.angle(x, y), e.fout() * 60));
        }

        Draw.color();
        Draw.mixcol(e.color, 1);
        Draw.rect(type.fullIcon, e.x, e.y, type.fullIcon.width * e.fout(Interp.pow2Out) * Draw.scl * 1.2f, type.fullIcon.height * e.fout(Interp.pow2Out) * Draw.scl * 1.2f, e.rotation - 90f);
        Draw.reset();
    }),

    jumpTrailOut = new Effect(120f, 200, e -> {
        if (!(e.data instanceof UnitType)) return;
        UnitType type = e.data();
        color(type.engineColor == null ? e.color : type.engineColor);

        if (type.engineLayer > 0) Draw.z(type.engineLayer);
        else Draw.z((type.lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) - 0.001f);

        Tmp.v2.trns(e.rotation, 2300);

        for (int index = 0; index < type.engines.size; index++) {
            UnitType.UnitEngine engine = type.engines.get(index);

            if (Angles.angleDist(engine.rotation, -90) > 75) return;
            float ang = Mathf.slerp(engine.rotation, -90, 0.75f);

            //noinspection SuspiciousNameCombination
            Tmp.v1.trns(e.rotation, engine.y, -engine.x).add(Tmp.v2);

            rand.setSeed(e.id);
            e.scaled(80, i -> {
                tri(i.x + Tmp.v1.x, i.y + Tmp.v1.y, engine.radius * 3f * i.fout(Interp.slowFast), 2300 + rand.range(120), i.rotation + ang - 90);
                Fill.circle(i.x + Tmp.v1.x, i.y + Tmp.v1.y, engine.radius * 3f * i.fout(Interp.slowFast));
            });

            randLenVectors(e.id + index, 42, 2330, e.rotation + ang - 90, 0f, (x, y) -> lineAngle(e.x + x + Tmp.v1.x, e.y + y + Tmp.v1.y, Mathf.angle(x, y), e.fout() * 60));
        }
    });
    private static void arrow(float x, float y, float width, float length, float backLength, float angle) {
        float wx = Angles.trnsx(angle + 90, width), wy = Angles.trnsy(angle + 90, width);
        float ox = Angles.trnsx(angle, backLength), oy = Angles.trnsy(angle, backLength);
        float cx = Angles.trnsx(angle, length) + x, cy = Angles.trnsy(angle, length) + y;
        Fill.tri(x + ox, y + oy, x - wx, y - wy, cx, cy);
        Fill.tri(x + wx, y + wy, x + ox, y + oy, cx, cy);
    }

    private static void tri(float x, float y, float width, float length, float angle) {
        float wx = Angles.trnsx(angle + 90, width), wy = Angles.trnsy(angle + 90, width);
        Fill.tri(x + wx, y + wy, x - wx, y - wy, Angles.trnsx(angle, length) + x, Angles.trnsy(angle, length) + y);
    }

    public static Effect hitSpark(Color color, float lifetime, int num, float range, float stroke, float length) {
        return new Effect(lifetime, e -> {
            color(color, Color.white, e.fout() * 0.3f);
            stroke(e.fout() * stroke);

            randLenVectors(e.id, num, e.finpow() * range, e.rotation, 360f, (x, y) -> {
                float ang = Mathf.angle(x, y);
                lineAngle(e.x + x, e.y + y, ang, e.fout() * length * 0.85f + length * 0.15f);
            });
        });
    }

    public static Effect smoothColorCircle(Color out, float rad, float lifetime) {
        return new Effect(lifetime, rad * 2, e -> {
            Draw.blend(Blending.additive);
            float radius = e.fin(Interp.pow3Out) * rad;
            Fill.light(e.x, e.y, circleVertices(radius), radius, Color.clear, Tmp.c1.set(out).a(e.fout(Interp.pow5Out)));
            Drawf.light(e.x, e.y, radius * 1.3f, out, 0.7f * e.fout(0.23f));
            Draw.blend();
        }).layer(Layer.effect + 0.15f);
    }

    public static Effect smoothColorCircle(Color out, float rad, float lifetime, float alpha) {
        return new Effect(lifetime, rad * 2, e -> {
            Draw.blend(Blending.additive);
            float radius = e.fin(Interp.pow3Out) * rad;
            Fill.light(e.x, e.y, circleVertices(radius), radius, Color.clear, Tmp.c1.set(out).a(e.fout(Interp.pow5Out) * alpha));
            Drawf.light(e.x, e.y, radius * 1.3f, out, 0.7f * e.fout(0.23f));
            Draw.blend();
        }).layer(Layer.effect + 0.15f);
    }
}
