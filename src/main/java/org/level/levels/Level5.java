package org.level.levels;

import org.graphics.Graphics;
import org.graphics.Render;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.HeightMap;
import org.world.HeightVal;

public class Level5 extends Level {
    public Level5(ImageResource[] backgrounds) {
        super(backgrounds, 3);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(int subLevel) {
        ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
        if(subLevel<2) {
            HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7, Render.unitsWide,true)});
            Graphics.setScaleFactor(1f);
        }
        else {
            HeightMap.setHeights(new HeightVal[]{new HeightVal(0,5,Render.unitsWide,true),
                    new HeightVal(15,21,34, false),
                    new HeightVal(64,21,84,false),
                    new HeightVal(35,30,63,false),
                    new HeightVal(15,41,35,false),
                    new HeightVal(64,41,86,false)});
            Graphics.setScaleFactor(0.75f);
        }
    }

    @Override
    public void render(int subLevel) {
        Graphics.setIgnoreScale(true);
        Graphics.drawImage(backgrounds[subLevel],0,0);
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
