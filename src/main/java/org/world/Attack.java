package org.world;

import org.engine.Main;
import org.entities.Entity;
import org.entities.Harold;

import java.util.ArrayList;

public class Attack {
    public static void attack(Entity e,int damage,float range){
        Entity[] applicable=sortRegister(e);
        if(e instanceof Harold)
        for(Entity x:applicable){
            if(x==e||x.getY()>e.getY()+e.getHeight()||x.getY()+x.getHeight()<e.getY())continue;
            if(e.isFacingRight()&&e.getX()+e.getWidth()+range>=x.getX()&&x.getX()>=e.getX()+e.getWidth()){
                x.doDamage(damage);
            }else if(!e.isFacingRight()&&x.getX()+x.getWidth()>=e.getX()-range&&x.getX()+x.getWidth()<=e.getX()){
                x.doDamage(damage);
            }
        }
        else {
            Entity x = Main.getHarold();
            if (e != x && !(x.getY() > e.getY() + e.getHeight() || x.getY() + x.getHeight() < e.getY())) {
                if (e.isFacingRight() && e.getX() + e.getWidth() + range >= x.getX()&&x.getX()>=e.getX()+e.getWidth()) {
                    x.doDamage(damage);
                } else if (!e.isFacingRight() && x.getX() + x.getWidth() >= e.getX() - range&&x.getX()+x.getWidth()<=e.getX()) {
                    x.doDamage(damage);
                }
            }
        }
    }

    private static Entity[] sortRegister(Entity e){
        Object[] levelReg= World.getEntites().toArray();
        ArrayList<Entity> applicable=new ArrayList<>();
        for(Object y:levelReg){
            Entity x=(Entity)y;
            if(e.isFacingRight()&&x.getX()>=e.getX()){
                applicable.add(x);
            }else if(!e.isFacingRight()&&x.getX()+x.getWidth()<=e.getX()){
                applicable.add(x);
            }
        }
        Entity[] out=new Entity[applicable.size()];
        for (int i = 0; i < out.length; i++) {
            out[i]=applicable.get(i);
        }
        return out;
    }
}
