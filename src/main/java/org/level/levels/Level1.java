package org.level.levels;

import org.graphics.Graphics;
import org.level.Level;
import org.loader.ImageResource;

public class Level1 extends Level {
    public Level1(ImageResource[] backgrounds) {
        super(backgrounds,6);
    }

    public void update(int subLevel) {
        switch(subLevel){
            case 0:
                update0();
                break;
        }
    }

    private void update0(){

    }

    public void render(int subLevel) {
        Graphics.drawImage(backgrounds[subLevel],0,0);
        switch (subLevel){
            case 0:
                render0();
                break;
        }
    }

    @Override
    public void reset() {

    }

    private void render0(){
    }
}
