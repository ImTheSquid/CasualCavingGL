package org.entities.aggressive;

import org.entities.Autonomous;

public class GreenGolem extends Autonomous {
    public GreenGolem(int subLevel,float spawnX,float spawnY){
        super(subLevel,spawnX,spawnY);
        health=2;
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
        return "Green Golem @ "+x+","+y;
    }
}
