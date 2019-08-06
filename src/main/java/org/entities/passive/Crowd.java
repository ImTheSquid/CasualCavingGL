package org.entities.passive;

import org.engine.Main;
import org.entities.Entity;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_E;

public class Crowd extends Entity {
    private Animator crowdAnimator=new Animator(ResourceHandler.getCrowdLoader().getCrowdWalk(),12);
    private ImageResource crowd;
    private boolean start=false,wood=true,chainsaw=false,cartIntersect=false,fadeDelaySet=false;
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
        cartIntersect=cart.intersects(Main.getHarold().getHitbox());
        switch(World.getSubLevel()){
            case 1:
                if(Main.getHarold().getX()> Render.unitsWide/2)start=true;
                if(x<5&&start)vX=0.6f;
                break;
            case 2:
                if(x<5&&subLevel==2)vX=0.6f;
                if(cartIntersect&& Keyboard.keys.contains(VK_E)&&!chainsaw){
                    ResourceHandler.getHaroldLoader().setState(HaroldLoader.CHAINSAW);
                    chainsaw=true;
                }
                break;
            case 3:
                if(x<5&&subLevel==3)vX=0.6f;
                break;
            case 4:
                if(x<5&&subLevel==4)vX=0.6f;
                break;
            case 5:
                if(x<5&&subLevel==5)vX=0.6f;
                if(vX==0&&!fadeDelaySet){
                    fadeDelaySet=true;
                    World.getMaster().setSecondDelay(4);
                    World.getMaster().setDirection(false);
                    World.getMaster().setActive(true);
                }
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
        cartWidth =Graphics.convertToWorldWidth(ResourceHandler.getCrowdLoader().getCart().getTexture().getWidth());
        cartHeight =Graphics.convertToWorldHeight(ResourceHandler.getCrowdLoader().getCart().getTexture().getHeight());
        if(World.getSubLevel()!=subLevel)return;
        Graphics.setColor(1,1,1,1);
        if(crowd!=null&&ResourceHandler.getCrowdLoader().getCart()!=null) {
            Graphics.drawImage(crowd, x, y);
            Graphics.drawImage(ResourceHandler.getCrowdLoader().getCart(), x + 24, y);
        }
        if(subLevel==2&&vX==0){
            Graphics.setColor(1,1,1,1);
            Graphics.setFont(Graphics.SMALL);
            if(wood)Graphics.drawText("Hey, we won't be able to get the cart over that log. You should use some tools.",8,35,20,true);
            if(cartIntersect&&!chainsaw)Graphics.drawTextWithBox("Press E to pick up chainsaw",32,29);
        }
        if(subLevel==5&&vX==0){
            Graphics.setColor(1,1,1,1);
            Graphics.setFont(Graphics.SMALL);
            Graphics.drawText("This looks like a good place to set up camp. Let's put our stuff down.",8,35,20,true);
        }
    }

    @Override
    public void reset() {
        subLevel=1;
        level=1;
        start=false;
        wood=true;
        chainsaw=false;
        cartIntersect=false;
        fadeDelaySet=false;
        x=-55;
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

    @Override
    public String toString() {
        return "Crowd @ "+x+","+y;
    }
}
