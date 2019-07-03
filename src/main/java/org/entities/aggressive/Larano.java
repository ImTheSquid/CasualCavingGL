package org.entities.aggressive;

import org.engine.Main;
import org.entities.Autonomous;
import org.entities.Entity;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.BossBar;
import org.graphics.Graphics;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.*;

public class Larano extends Autonomous {
    private final int NORMAL=0,READY=1,ATTACK=2,CHARGE=3, DIZZY =4,CHARGERDY=5,DAMAGE=6,JUMP=7;
    private Animator larano=new Animator(ResourceHandler.getBossLoader().getLaranoReadying(),30);
    private ImageResource sprite=null;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    private BossBar bossBar=new BossBar(this);
    private boolean altAttack=false;
    private int dizzyCount=0,chargeAttemptCooldown=0;
    public Larano() {
        super(2, 0, 0);
        displayName="Larano";
        reset();
    }

    @Override
    public void update() {
        HeightReturn h= HeightMap.onGround(hitbox);
        bossBar.update();
        if(Main.getHarold().getX()>10&&state==-1)state=READY;
        if(state==-1)return;
        if(state==NORMAL){
            if(direction)vX=.2f;
            else vX=-.2f;
        }else if(state==CHARGE){
            if(direction)vX=2;
            else vX=-2;
            if(attackCooldown==0&&Attack.melee(this,1,0.5f)){
                attackCooldown=20;
            }
        }else{
            vX=0;
        }
        x+=vX;
        Level l= LevelController.getCurrentLevel();
        if(x<l.getLeftLimit()||x+width>l.getRightLimit()){
            if(state!=CHARGE&&state!=DIZZY)direction=!direction;
            else if(state==CHARGE){
                state= DIZZY;
                if(x<l.getLeftLimit())x=l.getLeftLimit();
                else x= l.getRightLimit()-width/2-3;
            }
        }
        if(direction&&x<l.getLeftLimit()){
            x=l.getLeftLimit()+1;
        }else if(!direction&&x+width>l.getRightLimit()){
            x=l.getRightLimit()-width-1;
        }
        y+=vY;
        if(state!=CHARGE)vY-= World.getGravity();
        if(h.isOnGround()&&vY<0){
            y=h.getGroundLevel();
            vY=0;
            if(state==JUMP&&larano.getCurrentFrameNum()==larano.getFrames().length-1) {
                state = NORMAL;
                if(direction&&Main.getHarold().getX()+Main.getHarold().getWidth()<x||!direction&&Main.getHarold().getX()>x+width)direction=!direction;
            }
        }

        doAttackCalc();
        doSpriteCalc();
        if(damageTakenFrame>0)damageTakenFrame--;
    }

    private void doAttackCalc(){
        if(!(state==NORMAL||state==ATTACK))return;
        final int range=4;
        if(state==ATTACK&&larano.getCurrentFrameNum()==larano.getFrames().length-1){
            state=NORMAL;
            Attack.melee(this,1,range);
        }
        if(attackCooldown>0){
            attackCooldown--;
            return;
        }
        if(Main.getHarold().getY()>y+height||Main.getHarold().getY()+Main.getHarold().getWidth()<y){
            if(checkJump())state=JUMP;
            else state=NORMAL;
            return;
        }
        if(direction){
            if(Main.getHarold().getX()>=x&&Main.getHarold().getX()<=x+width+range){
                state=ATTACK;
                attackCooldown=100;
            }else if(Main.getHarold().getX()>=x)tryCharge();
        }else{
            if(Main.getHarold().getX()+Main.getHarold().getWidth()<=x+width&&Main.getHarold().getWidth()+Main.getHarold().getX()>=x-range){
                state=ATTACK;
                attackCooldown=100;
            }else if(Main.getHarold().getX()+Main.getHarold().getWidth()<=x+width)tryCharge();
        }
    }

    private boolean checkJump(){
        HeightVal h=HeightMap.findNextJumpPlat(hitbox,direction,height);
        return h != null&&Main.getHarold().getY()>y+height;
    }

    private void tryCharge(){
        if(chargeAttemptCooldown>0){
            chargeAttemptCooldown--;
            return;
        }
        double rand=Math.random();
        if(rand<.9)chargeAttemptCooldown=50;
        else state=CHARGERDY;
    }

