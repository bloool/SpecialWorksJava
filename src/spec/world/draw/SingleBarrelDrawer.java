package spec.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.Turret.*;

import static arc.Core.atlas;

public class SingleBarrelDrawer extends BaseBarrelDrawer{
    public float barrelRecoilMultiplier = 1.5f;
    public boolean barrelTop = false;
    public TextureRegion turret, outline, barrel, barrelOutline, full;

    public SingleBarrelDrawer(){
    }

    public SingleBarrelDrawer(float barrelRecoilMultiplier){
        this.barrelRecoilMultiplier = barrelRecoilMultiplier;
    }

    @Override
    public void load(Block block){
        turret = atlas.find(block.name);
        outline = atlas.find(block.name + "-outline");
        barrel = atlas.find(block.name + "-barrel");
        barrelOutline = atlas.find(block.name + "-barrel-outline");
        full = atlas.find(block.name + "-full");
    }

    @Override
    public TextureRegion[] icons(Block block, TextureRegion baseRegion){
        return new TextureRegion[]{
        baseRegion,
        full
        };
    }

    @Override
    public void draw(TurretBuild build, float elevation, TextureRegion region, TextureRegion baseRegion){
        //TODO add heat drawer
        int top = barrelTop ? 1 : -1;

        Draw.rect(baseRegion, build.x, build.y);
        Draw.color();

        Draw.z(Layer.turret - 2);

        Tmp.v1.trns(build.rotation, -build.recoil);
        Tmp.v2.trns(build.rotation, -build.recoil * barrelRecoilMultiplier);

        //shadows
        Drawf.shadow(turret, build.x + Tmp.v1.x - elevation, build.y + Tmp.v1.y - elevation, build.rotation - 90);
        Drawf.shadow(barrel, build.x + Tmp.v2.x - elevation, build.y + Tmp.v2.y - elevation, build.rotation - 90);

        //outlines
        //barrel
        Draw.rect(barrelOutline, build.x + Tmp.v2.x, build.y + Tmp.v2.y, build.rotation - 90);
        //turret
        Draw.rect(outline, build.x + Tmp.v1.x, build.y + Tmp.v1.y, build.rotation - 90);

        //actual turret
        //barrel
        Draw.z(Layer.turret + top);
        Draw.rect(barrel, build.x + Tmp.v2.x, build.y + Tmp.v2.y, build.rotation - 90);

        Draw.z(Layer.turret);
        //turret
        Draw.rect(turret, build.x + Tmp.v1.x, build.y + Tmp.v1.y, build.rotation - 90);
    }
}
