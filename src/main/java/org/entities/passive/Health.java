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
    public Health(int subLevel, float spawnX, float spawnY) {
        super(subLevel, spawnX, spawnY);
        width=10;
        height=10;
        health=1;
        hitbox=new SmartRectangle(x,y,width,height);
    }

    @Override
    public void update() {
        HeightReturn h=HeightMap.onGround(hitbox);
        //Calculations
        y+=vY;
        vY-= World.getGravity();
        //Y-velocity and ground calc
        if(h.isOnGround()&&vY<0){
            y=h.getGroundLevel();
            vY=0;
        }

        if(Main.getHarold().getHitbox().intersects(hitbox)){
            health=0;
            Main.getHarold().giveHealth(1);
        }
    }

    @Override
    public void render() {
        Graphics.setColor(1,1,1,1);
        Graphics.drawImageCentered(ResourceHandler.getMiscLoader().getHealthHeart(),x,y,width,height);
    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return "Health Heart @ "+x+","+y;
    }
}
