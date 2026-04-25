package terra.world.blocks;

import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.*;

public class AdvancedWall extends Wall {
    public static final Stat hitChance = new Stat("hitChance", StatCat.function);
    public static final Stat hitAmount = new Stat("hitAmount", StatCat.function);
    
    public float hitBulletSpawnChance = 0f;
    public BulletType hitBullet = new BulletType();
    public int hitBulletAmount = 4;
    public int hitBulletAmountRand = 1;
    public float hitBulletLifeRandScl = 0.2f;
    public float hitBulletSpeedRandScl = 0.2f;
    public float hitBulletSpread = 360f;
    public float hitBulletAngleOffset = 0f;
    public boolean hitBulletOnDeath = true;
    public Effect hitBulletEffect = Fx.none;
    public arc.audio.Sound hitBulletSound = Sounds.none;
    public float hitBulletSoundPitchMin = 0.8f;
    public float hitBulletSoundPitchMax = 1f;
    public float hitBulletSoundVolume = 1f;

    public boolean autoRegeneration = false;
    public Effect regenEffect = Fx.electrified;
    public float regenEffectChance = 0.04f;
    public float regenAmount = 0.01f / 60f;
    public boolean regenPercent = true;
    public float regenStartDelay = 0f;
    public boolean regenDamageStop = false;

    public AdvancedWall(String name) {
        super(name);
        update = true;
    }

    @Override
    public void setStats() {
        super.setStats();
        
        if (hitBulletSpawnChance > 0) {
            stats.add(Stat.bullet, StatValues.ammo(ObjectMap.of(Items.metaglass, hitBullet)));
            stats.add(hitChance, hitBulletSpawnChance * 100f, StatUnit.percent);
            stats.add(hitAmount, (hitBulletAmount - hitBulletAmountRand) + " - " + (hitBulletAmount + hitBulletAmountRand));
        }

        if (autoRegeneration && regenAmount > 0) {
            float displayRegen = regenAmount * 60f * 100;
            string sign = regenPercent ? "%" : Core.bundle.get("stat.health");
            stats.add(Stat.repairSpeed, displayRegen + sign, StatUnit.perSecond);
        }
    }

    public class AdvancedWallBuild extends WallBuild {
        public float lastDamageTime = -9999f;

        @Override
        public void updateTile() {
            super.updateTile();

            if (autoRegeneration && regenAmount > 0 && health < maxHealth) {
                if (Time.time >= lastDamageTime + regenStartDelay) {
                    float add = regenPercent ? maxHealth * regenAmount : regenAmount;
                    heal(add * delta());

                    if (Mathf.chance(regenEffectChance * delta())) {
                        float offset = (size * mindustry.Vars.tilesize) / 2f;
                        regenEffect.at(x + Mathf.range(offset), y + Mathf.range(offset), 0, team.color, this);
                    }
                }
            }
        }

        @Override
        public boolean collision(Bullet bullet) {
            boolean result = super.collision(bullet);

            if (Mathf.chance(hitBulletSpawnChance)) {
                spawnHitBullets();
            }

            if (regenDamageStop) {
                lastDamageTime = Time.time;
            }
            return result;
        }

        @Override
        public void onDestroyed() {
            if (hitBulletOnDeath) {
                spawnHitBullets();
            }
            super.onDestroyed();
        }

        public void spawnHitBullets() {
            hitBulletSound.at(x, y, Mathf.random(hitBulletSoundPitchMin, hitBulletSoundPitchMax), hitBulletSoundVolume);
            hitBulletEffect.at(x, y);

            int amount = hitBulletAmount + Mathf.random(-hitBulletAmountRand, hitBulletAmountRand);
            for (int i = 0; i < amount; i++) {
                float angle = (i * hitBulletSpread / Math.max(amount, 1)) - (hitBulletSpread / 2f) + hitBulletAngleOffset + Mathf.range(hitBulletSpread / Math.max(amount, 1) / 2f);
                hitBullet.create(this, team, x, y, angle, 
                    1f + Mathf.range(hitBulletSpeedRandScl), 
                    1f + Mathf.range(hitBulletLifeRandScl));
            }
        }
    }
}
