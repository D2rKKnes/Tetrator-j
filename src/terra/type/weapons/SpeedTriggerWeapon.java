package terra.type.weapons;

import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.type.Weapon;
import mindustry.type.Weapon.WeaponMount;

public class SpeedTriggerWeapon extends Weapon {
    public float speedThreshold;
    public float requiredTime;
    protected float speedTimer = 0f;

    public SpeedTriggerWeapon(String name, float threshold, float time) {
        super(name);
        this.speedThreshold = threshold;
        this.requiredTime = time;
        this.controllable = false;
        this.rotate = false;
    }

    @Override
    public void update(Unit unit, WeaponMount mount) {
        super.update(unit, mount);

        float velLen = unit.isRemote() ? unit.vel.len() : unit.deltaLen() / Time.delta;
        float maxSpeed = unit.type.speed;
        float speedFrac = maxSpeed > 0 ? velLen / maxSpeed : 0f;

        if (speedFrac >= speedThreshold) {
            speedTimer += Time.delta;
        } else {
            speedTimer = 0f;
        }

        boolean shouldShoot = speedTimer >= requiredTime && mount.target != null && unit.canShoot();
        mount.shoot = shouldShoot;
    }
                       }
