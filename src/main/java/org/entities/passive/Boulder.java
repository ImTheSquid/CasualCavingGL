package org.entities.passive;

import org.entities.Autonomous;
import org.graphics.Graphics;
import org.loader.ResourceHandler;

public class Boulder extends Autonomous {
    private float rotation=0;
    public Boulder(float spawnX, float spawnY) {
        super(4, spawnX, spawnY);
    }

    @Override
    public void update() {
        rotation+=vX;
    }

    @Override
    public void render() {
        Graphics.setRotation(rotation);
        Graphics.drawImageCentered(ResourceHandler.getMiscLoader().getBoulder(),x,y);
        Graphics.setRotation(0);
    }

    @Override
    public void reset() {

    }
}
