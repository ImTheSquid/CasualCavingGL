package org.entities.aggressive;

import org.entities.Autonomous;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.HeightMap;
import org.world.HeightReturn;

public class BlueGolem extends Autonomous {
    private Animator golemAnimator=new Animator(ResourceHandler.getGolemLoader().getBlueGolemWalk(direction),10);
    private ImageResource blueGolem;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    BlueGolem(boolean lockHeightVal){super(lockHeightVal);}
    @Override
    public void update() {
        HeightReturn h= HeightMap.onGround(hitbox);

        //Action input
        if(direction){
            vX=.25f;
        }else{
            vX=-.25f;
        }

        //Calculations
        if(vX>0)direction=true;
        else if(vX<0)direction=false;
        if(vX!=0) {
            golemAnimator.update();
        }else{
            blueGolem=golemAnimator.getCurrentFrame();
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void reset() {

    }
}
