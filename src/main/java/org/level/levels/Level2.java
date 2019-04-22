package org.level.levels;

import org.engine.Main;
import org.entities.SmartRectangle;
import org.graphics.FadeIO;
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

public class Level2 extends Level {
    private FadeIO subBlink=new FadeIO(0,4,0,1,3);
    private FadeIO choice=new FadeIO(0,1,0,0.02f,35);
    private ImageResource[] sprites= ResourceHandler.getLevelLoader().getLevel2Sprites();
    private SmartRectangle cart=new SmartRectangle(33,21,23,19);
    private SmartRectangle edge=new SmartRectangle(70,0,30,Render.unitsTall);
    private SmartRectangle stoneBox=new SmartRectangle(64,23,13,14);
    private boolean anchor=false,choiceMade=false,choiceDir=true;
    public Level2(ImageResource[][] backgrounds) {
        super(backgrounds,9);
    }

    public void update(int subLevel) {
        if(subLevel!=8){
            Main.getHarold().setVisible(true);
            Main.getHarold().setMovement(true);
        }
        updateBounds(subLevel);
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
            case 4:
                update4();
                break;
            case 7:
                if(choiceMade)update7Post();
                else update7Pre();
                break;
            case 8:
                update8();
                break;
        }
    }

    private void updateBounds(int subLevel){
        switch(subLevel){
            case 2:
            case 3:
            case 4:
                rightLimit=80;
                leftLimit=0;
                break;
            case 5:
                leftLimit=16;
                rightLimit=Render.unitsWide+1;
                ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);//Set Harold to lantern light mode
                break;
            case 7:
            case 8:
                rightLimit=Render.unitsWide;
                leftLimit=0;
                ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);//Set Harold to lantern light mode
                break;
            default:
                rightLimit=Render.unitsWide+1;
                leftLimit=-1;
                ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);//Set Harold to lantern light mode
        }
    }

    private void update0(){
        HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7, Render.unitsWide,true)});
    }

    private void update1(){
        HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7, Render.unitsWide,true)});
        if(subBlink.getCurrent()==0){
            subBlink.setActive(true);
            World.getMaster().setCurrent(0);
        }
        else if(subBlink.getCurrent()==4)subBlink.setActive(false);
        updateDarkness();
        subBlink.update();
    }

    private void update2(){
        HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7, Render.unitsWide,true)});
        if(cart.intersects(Main.getHarold().getHitbox())&&ResourceHandler.getHaroldLoader().getState()!=HaroldLoader.ROPE&& Keyboard.keys.contains(VK_E))ResourceHandler.getHaroldLoader().setState(HaroldLoader.ROPE);
        if(edge.intersects(Main.getHarold().getHitbox())&&Keyboard.keys.contains(VK_E)){
            while(Keyboard.keys.contains(VK_E)){}
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
            World.setSubLevel(World.getSubLevel()+1);
        }
    }

    private void update3(){
        HeightMap.setHeights(new HeightVal[]{new HeightVal(0,7, Render.unitsWide,true)});
        if(cart.intersects(Main.getHarold().getHitbox())&&!anchor&& Keyboard.keys.contains(VK_E))anchor=true;
        if(edge.intersects(Main.getHarold().getHitbox())&&Keyboard.keys.contains(VK_E)){
            while(Keyboard.keys.contains(VK_E)){}
            if(anchor)World.setSubLevel(World.getSubLevel()+1);
            else World.setLevel(-1);
        }
    }

    private void update4(){
        if(edge.intersects(Main.getHarold().getHitbox())&&Keyboard.keys.contains(VK_E)){
            World.setSubLevel(World.getSubLevel()+1);
            Main.getHarold().setX(22);
        }
    }

    private void update7Pre(){
        if(stoneBox.intersects(Main.getHarold().getHitbox())&&Keyboard.keys.contains(VK_E))World.setSubLevel(World.getSubLevel()+1);
    }

    private void update7Post(){

    }

    private void update8(){
        Main.getHarold().setVisible(false);
        Main.getHarold().setMovement(false);

    }

    private void choiceFade(){
        if(choiceDir){

        }else if(choice.getCurrent()==0){

        }
    }

    private void updateDarkness(){
        switch((int)subBlink.getCurrent()){
            case 0:
            case 1:
            case 3:
                World.getMaster().setCurrent(0);
                break;
            case 4:
            case 2:
                World.getMaster().setCurrent(1);
                break;
        }
    }

    public void render(int subLevel) {
        Graphics.drawImage(backgrounds[subLevel],0,0);
        switch(subLevel){
            case 1:
                render1();
                break;
            case 2:
                render2();
                break;
            case 3:
                render3();
                break;
            case 4:
                render4();
                break;
            case 7:
                if(choiceMade)render7Post();
                else render7Pre();
                break;
            case 8:
                render8();
                break;
        }
    }

    private void render1(){
        if(subBlink.getCurrent()==0)Graphics.drawImage(sprites[0],0,0);
        else Graphics.drawImage(sprites[1],0,0);
    }

    private void render2(){
        Graphics.setFont(Graphics.SMALL_FONT);
        if(cart.intersects(Main.getHarold().getHitbox())&&ResourceHandler.getHaroldLoader().getState()!=HaroldLoader.ROPE)Graphics.drawText("Press E to pick up rope",33,42);
        else if(edge.intersects(Main.getHarold().getHitbox())&&ResourceHandler.getHaroldLoader().getState()==HaroldLoader.ROPE)Graphics.drawText("Press E to place rope",81,24);
    }

    private void render3(){
        Graphics.setFont(Graphics.SMALL_FONT);
        if(cart.intersects(Main.getHarold().getHitbox())&&!anchor)Graphics.drawText("Press E to pick up anchor",33,42);
        else if(edge.intersects(Main.getHarold().getHitbox())){
            if(anchor)Graphics.drawText("Press E to place anchor",81,24);
            else Graphics.drawText("Press E to descend",81,24);
        }
    }

    private void render4(){
        Graphics.setFont(Graphics.SMALL_FONT);
        if(edge.intersects(Main.getHarold().getHitbox()))Graphics.drawText("Press E to descend",81,24);
    }

    private void render7Pre(){
        if(stoneBox.intersects(Main.getHarold().getHitbox()))Graphics.drawText("Press E to interact",64,39);
    }

    private void render7Post(){

    }

    private void render8(){
        Graphics.setColor(1,1,1,choice.getCurrent());
        Graphics.drawImage(sprites[3],0,0);
        Graphics.setColor(1,1,1,1);
    }

    @Override
    public void renderForeground(int subLevel) {
        if(foregrounds[subLevel]!=null) Graphics.drawImage(foregrounds[subLevel],0,0);
    }

    @Override
    public void cleanup() {
        Main.getHarold().setVisible(true);
        Main.getHarold().setMovement(true);
    }

    @Override
    public void reset() {
        subBlink.setCurrent(0);
        anchor=false;
        choiceMade=false;
    }
}
