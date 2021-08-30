package spec.world.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.Turret.*;
import spec.libs.*;

public class ShineDrawer extends TurretDrawer{
    public Color shineColor = Palf.starMid;
    public float lightRadius = 60f, lightAlpha = 0.65f, lightSinScl = 10f, lightSinMag = 5;
    public float shineScl = 11f, shineMag = 7f, shineSize = 16f;

    public ShineDrawer(){
    }

    public ShineDrawer(Color shineColor){
        this.shineColor = shineColor;
    }

    public void draw(TurretBuild build){
        float shootVar = 0;
        shootVar = Mathf.lerpDelta(shootVar, isShooting(build) ? 3 : 1, 0.06f);

        float size = build.power.status <= 0 ? 0 : shineSize + Mathf.absin(Time.time, shineScl + 1, shineMag - 1) * shootVar * build.power.status;
        float angle = Mathf.absin(Time.time, shineScl, shineMag) + 43;

        Draw.z(Layer.effect);

        Draw.color(shineColor);
        for(int i = 0; i < 4; i++){
            Drawf.tri(build.x, build.y, size/4, size, build.rotation + angle + i*90);
        }

        Draw.color(Color.white);
        for(int i = 0; i < 4; i++){
            Drawf.tri(build.x, build.y, size/4/2, size/2, build.rotation + angle + i*90);
        }
    }
    @Override
    public void drawLight(TurretBuild build){
        Drawf.light(build.team, build.x, build.y, (lightRadius + Mathf.absin(lightSinScl, lightSinMag)) * build.block.size, shineColor, lightAlpha);
    }
}
