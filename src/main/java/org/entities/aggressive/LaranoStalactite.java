package org.entities.aggressive;

import org.entities.Autonomous;
import org.graphics.Graphics;
import org.loader.ResourceHandler;
import org.world.Attack;
import org.world.World;

public class LaranoStalactite extends Autonomous {
    public LaranoStalactite(float spawnX, float spawnY,boolean dir) {
        super(2, spawnX, spawnY);
        invincible=true;
        direction=dir;
        displayName="Stalactite";
    }

    @Override
    public void update() {
        if(y+height<0)health=0;
        y-= World.getGravity()*4;
        Attack.melee(this,1,2f);
    }

    @Override
    public void render() {
        width=Graphics.convertToWorldWidth(ResourceHandler.getMiscLoader().getLaranoStalactite().getTexture().getWidth());
        height=Graphics.convertToWorldHeight(ResourceHandler.getMiscLoader().getLaranoStalactite().getTexture().getHeight());
        Graphics.setIgnoreScale(true);
        Graphics.drawImage(ResourceHandler.getMiscLoader().getLaranoStalactite(),x,y);
        Graphics.setIgnoreScale(false);
    }

    @Override
    public void reset() {

    }
}
