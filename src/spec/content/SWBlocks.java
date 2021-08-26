package spec.content;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.util.Log;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.BuildVisibility;
import spec.entities.bullet.ClusterArtilleryBulletType;
import spec.entities.bullet.KillBulletType;
import spec.entities.bullet.RicochetBulletType;
import spec.libs.dynamicEffects;
import spec.world.blocks.denfese.turret.AcceleratingTurret;

import static mindustry.type.ItemStack.*;

public class SWBlocks implements ContentList {
    public static Block
            //deez
            gale,
            //artillery
            pointBlank, ridge,

            frog; //turret for testing

    @Override
    public void load(){

        //
        ridge = new ItemTurret("ridge"){
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
            restitution = 0.02f;
            recoilAmount = 6f;
            shootShake = 3f;
            range = 290f;
            minRange = 50f;

            health = 130 * size * size;
            shootSound = Sounds.artillery;
            }

            class RidgeBuild extends ItemTurretBuild{
                @Override
                public void draw(){
                    Draw.rect(Core.atlas.find("spec-j-frog"), x, y, rotation);
                }
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
                Items.copper, new KillBulletType(4, 10000){{
                    width = 16;
                    height = 16;
                    shrinkY = 0;
                    onKill(() -> Log.info("h"));
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
