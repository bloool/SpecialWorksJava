package spec.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.Turret.*;

import static arc.Core.atlas;

public class BaseBarrelDrawer extends TurretDrawer{
    public void draw(TurretBuild build, float elevation, TextureRegion region, TextureRegion baseRegion){
        Draw.rect(baseRegion, build.x, build.y);
        Draw.color();

        Draw.z(Layer.turret);

        Tmp.v1.trns(build.rotation, -build.recoil);

        Drawf.shadow(region, build.x + Tmp.v1.x - elevation, build.y + Tmp.v1.y - elevation, build.rotation - 90);
        Draw.rect(region, build.x + Tmp.v1.x, build.y + Tmp.v1.y, build.rotation - 90);
    }
    public TextureRegion[] icons(Block block, TextureRegion baseRegion){
        return new TextureRegion[]{
        baseRegion,
        atlas.find(block.name)
        };
    }
}
