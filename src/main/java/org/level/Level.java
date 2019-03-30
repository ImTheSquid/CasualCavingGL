package org.level;

import org.loader.ImageResource;

public abstract class Level {
    protected ImageResource[] backgrounds,foregrounds;

    public Level(ImageResource[] backgrounds){
        this.backgrounds=backgrounds;
    }

    public abstract void update(int subLevel);

    public abstract void render(int subLevel);

    public void setForegrounds(ImageResource[] foregrounds){
        this.foregrounds=foregrounds;
    }

}
