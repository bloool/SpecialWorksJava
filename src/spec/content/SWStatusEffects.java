package spec.content;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;

public class SWStatusEffects implements ContentList{
    public static StatusEffect magnetized;

    @Override
    public void load(){
        magnetized = new StatusEffect("magnetized"){{
            effect = new Effect(30, e -> {
                Draw.color();
                Fill.circle(e.x, e.y, e.fout() * 5);
            });

        }
            @Override
            public void update(Unit u, float t){
                super.update(u, t);
                float range = u.hitSize * 2.5f;
                float homingPower = 0.2f;
                @Nullable Seq<Bullet> target;

                target = Groups.bullet.intersect(u.x - range, u.y - range, range*2, range*2);
                target.forEach(b -> b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(u), homingPower * Time.delta * 50f)));
            }
        };
    }
}
