package spec.world.blocks.denfese.turret;

import arc.audio.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import spec.world.draw.*;

public class SWPowerTurret extends PowerTurret{
    public float accSpeed = 0;
    public int accCap = 20;
    public float coolDownSpeed = accSpeed / 5f;
    public boolean accSpawns = false;

    public int spawnReloadTime = 10;
    public float spawnRange = 40f;
    public float spawnRangeInaccuracy = 180f;
    public float spawnRangeRand = 0.5f;
    public float spawnInaccuracy = inaccuracy;
    public float spawnVelocityInaccuracy = velocityInaccuracy;
    public int spawnAmount = shots;
    public Effect spawnEffect = shootEffect;
    public Sound spawnSound = shootSound;
    public BulletType secondary = null;

    public TurretDrawer shootDrawer = new TurretDrawer();
    public BaseBarrelDrawer barrelDrawer = new BaseBarrelDrawer();

    public SWPowerTurret(String name){ super(name); }

    @Override
    public void load(){
        super.load();

        shootDrawer.load(this);
        barrelDrawer.load(this);
    }

    @Override
    public TextureRegion[] icons(){
        return barrelDrawer.icons(this, baseRegion);
    }

    public class SWItemTurretBuild extends PowerTurretBuild{
        public float accAmount; //making it a increasing value that gets added to local reload var instead of a decreasing global reload time var
        public float spawnerReload = 0;

        @Override
        public void draw(){
            barrelDrawer.draw(this, elevation, region, baseRegion);
            shootDrawer.draw(this);
        }

        @Override
        public void drawLight(){
            super.drawLight();
            barrelDrawer.drawLight(this);
            shootDrawer.drawLight(this);
        }

        @Override
        public void displayBars(Table bars){
            super.displayBars(bars);

            if(accSpeed != 0){
                bars.add(new Bar("@stat.spec-j-accelerationAmount", Pal.ammo, () -> accAmount / accCap)).growX();
                bars.row();
            }
        }

        @Override
        public void updateTile(){
            // go back to zero when not shooting
            if(!isShooting()) accAmount = Mathf.lerpDelta(accAmount, 0, coolDownSpeed * Time.delta);

            super.updateTile();
        }

        @Override
        public void updateShooting(){
            if(accSpeed != 0){
                if(accAmount < accCap) accAmount += accSpeed * Time.delta * baseReloadSpeed();
                reload += accAmount;
            }
            if(secondary != null){
                spawnerReload += delta() * peekAmmo().reloadMultiplier * baseReloadSpeed() + (accSpawns ? accAmount : 0);
                Log.info(spawnerReload);

                if(spawnerReload >= spawnReloadTime){
                    spawn(secondary);
                    spawnerReload %= spawnReloadTime;
                }
            }

            super.updateShooting();
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
