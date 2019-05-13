package org.entities;

import com.jogamp.newt.event.KeyEvent;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.loader.harold.HaroldLoader;
import org.world.Attack;
import org.world.HeightMap;
import org.world.HeightReturn;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_W;

public class Harold extends Entity{
    private Animator haroldAnimator=new Animator(ResourceHandler.getHaroldLoader().getHaroldWalk(),12);
    private ImageResource harold;
    private boolean jump=false;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);

    public Harold(){
        reset();
    }
    public void update() {
        if(!movement)return;
        if(health<=0||y+height<-10){//Am I dead?
            World.clearEntites();
            World.setLevel(-1);
        }
        HeightReturn h=HeightMap.onGround(hitbox);
        //Movement keys
        if(damageTakenFrame==0) {
            if (Keyboard.keys.contains(KeyEvent.VK_A)) {
                vX = -0.5f;
            }
            if (Keyboard.keys.contains(KeyEvent.VK_D)) {
                vX = 0.5f;
            }
        }else{
            if((direction&&!attackerBehind)||(!direction&&attackerBehind))vX=-1f;
            else vX=1f;
            damageTakenFrame--;
        }
        if(Keyboard.keys.contains(KeyEvent.VK_SPACE)&&!jump) {
            if(h.isOnGround()) {
                vY = 2.5f;
                jump=true;
            }
        }else if(!Keyboard.keys.contains(KeyEvent.VK_SPACE)&&jump){
            jump=false;
            if(vY<-.5f){
                vY=-.5f;
            }
        }
        //Attack key
        if(Keyboard.keys.contains(VK_W)&&ResourceHandler.getHaroldLoader().getState()==HaroldLoader.LANTERN&&attackCooldown<=0){
            haroldAnimator.setCurrentFrame(0);
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.ATTACK);
            attackCooldown=20;
        }
        Keyboard.keys.remove(VK_W);//Fallback if key gets stuck

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

        if(h.isOnGround()&&vY<0){
            y=h.getGroundLevel();
            vY=0;
            jump=false;
        }

        if(attackCooldown>0)
            attackCooldown--;

        if(damageTakenFrame==0)ResourceHandler.getHaroldLoader().setDirection(direction);

        if((vX==0||damageTakenFrame>0)&&ResourceHandler.getHaroldLoader().getState()!=HaroldLoader.ATTACK){
            harold= ResourceHandler.getHaroldLoader().getHarold();
        }else{
            harold=haroldAnimator.getCurrentFrame();
        }

        if(ResourceHandler.getHaroldLoader().getState()==HaroldLoader.ATTACK&&haroldAnimator.getCurrentFrameNum()==3) {
            Attack.attack(this, 1, 5);
            ResourceHandler.getHaroldLoader().disableAttackPause();
        }

        Level currentLevel=LevelController.getCurrentLevel();
        //TODO cleanup usages
        if(x<currentLevel.getLeftLimit())x=currentLevel.getLeftLimit();
        if(x+width>currentLevel.getRightLimit())x=currentLevel.getRightLimit()-width;
        if(x<currentLevel.getLeftBound()){
            if(!currentLevel.isDecreaseAllowed())x=currentLevel.getLeftBound();
            else if(World.getSubLevel()>0){
                World.setSubLevel(World.getSubLevel()-1);
                x=currentLevel.getRightBound()-15;
            }
            else x=currentLevel.getLeftBound();
        }
        if(x+width> currentLevel.getRightBound()){
            if(!currentLevel.isIncreaseAllowed()) x=currentLevel.getRightBound()-width;
            else if(World.getSubLevel()< currentLevel.getNumSublevels()-1){
                World.setSubLevel(World.getSubLevel()+1);
                x=5;
            }else{
                x=currentLevel.getRightBound()-width;
            }
        }
        haroldAnimator.setFrames(ResourceHandler.getHaroldLoader().getHaroldWalk());
        haroldAnimator.update();
    }

    private void doXCalc(){
        if(damageTakenFrame==0) {
            if (vX > 0) {
                direction = true;
                if (vX - World.getGravity() < 0) vX = 0;
                else vX -= World.getGravity();
            } else if (vX < 0) {
                direction = false;
                if (vX + World.getGravity() > 0) vX = 0;
                else vX += World.getGravity();
            }
        }else{
            if((direction&&!attackerBehind)||(!direction&&attackerBehind))vX+=World.getGravity();
            else vX-=World.getGravity();
        }
    }

    public void render() {
        width=Graphics.convertToWorldWidth(harold.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(harold.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        if(!visible)return;
        if(ResourceHandler.getHaroldLoader().getState()==HaroldLoader.TURN){
            if(haroldAnimator.getCurrentFrameNum()>0&&haroldAnimator.getFrames()!=ResourceHandler.getHaroldLoader().getTurn())haroldAnimator.setCurrentFrame(0);
            haroldAnimator.setFrames(ResourceHandler.getHaroldLoader().getTurn());
            harold=haroldAnimator.getCurrentFrame();
            if(haroldAnimator.getCurrentFrameNum()!=1)haroldAnimator.update();
        }
        if(damageTakenFrame >0){
            Graphics.setColor(1f,.0f,.0f,1);
        }
        else Graphics.setColor(1,1,1,1);
        Graphics.drawImage(harold,x,y);
    }

    public void renderHealth(){
        if(!visible||World.getLevel()<1)return;
        Graphics.setColor(1,1,1,1);
        if(!invincible)
        for(int i=0;i<health;i++){
            float x=0.5f+(5.5f)*i;
            Graphics.drawImage(ResourceHandler.getHaroldLoader().getHealth(),x,0.5f,5,5);
        }
        else Graphics.drawImage(ResourceHandler.getHaroldLoader().getInfiniteHealth(),0.5f,0.5f,5,5);
    }

    @Override
    public void reset() {
        ResourceHandler.getHaroldLoader().setState(HaroldLoader.NORMAL);
        movement=true;
        x=65;
        y=7;
        health=3;
        vX=0;
        vY=0;
        damageTakenFrame=0;
        damageCooldown=0;
    }

    @Override
    public String toString() {
        return "Harold @ "+x+","+y;
    }

    public SmartRectangle getHitbox() {
        return hitbox;
    }
}
