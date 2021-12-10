package spec.entities.bullet;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import spec.world.draw.*;

import java.util.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static mindustry.Vars.headless;

public class OrbBulletType extends BasicBulletType{
    public Color orbColor = Pal.lancerLaser, innerOrbColor = Color.white;
    public float orbSize = 5f;
    public float orbScl = 11f, orbMag = 0.5f;
    public float maxSpeed = speed * 5f;

    public boolean shiny = false;
    public int spikeAmount = 4;

    public OrbBulletType(float speed, float damage){
        super(speed, damage);

        hitEffect = new Effect(20f, e -> {
            color(orbColor, innerOrbColor, e.fin());
            alpha(e.fout());

            stroke(0.5f + e.fout());

            Angles.randLenVectors(e.id, (int)orbSize, e.fin() * 35f, (x, y) -> {
                float ang = Mathf.angle(x, y);
                lineAngle(e.x + x, e.y + y, ang, e.fout() * 3 + 1f);
            });
        });

        hitSize = orbSize;
        lifetime = 100f;
        homingPower = 0.1f;
        pierce = true;
        pierceBuilding = true;
        pierceCap = -1;

        hittable = false;
        absorbable = false;
    }

    @Override
    public void draw(Bullet b){
        drawTrail(b);
        updateTrail(b);

        float size = orbSize + Mathf.absin(Time.time, orbScl, orbMag);

        if(!Objects.equals(sprite, "bullet")){
            float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f);

            Draw.color(orbColor);
            Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() + offset);
            Draw.color(innerOrbColor);
            Draw.rect(frontRegion, b.x, b.y, width, height, b.rotation() + offset);
        }
        else if(shiny){
            float width = size * 0.25f;

            color(orbColor);
            for(int i = 0; i < spikeAmount; i++){
                Drawf.tri(b.x, b.y, width, size, b.vel.len2() + i * (360f / spikeAmount));
            }

            color(innerOrbColor);
            for(int i = 0; i < spikeAmount; i++){
                Drawf.tri(b.x, b.y, width * 0.5f, size * 0.5f, b.vel.len2() + i * (360f / spikeAmount));
            }

            color(innerOrbColor.cpy().a(0.6f));
            for(int i = 0; i < spikeAmount; i++){
                Drawf.tri(b.x, b.y, width * 0.5f, size * 0.75f, Time.time - i * (360f / spikeAmount));
            }
        }
        else{
            color(orbColor);
            SWDraw.FillCircle(b.x, b.y, size);

            color(innerOrbColor);
            SWDraw.FillCircle(b.x, b.y, size / 2);
        }
    }

    @Override
    public void update(Bullet b){
        //no
    }

    //for some reason, I cant call updateTrail() even though it exists in BulletType so I just copy and pasted it here
    public void updateTrail(Bullet b){
        if(!headless && trailLength > 0){
            if(b.trail == null){
                b.trail = new Trail(trailLength);
            }
            b.trail.length = trailLength;
            b.trail.update(b.x, b.y, trailInterp.apply(b.fin()));
        }
    }
}
