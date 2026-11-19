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
import terra.util.*;

public class TeslaBulletType extends BasicBulletType {
    //I recommend setting it to at least 2–3 times the size of the bullet itself.
    public float auraRadius = 24f;
    public float auraStroke = 2f;
    //How transparent inner part is
    public float auraAlpha = 0.2f;

    //If set to -1, lightnings will use bullet damage instead
    public float auraLightningDamage = -1f;
    //In ticks
    public float auraLightningInterval = 10f;
    //How wide lightnings will be
    public float auraLightningWidth = 6f;
    //Сhance that a single, individual lightning bolt will be created
    public float auraLightningChance = 1f;
    //Sound when lightnings was created
    public Sound auraLightningSound = Sounds.shootArc;
    public float auraLightningSoundVolume = 0.2f;
    public float auraLightningSoundPitchMin = 0.9f;
    public float auraLightningSoundPitchMax = 1.1f;
    //How "broken" lightnings will be
    public int auraLightningBrokenness = 12;
    //Number of lightnings. Set to 0 to disable lightning.
    public int auraLightnings = 2;

    //Color of aura. If not set, uses backColor of bullet instead
    public Color auraColor = backColor;
    //Color of lightnings. If not set, uses auraColor instead
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
            for (int i = 0; i < auraLightnings; i++) {
                if (Mathf.chance(auraLightningChance)) {
                    spawnLightning(b);
                }     
            }
        }
    }

    protected void spawnLightning(Bullet b) {
        float angle = Mathf.random(360f);
        float endX = b.x + Mathf.cosDeg(angle) * auraRadius;
        float endY = b.y + Mathf.sinDeg(angle) * auraRadius;
        float maxWidth = auraLightningWidth * 1.25f;
        Utils.lightningSingle(b.x, b.y, auraRadius, angle, maxWidth, auraLightningBrokenness, auraLightningColor);
        if (auraLightningSound != null) {
            auraLightningSound.at(b.x, b.y, Mathf.random(auraLightningSoundPitchMin, auraLightningSoundPitchMax), auraLightningSoundVolume);
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
