package org.level;

import org.engine.AudioManager;
import org.graphics.Graphics;
import org.level.levels.*;
import org.loader.ResourceHandler;
import org.world.World;

public class LevelController {
    private static Level[] levels={new Death(),
            new Title(ResourceHandler.getLevelLoader().getTitle(),ResourceHandler.getLevelLoader().getTitleLogo()),
            new Level1(ResourceHandler.getLevelLoader().getLevel1()),
            new Level2(ResourceHandler.getLevelLoader().getLevel2()),
            new Level3(ResourceHandler.getLevelLoader().getLevel3()),
            new Level4(ResourceHandler.getLevelLoader().getLevel4()),
            new Level5(ResourceHandler.getLevelLoader().getLevel5()),
            new Level6(null)};

    public static void update(int level,int subLevel){
        levels[level+1].update(subLevel);
    }

    public static void render(int level,int subLevel){
        levels[level+1].render(subLevel);
    }

    public static void renderForeground(int level,int subLevel){levels[level+1].renderForeground(subLevel);}

    public static void cleanup(int level){levels[level+1].cleanup();}

    public static void init(int level){levels[level+1].init();}

    public static void loadAssets(int level){levels[level+1].loadAssets();}

    public static Level[] getLevels(){return levels;}

    public static Level getCurrentLevel(){return levels[World.getLevel()+1];}

    public static void resetAll(){
        Graphics.setScaleFactor(1);
        World.setMasterColor(0,0,0);
        AudioManager.resetGame();
        for(Level l:levels){
            l.reset();
        }
    }

    public static int getNumLevels(){return levels.length;}

    public static int getNumSubLevels(){return levels[World.getLevel()+1].getNumSublevels();}
}
