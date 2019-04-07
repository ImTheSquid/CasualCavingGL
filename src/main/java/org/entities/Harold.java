package org.entities;

import com.jogamp.newt.event.KeyEvent;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.HeightMap;
import org.world.HeightReturn;
import org.world.World;

public class Harold extends Entity{
    private Animator haroldAnimator=new Animator(ResourceHandler.getHaroldLoader().getHaroldWalk(),12);
    private ImageResource harold;
    private boolean jump=false;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);

    public Harold(){
        health=3;
    }
    public void update() {
        HeightReturn h=HeightMap.onGround(hitbox);
        if(Keyboard.keys.contains(KeyEvent.VK_A)){
            vX=-0.5f;
        }
        if(Keyboard.keys.contains(KeyEvent.VK_D)){
            vX=0.5f;
        }
        if(Keyboard.keys.contains(KeyEvent.VK_SPACE)&&!jump) {
            if(h.isOnGround()) {
                vY = 2.5f;
                jump=true;
            }
        }else if(!Keyboard.keys.contains(KeyEvent.VK_SPACE)&&jump){
            jump=false;
            if(vY<-.5f){
                vY=-.5f;
            }
        }

        y+=vY;
        vY-=World.getGravity();

        //X-velocity stuff
        boolean doXCalc=true;

        if (HeightMap.checkRightCollision(hitbox)) {
            if (x + width + 0.5>= HeightMap.findApplicable(x,true).getStartX()) {
                if (vX < 0) x += vX;
                else vX=0;
                doXCalc=false;
            }
        }
        if(HeightMap.checkLeftCollision(hitbox)){
            if(x-0.5<=HeightMap.findApplicable(x,false).getEndX()){
                if(vX>0)x+=vX;
                else vX=0;
                doXCalc=false;
            }
        }

        if(doXCalc){
            x+=vX;
            doXCalc();
        }


        if(h.isOnGround()&&!jump){
            y=h.getGroundLevel();
            vY=0;
            jump=false;
        }else if(h.isOnGround()&&jump){
            vY=2.5f;
        }

        if(vX==0){
            harold= ResourceHandler.getHaroldLoader().getHarold();
        }else{
            harold=haroldAnimator.getCurrentFrame();
        }

        Level currentLevel=LevelController.getLevels()[World.getLevel()];
        if(x<currentLevel.getLeftLimit())x=currentLevel.getLeftLimit();
        if(x+width>currentLevel.getRightLimit())x=currentLevel.getRightLimit()-width;
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
        haroldAnimator.setFrames(ResourceHandler.getHaroldLoader().getHaroldWalk());
        haroldAnimator.update();
    }

    private void doXCalc(){
        if(vX>0){
            ResourceHandler.getHaroldLoader().setDirection(true);
            if(vX-World.getGravity()<0)vX=0;
            else vX-=World.getGravity();
        }else if(vX<0){
            ResourceHandler.getHaroldLoader().setDirection(false);
            if(vX+World.getGravity()>0)vX=0;
            else vX+=World.getGravity();
        }
    }

    public void render() {
        width=Graphics.convertToWorldWidth(harold.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(harold.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        Graphics.setColor(1,1,1,1);
        Graphics.drawImage(harold,x,y);
    }
}
