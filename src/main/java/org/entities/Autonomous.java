package org.entities;

public abstract class Autonomous extends Entity{
    protected boolean lockHeightVal;//Whether to stay on current HeightVal
    public Autonomous(boolean lockHeightVal){
        this.lockHeightVal=lockHeightVal;
    }
}
