package terra.type;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.bullet.*;
import mindustry.game.EventType.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.meta.*;

public class AdvancedItem extends Item {
    public static final Stat statthreat = new Stat("statthreat", StatCat.function);

    public float threat = -1;
    public boolean showThreat = true;
    public float threatMul = -1;

    public boolean spawnBulletOnDestroy = false;
    public float spawnBulletChance = 0.01f;
    public boolean spawnBulletChanceMul = true;
    public BulletType spawnBullet;
    public boolean spawnBulletStatsScale = false;

    public boolean damageContainer = false;
    public float damageChance = 0.1f / 60f;
    public boolean damageChanceMul = false;
    public float damage = 1f;
    public float damageRand = 0.5f;
    public boolean damagePercent = true;
    public mindustry.entities.Effect damageEffect;

    public AdvancedItem(String name) {
        super(name);

        Events.run(Trigger.update, () -> {
            if(!damageContainer || Vars.state.isPaused()) return;

            Groups.unit.each(u -> u.stack.item == this, u -> {
                if(Mathf.chance(getChance(damageChance, u.stack.amount, damageChanceMul))){
                    applyDamage(u, u.hitSize);
                }
            });

            Groups.build.each(b.items.has(this), b -> {
                if(Mathf.chance(getChance(damageChance, b.items.get(this), damageChanceMul))){
                    applyDamage(b, b.block.size * 4f);
                }
            });
        });

        Events.on(UnitDestroyEvent.class, e -> {
            if(spawnBulletOnDestroy && e.unit.stack.item == this) spawnBullet(e.unit, e.unit.stack.amount);
        });
        
        Events.on(BlockDestroyEvent.class, e -> {
            if(spawnBulletOnDestroy && e.tile.build.items != null){
                int amount = e.tile.build.items.get(this);
                if(amount > 0) spawnBullet(e.tile.build, amount);
            }
        });
    }

    @Override
    public void init() {
        super.init();
        if (threat == -1) threat = (explosiveness / 2f) + (flammability / 4f) + (radioactivity / 6f) + (charge / 8f);
        if (threatMul == -1) threatMul = Mathf.pow(threat * 2.1f, 2f);
    }

    private float getChance(float base, int amount, boolean mul) {
        float c = base + ((threat / 50f * amount) / 60f);
        return mul ? c * threatMul : c;
    }

    private void applyDamage(Healthc entity, float hitSize) {
        float fDamage = damage + Mathf.range(damageRand);
        if (damagePercent) fDamage = (fDamage / 100f) * entity.maxHealth();
        
        entity.damage(fDamage);

        if (damageEffect != null) {
            Posc p = (Posc)entity;
            damageEffect.at(p.getX() + Mathf.range(hitSize), p.getY() + Mathf.range(hitSize), color);
        }
    }

    private void spawnBullet(Posc pos, int amount) {
        if (spawnBullet == null) return;
        if (Mathf.chance(getChance(spawnBulletChance, amount, spawnBulletChanceMul))) {
            float dmg = spawnBulletStatsScale ? threatMul : 1f;
            spawnBullet.create(null, Team.derelict, pos.getX(), pos.getY(), Mathf.random(360f), dmg, 1f);
        }
    }

    @Override
    public void setStats() {
        super.setStats();
        if (showThreat && threat >= 0) {
            Color tCol = Color.white.cpy().lerp(Color.red, Mathf.clamp(threat));
            stats.add(statthreat, "[#" + tCol.toString() + "]" + Strings.fixed(threat * 100f, 1) + "%[]");
        }
        if (damageContainer && damage > 0) {
            stats.add(Stat.damage, damage);
        }
    }
}
