package terra.type;

import arc.Events;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Strings;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType.*;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.type.Item;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class AdvancedItem extends Item {
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
        
        Events.on(UnitUpdateEvent.class, e -> {
            if (e.unit.stack.item == this) updateUnit(e.unit, e.unit.stack.amount);
        });
        
        Events.on(UnitDestroyEvent.class, e -> {
            if (e.unit.stack.item == this) spawnBullet(e.unit, e.unit.stack.amount);
        });
    }

    @Override
    public void init() {
        if (threat == -1) threat = (explosiveness / 2f) + (flammability / 4f) + (radioactivity / 6f) + (charge / 8f);
        if (threatMul == -1) threatMul = Mathf.pow(threat * 2.1f, 2f);
    }

    @Override
    public void update(Building build, int amount) {
        if (damageContainer && Mathf.chance(getChance(damageChance, amount, damageChanceMul))) {
            applyDamage(build, amount);
        }
    }

    @Override
    public void onRemoved(Building build, int amount) {
        if (spawnBulletOnDestroy) spawnBullet(build, amount);
    }

    // Логика для юнитов
    public void updateUnit(Unit unit, int amount) {
        if (damageContainer && Mathf.chance(getChance(damageChance, amount, damageChanceMul))) {
            applyDamage(unit, amount);
        }
    }

    private float getChance(float base, int amount, boolean mul) {
        float c = base + (threat / 50f * amount);
        return mul ? c * threatMul : c;
    }

    private void applyDamage(mindustry.gen.Healthc entity, int amount) {
        float fDamage = damage + Mathf.range(damageRand);
        if (damagePercent) fDamage = (fDamage / 100f) * entity.maxHealth();
        
        entity.damage(fDamage);

        if (damageEffect != null) {
            float size = (entity instanceof Building b) ? b.block.size * 4f : ((Unit)entity).hitSize;
            damageEffect.at(entity.x() + Mathf.range(size), entity.y() + Mathf.range(size), color);
        }
    }

    private void spawnBullet(mindustry.gen.Positionc pos, int amount) {
        if (spawnBullet == null) return;
        if (Mathf.chance(getChance(spawnBulletChance, amount, spawnBulletChanceMul))) {
            float dmg = spawnBulletStatsScale ? threatMul : 1f;
            spawnBullet.create(null, (pos instanceof mindustry.gen.Teamc t) ? t.team() : mindustry.game.Team.derelict, pos.getX(), pos.getY(), Mathf.random(360f), dmg, 1f);
        }
    }

    @Override
    public void setStats() {
        super.setStats();
        if (showThreat && threat >= 0) {
            Color tCol = Color.white.cpy().lerp(Color.red, Mathf.clamp(threat));
            stats.add(Stat.health, "[#" + tCol.toString() + "]" + Strings.fixed(threat * 100f, 1) + "% (Threat)[]");
        }
        if (damageContainer && damage > 0) {
            stats.add(Stat.damage, (damagePercent ? damage + "%" : damage), StatUnit.perSecond);
        }
    }
}
