package org.entities.aggressive;

import org.engine.Main;
import org.entities.Autonomous;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.World;

import static com.jogamp.newt.event.KeyEvent.VK_SPACE;

//Key Master (Emerie) class for the end of the Larano boss fight
public class KeyMasterL extends Autonomous {
    private final int WALK_IN=0,TALK=1,WALK_OUT=2,INTO_SUN_STONE=3;
    private int convoProgress=0;
    private Larano larano;
    private Animator animator=new Animator(ResourceHandler.getBossLoader().getEmerieWalk(false),8);
    private ImageResource emerie;
    private String[] conversation={
            "Larano! Stop this fighting!",
            "Keymaster? What are you doing here!?",
            "Oh please, look at yourself. You are far too injured to keep this up.",
            "But this human is possessed by the Sun Golems. He must be stopped!",
            "Get yourself to the medical center, let me take it from here."
    };
    private boolean keymasterTalking=true;
    public KeyMasterL(Larano l) {
        super(2,101, 4);
        larano=l;
        reset();
    }

    @Override
    public void update() {
        if(larano.getHealth()==1&&larano.getvX()==0&&state==-1)state++;
        switch(state){
            case WALK_IN:
                vX=-0.15f;
                animator.update();
                emerie=animator.getCurrentFrame();
                larano.getAnimator().setActive(false);
                larano.getAnimator().setCurrentFrame(0);
                if(x<80){
                    state++;
                }
                break;
            case TALK:
                vX=0;
                if(Keyboard.keys.contains(VK_SPACE)){
                    if(convoProgress<4)convoProgress++;
                    else{
                        larano.increaseState();
                        state++;
                    }
                    keymasterTalking=!keymasterTalking;
                    larano.getAnimator().setCurrentFrame(convoProgress);
                    larano.updateSprite();
                    while(Keyboard.keys.contains(VK_SPACE)){}
                }
                if(convoProgress==2)emerie=ResourceHandler.getBossLoader().getEmerieGesture(true);
                else emerie=ResourceHandler.getBossLoader().getEmerieGesture(false);
                break;
            case WALK_OUT:
                vX=0.15f;
                animator.setFrames(ResourceHandler.getBossLoader().getEmerieWalk(true));
                animator.update();
                emerie=animator.getCurrentFrame();
                if(x>101&&larano.getX()>101){
                    state++;
                    World.getMaster().setDirection(false);
                    World.getMaster().setActive(true);
                }
                break;
            case INTO_SUN_STONE:
                World.setMasterColor(1,1,1);
                if(World.getMaster().getCurrent()==0&&!World.getMaster().getDirection()){
                    World.setSubLevel(World.getSubLevel()+1);
                    World.getMaster().setActive(true);
                    World.getMaster().setDirection(true);
                    Main.getHarold().setX(5);
                    Main.getHarold().setY(7);
                }
        }
        x+=vX;
    }

    @Override
    public void render() {
        Graphics.setDrawColor(1,1,1,1);
        if(emerie!=null)Graphics.drawImage(emerie,x+calcOffset(),y);
        switch(state){
            case WALK_IN:
                //Implement later
                break;
            case TALK:
                Graphics.setFont(Graphics.SMALL);
                if(keymasterTalking)Graphics.drawText(conversation[convoProgress],75,30,25,true);
                else Graphics.drawText(conversation[convoProgress],55,25,25,true);
                break;
            case WALK_OUT:
                //Implement later on
                break;
        }
    }

    private float calcOffset(){
        if(state==TALK){
            if(convoProgress==2)return -Graphics.convertToWorldWidth(82-16);
            else return -Graphics.convertToWorldWidth(16);
        }
        return 0;
    }

    @Override
    public void reset() {
        state=-1;
        convoProgress=0;
        keymasterTalking=true;
        vX=0;
        x=101;
        y=4;
        animator.setFrames(ResourceHandler.getBossLoader().getEmerieWalk(false));
    }
}
