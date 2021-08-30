// suppress inspection "Statement lambda can be replaced with expression lambda" for whole file

package spec.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.content.TechTree.*;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;
import mindustry.world.*;

import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static mindustry.content.TechTree.*;
import static mindustry.content.UnitTypes.*;
import static spec.content.SWBlocks.*;

public class SWTechTree implements ContentList{
    //Don't mind me I'ma just yoink some stuff from Prog Mats, which is yoinked from beta mindy
    static TechTree.TechNode context = null;

    @Override
    public void load(){
        vanillaNode(salvo, () -> {
            node(discharge, Seq.with(new SectorComplete(SectorPresets.tarFields)));
        });

        vanillaNode(ripple, () -> {
            node(ridge, Seq.with(new SectorComplete(SectorPresets.windsweptIslands)), () -> {
                node(shogun, Seq.with(new SectorComplete(SectorPresets.nuclearComplex)));
            });
        });

        vanillaNode(meltdown, () -> {
            node(antares, Seq.with(new SectorComplete(SectorPresets.nuclearComplex)));
        });

        vanillaNode(scatter, () -> {
            node(gale, Seq.with(new SectorComplete(SectorPresets.saltFlats)));
        });
    }


    private static ItemStack[] brq(Block content){
        return content.researchRequirements();
    }

    private static void vanillaNode(UnlockableContent parent, Runnable children){
        context = TechTree.get(parent);
        children.run();
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children){
        TechNode node = new TechNode(context, content, requirements);
        if(objectives != null) node.objectives = objectives;

        TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives){
        node(content, requirements, objectives, () -> {});
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives){
        node(content, content.researchRequirements(), objectives, () -> {});
    }

    private static void node(UnlockableContent content, ItemStack[] requirements){
        node(content, requirements, Seq.with(), () -> {});
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, children);
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives, children);
    }

    private static void node(UnlockableContent content, Runnable children){
        node(content, content.researchRequirements(), children);
    }

    private static void node(UnlockableContent block){
        node(block, () -> {});
    }

    private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives.and(new Produce(content)), children);
    }

    private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives){
        nodeProduce(content, objectives, () -> {});
    }

    private static void nodeProduce(UnlockableContent content, Runnable children){
        nodeProduce(content, Seq.with(), children);
    }

    private static void nodeProduce(UnlockableContent content){
        nodeProduce(content, Seq.with(), () -> {});
    }
}