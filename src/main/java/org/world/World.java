package org.world;

import org.engine.Main;
import org.entities.Entity;
import org.entities.SmartRectangle;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.level.LevelController;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.jogamp.newt.event.KeyEvent.VK_ESCAPE;
import static com.jogamp.newt.event.KeyEvent.VK_R;

public class World {
    private static FadeIO master=new FadeIO(0,1,1,0.02f,35);
    private static FadeIO tFade=new FadeIO(0,1,0,0.02f,35);//Fade controller for level transitions
    private static int level=0,subLevel=0;
    private static boolean game=false,pause=false,levelTransition=false,transititonDir=true;//Set whether in game or menu. Set pause status
    private static float gravity=0.15f;
    private static float masterRed=0,masterGreen=0,masterBlue=0;
    private static ConcurrentLinkedQueue<Entity> entites=new ConcurrentLinkedQueue<>();//Entity registry
    private static SmartRectangle pauseReturn=new SmartRectangle(Render.unitsWide/2,30,20,5,true);//Button detectors
    private static SmartRectangle pauseTitleReturn=new SmartRectangle(Render.unitsWide/2,6.6f,18,4,true);

    public static void update(){
        //if(!entites.contains(Main.getHarold()))World.addEntity(Main.getHarold());
        Debug.update();
        if(Render.getWindow().getWidth()!=Render.screenWidth||Render.getWindow().getHeight()!=Render.screenHeight){
            if(Keyboard.keys.contains(VK_R))Render.getWindow().setSize(Render.screenWidth,Render.screenHeight);
            return;
        }

        if(Keyboard.keys.contains(VK_ESCAPE)&&game&&!levelTransition){
            pause=!pause;
            while(Keyboard.keys.contains(VK_ESCAPE)){}//Wait for key release
        }

        if(level==0)game=false;

        LevelController.update(level,subLevel);
        //TODO implement render stages (pre-update,update,post-update)
        entites.removeIf(n->n.getHealth()<=0);
        for(Entity e:entites){
            if(e.getSubLevel()!=subLevel)continue;
            if(pause){
                if(e.getPauseUpdate())e.update();
            }else if(!game){
                if(e.getNonGameUpdate())e.update();
            }else{
                e.update();
            }
        }

        if(level>0)Main.getHarold().update();

        if(pause){
            pauseReturn.setActive(true);
            pauseReturn.update();
            pauseTitleReturn.setActive(true);
            pauseTitleReturn.update();
            if(pauseReturn.isPressed())pause=false;
            if(pauseTitleReturn.isPressed()){
                level = 0;
                subLevel = 1;
                LevelController.resetAll();
                Main.getHarold().reset();
                pause = false;
            }
        }else{
            pauseReturn.setActive(false);
            pauseTitleReturn.setActive(false);
        }

        if(levelTransition){
            if(master.getCurrent()>0){
                master.setDirection(false);
                master.setActive(true);
            }else
            if(transititonDir) {
                if (tFade.getCurrent() == 1) {
                    transititonDir = false;
                    tFade.setSecondDelay(2);
                    tFade.setDirection(false);
                } else {
                    tFade.setDirection(true);
                    tFade.setActive(true);
                }
            }else if(tFade.getCurrent()==0){
                transititonDir=true;
                levelTransition=false;
                LevelController.cleanup(level);
                level++;
                LevelController.init(level);
                subLevel=0;
                Main.getHarold().setMovement(true);
                Main.getHarold().setX(5);
                master.setActive(false);
                master.setCurrent(1);
            }
        }

        //Master brightness code
        if(!pause)master.update();
        tFade.update();
    }

    public static void render(){
        if(Render.getWindow().getWidth()!=Render.screenWidth||Render.getWindow().getHeight()!=Render.screenHeight){
            Graphics.setColor(.9f,0,0,1);
            Graphics.setFont(Graphics.REGULAR_FONT);
            Graphics.drawText("Please resize your window to 1280x720, or press R to automatically resize.",0,Graphics.convertToWorldY(Render.getWindow().getHeight()-90));
            return;
        }

        LevelController.render(level,subLevel);
        //TODO implement render stages (pre-render,render,post-render)
        for(Entity e:entites){
            if(e.getSubLevel()!=subLevel)continue;
            if(pause){
                if(e.getPauseRender())e.render();
            }else if(!game){
                if(e.getNonGameRender())e.render();
            }else{
                e.render();
            }
        }

        if(level>0)Main.getHarold().render();

        LevelController.renderForeground(level,subLevel);

        Main.getHarold().renderHealth();

        //Master brightness, always do last
        Graphics.setColor(masterRed,masterGreen,masterBlue,1-master.getCurrent());
        Graphics.fillRect(0,0, Render.unitsWide,Render.unitsTall);
        Graphics.setColor(1,1,1,1);//Reset color

        //Special case level transition
        if(levelTransition){
            levelTransition();
        }

        if(pause){
            Graphics.setColor(.25f,.25f,.25f,.4f);
            Graphics.fillRect(0,0,Render.unitsWide,Render.unitsTall);
            Graphics.setColor(1,1,1,1);
            Graphics.setFont(Graphics.TITLE_FONT);
            Graphics.drawTextCentered("Paused",Render.unitsWide/2,40);
            pauseReturn.setColor(0.721f, 0.721f, 0.721f,1f);
            pauseReturn.render();
            Graphics.setColor(1,1,1,1);
            Graphics.setFont(Graphics.REGULAR_FONT);
            Graphics.drawTextCentered("Back to Game",Render.unitsWide/2,30);
            pauseTitleReturn.setColor(0.6f, 0, 0,1);
            pauseTitleReturn.render();
            Graphics.setColor(1,1,1,1);
            Graphics.drawTextCentered("Quit to Title",Render.unitsWide/2,7);
        }
        Debug.render();
        Graphics.setColor(1,1,1,1);
    }

    private static void levelTransition(){
        Graphics.setColor(1,1,1,tFade.getCurrent());
        Graphics.setFont(Graphics.TITLE_FONT);
        Graphics.drawTextCentered("Part "+(level+1),50,35);
    }

    public static void addEntity(Entity e){
        if(!entites.contains(e))entites.offer(e);
    }

    public static void addEntities(Collection<? extends Entity> list){
        for(Entity e:list){
            addEntity(e);
        }
    }

    public static void addEntities(Entity[] array){
        for(Entity e:array){
            addEntity(e);
        }
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

    public static void setLevel(int level) {
        World.level = level;
        LevelController.init(level);
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

    public static FadeIO getMaster(){return master;}

    public static float getGravity() {
        return gravity;
    }

    public static int getNumLevels(){return LevelController.getNumLevels();}

    public static int getNumSubLevels(){return LevelController.getNumSubLevels();}

    public static ConcurrentLinkedQueue<Entity> getEntites() {
        return entites;
    }

    public static void setLevelTransition(boolean levelTransition) {
        World.levelTransition = levelTransition;
    }

    public static void setMasterColor(float red,float green,float blue){
        masterRed=red;
        masterBlue=blue;
        masterGreen=green;
    }
}
