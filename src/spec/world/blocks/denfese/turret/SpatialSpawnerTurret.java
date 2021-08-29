package spec.world.blocks.denfese.turret;

import arc.audio.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.turrets.*;

public class SpatialSpawnerTurret extends PowerTurret{
    public int spawnReloadTime = 10;
    public float spawnRange = 40f;
    public float spawnRangeInaccuracy = 180f;
    public float spawnRangeRand = 0.5f;
    public BulletType secondary = null;

    public float spawnInaccuracy = inaccuracy;
    public float spawnVelocityInaccuracy = velocityInaccuracy;
    public int spawnAmount = shots;

    public Effect spawnEffect = shootEffect;
    public Sound spawnSound = shootSound;

    public SpatialSpawnerTurret(String name){
        super(name);
    }
    public class SpatialSpawnerTurretBuild extends PowerTurretBuild{
        public float spawnerReload = 0;

        @Override
        public void updateShooting(){
            super.updateShooting();

            spawnerReload += delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
            Log.info(spawnerReload);

            if(spawnerReload >= spawnReloadTime){
                spawn(secondary);
                spawnerReload %= spawnReloadTime;
            }
        }

        public void spawn(BulletType type){
            for(int i = 0; i < spawnAmount; i++){
                Tmp.v1.trns(rotation + Mathf.range(spawnRangeInaccuracy), spawnRange + (1f + Mathf.range(spawnRangeRand)));
                float rx = Tmp.v1.x + x;
                float ry = Tmp.v1.y + y;
                float angle = Angles.angle(rx, ry, targetPos.x, targetPos.y) + Mathf.range(spawnInaccuracy);

                type.create(this, team, rx, ry, angle, spawnVelocityInaccuracy);

                spawnEffect.at(rx, ry, angle);
                spawnSound.at(rx, ry);
            }
        }
    }
}
