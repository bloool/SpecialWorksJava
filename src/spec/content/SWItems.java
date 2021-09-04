package spec.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.type.*;

public class SWItems implements ContentList{
    public static Item
    laminaglass;

    @Override
    public void load(){
        laminaglass = new Item("lamina-glass", Color.valueOf("B0BAC0")){{
            cost = 1.6f;
        }};
    }
}
