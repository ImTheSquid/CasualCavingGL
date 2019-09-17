package org.entities.passive;

import org.engine.Main;
import org.entities.Autonomous;
import org.entities.Entity;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.level.LevelController;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_E;
import static com.jogamp.newt.event.KeyEvent.VK_Q;

public class Boulder extends Autonomous {
    private float rotation=0;
    private boolean isDone, townOK;
    private FadeIO falterTimer=new FadeIO(0,1,0,1,1);
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
        if(falterTimer.getCurrent()==falterTimer.getMax()){
            if(Math.random()<0.33){
                //do falter
                System.out.println("FALTER!");
            }
            falterTimer.setCurrent(0);
        }
        float width=Graphics.convertToWorldWidth(LevelController.getCurrentLevel().getBackgrounds()[World.getSubLevel()].getTexture().getWidth());
        if(Render.getCameraX()<width-100)Render.setCameraX(Render.getCameraX()+.12f);
        if(Render.getCameraY()>-Graphics.convertToWorldHeight(700)){
            if(Render.getCameraX()>5){
                Render.setCameraY(Render.getCameraY()-.09f);
                y-=0.09;
            }
            vX=.1f;
        }else {
            vX=0.05f;
        }
        Main.getHarold().setMovement(false);
        Main.getHarold().setHarold(haroldPuppet.getCurrentFrame());
        Main.getHarold().setX(x);
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
    }

    @Override
    public void reset() {
        x=73;
        y=16;
        state=0;
        isDone=false;
        townOK=true;
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
