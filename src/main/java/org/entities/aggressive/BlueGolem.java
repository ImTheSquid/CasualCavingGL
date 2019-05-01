package org.entities.aggressive;

import org.entities.Autonomous;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.HeightMap;
import org.world.HeightReturn;
import org.world.World;

public class BlueGolem extends Autonomous {
    private Animator golemAnimator=new Animator(ResourceHandler.getGolemLoader().getBlueGolemWalk(direction),10);
    private ImageResource blueGolem;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    public BlueGolem(boolean lockHeightVal,int subLevel,float spawnX,float spawnY){
        super(lockHeightVal);
        super.subLevel=subLevel;
        x=spawnX;
        y=spawnY;
    }
    @Override
    public void update() {
        HeightReturn h= HeightMap.onGround(hitbox);

        //Action input
        if(direction){
            vX=.25f;
        }else{
            vX=-.25f;
        }

        //Calculations
        y+=vY;
        vY-=World.getGravity();

        //X-velocity stuff
        boolean doXCalc=true;

        if (HeightMap.checkRightCollision(hitbox)) {
            if (x + width + 0.5>= HeightMap.findApplicable(hitbox,true).getStartX()) {
                if (vX < 0) x += vX;
                else vX=0;
                doXCalc=false;
            }
        }
        if(HeightMap.checkLeftCollision(hitbox)){
            if(x-0.5<=HeightMap.findApplicable(hitbox,false).getEndX()){
                if(vX>0)x+=vX;
                else vX=0;
                doXCalc=false;
            }
        }

        if(doXCalc){
            x+=vX;
            doXCalc();
        }

        //Y-velocity and ground calc
        if(h.isOnGround()&&vY<0){
            y=h.getGroundLevel();
            vY=0;
        }

        if(vX==0) {
            blueGolem=ResourceHandler.getGolemLoader().getBlueGolem(direction);
        }else{
            blueGolem=golemAnimator.getCurrentFrame();
        }
        golemAnimator.setFrames(ResourceHandler.getGolemLoader().getBlueGolemWalk(direction));
        golemAnimator.update();
    }

    private void doXCalc(){
        Level l= LevelController.getLevels()[World.getLevel()];
        if(x<l.getLeftLimit()||x+width>l.getRightLimit())direction=!direction;
    }

    @Override
    public void render() {
        width=Graphics.convertToWorldWidth(blueGolem.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(blueGolem.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        Graphics.setColor(1,1,1,1);
        Graphics.drawImage(blueGolem,x,y);
    }

    @Override
    public void reset() {

    }
}
