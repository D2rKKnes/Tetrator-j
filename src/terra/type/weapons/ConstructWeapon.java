package progressed.type.weapons;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.util.*;
import arc.util.pooling.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.type.*;
import terra.graphics.shaders.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Mathf.*;
import static mindustry.Vars.*;
import static terra.graphics.shaders.TerraShaders.*;

//Original code from uhh prog-mats
/** @author MEEP */
public class ConstructWeapon extends Weapon{
    public ConstructWeapon(String name){
        super(name);
        mountType = ConstructMount::new;

        rotate = true;
        shootX = shootY = 0f;
        inaccuracy = velocityRnd = 0;
    }

    public static void vertConstruct(float x, float y, TextureRegion region, float rotation, float progress, float alpha, float time){
        vertConstruct(x, y, region, Pal.accent, rotation, progress, alpha, time);
    }

    public static void vertConstruct(float x, float y, TextureRegion region, Color color, float rotation, float progress, float alpha, float time){
        vertBuild.region = region;
        vertBuild.progress = progress;
        vertBuild.color.set(color);
        vertBuild.color.a = alpha;
        vertBuild.time = -time / 20f;

        shader(vertBuild);
        rect(region, x, y, rotation);
        shader();

        reset();
    }

    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);

        ConstructMount rMount = (ConstructMount)mount;
        if(loadf(mount) < 0.99f) rMount.total += Time.delta * unit.reloadMultiplier;
        rMount.warmup = Mathf.lerpDelta(rMount.warmup, Mathf.num(mount.reload / reload > 0.001), 0.15f);
    }

    public void drawOutline(Unit unit, WeaponMount mount){
        ConstructMount rMount = (ConstructMount)mount;

        float
            rotation = unit.rotation - 90,
            weaponRotation  = rotation + (rotate ? mount.rotation : 0),
            wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil),
            wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);

        if(rMount.warmup > 0.01f || loadf(mount) > 0.01f){
            if(outlineRegion.found() && !top){
                Draw.draw(Draw.z(), () -> {
                    vertConstruct(wx, wy, outlineRegion, weaponRotation, loadf(mount), rMount.warmup, rMount.total);
                });
            }
        }else{
            if(outlineRegion.found()){
                Draw.rect(outlineRegion,
                    wx, wy,
                    outlineRegion.width * Draw.scl * -Mathf.sign(flipSprite),
                    region.height * Draw.scl,
                    weaponRotation
                );
            }
        }
    }

    @Override
    public void draw(Unit unit, WeaponMount mount){
        ConstructMount rMount = (ConstructMount)mount;
        float z = Draw.z();
        Draw.z(z + layerOffset);

        float rotation = unit.rotation - 90,
            weaponRotation  = rotation + (rotate ? mount.rotation : 0),
            wx = unit.x + Angles.trnsx(rotation, x, y),
            wy = unit.y + Angles.trnsy(rotation, x, y);

        if(shadow > 0){
            Drawf.shadow(wx, wy, shadow, 1f - loadf(mount));
        }

        if(rMount.warmup > 0.01f || loadf(mount) > 0.01f){
            Draw.draw(Draw.z(), () -> {
                if(outlineRegion.found() && top){
                    vertConstruct(wx, wy, outlineRegion, weaponRotation, loadf(mount), rMount.warmup, rMount.total);
                }
                vertConstruct(wx, wy, region, weaponRotation, loadf(mount), rMount.warmup, rMount.total);
            });
        }else{
            if(outlineRegion.found() && top){
                Draw.rect(outlineRegion,
                    wx, wy,
                    outlineRegion.width * Draw.scl * -Mathf.sign(flipSprite),
                    region.height * Draw.scl,
                    weaponRotation
                );
            }

            Draw.rect(region,
                wx, wy,
                region.width * Draw.scl * -Mathf.sign(flipSprite),
                region.height * Draw.scl,
                weaponRotation
            );
        }

        Draw.z(z);
    }

    public float loadf(WeaponMount mount){
        return 1f - (mount.reload / reload);
    }

    public static class ConstructMount extends WeaponMount{
        float total, warmup;

        public ConstructMount(Weapon weapon){
            super(weapon);
        }
    }
}
