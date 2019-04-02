package org.level.levels;

import org.graphics.Graphics;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

public class Level1 extends Level {
    public Level1(ImageResource[] backgrounds) {
        super(backgrounds,6);
    }
    private ImageResource[] sprites= ResourceHandler.getLevelLoader().getLevel1Sprites();

    public void update(int subLevel) {
        if(subLevel==0){
            leftBound=65;
        }else{
            leftBound=0;
        }
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

    private void render0(){
        Graphics.drawImage(sprites[0],6,7);
        Graphics.drawImage(sprites[1],40,7);
    }

    @Override
    public void reset() {

    }
}
