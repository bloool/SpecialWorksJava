package spec.entities.bullet;

import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import spec.libs.*;

public class ClusterBulletType extends BasicBulletType{
    /** The amount of explosions */
    public int explosionAmount = 12;
    /** Delay between each explosion */
    public float explosionDelay = 2f;
    /** damage */
    public float explosionDamage = 40f;
    /** how far from the despawn point they can spawn */
    public float explosionRange = 35;
    /** how big is the explosion splash damage radius */
    public float explosionRadius = 30;
    /** the explosion effect */
    public Effect explosionEffect = Fx.massiveExplosion;

    public ClusterBulletType(float speed, float damage){
        super(speed, damage);
        despawnHit = true;
        shootEffect = Fx.shootBig;
    }

    @Override
    public void hit(Bullet b, float x, float y){
        float x2 = b.x;
        float y2 = b.y;
        for(int i = 0; i < explosionAmount; i++){
            Time.run(explosionDelay * i, () -> {
                float rx = x2 + Mathf.range(explosionRange);
                float ry = y2 + Mathf.range(explosionRange);
                dynamic.explosion(explosionDamage, explosionRadius, explosionEffect).create(b, rx, ry, 0);
            });
        }
        super.hit(b, x, y);
    }
}