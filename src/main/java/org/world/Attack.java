package org.world;

import org.engine.Main;
import org.entities.Entity;
import org.entities.Harold;

import java.util.ArrayList;

public class Attack {
    public static void melee(Entity e, int damage, float range){
        if(e instanceof Harold) {//Check if player is executing melee
            Entity[] applicable=sortRegister(e);//Finds entities in range
            for (Entity x : applicable) {
                if (x == e || x.getY() > e.getY() + e.getHeight() || x.getY() + x.getHeight() < e.getY())
                    continue;//Skip if y-val is out of range
                if (e.isFacingRight() && e.getX() + e.getWidth() + range >= x.getX()) {
                    x.doDamage(e, damage);
                } else if (!e.isFacingRight() && x.getX() + x.getWidth() + range >= e.getX()) {
                    x.doDamage(e, damage);
                }
            }
        }else {
            Entity x = Main.getHarold();
            if ((x.getY() > e.getY() + e.getHeight() || x.getY() + x.getHeight() < e.getY()))return;//Return if y-val is out of range
            if (e.isFacingRight() && e.getX() + e.getWidth() + range >= x.getX()) {
                x.doDamage(e,damage);
            } else if (!e.isFacingRight() && x.getX() + x.getWidth() + range>= e.getX()) {
                x.doDamage(e,damage);
            }
        }
    }

    private static Entity[] sortRegister(Entity e){
        Object[] levelReg= World.getEntities().toArray();
        ArrayList<Entity> applicable=new ArrayList<>();
        for(Object y:levelReg){
            Entity x=(Entity)y;
            if(x==e)continue;//Skip if entity is itself
            if(e.isFacingRight()&&x.getX()>=e.getX()){
                applicable.add(x);
            }else if(!e.isFacingRight()&&x.getX()<=e.getX()){
                applicable.add(x);
            }
        }
        applicable.removeIf(n->n.getSubLevel()!=World.getSubLevel());
        Entity[] out=new Entity[applicable.size()];
        for (int i = 0; i < out.length; i++) {
            out[i]=applicable.get(i);
        }
        return out;
    }
}
