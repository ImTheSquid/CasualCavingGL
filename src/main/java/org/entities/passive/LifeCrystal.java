package org.entities.passive;

import org.entities.Autonomous;
import org.graphics.Graphics;
import org.level.LevelController;
import org.loader.ResourceHandler;

public class LifeCrystal extends Autonomous {
    public LifeCrystal(int subLevel, float spawnX, float spawnY) {
        super(subLevel, spawnX, spawnY);
    }

    @Override
    public void update() {
        if(health==0) LevelController.getCurrentLevel().getEntityRegister().add(new Health(subLevel,x,y));
        if(damageTakenFrame>0)damageTakenFrame--;
    }

    @Override
    public void render() {
        if(damageTakenFrame>0)Graphics.setColor(1,0,0,1);
        else Graphics.setColor(1,1,1,1);
        Graphics.drawImageCentered(ResourceHandler.getMiscLoader().getLifeCrystal(),x,y);
    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return null;
    }
}
