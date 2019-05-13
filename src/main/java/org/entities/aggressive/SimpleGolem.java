package org.entities.aggressive;

import org.entities.Autonomous;

//This class encompasses the blue, green, and red golems, as their classes are very similar

public class SimpleGolem extends Autonomous {
    public SimpleGolem(int golemType, int subLevel, float spawnX, float spawnY) {
        super(subLevel, spawnX, spawnY);
    }

    @Override
    public void update() {

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
