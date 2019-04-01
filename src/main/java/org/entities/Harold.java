package org.entities;

import com.jogamp.newt.event.KeyEvent;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.World;

public class Harold extends Entity{
    public Harold(){
        health=3;
    }
    public void update() {
        if(Keyboard.keys.contains(KeyEvent.VK_A)){
            vX=-0.6f;
        }
        if(Keyboard.keys.contains(KeyEvent.VK_D)){
            vX=0.6f;
        }

        x+=vX;
        y+=vY;
        //vY-=World.getGravity(); //still need to implement height map
        if(vX>0){
            if(vX-World.getGravity()<0)vX=0;
            else vX-=World.getGravity();
        }else if(vX<0){
            if(vX+World.getGravity()>0)vX=0;
            else vX+=World.getGravity();
        }
    }

    public void render() {
        ImageResource harold= ResourceHandler.getHaroldLoader().getHarold();
        Graphics.setColor(1,1,1,1);
        Graphics.drawImage(harold,x,y);
    }
}
