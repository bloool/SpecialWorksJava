package spec.content;

import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.*;
import spec.entities.bullet.*;

public class SWBullets implements ContentList{
    public static BulletType

    clusterLance, clusterLanceDense,
    fireLance, fireLanceDense,
    pierceLance, pierceLanceDense,
    lightLance, lightLanceDense,

    copperSentry;

    @Override
    public void load(){

        //region normal artillery
        clusterLance = new ClusterBulletType(5f, 200f){{
            knockback = 5f;
            lifetime = 80f;
            width = 8;
            height = 26f;
            despawnEffect = Fx.flakExplosion;
            trailChance = 0.5f;

            explosionAmount = 10;
            explosionRange = 15f;
            explosionDamage = 60f;
            explosionRadius = 18f * 0.75f;
            explosionEffect = despawnEffect;
        }};

        fireLance = new ClusterBulletType(5f, 200f){{
            knockback = 5f;
            lifetime = 80f;
            width = 8;
            height = 26f;
            despawnEffect = Fx.flakExplosion;
            trailChance = 0.8f;
            status = StatusEffects.burning;
            statusDuration = 60f * 12f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            makeFire = true;
            trailEffect = Fx.incendTrail;
            ammoMultiplier = 4f;

            explosionAmount = 10;
            explosionRange = 15f;
            explosionDamage = 60f;
            explosionRadius = 18f * 0.75f;
            explosionEffect = despawnEffect;
        }};

        pierceLance = new ClusterBulletType(8f, 220f){{
            knockback = 8f;
            lifetime = 80f;
            width = 9;
            height = 32f;
            despawnEffect = Fx.flakExplosion;
            reloadMultiplier = 0.8f;
            trailWidth = 2;
            trailLength = 6;
            pierce = true;
            pierceCap = 3;

            explosionAmount = 3;
            explosionRange = 15f;
            explosionDamage = 60f;
            explosionRadius = 18f * 0.75f;
            explosionEffect = despawnEffect;
        }};

        lightLance = new ClusterBulletType(7f, 100f){{
            knockback = 1f;
            lifetime = 80f;
            reloadMultiplier = 2;
            ammoMultiplier = 2;
            width = 7;
            height = 23f;
            despawnEffect = Fx.flakExplosion;
            trailChance = 0.2f;

            explosionAmount = 5;
            explosionRange = 15f;
            explosionDamage = 60f;
            explosionRadius = 18f * 0.75f;
            explosionEffect = despawnEffect;
        }};

        //endregion

        //region dense artillery
        clusterLanceDense = new ClusterBulletType(5f, 200f){{
            knockback = 3f;
            lifetime = 80f;
            width = 11;
            height = 29f;
            despawnEffect = Fx.blastExplosion;
            trailChance = 0.5f;

            explosionAmount = 8;
            explosionRange = 20f;
            explosionDamage = 150f;
            explosionRadius = 25f * 0.75f;
            explosionEffect = despawnEffect;
        }};

        fireLanceDense = new ClusterBulletType(5f, 200f){{
            knockback = 5f;
            lifetime = 80f;
            width = 11;
            height = 29f;
            despawnEffect = Fx.blastExplosion;
            trailChance = 0.8f;
            status = StatusEffects.burning;
            statusDuration = 60f * 12f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            makeFire = true;
            trailEffect = Fx.incendTrail;
            ammoMultiplier = 4f;

            explosionAmount = 8;
            explosionRange = 20f;
            explosionDamage = 150f;
            explosionRadius = 25f * 0.75f;
            explosionEffect = despawnEffect;
        }};

        pierceLanceDense = new ClusterBulletType(9f, 300f){{
            knockback = 4f;
            reloadMultiplier = 0.8f;
            lifetime = 80f;
            width = 11;
            height = 35f;
            despawnEffect = Fx.flakExplosion;
            trailWidth = 2;
            trailLength = 6;
            pierce = true;
            pierceCap = 5;

            explosionAmount = 5;
            explosionRange = 15f;
            explosionDamage = 150f;
            explosionRadius = 25f * 0.75f;
            explosionEffect = despawnEffect;
        }};

        lightLanceDense = new ClusterBulletType(7f, 100f){{
            knockback = 1f;
            lifetime = 80f;
            reloadMultiplier = 2;
            ammoMultiplier = 2;
            width = 8;
            height = 26f;
            despawnEffect = Fx.flakExplosion;
            trailChance = 0.2f;

            explosionAmount = 40;
            explosionDelay = 1f;
            explosionRange = 15f;
            explosionDamage = 75f;
            explosionRadius = 25f * 0.75f;
            explosionEffect = despawnEffect;
        }};
        //endregion

        //region sentries


        //endregion
    }
}
