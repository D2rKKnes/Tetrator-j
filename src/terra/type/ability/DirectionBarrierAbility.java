package terra.type.ability;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;

public class DirectionBarrierAbility extends Ability{
    protected final float shieldWidth = 7f;
    protected final float blinkTime = 5f;

    public BulletType shrapnel;
    public boolean shrapnelSpawn = false;
    public TextureRegion shieldRegion;
    public String shieldSuffix = "-barrier";
    public float layerOffset = 1f;
    public Sound breakSound = Sounds.shieldBreakSmall;
    public Sound hitSound = Sounds.shieldHit;
    public float hitSoundVolume = 0.08f;

    public int shields;
    public float[] shieldAngles;
    public float[] healths;
    public float[] hitTimes;
    public boolean[] available;
    public float maxHealth;
    public float armor;
    public float disableRegen;
    public float shieldRegen;
    public float distanceRadius;
    public float shieldSize;
    public float shieldSpeed;
    public Interval timer = new Interval();

    public float explosiveReflectDamageMultiplier = 0.7f;
    public float explosiveDamageThreshold = 90f;
    public float healthBarOffset = 4f;
    public Color healthBarColor = Color.white;

    public DirectionBarrierAbility(int shields, float speed, float size, float health, float armor, float regen, float disableRegen, float distance, Color color){
        shieldSpeed = speed;
        shieldSize = size;
        maxHealth = health;
        shieldRegen = regen;
        distanceRadius = distance;
        healthBarColor = color;
        shieldAngles = new float[shields];
        healths = new float[shields];
        hitTimes = new float[shields];
        available = new boolean[shields];
        this.disableRegen = disableRegen;
        this.shields = shields;
        this.armor = armor;

        for(int i = 0; i < shields; i++){
            shieldAngles[i] = 0f;
            hitTimes[i] = 0f;
            healths[i] = health;
            available[i] = true;
        }
    }
    public DirectionBarrierAbility(int shields, float speed, float size, float health, float armor, float regen, float disableRegen, float distance, Color color, BulletType bullet){
        this(shields, speed, size, health, armor, regen, disableRegen, distance, color);
        shrapnel = bullet;
        shrapnelSpawn = true;
    }

    @Override
    public void addStats(Table t){
        super.addStats(t);
        t.add(abilityStat("barriercount", shields));
        t.row(); 
        t.add(abilityStat("barrierhealth", Strings.autoFixed(maxHealth, 2)));
        t.row();
        t.add(abilityStat("barrierarmor", Strings.autoFixed(armor, 2)));
        t.row();
        t.add(abilityStat("regen", Strings.autoFixed(shieldRegen * 60f, 2)));
        t.row();
        t.add(abilityStat("barrierdownregen", Strings.autoFixed(disableRegen * 60f, 2)));
    }

    @Override
    public Ability copy(){
        DirectionBarrierAbility instance = new DirectionBarrierAbility(shields, shieldSpeed, shieldSize, maxHealth, armor, shieldRegen, disableRegen, distanceRadius, healthBarColor);
        instance.explosiveReflectDamageMultiplier = explosiveReflectDamageMultiplier;
        instance.explosiveDamageThreshold = explosiveDamageThreshold;
        instance.healthBarOffset = healthBarOffset;
        instance.healthBarColor = healthBarColor;
        instance.shrapnel = shrapnel;
        instance.shrapnelSpawn = shrapnelSpawn;
        instance.shieldSuffix = shieldSuffix;
        return instance;
    }

    protected static float getBulletDamage(BulletType type){
        return type.damage + type.splashDamage + (Math.max(type.lightningDamage / 2f, 0f) * type.lightning * type.lightningLength);
    }

