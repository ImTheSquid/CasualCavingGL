package org.level.levels;

import org.engine.Main;
import org.entities.aggressive.Swolem;
import org.entities.passive.Boulder;
import org.entities.passive.Isolsi;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.level.Level;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.HeightMap;
import org.world.HeightVal;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_E;
import static org.world.World.CHECK_LARANO_FINISH;

public class Level6 extends Level {
    /* Create objects for special entities (ones that have different member functions) */
    private Boulder boulder=new Boulder();
    private Swolem swolem = new Swolem();
    /* Minigame variable */
    private boolean golemPassedLava=false;
    public Level6(ImageResource[] backgrounds) {
        super(backgrounds, backgrounds.length);
    }

    @Override
    public void init() {
        if(World.getLatestCheckpoint()< CHECK_LARANO_FINISH)World.newCheckpoint(CHECK_LARANO_FINISH);
        ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
    }

    @Override
    public ImageResource[] getAssets() {
        return ResourceHandler.create1DLoadable(new ImageResource[][]
                {ResourceHandler.getLevelLoader().getLevel6(),ResourceHandler.getLevelLoader().getLevel6Town()});
    }

    @Override
    public void update(int subLevel) {
        checkHealthVals();
        setBounds(subLevel);
        if(subLevel!=3) {
            HeightMap.setHeights(new HeightVal(0,7,100,true));
            Graphics.setScaleFactor(1);
            World.setGravity(.15f);
        }
        else{
            HeightMap.setHeights(new HeightVal(0,7,24,true),
                    new HeightVal(36,7,46,true),
                    new HeightVal(60,7,66,true),
                    new HeightVal(80,7,100,true));
            Graphics.setScaleFactor(.75f);
            if(ResourceHandler.getHaroldLoader().getState()==HaroldLoader.GOLEM){
                World.setGravity(.25f);
                if(Main.getHarold().getX()>80){
                    ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
                    golemPassedLava=true;
                }
            }else{
                World.setGravity(.2f);
                if(Main.getHarold().getX()<20&&
                        Keyboard.keys.contains(VK_E))ResourceHandler.getHaroldLoader().setState(HaroldLoader.GOLEM);
            }

        }
    }

    private void setBounds(int subLevel){
        rightLimit=100;
        if(subLevel==4)leftLimit=boulder.isDone()?40:3;
        else leftLimit=0;
        switch(subLevel){
            case 0:
                rightBound=100;
                break;
            case 1:
                leftBound=0;
                rightBound=90;
                break;
            case 2:
                leftBound=5;
                rightBound=95;
                break;
            case 3:
                leftBound=3;
                rightBound=96;
                break;
            case 4:
                leftBound=boulder.isDone()?0:-1;
                rightBound=101;
                //Skip sun stone if player pushed boulder
                if(boulder.isDone()&&Main.getHarold().getX()+Main.getHarold().getWidth()>=99){
                    if(boulder.isTownOK())update4();
                    else {
                        World.setSubLevel(6);
                        Main.getHarold().setX(5);
                    }
                }
                break;
            case 5:
                leftBound=-1;
                rightBound=101;
            case 6:
                leftBound=-1;
                rightBound=101;
            case 7:
                leftBound=0;
                rightBound=101;
        }
    }

    //Handles transition to next sublevel (5)
    private void update4(){
        World.getMaster().setActive(true);
        World.setMasterColor(1,1,1);
        World.getMaster().setDirection(false);
        if(World.getMaster().getCurrent()==0) {
            World.getMaster().setSecondDelay((long) .5);
            World.incrementSubLevel();
            if(!boulder.isTownOK())World.incrementSubLevel();
            World.getMaster().setDirection(true);
        }
    }

    @Override
    public void render(int subLevel) {
        Graphics.setIgnoreScale(true);
        Graphics.setFollowCamera(true);
        Graphics.setDrawColor(.22f,.22f,.22f,1);
        Graphics.fillRect(0,0,100,60);
        Graphics.setDrawColor(1,1,1,1);
        Graphics.setFollowCamera(false);
        switch(subLevel){
            case 1:Graphics.drawImage(ResourceHandler.getBossLoader().getEmerieForward(),41,25);
            break;
            case 2:Graphics.drawImage(ResourceHandler.getBossLoader().getEmerieForward(),50,25);
            break;
            case 3:Graphics.drawImage(ResourceHandler.getBossLoader().getEmerieForward(),61,25);
            break;
            case 4:Graphics.drawImage(ResourceHandler.getBossLoader().getEmerieForward(),30,25);
                Graphics.drawImage(ResourceHandler.getBossLoader().getEmerieForward(),65,20);
                Graphics.drawImage(ResourceHandler.getBossLoader().getEmerieForward(),165,-34.75f);
                break;
            case 6:Graphics.drawImage(ResourceHandler.getBossLoader().getEmerieForward(),47,17);
            break;
        }
        /* Special code for boulder minigame */
        if(subLevel!=4)Graphics.drawImage(backgrounds[subLevel],0,0);
        else {
            if(!boulder.isDone()){
                Graphics.drawImage(backgrounds[subLevel],0,-Graphics.convertToWorldHeight(700));
                Graphics.drawImage(ResourceHandler.getLevelLoader().getLevel6Town()[boulder.isTownOK()?1:0],
                        10,-Graphics.convertToWorldHeight(700));
            }else{
                Graphics.drawImage(backgrounds[subLevel], -Graphics.toWorldWidth(1280), 0);
                Graphics.drawImage(ResourceHandler.getLevelLoader().getLevel6Town()[boulder.isTownOK() ? 1 : 0],
                        -Graphics.toWorldWidth(1280) + 10, 0);
            }
        }
        /* Special code for golem parkour */
        if(subLevel==3&&ResourceHandler.getHaroldLoader().getState()!=HaroldLoader.GOLEM){
            if(!golemPassedLava){
                Graphics.setFont(Graphics.SMALL);
                Graphics.drawImage(ResourceHandler.getGolemLoader().getOldGolem()[0],16,19);
                if(Main.getHarold().getX()<20)Graphics.drawTextWithBox("E to carry",16,25);
            }else Graphics.drawImage(ResourceHandler.getGolemLoader().getOldGolem()[0],77,19);
        }
        /* Render Swolem boss bar */
        if(subLevel == 6)swolem.getBossBar().render();

        Graphics.setIgnoreScale(false);
    }

    @Override
    public void renderForeground(int subLevel) {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public void reset() {
        boulder.reset();
        swolem.reset();
        clearEntityRegister();
        entityRegister.add(boulder);
        entityRegister.add(new Isolsi(false));
        entityRegister.add(swolem);
        golemPassedLava=false;
    }
}
