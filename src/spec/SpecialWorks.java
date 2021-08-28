package spec;

import arc.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import spec.content.*;

public class SpecialWorks extends Mod{
    private final ContentList[] content = {
    new SWBullets(),
    new SWBlocks(),
    };

    public SpecialWorks(){
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("this stupid mod was made by an even more stupid dev").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("spec-j-frog")).pad(20f).row();
                dialog.cont.button("ok :)", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent(){
        for(ContentList list : content){
            list.load();

            Log.info("@: Loaded content list: @", getClass().getSimpleName(), list.getClass().getSimpleName());
        }
    }
}
