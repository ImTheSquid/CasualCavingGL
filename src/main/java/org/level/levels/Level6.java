package org.level.levels;

import org.level.Level;
import org.loader.ImageResource;
import org.world.World;

import static org.world.World.CHECK_LARANO_FINISH;

public class Level6 extends Level {
    public Level6(ImageResource[] backgrounds) {
        super(backgrounds, 8);
    }

    @Override
    public void init() {
        if(World.getLatestCheckpoint()< CHECK_LARANO_FINISH)World.newCheckpoint(CHECK_LARANO_FINISH);
    }

    @Override
    public ImageResource[] getAssets() {
        return null;
    }

    @Override
    public void update(int subLevel) {
        checkHealthVals();
    }

    @Override
    public void render(int subLevel) {

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
