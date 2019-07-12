package org.entities.aggressive;

import org.entities.Autonomous;
import org.graphics.Graphics;
import org.input.Keyboard;

import static com.jogamp.newt.event.KeyEvent.VK_SPACE;

//Key Master class for the end of the Larano boss fight
public class KeyMasterL extends Autonomous {
    private final int WALK_IN=0,TALK=1,WALK_OUT=2;
    private int convoProgress=0;
    private Larano larano;
    private String[] conversation={
            "Larano! Stop this fighting!",
            "Keymaster? What are you doing here!?",
            "Oh please, look at yourself. You’re far too injured to keep this up.",
            "But this human is possessed by the Sun Golems. He must be stopped!",
            "Get yourself to the medical center, let me take it from here."
    };
    private boolean keymasterTalking=true;
    public KeyMasterL(Larano l) {
        super(2,101, 7);
        larano=l;
    }

    @Override
    public void update() {
        if(larano.getHealth()==1&&larano.getvX()==0&&state==-1)state++;
        switch(state){
            case WALK_IN:
                //Walk-in code for when sprites are available
                state++;
                break;
            case TALK:
                if(Keyboard.keys.contains(VK_SPACE)){
                    if(convoProgress<4)convoProgress++;
                    else state++;
                    keymasterTalking=!keymasterTalking;
                    larano.updateSprite();
                    while(Keyboard.keys.contains(VK_SPACE)){}
                }
                break;
            case WALK_OUT:
                //Walk-out code for when sprites are available
                break;
        }
    }

    @Override
    public void render() {
        switch(state){
            case WALK_IN:
                //Implement later
                break;
            case TALK:
                Graphics.setFont(Graphics.SMALL_FONT);
                if(keymasterTalking)Graphics.drawText(conversation[convoProgress],75,30,15,true);
                else Graphics.drawText(conversation[convoProgress],55,30,15,true);
                break;
            case WALK_OUT:
                //Implement later on
                break;
        }
    }

    @Override
    public void reset() {
        state=-1;
        convoProgress=0;
        keymasterTalking=true;
    }

    @Override
    public String toString() {
        return null;
    }
}
