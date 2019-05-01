package org.world;

import org.entities.Entity;
import org.level.LevelController;

import java.util.ArrayList;

public class Attack {
    public static void attack(Entity e,int damage,float range){
        Entity[] applicable=sortRegister(e);
        for(Entity x:applicable){
            if(x==e)continue;
            if(e.isFacingRight()&&e.getX()+e.getWidth()+range>=x.getX()){
                x.doDamage(damage);
            }else if(!e.isFacingRight()&&x.getX()+x.getWidth()>=e.getX()-range){
                x.doDamage(damage);
            }
        }
    }

    private static Entity[] sortRegister(Entity e){
        Entity[] levelReg= LevelController.getLevels()[World.getLevel()].getEntityRegister();
        ArrayList<Entity> applicable=new ArrayList<>();
        for(Entity x:levelReg){
            if(e.isFacingRight()&&x.getX()>=e.getX()+e.getWidth()){
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
