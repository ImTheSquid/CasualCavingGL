package org.entities.passive;

import org.entities.Autonomous;
import org.graphics.Graphics;
import org.level.LevelController;
import org.loader.ResourceHandler;

public class LifeCrystal extends Autonomous {
    public LifeCrystal(int subLevel, float spawnX, float spawnY) {
        super(subLevel, spawnX, spawnY);
        displayName="Life Crystal";
    }

    @Override
    public void update() {
        if(damageTakenFrame>0)damageTakenFrame--;
    }

    @Override
    public void render() {
        if(damageTakenFrame>0)Graphics.setColor(1,0,0,1);
        else Graphics.setColor(1,1,1,1);
        Graphics.drawImage(ResourceHandler.getMiscLoader().getLifeCrystal(),x,y,8.44f,10);
    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void handleDeath() {
        LevelController.getCurrentLevel().getEntityRegister().add(new Health(subLevel,x,y));
    }
}
