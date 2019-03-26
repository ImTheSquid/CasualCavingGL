package org.level;

import org.loader.ImageResource;

public abstract class Level {
    private ImageResource[] backgrounds;

    public Level(ImageResource[] backgrounds){
        this.backgrounds=backgrounds;
    }

    public abstract void update();

    public abstract void render();

}
