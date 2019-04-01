package org.entities;

public abstract class Entity {
    protected float x=5,y=7,vX,vY,width,height;
    protected float red=1,green=1,blue=1,alpha=1;
    protected int health;
    private boolean nonGameUpdate=false,nonGameRender=false;
    public abstract void update();
    public abstract void render();

    public boolean contains(float x1,float y1){
        return (y1 >= y && y1 <= y + height)&&(x1>=x&&x1<=x+width);
    }

    protected void setNonGameUpdate(boolean update){
        nonGameUpdate=update;
    }

    protected void setNonGameRender(boolean render){
        nonGameRender=render;
    }

    public boolean getNonGameUpdate(){return nonGameUpdate;}

    public boolean getNonGameRender(){
        return nonGameRender;
    }


}
