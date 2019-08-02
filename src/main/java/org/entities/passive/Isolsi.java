package org.entities.passive;

import org.engine.Main;
import org.entities.Autonomous;
import org.graphics.FadeIO;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_SPACE;

public class Isolsi extends Autonomous {
    private int convoState;
    private boolean crossfadeActive;
    private FadeIO crossfade=new FadeIO(0,1,1,0.01f,60);
    private ImageResource[] isolsi= ResourceHandler.getGolemLoader().getIsolsi();
    private String[] conversation={"That orange golem certainly caused you a bit of trouble didn't he.",
            "Does he not know the importance of your mission?!",
            "The nerve of those lesser golems!",
            "If anyone should try to stand in our way like that again...",
            "don't hold back."};
    public Isolsi() {
        super(3,64,60);
        reset();
    }

    @Override
    public void update() {
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
                    convoState=0;
                }
                break;
            default:
                if(Keyboard.keys.contains(VK_SPACE)&&convoState+1<conversation.length){
                    convoState++;
                }else if(Keyboard.keys.contains(VK_SPACE)){
                    World.setLevelTransition(true);
                }
                while(Keyboard.keys.contains(VK_SPACE)){}
        }
        y+=vY;
        vY-=vY-World.getGravity()>=0?World.getGravity():vY;
        crossfade.setActive(crossfadeActive);
        crossfade.update();
    }

    @Override
    public void render() {
        if (state == -1) return;
        if (state < 2){
            Graphics.setColor(1, 1, 1, crossfade.getCurrent());
            Graphics.drawImage(isolsi[state], x - Graphics.convertToWorldWidth(calcXOffset(state)), y + Graphics.convertToWorldHeight(calcYOffset(state)));
            if (crossfadeActive && state + 1 < isolsi.length) {
                Graphics.setColor(1, 1, 1, 1 - crossfade.getCurrent());
                Graphics.drawImage(isolsi[state + 1], x - Graphics.convertToWorldWidth(calcXOffset(state + 1)), y + Graphics.convertToWorldHeight(calcYOffset(state + 1)));
            }
        }else{
            Graphics.drawImage(isolsi[calcConvoSprite()], x - Graphics.convertToWorldWidth(calcXOffset(calcConvoSprite())), y + Graphics.convertToWorldHeight(calcYOffset(calcConvoSprite())));
        }
        Graphics.setColor(1,1,1,1);
        Graphics.setFont(Graphics.SMALL_FONT);
        if(convoState>-1){
            Graphics.drawText(conversation[convoState],45,45,20,true);
        }
    }

    private float calcXOffset(int frame){
        switch(frame){
            case 0: return -26;
            case 1: return 91-37;
            case 2: //Collapse
            case 3: return 54-37;
            case 4: return 106-37;
            case 5: return 139-37;
            case 6: return 92-37;
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
        switch(convoState){
            case 4: return 6;
            case 1: return 4;
            case 2: return 5;
            case 3:
            case 0:
            default:return 3;
        }
    }

    @Override
    public void reset() {
        state=-1;
        convoState=-1;
        crossfadeActive=false;
        crossfade.setActive(false);
        crossfade.setDirection(false);
        crossfade.setCurrent(1);
    }

    @Override
    public String toString() {
        return "Isolsi @ "+x+","+y;
    }
}
