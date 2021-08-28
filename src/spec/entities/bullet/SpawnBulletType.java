package spec.entities.bullet;

import arc.math.*;
import arc.util.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

//similar to frag bullets but only creates the bullet if kills the target

public class SpawnBulletType extends BasicBulletType{
    public @Nullable
    BulletType spawnBullet = null;

    public SpawnBulletType(float speed, float damage){
        super(speed, damage);
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        super.hitEntity(b, entity, health);
        if(entity instanceof Unit u){
            if(health <= 0 || u.dead() && spawnBullet != null){
                float len = Mathf.random(1f, 7f);
                float a = b.rotation() + Mathf.range(fragCone / 2) + fragAngle;
                spawnBullet.create(b, b.x + Angles.trnsx(a, len), b.y + Angles.trnsy(a, len), a, Mathf.random(fragVelocityMin, fragVelocityMax), Mathf.random(fragLifeMin, fragLifeMax));
            }
        }
    }
}