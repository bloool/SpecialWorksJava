package spec.world.blocks.denfese.turret;

import mindustry.world.blocks.defense.turrets.*;

public class PowerTurret360 extends PowerTurret{

    public boolean constantRotation = false;
    public boolean rotateShooting = false;
    public float rotateVel = 5;

    public PowerTurret360(String name){
        super(name);

        inaccuracy = 360f;
        shootCone = 360;
        shootLength = 0;
        rotateSpeed = 0;
    }

    public class PowerTurret360Build extends PowerTurretBuild{

        @Override
        public void updateTile(){
            super.updateTile();
            if(constantRotation){
                rotation += rotateVel;
            }
        }

        @Override
        public void updateShooting(){
            super.updateShooting();
            if(rotateShooting){
                rotation += rotateVel;
            }
        }
    }
}
