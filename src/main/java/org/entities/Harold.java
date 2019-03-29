package org.entities;

import com.jogamp.newt.event.KeyEvent;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

public class Harold extends Entity{

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
        Graphics.drawImage(harold,0,0);
    }
}
