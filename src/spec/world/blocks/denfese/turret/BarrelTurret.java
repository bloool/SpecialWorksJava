package spec.world.blocks.denfese.turret;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Tmp;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.turrets.ItemTurret;

import static arc.Core.atlas;

public class BarrelTurret extends ItemTurret {
    public float barrelRecoilMultiplier = 1.5f;
    public boolean barrelTop = false;
    public TextureRegion turret, outline, barrel, barrelOutline, full;

    public BarrelTurret(String name){
        super(name);
        outlineIcon = false;
    }

    @Override
    public void load(){
        super.load();

        turret = atlas.find(name);
        outline = atlas.find(name + "-outline");
        barrel = atlas.find(name + "-barrel");
        barrelOutline = atlas.find(name + "-barrel-outline");
        full = atlas.find(name + "-full");
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{
                baseRegion,
                atlas.find(name + "-full")
        };
    }

    public class BarrelTurretBuild extends ItemTurretBuild{
        public int top = barrelTop ? 1 : -1;

        @Override
        public void draw(){
            Draw.rect(baseRegion, x, y);
            Draw.color();

            Draw.z(Layer.turret - 2);

            Tmp.v1.trns(rotation, -recoil);
            Tmp.v2.trns(rotation, -recoil * barrelRecoilMultiplier);

            //shadows
            Drawf.shadow(turret, x + Tmp.v1.x - elevation, y + Tmp.v1.y - elevation, rotation - 90);
            Drawf.shadow(barrel, x + Tmp.v2.x - elevation, y + Tmp.v2.y - elevation, rotation - 90);

            //outlines
            //barrel
            Draw.rect(barrelOutline, x + Tmp.v2.x, y + Tmp.v2.y, rotation - 90);
            //turret
            Draw.rect(outline, x + Tmp.v1.x, y + Tmp.v1.y, rotation - 90);

            //actual turret
            //barrel
            Draw.z(Layer.turret + top);
            Draw.rect(barrel, x + Tmp.v2.x, y + Tmp.v2.y, rotation - 90);

            Draw.z(Layer.turret);
            //turret
            Draw.rect(turret, x + Tmp.v1.x, y + Tmp.v1.y, rotation - 90);

            if(heatRegion != atlas.find("error")){
                heatDrawer.get(this);
            }
        }
    }
}
