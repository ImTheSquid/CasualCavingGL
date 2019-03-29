package org.entities;

public abstract class Entity {
    protected float x,y,vX,vY;
    protected int health;
    public abstract void update();
    public abstract void render();
}
