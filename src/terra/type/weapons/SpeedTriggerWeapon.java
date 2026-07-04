package terra.type.weapons;

import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.type.*;
import mindustry.entities.units.*;

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
        float velLen = unit.isRemote() ? unit.vel.len() : unit.deltaLen() / Time.delta;
        float maxSpeed = unit.type.speed;
        float speedFrac = maxSpeed > 0 ? velLen / maxSpeed : 0f;

        Float timer = timers.get(mount);
        if (timer == null) timer = 0f;

        if (speedFrac >= speedThreshold) {
            timer += Time.delta;
        } else {
            timer = 0f;
        }

        timers.put(mount, timer);

        this.alwaysShooting = timer >= requiredTime && unit.canShoot();

        super.update(unit, mount);
    }
}
