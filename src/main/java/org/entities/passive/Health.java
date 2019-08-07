package org.entities.passive;

import org.engine.Main;
import org.entities.Autonomous;
import org.entities.SmartRectangle;
import org.graphics.Graphics;
import org.loader.ResourceHandler;
import org.world.HeightMap;
import org.world.HeightReturn;
import org.world.World;

public class Health extends Autonomous {
    private SmartRectangle hitbox;
    private int bounceWait=60;
    Health(int subLevel, float spawnX, float spawnY) {
        super(subLevel, spawnX, spawnY);
        width=6;
        height=6;
        health=1;
        vY=1;
        invincible=true;
        hitbox=new SmartRectangle(x,y,width,height);
    }

    @Override
    public void update() {
        HeightReturn h=HeightMap.onGround(hitbox);
        //Calculations
        if(bounceWait==0){
            vY=1;
            bounceWait=60;
        }else bounceWait--;
        y+=vY;
        vY-= World.getGravity();
        //Y-velocity and ground calc
        if(h.isOnGround()&&vY<0){
            y=h.getGroundLevel();
            vY=0;
        }

        if(Main.getHarold().getHitbox().intersects(hitbox)&&Main.getHarold().getHealth()+1<=Main.getHarold().getMaxHealth()){
            health=0;
            Main.getHarold().giveHealth(1);
        }
        hitbox.updateBounds(x,y,width,height);
    }

    @Override
    public void render() {
        Graphics.setDrawColor(1,1,1,1);
        Graphics.drawImage(ResourceHandler.getMiscLoader().getHealthHeart(),x,y,width,height);
    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return "Health Heart @ "+x+","+y;
    }
}
