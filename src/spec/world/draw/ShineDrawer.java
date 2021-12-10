package spec.world.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.blocks.defense.turrets.Turret.*;
import spec.libs.*;

public class ShineDrawer extends TurretDrawer{

    public float lightRadius = 60f, lightAlpha = 0.65f, lightSinScl = 10f, lightSinMag = 5;

    public float shineScl = 22f, shineMag = 8f, shineSize = 16f;
    public Color shineColor = Palf.starMid, innerShineColor = Color.white;
    public int spikeAmount = 4;
    public boolean drawCircle = true;
    public boolean rotatingShine = false;

    public float angleScl = 11f, angleMag = 7f;
    public float angleOffset = 0f;

    public ShineDrawer(){}

    public ShineDrawer(Color shineColor){
        this.shineColor = shineColor;
    }

    public void draw(TurretBuild build){
        float size = build.power.status <= 0 ? 0 : shineSize + Mathf.absin(Time.time, shineScl, shineMag);
        float angle = Mathf.absin(Time.time, angleScl, angleMag) - angleMag * 0.5f + angleOffset;
        float width = size * 0.25f;

        Draw.z(Layer.effect);

        Draw.color(shineColor);
        for(int i = 0; i < spikeAmount; i++){
            Drawf.tri(build.x, build.y, width, size, build.rotation + angle + (i * (360f / spikeAmount)));
        }

        if(drawCircle){
            Draw.color(shineColor);
            SWDraw.FillCircle(build.x, build.y, width * 0.75f);

            Draw.color(innerShineColor);
            SWDraw.FillCircle(build.x, build.y, width * 0.35f);
        }

        Draw.color(innerShineColor);
        for(int i = 0; i < spikeAmount; i++){
            Drawf.tri(build.x, build.y, width * 0.5f, size * 0.5f, build.rotation + angle + (i * (360f / spikeAmount)));
        }

        if(rotatingShine){
            Draw.color(innerShineColor.cpy().a(0.7f));
            for(int i = 0; i < spikeAmount; i++){
                Drawf.tri(build.x, build.y, width * 0.5f, size * 0.75f,Time.time + angle + (i * (360f / spikeAmount)));
            }
        }
    }

    @Override
    public void drawLight(TurretBuild build){
        Drawf.light(build.team, build.x, build.y, (lightRadius + Mathf.absin(lightSinScl, lightSinMag)) * build.block.size, shineColor, lightAlpha);
    }
}
