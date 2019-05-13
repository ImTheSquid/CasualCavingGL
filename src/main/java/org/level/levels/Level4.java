package org.level.levels;

import org.entities.aggressive.SimpleGolem;
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
        World.addEntities(super.getEntityRegister());
    }

    @Override
    public void update(int subLevel) {
        entityRegister.removeIf(n->n.getHealth()<=0);
        World.clearEntites();
        World.addEntities(super.getEntityRegister());
        ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
        switch(subLevel){
            case 0:
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
        }
    }

    @Override
    public void render(int subLevel) {
        Graphics.drawImage(backgrounds[subLevel],0,0);
    }

    @Override
    public void renderForeground(int subLevel) {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public void reset() {
        clearEntityRegister();
        entityRegister.add(new SimpleGolem(SimpleGolem.BLUE,0,25,7));
        entityRegister.add(new SimpleGolem(SimpleGolem.RED,0,50,7));
        entityRegister.add(new SimpleGolem(SimpleGolem.GREEN,1,50,29));
    }
}
