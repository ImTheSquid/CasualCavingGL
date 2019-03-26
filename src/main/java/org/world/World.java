package org.world;

import org.entities.Entity;

import java.util.concurrent.ConcurrentLinkedQueue;

public class World {
    private static ConcurrentLinkedQueue<Entity> entites=new ConcurrentLinkedQueue<Entity>();


    public static void update(){

    }

    public static void render(){

    }

    public void addEntity(Entity e){
        entites.offer(e);
    }

    public void removeEntity(Entity e){
        entites.remove(e);
    }

    public void clearEntites(){
        entites.clear();
    }


}
