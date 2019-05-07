package org.entities;

public abstract class Autonomous extends Entity{
    boolean lockHeightVal;//Whether to stay on current HeightVal
    protected int state=0;//Controls various states, such as normal movement and attack movement
    public Autonomous(boolean lockHeightVal){
        this.lockHeightVal=lockHeightVal;
    }
}
