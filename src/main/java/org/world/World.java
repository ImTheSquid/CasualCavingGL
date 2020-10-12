package org.world;

import org.engine.AudioManager;
import org.engine.Main;
import org.entities.Entity;
import org.entities.SmartRectangle;
import org.graphics.Graphics;
import org.graphics.Notification;
import org.graphics.Render;
import org.graphics.Timer;
import org.input.Keyboard;
import org.level.LevelController;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.jogamp.newt.event.KeyEvent.VK_ESCAPE;
import static com.jogamp.newt.event.KeyEvent.VK_F2;
import static org.engine.AudioManager.MUSIC_VOL;
import static org.graphics.Graphics.*;

public class World {
    private static Timer master = new Timer(0, 1, 1, 0.02f, 35);
    private static Timer tFade = new Timer(0, 1, 0, 0.02f, 35);//Fade controller for level transitions
    private static int level = 0, subLevel = 0, assetLoaderCounter = 0, latestCheckpoint = -1;
    public static final int CHECK_START = -1, CHECK_LARANO = 0, CHECK_LARANO_FINISH = 1;
    private static boolean game = false, pause = false, levelTransition = false, transitionDir = true, isHaroldEvil = false;//Set whether in game or menu. Set pause status
    private static float masterRed = 0, masterGreen = 0, masterBlue = 0;
    private static float gravity = 70f;
    private static ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();//Entity registry
    private static ConcurrentLinkedQueue<Notification> notifications = new ConcurrentLinkedQueue<>();
    private static SmartRectangle pauseReturn = new SmartRectangle(Render.unitsWide / 2, 30, 20, 5, true);//Button detectors
    private static SmartRectangle pauseTitleReturn = new SmartRectangle(Render.unitsWide / 2, 6.6f, 18, 4, true);
    private static SmartRectangle musicControl = new SmartRectangle(0.5f, 0.5f, 5, 5);

    public static void update(float deltaTime) {
        Debug.update();
        if (Render.getWindow().getWidth() != Render.virtual_width || Render.getWindow().getHeight() != Render.virtual_height) {
            Render.getWindow().setSize(Render.virtual_width, Render.virtual_height);
            Notification resWarn = new Notification("Resolution Warning", "This game only supports a resolution of 1280x720", ResourceHandler.getMiscLoader().getResolutionWarning());
            if (!notificationPresent(resWarn)) newNotification(resWarn);
        }

        if (Keyboard.keys.contains(VK_ESCAPE) && game && !levelTransition) {
            pause = !pause;
            AudioManager.handlePause(pause);
            //noinspection StatementWithEmptyBody
            while (Keyboard.keys.contains(VK_ESCAPE)) {
            }//Wait for key release
        }

        if (level == 0) game = false;

        LevelController.update(level, subLevel, deltaTime);
        //TODO implement render stages (pre-update,update,post-update)
        entities.removeIf(n -> n.getHealth() <= 0);
        for(Entity e: entities){
            if(e.getSubLevel()!=subLevel)continue;
            if(pause){
                if (e.getPauseUpdate()) e.update(deltaTime);
            }else if(!game){
                if (e.getNonGameUpdate()) e.update(deltaTime);
            }else{
                e.update(deltaTime);
            }
        }

        if (level > 0 && !pause) Main.getHarold().update(deltaTime);

        if(pause){
            pauseReturn.setActive(true);
            pauseReturn.update(deltaTime);
            pauseTitleReturn.setActive(true);
            pauseTitleReturn.update(deltaTime);
            if(pauseReturn.isPressed())pause=false;
            if(pauseTitleReturn.isPressed()){
                setLevel(0);
                setSubLevel(1);
                LevelController.resetAll();
                Main.getHarold().reset();
                pause = false;
            }
            musicControl.update(deltaTime);
            if(musicControl.isPressed()){
                AudioManager.setMusicEnabled(!AudioManager.isMusicEnabled());
                while (musicControl.isPressed()) musicControl.update(deltaTime);
            }
        }else{
            pauseReturn.setActive(false);
            pauseTitleReturn.setActive(false);
        }

        levelTransUpdate();
        updateNotifications();

        //Master brightness code
        if(!pause)master.update();
        tFade.update();
    }

