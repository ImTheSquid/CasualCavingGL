package org.entities;

import com.jogamp.newt.event.KeyEvent;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

public class Harold extends Entity{
    public Harold(){
        health=3;
    }
    public void update() {
        if(Keyboard.keys.contains(KeyEvent.VK_A)){
            vX=-1;
        }
        if(Keyboard.keys.contains(KeyEvent.VK_D)){
            vX=1;
        }
    }

    public void render() {
        ImageResource harold= ResourceHandler.getHaroldLoader().getHarold();
        Graphics.setColor(1,1,1,1);
        Graphics.drawImage(harold,x,y);
        Graphics.setColor(1,1,1,1);
        Graphics.setFont(Graphics.TITLE_FONT);
        Graphics.drawRect(20,20,20,20);
    }
}
