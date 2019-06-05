package org.entities.aggressive;

import org.engine.Main;
import org.entities.Autonomous;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.input.Keyboard;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

import java.security.Key;

import static com.jogamp.newt.event.KeyEvent.VK_SPACE;

public class CineLarano extends Autonomous {
    private final int ACCUSING=0,ASKING=1,DESCRIBING=2,GESTURING=3,INTRIGUED=4,REALIZING=5,STERN=6;
    private Animator cine=new Animator(ResourceHandler.getBossLoader().getCineWalk(),5);
    private ImageResource sprite;
    private boolean speechDone=false;
    private int speechState=0;
    public CineLarano() {
        super(1,66,11);
        sprite=cine.getCurrentFrame();
    }

    @Override
    public void update() {
        while(Main.getHarold().getX()<20)return;
        if(Keyboard.keys.contains(VK_SPACE)&&state==1){
            while (Keyboard.keys.contains(VK_SPACE)){}
            speechState++;
        }
        if(!speechDone){
            Main.getHarold().setHarold(ResourceHandler.getHaroldLoader().getHarold());
            Main.getHarold().setMovement(false);
        }
        else Main.getHarold().setMovement(true);
        switch(state) {
            case 0:
                vX = -0.1f;
                x += vX;
                sprite = cine.getCurrentFrame();
                if (x <= 50) state = 1;
                cine.update();
                break;
            case 1:
                doSpeech();
                break;
        }
    }

    private void doSpeech(){
        switch(speechState){
            case 0:
                sprite=ResourceHandler.getBossLoader().getCineExpression()[INTRIGUED];
                break;
            case 1:
                sprite=ResourceHandler.getBossLoader().getCineExpression()[GESTURING];
                break;
            case 2:
                sprite=ResourceHandler.getBossLoader().getCineExpression()[ASKING];
                break;
        }
    }

    @Override
    public void render() {
        if(sprite==null)return;
        Graphics.setColor(1,1,1,1);
        doSpeechDisplay();
        Graphics.drawImage(sprite,x,y);
    }

    private void doSpeechDisplay(){
        if(state!=1)return;
        switch(speechState){
            case 0:
                Graphics.drawText("Hmmm, I was told a sun golem was headed this way... [SPACE]",51,37,18,true);
                break;
            case 1:
                Graphics.drawText("But you're no sun golem, you're just a human. [SPACE]",51,37,18,true);
                break;
            case 2:
                Graphics.drawText("You wouldn't have happened to have seen any of them would you?",51,37,18,true);
                break;
        }
    }

    @Override
    public void reset() {
        state=0;
        x=66;
        y=11;
        speechState=0;
        cine.setCurrentFrame(0);
        sprite=cine.getCurrentFrame();
    }

    @Override
    public String toString() {
        return null;
    }
}
