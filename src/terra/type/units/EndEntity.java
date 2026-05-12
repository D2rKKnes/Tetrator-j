package terra.type.units;

import arc.Core;
import arc.graphics.g2d.*;
import arc.graphics.*;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.entities.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.Vars;
import terra.content.*;
import terra.type.units.*;

import static mindustry.Vars.content;

public class EndEntity extends UnitEntity {
    public static final float MAX_DAMAGE_PER_SEC = 30000f;
    public static final float RECENT_DAMAGE_RESUME = MAX_DAMAGE_PER_SEC / 60f;
    public static float maxOnceDamage = 3000f;
    public float recentDamage = MAX_DAMAGE_PER_SEC;

    private static final float REINFORCEMENTS_SPACING = Time.toMinutes * 0.75f;
    private static final int SPAWN_COUNT = 4;
    private static final int SPAWN_SECOND_COUNT = 2;
    private static final float SPAWN_RADIUS_FACTOR = 1.8f;
    private static final int ARROW_COUNT = 5;
    private static final float ARROW_MAX_SIZE = 0.25f;
    private static final float ARROW_ROTATION_SPEED = 0.15f;
    private static final float ARROW_WOBBLE_SPEED = 0.02f;
    private static final float ARROW_RADIUS_FACTOR = 2f;

    private boolean shockOne = true;
    private boolean shockTwo = true;

    private float reload = REINFORCEMENTS_SPACING;

    @Override
    public int classId() {
        return EntityRegister.getID(EndEntity.class);
    }

    @Override
    public void update() {
        super.update();

        recentDamage += RECENT_DAMAGE_RESUME * Time.delta;
        if (recentDamage >= MAX_DAMAGE_PER_SEC) {
            recentDamage = MAX_DAMAGE_PER_SEC;
        }

        reload += Time.delta;
        if (reload > REINFORCEMENTS_SPACING) reload = REINFORCEMENTS_SPACING;

        if (healthf() < 0.75f && reload >= REINFORCEMENTS_SPACING) {
            reload = 0f;
            for (int i = 0; i < SPAWN_COUNT; i++) {
                float angleOffset = (360f / SPAWN_COUNT * i) - ((360f / SPAWN_COUNT) / 2);
                float spawnAngle = rotation + angleOffset;
                float distance = hitSize * SPAWN_RADIUS_FACTOR;
                Tmp.v1.trns(spawnAngle, distance);
                float spawnX = x + Tmp.v1.x;
                float spawnY = y + Tmp.v1.y;
                float duration = TerraUnitTypes.endSpawn.hitSize * 9f;
                if (TerraUnitTypes.endSpawn != null) {
                    spawnUnit(TerraUnitTypes.endSpawn, spawnX, spawnY, rotation, duration);
                }
            }
            for (int i = 0; i < SPAWN_SECOND_COUNT; i++) {
                float angleOffset2 = (360f / SPAWN_SECOND_COUNT * i) - ((360f / SPAWN_SECOND_COUNT) / 2);
                float spawnAngle2 = rotation + angleOffset2;
                float distance2 = hitSize * SPAWN_RADIUS_FACTOR;
                Tmp.v1.trns(spawnAngle2, distance2);
                float spawnX2 = x + Tmp.v1.x;
                float spawnY2 = y + Tmp.v1.y;
                float duration2 = TerraUnitTypes.endGuard.hitSize * 9f;
                if (TerraUnitTypes.endGuard != null) {
                    spawnUnit(TerraUnitTypes.endGuard, spawnX2, spawnY2, rotation, duration2);
                }
            }
            apply(TerraStatusEffects.warpPower, hitSize * 4f);
            shockwave(20f, 2000f, hitSize * 1.8f, StatusEffects.unmoving, 300f);
            shockOne = true;
            shockTwo = true;
        }
        
        private float reloadOne = REINFORCEMENTS_SPACING / 3;
        private float reloadTwo = reloadOne * 2;
        if (healthf() < 0.75f && reload >= reloadOne && shockOne == true) {
            shockwave(20f, 2000f, hitSize * 1.8f, StatusEffects.unmoving, 300f);
            shockOne = false;
        }
        if (healthf() < 0.75f && reload >= reloadTwo && shockTwo == true) {
            shockwave(20f, 2000f, hitSize * 1.8f, StatusEffects.unmoving, 300f);
            shockTwo = false;
        }
    }

    private void spawnUnit(UnitType type, float spawnX, float spawnY, float rot, float statusDuration) {
        Effect.shake(type.hitSize / 10f, type.hitSize / 8f, spawnX, spawnY);
        TerraFx.jumpTrail.at(spawnX, spawnY, rot, team.color, type);
        TerraSounds.jumpIn.at(spawnX, spawnY, 1, 2);
        Unit unit = type.create(team);
        unit.set(spawnX, spawnY);
        unit.rotation = rot;
        unit.add();
        unit.apply(TerraStatusEffects.warped, statusDuration);
    }

