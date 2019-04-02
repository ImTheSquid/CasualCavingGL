package org.level;

import org.loader.ImageResource;

public abstract class Level {
    protected int subLevels;
    protected boolean decreaseAllowed=true,increaseAllowed=true;
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
}
