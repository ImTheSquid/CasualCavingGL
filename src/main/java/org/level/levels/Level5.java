package org.level.levels;

import org.entities.HitDetector;
import org.entities.aggressive.CineLarano;
import org.entities.aggressive.KeyMasterL;
import org.entities.aggressive.Larano;
import org.entities.aggressive.LaranoStalactite;
import org.entities.passive.LifeCrystal;
import org.graphics.Graphics;
import org.graphics.Render;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.HeightMap;
import org.world.HeightVal;
import org.world.World;

public class Level5 extends Level {
    private CineLarano cineLarano=new CineLarano();
    private Larano larano=new Larano();
    private KeyMasterL keyMaster=new KeyMasterL(larano);
    private HitDetector stalactiteLeft=new HitDetector(2,0, 50, 12, Render.unitsTall - 50, () -> entityRegister.add(new LaranoStalactite(0,50,true)),"Harold");
    private HitDetector stalactiteRight=new HitDetector(2,87, 50, 13, Render.unitsTall - 50, () -> entityRegister.add(new LaranoStalactite(95,50,false)),"Harold");
    public Level5(ImageResource[] backgrounds) {
        super(backgrounds, 3);
        numAssetsToLoad=ResourceHandler.getBossLoader().getLaranoReadying().length+ResourceHandler.getBossLoader().getLaranoShimmer(true).length*2;
    }

    @Override
    public void init() {

    }

    @Override
    public void loadAssets() {
        ImageResource[] r=ResourceHandler.getBossLoader().getLaranoReadying();
        ImageResource[] sRight=ResourceHandler.getBossLoader().getLaranoShimmer(true);
        ImageResource[] sLeft=ResourceHandler.getBossLoader().getLaranoShimmer(false);
        ImageResource[] toLoad=ResourceHandler.create1DLoadable(new ImageResource[][]{r,sRight,sLeft});
        if(World.getAssetLoaderCounter()<numAssetsToLoad){
            toLoad[World.getAssetLoaderCounter()].preloadTexture();
            World.incrementAssetLoadCount();
            World.renderAssetLoadingIndicator(numAssetsToLoad);
        }
    }

    @Override
    public void update(int subLevel) {
        checkHealthVals();
        ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
        if(subLevel<2) {
            HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7, Render.unitsWide,true)});
            Graphics.setScaleFactor(1f);
            leftLimit=-1;
        }
        else {
            HeightMap.setHeights(new HeightVal[]{new HeightVal(0,5,Render.unitsWide,true),
                    new HeightVal(15,21,34, false),
                    new HeightVal(64,21,84,false),
                    new HeightVal(35,30,63,false),
                    new HeightVal(15,41,35,false),
                    new HeightVal(64,41,86,false)});
            Graphics.setScaleFactor(0.75f);
            leftLimit=0;
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
        if(subLevel==2){
            larano.getBossBar().render();
            Graphics.setIgnoreScale(true);
            Graphics.drawImage(ResourceHandler.getMiscLoader().getLaranoStalactite(),0,50);
            Graphics.drawImage(ResourceHandler.getMiscLoader().getLaranoStalactite(),95,50);
            Graphics.setIgnoreScale(false);
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void reset() {
        cineLarano.reset();
        larano.reset();
        keyMaster.reset();
        clearEntityRegister();
        entityRegister.add(new LifeCrystal(0,65,7));
        entityRegister.add(new LifeCrystal(0,85,7));
        entityRegister.add(stalactiteLeft);
        entityRegister.add(stalactiteRight);
        entityRegister.add(cineLarano);
        entityRegister.add(larano);
        entityRegister.add(keyMaster);
    }
}
