package org.entities.aggressive;

import org.entities.Autonomous;

public class Stalactite extends Autonomous {
    public Stalactite(float spawnX, float spawnY) {
        super(2, spawnX, spawnY);
        displayName="Stalactite";
    }

    @Override
    public void update() {
        if(y+height<0)health=0;
    }

    @Override
    public void render() {

    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return "Stalactite @ "+x+","+y;
    }
}
