package org.level.levels;

import org.entities.aggressive.BlueGolem;
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
        System.out.println("CALLED");
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
        entityRegister.add(new BlueGolem(0,25,7));
    }
}
