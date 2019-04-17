package org.loader;

import org.loader.entity.CrowdLoader;
import org.loader.harold.HaroldLoader;
import org.loader.levels.LevelLoader;

public class ResourceHandler {
    private static HaroldLoader haroldLoader =new HaroldLoader();
    private static LevelLoader levelLoader=new LevelLoader();
    private static CrowdLoader crowdLoader=new CrowdLoader();

    public static HaroldLoader getHaroldLoader(){
        return haroldLoader;
    }

    public static LevelLoader getLevelLoader() {
        return levelLoader;
    }

    public static CrowdLoader getCrowdLoader() {
        return crowdLoader;
    }
}
