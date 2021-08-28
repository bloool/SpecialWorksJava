package spec.entities.bullet;

import arc.math.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class RicochetBulletType extends BasicBulletType{
    public float ricochetRange = 120;

    public RicochetBulletType(float speed, float damage){
        super(speed, damage);

        hitEffect = Fx.hitBeam;
        pierce = true;
    }

    public RicochetBulletType(){
        this(1f, 1f);
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        if(entity instanceof Unit u){
            Units.nearbyEnemies(b.team, b.x, b.y, ricochetRange, e -> {
                if(e != null && Mathf.dst(u.x, u.y, e.x, e.y) > 0.1) b.rotation(b.angleTo(e));
            });
        }
        super.hitEntity(b, entity, health);
    }
}
