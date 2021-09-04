package spec.world.blocks.denfese;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.Units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import spec.libs.*;
import spec.world.draw.*;

import static arc.Core.atlas;
import static mindustry.Vars.*;

public class AnchorProjector extends Block{
    public final int timerUse = timers++;

    public float consumeAmount = 60f;
    public Color baseColor = Pal.shield;
    public Color phaseColor = baseColor;
    public float phaseBoost = 12f;
    public float phaseRangeBoost = 50f;

    public float breakRange = 80f;
    public float range = breakRange * 0.8f;
    public float force = 20f;
    public boolean targetAir = true;
    public boolean targetGround = false;
    public Effect breakEffect = Fx.hitLancer;

    public TextureRegion topRegion;

    public Sortf unitSort = (u, x, y) -> -u.maxHealth + Mathf.dst2(u.x, u.y, x, y) / 6400f;

    public AnchorProjector(String name){
        super(name);
        solid = true;
        update = true;
        group = BlockGroup.projectors;
        hasPower = true;
        hasItems = true;
        emitLight = true;
        lightRadius = range;
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
        stats.add(Stat.boostEffect, phaseRangeBoost / tilesize, StatUnit.blocks);
        stats.add(Stat.boostEffect, (phaseBoost + force) / force, StatUnit.timesSpeed);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, breakRange, baseColor);
    }

    @Override
    public void load(){
        super.load();

        topRegion = atlas.find(name + "-top");
    }

    public class AnchorBuild extends Building implements Ranged{
        float phaseHeat = 0;
        public @Nullable Posc target;
        public Vec2 targetPos = new Vec2();
        public float targetDst;

        public float realRange = 0;
        public float realBreakRange = 0;

        @Override
        public float range(){
            return range;
        }

        @Override
        public void updateTile(){
            phaseHeat = Mathf.lerpDelta(phaseHeat, Mathf.num(cons.optionalValid()), 0.1f);

            targetDst = Mathf.dst(x, y, targetPos.x, targetPos.y);

            realRange = cons.valid() ? Mathf.lerpDelta(realRange, range - (phaseHeat * phaseRangeBoost), 0.1f) : 0;
            realBreakRange = cons.valid() ? Mathf.lerpDelta(realBreakRange, breakRange + (phaseHeat * phaseRangeBoost) * 0.2f, 0.1f) : 0;

            if(cons.optionalValid() && timer(timerUse, consumeAmount) && efficiency() > 0){
                consume();
                dynamicEffects.implodeWave(realBreakRange, cons.optionalValid() ? phaseColor : baseColor).at(x, y);
            }

            findTarget();
            if(target != null && cons.valid()){
                targetPos.set(target);
                if(target instanceof Unit u){
                    bind(u);
                }
            }
        }

        protected void findTarget(){
            if(targetAir && !targetGround){
                target = Units.bestEnemy(team, x, y, realBreakRange + 5, e -> !e.dead() && !e.isGrounded(), unitSort);
            }else{
                target = Units.bestTarget(team, x, y, realBreakRange + 5, e -> !e.dead() && (e.isGrounded() || targetAir) && (!e.isGrounded() || targetGround), b -> true, unitSort);
            }
        }

        protected void bind(Unit u){
            Tmp.v1.trns(Angles.angle(u.x, u.y, x, y), (force + (phaseHeat * phaseBoost)) * Time.delta);

            if(!u.within(x, y, realRange)){
                u.impulse(Tmp.v1);
            }else if(targetDst > realBreakRange){
                breakEffect.at(targetPos);
            }
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.config) return targetDst;
            return super.sense(sensor);
        }

        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, realBreakRange, baseColor);
        }

        @Override
        public void draw(){
            super.draw();
            if(cons.valid()){
                float sizeSin = Mathf.absin(7f, 3f);
                float alpha = Core.settings.getBool("animatedshields") ? 0f : 0.6f;

                Draw.alpha(Mathf.absin(Time.time, 10f, 1f) * 0.5f);
                Draw.rect(topRegion, x, y);

                Draw.z(Layer.shields);

                Draw.color(cons.optionalValid() ? phaseColor : baseColor);

                if(target != null){
                    Lines.stroke((targetDst - realRange) / breakRange + sizeSin);
                    Lines.line(x, y, targetPos.x, targetPos.y);
                }

                if(!Core.settings.getBool("animatedshields")){
                    Draw.alpha(realRange / range - alpha);
                    Lines.stroke(5);
                    Lines.circle(x, y, realRange + sizeSin);
                }

                Draw.alpha(realBreakRange / breakRange - alpha);
                Lines.stroke(5);
                Lines.circle(x, y, realBreakRange + sizeSin);

                Draw.alpha(1 - alpha);
                SWDraw.FillCircle(x, y, realRange + sizeSin);
            }
            Draw.reset();
        }

        @Override
        public void drawLight(){
            Drawf.light(team, x, y, realBreakRange, baseColor, 0.7f);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(phaseHeat);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            phaseHeat = read.f();
        }
    }
}