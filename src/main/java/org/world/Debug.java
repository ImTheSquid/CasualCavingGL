package org.world;

import org.engine.AudioManager;
import org.engine.Main;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.input.Mouse;
import org.level.LevelController;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;

import javax.swing.*;

import static com.jogamp.newt.event.KeyEvent.*;

class Debug {
    private static boolean show=false,cheatsUsed=false,assetLoadFinished=true;
    static void update(){
        if(Keyboard.keys.contains(VK_F3)){
            show=!show;
            while(Keyboard.keys.contains(VK_F3)){}
            Render.getGameLoop().overrideUpdateTime();
        }
        if(Keyboard.keys.contains(VK_L)&&World.getLevel()>0){
            while(Keyboard.keys.contains(VK_L)){}
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed) {
                Integer[] levels = new Integer[World.getNumLevels() - 2];
                for (int i = 0; i < levels.length; i++) {
                    levels[i] = i+1;
                }
                Integer x=(Integer) JOptionPane.showInputDialog(null, "Select Level", "Level Selector", JOptionPane.QUESTION_MESSAGE, null, levels, World.getLevel());
                if(x!=null){
                    Graphics.setScaleFactor(1);
                    World.setSubLevel(0);
                    World.setLevel(x);
                    World.clearEntites();
                    AudioManager.handleDebugSwitch(x);
                    Main.getHarold().setFollowCamera(false);
                    if(LevelController.getCurrentLevel().getAssets()!=null&&LevelController.getCurrentLevel().getAssets().length>0)assetLoadFinished=false;
                    if(World.getLevel()>1)ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
                }
                Render.getGameLoop().overrideUpdateTime();
            }
        }
        if(Keyboard.keys.contains(VK_SEMICOLON)&&World.getLevel()>0){
            while(Keyboard.keys.contains(VK_SEMICOLON)){}
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Integer[] sublevels=new Integer[World.getNumSubLevels()];
                for(int i=0;i<sublevels.length;i++){
                    sublevels[i]=i;
                }
                Integer x=(Integer) JOptionPane.showInputDialog(null,"Select Sublevel","Level Selector",JOptionPane.QUESTION_MESSAGE,null,sublevels,World.getSubLevel());
                if(x!=null){
                    Graphics.setScaleFactor(1);
                    World.setSubLevel(x);
                    Main.getHarold().setFollowCamera(false);
                }
                Render.getGameLoop().overrideUpdateTime();
            }
        }
        if(Keyboard.keys.contains(VK_H)&&World.getLevel()>0){
            while(Keyboard.keys.contains(VK_H)){}
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Main.getHarold().setInvincible(!Main.getHarold().isInvincible());
                Render.getGameLoop().overrideUpdateTime();
            }
        }
        checkCam();
    }

    private static void checkCam(){
        if(Keyboard.keys.contains(VK_UP)){
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Render.setCameraY(Render.getCameraY()+1);
            }
        }
        if(Keyboard.keys.contains(VK_DOWN)){
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Render.setCameraY(Render.getCameraY()-1);
            }
        }
        if(Keyboard.keys.contains(VK_LEFT)){
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Render.setCameraX(Render.getCameraX()-1);
            }
        }
        if(Keyboard.keys.contains(VK_RIGHT)){
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Render.setCameraX(Render.getCameraX()+1);
            }
        }
        if(Keyboard.keys.contains(VK_R)){
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Render.setCameraX(0);
                Render.setCameraY(0);
            }
        }
        if(Keyboard.keys.contains(VK_HOME)){
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Render.setCameraX(0);
            }
        }
        if(Keyboard.keys.contains(VK_PAGE_DOWN)){
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Render.setCameraY(0);
            }
        }
        if(Keyboard.keys.contains(VK_END)){
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Render.setCameraX(Graphics.convertToWorldWidth(LevelController.getCurrentLevel().getBackgrounds()[World.getSubLevel()].getTexture().getWidth())-100);
            }
        }
        if(Keyboard.keys.contains(VK_PAGE_UP)){
            if(!cheatsUsed)cheatsUsed=firstRunEvent();
            if(cheatsUsed){
                Render.setCameraY(Graphics.convertToWorldHeight(LevelController.getCurrentLevel().getBackgrounds()[World.getSubLevel()].getTexture().getHeight())-Render.unitsTall);
            }
        }
    }
    //Returns true if going ahead with cheats, returns false to exit
    private static boolean firstRunEvent(){
        int x=JOptionPane.showConfirmDialog(null,"Are you sure you want to enable cheats?\nThe game may become unstable.","Are you sure?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        return x != JOptionPane.NO_OPTION;
    }

    static void render(){
        if(!assetLoadFinished){
            if(World.getAssetLoaderCounter()<(LevelController.getLevels()[World.getLevel()+1].getAssets()==null?0:LevelController.getLevels()[World.getLevel()+1].getAssets().length)) {
                LevelController.getLevels()[World.getLevel()+1].getAssets()[World.getAssetLoaderCounter()].preloadTexture();
                World.incrementAssetLoadCount();
                World.renderAssetLoadingIndicator(LevelController.getLevels()[World.getLevel()+1].getAssets().length);
            }else{
                assetLoadFinished=true;
                World.resetAssetLoaderCounter();
            }
        }
        if(!show)return;
        Graphics.setFollowCamera(true);
        Graphics.setIgnoreScale(true);
        Graphics.setFont(Graphics.DEBUG_SMALL);
        Graphics.setDrawColor(.1f, .1f, .1f, .3f);
        Graphics.fillRect(0, Render.unitsTall - 9f, 15, 9f);
        String memory="Memory:"+ getInUseMemoryMB()+"/"+ getMaxMemoryMB()+"MB";
        float charHeight=Graphics.convertToWorldHeight((float)Graphics.getCurrentFont().getBounds("TEST").getHeight());
        float memWidth=Graphics.convertToWorldHeight((float)Graphics.getCurrentFont().getBounds(memory).getWidth())-.1f;
        Graphics.fillRect(99-memWidth, Render.unitsTall - charHeight-1, memWidth+1, charHeight+1);
        Graphics.setDrawColor(1,1,1,1);
        Graphics.drawText("FPS: "+Render.getGameLoop().getCurrentFPS(),.5f,Render.unitsTall-charHeight-.5f);
        Graphics.drawText("X,Y: "+(Math.round(Main.getHarold().getX()*100)/100)+","+(Math.round(Main.getHarold().getY()*100)/100),.5f,Render.unitsTall-2*charHeight-1);
        Graphics.drawText("Lvl,Sublvl: "+World.getLevel()+","+World.getSubLevel(),.5f,Render.unitsTall-3*charHeight-1.5f);
        Graphics.drawText("Mouse X,Y: "+ Math.round(Mouse.getX())+","+Math.round(Mouse.getY()),.5f,Render.unitsTall-4*charHeight-2f);
        Graphics.drawText(memory,99.5f-memWidth,Render.unitsTall-charHeight-.5f);
        Graphics.setIgnoreScale(false);
        Graphics.setFollowCamera(false);
    }

    private static long getInUseMemoryMB(){
        return (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(long)Math.pow(1024,2);
    }

    private static long getMaxMemoryMB(){
        return Runtime.getRuntime().maxMemory()/(long)Math.pow(1024,2);
    }
}
