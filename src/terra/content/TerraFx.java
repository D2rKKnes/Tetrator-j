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
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class NHFx {
    public static final Effect
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
    private static void tri(float x, float y, float width, float length, float angle) {
        float wx = Angles.trnsx(angle + 90, width), wy = Angles.trnsy(angle + 90, width);
        Fill.tri(x + wx, y + wy, x - wx, y - wy, Angles.trnsx(angle, length) + x, Angles.trnsy(angle, length) + y);
    }
}
