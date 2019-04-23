package org.level.levels;

import org.engine.Main;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;

public class Level3 extends Level {
    private FadeIO isolsi=new FadeIO(0,1,0,0.02f,35);
    private FadeIO hematus=new FadeIO(0,1,0,0.02f,35);
    private FadeIO igneox=new FadeIO(0,1,0,0.02f,35);
    private ImageResource[] sprites= ResourceHandler.getLevelLoader().getLevel3Sprites();
    private boolean fadeActive=false;
    private int switchFade=0;
    public Level3(ImageResource[] backgrounds) {
        super(backgrounds,4);
    }

    @Override
    public void update(int subLevel) {
        if(subLevel!=1)ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
        switch (subLevel){
            case 1:
                update1();
                break;
        }
    }

    private void update1(){
        if(Main.getHarold().getX()>50)fadeActive=true;
        if(fadeActive) {
            switch(switchFade){
                case 0:
                    if(isolsi.getCurrent()<1){
                        isolsi.setActive(true);
                        isolsi.update();
                    }else{
                        isolsi.setActive(false);
                        switchFade++;
                    }
                    break;
                case 1:
                    if(hematus.getCurrent()<1){
                        hematus.setActive(true);
                        hematus.update();
                    }else{
                        hematus.setActive(false);
                        switchFade++;
                    }
                    break;
                case 2:
                    if(igneox.getCurrent()<1){
                        igneox.setActive(true);
                        igneox.update();
                    }else{
                        igneox.setActive(false);
                        fadeActive=false;
                    }
            }
        }
    }

    @Override
    public void render(int subLevel) {
        Graphics.drawImage(backgrounds[subLevel],0,0);
        switch(subLevel){
            case 1:
                render1();
                break;
        }
    }

    private void render1(){
        if(isolsi.getCurrent()>0) {
            Graphics.setColor(1, 1, 1, isolsi.getCurrent());
            Graphics.drawImage(sprites[0], 0, 0);
        }
        if(hematus.getCurrent()>0){
            Graphics.setColor(1, 1, 1, hematus.getCurrent());
            Graphics.drawImage(sprites[1], 0, 0);
        }
        if(igneox.getCurrent()>0){
            Graphics.setColor(1, 1, 1, igneox.getCurrent());
            Graphics.drawImage(sprites[igneoxCalc()+1], 0, 0);
        }
    }

    private int igneoxCalc(){
        if(igneox.getCurrent()==1){
            return 4;
        }else if(igneox.getCurrent()>.75f){
            return 3;
        }else if(igneox.getCurrent()>.5f){
            return 2;
        }else{
            return 1;
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

    }
}
