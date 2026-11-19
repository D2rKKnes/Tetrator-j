package terra.type.bullet;

import arc.audio.*;
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

    public float auraLightningDamage = -1f;
    public float auraLightningInterval = 10f;
    public float auraLightningWidth = 5f;
    public float auraLightningChance = 1f;
    public Sound auraLightningShootSound = Sounds.shootArc;
    public float auraLightningSoundVolume = 0.2f;

    public Color auraColor = backColor;
    public Color auraLightningColor = auraColor;

    protected float auraLightningTimer = 0f;

    public TeslaBulletType(float speed, float damage) {
        super(speed, damage);
        this.width = 8f;
        this.height = 8f;
        this.shrinkY = 0f;
        this.sprite = "circle-bullet";
        this.auraColor = backColor;
        this.auraLightningColor = auraColor;
    }

    @Override
    public void init(Bullet b) {
        super.init(b);
        auraLightningTimer = 0f;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

        if (auraLightningChance <= 0f) return;

        auraLightningTimer += Time.delta;
        if (auraLightningTimer >= auraLightningInterval) {
            auraLightningTimer = 0f;
            if (Mathf.chance(auraLightningChance)) {
                spawnLightning(b);
            }
        }
    }

    protected void spawnLightning(Bullet b) {
        float angle = Mathf.random(360f);
        float endX = b.x + Mathf.cosDeg(angle) * auraRadius;
        float endY = b.y + Mathf.sinDeg(angle) * auraRadius;
        Lightning.create(b, auraLightningColor, auraLightningDamage < 0 ? damage : auraLightningDamage,
                b.x, b.y, angle, (int) auraRadius);
        if (auraLightningShootSound != null) {
            auraLightningShootSound.at(b.x, b.y, 1f, auraLightningSoundVolume);
        }
        int steps = Math.max(1, (int) (auraRadius / 4f));
        for (int i = 1; i <= steps; i++) {
            float t = i / (float) steps;
            float px = Mathf.lerp(b.x, endX, t);
            float py = Mathf.lerp(b.y, endY, t);
            Damage.damage(b.team, px, py, auraLightningWidth,
                    auraLightningDamage < 0 ? damage : auraLightningDamage);
            if (status != null) {
                mindustry.entities.Units.nearby(b.team, px, py, auraLightningWidth, unit -> {
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
