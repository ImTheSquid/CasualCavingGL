package org.level;

import org.graphics.Render;
import org.loader.ImageResource;

public abstract class Level {
    protected int subLevels;
    protected boolean decreaseAllowed=true,increaseAllowed=true;
    protected float leftBound=0,rightBound=Render.unitsWide;
    protected ImageResource[] backgrounds,foregrounds;

    public Level(ImageResource[] backgrounds,int subLevels){
        this.backgrounds=backgrounds;
        this.subLevels=subLevels;
    }

    public abstract void update(int subLevel);

    public abstract void render(int subLevel);

    public abstract void reset();

    public void setForegrounds(ImageResource[] foregrounds){
        this.foregrounds=foregrounds;
    }

    public int getSublevels(){
        return subLevels;
    }

    public boolean isDecreaseAllowed() {
        return decreaseAllowed;
    }

    public boolean isIncreaseAllowed() {
        return increaseAllowed;
    }

    public float getLeftBound() {
        return leftBound;
    }

    public float getRightBound() {
        return rightBound;
    }
}
