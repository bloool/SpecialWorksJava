package spec.world.blocks.denfese.turret;

import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class AcceleratingTurret extends ItemTurret {

    public float accSpeed = 0.05f;
    public int accCap = 20;
    public float coolDownSpeed = accSpeed / 5f;

    public AcceleratingTurret(String name){
        super(name);
    }

    public class AcceleratingTurretBuild extends ItemTurretBuild{
        public float accAmount; //making it a increasing value that gets added to local reload var instead of a decreasing global reload time var

        @Override
        public void updateTile(){
            // go back to zero when not shooting
            if(!isShooting())accAmount = Mathf.lerpDelta(accAmount, 0, coolDownSpeed * Time.delta);

            super.updateTile();
        }

        @Override
        public void updateShooting(){
            if(accAmount < accCap)accAmount += accSpeed * Time.delta * baseReloadSpeed();
            reload += accAmount;

            super.updateShooting();
        }
    }
}
