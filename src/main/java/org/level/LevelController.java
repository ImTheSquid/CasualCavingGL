package org.level;

import org.level.levels.Level1;
import org.level.levels.Level2;
import org.level.levels.Title;
import org.loader.ResourceHandler;
import org.world.World;

public class LevelController {
    private static Level[] levels={new Title(ResourceHandler.getLevelLoader().getTitle(),ResourceHandler.getLevelLoader().getTitleLogo()),
    new Level1(ResourceHandler.getLevelLoader().getLevel1()),
    new Level2(ResourceHandler.getLevelLoader().getLevel2())};

    public static void update(int level,int subLevel){
        levels[level].update(subLevel);
    }

    public static void render(int level,int subLevel){
        levels[level].render(subLevel);
    }

    public static void renderForeground(int level,int subLevel){levels[level].renderForeground(subLevel);}

    public static void cleanup(int level){levels[level].cleanup();}

    public static Level[] getLevels(){return levels;}

    public static void resetAll(){
        for(Level l:levels){
            l.reset();
        }
    }

    public static int getNumLevels(){return levels.length;}

    public static int getNumSubLevels(){return levels[World.getLevel()].getNumSublevels();}
}