    private void doSpriteCalc(){
        if(state!=READY&&state!=JUMP)larano.setFps(10);
        else if(state==JUMP)larano.setFps(4);
        switch(state){
            case NORMAL:
                larano.setFrames(ResourceHandler.getBossLoader().getLaranoWalk(direction));
            break;
            case READY:
                larano.setFrames(ResourceHandler.getBossLoader().getLaranoReadying());
                if(larano.getCurrentFrameNum()==larano.getFrames().length-1){
                    state=NORMAL;
                    larano.setFps(10);
                    x=Graphics.convertToWorldWidth(541);
                    y=5;
                    larano.setFrames(ResourceHandler.getBossLoader().getLaranoWalk(direction));
                    attackCooldown=100;
                }
            break;
            case ATTACK:
                altAttack= !(Main.getHarold().getY() < y + width / 2);
                if(altAttack){
                    larano.setFrames(ResourceHandler.getBossLoader().getLaranoAltAttack(direction));
                }else{
                    larano.setFrames(ResourceHandler.getBossLoader().getLaranoAttack(direction));
                }
            break;
            case CHARGERDY:
                larano.setFrames(ResourceHandler.getBossLoader().getLaranoShimmer(direction));
                if(larano.getCurrentFrameNum()==larano.getFrames().length-1)state=CHARGE;
            break;
            case CHARGE:
                larano.setFrames(new ImageResource[]{ResourceHandler.getBossLoader().getLaranoDash(direction)});
            break;
            case DIZZY:
                larano.setFrames(ResourceHandler.getBossLoader().getLaranoDizzy(direction));
                if(larano.getCurrentFrameNum()==larano.getFrames().length-1){
                    dizzyCount++;
                }
                if(dizzyCount==60){
                    state=NORMAL;
                    dizzyCount=0;
                }
            break;
            case DAMAGE:
                if(damageTakenFrame==0)state=NORMAL;
                else larano.setFrames(new ImageResource[]{ResourceHandler.getBossLoader().getLaranoDamage(direction)});
            break;
            case JUMP:
                larano.setFrames(ResourceHandler.getBossLoader().getLaranoJump(direction));
            break;
        }
        sprite=larano.getCurrentFrame();
        if(!(state==JUMP&&larano.getCurrentFrameNum()==larano.getFrames().length-1))
        larano.update();
        else
            if(vY==0)vY=3;
    }

    @Override
    public void render() {
        if(sprite==null)return;
        Graphics.setIgnoreScale(true);
        width= Graphics.convertToWorldWidth(sprite.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(sprite.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        if(damageTakenFrame>0)Graphics.setColor(1,0,0,1);
        else Graphics.setColor(1,1,1,1);
        if(state==READY)Graphics.drawImage(sprite,0,0);
        else Graphics.drawImage(sprite,x-doOffsetCalc(),y);
        Graphics.setIgnoreScale(false);
        Graphics.setColor(1,1,1,1);
    }

    private float doOffsetCalc(){
        if(direction)return 0;
        if (state == ATTACK) {
            if (altAttack) {
                switch (larano.getCurrentFrameNum()) {
                    case 0:
                        return 0;
                    case 1:
                        return Graphics.convertToWorldWidth(10);
                    case 2:
                        return Graphics.convertToWorldWidth(99);
                    case 3:
                        return Graphics.convertToWorldWidth(143);
                }
            }
        }
        return 0;
    }

    @Override
    public void reset() {
        direction=false;
        larano.setFps(30);
        larano.setFrames(ResourceHandler.getBossLoader().getLaranoReadying());
        sprite=larano.getCurrentFrame();
        state=-1;
        health=4;
        maxHealth=4;
        x=0;
        y=0;
        dizzyCount=0;
    }

    @Override
    public String toString() {
        return "Larano @ "+x+","+y;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    @Override
    public void doDamage(Entity attacker, int damage) {
        if(attacker.getDisplayName().equals("Stalactite")&&state== DIZZY){
            super.doDamage(attacker, damage);
            state=DAMAGE;
            damageCooldown=0;
        }
    }

    @Override
    public void handleDeath() {
        bossBar.update();
    }
}
