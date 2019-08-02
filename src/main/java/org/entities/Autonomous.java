package org.entities;

public abstract class Autonomous extends Entity{
    protected int state=0;//Controls various states, such as normal movement and melee movement
    public Autonomous(int subLevel,float spawnX,float spawnY){
        this.subLevel=subLevel;
        x=spawnX;
        y=spawnY;
    }
}
