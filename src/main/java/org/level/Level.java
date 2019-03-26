package org.level;

import org.loader.ImageResource;

public class Level {
    private ImageResource[] backgrounds;

    public Level(ImageResource[] backgrounds){
        this.backgrounds=backgrounds;
    }

    public void update(){/*Implement in subclass*/}

    public void render(){/*Implement in subclass*/}

}
