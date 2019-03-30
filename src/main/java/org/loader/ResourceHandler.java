package org.loader;

import org.loader.harold.HaroldLoader;
import org.loader.levels.LevelLoader;

public class ResourceHandler {
    private static HaroldLoader haroldLoader =new HaroldLoader();
    private static LevelLoader levelLoader=new LevelLoader();

    public static HaroldLoader getHaroldLoader(){
        return haroldLoader;
    }

    public static LevelLoader getLevelLoader() {
        return levelLoader;
    }
}
