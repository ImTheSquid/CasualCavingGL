package org.world;

public class HeightReturn {
    private boolean onGround;
    private float groundLevel;
    HeightReturn(boolean in){
        onGround=in;
    }

    HeightReturn(boolean in, float height){
        onGround=in;
        groundLevel=height;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public float getGroundLevel() {
        return groundLevel;
    }
}
