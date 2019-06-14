package org.loader;

import org.loader.entity.BossLoader;
import org.loader.entity.CrowdLoader;
import org.loader.entity.GolemLoader;
import org.loader.harold.HaroldLoader;
import org.loader.levels.LevelLoader;

public class ResourceHandler {
    private static HaroldLoader haroldLoader =new HaroldLoader();
    private static LevelLoader levelLoader=new LevelLoader();
    private static CrowdLoader crowdLoader=new CrowdLoader();
    private static GolemLoader golemLoader=new GolemLoader();
    private static MiscLoader miscLoader=new MiscLoader();
    private static BossLoader bossLoader=new BossLoader();

    public static HaroldLoader getHaroldLoader(){
        return haroldLoader;
    }

    public static LevelLoader getLevelLoader() {
        return levelLoader;
    }

    public static CrowdLoader getCrowdLoader() {
        return crowdLoader;
    }

    public static GolemLoader getGolemLoader() {
        return golemLoader;
    }

    public static MiscLoader getMiscLoader() {
        return miscLoader;
    }

    public static BossLoader getBossLoader() {
        return bossLoader;
    }
}
