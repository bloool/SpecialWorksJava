package spec.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;
import spec.entities.*;
import spec.entities.bullet.*;
import spec.libs.*;
import spec.world.blocks.denfese.*;
import spec.world.blocks.denfese.turret.*;
import spec.world.draw.*;
import spec.world.meta.*;

import static mindustry.type.ItemStack.with;

public class SWBlocks implements ContentList{
    public static Block

    //environment
    tree, treeBig,

    //turrets
    discharge, gale,

    shogun, ridge,

    antares, polaris,

    razor, excalibur,

    spawn,

    orbit, impaler, solaris,

    //crafters
    laminaPress,

    //transport
    plastPayloadConveyor, plastPayloadRouter,

    //effect
    anchor,

    ferret; //turret for testing

    @Override
    public void load(){
        //region turrets

        //region misc
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
                reloadTime = 30;
                recoilAmount = 3;
                range = 220;
                shootCone = 15f;
                ammoUseEffect = Fx.casing2;
                health = 250;
                shots = 2;
                inaccuracy = 15f;
                rotateSpeed = 10f;

                limitRange();
            }

            @Override
            public void setStats(){
                super.setStats();

                stats.remove(Stat.ammo);
                stats.add(Stat.ammo, SWBulletsStats.ammo(ammoTypes));
            }
        };

        discharge = new SWItemTurret("discharge"){{
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

        //endregion

        //region artillery
        ridge = new SWItemTurret("ridge"){
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

                barrelDrawer = new SingleBarrelDrawer();
                outlineIcon = false;

                limitRange();
            }
        };

        shogun = new SWItemTurret("shogun"){
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

                barrelDrawer = new SingleBarrelDrawer();
                outlineIcon = false;

                limitRange();
            }
        };
        //endregion artillery

        //region star
        antares = new SWPowerTurret("antares"){{
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

            health = 300 * size * size;
            shootDrawer = new ShineDrawer(){{
                shineSize = 20f;
                angleOffset = 45f;
            }};
        }};

        polaris = new SWPowerTurret("polaris"){{
            requirements(Category.turret, with(Items.copper, 300, Items.lead, 200, Items.silicon, 325));
            size = 2;
            health = 300 * size * size;
            shootType = new BulletType(){{
                instantDisappear = true;
                despawnEffect = Fx.none;
                hitEffect = Fx.none;
            }};

            spawnConsumeAmmo = true;
            spawnsAim = false;
            spawnRangeInaccuracy = 45;
            spawnAmount = 2;
            spawnReloadTime = 20;
            spawnRange = 10 * size;
            spawnSound = Sounds.missile;
            spawnEffect = new Effect(30, e-> {
                Draw.color(Palf.starLight);
                e.rotation += e.fout() * 100 * Mathf.randomSeedRange(e.id, 0.5f);
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 5f * e.fout(), 10f, e.rotation + 45 + 90 * i);
                }
            });
            range = 180;
            secondary = new AcceleratingBulletType(0.5f, 30){{
                speedCap = 8;
                lifetime = 50;
                homingDelay = 20;
                homingPower = 0.4f;
                homingRange = 120;
                drag = -0.1f;
                hitEffect = Fx.absorb;
                despawnHit = true;
                trailLength = 5;
                trailColor = Palf.starMid;
                sprite = "large-bomb";
                width = 10;
                height = 20;
            }};

            reloadTime = spawnReloadTime;
        }};
        //endregion star

        //region orb

        orbit = new OrbTurret("orbit"){{
            requirements(Category.turret, with(Items.copper, 35), true);

            size = 2;
            range = 100f;
            rotateSpeed = 10f;
            health = 200 * size * size;

            shootDrawer = new ShineDrawer(Palf.neuronLight){{
                shineSize = 12f;
                spikeAmount = 0;
            }};

            orbType = new OrbBulletType(1f, 16f){{
                orbSize = 3f;
                trailLength = 5;
                trailWidth = 3f;
                orbColor = trailColor = Palf.neuronLight;
            }};
        }};

        impaler = new OrbTurret("impaler"){{
            requirements(Category.turret, with(Items.copper, 35), true);

            size = 3;
            range = 200f;
            rotateSpeed = 10f;
            orbAmount = 3;
            health = 200 * size * size;

            shootDrawer = new ShineDrawer(Palf.neuronLight){{
                shineSize = 18f;
                spikeAmount = 3;
            }};

            orbType = new OrbBulletType(1.8f, 12f){{
                orbSize = 2.5f;
                trailLength = 8;
                trailWidth = 3f;
                orbColor = trailColor = Palf.neuronLight;
                sprite = "large-bomb";
                width = 8f;
                height = 25f;
            }};
        }};

        solaris = new OrbTurret("solaris"){{
            requirements(Category.turret, with(Items.copper, 35), true);

            size = 4;
            range = 300f;
            rotateSpeed = 10f;
            orbAmount = 2;
            health = 200 * size * size;

            shootDrawer = new ShineDrawer(Palf.neuronLight){{
                shineSize = 24f;
                spikeAmount = 6;
                rotatingShine = true;
            }};

            orbType = new OrbBulletType(1.5f, 120f){{
                orbSize = 16f;
                trailLength = 6;
                trailWidth = 3f;
                orbColor = trailColor = Palf.neuronDark;
                shiny = true;
            }};
        }};

        //endregion

        //region melee
        razor = new SWPowerTurret("razor"){{
            requirements(Category.turret, with(Items.lead, 80, Items.graphite, 30));

            size = 2;
            reloadTime = 20f;
            recoilAmount = -9f;
            shootShake = 1f;
            range = 35f;
            restitution = 0.06f;

            health = 130 * size * size;
            shootSound = Sounds.release;

            barrelDrawer = new SingleBarrelDrawer(1.8f);
            outlineIcon = false;

            shootType = new RailBulletType(){{
                length = range + 5;
                damage = 160;
                pierceDamageFactor = 0f;
            }};
        }};

        //endregion melee

        //region sentry spawners

        spawn = new SWItemTurret("spawn"){{
            requirements(Category.turret, with(Items.copper, 35), true);
            ammo(
            Items.copper, new Sentry(1000, 5){{
                name = "copper-sentry";
            }}
            );

            size = 3;
            reloadTime = 60;
            range = 300;

            rotateSpeed = 0;
            shootCone = 360;
            inaccuracy = 360;
            shootLength = 0;
        }};

        //endregion

        // turret made for testing purposes
        ferret = new OrbTurret("ferret"){{
            requirements(Category.turret, with(Items.copper, 35), true);

            size = 4;
            range = 200f;
            rotateSpeed = 10f;
            orbAmount = 3;

            orbType = new OrbBulletType(2f, 50f){{
                trailLength = 5;
                trailWidth = 3f;
                trailColor = Pal.lancerLaser;
            }};

            buildVisibility = BuildVisibility.sandboxOnly;
        }};

        //endregion

        //region crafters

        laminaPress = new GenericCrafter("kiln"){{
            requirements(Category.crafting, with(Items.metaglass, 400, Items.lead, 180, Items.copper, 220, Items.graphite, 120));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(SWItems.laminaglass, 1);
            craftTime = 40f;
            size = 4;
            hasPower = hasItems = true;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

            consumes.liquid(Liquids.water, 0.4f);
            consumes.items(with(Items.coal, 1, Items.metaglass, 2));
            consumes.power(1f);
        }};

        //endregion

        //region transport

        plastPayloadConveyor = new PayloadConveyor("payload-conveyor"){{
            requirements(Category.units, with(Items.graphite, 10, Items.copper, 20));
            size = 2;
            canOverdrive = false;
        }};

        plastPayloadRouter = new PayloadRouter("payload-router"){{
            requirements(Category.units, with(Items.graphite, 15, Items.copper, 20));
            size = 2;
            canOverdrive = false;
        }};

        //endregion

        //region effect

        anchor = new AnchorProjector("anchor"){{
            requirements(Category.effect, with(Items.lead, 100, Items.titanium, 25, Items.silicon, 40));
            consumes.power(1.5f);
            size = 2;
            breakRange = 90f;
            force = breakRange;
            phaseColor = Items.phaseFabric.color;
            phaseBoost = 40f;
            phaseRangeBoost = 20f;
            consumes.item(Items.phaseFabric).boost();
        }};

        //endregion
    }
}
