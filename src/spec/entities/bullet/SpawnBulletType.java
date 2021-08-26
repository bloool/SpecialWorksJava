package spec.entities.bullet;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.entities.bullet.*;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;

//similar to frag bullets but only creates the bullet if kills the target

public class SpawnBulletType extends BasicBulletType{
    public SpawnBulletType(float speed, float damage) {
        super(speed, damage);
    }

    public @Nullable BulletType spawnBullet = null;

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        super.hitEntity(b, entity, health);
        if(entity instanceof Unit u) {
            if(health <= 0 || u.dead() && spawnBullet != null){
                float len = Mathf.random(1f, 7f);
                float a = b.rotation() + Mathf.range(fragCone/2) + fragAngle;
                spawnBullet.create(b, b.x + Angles.trnsx(a, len), b.y + Angles.trnsy(a, len), a, Mathf.random(fragVelocityMin, fragVelocityMax), Mathf.random(fragLifeMin, fragLifeMax));
            }
        }
    }
}