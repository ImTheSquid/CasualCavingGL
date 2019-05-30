package org.level.levels;

import org.engine.Main;
import org.entities.aggressive.ShortGolem;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.graphics.Render;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.HeightMap;
import org.world.HeightVal;
import org.world.World;

public class Level3 extends Level {
    private FadeIO isolsi=new FadeIO(0,1,0,0.02f,35);
    private FadeIO hematus=new FadeIO(0,1,0,0.02f,35);
    private FadeIO igneox=new FadeIO(0,1,0,0.02f,35);
    private ImageResource[] sprites= ResourceHandler.getLevelLoader().getLevel3Sprites();
    private boolean fadeActive=false;
    private int switchFade=0;
    public Level3(ImageResource[] backgrounds) {
        super(backgrounds,backgrounds.length);
        reset();
    }

    @Override
    public void init() {
        ResourceHandler.getHaroldLoader().disableAttackPause();
        World.clearEntites();
        World.addEntities(super.getEntityRegisterArray());
    }

    @Override
    public void update(int subLevel) {
        checkHealthVals();
        if(subLevel!=6)HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7, Render.unitsWide,true)});//Set heights
        else HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7,87,true),new HeightVal(63,29,Render.unitsWide,false),new HeightVal(87,29,Render.unitsWide,true)});
        World.clearEntites();
        World.addEntities(super.getEntityRegisterArray());
        if(subLevel!=1)ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
        if(subLevel!=2)leftLimit=-1;
        switch (subLevel){
            case 1:
                update1();
                break;
            case 2:
                update2();
                break;
            case 6:
                update6();
                break;
        }
    }

    private void update1(){
        if(Main.getHarold().getX()>50&&igneox.getCurrent()<1)fadeActive=true;
        if(fadeActive) {
            Main.getHarold().setMovement(false);
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.TURN);
            switch(switchFade){
                case 0:
                    if(isolsi.getCurrent()<1){
                        isolsi.setActive(true);
                        isolsi.update();
                    }else{
                        isolsi.setActive(false);
                        switchFade++;
                    }
                    break;
                case 1:
                    if(hematus.getCurrent()<1){
                        hematus.setActive(true);
                        hematus.update();
                    }else{
                        hematus.setActive(false);
                        switchFade++;
                    }
                    break;
                case 2:
                    if(igneox.getCurrent()<1){
                        igneox.setActive(true);
                        igneox.update();
                    }else{
                        igneox.setActive(false);
                        fadeActive=false;
                        World.setMasterColor(1,1,1);
                        World.getMaster().setDirection(false);
                        World.getMaster().setActive(true);
                    }
            }
        }else{
            if(switchFade>0&&World.getMaster().getCurrent()==0) {
                World.setSubLevel(World.getSubLevel() + 1);
                World.getMaster().setDirection(true);
                Main.getHarold().setMovement(true);
                ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
            }
        }
    }

    private void update2(){
        leftLimit=0;
    }

    private void update6(){
        if(Main.getHarold().getWidth()+Main.getHarold().getX()==Render.unitsWide)World.setLevelTransition(true);
    }

    @Override
    public void render(int subLevel) {
        Graphics.drawImage(backgrounds[subLevel],0,0);
        if (subLevel == 1) {
            render1();
        }
    }

    private void render1(){
        if(isolsi.getCurrent()>0) {
            Graphics.setColor(1, 1, 1, isolsi.getCurrent());
            Graphics.drawImage(sprites[0], 0, 0);
        }
        if(hematus.getCurrent()>0){
            Graphics.setColor(1, 1, 1, hematus.getCurrent());
            Graphics.drawImage(sprites[1], 0, 0);
        }
        if(igneox.getCurrent()>0){
            Graphics.setColor(1, 1, 1, igneox.getCurrent());
            Graphics.drawImage(sprites[igneoxCalc()+1], 0, 0);
        }
    }

    private int igneoxCalc(){
        if(igneox.getCurrent()==1){
            return 4;
        }else if(igneox.getCurrent()>.75f){
            return 3;
        }else if(igneox.getCurrent()>.5f){
            return 2;
        }else{
            return 1;
        }
    }

    @Override
    public void renderForeground(int subLevel) {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public void reset() {
        isolsi.setActive(false);
        isolsi.setCurrent(0);
        hematus.setActive(false);
        hematus.setCurrent(0);
        igneox.setActive(false);
        igneox.setCurrent(0);
        clearEntityRegister();
        entityRegister.add(new ShortGolem(ShortGolem.BLUE,5,20,7));
        entityRegister.add(new ShortGolem(ShortGolem.GREEN,6,79,34));
    }
}
