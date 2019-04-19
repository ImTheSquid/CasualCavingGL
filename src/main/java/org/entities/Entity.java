package org.entities;

public abstract class Entity {
    protected float x=5,y=7,vX,vY,width,height;
    protected float red=1,green=1,blue=1,alpha=1;
    protected int health,level=0,subLevel=0;
    protected boolean nonGameUpdate=false,nonGameRender=false,pauseUpdate=false,pauseRender=true,movement=true;
    public abstract void update();
    public abstract void render();
    public abstract void reset();

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

    public int getLevel() {
        return level;
    }

    public int getSubLevel() {
        return subLevel;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setMovement(boolean movement) {
        this.movement = movement;
    }
}
