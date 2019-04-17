package org.level.levels;

import org.entities.passive.Crowd;
import org.graphics.Graphics;
import org.graphics.Render;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.HeightMap;
import org.world.HeightVal;
import org.world.World;

public class Level1 extends Level {
    public Level1(ImageResource[] backgrounds) {
        super(backgrounds,6);
    }
    private ImageResource[] sprites= ResourceHandler.getLevelLoader().getLevel1Sprites();
    private boolean bridge=false,wood=true;
    private Crowd crowd=new Crowd();

    public void update(int subLevel) {
        if(!World.getEntites().contains(crowd))World.addEntity(crowd);
        if(subLevel==0){
            leftBound=65;
        }else{
            leftBound=0;
        }
        if(subLevel!=3){
            rightLimit= Render.unitsWide+1;
            crowd.updateSublevel(World.getSubLevel());
        }
        switch(subLevel){
            case 0:
                update0();
                break;
            case 1:
                update1();
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
        HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7,Render.unitsWide,true)});
    }

    private void update1() {HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7,Render.unitsWide,true)});}

    private void update2(){
        crowd.setWood(wood);
        if(wood)HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7,70,true),new HeightVal(70,13,76,true),new HeightVal(76,7,Render.unitsWide,true)});
        else HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7,Render.unitsWide,true)});
    }

    private void update3(){
        HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7,Render.unitsWide,true)});
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
            case 1:
                render1();
                break;
            case 2:
                render2();
                break;
        }
    }

    private void render0(){
        Graphics.drawImage(sprites[0],6,7);
        Graphics.drawImage(sprites[1],40,7);
        Graphics.setFont(Graphics.SMALL_FONT);
        Graphics.drawText("Alright guys, you know what to do; we're looking for a precious yellow gem located in a nearby cave. Go!",20,48,18);
    }

    private void render1(){

    }

    private void render2(){
        Graphics.setColor(1,1,1,1);
        if(wood)Graphics.drawImage(sprites[2],70,7);
    }

    @Override
    public void reset() {

    }
}
