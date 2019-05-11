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

public class GreenGolem extends Autonomous {
    private Animator golemAnimator=new Animator(ResourceHandler.getGolemLoader().getGreenGolemWalk(direction),10);
    private ImageResource greenGolem;
    private SmartRectangle hitbox=new SmartRectangle(x,y,width,height);
    public GreenGolem(int subLevel,float spawnX,float spawnY){
        super(subLevel,spawnX,spawnY);
        health=2;
    }

    @Override
    public void update() {
        HeightReturn h= HeightMap.onGround(hitbox);

        if(damageTakenFrame==0){
            if (direction) {
                vX = .25f;
            } else {
                vX = -.25f;
            }
        }else{
            if((direction&&!attackerBehind)||(!direction&&attackerBehind))vX=-1f;
            else vX=1f;
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

        if(damageCooldown>0)damageCooldown--;

        //Y-velocity and ground calc
        if(h.isOnGround()&&vY<0){
            y=h.getGroundLevel();
            vY=0;
        }
        doAttackCalc();

        if(vX==0) {
            greenGolem=ResourceHandler.getGolemLoader().getGreenGolem(direction);
        }else{
            greenGolem=golemAnimator.getCurrentFrame();
        }
        if(state==0)golemAnimator.setFrames(ResourceHandler.getGolemLoader().getGreenGolemWalk(direction));
        else if(state==1)golemAnimator.setFrames(ResourceHandler.getGolemLoader().getGreenGolemAttack(direction));
        golemAnimator.update();
        if(state==1&&golemAnimator.getCurrentFrameNum()==3){
            state=0;
            Attack.attack(this,1,4);
        }
    }

    private void doXCalc(){
        Level l= LevelController.getLevels()[World.getLevel()];
        if(x<l.getLeftLimit()||x+width>l.getRightLimit()||HeightMap.onEdge(hitbox,direction))direction=!direction;
    }

    private void doAttackCalc(){
        if(attackCooldown>0){
            attackCooldown--;
            return;
        }
        if(Main.getHarold().getY()>y+height||Main.getHarold().getY()+Main.getHarold().getWidth()<y)return;
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
        width= Graphics.convertToWorldWidth(greenGolem.getTexture().getWidth());
        height=Graphics.convertToWorldHeight(greenGolem.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        if(damageTakenFrame >0){
            Graphics.setColor(1f,.0f,.0f,1);
        }
        else Graphics.setColor(1,1,1,1);
        Graphics.drawImage(greenGolem,x,y);
    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return "Green Golem @ "+x+","+y;
    }
}
