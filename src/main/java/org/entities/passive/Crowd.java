package org.entities.passive;

import org.engine.Main;
import org.entities.Entity;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.graphics.Render;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.World;

public class Crowd extends Entity {
    private Animator crowdAnimator=new Animator(ResourceHandler.getCrowdLoader().getCrowdWalk(),12);
    private ImageResource crowd;
    private boolean start=false,wood=true;
    private float cartWidth =0, cartHeight =0;
    private SmartRectangle cart=new SmartRectangle(x+24,y, cartWidth, cartHeight);
    public Crowd(){
        subLevel=1;
        x=-55;
    }

    @Override
    public void update() {
        if(vX==0){
            crowd=ResourceHandler.getCrowdLoader().getCrowd();
        }else{
            crowd=crowdAnimator.getCurrentFrame();
        }
        switch(World.getSubLevel()){
            case 1:
                if(Main.getHarold().getX()> Render.unitsWide/2)start=true;
                if(x<5&&start)vX=0.6f;
                break;
            case 2:
                if(x<5)vX=0.6f;
                break;
        }
        x+=vX;
        if(vX-World.getGravity()<0)vX=0;
        else vX-=World.getGravity();
        crowdAnimator.update();
        cart.updateBounds(x+24,y, cartWidth, cartHeight);
    }

    @Override
    public void render() {
        cartWidth =ResourceHandler.getCrowdLoader().getCart().getTexture().getWidth();
        cartHeight =ResourceHandler.getCrowdLoader().getCart().getTexture().getHeight();
        if(World.getSubLevel()!=subLevel)return;
        Graphics.setColor(1,1,1,1);
        Graphics.drawImage(crowd,x,y);
        Graphics.drawImage(ResourceHandler.getCrowdLoader().getCart(),x+24,y);
        if(subLevel==2&&vX==0){
            Graphics.setColor(1,1,1,1);
            Graphics.setFont(Graphics.SMALL_FONT);
            Graphics.drawText("Hey, we won't be able to get the cart over that log. You should use some tools.",8,35,20);
        }
    }

    public void updateSublevel(int newSub){
        if(newSub>subLevel){
            subLevel=newSub;
            x=-55;
        }
    }

    public void setWood(boolean wood){
        this.wood=wood;
    }
}
