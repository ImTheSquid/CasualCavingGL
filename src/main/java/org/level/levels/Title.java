package org.level.levels;

import org.entities.SmartRectangle;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.level.Level;
import org.loader.ImageResource;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_SPACE;

public class Title extends Level {
    private FadeIO logo = new FadeIO(0, 1, 0, 0.01f, 40);
    private SmartRectangle start=new SmartRectangle(Render.unitsWide/2,30,20,7,true);
    private SmartRectangle quit=new SmartRectangle(Render.unitsWide/2,5,7,4,true);
    private boolean gameReady = false;

    public Title(ImageResource[] backgrounds, ImageResource[] foregrounds) {
        super(backgrounds);
        super.foregrounds = foregrounds;
        logo.setActive(true);
    }

    public void update(int subLevel) {
        if (subLevel == 0) {
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
        } else {
            if(World.getMaster().isActive())gameReady=true;
            if(World.getMaster().getDirection()&&World.getMaster().getCurrent()==1f&&!gameReady) {
                World.getMaster().setCurrent(0);
                World.getMaster().setActive(true);
            }
            quit.update();
            if(quit.isPressed()){
                Render.getGameLoop().setRunning(false);
            }
            start.update();
            if(start.isPressed()){
                World.setGame(true);
                World.getMaster().setActive(false);
                World.getMaster().setCurrent(1f);
                World.setLevel(1);
                World.setSubLevel(0);
            }
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
        Graphics.setColor(0,0.5f,0,1);
        Graphics.fillRectCentered(Render.unitsWide/2,30,20,7);
        Graphics.setColor(1,1,1,1);
        Graphics.setFont(Graphics.TITLE_FONT);
        Graphics.drawTextCentered("Start",Render.unitsWide/2,30);
        Graphics.setColor(0.5f,0,0,1);
        Graphics.fillRectCentered(Render.unitsWide/2,5,7,4);
        Graphics.setColor(1,1,1,1);
        Graphics.setFont(Graphics.REGULAR_FONT);
        Graphics.drawTextCentered("Quit",Render.unitsWide/2,5.3f);
        Graphics.setFont(Graphics.SMALL_FONT);
        Graphics.drawText("Casual Caving 0.0.0a",0.1f,0.7f);
    }
}
