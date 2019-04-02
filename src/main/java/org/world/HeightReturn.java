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

    boolean isOnGround() {
        return onGround;
    }

    float getGroundLevel() {
        return groundLevel;
    }
}
