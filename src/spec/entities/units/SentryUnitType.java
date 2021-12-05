package spec.entities.units;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.BulletType;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;
import spec.entities.units.entity.SentryEntity;

public class SentryUnitType extends UnitType{

    public float duration = 600f;
    public float turretInaccuracy = 0f;
    public float turretReload = 20f;
    public float turretRotateSpeed = 20f;
    public float turretVelocityInaccuracy = 0f;
    public int turretShots = 1;
    public float turretSpread = 4f;
    public float turretRecoilAmount = 1f;
    public float turretShootCone = 8f;
    public float turretShootShake = 0f;
    public float turretShootLength = -1;
    public float turretXRand = 0f;
    public int turretSize = 2;

    public boolean turretTargetAir = true;
    public boolean turretTargetGround = true;

    public BulletType shootType = Bullets.standardCopper;
    public TextureRegion turretBaseRegion;

    public SentryUnitType(String name){
        super(name);
        constructor = SentryEntity::new;

        rotateSpeed = 0f;
        rotateShooting = true;
        speed = accel = 0f;
        drag = 0.12f;
        isCounted = false;
        itemCapacity = 10;
        health = 200;
        commandLimit = 0;
    }

    @Override
    public void init(){
        super.init();

        health = duration;

        weapons.add(new Weapon(name + "-gun"){{
            top = false;
            rotate = true;
            x = 0f;
            y = 0f;

            inaccuracy = turretInaccuracy;
            reload = turretReload;
            rotateSpeed = turretRotateSpeed;
            shots = turretShots;
            shake = turretShootShake;
            recoil = turretRecoilAmount;
            spacing = turretSpread;
            shootY = turretShootLength;
            xRand = turretXRand;
            velocityRnd = turretVelocityInaccuracy;
            shootCone = turretShootCone;

            bullet = shootType;
        }});
    }

    @Override
    public void load(){
        super.load();

        turretBaseRegion = Core.atlas.find("sentry-base-size-" + turretSize);
    }

    @Override
    public void update(Unit u){
        u.damage(1);
    }

    @Override
    public void draw(Unit unit){
        Draw.reset();
        Draw.rect(turretBaseRegion, unit.x, unit.y);
        drawWeapons(unit);
    }
}
