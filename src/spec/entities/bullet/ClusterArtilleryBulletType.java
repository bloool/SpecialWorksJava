package spec.entities.bullet;

import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import spec.libs.dynamic;

public class ClusterArtilleryBulletType extends ArtilleryBulletType {
    /** The amount of explosions */
    public int explosionAmount = 12;
    /** Delay between each explosion */
    public float explosionDelay = 2f;
    /** damage */
    public float explosionDamage = 40f;
    /** how far from the despawn point they can spawn*/
    public float explosionRange = 35;
    /** how big is the explosion splash damage radius*/
    public float explosionRadius = 30;
    /** the explosion effect */
    public Effect explosionEffect = Fx.massiveExplosion;

    public ClusterArtilleryBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
    }

    @Override
    public void despawned(Bullet b){
        float x = b.x;
        float y = b.y;
        for(int i = 0; i < explosionAmount; i++){
            Time.run(explosionDelay * i, () -> {
                float rx = x + Mathf.range(explosionRange);
                float ry = y + Mathf.range(explosionRange);
                dynamic.explosion(explosionDamage, explosionRadius, explosionEffect).create(b, rx, ry, 0);
            });
        }

        super.despawned(b);
    }
}