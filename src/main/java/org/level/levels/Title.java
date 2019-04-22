package org.level.levels;

import org.entities.SmartRectangle;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_ENTER;
import static com.jogamp.newt.event.KeyEvent.VK_SPACE;

public class Title extends Level {
    private FadeIO logo = new FadeIO(0, 1, 0, 0.01f, 40);
    private SmartRectangle start=new SmartRectangle(Render.unitsWide/2,30,20,7,true);
    private SmartRectangle quit=new SmartRectangle(Render.unitsWide/2,5,7,4,true);
    private boolean gameReady = false;

    public Title(ImageResource[] backgrounds, ImageResource[] foregrounds) {
        super(backgrounds,2);
        super.foregrounds = foregrounds;
        logo.setActive(true);
    }

    public void update(int subLevel) {
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
            World.setSubLevel(1);
        }
    }

    private void updateTitle(){
        if(World.getMaster().isActive())gameReady=true;
        if(World.getMaster().getDirection()&&World.getMaster().getCurrent()==1f&&!gameReady) {
            World.getMaster().setCurrent(0);
            World.getMaster().setActive(true);
        }
        if(World.getMaster().getCurrent()>0.5f&&!quit.isActive())quit.setActive(true);
        quit.update();
        if(quit.isPressed()){
            Render.getGameLoop().setRunning(false);
        }
        start.update();
        if(start.isPressed()||Keyboard.keys.contains(VK_ENTER)){
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.NORMAL);
            World.setGame(true);
            World.getMaster().setActive(false);
            World.getMaster().setCurrent(1f);
            World.setLevel(1);
            World.setSubLevel(0);
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
        Graphics.setColor(1,1,1,1);
        Graphics.setFont(Graphics.REGULAR_FONT);
        Graphics.drawTextCentered("Quit",Render.unitsWide/2,5.3f);
        Graphics.setFont(Graphics.SMALL_FONT);
        Graphics.drawText("Casual Caving 0.0.6",0.1f,0.7f);
    }
}
