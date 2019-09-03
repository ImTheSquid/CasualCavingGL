package org.entities.passive;

import org.entities.Autonomous;
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
        Graphics.setRotation(rotation);
        Graphics.drawImageCentered(ResourceHandler.getMiscLoader().getBoulder(),x,y);
        Graphics.setRotation(0);
    }

    @Override
    public void reset() {

    }
}
