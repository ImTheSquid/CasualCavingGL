package org.entities;

import com.jogamp.newt.event.KeyEvent;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.World;

public class Harold extends Entity{
    private Animator haroldAnimator=new Animator(ResourceHandler.getHaroldLoader().getHaroldWalk(),20);

    public Harold(){
        health=3;
    }
    public void update() {
        if(Keyboard.keys.contains(KeyEvent.VK_A)){
            vX=-0.5f;
        }
        if(Keyboard.keys.contains(KeyEvent.VK_D)){
            vX=0.5f;
        }

        x+=vX;
        y+=vY;
        //vY-=World.getGravity(); //still need to implement height map
        if(vX>0){
            ResourceHandler.getHaroldLoader().setDirection(true);
            if(vX-World.getGravity()<0)vX=0;
            else vX-=World.getGravity();
        }else if(vX<0){
            ResourceHandler.getHaroldLoader().setDirection(false);
            if(vX+World.getGravity()>0)vX=0;
            else vX+=World.getGravity();
        }

        Level currentLevel=LevelController.getLevels()[World.getLevel()];
        if(x<currentLevel.getLeftBound()){
            if(!currentLevel.isDecreaseAllowed())x=currentLevel.getLeftBound();
            else if(World.getSubLevel()>0){
                World.setSubLevel(World.getSubLevel()-1);
                x=currentLevel.getRightBound()-15;
            }
            else x=currentLevel.getLeftBound();
        }
        if(x+width> currentLevel.getRightBound()){
            if(!currentLevel.isIncreaseAllowed()) x=currentLevel.getRightBound()-width;
            else if(World.getSubLevel()< currentLevel.getSublevels()-1){
                World.setSubLevel(World.getSubLevel()+1);
                x=5;
            }else{
                x=currentLevel.getRightBound()-width;
            }
        }

    }

    public void render() {
        ImageResource harold;
        if(vX==0){
            harold= ResourceHandler.getHaroldLoader().getHarold();
        }else{
            harold=haroldAnimator.getCurrentFrame();
        }
        width=Graphics.convertToWorldWidth(harold.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(harold.getTexture().getHeight());
        Graphics.setColor(1,1,1,1);
        Graphics.drawImage(harold,x,y);
    }
}
