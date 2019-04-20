package org.level.levels;

import org.graphics.Graphics;
import org.graphics.Render;
import org.level.Level;
import org.loader.ImageResource;
import org.world.HeightMap;
import org.world.HeightVal;

public class Level2 extends Level {
    public Level2(ImageResource[][] backgrounds) {
        super(backgrounds,9);
    }

    public void update(int subLevel) {
        switch(subLevel){
            case 0:
                update0();
                break;
        }
    }

    private void update0(){
        HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7, Render.unitsWide,true)});
    }

    public void render(int subLevel) {
        Graphics.drawImage(backgrounds[subLevel],0,0);
    }

    @Override
    public void renderForeground(int subLevel) {
        if(foregrounds[subLevel]!=null) Graphics.drawImage(foregrounds[subLevel],0,0);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void reset() {

    }
}
