package spec.world.draw;

import arc.graphics.g2d.*;

import static arc.graphics.g2d.Lines.circleVertices;

public class SWDraw{
    public static void FillCircle(float x, float y, float rad){
        Fill.poly(x, y, circleVertices(rad), rad);
    }
}
