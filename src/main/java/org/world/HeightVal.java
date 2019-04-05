package org.world;

public class HeightVal{
    private float startX,height,endX;
    HeightVal(float startX,float height,float endX){
        this.startX=startX;
        this.height=height;
        this.endX=endX;
    }
    public float getStartX(){return startX;}

    public float getHeight() {
        return height;
    }

    public float getEndX() {
        return endX;
    }
}
