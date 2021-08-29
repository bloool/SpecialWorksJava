package spec.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import spec.entities.bullet.*;
import spec.libs.*;
import spec.world.blocks.denfese.turret.*;
import spec.world.blocks.denfese.turret.SpatialSpawnerTurret.*;
import spec.world.meta.*;

import static mindustry.type.ItemStack.with;

public class SWBlocks implements ContentList{
    public static Block
    discharge,
    //deez
    gale,
    //artillery
    shogun, ridge,
    //star
    antares,

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
        };

        shogun = new BarrelTurret("shogun"){
            {
                requirements(Category.turret, with(Items.copper, 1000, Items.graphite, 800, Items.surgeAlloy, 400));
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
        };
        //end region artillery

        //start region star
        antares = new SpatialSpawnerTurret("antares"){{
            requirements(Category.turret, with(Items.copper, 1200, Items.lead, 350, Items.graphite, 300, Items.surgeAlloy, 325, Items.silicon, 325));
            shootEffect = Fx.shootBigSmoke2;
            shootCone = 40f;
            recoilAmount = 4f;
            size = 4;
            shootLength = 0;
            shootShake = 2f;
            range = 195f;
            reloadTime = 180f;
            powerUse = 17f;
            shootSound = Sounds.plasmaboom;

            spawnAmount = 2;
            spawnEffect = Fx.absorb;
            spawnInaccuracy = 3f;
            spawnRange = 28f;
            spawnRangeRand = 0.7f;
            spawnReloadTime = 10;
            spawnSound = Sounds.sap;

            shootType = new ContinuousLaserBulletType(400){{
                colors = new Color[]{Palf.starLight.cpy().a(0.4f), Palf.starMid, Color.white};
                length = 250f;
                hitEffect = Fx.hitMeltdown;
                hitColor = Palf.starLight;
                drawSize = 420f;
                width = 8;

                incendChance = 0.4f;
                incendSpread = 5f;
                incendAmount = 1;
                ammoMultiplier = 1f;
            }};

            secondary = new LaserBulletType(140){{
                colors = new Color[]{Palf.starLight.cpy().a(0.4f), Palf.starMid, Color.white};
                hitEffect = Fx.hitLancer;
                hitSize = 4;
                lifetime = 16f;
                drawSize = 400f;
                length = 173f;
                ammoMultiplier = 1f;
            }};

            health = 200 * size * size;
            consumes.add(new ConsumeCoolant(0.5f)).update(false);
        }};

        //end region star

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
        frog = new SpatialSpawnerTurret("frog"){{
            requirements(Category.turret, with(Items.copper, 35), true);

            size = 4;
            shots = 1;
            reloadTime = 20;
            restitution = 0.03f;
            range = 400;
            ammoUseEffect = Fx.casing2;
            health = 250;
            inaccuracy = 2f;
            rotateSpeed = 10f;
            shootType = new LaserBulletType(140){{
                colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                hitEffect = Fx.hitLancer;
                hitSize = 4;
                lifetime = 16f;
                drawSize = 400f;
                length = 173f;
                ammoMultiplier = 1f;
            }};
            secondary = new LaserBulletType(140){{
                colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                hitEffect = Fx.hitLancer;
                hitSize = 4;
                lifetime = 16f;
                drawSize = 400f;
                length = 173f;
                ammoMultiplier = 1f;
            }};

            buildVisibility = BuildVisibility.sandboxOnly;
        }};
    }
}
