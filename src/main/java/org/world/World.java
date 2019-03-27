package org.world;

import org.entities.Entity;

import java.util.concurrent.ConcurrentLinkedQueue;

public class World {
    private static ConcurrentLinkedQueue<Entity> entites=new ConcurrentLinkedQueue<Entity>();


    public static void update(){

    }

    public static void render(){
        for(Entity e:entites){
            e.render();
        }
    }

    public static void addEntity(Entity e){
        entites.offer(e);
    }

    public static void removeEntity(Entity e){
        entites.remove(e);
    }

    public static void clearEntites(){
        entites.clear();
    }


}