    protected void updateShields(Unit unit){
        Tmp.r1.setCentered(unit.x, unit.y, shieldSize);
        Seq<ShieldNode> nodes = new Seq<>();
        for(int i = 0; i < shields; i++){
            Tmp.v1.trns(shieldAngles[i], distanceRadius - (shieldWidth / 2f));
            Tmp.v1.add(unit);
            Tmp.v2.trns(shieldAngles[i] + 90f, (shieldSize / 2f) - (shieldWidth / 2f));

            ShieldNode ts = new ShieldNode();
            ts.id = i;
            for(int s : Mathf.signs){
                ts.getNodes(s).set(Tmp.v1.x + (Tmp.v2.x * s), Tmp.v1.y + (Tmp.v2.y * s));
                Tmp.r2.setCentered(Tmp.v1.x + (Tmp.v2.x * s), Tmp.v1.y + (Tmp.v2.y * s), shieldSize / 2f);
                Tmp.r1.merge(Tmp.r2);
            }
            nodes.add(ts);
        }
        //if(timer.get(1.5f)){
        Groups.bullet.intersect(Tmp.r1.x, Tmp.r1.y, Tmp.r1.width, Tmp.r1.height, b -> {
            if(b.team != unit.team && b.type.absorbable && !(b.type instanceof ContinuousLaserBulletType || b.type instanceof LaserBulletType) && !b.type.scaleLife && b.vel().len() > 0.1f){
                b.hitbox(Tmp.r2);
                Tmp.r3.set(Tmp.r2).grow(shieldWidth).move(b.vel.x / 2f, b.vel.y / 2f);
                Tmp.r2.grow(shieldWidth);
                nodes.each(n -> {
                    if(!available[n.id]) return;

                    if(Geometry.raycastRect(n.nodeA.x, n.nodeA.y, n.nodeB.x, n.nodeB.y, Tmp.r2) != null || Geometry.raycastRect(n.nodeA.x, n.nodeA.y, n.nodeB.x, n.nodeB.y, Tmp.r3) != null){
                        float rawDamage = getBulletDamage(b.type) * (b.damage() / (b.type.damage * b.damageMultiplier()));
                        float d = b.type.pierceArmor ? rawDamage : Damage.applyArmor(rawDamage, armor * b.type.armorMultiplier);
                        healths[n.id] -= d;
                        if(b.type.reflectable){
                            b.damage(b.damage() / 1.5f);
                            float angC = (((shieldAngles[n.id] + 90f) * 2f) - b.rotation()) + Mathf.range(15f);
        
                            if(explosiveReflectDamageMultiplier > 0f && d >= explosiveDamageThreshold && shrapnelSpawn){
                                for(int i = 0; i < 3; i++){
                                    float off = (i * 20f - (3 - 1) * 20f / 2f);
                                    shrapnel.create(unit, unit.team, b.x, b.y, angC + off, d * explosiveReflectDamageMultiplier, 1f, 1f, null);
                                }
                            }
                            hitTimes[n.id] = blinkTime;
                            b.team(unit.team());
                            b.rotation(angC);
                        }else{
                            b.absorb();
                        }
                        if(healths[n.id] < 0){
                            available[n.id] = false;
                            breakSound.at(b.x, b.y);
                        }else{
                            hitSound.at(b.x, b.y, 1f + Mathf.range(0.1f), hitSoundVolume);
                        }
                    }
                });
            }
        });
        //}
        for(int i = 0; i < shields; i++){
            if(available[i]){
                healths[i] = Math.min(healths[i] + (shieldRegen * Time.delta), maxHealth);
            }else{
                if(Mathf.chanceDelta(0.32 * (1f - Mathf.clamp(healths[i] / maxHealth)))){
                    Tmp.v1.trns(shieldAngles[i], distanceRadius);
                    Tmp.v1.add(unit);
                    Tmp.v2.trns(shieldAngles[i] + 90, Mathf.range(shieldSize / 2f), Mathf.range(2f));
                    Tmp.v1.add(Tmp.v2);
                    Fx.smoke.at(Tmp.v1.x, Tmp.v1.y);
                }
                healths[i] = Math.min(healths[i] + (disableRegen * Time.delta), maxHealth);
                if(healths[i] >= maxHealth){
                    available[i] = true;
                    hitTimes[i] = blinkTime;
                }
            }
        }
    }

