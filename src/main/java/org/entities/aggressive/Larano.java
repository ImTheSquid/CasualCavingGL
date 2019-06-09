package org.entities.aggressive;

import org.entities.Autonomous;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

public class Larano extends Autonomous {
    private Animator larano=new Animator(ResourceHandler.getBossLoader().getLaranoReadying(),10);
    private ImageResource sprite=null;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    public Larano() {
        super(2, 69, 5);
        reset();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void reset() {
        health=3;
        x=69;
        y=5;
    }

    @Override
    public String toString() {
        return null;
    }
}
