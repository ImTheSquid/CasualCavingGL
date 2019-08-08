package org.level.levels;

import org.level.Level;
import org.loader.ImageResource;

import static org.world.World.*;

public class Level6 extends Level {
    public Level6(ImageResource[] backgrounds) {
        super(backgrounds, 8);
    }

    @Override
    public void init() {

    }

    @Override
    public ImageResource[] getAssets() {
        return null;
    }

    @Override
    public void update(int subLevel) {
        checkHealthVals();
        if(getLatestCheckpoint()<CHECK_LARANO_FINISH)newCheckpoint(CHECK_LARANO_FINISH);
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
