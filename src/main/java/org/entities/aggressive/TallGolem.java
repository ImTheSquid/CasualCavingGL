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
import org.world.*;

public class TallGolem extends Autonomous {
    public static final int BLUE=0;
    private int golemType;
    private Animator golemAnimator;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    private ImageResource golem;
    public TallGolem(int golemType, int subLevel, float spawnX, float spawnY) {
        super(subLevel, spawnX, spawnY);
        this.golemType=golemType;
        switch(golemType){
            case BLUE:
                health=3;
                displayName="Tall Blue Golem";
                golemAnimator=new Animator(ResourceHandler.getGolemLoader().getTallBlueGolemWalk(direction),10);
                break;
        }
    }

    @Override
    public void update() {
        //Get HeightMap information
        HeightReturn h= HeightMap.onGround(hitbox);

        //Movement input
        if(damageTakenFrame==0) {
            if (direction) {
                vX = .3f;
            } else {
                vX = -.3f;
            }
        }else{
            if((direction&&!attackerBehind)||(!direction&&attackerBehind))vX=-.8f;
            else vX=.8f;
            damageTakenFrame--;
        }

        if(damageCooldown>0){
            damageCooldown--;
            state=2;
        }else if(state==2){
            state=0;
        }

        //Y-Velocity Calculations
        y+=vY;
        vY-= World.getGravity();

        //X-Velocity and Jumping Calculations
        boolean doXCalc=true;

        if (HeightMap.checkRightCollision(hitbox)) {
            HeightVal hv=HeightMap.findApplicable(hitbox,true);
            if (hv!=null&&x + width + 0.5>= hv.getStartX()) {
                if (vX < 0) x += vX;
                else vX=0;
                doXCalc=false;
                direction=!direction;
                x-=.25f;
            }
        }
        if(HeightMap.checkLeftCollision(hitbox)){
            HeightVal hv=HeightMap.findApplicable(hitbox,false);
            if(hv!=null&&x-0.5<=hv.getEndX()){
                if(vX>0)x+=vX;
                else vX=0;
                doXCalc=false;
                direction=!direction;
                x+=.25f;
            }
        }
        HeightVal wall=HeightMap.findNextWall(hitbox,direction);
        final int jumpDist=5;
        if(wall !=null&&y+32>=wall.getHeight()) {
            if (direction) {
                if (wall.getStartX() - x - width <= jumpDist) {
                    state = 3;
                }
            } else {
                if (x - wall.getEndX() <= jumpDist) {
                    state = 3;
                }
            }
        }
        if(state==3&&golemAnimator.getCurrentFrameNum()<golemAnimator.getFrames().length-1)doXCalc=false;
        if(doXCalc){
            x+=vX;
            doXCalc();
        }

        //Y-velocity and ground calc
        if(h.isOnGround()&&vY<0){
            y=h.getGroundLevel();
            vY=0;
            if(state==3&&golemAnimator.getCurrentFrameNum()==golemAnimator.getFrames().length-1)
                state=0;
        }

        doAttackCalc();

        //Update the frames
        if(vX==0){
            switch (golemType){
                case BLUE:
                    golemAnimator.setFrames(new ImageResource[]{ResourceHandler.getGolemLoader().getTallBlueGolem(direction)});
                    break;
            }
        }else {
            doSpriteCalc();
        }

        if (state != 3 || golemAnimator.getCurrentFrameNum() < golemAnimator.getFrames().length - 1) {
            golemAnimator.update();
        }

        if(state==1&&golemAnimator.getCurrentFrameNum()==golemAnimator.getFrames().length-1){
            state=0;
            Attack.attack(this,1,4);
        }
        if(state==3&&golemAnimator.getCurrentFrameNum()==golemAnimator.getFrames().length-1){
            if(vY==0){
                vY=3f;
            }
        }
    }

    private void doSpriteCalc(){
        golem = golemAnimator.getCurrentFrame();
        switch (state) {
            case 0:
                switch (golemType) {
                    case BLUE:
                        golemAnimator.setFrames(ResourceHandler.getGolemLoader().getTallBlueGolemWalk(direction));
                        break;
                }
                break;
            case 1:
                switch (golemType) {
                    case BLUE:
                        golemAnimator.setFrames(ResourceHandler.getGolemLoader().getTallBlueGolemAttack(direction));
                        break;
                }
                break;
            case 2:
                switch (golemType) {
                    case BLUE:
                        golemAnimator.setFrames(new ImageResource[]{ResourceHandler.getGolemLoader().getTallBlueGolemKnockback(direction)});
                        break;
                }
                break;
            case 3:
                switch (golemType) {
                    case BLUE:
                        golemAnimator.setFrames(ResourceHandler.getGolemLoader().getTallBlueGolemJump(direction));
                        break;
                }
                break;
        }
    }

    private void doXCalc(){
        Level l= LevelController.getCurrentLevel();
        if(x<l.getLeftLimit()||x+width>l.getRightLimit()){
            direction=!direction;
        }
    }

    private void doAttackCalc(){
        if(state!=0)return;
        if(attackCooldown>0){
            attackCooldown--;
            return;
        }
        if(Main.getHarold().getY()>y+height||Main.getHarold().getY()+Main.getHarold().getWidth()<y){
            state=0;
            return;
        }
        if(direction){
            if(Main.getHarold().getX()>=x&&Main.getHarold().getX()<=x+width+6){
                state=1;
                golemAnimator.setCurrentFrame(0);
                attackCooldown=100;
            }
        }else{
            if(Main.getHarold().getX()+Main.getHarold().getWidth()<=x+width&&Main.getHarold().getWidth()+Main.getHarold().getX()>=x-6){
                state=1;
                golemAnimator.setCurrentFrame(0);
                attackCooldown=100;
            }
        }
    }

    @Override
    public void render() {
        if(golem==null)return;
        //Update hitbox info
        width= Graphics.convertToWorldWidth(golem.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(golem.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);

        //Draw the golem
        if(damageTakenFrame>0){
            Graphics.setColor(1,0,0,1);//Set damage color if needed
        }
        else Graphics.setColor(1,1,1,1);
        Graphics.drawImage(golem,x,y);
        Graphics.setColor(1,1,1,1);
    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        switch(golemType) {
            case 0:
                return "Tall Blue Golem @ " + x + "," + y;
            default:
                return "Tall Golem @ "+x+","+y;
        }
    }
}
