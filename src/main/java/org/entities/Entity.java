package org.entities;

public abstract class Entity {
    protected float x,y,vX,vY,width,height;
    protected float red=1,green=1,blue=1,alpha=1;
    protected int health;
    public abstract void update();
    public abstract void render();

    public boolean contains(float x1,float y1){
        return (y1 >= y && y1 <= y + height)&&(x1>=x&&x1<=x+width);
    }


}
