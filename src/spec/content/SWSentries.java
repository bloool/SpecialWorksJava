package spec.content;


import arc.func.*;
import arc.graphics.*;
import arc.struct.*;
import arc.struct.ObjectMap.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import spec.entities.units.SentryUnitType;
import spec.entities.units.entity.SentryEntity;

@SuppressWarnings("unchecked")
public class SWSentries implements ContentList {
    //Stolen from Progressed Materials in the present which stole from Endless Rusting which stole from Progressed Materials in the past which stole from BetaMindy, peak java modding
    private static final Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
            prov(SentryEntity.class, SentryEntity::new),
    };

    private static final ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

    /**
     * Internal function to flatmap {@code Class -> Prov} into an {@link Entry}.
     * @author GlennFolker
     */
    private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov){
        Entry<Class<T>, Prov<T>> entry = new Entry<>();
        entry.key = type;
        entry.value = prov;
        return entry;
    }

    /**
     * Setups all entity IDs and maps them into {@link EntityMapping}.
     * @author GlennFolker
     */

    private static void setupID(){
        for(
                int i = 0,
                j = 0,
                len = EntityMapping.idMap.length;

                i < len;

                i++
        ){
            if(EntityMapping.idMap[i] == null){
                idMap.put(types[j].key, i);
                EntityMapping.idMap[i] = types[j].value;

                if(++j >= types.length) break;
            }
        }
    }

    /**
     * Retrieves the class ID for a certain entity type.
     * @author GlennFolker
     */
    public static <T extends Entityc> int classID(Class<T> type){
        return idMap.get(type, -1);
    }

    public static UnitType

    copperSentry;

    @Override
    public void load(){
        setupID();

        EntityMapping.nameMap.put("copper-sentry", SentryEntity::new);
        copperSentry = new SentryUnitType("copper-sentry"){{
            turretReload = 10f;
        }};
    }
}
