package org.entities.passive;

import org.engine.Main;
import org.entities.Autonomous;
import org.entities.Entity;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.graphics.Render;
import org.graphics.Timer;
import org.input.Keyboard;
import org.level.LevelController;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_E;
import static com.jogamp.newt.event.KeyEvent.VK_Q;

public class Boulder extends Autonomous {
    private float rotation=0;
    private boolean isDone, townOK, isFaltering=false;
    //Defines key that needs to be pressed during quick time events
    private short quickKey=0;
    private Timer falterTimer=new Timer(0,1,0,1,1);
    private Timer deathTimer=new Timer(0,2,0,1,1);
    //Animator that controls Harold during this minigame
    private Animator haroldPuppet=new Animator(ResourceHandler.getHaroldLoader().getBoulder(),12);
    public Boulder() {
        super(4,73,16);
        invincible=true;
    }

    @Override
    public void update() {
        switch(state){
            case -1:
                vX=0;
            case 0:
                vX=0;
                if(Keyboard.keys.contains(VK_Q)){
                    state=1;
                }else if(Keyboard.keys.contains(VK_E)){
                    state=2;
                }
                break;
            case 1:doMinigame();
            break;
            case 2:
                vX=.2f;
                if(x>82){
                    y-=0.09;
                }
                if(x>120){
                    state=4;
                }
                break;
            case 3:
                isDone=true;
                Render.setCameraX(0);
                Render.setCameraY(0);
                state=-1;
                break;
            case 4:
                World.getMaster().setDirection(false);
                World.setMasterColor(1,1,1);
                World.getMaster().setActive(true);
                if(World.getMaster().getCurrent()==0){
                    state=5;
                    Main.getHarold().setX(45);
                    isDone=true;
                    townOK=false;
                    vX=0;
                    World.getMaster().setSecondDelay(1);
                }
                break;
            case 5:
                World.getMaster().setDirection(true);
                if(World.getMaster().getCurrent()==1)state =-1;
                break;
        }
        rotation+=vX*Math.PI;
        x+=vX;
    }

    private void doMinigame(){
        haroldPuppet.update();
        Main.getHarold().setFollowCamera(false);
        falterTimer.setActive(true);
        falterTimer.update();
        deathTimer.setActive(isFaltering);
        deathTimer.update();
        if(falterTimer.getCurrent()==falterTimer.getMax()&&!isFaltering&&x>100){
            if(Math.random()<0.33){
                //Do falter
                isFaltering=true;
                haroldPuppet.setFrames(ResourceHandler.getHaroldLoader().getFalter());
                haroldPuppet.setFps(40);
                quickKey=(short)(Math.random()*26);
            }
            falterTimer.setCurrent(0);
        }else if(!isFaltering){
            haroldPuppet.setFrames(ResourceHandler.getHaroldLoader().getBoulder());
            haroldPuppet.setFps(12);
        }
        float width=Graphics.convertToWorldWidth(LevelController.getCurrentLevel().getBackgrounds()[World.getSubLevel()].getTexture().getWidth());
        if(!isFaltering) {
            Main.getHarold().setMovement(false);
            if (Render.getCameraX() < width - 100) Render.setCameraX(Render.getCameraX() + .12f);
            if (Render.getCameraY() > -Graphics.convertToWorldHeight(700)) {
                if (Render.getCameraX() > 5) {
                    Render.setCameraY(Render.getCameraY() - .09f);
                    y -= 0.09;
                }
                vX = .1f;
            } else {
                vX = 0.05f;
            }
        }else{
            if(deathTimer.getCurrent()==deathTimer.getMax()){
                Main.getHarold().setHealth(0);
                Main.getHarold().setMovement(true);
            }
            if(Keyboard.keys.contains((short)(65+quickKey))){
                isFaltering=false;
                deathTimer.setCurrent(0);
            }
            vX=0;
        }
        Main.getHarold().setHarold(haroldPuppet.getCurrentFrame());
        if(!isFaltering)Main.getHarold().setX(x);
        else Main.getHarold().setX(x+offsetFumble(haroldPuppet.getCurrentFrameNum()));
        Main.getHarold().setY(y-9);
        if(Render.getCameraX()>=width-100&&Render.getCameraY()<=-Graphics.convertToWorldHeight(700)){
            Main.getHarold().setMovement(true);
            Main.getHarold().setX(50);
            x=50;
            y=16;
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
            ResourceHandler.getHaroldLoader().setDirection(true);
            vX=0;
            state=3;
        }
    }

    private float offsetFumble(int frame){
        float val=0;
        switch(frame){
            case 0:val=2;
                break;
            case 1:val=8;
                break;
            case 2:val=16;
                break;
        }
        return -Graphics.convertToWorldWidth(val);
    }

    @Override
    public void render() {
        if(!townOK)return;
        if(state==1)Graphics.setFollowCamera(false);
        else Graphics.setFollowCamera(false);
        Graphics.setRotation(rotation);
        Graphics.drawImageCentered(ResourceHandler.getMiscLoader().getBoulder(),x,y);
        Graphics.setRotation(0);
        Graphics.setFollowCamera(false);
        Graphics.setFont(Graphics.SMALL);
        if(state==0&& Main.getHarold().getHitbox().intersects(new SmartRectangle(x-4.5f,y-4.5f,9,9)))Graphics.drawText("'Q' to guide downwards\\n'E' to push",x-10,30,30,true);
        if(isFaltering){
            quickTime();
        }
    }

    private void quickTime(){
        Graphics.drawTextWithBox(codeToKey(quickKey)+" to save!",x+6,y+12);
    }

    private char codeToKey(short in){
        String alpha="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return alpha.charAt(in);
    }

    @Override
    public void reset() {
        x=73;
        y=16;
        state=0;
        isDone=false;
        townOK=true;
        isFaltering=false;
        falterTimer.setCurrent(0);
        deathTimer.setCurrent(0);
    }

    @Override
    public void doDamage(Entity attacker, int damage) {}

    public boolean isDone() {
        return isDone;
    }

    public boolean isTownOK() {
        return townOK;
    }
}
