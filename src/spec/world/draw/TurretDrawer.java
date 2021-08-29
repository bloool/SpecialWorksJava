package spec.world.draw;

import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.ItemTurret.*;
import mindustry.world.blocks.defense.turrets.Turret.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import spec.world.blocks.denfese.turret.SWItemTurret.*;

/** An implementation of custom rendering behavior for a block.
 * This is used mostly for mods.
 * And modified to work on my item turret class because anuke made it so it only works with generic crafters*/
public class TurretDrawer{
    protected static final Rand rand = new Rand();

    /** Draws the block. */
    public void draw(TurretBuild build){

    }

    /** Draws any extra light for the block. */
    public void drawLight(TurretBuild build){

    }

    /** Load any relevant texture regions. */
    public void load(Block block){

    }

    /** @return the generated icons to be used for this block. */
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{};
    }
}