package terra.type.part;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.entities.part.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class EnginePart extends DrawPart {
    public float size = 1f, trailLength = 0f, trailScl = 1f;
    public float x = 0f, y = 0f, rot = -90f;
    public float layer = -1f, layerOffset = 0f;
    public @Nullable Color color;
    public Color colorInner = Color.white;

    private final WeakHashMap<Object, Trail[]> trails = new WeakHashMap<>();
    private Object foundOwner;

    @Override
    public void draw(PartParams params) {
        float scale = Mathf.clamp(progress.get(params));
        if (scale <= 0.0001f || size <= 0f) return;

        Object owner = findOwner(params);

        Color teamColor = Tmp.c1.set(Draw.getColor());
        Color engineColor = color != null ? color : teamColor;
        Color tColor = trailColor != null ? trailColor : engineColor;

        float z = Draw.z();
        float targetZ = layer > 0 ? layer + layerOffset : z + layerOffset;
        Draw.z(targetZ);

        int len = mirror && params.sideOverride == -1 ? 2 : 1;
        float unitRot = params.rotation - 90f;

        Trail[] ownerTrails = null;
        if (owner != null && trailLength > 0) {
            ownerTrails = trails.computeIfAbsent(owner, o -> {
                Trail[] arr = new Trail[mirror ? 2 : 1];
                for (int j = 0; j < arr.length; j++) {
                    arr[j] = new Trail((int) trailLength);
                }
                return arr;
            });
        }

        for (int s = 0; s < len; s++) {
            int i = params.sideOverride == -1 ? s : params.sideOverride;
            float sign = (i == 0 ? 1f : -1f) * params.sideMultiplier;
            Tmp.v1.set(x * sign, y).rotate(unitRot);
            float ex = params.x + Tmp.v1.x;
            float ey = params.y + Tmp.v1.y;
            float rad = (size + Mathf.absin(Time.time, 2f, size / 4f)) * scale;
            float engineRot = unitRot + (sign < 0 ? 180f - rot : rot);

            if (ownerTrails != null && i < ownerTrails.length) {
                Trail trail = ownerTrails[i];
                if (!Vars.state.isPaused()) {
                    trail.update(ex, ey, scale);
                }
                float baseWidth = (size + Mathf.absin(Time.time, 2f, size / 4f)) * trailScl;

                Draw.z(targetZ - 0.001f);
                trail.draw(tColor, baseWidth);
                if (drawCap) {
                    trail.drawCap(tColor, baseWidth);
                }
                Draw.z(targetZ);
            }

            Draw.color(engineColor);
            Fill.circle(ex, ey, rad);
            Draw.color(colorInner);
            Fill.circle(ex - Angles.trnsx(engineRot, rad / 4f), ey - Angles.trnsy(engineRot, rad / 4f), rad / 2f);
        }

        Draw.reset();
        Draw.z(z);
    }
    private @Nullable Object findOwner(PartParams params) {
        foundOwner = null;

        Groups.unit.intersect(params.x - 16f, params.y - 16f, 32f, 32f, u -> {
            if (foundOwner != null) return;
            if (Mathf.equal(u.x, params.x, 0.01f) && Mathf.equal(u.y, params.y, 0.01f)) {
                foundOwner = u;
                return;
            }
            for (WeaponMount mount : u.mounts) {
                float weaponRot = u.rotation - 90f;
                float mx = u.x + Angles.trnsx(weaponRot, mount.weapon.x, mount.weapon.y);
                float my = u.y + Angles.trnsy(weaponRot, mount.weapon.x, mount.weapon.y);
                if (Mathf.equal(mx, params.x, 0.5f) && Mathf.equal(my, params.y, 0.5f)) {
                    foundOwner = mount;
                    return;
                }
            }
        });
        if (foundOwner != null) return foundOwner;
        Groups.bullet.intersect(params.x - 0.2f, params.y - 0.2f, 0.4f, 0.4f, b -> {
            if (foundOwner == null && Mathf.equal(b.x, params.x, 0.05f) && Mathf.equal(b.y, params.y, 0.05f)) {
                foundOwner = b;
            }
        });
        if (foundOwner != null) return foundOwner;
        Building build = Vars.world.buildWorld(params.x, params.y);
        if (build != null && Mathf.equal(build.x, params.x, 0.1f) && Mathf.equal(build.y, params.y, 0.1f)) {
            return build;
        }

        return null;
    }

    @Override
    public void load(String name) {

    }
}
