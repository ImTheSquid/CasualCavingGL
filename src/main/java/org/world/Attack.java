package org.world;

import org.entities.Entity;
import org.level.LevelController;

public class Attack {
    static void attack(Entity e,int damage){
        Entity[] applicable=sortRegister(e);
    }

    private static Entity[] sortRegister(Entity e){
        Entity[] levelReg= LevelController.getLevels()[World.getLevel()].getEntityRegister();
        return null;
    }
}
