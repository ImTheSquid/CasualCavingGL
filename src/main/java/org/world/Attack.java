package org.world;

import org.engine.Main;
import org.entities.Entity;
import org.entities.Harold;
import org.entities.aggressive.LaranoStalactite;

import java.util.ArrayList;

public class Attack {
    //Returns true if attack was successful
    public static boolean melee(Entity e, int damage, float range){
        if(e instanceof Harold||e instanceof LaranoStalactite) {//Check if player is executing melee
            Entity[] applicable=sortRegister(e);//Finds entities in range
            for (Entity x : applicable) {
                if (x == e || x.getY() > e.getY() + e.getHeight() || x.getY() + x.getHeight() < e.getY())
                    continue;//Skip if y-val is out of range
                if (e.isFacingRight() && e.getX() + e.getWidth() + range >= x.getX()) {
                    x.doDamage(e, damage);
                    return true;
                } else if (!e.isFacingRight() && x.getX() + x.getWidth() + range >= e.getX()) {
                    x.doDamage(e, damage);
                    return true;
                }
            }
            if(e instanceof LaranoStalactite)doHaroldAttack(e,damage,range);
        }else {
            doHaroldAttack(e,damage,range);
        }
        return false;
    }

    private static boolean doHaroldAttack(Entity e,int damage,float range){
        Entity x = Main.getHarold();
        if ((x.getY() > e.getY() + e.getHeight() || x.getY() + x.getHeight() < e.getY()))return false;//Return if y-val is out of range
        if (e.isFacingRight() && e.getX() + e.getWidth() + range >= x.getX()) {
            x.doDamage(e,damage);
            return true;
        } else if (!e.isFacingRight() && x.getX() + x.getWidth() + range>= e.getX()) {
            x.doDamage(e,damage);
            return true;
        }
        return false;
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
