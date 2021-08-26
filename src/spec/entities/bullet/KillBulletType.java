package spec.entities.bullet;

import mindustry.entities.bullet.*;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;

public class KillBulletType extends BasicBulletType{
    public KillBulletType(float speed, float damage) {
        super(speed, damage);
    }

    protected Runnable unitKill = () -> {};

    public void onKill(Runnable run){
        this.unitKill = run;
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        super.hitEntity(b, entity, health);
        if(entity instanceof Unit u) {
            if(health <= 0 || u.dead()) unitKill.run();
        }
    }
}