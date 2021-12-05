package spec.entities.units.entity;


import arc.math.*;
import arc.util.io.*;
import mindustry.gen.*;
import spec.content.SWSentries;
import spec.content.SWUnitTypes;
import spec.entities.units.SentryUnitType;

public class SentryEntity extends UnitEntity{
    public float duration;

    public float durationf(){
        return duration / ((SentryUnitType)type).duration;
    }

    public void clampDuration(){
        duration = Mathf.clamp(duration, 0f, ((SentryUnitType)type).duration);
    }

    @Override
    public boolean damaged(){
        return false; //Never view as damaged, healing will not target this.
    }

    @Override
    public int cap(){
        return count() + 1;
    }

    @Override
    public float prefRotation(){
        return rotation();
    }

    @Override
    public void write(Writes write){
        super.write(write);
        write.f(duration);
    }

    @Override
    public void read(Reads read){
        super.read(read);
        duration = read.f();
    }

    @Override
    public int classId(){
        return SWSentries.classID(SentryEntity.class);
    }
}