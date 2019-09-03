package org.level.levels;

import org.graphics.Graphics;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.HeightMap;
import org.world.HeightVal;
import org.world.World;

import static org.world.World.CHECK_LARANO_FINISH;

public class Level6 extends Level {
    public Level6(ImageResource[] backgrounds) {
        super(backgrounds, backgrounds.length);
    }

    @Override
    public void init() {
        if(World.getLatestCheckpoint()< CHECK_LARANO_FINISH)World.newCheckpoint(CHECK_LARANO_FINISH);
    }

    @Override
    public ImageResource[] getAssets() {
        return ResourceHandler.getLevelLoader().getLevel6();
    }

    @Override
    public void update(int subLevel) {
        checkHealthVals();
        ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
        if(subLevel!=3) {
            HeightMap.setHeights(new HeightVal(0,7,100,true));
            Graphics.setScaleFactor(1);
        }
        else{
            HeightMap.setHeights(new HeightVal(0,7,24,true),new HeightVal(36,7,46,true),new HeightVal(60,7,66,true),
                    new HeightVal(80,7,100,true));
            Graphics.setScaleFactor(.75f);
        }
    }

    @Override
    public void render(int subLevel) {
        Graphics.setIgnoreScale(true);
        if(subLevel!=4)Graphics.drawImage(backgrounds[subLevel],0,0);
        else Graphics.drawImage(backgrounds[subLevel],0,-Graphics.convertToWorldHeight(700));
        Graphics.setIgnoreScale(false);
    }

    @Override
    public void renderForeground(int subLevel) {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public void reset() {
    }
}
