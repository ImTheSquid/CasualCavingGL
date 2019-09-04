package org.entities.passive;

import org.entities.Autonomous;
import org.entities.Entity;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.loader.ResourceHandler;

public class Boulder extends Autonomous {
    private float rotation=0;
    private FadeIO falterTimer=new FadeIO(0,2,0,1,1);
    public Boulder(float spawnX, float spawnY) {
        super(4, spawnX, spawnY);
    }

    @Override
    public void update() {
        if(state==1)doMinigame();
    }

    private void doMinigame(){
        falterTimer.update();
        if(falterTimer.getCurrent()==falterTimer.getMax()){
            if(Math.random()<0.15){
                //do falter
            }
            falterTimer.setCurrent(0);
        }
        rotation+=vX;
    }

    @Override
    public void render() {
        if(state==1)Graphics.setFollowCamera(true);
        else Graphics.setFollowCamera(false);
        Graphics.setRotation(rotation);
        Graphics.drawImageCentered(ResourceHandler.getMiscLoader().getBoulder(),x,y);
        Graphics.setRotation(0);
        Graphics.setFollowCamera(false);
        Graphics.drawText("Hello, this\\nis a test to see if this works",x-10,y,30,true);
    }

    @Override
    public void reset() {

    }

    @Override
    public void doDamage(Entity attacker, int damage) {}
}
