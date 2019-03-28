package org.entities;

import org.graphics.Graphics;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

public class Harold extends Entity{

    public void update() {

    }

    public void render() {
        ImageResource harold= ResourceHandler.getHaroldLoader().getHarold();
        Graphics.drawImage(harold,0,0);
        Graphics.setColor(1,1,1,1f);
        Graphics.setText(Graphics.TITLE_FONT);
        Graphics.drawText("Hello",20,20);
        Graphics.setText(Graphics.REGULAR_FONT);
        Graphics.drawText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla mollis dui eget auctor bibendum. In hac habitasse platea dictumst. Suspendisse facilisis facilisis purus, vel vestibulum est porta quis. Morbi commodo rutrum diam, at rhoncus risus tempus ac.",50,40,300);
        //Graphics.drawRect(0,0,0.5f,1);
    }
}
