package spec.content;

import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import spec.entities.bullet.*;
import spec.libs.*;
import spec.world.blocks.denfese.turret.*;
import spec.world.meta.*;

import static mindustry.type.ItemStack.with;

public class SWBlocks implements ContentList{
    public static Block
    discharge,
    //deez
    gale,
    //artillery
    shogun, ridge,

    frog; //turret for testing

    @Override
    public void load(){

        discharge = new AcceleratingTurret("discharge"){{
            requirements(Category.turret, with(Items.copper, 100, Items.graphite, 80, Items.titanium, 50));
            ammo(
            Items.thorium, new SpawnBulletType(4, 50){{
                width = 15;
                height = 18;
                despawnEffect = Fx.flakExplosion;
                spawnBullet = dynamic.explosion(180, 30 * 0.75f, Fx.massiveExplosion);
            }}
            );

            size = 3;
            range = 230f;
            reloadTime = 120f;
            accSpeed = 0.01f;
            accCap = 5;
            restitution = 0.03f;
            ammoEjectBack = 3f;
            cooldown = 0.03f;
            recoilAmount = 3f;
            shootShake = 2f;
            burstSpacing = 3f;
            shots = 4;
            ammoUseEffect = Fx.casing3Double;
            health = 240 * size * size;
            shootSound = Sounds.shootBig;

            limitRange();
        }};

        //start region artillery
        ridge = new BarrelTurret("ridge"){
            {
                requirements(Category.turret, with(Items.copper, 150, Items.graphite, 135, Items.titanium, 60));
                ammo(
                Items.graphite, SWBullets.clusterLance,
                Items.pyratite, SWBullets.fireLance,
                Items.thorium, SWBullets.pierceLance,
                Items.metaglass, SWBullets.lightLance
                );

                targetAir = false;
                size = 3;
                shots = 1;
                burstSpacing = 2;
                inaccuracy = 3f;
                reloadTime = 80f;
                ammoUseEffect = Fx.casing4;
                restitution = 0.03f;
                recoilAmount = 6f;
                shootShake = 3f;
                range = 290f;

                health = 130 * size * size;
                shootSound = Sounds.artillery;

                limitRange();
            }

            @Override
            public void setStats(){
                super.setStats();

                stats.remove(Stat.ammo);
                stats.add(Stat.ammo, SWStats.ammo(ammoTypes));
            }
        };

        shogun = new BarrelTurret("shogun"){
            {
                requirements(Category.turret, with(Items.copper, 150, Items.graphite, 135, Items.titanium, 60));
                ammo(
                Items.graphite, SWBullets.clusterLanceDense,
                Items.pyratite, SWBullets.fireLanceDense,
                Items.thorium, SWBullets.pierceLanceDense,
                Items.metaglass, SWBullets.lightLanceDense
                );

                targetAir = false;
                size = 4;
                shots = 3;
                shootCone = 60;
                burstSpacing = 2;
                inaccuracy = 5f;
                reloadTime = 80f;
                ammoUseEffect = Fx.casing3Double;
                velocityInaccuracy = 0.1f;
                restitution = 0.02f;
                recoilAmount = 6f;
                shootShake = 6f;
                range = 340f;

                health = 130 * size * size;
                shootSound = Sounds.artillery;

                limitRange();
            }

            @Override
            public void setStats(){
                super.setStats();

                stats.remove(Stat.ammo);
                stats.add(Stat.ammo, SWStats.ammo(ammoTypes));
            }
        };
        //end region

        gale = new ItemTurret("gale"){
            {
                requirements(Category.turret, with(Items.copper, 35), true);
                ammo(
                Items.metaglass, new RicochetBulletType(){{
                    sprite = "large-bomb";
                    width = 8;
                    height = 32;
                    shrinkY = 0;
                    speed = 5;
                    damage = 30;
                    trailLength = 5;
                }}
                );

                size = 3;
                shootLength = 0.5f;
                reloadTime = 30;
                recoilAmount = 3;
                range = 220;
                shootCone = 15f;
                ammoUseEffect = Fx.casing2;
                health = 250;
                inaccuracy = 2f;
                rotateSpeed = 10f;

                limitRange();
            }

            @Override
            public void setStats(){
                super.setStats();

                stats.remove(Stat.ammo);
                stats.add(Stat.ammo, SWStats.ammo(ammoTypes));
            }
        };

        // turret made for testing purposes
        frog = new AcceleratingTurret("frog"){{
            requirements(Category.turret, with(Items.copper, 35), true);
            ammo(
            Items.copper, new AcceleratingBulletType(1f, 100){{
                width = 8;
                height = 24;
                speedCap = 7f;
                lifetime = 120;
                drag = -0.01f;
                homingRange = 120;
                homingPower = 0.3f;
                homingDelay = 30f;
                trailLength = 5;
            }}
            );

            size = 4;
            reloadTime = 120;
            restitution = 0.03f;
            range = 400;
            ammoUseEffect = Fx.casing2;
            health = 250;
            inaccuracy = 2f;
            rotateSpeed = 10f;

            buildVisibility = BuildVisibility.sandboxOnly;

            limitRange();
        }};
    }
}
