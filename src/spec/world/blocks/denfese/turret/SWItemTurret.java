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
import mindustry.world.meta.*;
import spec.world.draw.*;
import spec.world.meta.*;

public class SWItemTurret extends ItemTurret{
    /** How fast the turret accelerates */
    public float accSpeed = 0;
    /** A cap to the acceleration */
    public int accCap = 20;
    /** How fast the turret goes back to normal speed */
    public float coolDownSpeed = accSpeed / 5f;
    /** If to also accelerate the spawns of spatial bullets */
    public boolean accSpawns = false;

    /** The time between each spawn */
    public int spawnReloadTime = 10;
    /** How far away from the turret it spawns */
    public float spawnRange = 40f;
    /** The angle from where they spawn from the turret rotation, 180 to spawn all around */
    public float spawnRangeInaccuracy = 180f;
    /** A multiplier that gets applied to the spawn range */
    public float spawnRangeRand = 0.5f;
    /** The inaccuracy that each bullet has relative to the turret rotation, not to be confounded with spawnRangeInaccuracy that changes the location rather the rotation  */
    public float spawnInaccuracy = inaccuracy;
    /** The velocity inaccuracy of each bullet */
    public float spawnVelocityInaccuracy = velocityInaccuracy;
    /** The amount of bullets to spawn at each interval */
    public int spawnAmount = shots;
    /** If the turret should only shoot the secondary, this will make them consume ammo*/
    public boolean onlySpawns = false;
    /** Whether the spawned bullets aim toward the target or in the same direction as the turret*/
    public boolean spawnsAim = true;
    /** If each spawns consumes ammo*/
    public boolean spawnConsumeAmmo = false;
    /** The bullet that gets spawned*/
    public BulletType secondary = null;
    public Effect spawnEffect = shootEffect;
    public Sound spawnSound = shootSound;

    public TurretDrawer shootDrawer = new TurretDrawer();
    public BaseBarrelDrawer barrelDrawer = new BaseBarrelDrawer();

    public SWItemTurret(String name){ super(name); }

    @Override
    public void load(){
        super.load();

        shootDrawer.load(this);
        barrelDrawer.load(this);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, SWStats.ammo(ammoTypes));
    }

    @Override
    public TextureRegion[] icons(){
        return barrelDrawer.icons(this, baseRegion);
    }

    public class SWItemTurretBuild extends ItemTurretBuild{
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
            if(!isShooting()) accAmount = Mathf.lerpDelta(accAmount, 0, coolDownSpeed);

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
            if(!onlySpawns){
                super.updateShooting();
            }
        }

        public void spawn(BulletType type){
            for(int i = 0; i < spawnAmount; i++){
                Tmp.v1.trns(rotation + Mathf.range(spawnRangeInaccuracy), spawnRange + (1f + Mathf.range(spawnRangeRand)));
                float rx = Tmp.v1.x + x;
                float ry = Tmp.v1.y + y;
                float angle = (spawnsAim ? Angles.angle(rx, ry, targetPos.x, targetPos.y) : rotation) + Mathf.range(spawnInaccuracy);

                type.create(this, team, rx, ry, angle, 1 + Mathf.range(spawnVelocityInaccuracy));

                spawnEffect.at(rx, ry, angle);
                spawnSound.at(rx, ry);

                if(spawnConsumeAmmo || onlySpawns){
                    useAmmo();
                }
            }
        }
    }
}