package spec.world.blocks.denfese.turret;

import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import spec.entities.bullet.*;

public class OrbTurret extends SWPowerTurret{
    public OrbBulletType orbType;
    public int orbAmount = 1;

    public OrbTurret(String name){
        super(name);
    }

    public class OrbTurretBuild extends SWPowerTurretBuild{
        public Bullet[] orbs = new Bullet[orbAmount];

        @Override
        public void updateTile(){
            for(int i = 0; i < orbAmount; i++){
                if(orbs[i] == null && efficiency() != 0){
                    orbs[i] = orbType.create(this, x, y, Mathf.random(0f, 360f));
                }

                if(orbs[i] == null) return;

                //never despawns
                orbs[i].lifetime = orbs[i].time + 60;

                findTarget();

                if(target == null){
                    target = this;
                }

                if(isControlled()){
                    if(Mathf.dst(unit.aimX(), unit.aimY(), x,y) <= range){
                        targetPos.set(unit.aimX(), unit.aimY());
                    }
                }else{
                    targetPos.set(target);
                }

                Tmp.v1.trns(orbs[i].angleTo(targetPos), orbType.speed * 0.5f * delta() * efficiency() * baseReloadSpeed());
                if(!Tmp.v1.isZero()){
                    if(orbs[i].dst2(targetPos) > orbType.orbSize){
                        orbs[i].vel().add(Tmp.v1);
                    }else{
                        orbs[i].vel().sub(Tmp.v1.scl(10f));
                        orbs[i].collided.clear();
                    }

                    if(orbType.orbSize <= 6) orbs[i].collided.clear();
                    orbs[i].vel().setAngle(Angles.moveToward(orbs[i].rotation(), orbs[i].angleTo(targetPos), orbType.homingPower * 50f * Time.delta));
                }

                orbs[i].vel().clamp(0.01f, orbType.maxSpeed);
            }
        }
    }
}
