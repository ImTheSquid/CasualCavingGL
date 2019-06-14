package org.level.levels;

import org.engine.AudioManager;
import org.engine.Main;
import org.entities.SmartRectangle;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_ENTER;
import static com.jogamp.newt.event.KeyEvent.VK_SPACE;
import static org.engine.AudioManager.STOP;

public class Title extends Level {
    private FadeIO logo = new FadeIO(0, 1, 0, 0.01f, 40);
    private SmartRectangle start=new SmartRectangle(Render.unitsWide/2,30,20,7,true);
    private SmartRectangle quit=new SmartRectangle(Render.unitsWide/2,3.5f,7,4,true);
    private SmartRectangle controls=new SmartRectangle(Render.unitsWide/2,8,12.5f,3,true);
    private SmartRectangle music=new SmartRectangle(0.5f,2,8,8);
    private boolean controlsVisible=false;

    public Title(ImageResource[] backgrounds, ImageResource[] foregrounds) {
        super(backgrounds,backgrounds.length);
        super.foregrounds = foregrounds;
        logo.setActive(true);
    }

    @Override
    public void init() {

    }

    public void update(int subLevel) {
        AudioManager.setMusicPlayback(STOP);
        if (subLevel == 0) {
            updateLoad();
        } else {
            updateTitle();
        }
    }

    private void updateLoad(){
        if (logo.getDirection() || (logo.getCurrent() > 0)) {
            logo.update();
        } else {
            World.getMaster().setCurrent(0);
            World.getMaster().setActive(true);
            World.setSubLevel(1);
        }
        if (logo.getCurrent() == 1) {
            if(logo.getFrameDelay()==0&&logo.getDirection()){
                logo.setFrameDelay(60);
                logo.setDirection(false);
            }
        }
        if(Keyboard.keys.contains(VK_SPACE)){
            logo.setActive(false);
            logo.setCurrent(0);
            logo.setDirection(true);
            World.getMaster().setCurrent(0);
            World.getMaster().setActive(true);
            World.setSubLevel(1);
        }
    }

    private void updateTitle(){
        start.setActive(!controlsVisible);
        if(World.getMaster().getCurrent()>0.25f&&(!quit.isActive()||!controls.isActive())){
            quit.setActive(true);
            controls.setActive(true);
        }
        quit.update();
        if(quit.isPressed()){
            Render.getGameLoop().setRunning(false);
        }
        start.update();
        if(start.isPressed()||Keyboard.keys.contains(VK_ENTER)){
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.NORMAL);
            LevelController.resetAll();
            World.clearEntites();
            Main.getHarold().reset();
            World.setGame(true);
            World.getMaster().setActive(false);
            World.getMaster().setCurrent(1f);
            World.setLevel(1);
            World.setSubLevel(0);
            AudioManager.setMusicPlayback(AudioManager.PLAY);
        }
        controls.update();
        if(controls.isPressed()){
            controlsVisible=!controlsVisible;
            while(controls.isPressed())controls.update();
        }
        music.update();
        if(music.isPressed()){
            AudioManager.setMusicEnabled(!AudioManager.isMusicEnabled());
            while(music.isPressed())music.update();
        }
    }

    public void render(int subLevel) {
        switch (subLevel) {
            case 0:
                loadingScreen();
                break;
            case 1:
                titleScreen();
                break;
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
        World.setGame(false);
        quit.setActive(false);
        controls.setActive(false);
        World.getMaster().setCurrent(0f);
        World.getMaster().setDirection(true);
        World.getMaster().setActive(true);
    }

    private void loadingScreen() {
        Graphics.setColor(1, 1, 1, 1);
        Graphics.drawImage(backgrounds[0], 0, 0,Render.unitsWide,Render.unitsTall);
        Graphics.setColor(1, 1, 1, logo.getCurrent());
        Graphics.drawImageCentered(foregrounds[0], Render.unitsWide / 2, 55);
        Graphics.setColor(1,1,1,1);
        Graphics.setFont(Graphics.SMALL_FONT);
        Graphics.drawText("SPACE to skip",0.1f,0.7f);
    }

    private void titleScreen() {
        Graphics.setColor(1,1,1,1);
        Graphics.drawImage(backgrounds[1],0,0);
        start.setColor(0,0.5f,0,1);
        start.render();
        Graphics.setColor(1,1,1,1);
        Graphics.setFont(Graphics.TITLE_FONT);
        Graphics.drawTextCentered("Start",Render.unitsWide/2,30);
        quit.setColor(0.5f,0,0,1);
        quit.render();
        controls.setColor(0.8f,0.74f,0.03f,1);
        controls.render();
        Graphics.setColor(1,1,1,1);
        Graphics.setFont(Graphics.REGULAR_FONT);
        Graphics.drawTextCentered("Quit",Render.unitsWide/2,4f);
        Graphics.drawTextCentered("Controls",Render.unitsWide/2,8.3f);
        Graphics.setFont(Graphics.SMALL_FONT);
        Graphics.drawText("Casual Caving 0.2.4",0.1f,0.7f);
        Graphics.drawText("Lunan Productions",Render.unitsWide-Graphics.convertToWorldWidth((float)Graphics.getCurrentFont().getBounds("Lunan Productions").getWidth())-.1f,.7f);
        Graphics.drawImage(ResourceHandler.getMiscLoader().getMusicButton(AudioManager.isMusicEnabled()),0.5f,2,5,5);

        if(controlsVisible){
            Graphics.setColor(0.3f,0.3f,0.3f,.7f);
            Graphics.fillRectCentered(Render.unitsWide/2,Render.unitsTall/2,50,35);
            Graphics.setColor(1,1,1,1);
            Graphics.setFont(Graphics.REGULAR_FONT);
            Graphics.drawTextCentered("Controls",Render.unitsWide/2, Render.unitsTall/2+15);
            Graphics.setFont(Graphics.SMALL_FONT);
            Graphics.drawTextCentered("W: Attack (part 2 and above)",Render.unitsWide/2,Render.unitsTall/2+11);
            Graphics.drawTextCentered("A/D: Left/right",Render.unitsWide/2,Render.unitsTall/2+8);
            Graphics.drawTextCentered("Space: Jump",Render.unitsWide/2,Render.unitsTall/2+5);
        }
    }
}
