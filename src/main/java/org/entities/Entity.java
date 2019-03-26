package org.entities;

public abstract class Entity {
    private float x,y,vX,vY;
    private int health;
    public abstract void update();
    public abstract void render();
}
