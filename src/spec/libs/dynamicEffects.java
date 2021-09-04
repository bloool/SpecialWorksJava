package spec.libs;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.entities.*;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;

public class dynamicEffects{
    public static Effect circleBoom(float rad, Color boomColor, float lifetime, float clipsize){
        return new Effect(lifetime, clipsize, e -> {
            e.scaled(14f, b -> {
                color(boomColor, e.fout());
                Fill.circle(e.x, e.y, rad * b.fout());
            });

            color(boomColor);

            stroke(e.fout() * 3f);
            Lines.circle(e.x, e.y, rad);

            Fill.circle(e.x, e.y, rad * 0.2f * e.fout());
            color();
            Fill.circle(e.x, e.y, rad * 0.1f * e.fout());
            Drawf.light(e.x, e.y, rad * 1.6f, boomColor, e.fout());
        });
    }

    public static Effect circleBoom(float rad, Color boomColor, float lifetime){
        return circleBoom(rad, boomColor, lifetime, rad * 2);
    }

    public static Effect circleBoom(float rad, Color boomColor){
        return circleBoom(rad, boomColor, 30);
    }


    public static Effect implodeWave(float rad, Color waveColor, float width, float lifetime, float clipsize){
        return new Effect(lifetime, clipsize, e -> {
            Lines.stroke(width * e.fin(), waveColor);
            Lines.circle(e.x, e.y, rad * e.fout());
        });
    }

    public static Effect implodeWave(float rad, Color waveColor){ return implodeWave(rad, waveColor, 6, 20, rad); }
}
