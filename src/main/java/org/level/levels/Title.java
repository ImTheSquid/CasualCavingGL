package org.level.levels;

import org.engine.AudioManager;
import org.engine.Main;
import org.entities.SmartRectangle;
import org.graphics.FadeIO;
import org.graphics.Render;
import org.input.Keyboard;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;

import java.net.MalformedURLException;
import java.net.URL;

import static com.jogamp.newt.event.KeyEvent.VK_ENTER;
import static com.jogamp.newt.event.KeyEvent.VK_SPACE;
import static org.engine.AudioManager.STOP;
import static org.graphics.Graphics.*;
import static org.world.World.*;


public class Title extends Level {
    private FadeIO logo = new FadeIO(0, 1, 0, 0.01f, 40);
    private SmartRectangle start=new SmartRectangle(Render.unitsWide/2,30,20,7,true);
    private SmartRectangle quit=new SmartRectangle(Render.unitsWide/2,3.5f,7,4,true);
    private SmartRectangle controls=new SmartRectangle(Render.unitsWide/2,8,12.5f,3,true);
    private SmartRectangle music=new SmartRectangle(0.5f,2,8,8);
    private SmartRectangle credits=new SmartRectangle(89,2,10,3);
    private SmartRectangle creditFontA=new SmartRectangle(44,30,8,1);
    private SmartRectangle creditFontB=new SmartRectangle(55,30,10,1);
    private boolean controlsVisible=false,creditsVisible=false;

    public Title(ImageResource[] backgrounds, ImageResource[] foregrounds) {
        super(backgrounds,backgrounds.length);
        super.foregrounds = foregrounds;
        logo.setActive(true);
    }

    @Override
    public void init() {

    }

    @Override
    public void loadAssets() {

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
            getMaster().setCurrent(0);
            getMaster().setActive(true);
            setSubLevel(1);
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
            getMaster().setCurrent(0);
            getMaster().setActive(true);
            setSubLevel(1);
        }
    }

    private void updateTitle(){
        start.setActive(!(controlsVisible||creditsVisible));
        if(getLatestCheckpoint()>CHECK_START){
            start.setWidth(30);
        }else start.setWidth(20);
        if(getMaster().getCurrent()>0.25f&&(!quit.isActive()||!controls.isActive())){
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
            clearEntites();
            Main.getHarold().reset();
            setGame(true);
            getMaster().setActive(false);
            getMaster().setCurrent(1f);
            startFromCheckpoint();
        }
        controls.update();
        if(controls.isPressed()){
            creditsVisible=false;
            controlsVisible=!controlsVisible;
            while(controls.isPressed())controls.update();
        }
        music.update();
        if(music.isPressed()){
            AudioManager.setMusicEnabled(!AudioManager.isMusicEnabled());
            while(music.isPressed())music.update();
        }
        credits.update();
        if(credits.isPressed()){
            controlsVisible=false;
            creditsVisible=!creditsVisible;
            while(credits.isPressed())credits.update();
        }
        if(creditsVisible){
            creditFontA.setActive(true);
            creditFontB.setActive(true);
            creditFontA.update();
            if(creditFontA.isPressed()){
                try {
                    Main.openURL(new URL("https://levien.com/type/myfonts/inconsolata.html"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                while(creditFontA.isPressed())creditFontA.update();
            }
            creditFontB.update();
            if(creditFontB.isPressed()){
                try {
                    Main.openURL(new URL("https://fonts.google.com/specimen/Merriweather"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                while(creditFontB.isPressed())creditFontB.update();
            }
        }else{
            creditFontA.setActive(false);
            creditFontB.setActive(false);
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
        setGame(false);
        quit.setActive(false);
        controls.setActive(false);
        getMaster().setCurrent(0f);
        getMaster().setDirection(true);
        getMaster().setActive(true);
    }

    private void loadingScreen() {
        setDrawColor(1, 1, 1, 1);
        drawImage(backgrounds[0], 0, 0,Render.unitsWide,Render.unitsTall);
        setDrawColor(1, 1, 1, logo.getCurrent());
        drawImageCentered(foregrounds[0], Render.unitsWide / 2, 55);
        setDrawColor(1,1,1,1);
        setFont(SMALL);
        drawText("SPACE to skip",0.1f,0.7f);
    }

    private void titleScreen() {
        setDrawColor(1,1,1,1);
        drawImage(backgrounds[1],0,0);
        start.setColor(0,0.5f,0,1);
        start.render();
        setDrawColor(1,1,1,1);
        setFont(TITLE);
        if(getLatestCheckpoint()==CHECK_START)drawTextCentered("Start",Render.unitsWide/2,30);
        else drawTextCentered("Resume",Render.unitsWide/2,30);
        quit.setColor(0.5f,0,0,1);
        quit.render();
        controls.setColor(0.8f,0.74f,0.03f,1);
        controls.render();
        credits.setColor(0.8f,0.74f,0.03f,1);
        credits.render();
        setDrawColor(1,1,1,1);
        setFont(NORMAL);
        drawTextCentered("Quit",Render.unitsWide/2,4f);
        drawTextCentered("Controls",Render.unitsWide/2,8.3f);
        drawText("Credits",89,2.4f);
        setFont(SMALL);
        drawText("Casual Caving 0.5.0",0.1f,0.7f);
        drawText("Lunan Productions",Render.unitsWide-convertToWorldWidth((float)getCurrentFont().getBounds("Lunan Productions").getWidth())-.1f,.7f);
        drawImage(ResourceHandler.getMiscLoader().getMusicButton(AudioManager.isMusicEnabled()),0.5f,2,5,5);

        if(controlsVisible){
            setDrawColor(0.3f,0.3f,0.3f,.7f);
            fillRectCentered(Render.unitsWide/2,Render.unitsTall/2,50,35);
            setDrawColor(1,1,1,1);
            setFont(NORMAL);
            drawTextCentered("Controls",Render.unitsWide/2, Render.unitsTall/2+15);
            setFont(SMALL);
            drawTextCentered("W: Attack (part 2 and above)",Render.unitsWide/2,Render.unitsTall/2+11);
            drawTextCentered("A/D: Left/right",Render.unitsWide/2,Render.unitsTall/2+8);
            drawTextCentered("Space: Jump",Render.unitsWide/2,Render.unitsTall/2+5);
        }
        if(creditsVisible){
            setDrawColor(0.3f,0.3f,0.3f,.7f);
            fillRectCentered(Render.unitsWide/2,Render.unitsTall/2,50,35);
            setDrawColor(1,1,1,1);
            setFont(NORMAL);
            drawTextCentered("Credits",Render.unitsWide/2, Render.unitsTall/2+15);
            setFont(SMALL);
            drawTextCentered("Programming and Game Design: Jack Hogan",Render.unitsWide/2,Render.unitsTall/2+11);
            drawTextCentered("Artwork and Game Design: Stuart Lunn",Render.unitsWide/2,Render.unitsTall/2+8);
            drawTextCentered("Music: Chris Hall",Render.unitsWide/2,Render.unitsTall/2+5);
            drawTextCentered("Fonts (click): Inconsolata and Merriweather",Render.unitsWide/2,Render.unitsTall/2+2);
        }
    }
}
