package spec.world.meta;

import arc.*;
import mindustry.world.meta.*;

import java.util.*;

/** Describes one type of stat for content. */
public enum SWStat{
    force;

    public final StatCat category;

    SWStat(StatCat category){
        this.category = category;
    }

    SWStat(){
        this.category = StatCat.general;
    }

    public String localized(){
        return Core.bundle.get("stat." + name().toLowerCase(Locale.ROOT));
    }
}