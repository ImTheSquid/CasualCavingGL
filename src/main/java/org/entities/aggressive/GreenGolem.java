package org.entities.aggressive;

import org.entities.Autonomous;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

public class GreenGolem extends Autonomous {
    private Animator golemAnimator=new Animator(ResourceHandler.getGolemLoader().getGreenGolemWalk(direction),10);
    private ImageResource greenGolem;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    public GreenGolem(int subLevel,float spawnX,float spawnY){
        super(subLevel,spawnX,spawnY);
        health=2;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return "Green Golem @ "+x+","+y;
    }
}
