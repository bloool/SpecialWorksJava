package spec.content;

import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.BuildVisibility;
import spec.entities.bullet.SpawnBulletType;
import spec.entities.bullet.RicochetBulletType;
import spec.libs.dynamic;
import spec.libs.dynamicEffects;
import spec.world.blocks.denfese.turret.AcceleratingTurret;
import spec.world.blocks.denfese.turret.BarrelTurret;

import static mindustry.type.ItemStack.*;

public class SWBlocks implements ContentList {
    public static Block
            discharge,
            //deez
            gale,
            //artillery
            pointBlank, ridge,

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
                    Items.graphite, SWBullets.clusterBomb,
                    Items.surgeAlloy, SWBullets.eletricBomb
            );

            targetAir = false;
            size = 3;
            shots = 1;
            burstSpacing = 2;
            inaccuracy = 3f;
            reloadTime = 80f;
            ammoUseEffect = Fx.casing4;
            velocityInaccuracy = 0.05f;
            restitution = 0.03f;
            recoilAmount = 6f;
            shootShake = 3f;
            range = 290f;
            minRange = 50f;

            health = 130 * size * size;
            shootSound = Sounds.artillery;
            }
        };

        pointBlank = new ItemTurret("point-blank"){{
            requirements(Category.turret, with(Items.copper, 150, Items.graphite, 135, Items.titanium, 60));
            ammo(
                    Items.graphite, SWBullets.clusterBombDense,
                    Items.surgeAlloy, SWBullets.eletricBombDense
            );

            targetAir = false;
            size = 4;
            shots = 3;
            burstSpacing = 2;
            inaccuracy = 12f;
            reloadTime = 80f;
            ammoUseEffect = Fx.casing3Double;
            velocityInaccuracy = 0.3f;
            restitution = 0.02f;
            recoilAmount = 6f;
            shootShake = 6f;
            range = 290f;
            minRange = 50f;

            health = 130 * size * size;
            shootSound = Sounds.artillery;
        }};
        //end region

        gale = new ItemTurret("gale"){{
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
        }};

        // turret made for testing purposes
        frog = new AcceleratingTurret("frog"){{
            requirements(Category.turret, with(Items.copper, 35), true);
            ammo(
                    Items.copper, new SpawnBulletType(4, 100){{
                        width = 16;
                        height = 16;
                        shrinkY = 0;
                        spawnBullet = dynamic.explosion(100000, 100, dynamicEffects.circleBoom(100, Pal.bulletYellow));
                    }}
            );

            size = 3;
            shootLength = 0.5f;
            reloadTime = 120;
            restitution = 0.03f;
            range = 400;
            shootCone = 15f;
            ammoUseEffect = Fx.casing2;
            health = 250;
            inaccuracy = 2f;
            rotateSpeed = 10f;

            buildVisibility = BuildVisibility.sandboxOnly;

            limitRange();
        }};
    }
}
