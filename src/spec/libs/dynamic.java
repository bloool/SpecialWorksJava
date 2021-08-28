package spec.libs;

import mindustry.entities.*;
import mindustry.entities.bullet.*;

public class dynamic{
    public static BulletType explosion(float damage2, float radius, Effect effect){
        return new BombBulletType(0f, 0f, "clear"){{
            despawnEffect = effect;
            hitEffect = effect;
            lifetime = 10f;
            speed = 1f;
            splashDamageRadius = radius;
            instantDisappear = true;
            splashDamage = damage2; //for some reason intellij doesnt recognize when I write it as just 'damage' so I'll keep it like this
            hittable = false;
            collidesAir = true;
        }};
    }
}
