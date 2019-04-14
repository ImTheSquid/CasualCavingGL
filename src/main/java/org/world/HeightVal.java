package org.world;

public class HeightVal{
    private Float startX,height,endX;
    private boolean opaque;
    public HeightVal(float startX,float height,float endX,boolean opaque){
        this.startX=startX;
        this.height=height;
        this.endX=endX;
        this.opaque=opaque;
    }
    public float getStartX(){return startX;}

    public float getHeight() {
        return height;
    }

    public float getEndX() {
        return endX;
    }

    public boolean isOpaque() {
        return opaque;
    }

    @Override
    public String toString() {
        return "Start X: "+startX+", Height: "+height+", End X: "+endX+", Opaque: "+opaque;
    }
}
