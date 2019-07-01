package org.level.levels;

import org.engine.Main;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_SPACE;

public class Death extends Level {
    private FadeIO textHandler=new FadeIO(0,1,0,0.02f,35);
    private boolean fadeDir=true;
    public Death() {
        super(new ImageResource[]{null}, 1);
    }

    @Override
    public void init() {
        if(World.getMaster().isActive()){
            World.getMaster().setActive(false);
            World.getMaster().setCurrent(1);
        }
        Main.getHarold().setVisible(false);
        Main.getHarold().setMovement(false);
        Main.getHarold().reset();
        textHandler.setCurrent(0);
        textHandler.setDirection(true);
        textHandler.setActive(true);
        fadeDir=true;
    }

    @Override
    public void loadAssets() {

    }

    @Override
    public void update(int subLevel) {
        if(fadeDir){
            if (textHandler.getCurrent() == 1) {
                fadeDir = false;
                textHandler.setSecondDelay(2);
                textHandler.setDirection(false);
            } else {
                textHandler.setDirection(true);
                textHandler.setActive(true);
            }
        }else if(textHandler.getCurrent()==0){
            finish();
        }
        if(Keyboard.keys.contains(VK_SPACE))finish();
        textHandler.update();
    }

    private void finish(){
        fadeDir=true;
        cleanup();
        World.clearEntites();
        LevelController.resetAll();
        World.setLevel(World.getLevel()+1);
        World.setSubLevel(1);
    }

    @Override
    public void render(int subLevel) {
        Graphics.setFont(Graphics.TITLE_FONT);
        Graphics.setColor(1,1,1,textHandler.getCurrent());
        Graphics.drawTextCentered("Game Over", Render.unitsWide/2,35);
        Graphics.setColor(1,1,1,1);
    }

    @Override
    public void renderForeground(int subLevel) {

    }

    @Override
    public void cleanup() {
        Main.getHarold().setVisible(true);
        Main.getHarold().setMovement(true);
        Main.getHarold().reset();
    }

    @Override
    public void reset() {
        cleanup();
    }
}
