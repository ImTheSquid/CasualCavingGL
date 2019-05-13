package org.entities.passive;

import org.entities.Autonomous;
import org.level.LevelController;

public class LifeCrystal extends Autonomous {
    public LifeCrystal(int subLevel, float spawnX, float spawnY) {
        super(subLevel, spawnX, spawnY);
    }

    @Override
    public void update() {
        if(health==0) LevelController.getCurrentLevel().getEntityRegister().add(new Health(subLevel,x,y));
    }

    @Override
    public void render() {

    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return null;
    }
}
