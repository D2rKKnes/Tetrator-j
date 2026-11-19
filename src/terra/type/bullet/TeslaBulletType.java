package terra.type.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.type.*;

public class TeslaBulletType extends BasicBulletType {
    public float auraRadius = 24f;
    public float auraStroke = 2f;
    public float auraAlpha = 0.2f;

    public float lightningDamage = -1f;
    public float lightningInterval = 10f;
    public float lightningWidth = 5f;
    public float lightningChance = 1f;
    public Sound lightningShootSound = Sounds.shootArc;
    public float lightningSoundVolume = 0.2f;

    public Color auraColor = backColor;
    public Color lightningColor = auraColor;

    protected float lightningTimer = 0f;

    public TeslaBulletType(float speed, float damage) {
        super(speed, damage);
        this.width = 8f;
        this.height = 8f;
        this.shrinkY = 0f;
        this.sprite = "circle-bullet";
        this.auraColor = backColor;
        this.lightningColor = auraColor;
    }

    @Override
    public void init(Bullet b) {
        super.init(b);
        lightningTimer = 0f;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

        if (lightningChance <= 0f) return;

        lightningTimer += Time.delta;
        if (lightningTimer >= lightningInterval) {
            lightningTimer = 0f;
            if (Mathf.chance(lightningChance)) {
                spawnLightning(b);
            }
        }
    }

    protected void spawnLightning(Bullet b) {
        float angle = Mathf.random(360f);
        float endX = b.x + Mathf.cosDeg(angle) * auraRadius;
        float endY = b.y + Mathf.sinDeg(angle) * auraRadius;
        Lightning.create(b, lightningColor, lightningDamage < 0 ? damage : lightningDamage,
                b.x, b.y, angle, (int) auraRadius);
        if (lightningShootSound != null) {
            lightningShootSound.at(b.x, b.y, 1f, lightningSoundVolume);
        }
        int steps = Math.max(1, (int) (auraRadius / 4f));
        for (int i = 1; i <= steps; i++) {
            float t = i / (float) steps;
            float px = Mathf.lerp(b.x, endX, t);
            float py = Mathf.lerp(b.y, endY, t);
            Damage.damage(b.team, px, py, lightningWidth,
                    lightningDamage < 0 ? damage : lightningDamage);
            if (status != StatusEffect.none) {
                mindustry.entities.Units.nearby(b.team, px, py, lightningWidth, unit -> {
                    if (unit != null && unit.isValid()) {
                        unit.apply(status, statusDuration);
                    }
                });
            }
        }
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        Draw.color(auraColor);
        Draw.alpha(auraAlpha);
        Fill.circle(b.x, b.y, auraRadius);
      
        Draw.color(auraColor);
        Draw.alpha(1f);
        Lines.stroke(auraStroke);
        Lines.circle(b.x, b.y, auraRadius);
      
        Draw.reset();
    }
}
