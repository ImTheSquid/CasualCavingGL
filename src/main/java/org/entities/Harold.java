package org.entities;

import org.graphics.Graphics;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

public class Harold extends Entity{

    public void update() {

    }

    public void render() {
        ImageResource harold= ResourceHandler.getHaroldLoader().getHarold();
        //Graphics.drawImage(harold,0,0);
        Graphics.setColor(1,1,1,1f);
        Graphics.drawRect(0,0,0.5f,1);
    }
}
