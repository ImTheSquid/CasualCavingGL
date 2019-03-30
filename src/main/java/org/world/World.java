package org.world;

import org.entities.Entity;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.level.LevelController;

import java.util.concurrent.ConcurrentLinkedQueue;

import static com.jogamp.newt.event.KeyEvent.VK_R;

public class World {
    private static float brightness=1f;//Controls master brightness
    private static int level=0,subLevel=0;
    private static boolean game=false,pause=false;//Set whether in game or menu. Set pause status
    private static ConcurrentLinkedQueue<Entity> entites=new ConcurrentLinkedQueue<Entity>();

    public static void update(){
        if(Render.getWindow().getWidth()!=Render.screenWidth||Render.getWindow().getHeight()!=Render.screenHeight){
            if(Keyboard.keys.contains(VK_R))Render.getWindow().setSize(Render.screenWidth,Render.screenHeight);
            return;
        }
        if(level==0)game=false;

        LevelController.update(level,subLevel);
        //TODO implement render stages (pre-update,update,post-update)
        for(Entity e:entites){
            if((pause||!game)&&e.getNonGameUpdate())e.update();
        }
    }

    public static void render(){
        if(Render.getWindow().getWidth()!=Render.screenWidth||Render.getWindow().getHeight()!=Render.screenHeight){
            Graphics.setColor(.9f,0,0,1);
            Graphics.setFont(Graphics.REGULAR_FONT);
            Graphics.drawText("Please resize your window to 1280x720, or press R",0,Graphics.convertToWorldY(Render.getWindow().getHeight()-90));
            return;
        }

        LevelController.render(level,subLevel);
        //TODO implement render stages (pre-render,render,post-render)
        for(Entity e:entites){
            if((pause||!game)&&e.getNonGameRender())e.render();
        }

        //Master brightness, always do last
        Graphics.setColor(0,0,0,1-brightness);
        Graphics.drawRect(0,0, Render.unitsWide,Render.unitsTall);
        Graphics.setColor(1,1,1,1);//Reset color
    }

    public static void addEntity(Entity e){
        entites.offer(e);
    }

    public static void removeEntity(Entity e){
        entites.remove(e);
    }

    public static void clearEntites(){
        entites.clear();
    }

    public static void setGame(boolean game) {
        World.game = game;
    }

    public static void setPause(boolean pause) {
        World.pause = pause;
    }

    public static void setBrightness(float brightness) {
        World.brightness = brightness;
    }

    public static void setLevel(int level) {
        World.level = level;
    }

    public static void setSubLevel(int subLevel) {
        World.subLevel = subLevel;
    }

    public static int getLevel() {
        return level;
    }

    public static int getSubLevel() {
        return subLevel;
    }
}
