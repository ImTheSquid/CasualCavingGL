package org.level.levels;

import org.level.Level;
import org.loader.ImageResource;

public class Level6 extends Level {
    public Level6(ImageResource[] backgrounds) {
        super(backgrounds, 8);
    }

    @Override
    public void init() {

    }

    @Override
    public void loadAssets() {

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
