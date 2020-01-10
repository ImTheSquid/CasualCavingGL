package org.entities.passive;

import org.engine.Main;
import org.entities.Autonomous;
import org.graphics.Graphics;
import org.graphics.Timer;
import org.input.Keyboard;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_SPACE;

@SuppressWarnings("StatementWithEmptyBody")
public class Isolsi extends Autonomous {
    private static final int LOOK_DOWN=3, GESTURE=4, SLIGHT_ANGER=5, CLENCH_FIST=6, POINTING=7, ANGRY=8, QUESTION=9;
    private int convoState=0;
    private boolean crossfadeActive, inPostLaranoScene;
    private Timer crossfade=new Timer(0,1,1,0.01f,60);
    private ImageResource[] isolsi= ResourceHandler.getGolemLoader().getIsolsi();
    private String[] postLaranoConvo ={"That orange golem certainly caused you a bit of trouble didn't he.",
            "Does he not know the importance of your mission?!",
            "The nerve of those lesser golems!",
            "If anyone should try to stand in our way like that again...",
            "don't hold back."};
    private String[] postBoulderConvo={"What are you doing?",
            "You could have died doing that!",
            "And for what?",
            "The precious model town is safe...",
            "It's not real!",
            "It's not worth our time!",
            "Now stop fooling around...",
            "...and TAKE THINGS SERIOUSLY!"};
    
    public Isolsi(boolean inPostLaranoScene) {
        super(3, 64, 60);
        if(!inPostLaranoScene){
            y=7;
            subLevel=5;
            state=2;
        }
        this.inPostLaranoScene =inPostLaranoScene;
        reset();
    }

    @Override
    public void update() {
        if(inPostLaranoScene)larano();
        else boulder();
        y+=vY;
        vY-=vY-World.getGravity()>=0?World.getGravity():vY;
        crossfade.setActive(crossfadeActive);
        crossfade.update();
    }

    private void larano(){
        switch(state){
            case -1:
                if(Main.getHarold().getX()>15){
                    Main.getHarold().setLockControls(true);
                    state++;
                }
                break;
            case 0:
                if(y>7)vY=-0.15f;
                else{
                    crossfadeActive=true;
                }
                if(crossfade.isActive()&&crossfade.getCurrent()==0){
                    crossfade.setCurrent(1);
                    state++;
                }
                break;
            case 1:
                if(crossfade.isActive()&&crossfade.getCurrent()==0){
                    crossfadeActive=false;
                    crossfade.setCurrent(1);
                    state++;
                }
                break;
            default:
                if(Keyboard.keys.contains(VK_SPACE)&&convoState+1< postLaranoConvo.length){
                    convoState++;
                }else if(Keyboard.keys.contains(VK_SPACE)){
                    World.setLevelTransition(true);
                }
                while(Keyboard.keys.contains(VK_SPACE)){}
        }
    }

    private void boulder(){
        Main.getHarold().setX(10);
        ResourceHandler.getHaroldLoader().setDirection(true);
        if(state==2||convoState==0) {
            Main.getHarold().setLockControls(true);
            if (Keyboard.keys.contains(VK_SPACE) && convoState + 1 < postBoulderConvo.length) {
                convoState++;
            } else if (Keyboard.keys.contains(VK_SPACE)) {
                state++;
            }
            while (Keyboard.keys.contains(VK_SPACE)) {
            }
        }else if(state==3){
            World.setMasterColor(1,1,1);
            World.getMaster().setDirection(false);
            if(World.getMaster().getCurrent()==0){
                World.incrementSubLevel();
                World.getMaster().setDirection(true);
                Main.getHarold().setLockControls(false);
            }
        }
    }

    @Override
    public void render() {
        if (state == -1) return;
        if (state < 2){
            Graphics.setDrawColor(1, 1, 1, crossfade.getCurrent());
            Graphics.drawImage(isolsi[state], x - Graphics.toWorldWidth(calcXOffset(state)),
                    y + Graphics.convertToWorldHeight(calcYOffset(state)));
            if (crossfadeActive && state + 1 < isolsi.length) {
                Graphics.setDrawColor(1, 1, 1, 1 - crossfade.getCurrent());
                Graphics.drawImage(isolsi[state + 1],
                        x - Graphics.toWorldWidth(calcXOffset(state + 1)),
                        y + Graphics.convertToWorldHeight(calcYOffset(state + 1)));
            }
        }else{
            Graphics.drawImage(isolsi[calcConvoSprite()],
                    x - Graphics.toWorldWidth(calcXOffset(calcConvoSprite())),
                    y + Graphics.convertToWorldHeight(calcYOffset(calcConvoSprite())));
        }
        Graphics.setDrawColor(1,1,1,1);
        Graphics.setFont(Graphics.SMALL);
        if(convoState>-1){
            if(inPostLaranoScene)Graphics.drawText(postLaranoConvo[convoState],45,45,20,true);
            else Graphics.drawText(postBoulderConvo[convoState],45,45,20,true);
        }
    }

    private float calcXOffset(int frame){
        switch(frame){
            case 0: return -26;
            case 1: return 91-37;
            case 2: //Collapse
            case LOOK_DOWN: return 54-37;
            case GESTURE: return 106-37;
            case SLIGHT_ANGER: return 139-37;
            case CLENCH_FIST: return 92-37;
            case POINTING: return 129-37;
            case ANGRY: return 113-37;
            case QUESTION: return 106-37;
            default: return 0;
        }
    }

    private float calcYOffset(int frame){
        switch(frame){
            case 0: return 387;
            case 1: return -35.8f;
            default: return 0;
        }
    }

    private int calcConvoSprite(){
        if(inPostLaranoScene)
            switch(convoState){
                case 4: return CLENCH_FIST;
                case 1: return GESTURE;
                case 2: return SLIGHT_ANGER;
                case 3:
                case 0:
                default:return LOOK_DOWN;
            }
        else
            switch(convoState){
                case 0: return QUESTION;
                case 1: return SLIGHT_ANGER;
                case 3: return GESTURE;
                case 4:
                case 5:
                case 2: return ANGRY;
                case 6:
                case 7: return POINTING;
                default: return LOOK_DOWN;
            }
    }

    @Override
    public void reset() {
        if(inPostLaranoScene)state=-1;
        else state=2;
        convoState=0;
        crossfadeActive=false;
        crossfade.setActive(false);
        crossfade.setDirection(false);
        crossfade.setCurrent(1);
    }
}
