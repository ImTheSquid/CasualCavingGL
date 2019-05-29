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

public class RedMajor extends Autonomous {
    private final int NORMAL=0,READYING=1,ATTACK=2, DAMAGE =3;
    private Animator redAnimator;
    private ImageResource redMajor;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    private boolean doStartReady=true;
    public RedMajor() {
        super(5, 75, 7);
        reset();
    }

    @Override
    public void update() {
        HeightReturn heightReturn= HeightMap.onGround(hitbox);

        //Determines movement and knockback
        if(damageTakenFrame==0) {
            if (direction) {
                vX = .15f;
            } else {
                vX = -.15f;
            }
        }else{
            if((direction&&!attackerBehind)||(!direction&&attackerBehind))vX=-.3f;
            else vX=.3f;
            damageTakenFrame--;
        }

        //Calculates the y-value and velocity
        y+=vY;
        vY-= World.getGravity();
        if(heightReturn.isOnGround()&&vY<0){
            y=heightReturn.getGroundLevel();
            vY=0;
        }

        //Calculates the x-value and velocity
        if(state!=READYING&&redAnimator.getDelay()==0) {
            x += vX;
            Level l = LevelController.getCurrentLevel();
            if (x < l.getLeftLimit() || x + width > l.getRightLimit()) direction = !direction;
        }

        //Current sprite calculations
        if(damageCooldown>0){
            damageCooldown--;
            state=DAMAGE;
        }else if(state==DAMAGE){
            state=0;
        }

        doAttackCalc();
        if(state==ATTACK&&redAnimator.getCurrentFrameNum()==redAnimator.getFrames().length-1){
            redAnimator.setDelay(7);
            state=NORMAL;
            Attack.attack(this,1,4);
        }

        if(vX==0)redAnimator.setFrames(new ImageResource[]{ResourceHandler.getBossLoader().getRedMajorStill(direction)});
        else {
            doSpriteCalc();
        }
        redAnimator.update();
    }

    private void doSpriteCalc(){
        if(redAnimator.getDelay()==0)
        switch (state) {
            case NORMAL:
                redAnimator.setFrames(ResourceHandler.getBossLoader().getRedMajorWalk(direction));
                break;
            case READYING:
                redAnimator.setFrames(ResourceHandler.getBossLoader().getRedMajorReady(direction));
                break;
            case ATTACK:
                redAnimator.setFrames(ResourceHandler.getBossLoader().getRedMajorAttack(direction));
                break;
            case DAMAGE:
                redAnimator.setFrames(new ImageResource[]{ResourceHandler.getBossLoader().getRedMajorDamage(direction)});
                break;
        }

        if(doStartReady&&redAnimator.getCurrentFrameNum()==redAnimator.getFrames().length-1){
            redMajor=redAnimator.getCurrentFrame();
            redAnimator.setDelay(60);
            redAnimator.setFps(7);
            doStartReady=false;
            state=NORMAL;
        }

        redMajor = redAnimator.getCurrentFrame();
    }

    private void doAttackCalc(){
        if(attackCooldown>0){
            attackCooldown--;
            return;
        }
        if(Main.getHarold().getY()>y+height||Main.getHarold().getY()+Main.getHarold().getWidth()<y){
            state=NORMAL;
            return;
        }
        if(direction){
            if(Main.getHarold().getX()>=x&&Main.getHarold().getX()<=x+width+5){
                state=ATTACK;
                redAnimator.setCurrentFrame(0);
                attackCooldown=100;
            }
        }else{
            if(Main.getHarold().getX()+Main.getHarold().getWidth()<=x+width&&Main.getHarold().getWidth()+Main.getHarold().getX()>=x-5){
                state=ATTACK;
                redAnimator.setCurrentFrame(0);
                attackCooldown=100;
            }
        }
    }

    @Override
    public void render() {
        width= Graphics.convertToWorldWidth(redMajor.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(redMajor.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        if(damageTakenFrame>0){
            Graphics.setColor(1,0,0,1);//Set damage color if needed
        }
        else Graphics.setColor(1,1,1,1);
        Graphics.drawImage(redMajor,x,y);
        Graphics.setColor(1,1,1,1);
    }

    @Override
    public void reset() {
        direction=false;
        redAnimator=new Animator(ResourceHandler.getBossLoader().getRedMajorReady(false),1);
        health=8;
        state=READYING;
        redAnimator.setDelay(60);
        x=75;
        y=7;
    }

    @Override
    public String toString() {
        return "Red Major @ "+x+","+y;
    }
}