    private static void levelTransUpdate(){
        if(levelTransition){
            AudioManager.fadeOut();
            if(master.getCurrent()>0){
                master.setDirection(false);
                master.setActive(true);
            }else if(transitionDir) {
                if (tFade.getCurrent() == 1&&assetLoaderCounter>=(LevelController.getLevels()[level+2].getAssets()!=null?LevelController.getLevels()[level+2].getAssets().length:0)) {
                    transitionDir = false;
                    tFade.setSecondDelay(2);
                    tFade.setDirection(false);
                } else {
                    tFade.setDirection(true);
                    tFade.setActive(true);
                }
            }else if(tFade.getCurrent()==0){
                transitionDir =true;
                levelTransition=false;
                LevelController.cleanup(level);
                level++;
                LevelController.init(level);
                subLevel=0;
                Main.getHarold().setMovement(true);
                Main.getHarold().setX(5);
                master.setActive(false);
                master.setCurrent(1);
                AudioManager.handleLevelTransition(level);
                assetLoaderCounter=0;
                entities.clear();
            }
        }
    }

    private static void updateNotifications(){
        for(Notification n:notifications){
            n.update();
        }
        notifications.removeIf(Notification::isDone);
    }

    public static void render(){
        if(Render.getWindow().getWidth()!=Render.virtual_width ||Render.getWindow().getHeight()!=Render.virtual_height)return;

        LevelController.render(level,subLevel);
        //TODO implement render stages (pre-render,render,post-render)
        for(Entity e: entities){
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

        //Master brightness, always do last
        setDrawColor(masterRed,masterGreen,masterBlue,1-master.getCurrent());
        setIgnoreScale(true);
        fillRect(0,0, Render.unitsWide,Render.unitsTall);
        setIgnoreScale(false);
        setDrawColor(1,1,1,1);//Reset color

        //Special case level transition
        if(levelTransition){
            levelTransition();
        }else Main.getHarold().renderHealth();

        if(pause){
            setIgnoreScale(true);
            setFollowCamera(true);
            setDrawColor(.25f,.25f,.25f,.4f);
            fillRect(0,0,Render.unitsWide,Render.unitsTall);
            setDrawColor(1,1,1,1);
            setFont(TITLE);
            drawTextCentered("Paused",Render.unitsWide/2,40);
            pauseReturn.setColor(0.721f, 0.721f, 0.721f,1f);
            pauseReturn.render();
            setDrawColor(1,1,1,1);
            setFont(NORMAL);
            drawTextCentered("Back to Game",Render.unitsWide/2,30);
            pauseTitleReturn.setColor(0.6f, 0, 0,1);
            pauseTitleReturn.render();
            setDrawColor(1,1,1,1);
            drawTextCentered("Quit to Title",Render.unitsWide/2,7);
            drawImage(ResourceHandler.getMiscLoader().getMusicButton(AudioManager.isMusicEnabled()),0.5f,0.5f,5,5);
            setIgnoreScale(false);
            setFollowCamera(false);
        }
        renderNotifications();
        Debug.render();
        setDrawColor(1,1,1,1);
        if(Keyboard.keys.contains(VK_F2)){
            Graphics.takeScreenshot();
            //noinspection StatementWithEmptyBody
            while(Keyboard.keys.contains(VK_F2)){}
        }
    }

    private static void levelTransition(){
        setMasterColor(0,0,0);
        setDrawColor(1,1,1,tFade.getCurrent());
        setFont(TITLE);
        drawTextCentered("Part "+(level+1),50,35);
        if(level==0)ResourceHandler.getHaroldLoader().setState(HaroldLoader.NORMAL);
        else ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
        if(tFade.getCurrent()==1&&assetLoaderCounter<(LevelController.getLevels()[level+2].getAssets()==null?0:LevelController.getLevels()[level+2].getAssets().length)){
            LevelController.getLevels()[level+2].getAssets()[World.getAssetLoaderCounter()].preloadTexture();
            World.incrementAssetLoadCount();
            renderAssetLoadingIndicator(LevelController.getLevels()[level+2].getAssets().length);
        }
    }

    private static void renderNotifications(){
        float yOffset=Render.unitsTall-Notification.getHeight();
        for(Notification n:notifications){
            n.render(yOffset);
            yOffset-=Notification.getHeight();
        }
    }

    static void renderAssetLoadingIndicator(int numAssetsToLoad){
        setFont(SMALL);
        drawText("Loading assets... ("+assetLoaderCounter+"/"+numAssetsToLoad+")",0.5f,1f);
    }

    public static void addEntity(Entity e){
        if(!entities.contains(e)) entities.offer(e);
    }

    public static void addEntities(Collection<? extends Entity> list){
        for(Entity e:list){
            addEntity(e);
        }
    }

    private static void newNotification(Notification n){
        if(!notifications.contains(n))notifications.offer(n);
    }

    private static boolean notificationPresent(Notification n){
        for (Notification notification : notifications) {
            if (n.compareTo(notification) == 0) return true;
        }
        return false;
    }

    public static void newCheckpoint(int checkpoint){
        latestCheckpoint=checkpoint;
        switch (latestCheckpoint){
            case CHECK_LARANO:
                World.newNotification(new Notification("Checkpoint Unlocked","Larano",ResourceHandler.getMiscLoader().getCheckmark()));
                break;
            case CHECK_LARANO_FINISH:
                World.newNotification(new Notification("Checkpoint Unlocked","Larano's Defeat",ResourceHandler.getMiscLoader().getCheckmark()));
                break;
        }
    }

    public static void startFromCheckpoint(){
        AudioManager.setMusicGain(MUSIC_VOL);
        switch(latestCheckpoint){
            case CHECK_START:
                setLevel(1);
                setSubLevel(0);
                AudioManager.setMusicPlayback(AudioManager.PLAY);
                break;
            case CHECK_LARANO:
                setLevel(5);
                setSubLevel(0);
                AudioManager.setMusicPlayback(AudioManager.STOP);
                break;
            case CHECK_LARANO_FINISH:
                setLevel(6);
                setSubLevel(0);
                AudioManager.setMusicPlayback(AudioManager.STOP);
                break;
        }
    }

    public static void addEntities(Entity[] array){
        for(Entity e:array){
            addEntity(e);
        }
    }

    public static void removeEntity(Entity e){
        entities.remove(e);
    }

    public static void clearEntites(){
        entities.clear();
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

    public static Timer getMaster(){return master;}

    public static float getGravity() {
        return gravity;
    }

    public static void setGravity(float gravity) {
        World.gravity = gravity;
    }

    static int getNumLevels(){return LevelController.getNumLevels();}

    static int getNumSubLevels(){return LevelController.getNumSubLevels();}

    public static ConcurrentLinkedQueue<Entity> getEntities() {
        return entities;
    }

    public static void setLevelTransition(boolean levelTransition) {
        World.levelTransition = levelTransition;
    }

    public static void setMasterColor(float red,float green,float blue){
        masterRed=red;
        masterBlue=blue;
        masterGreen=green;
    }

    static void incrementAssetLoadCount(){
        assetLoaderCounter++;
    }

    static int getAssetLoaderCounter() {
        return assetLoaderCounter;
    }

    static void resetAssetLoaderCounter() {assetLoaderCounter=0;}

    public static int getLatestCheckpoint() {
        return latestCheckpoint;
    }

    public static void resetCheckpoints(){latestCheckpoint=-1;}

    public static void setIsHaroldEvil(boolean isHaroldEvil) {
        World.isHaroldEvil = isHaroldEvil;
    }

    public static boolean isHaroldEvil() {
        return isHaroldEvil;
    }

    public static void incrementSubLevel(){
        subLevel++;
    }
}
