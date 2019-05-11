package org.level;

import org.level.levels.*;
import org.loader.ResourceHandler;
import org.world.World;

public class LevelController {
    private static Level[] levels={new Death(),
            new Title(ResourceHandler.getLevelLoader().getTitle(),ResourceHandler.getLevelLoader().getTitleLogo()),
            new Level1(ResourceHandler.getLevelLoader().getLevel1()),
            new Level2(ResourceHandler.getLevelLoader().getLevel2()),
            new Level3(ResourceHandler.getLevelLoader().getLevel3())};

    public static void update(int level,int subLevel){
        levels[level+1].update(subLevel);
    }

    public static void render(int level,int subLevel){
        levels[level+1].render(subLevel);
    }

    public static void renderForeground(int level,int subLevel){levels[level+1].renderForeground(subLevel);}

    public static void cleanup(int level){levels[level+1].cleanup();}

    public static void init(int level){levels[level+1].init();}

    public static Level[] getLevels(){return levels;}

    public static void resetAll(){
        World.setMasterColor(0,0,0);
        for(Level l:levels){
            l.reset();
        }
    }

    public static int getNumLevels(){return levels.length;}

    public static int getNumSubLevels(){return levels[World.getLevel()].getNumSublevels();}
}
