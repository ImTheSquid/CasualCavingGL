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

    //Counts number of non-null objects in a 2D array
    public static int count2DArr(ImageResource[][] in){
        int count=0;
        for(ImageResource[] o:in)for(ImageResource p:o)if(p!=null)count++;
        return count;
    }

    //Converts a 2D array to a 1D array with no null values
    public static ImageResource[] create2DLoadable(ImageResource[][] in){
        ImageResource[] out=new ImageResource[count2DArr(in)];
        int count=0;
        for(ImageResource[] i:in){
            for(ImageResource j:i){
                if(j!=null){
                    out[count]=j;
                    count++;
                }
            }
        }
        return out;
    }

    //Creates a 1D array from other 1D arrays with no null values
    public static ImageResource[] create1DLoadable(ImageResource[][] in){
        ImageResource[] out=new ImageResource[count2DArr(in)];
        int count=0;
        for(ImageResource[] i:in)
            for(ImageResource j:i)
                if(j!=null){
                    out[count]=j;
                    count++;
                }
        return out;
    }
}
