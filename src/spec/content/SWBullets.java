package spec.content;

import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import spec.entities.bullet.ClusterArtilleryBulletType;
import spec.entities.bullet.RicochetBulletType;
import spec.libs.dynamicEffects;
import spec.world.blocks.denfese.turret.AcceleratingTurret;

import static mindustry.type.ItemStack.*;

public class SWBullets implements ContentList {
    public static BulletType

    clusterBomb, clusterBombDense,
    eletricBomb, eletricBombDense

    ;

    @Override
    public void load(){
        clusterBomb = new ClusterArtilleryBulletType(3f, 20, "shell"){{
            hitEffect = Fx.flakExplosion;
            knockback = 0.8f;
            lifetime = 80f;
            width = height = 26f;
            collidesTiles = false;
            splashDamageRadius = 18f * 0.75f;
            splashDamage = 60;
            despawnEffect = Fx.flakExplosion;

            explosionAmount = 20;
            explosionRange = 20f;
            explosionDamage = 60f;
            explosionRadius = splashDamageRadius;
            explosionEffect = despawnEffect;
        }};

        clusterBombDense = new ClusterArtilleryBulletType(3f, 20, "shell"){{
            hitEffect = Fx.flakExplosion;
            knockback = 0.8f;
            lifetime = 80f;
            width = height = 26f;
            collidesTiles = false;
            splashDamageRadius = 18f * 0.75f;
            splashDamage = 60;
            despawnEffect = dynamicEffects.circleBoom(splashDamageRadius, backColor);

            explosionAmount = 20;
            explosionRange = 20f;
            explosionDamage = 60f;
            explosionRadius = splashDamageRadius;
            explosionEffect = despawnEffect;
        }};

        clusterBombDense = new ClusterArtilleryBulletType(4f, 20, "shell"){{
            hitEffect = Fx.flakExplosion;
            knockback = 0.8f;
            lifetime = 80f;
            width = height = 24f;
            collidesTiles = false;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 52f;
            despawnEffect = Fx.massiveExplosion;

            explosionAmount = 12;
            explosionDelay = 2;
            explosionRange = 35f;
            explosionDamage = 500f;
            explosionRadius = splashDamageRadius;
            explosionEffect = despawnEffect;
        }};

        eletricBombDense = new ClusterArtilleryBulletType(3f, 20, "shell"){{
            hitEffect = Fx.flakExplosion;
            knockback = 0.8f;
            lifetime = 80f;
            width = height = 24f;
            collidesTiles = false;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 52f;
            despawnEffect = dynamicEffects.circleBoom(splashDamageRadius, backColor);

            explosionAmount = 12;
            explosionDelay = 2;
            explosionRange = 35f;
            explosionDamage = 500f;
            explosionRadius = splashDamageRadius;
            explosionEffect = despawnEffect;
        }};
    }
}