    @Override
    public void update(Unit unit){
        if(unit.isShooting()){
            float size = (shieldSize / distanceRadius) * Mathf.radDeg; //((shieldSize * Mathf.PI2) / Mathf.sqrt(distanceRadius / 1.5f));
            for(int i = 0; i < shields; i++){
                float ang = Mathf.mod((i * size - (shields - 1f) * size / 2f) + unit.rotation, 360f);
                shieldAngles[i] = Mathf.slerpDelta(shieldAngles[i], ang, shieldSpeed);
                hitTimes[i] = Math.max(hitTimes[i] - Time.delta, 0f);
            }
        }else{
            float offset = (360f / shields) / 2f;
            for(int i = 0; i < shields; i++){
                float ang = Mathf.mod(((i * 360f / shields) + offset) + unit.rotation + 180f, 360f);
                shieldAngles[i] = Mathf.slerpDelta(shieldAngles[i], ang, shieldSpeed);
                hitTimes[i] = Math.max(hitTimes[i] - Time.delta, 0f);
            }
        }
        updateShields(unit);
    }

    @Override
    public void draw(Unit unit){
        if(shieldRegion == null){
            shieldRegion = Core.atlas.find(unit.type.name + shieldSuffix, unit.type.region);
        }

        float z = Draw.z();
        float size = (Math.max(shieldRegion.width, shieldRegion.height) * Draw.scl) * 1.3f;
        Lines.stroke(1.5f);
        for(int i = 0; i < shields; i++){
            Draw.z(z + layerOffset);
            Tmp.v3.trns(shieldAngles[i], distanceRadius);
            Tmp.v3.add(unit);
            float offset = available[i] ? 2f : 1.5f;
            Draw.mixcol(Color.white, hitTimes[i] / blinkTime);
            Draw.color(Color.white, Color.black, (1f - (Mathf.clamp(healths[i] / maxHealth))) / offset);
            Draw.rect(shieldRegion, Tmp.v3.x, Tmp.v3.y, shieldAngles[i]);

            if(available[i]){
                Tmp.v3.trns(shieldAngles[i], distanceRadius + healthBarOffset);
                Tmp.v3.add(unit);
                Draw.color(healthBarColor);
                Lines.lineAngleCenter(Tmp.v3.x, Tmp.v3.y, shieldAngles[i] + 90f, Mathf.clamp(healths[i] / maxHealth) * shieldSize);
            }

            Draw.z(Math.min(Layer.darkness, z - 1f));
            Draw.mixcol();
            Draw.color(Pal.shadow);
            Draw.rect(unit.type.softShadowRegion, Tmp.v3.x, Tmp.v3.y, size, size);

            Draw.z(z + layerOffset - 0.001f);
            float engScl = shieldSize / 6f;
            float liveScl = (engScl - (engScl / 4f)) + Mathf.absin(Time.time, 2f, engScl / 4f);
            Tmp.v3.trns(shieldAngles[i], distanceRadius - engScl);
            Tmp.v3.add(unit);
            Draw.color(unit.team.color);
            Fill.circle(Tmp.v3.x, Tmp.v3.y, liveScl);
            Draw.color(Color.white);
            Fill.circle(Tmp.v3.x, Tmp.v3.y, liveScl / 2f);
            Draw.z(z);
        }
        Draw.reset();
    }

    public static class ShieldNode{
        public Vec2 nodeA = new Vec2();
        public Vec2 nodeB = new Vec2();
        public int id;

        public ShieldNode(){
        }

        public Vec2 getNodes(int sign){
            return sign <= 0 ? nodeA : nodeB;
        }
    }
}
