package spec.world.meta;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import spec.entities.bullet.*;

import static mindustry.Vars.tilesize;

public class SWStats{
    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map){
        return ammo(map, 0);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, int indent){
        return table -> {

            table.row();

            var orderedKeys = map.keys().toSeq();
            orderedKeys.sort();

            for(T t : orderedKeys){
                boolean compact = t instanceof UnitType || indent > 0;

                BulletType type = map.get(t);

                //no point in displaying unit icon twice
                if(!compact && !(t instanceof PowerTurret)){
                    table.image(icon(t)).size(3 * 8).padRight(4).right().top();
                    table.add(t.localizedName).padRight(10).left().top();
                }

                table.table(bt -> {
                    bt.left().defaults().padRight(3).left();

                    //vanilla ones
                    {
                        if(type.damage > 0 && (type.collides || type.splashDamage <= 0)){
                            if(type.continuousDamage() > 0){
                                bt.add(Core.bundle.format("bullet.damage", type.continuousDamage()) + StatUnit.perSecond.localized());
                            }else{
                                bt.add(Core.bundle.format("bullet.damage", type.damage));
                            }
                        }

                        if(type.buildingDamageMultiplier != 1){
                            sep(bt, Core.bundle.format("bullet.buildingdamage", (int)(type.buildingDamageMultiplier * 100)));
                        }

                        if(type.splashDamage > 0){
                            sep(bt, Core.bundle.format("bullet.splashdamage", (int)type.splashDamage, Strings.fixed(type.splashDamageRadius / tilesize, 1)));
                        }

                        if(!compact && !Mathf.equal(type.ammoMultiplier, 1f) && type.displayAmmoMultiplier){
                            sep(bt, Core.bundle.format("bullet.multiplier", (int)type.ammoMultiplier));
                        }

                        if(!compact && !Mathf.equal(type.reloadMultiplier, 1f)){
                            sep(bt, Core.bundle.format("bullet.reload", Strings.autoFixed(type.reloadMultiplier, 2)));
                        }

                        if(type.knockback > 0){
                            sep(bt, Core.bundle.format("bullet.knockback", Strings.autoFixed(type.knockback, 2)));
                        }

                        if(type.healPercent > 0f){
                            sep(bt, Core.bundle.format("bullet.healpercent", Strings.autoFixed(type.healPercent, 2)));
                        }

                        if(type.pierce || type.pierceCap != -1){
                            sep(bt, type.pierceCap == -1 ? "@bullet.infinitepierce" : Core.bundle.format("bullet.pierce", type.pierceCap));
                        }

                        if(type.incendAmount > 0){
                            sep(bt, "@bullet.incendiary");
                        }

                        if(type.homingPower > 0.01f){
                            sep(bt, "@bullet.homing");
                        }

                        if(type.lightning > 0){
                            sep(bt, Core.bundle.format("bullet.lightning", type.lightning, type.lightningDamage < 0 ? type.damage : type.lightningDamage));
                        }

                        if(type.status != StatusEffects.none){
                            sep(bt, (type.minfo.mod == null ? type.status.emoji() : "") + "[stat]" + type.status.localizedName);
                        }

                        if(type.fragBullet != null){
                            sep(bt, Core.bundle.format("bullet.frags", type.fragBullets));
                            bt.row();

                            ammo(ObjectMap.of(t, type.fragBullet), indent + 1).display(bt);
                        }
                    }

                    //mine
                    if(type instanceof RicochetBulletType b){
                        sep(bt, "bullet.spec-j-ricochets");
                        sep(bt, Core.bundle.format("bullet.spec-j-ricochet-range", b.ricochetRange));
                    }
                    if(type instanceof ClusterArtilleryBulletType b){
                        sep(bt, Core.bundle.format("bullet.spec-j-explosions", b.explosionAmount));
                        sep(bt, Core.bundle.format("bullet.spec-j-explosion-damage", b.explosionDamage));
                    }
                    if(type instanceof ClusterBulletType b){
                        sep(bt, Core.bundle.format("bullet.spec-j-explosions", b.explosionAmount));
                        sep(bt, Core.bundle.format("bullet.spec-j-explosion-damage", b.explosionDamage));
                    }
                    if(type instanceof SpawnBulletType b){
                        sep(bt, "bullet.spec-j-spawns");
                        bt.row();

                        ammo(ObjectMap.of(t, b.spawnBullet), indent + 1).display(bt);
                    }
                }).padTop(compact ? 0 : -9).padLeft(indent * 8).left().get().background(compact ? null : Tex.underline);

                table.row();
            }
        };
    }

    private static void sep(Table table, String text){
        table.row();
        table.add(text);
    }

    private static TextureRegion icon(UnlockableContent t){
        return t.fullIcon;
    }
}
