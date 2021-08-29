package spec.world.blocks.denfese.turret;


import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import spec.world.meta.*;

public class BackFirePowerTurret extends PowerTurret{
    public int inaccuracyf = 30;
    public float createRange = 40;
    public float createRangeInaccuracy = 3;

    public BackFirePowerTurret(String name){
        super(name);
    }

    public class BackFirePowerTurretBuild extends PowerTurretBuild{
        @Override
        public void targetPosition(Posc pos){
            if(!hasAmmo() || pos == null) return;
            //set the vanilla target pos to normal so the turret it self doesn't aim in advance
            targetPos.set(pos);
        }

        @Override
        protected void bullet(BulletType type, float angle){
            float lifeScl = type.scaleVelocity ? Mathf.clamp(Mathf.dst(x + tr.x, y + tr.y, targetPos.x, targetPos.y) / type.range(), minRange / type.range(), range / type.range()) : 1f;

            if(Mathf.dst(x, y, targetPos.x, targetPos.y) <= range){
                float hitsize = 0;
                if(target instanceof Unit u){
                    hitsize = u.hitSize;
                }

                Tmp.v1.trns(rotation + Mathf.range(inaccuracyf), createRange + Mathf.range(createRangeInaccuracy) + hitsize);

                type.create(this, team, targetPos.x + Tmp.v1.x, targetPos.y + Tmp.v1.y, Angles.angle(targetPos.x + Tmp.v1.x, targetPos.y + Tmp.v1.y, targetPos.x, targetPos.y), 1f + Mathf.range(velocityInaccuracy), lifeScl);
            }
        }
    }
}
