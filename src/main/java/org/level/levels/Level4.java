package org.level.levels;

import org.entities.aggressive.ShortGolem;
import org.entities.aggressive.TallGolem;
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

public class Level4 extends Level {
    public Level4(ImageResource[][] backgrounds) {
        super(backgrounds, backgrounds.length);
    }

    @Override
    public void init() {
        reset();
        ResourceHandler.getHaroldLoader().disableAttackPause();
        World.clearEntites();
        World.addEntities(super.getEntityRegisterArray());
    }

    @Override
    public void update(int subLevel) {
        checkHealthVals();
        World.clearEntites();
        World.addEntities(super.getEntityRegisterArray());
        ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
        if(subLevel!=4){
            rightBound=Render.unitsWide;
            rightLimit=Render.unitsWide+1;
        }
        if(subLevel!=5)leftLimit=-1;
        switch(subLevel){
            case 0:
            case 6:
                HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7, Render.unitsWide,true)});
                break;
            case 1:
                HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7,30,true),new HeightVal(34,29,72,false),new HeightVal(74,7,Render.unitsWide,true)});
                break;
            case 2:
                HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7,78,true),
                        new HeightVal(20,30,58,false),
                        new HeightVal(78,12,81,true),
                        new HeightVal(81,17,86,true),
                        new HeightVal(86,25,89,true),
                        new HeightVal(89,30,94,true),
                        new HeightVal(94,35,98,true),
                        new HeightVal(98,41,Render.unitsWide,true)});
                break;
            case 3:
                HeightMap.setHeights(new HeightVal[]{new HeightVal(0,20,24,true),
                        new HeightVal(36,30,66,false),
                        new HeightVal(77,20,Render.unitsWide,true)});
                break;
            case 4:
                HeightMap.setHeights(new HeightVal[]{new HeightVal(0,32,26,true),
                        new HeightVal(26,8,76,true),
                        new HeightVal(76,14,84,true)});
                rightBound=83;
                rightLimit=84;
                break;
            case 5:
                HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7,Render.unitsWide,true)});
                leftLimit=0;
                break;
        }
    }

    @Override
    public void render(int subLevel) {
        Graphics.drawImage(backgrounds[subLevel],0,0);
    }

    @Override
    public void renderForeground(int subLevel) {
        if(foregrounds[subLevel]!=null)Graphics.drawImage(foregrounds[subLevel],0,0);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void reset() {
        clearEntityRegister();
        entityRegister.add(new LifeCrystal(1,84,8));
        entityRegister.add(new LifeCrystal(3,38,32));
        entityRegister.add(new LifeCrystal(6,77,7));
        entityRegister.add(new ShortGolem(ShortGolem.BLUE,0,25,7));
        entityRegister.add(new ShortGolem(ShortGolem.RED,0,50,7));
        entityRegister.add(new ShortGolem(ShortGolem.GREEN,1,50,29));
        entityRegister.add(new ShortGolem(ShortGolem.PURPLE,2,24,31));
        entityRegister.add(new ShortGolem(ShortGolem.GREEN,3,52,30));
        entityRegister.add(new TallGolem(ShortGolem.BLUE,4,44,10));
        entityRegister.add(new TallGolem(TallGolem.BLUE,6,18,46));
        entityRegister.add(new TallGolem(TallGolem.BLUE,6,60,46));
    }
}
