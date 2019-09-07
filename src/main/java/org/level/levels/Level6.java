package org.level.levels;

import org.entities.passive.Boulder;
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
    private Boulder boulder=new Boulder();
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
        setBounds(subLevel);
        if(subLevel!=3) {
            HeightMap.setHeights(new HeightVal(0,7,100,true));
            Graphics.setScaleFactor(1);
            World.setGravity(.15f);
        }
        else{
            HeightMap.setHeights(new HeightVal(0,7,24,true),new HeightVal(36,7,46,true),new HeightVal(60,7,66,true),
                    new HeightVal(80,7,100,true));
            Graphics.setScaleFactor(.75f);
            World.setGravity(.25f);
        }
    }

    private void setBounds(int subLevel){
        if(subLevel==4)leftLimit=3;
        else leftLimit=-1;
        switch(subLevel){
            case 0:
                leftBound=0;
                rightBound=100;
                break;
            case 1:
                leftBound=0;
                rightBound=90;
                break;
            case 2:
                leftBound=5;
                rightBound=95;
                break;
            case 3:
                leftBound=3;
                rightBound=96;
                break;
            case 4:
                leftBound=2;
                rightBound=250;
                break;
        }
    }

    @Override
    public void render(int subLevel) {
        Graphics.setIgnoreScale(true);
        if(subLevel!=4)Graphics.drawImage(backgrounds[subLevel],0,0);
        else {
            if(!boulder.isDone())Graphics.drawImage(backgrounds[subLevel],0,-Graphics.convertToWorldHeight(700));
            else Graphics.drawImage(backgrounds[subLevel],0,0);
        }
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
        boulder.reset();
        clearEntityRegister();
        entityRegister.add(boulder);
    }
}
