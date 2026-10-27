package terra.type.bullet;

import mindustry.content.Fx;
import mindustry.gen.Bullet;
import mindustry.entities.bullet.*;

//Original code from Exogenesis (java)
public class EffectBulletType extends BulletType{
	public EffectBulletType(float lifetime){
		super();
		this.lifetime = lifetime;
		hittable = false;
		despawnEffect = hitEffect = shootEffect = smokeEffect = trailEffect = Fx.none;
		absorbable = collides = collidesAir = collidesGround = collidesTeam = collidesTiles = collideFloor = collideTerrain = false;
		hitSize = 0;
		speed = 0.0001f;
		drawSize = 120f;
    }

	@Override public void draw(Bullet b){}
	@Override public void drawLight(Bullet b){}
}













