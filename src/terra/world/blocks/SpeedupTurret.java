package terra.world.blocks;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class SpeedupTurret extends ItemTurret{
    public float windupSpeed = 0.00625f, windDownSpeed = 0.0125f, minFiringSpeed = 3f, logicSpeedScl = 0.25f, maxSpeed = 30f;

    public SpeedupTurret(String name){
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.reload);
        float minValue = minFiringSpeed / 90f * 60f * shoot.shots;
        float maxValue = maxSpeed / 90f * 60f * shoot.shots;
        stats.add(Stat.reload, Strings.fixed(minValue, 2) + " - " + Strings.fixed(maxValue, 2) + StatUnit.perSecond.localized());
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("gun-speed", (SpeedupTurretBuild entity) -> new Bar(
            () -> Core.bundle.format("bar.gun-speed", Strings.fixed(entity.speedf() * 100f, 2)),
            entity::barColor,
            entity::speedf
        ));
    }

    public class SpeedupTurretBuild extends ItemTurretBuild{
        protected float spinSpeed, spin;

        public Color barColor(){
            return spinSpeed > minFiringSpeed ? team.color : team.palette[2];
        }

        @Override
        public void updateTile(){
            boolean notShooting = !hasAmmo() || !isShooting() || !isActive();
            if(notShooting){
                spinSpeed = Mathf.approachDelta(spinSpeed, 0, windDownSpeed);
            }

            if(spinSpeed > getMaxSpeed()){
                spinSpeed = Mathf.approachDelta(spinSpeed, getMaxSpeed(), windDownSpeed);
            }
            
            super.updateTile();
        }

        @Override
        protected void updateShooting(){
            if(!hasAmmo()) return;

            spinSpeed = Mathf.approachDelta(spinSpeed, getMaxSpeed(), windupSpeed * peekAmmo().reloadMultiplier * timeScale);

            if(reloadCounter >= 90 && spinSpeed > minFiringSpeed){
                BulletType type = peekAmmo();
                shoot(type);
                reloadCounter = spin % 90;
            }
        }

        @Override
        protected void updateReload(){
            boolean shooting = hasAmmo() && isShooting() && isActive();
            float multiplier = hasAmmo() ? peekAmmo().reloadMultiplier : 1f;
            float add = spinSpeed * multiplier * Time.delta;
            if(shooting && coolant != null && coolant.efficiency(this) > 0 && efficiency > 0){
                float capacity = coolant instanceof ConsumeLiquidFilter filter ? filter.getConsumed(this).heatCapacity : 1f;
                coolant.update(this);
                add += coolant.amount * edelta() * capacity * coolantMultiplier;

                if(Mathf.chance(0.06 * coolant.amount)){
                    coolEffect.at(x + Mathf.range(size * tilesize / 2f), y + Mathf.range(size * tilesize / 2f));
                }
            }
            spin += add;
            reloadCounter += add;
        }

        protected float getMaxSpeed(){
            return maxSpeed * (!isControlled() && logicControlled() && logicShooting ? logicSpeedScl : 1f);
        }

        protected float speedf(){
            return spinSpeed / maxSpeed;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(spinSpeed);
            write.f(spin % 360f);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            if(revision >= 2){
                spinSpeed = read.f();
                if(revision >= 3){
                    spin = read.f();
                }
            }
        }

        @Override
        public byte version(){
            return 3;
        }
    }
}
