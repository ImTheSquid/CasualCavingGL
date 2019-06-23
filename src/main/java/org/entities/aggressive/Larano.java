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

public class Larano extends Autonomous {
    private final int NORMAL=0,READY=1,ATTACK=2,CHARGE=3,WOOZY=4;
    private Animator larano=new Animator(ResourceHandler.getBossLoader().getLaranoReadying(),20);
    private ImageResource sprite=null;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    public Larano() {
        super(2, 0, 0);
        reset();
    }

    @Override
    public void update() {
        if(Main.getHarold().getX()>10&&state==-1)state=READY;
        if(state==-1)return;
        if(state==NORMAL){
            if(direction)vX=.2f;
            else vX=-.2f;
        }
        x+=vX;
        Level l= LevelController.getCurrentLevel();
        if(x<l.getLeftLimit()||x+width>l.getRightLimit())direction=!direction;
        doAttackCalc();
        doSpriteCalc();
    }

    private void doAttackCalc(){
        if(state==READY)return;
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
            state=NORMAL;
            return;
        }
        if(direction){
            if(Main.getHarold().getX()>=x&&Main.getHarold().getX()<=x+width+range){
                state=ATTACK;
                attackCooldown=100;
            }
        }else{
            if(Main.getHarold().getX()+Main.getHarold().getWidth()<=x+width&&Main.getHarold().getWidth()+Main.getHarold().getX()>=x-range){
                state=ATTACK;
                attackCooldown=100;
            }
        }
    }

    private void doSpriteCalc(){
        if(state==READY&&larano.getCurrentFrameNum()==larano.getFrames().length-1){
            state=NORMAL;
            x=Graphics.convertToWorldWidth(541);
            y=5;
            larano.setFps(10);
        }
        switch(state){
            case NORMAL:larano.setFrames(ResourceHandler.getBossLoader().getLaranoWalk(direction));
            break;
            case READY:larano.setFrames(ResourceHandler.getBossLoader().getLaranoReadying());
            break;
            case ATTACK:larano.setFrames(ResourceHandler.getBossLoader().getLaranoAttack(direction));
            break;
        }
        sprite=larano.getCurrentFrame();
        larano.update();
    }

    @Override
    public void render() {
        if(sprite==null)return;
        Graphics.setIgnoreScale(true);
        width= Graphics.convertToWorldWidth(sprite.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(sprite.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        if(state==READY)Graphics.drawImage(sprite,0,0);
        else Graphics.drawImage(sprite,x,y);
        Graphics.setIgnoreScale(false);
    }

    private float doOffsetCalc(){
        return 0;
    }

    @Override
    public void reset() {
        direction=false;
        larano.setFrames(ResourceHandler.getBossLoader().getLaranoReadying());
        sprite=larano.getCurrentFrame();
        state=-1;
        health=3;
        x=0;
        y=0;
    }

    @Override
    public String toString() {
        return "Larano @ "+x+","+y;
    }
}
