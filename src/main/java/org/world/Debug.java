package org.world;

import org.engine.AudioManager;
import org.engine.Main;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.input.Mouse;
import org.level.LevelController;

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
                    if(LevelController.getCurrentLevel().getNumAssetsToLoad()>0)assetLoadFinished=false;
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
    }
    //Returns true if going ahead with cheats, returns false to exit
    private static boolean firstRunEvent(){
        int x=JOptionPane.showConfirmDialog(null,"Are you sure you want to enable cheats?\nThe game may become unstable.","Are you sure?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        return x != JOptionPane.NO_OPTION;
    }

    static void render(){
        if(!assetLoadFinished)LevelController.getCurrentLevel().loadAssets();
        if(!show)return;
        Graphics.setIgnoreScale(true);
        Graphics.setColor(.2f, .2f, .2f, .4f);
        Graphics.fillRect(0, Render.unitsTall - 9f, 15, 9f);
        Graphics.setColor(1,1,1,1);
        Graphics.setFont(Graphics.DEBUG_SMALL);
        float charHeight=Graphics.convertToWorldHeight((float)Graphics.getCurrentFont().getBounds("TEST").getHeight());
        Graphics.drawText("FPS: "+Render.getGameLoop().getCurrentFPS(),.5f,Render.unitsTall-charHeight-.5f);
        Graphics.drawText("X,Y: "+(Math.round(Main.getHarold().getX()*100)/100)+","+(Math.round(Main.getHarold().getY()*100)/100),.5f,Render.unitsTall-2*charHeight-1);
        Graphics.drawText("Lvl,Sublvl: "+World.getLevel()+","+World.getSubLevel(),.5f,Render.unitsTall-3*charHeight-1.5f);
        Graphics.drawText("Mouse X,Y: "+ Math.round(Mouse.getX())+","+Math.round(Mouse.getY()),.5f,Render.unitsTall-4*charHeight-2f);
        Graphics.setIgnoreScale(false);
    }
}
