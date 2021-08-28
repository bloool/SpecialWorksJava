package spec.entities.bullet;

import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class AcceleratingBulletType extends BasicBulletType{

    public float speedCap = 6;

    public AcceleratingBulletType(float speed, float damage){
        super(speed, damage);
        drag = -0.02f;
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        if(b.vel.len() > speedCap){
            b.vel.setLength(speedCap);
        }
    }
}
