package org.level.levels;

import org.engine.Main;
import org.entities.SmartRectangle;
import org.entities.passive.Crowd;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.HeightMap;
import org.world.HeightVal;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_E;

public class Level1 extends Level {
    public Level1(ImageResource[] backgrounds) {
        super(backgrounds,backgrounds.length);
    }
    private ImageResource[] sprites= ResourceHandler.getLevelLoader().getLevel1Sprites();
    private boolean bridge=false,wood=true;
    private Crowd crowd=new Crowd();
    private SmartRectangle log=new SmartRectangle(68,7,10,11);
    private SmartRectangle river=new SmartRectangle(40,0,60,Render.unitsTall);

    @Override
    public void init() {

    }

    @Override
    public ImageResource[] getAssets() {
        return null;
    }

    public void update(int subLevel, float deltaTime) {
        leftLimit = -1;
        if (!World.getEntities().contains(crowd) && crowd.getSubLevel() < 6) World.addEntity(crowd);
        else if (crowd.getSubLevel() == 6) World.removeEntity(crowd);
        if (subLevel == 0) {
            leftBound = 65;
        } else {
            leftBound = 0;
        }
        if (subLevel != 3 && !(subLevel >= 5)) {
            rightLimit = Render.unitsWide + 1;
            crowd.updateSublevel(World.getSubLevel());
        } else if (subLevel >= 5) {
            rightLimit = Render.unitsWide;
            leftLimit = 0;
        }
        switch (subLevel) {
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
            case 4:
                update4();
                break;
            case 5:
                update5();
                break;
            case 6:
                update6();
                break;
        }
    }

    private void update0(){
        HeightMap.setHeights(new HeightVal(0,7,Render.unitsWide,true));
    }

    private void update1() {HeightMap.setHeights(new HeightVal(0,7,Render.unitsWide,true));}

    private void update2(){
        crowd.setWood(wood);
        if(wood)HeightMap.setHeights(new HeightVal(0,7,70,true),new HeightVal(70,13,76,true),new HeightVal(76,7,Render.unitsWide,true));
        else HeightMap.setHeights(new HeightVal(0,7,Render.unitsWide,true));
        if(ResourceHandler.getHaroldLoader().getState()== HaroldLoader.CHAINSAW&&log.intersects(Main.getHarold().getHitbox())&&
        Keyboard.keys.contains(VK_E)){
            wood=false;
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.WOOD);
        }
    }

    private void update3(){
        HeightMap.setHeights(new HeightVal(0,7,Render.unitsWide,true));
        if(!wood&&river.intersects(Main.getHarold().getHitbox())&&Keyboard.keys.contains(VK_E)){
            bridge=true;
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.NORMAL);
        }
        if(!bridge){
            rightLimit=45;
        }else{
            rightLimit=Render.unitsWide+1;
            crowd.updateSublevel(World.getSubLevel());
        }
    }

    private void update4(){
        HeightMap.setHeights(new HeightVal(0,7,Render.unitsWide,true));
        crowd.updateSublevel(World.getSubLevel());
    }

    private void update5(){
        HeightMap.setHeights(new HeightVal(0,7,Render.unitsWide,true));
        crowd.updateSublevel(World.getSubLevel());
        if(World.getMaster().getCurrent()==0){
            World.setSubLevel(World.getSubLevel()+1);
            Main.getHarold().setX(50);
        }
    }

    private void update6(){
        HeightMap.setHeights(new HeightVal(0,14,24,true),new HeightVal(24,7,Render.unitsWide,true));
        if(!World.getMaster().getDirection()&&Main.getHarold().getX()<=80){
            World.getMaster().setDirection(true);
            World.getMaster().setSecondDelay(1);
        }
        if(Main.getHarold().getX()>80){
            World.setLevelTransition(true);
            Main.getHarold().setMovement(false);
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
            case 3:
                render3();
                break;
            case 6:
                render6();
                break;
        }
    }

    @Override
    public void renderForeground(int subLevel) {
        if(World.getSubLevel()==6){
            Graphics.drawImage(sprites[7],2,2);
            Graphics.drawImage(sprites[8],70,2);
        }
    }

    @Override
    public void cleanup() {
        World.removeEntity(crowd);
    }

    private void render0(){
        Graphics.drawImage(sprites[0],6,7);
        Graphics.drawImage(sprites[1],40,7);
        Graphics.setFont(Graphics.SMALL);
        Graphics.drawText("Alright guys, you know what to do; we're looking for a precious yellow gem located in a nearby cave. Go!",20,48,22,true);
    }

    private void render1(){

    }

    private void render2(){
        Graphics.setDrawColor(1,1,1,1);
        if(wood){
            Graphics.drawImage(sprites[2],70,7);
            Graphics.setFont(Graphics.SMALL);
            if(log.intersects(Main.getHarold().getHitbox())&&ResourceHandler.getHaroldLoader().getState()==HaroldLoader.CHAINSAW)Graphics.drawTextWithBox("Press E to cut",70,20);
        }
    }

    private void render3(){
        Graphics.setDrawColor(1,1,1,1);
        Graphics.setFont(Graphics.SMALL);
        if(!bridge){
            if(ResourceHandler.getHaroldLoader().getState()==HaroldLoader.WOOD&& river.intersects(Main.getHarold().getHitbox()))Graphics.drawTextWithBox("Press E to place wood",47,20);
        }else{
            Graphics.drawImage(sprites[3],41,5);
        }
    }

    private void render6(){
        Graphics.drawImage(sprites[5],28,17);
        Graphics.drawImage(sprites[6],50,-3);
        Graphics.drawImage(sprites[4],80,40);
    }

    @Override
    public void reset() {
        crowd.reset();
        bridge=false;
        wood=true;
    }
}
