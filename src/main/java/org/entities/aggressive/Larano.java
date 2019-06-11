package org.entities.aggressive;

import org.entities.Autonomous;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.Graphics;
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
        doSpriteCalc();
    }

    private void doSpriteCalc(){
        sprite=larano.getCurrentFrame();
    }

    @Override
    public void render() {
        if(sprite==null)return;
        Graphics.setIgnoreScale(true);
        width= Graphics.convertToWorldWidth(sprite.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(sprite.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        Graphics.drawImage(sprite,x,y);
        Graphics.setIgnoreScale(false);
    }

    @Override
    public void reset() {
        health=3;
        x=69;
        y=5;
    }

    @Override
    public String toString() {
        return "Larano @ "+x+","+y;
    }
}
