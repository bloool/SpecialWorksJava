package spec.entities;

import arc.*;
import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class Sentry extends BulletType{
    public String name;

    public BulletType shootType = Bullets.standardCopper;
    /** How much health the sentry has; they can only be hit by point defense */
    public int size = 2;
    public float sentryRange = 160;
    public float reloadTime = 30;
    public float shootCone = 40;
    public float shots = 1;
    public float rotateSpeed = 4;
    public float gunInaccuracy = 0; // gun before it, it doesn't conflict with the vanilla inaccuracy for bullets
    public float velocityInaccuracy = 0;
    public float gunLength = 0f;
    public float shootLength = 0f;
    public float recoilAmount = 2f;
    /** The delay before the gun starts shooting and aiming at enemies */
    public float armDelay = 50f;
    /** How tall the sentry gun is; only changes visually */
    public float elevation = 2f;

    public Sound shootSound = Sounds.shoot;

    public TextureRegion baseRegion, legRegion, gunRegion;

    public Sentry(float damage, float speed){
        super(damage, speed);

        this.collides = false; //don't change it to true
        this.lifetime = 15 * 60;
        this.drag = 0.1f;
        this.layer = Layer.groundUnit; //don't draw on the bullet layer so no bloom
        this.inaccuracy = 360; //not the sentry's inaccuracy!
        this.keepVelocity = false;
        this.hitColor = Color.clear;
        this.lightColor = Color.clear;

        this.reflectable = false;
        this.hittable = true;
        this.absorbable = false;

        this.hitEffect = Fx.none;
        this.despawnEffect = Fx.flakExplosion;
        this.despawnSound = Sounds.boom;
        this.smokeEffect = Fx.none;
        this.shootEffect = Fx.none;
    }

    @Override
    public void init(Bullet b){
        super.init(b);

        b.data = new float[]{0, 0};
    }

    @Override
    public void load(){
        super.load();

        baseRegion = Core.atlas.find("spec-j-sentry-base-size-" + size);
        gunRegion = Core.atlas.find("spec-j-" + name);
    }

    @Override
    public void draw(Bullet b){
        Draw.reset(); //don't remove or else blinks when it hits something for some reason

        //draw base
        Draw.rect(baseRegion, b.x, b.y, 0);

        if(b.data instanceof float[]){
            float recoil = ((float[])b.data)[1];
            float tx = b.x + Angles.trnsx(b.rotation(), -recoil);
            float ty = b.y + Angles.trnsy(b.rotation(), -recoil);
            //draw gun
            Drawf.shadow(gunRegion, tx - elevation, ty - elevation, b.rotation() - 90);
            //gun shadow
            Draw.rect(gunRegion, tx, ty, b.rotation() - 90);
        }

        Draw.z(layer - 1);
        Draw.color(Color.black);
        Draw.alpha(0.6f);
        Draw.rect(Core.atlas.find("circle-shadow"), b.x, b.y, size * 10, size * 10, 0);
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        if(b.data instanceof float[]){
            BulletType bullet = shootType;
            Posc target;

            ((float[])b.data)[1] = Mathf.lerpDelta(((float[])b.data)[1], 0f, 0.02f);

            if(bullet.healPercent > 0){
                target = Units.closestTarget(null, b.x, b.y, this.sentryRange,
                e -> e.team != b.team,
                t -> (t.team != b.team || t.damaged()));
            }else{
                target = Units.closestTarget(b.team, b.x, b.y, this.sentryRange);
            }

            if(target != null){
                Vec2 targetPos = new Vec2();

                // predict
                targetPos.set(Predict.intercept(b, target, bullet.speed <= 0.01 ? 99999999 : bullet.speed));
                if(targetPos.isZero()){
                    targetPos.set(target);
                }

                if(Float.isNaN(b.rotation())) b.rotation(0);

                float targetRot = b.angleTo(targetPos);
                if(b.time > armDelay) b.rotation(Angles.moveToward(b.rotation(), targetRot, rotateSpeed * Time.delta));

                if(Angles.angleDist(b.rotation(), targetRot) < shootCone && b.time > armDelay){
                    float reload = ((float[])b.data)[0];
                    float recoil = ((float[])b.data)[1];

                    ((float[])b.data)[0] += Time.delta * bullet.reloadMultiplier;

                    //shoot
                    if(reload >= reloadTime){
                        Vec2 tr = new Vec2();
                        tr.trns(b.rotation(), -recoil + shootLength);

                        for(int i = 0; i < shots; i++){
                            bullet.create(b, tr.x, tr.y, targetRot + Mathf.range(gunInaccuracy + bullet.inaccuracy), 1 + Mathf.range(velocityInaccuracy), 1);
                            shootSound.at(b);
                        }

                        ((float[])b.data)[0] %= reloadTime;
                        ((float[])b.data)[1] = recoilAmount;
                    }
                }
            }
        }
    }
}
