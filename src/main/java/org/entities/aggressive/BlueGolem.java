package org.entities.aggressive;

import org.engine.Main;
import org.entities.Autonomous;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.Attack;
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
        health=2;
    }
    @Override
    public void update() {
        HeightReturn h= HeightMap.onGround(hitbox);

        //Action input
        if(damageTakenFrame==0) {
            if (direction) {
                vX = .25f;
            } else {
                vX = -.25f;
            }
        }else{
            //TODO Fix knockback
            if(direction)vX=-.1f;
            else vX=.1f;
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

        if(damageCooldown>0)damageCooldown--;

        //Y-velocity and ground calc
        if(h.isOnGround()&&vY<0){
            y=h.getGroundLevel();
            vY=0;
        }
        doAttackCalc();

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

    private void doAttackCalc(){
        if(attackCooldown>0){
            attackCooldown--;
            return;
        }
        if(direction){
            if(Main.getHarold().getX()>=x&&Main.getHarold().getX()<=x+width+3){
                Attack.attack(this,1,3);
                attackCooldown=100;
            }
        }else{
            if(Main.getHarold().getX()+Main.getHarold().getWidth()<=x&&Main.getHarold().getWidth()+Main.getHarold().getX()>=x-3){
                Attack.attack(this,1,3);
                attackCooldown=100;
            }
        }
    }

    @Override
    public void render() {
        width=Graphics.convertToWorldWidth(blueGolem.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(blueGolem.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        if(damageTakenFrame>0){
            Graphics.setColor(1,0,0,1);//Set damage color if needed
            damageTakenFrame--;
        }
        else Graphics.setColor(1,1,1,1);
        Graphics.drawImage(blueGolem,x,y);
    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return "Blue Golem @ "+x+","+y;
    }
}
