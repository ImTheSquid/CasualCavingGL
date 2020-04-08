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
import org.world.*;

import static com.jogamp.newt.event.KeyEvent.VK_W;

public class Harold extends Entity {
    private Animator haroldAnimator = new Animator(ResourceHandler.getHaroldLoader().getHaroldWalk(), 12);
    private ImageResource harold;
    private boolean jump = false, lockControls = false, followCamera = false, isBlocking = false;
    private SmartRectangle hitbox = new SmartRectangle(x, y, width, height);

    public Harold() {
        maxHealth = 3;
        displayName = "Harold";
        reset();
    }

    public void update() {
        if (!movement) {
            return;
        }
        if(health<=0||(y+height<-10&&(World.getSubLevel()!=4&&World.getLevel()==6))){//Am I dead?
            if(y+height<-10)health=0;
            World.clearEntites();
            World.setLevel(-1);
        }
        //Get HeightMap info package
        HeightReturn h=HeightMap.onGround(hitbox);
        //Movement keys
        if(damageTakenFrame==0) {
            if (Keyboard.keys.contains(KeyEvent.VK_A) && !lockControls) {
                vX = -0.5f * Graphics.getScaleFactor();
            }
            if (Keyboard.keys.contains(KeyEvent.VK_D) && !lockControls) {
                vX = 0.5f * Graphics.getScaleFactor();
            }
            if (vX == 0 && ResourceHandler.getHaroldLoader().getState() == HaroldLoader.LANTERN) {
                isBlocking = Keyboard.keys.contains(KeyEvent.VK_C);
            } else isBlocking = false;
        }else{
            if((direction&&!attackerBehind)||(!direction&&attackerBehind))vX=-1f*Graphics.getScaleFactor();
            else vX=1f*Graphics.getScaleFactor();
            damageTakenFrame--;
        }
        if(Keyboard.keys.contains(KeyEvent.VK_SPACE)&&!jump&&!lockControls) {
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
        if(!lockControls&&Keyboard.keys.contains(VK_W)&&
                ResourceHandler.getHaroldLoader().getState()==HaroldLoader.LANTERN&&attackCooldown<=0){
            haroldAnimator.setCurrentFrame(0);
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.ATTACK);
            attackCooldown=45;
        }
        Keyboard.keys.remove(VK_W);//Fallback if key gets stuck

        y+=vY;
        vY-=World.getGravity();

        //X-velocity stuff
        boolean doXCalc=true;

        //Deals with colliding with objects above ground level
        if (HeightMap.checkRightCollision(hitbox)) {
            HeightVal hv=HeightMap.findApplicable(hitbox,true);
            if (hv!=null&&x + width + 0.5>= hv.getStartX()) {
                if (vX < 0) x += vX;
                else vX=0;
                doXCalc=false;
            }
        }
        if(HeightMap.checkLeftCollision(hitbox)){
            HeightVal hv=HeightMap.findApplicable(hitbox,false);
            if(hv!=null&&x-0.5<=hv.getEndX()){
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
            y = h.getGroundLevel();
            vY = 0;
            jump = false;
        }

        if (attackCooldown > 0)
            attackCooldown--;

        if (damageTakenFrame == 0) ResourceHandler.getHaroldLoader().setDirection(direction);

        if (isBlocking) ResourceHandler.getHaroldLoader().setState(HaroldLoader.BLOCK);

        if ((vX == 0 || damageTakenFrame > 0) && ResourceHandler.getHaroldLoader().getState() != HaroldLoader.ATTACK) {
            harold = ResourceHandler.getHaroldLoader().getHarold();
        } else {
            harold = haroldAnimator.getCurrentFrame();
        }

        //If on final frame of attack animation, send attack signal to Attack class
        if (ResourceHandler.getHaroldLoader().getState() == HaroldLoader.ATTACK && haroldAnimator.getCurrentFrameNum() == 3) {
            Attack.melee(this, 1, 5);
            ResourceHandler.getHaroldLoader().disableAttackPause();
            ResourceHandler.getHaroldLoader().setState(HaroldLoader.LANTERN);
        }

        Level currentLevel=LevelController.getCurrentLevel();
        //Deals with going between sublevels
        //Limit controls
        if (x < currentLevel.getLeftLimit()) x = currentLevel.getLeftLimit();
        if (x + width > currentLevel.getRightLimit()) x = currentLevel.getRightLimit() - width;
        //Transferring between sublevels
        if(x<currentLevel.getLeftBound()){
            if(World.getSubLevel()>0){
                World.setSubLevel(World.getSubLevel()-1);
                x=currentLevel.getRightBound()-5-width;
            }
            else x=currentLevel.getLeftBound();
        }
        if(x+width> currentLevel.getRightBound()){
            if(World.getSubLevel()< currentLevel.getNumSublevels()-1){
                World.incrementSubLevel();
                x=5;
            }else{
                x=currentLevel.getRightBound()-width;
            }
        }

        //If doing the special turn animation, set it up properly
        if(ResourceHandler.getHaroldLoader().getState()!=HaroldLoader.TURN) {
            haroldAnimator.setFps(12);
            haroldAnimator.setFrames(ResourceHandler.getHaroldLoader().getHaroldWalk());
            haroldAnimator.update();
        }
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
        Graphics.setFollowCamera(followCamera);
        width = Graphics.toWorldWidth(harold.getTexture().getWidth()) * Graphics.getScaleFactor();
        height = Graphics.toWorldHeight(harold.getTexture().getHeight()) * Graphics.getScaleFactor();
        hitbox.updateBounds(x, y, width, height);
        if (!visible) return;
        if (ResourceHandler.getHaroldLoader().getState() == HaroldLoader.TURN) {
            haroldAnimator.setFps(3);
            if (haroldAnimator.getCurrentFrameNum() > 0 && haroldAnimator.getFrames() !=
                    ResourceHandler.getHaroldLoader().getTurn()) haroldAnimator.setCurrentFrame(0);
            haroldAnimator.setFrames(ResourceHandler.getHaroldLoader().getTurn());
            harold = haroldAnimator.getCurrentFrame();
            if (haroldAnimator.getCurrentFrameNum() != 1) haroldAnimator.update();
        }
        if(damageTakenFrame >0){
            Graphics.setDrawColor(1f,.0f,.0f,1);
        }
        else Graphics.setDrawColor(1,1,1,1);
        Graphics.drawImage(harold,x,y);
        Graphics.setDrawColor(1,1,1,1);
        Graphics.setFollowCamera(false);
    }

    public void renderHealth(){
        if(!visible||World.getLevel()<1)return;
        Graphics.setIgnoreScale(true);
        Graphics.setFollowCamera(true);
        Graphics.setDrawColor(1,1,1,1);
        if(!invincible)
        for(int i=0;i<health;i++){
            float x=0.5f+(5.5f)*i;
            Graphics.drawImage(ResourceHandler.getHaroldLoader().getHealth(),x,0.5f,5,5);
        }
        else Graphics.drawImage(ResourceHandler.getHaroldLoader().getInfiniteHealth(),0.5f,0.5f,5,5);
        Graphics.setIgnoreScale(false);
        Graphics.setFollowCamera(false);
    }

    @Override
    public void reset() {
        ResourceHandler.getHaroldLoader().disableAttackPause();
        ResourceHandler.getHaroldLoader().setState(HaroldLoader.NORMAL);
        movement=true;
        x=World.getLatestCheckpoint()>World.CHECK_START?5:65;
        y=7;
        health=3;
        vX=0;
        vY=0;
        damageTakenFrame=0;
        damageCooldown=0;
        lockControls=false;
        followCamera=false;
    }

    public SmartRectangle getHitbox() {
        return hitbox;
    }

    public void setHarold(ImageResource harold) {
        this.harold = harold;
    }

    public void setLockControls(boolean lockControls) {
        this.lockControls = lockControls;
    }

    public boolean areControlsLocked() {
        return lockControls;
    }

    public void setFollowCamera(boolean followCamera) {
        this.followCamera = followCamera;
    }

    public boolean isBlocking() {
        return isBlocking;
    }
}
