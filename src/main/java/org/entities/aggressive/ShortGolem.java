package org.entities.aggressive;

import org.engine.Main;
import org.entities.Autonomous;
import org.entities.Entity;
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

//This class encompasses the blue, green, and red golems, as their classes are very similar

public class ShortGolem extends Autonomous {
    public static final int BLUE=0,GREEN=1,RED=2,PURPLE=3;
    private int golemType;
    private Animator golemAnimator;
    private ImageResource golem;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    public ShortGolem(int golemType, int subLevel, float spawnX, float spawnY) {
        super(subLevel, spawnX, spawnY);
        this.golemType=golemType;
        switch(golemType){
            case BLUE:
                health=2;
                golemAnimator=new Animator(ResourceHandler.getGolemLoader().getBlueGolemWalk(direction),10);
                displayName="Blue Golem";
                break;
            case GREEN:
                health=2;
                golemAnimator=new Animator(ResourceHandler.getGolemLoader().getGreenGolemWalk(direction),10);
                displayName="Green Golem";
                break;
            case RED:
                health=3;
                golemAnimator=new Animator(ResourceHandler.getGolemLoader().getRedGolemWalk(direction),10);
                displayName="Red Golem";
                break;
            case PURPLE:
                health=2;
                golemAnimator=new Animator(ResourceHandler.getGolemLoader().getPurpleGolemWalk(direction),10);
                displayName="Purple Golem";
                break;
        }
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
            if((direction&&!attackerBehind)||(!direction&&attackerBehind))vX=-.7f;
            else vX=.7f;
            damageTakenFrame--;
        }

        //Calculations
        y+=vY;
        vY-= World.getGravity();

        //X-velocity stuff
        boolean doXCalc=true;

        if (HeightMap.checkRightCollision(hitbox)) {
            if (x + width + 0.5>= HeightMap.findApplicable(hitbox,true).getStartX()) {
                if (vX < 0) x += vX;
                else vX=0;
                doXCalc=false;
                direction=!direction;
                x-=.25f;
            }
        }
        if(HeightMap.checkLeftCollision(hitbox)){
            if(x-0.5<=HeightMap.findApplicable(hitbox,false).getEndX()){
                if(vX>0)x+=vX;
                else vX=0;
                doXCalc=false;
                direction=!direction;
                x+=.25f;
            }
        }

        if(doXCalc){
            x+=vX;
            doXCalc();
        }

        if(damageCooldown>0){
            damageCooldown--;
            state=2;
        }else if(state==2){
            state=0;
        }

        //Y-velocity and ground calc
        if(h.isOnGround()&&vY<0){
            y=h.getGroundLevel();
            vY=0;
        }
        doAttackCalc();

        if(vX==0) {
            switch(golemType) {
                case BLUE:
                    golem = ResourceHandler.getGolemLoader().getBlueGolem(direction);
                    break;
                case GREEN:
                    golem = ResourceHandler.getGolemLoader().getGreenGolem(direction);
                    break;
                case RED:
                    golem = ResourceHandler.getGolemLoader().getRedGolem(direction);
                    break;
                case PURPLE:
                    golem=ResourceHandler.getGolemLoader().getPurpleGolem(direction);
                    break;
            }
        }else{
            golem=golemAnimator.getCurrentFrame();
        }
        if(state==0){
            switch(golemType) {
                case BLUE:
                    golemAnimator.setFrames(ResourceHandler.getGolemLoader().getBlueGolemWalk(direction));
                    break;
                case GREEN:
                    golemAnimator.setFrames(ResourceHandler.getGolemLoader().getGreenGolemWalk(direction));
                    break;
                case RED:
                    golemAnimator.setFrames(ResourceHandler.getGolemLoader().getRedGolemWalk(direction));
                    break;
                case PURPLE:
                    golemAnimator.setFrames(ResourceHandler.getGolemLoader().getPurpleGolemWalk(direction));
                    break;
            }
        }else if(state==1){
            switch(golemType) {
                case BLUE:
                    golemAnimator.setFrames(ResourceHandler.getGolemLoader().getBlueGolemAttack(direction));
                    break;
                case GREEN:
                    golemAnimator.setFrames(ResourceHandler.getGolemLoader().getGreenGolemAttack(direction));
                    break;
                case RED:
                    golemAnimator.setFrames(ResourceHandler.getGolemLoader().getRedGolemAttack(direction));
                    break;
                case PURPLE:
                    golemAnimator.setFrames(ResourceHandler.getGolemLoader().getPurpleGolemAttack(direction));
                    break;
            }
        }else if(state==2){
            switch (golemType) {
                case BLUE:
                    golemAnimator.setFrames(new ImageResource[]{ResourceHandler.getGolemLoader().getBlueGolemKnockback(direction)});
                    break;
                case GREEN:
                    golemAnimator.setFrames(new ImageResource[]{ResourceHandler.getGolemLoader().getGreenGolemKnockback(direction)});
                    break;
                case RED:
                    golemAnimator.setFrames(new ImageResource[]{ResourceHandler.getGolemLoader().getRedGolemKnockback(direction)});
                    break;
                case PURPLE:
                    golemAnimator.setFrames(new ImageResource[]{ResourceHandler.getGolemLoader().getPurpleGolemKnockback(direction)});
                    break;
            }
        }
        golemAnimator.update();
        if(state==1&&golemAnimator.getCurrentFrameNum()==golemAnimator.getFrames().length-1){
            state=0;
            Attack.attack(this,1,4);
        }
    }

    private void doXCalc(){
        Level l= LevelController.getCurrentLevel();
        if(x<l.getLeftLimit()||x+width>l.getRightLimit())direction=!direction;
        if(golemType==GREEN&&HeightMap.onEdge(hitbox,direction))direction=!direction;
    }

    private void doAttackCalc(){
        if(attackCooldown>0){
            attackCooldown--;
            return;
        }
        if(Main.getHarold().getY()>y+height||Main.getHarold().getY()+Main.getHarold().getWidth()<y){
            state=0;
            return;
        }
        if(direction){
            if(Main.getHarold().getX()>=x&&Main.getHarold().getX()<=x+width+4){
                state=1;
                golemAnimator.setCurrentFrame(0);
                attackCooldown=100;
            }
        }else{
            if(Main.getHarold().getX()+Main.getHarold().getWidth()<=x+width&&Main.getHarold().getWidth()+Main.getHarold().getX()>=x-4){
                state=1;
                golemAnimator.setCurrentFrame(0);
                attackCooldown=100;
            }
        }
    }

    @Override
    public void render() {
        width= Graphics.convertToWorldWidth(golem.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(golem.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        if(damageTakenFrame>0){
            Graphics.setColor(1,0,0,1);//Set damage color if needed
        }
        else Graphics.setColor(1,1,1,1);
        Graphics.drawImage(golem,x,y);
    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        switch(golemType) {
            case BLUE:
                return "Blue Golem @ " + x + "," + y;
            case GREEN:
                return "Green Golem @ " + x + "," + y;
            case RED:
                return "Red Golem @ " + x + "," + y;
            case PURPLE:
                return "Purple Golem @ "+x+","+y;
            default:
                return "Golem @ " + x + "," + y;
        }
    }

    @Override
    public void doDamage(Entity attacker, int damage) {
        if(golemType!=PURPLE)super.doDamage(attacker, damage);
        else{
            if(direction)attackerBehind=attacker.getX()<x;
            else attackerBehind=attacker.getX()>x;
            if(attackerBehind)direction=!direction;
            else if(!invincible)super.doDamage(attacker,damage);
        }
    }
}