    private void shockwave(float knockback, float damage, float radius, StatusEffect effect, float effectDuration) {
    Units.nearbyEnemies(team, x, y, radius, other -> {
        other.damage(damage);
        Tmp.v1.set(other.x - x, other.y - y).nor().scl(knockback);
        other.velocity.add(Tmp.v1);
        other.apply(effect, effectDuration);
    });
    new MultiEffect(TerraFx.circleOut, TerraFx.hitSpark(Color.valueOf("e13131"), 55, 40, (radius) + 30, 3, 8), TerraFx.crossBlastArrow45, TerraFx.smoothColorCircle(Color.valueOf("e13131"), radius, 60, 0.6f)).at(x, y);
    }
    
    @Override
    public void draw() {
        super.draw();

        float progress = reload / REINFORCEMENTS_SPACING;
        if (progress <= 0.005f) return;

        Draw.z(Layer.effect);
        Lines.stroke(4f);
        Draw.color(team.color);

        float rad = hitSize() * 1.8f;
        float p = Mathf.clamp(progress);
        int sides = 11 + (int)(rad * 0.4f);
        float space = 360f / sides;
        float hstep = Lines.getStroke() / 2f / Mathf.cosDeg(space / 2f);
        float r1 = rad - hstep;
        float r2 = rad + hstep;
        float angle = 0f;

        int max = (int)(sides * p);
        for (int i = 0; i < max; i++) {
            float a = space * i + angle;
            float cos = Mathf.cosDeg(a);
            float sin = Mathf.sinDeg(a);
            float cos2 = Mathf.cosDeg(a + space);
            float sin2 = Mathf.sinDeg(a + space);
            Fill.quad(
                x + r1 * cos, y + r1 * sin,
                x + r1 * cos2, y + r1 * sin2,
                x + r2 * cos2, y + r2 * sin2,
                x + r2 * cos, y + r2 * sin
            );
        }
        if (Math.abs(max - sides * p) > 0.001f) {
            float a = space * max + angle;
            float cos = Mathf.cosDeg(a);
            float sin = Mathf.sinDeg(a);
            float cos2 = Mathf.cosDeg(a + space);
            float sin2 = Mathf.sinDeg(a + space);
            float f = sides * p - max;
            float len = 2 * rad * Mathf.sinDeg(space / 2);
            Tmp.v3.trns(a, 0, len * (f - 1));
            Fill.quad(
                x + r1 * cos, y + r1 * sin,
                x + r1 * cos2 + Tmp.v3.x, y + r1 * sin2 + Tmp.v3.y,
                x + r2 * cos2 + Tmp.v3.x, y + r2 * sin2 + Tmp.v3.y,
                x + r2 * cos, y + r2 * sin
            );
        }

        if (progress > 0.9f && ARROW_MAX_SIZE > 0) {
            float arrowScale = Interp.pow2Out.apply((progress - 0.9f) / 0.1f) * ARROW_MAX_SIZE;
            if (arrowScale <= 0.01f) return;

            TextureRegion arrowSprite = Core.atlas.find("logic-node");
            if (!arrowSprite.found()) return;

            float baseRadius = hitSize() * ARROW_RADIUS_FACTOR;
            float time = Time.time;

            for (int i = 0; i < ARROW_COUNT; i++) {
                float aangle = time * ARROW_ROTATION_SPEED + (360f / ARROW_COUNT) * i;
                float wobble = Mathf.sin(time * ARROW_WOBBLE_SPEED + aangle) * (arrowScale * 0f);
                float arad = baseRadius + wobble;
                float xx = x + Mathf.cosDeg(aangle) * arad;
                float yy = y + Mathf.sinDeg(aangle) * arad;
                float rot = aangle + 180f;

                Draw.color(team.color);
                Draw.rect(arrowSprite, xx, yy,
                    arrowSprite.width * arrowScale / Draw.scl,
                    arrowSprite.height * arrowScale / Draw.scl,
                    rot);
            }
            Draw.reset();
        }
        Draw.reset();
    }

    public void rawDamage(float amount) {
        boolean hadShields = this.shield > 1.0E-4F;
        if (hadShields) {
            this.shieldAlpha = 1.0F;
        }

        amount = Math.min(amount, maxOnceDamage);

        float shieldDamage = Math.min(Math.max(this.shield, 0.0F), amount);
        this.shield -= shieldDamage;
        this.hitTime = 1.0F;

        amount -= shieldDamage;
        amount = Math.min(recentDamage / healthMultiplier, amount);
        recentDamage -= amount * 1.5f * healthMultiplier;

        if (amount > 0.0F && this.type.killable) {
            this.health -= amount;
            if (this.health <= 0.0F && !this.dead) {
                this.kill();
            }

            if (hadShields && this.shield <= 1.0E-4F) {
                Fx.unitShieldBreak.at(this.x, this.y, 0.0F, this.team.color, this);
            }
        }

    }

    @Override
    public void read(arc.util.io.Reads read) {
        reload = read.f();
        recentDamage = read.f();
        super.read(read);
    }

    @Override
    public void write(arc.util.io.Writes write) {
        write.f(reload);
        write.f(recentDamage);
        super.write(write);
    }

    @Override
    public void readSync(arc.util.io.Reads read) {
        super.readSync(read);
        if (!isLocal()) {
            reload = read.f();
            recentDamage = read.f();
        } else {
            read.f();
            read.f();
        }
    }

    @Override
    public void writeSync(arc.util.io.Writes write) {
        super.writeSync(write);
        write.f(reload);
        write.f(recentDamage);
    }
}
