package org.level;

import org.level.levels.Title;
import org.loader.ResourceHandler;

public class LevelController {
    private static Level[] levels={new Title(ResourceHandler.getLevelLoader().getTitle(),ResourceHandler.getLevelLoader().getTitleForeground())};

    public static void update(int level,int subLevel){

    }

    public static void render(int level,int subLevel){
        levels[level].render(subLevel);
    }
}
