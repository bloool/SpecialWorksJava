package spec.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.world.*;

import static arc.graphics.g2d.Lines.*;
import static mindustry.Vars.*;

public class SWDraw{
    public static void FillCircle(float x, float y, float rad){
        Fill.poly(x, y, circleVertices(rad), rad);
    }
}
