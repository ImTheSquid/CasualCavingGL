package org.level.levels;

import org.graphics.Graphics;
import org.graphics.Render;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.HeightMap;

public class Level1 extends Level {
    public Level1(ImageResource[] backgrounds) {
        super(backgrounds,6);
    }
    private ImageResource[] sprites= ResourceHandler.getLevelLoader().getLevel1Sprites();
    private boolean bridge=false,wood=true;

    public void update(int subLevel) {
        if(subLevel==0){
            leftBound=65;
        }else{
            leftBound=0;
        }
        if(subLevel!=3)rightLimit= Render.unitsWide+1;
        switch(subLevel){
            case 0:
                update0();
                break;
            case 2:
                update2();
                break;
            case 3:
                update3();
                break;
        }
    }

    private void update0(){
        HeightMap.setHeights(new float[][]{{0f,7f}});
    }

    private void update2(){
        HeightMap.setHeights(new float[][]{{0f,7f},{75f,9f},{80f,7f}});
    }

    private void update3(){
        HeightMap.setHeights(new float[][]{{0f,7f}});
        if(!bridge){
            rightLimit=45;
        }else{
            rightLimit=Render.unitsWide;
        }
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
        Graphics.setFont(Graphics.SMALL_FONT);
        Graphics.drawText("Alright guys, you know what to do, we're looking for a precious yellow gem located in a nearby cave, now go!",20,48,18);
    }

    @Override
    public void reset() {

    }
}
