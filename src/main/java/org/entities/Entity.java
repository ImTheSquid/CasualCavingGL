package org.entities;

public abstract class Entity {
    protected float x=5,y=7,vX,vY,width,height;
    protected float red=1,green=1,blue=1,alpha=1;
    protected int health,level=0,subLevel=0;
    private boolean nonGameUpdate=false,nonGameRender=false,pauseUpdate=false,pauseRender=true;
    public abstract void update();
    public abstract void render();

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

    public boolean getPauseUpdate(){return pauseUpdate;}

    public boolean getPauseRender(){return pauseRender;}

    public float getX(){return x;}

    public float getY(){return y;}

    public float getWidth(){return width;}

    public float getHeight(){return height;}

}
