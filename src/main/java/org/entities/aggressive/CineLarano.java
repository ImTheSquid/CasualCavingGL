package org.entities.aggressive;

import org.engine.Main;
import org.entities.Autonomous;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.graphics.Render;
import org.input.Keyboard;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

import static com.jogamp.newt.event.KeyEvent.VK_SPACE;

public class CineLarano extends Autonomous {
    private Animator cine=new Animator(ResourceHandler.getBossLoader().getCineWalk(false),5);
    private ImageResource sprite;
    private boolean speechDone=false;
    private int speechState=0;
    public CineLarano() {
        super(1,66,11);
        sprite=cine.getCurrentFrame();
        invincible=true;
    }

    @Override
    public void update(float deltaTime) {
        if (Main.getHarold().getX() < 20 && speechState == 0) return;
        if (Keyboard.keys.contains(VK_SPACE) && state == 1 && Main.getHarold().areControlsLocked()) {
            while (Keyboard.keys.contains(VK_SPACE)) {
            }
            speechState++;
        }
        if (!speechDone && speechState != 5) {
            Main.getHarold().setHarold(ResourceHandler.getHaroldLoader().getHarold());
            Main.getHarold().setLockControls(true);
        } else Main.getHarold().setLockControls(false);
        if (speechState == 5 && Main.getHarold().getX() > 30) speechState = 6;
        switch (state) {
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
            case 2:
                vX=0.1f;
                x+=vX;
                sprite=cine.getCurrentFrame();
                if(x> Render.unitsWide){
                    state=3;
                }
                cine.update();
                break;
            case 3:
                speechDone=true;
                break;
        }
    }

    private void doSpeech(){
        final int ACCUSING=0,ASKING=1,DESCRIBING=2,GESTURING=3,INTRIGUED=4,REALIZING=5,STERN=6;
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
            case 3:
            case 4:
                sprite=ResourceHandler.getBossLoader().getCineExpression()[DESCRIBING];
                break;
            case 6:
                sprite=ResourceHandler.getBossLoader().getCineExpression()[REALIZING];
                break;
            case 7:
            case 8:
                sprite=ResourceHandler.getBossLoader().getCineExpression()[ACCUSING];
                break;
            case 9:
                sprite=ResourceHandler.getBossLoader().getCineExpression()[STERN];
                break;
            case 10:
                cine.setFrames(ResourceHandler.getBossLoader().getCineWalk(true));
                state=2;
                break;
        }
    }

    @Override
    public void render() {
        if(sprite==null)return;
        Graphics.setDrawColor(1,1,1,1);
        doSpeechDisplay();
        Graphics.drawImage(sprite, x + Graphics.toWorldWidth(getOffset()), y);
    }

    private float getOffset(){
        if(state!=1)return 0;
        switch(speechState){
            case 0:
            case 6:
            case 9:
            case 10:
                return 0;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return -41;
            default: return -60;
        }
    }

    private void doSpeechDisplay(){
        if(state!=1)return;
        Graphics.setFont(Graphics.SMALL);
        switch(speechState){
            case 0:
                Graphics.drawText("Hmmm, I was told a sun golem was headed this way... [SPACE]",51,37,18,true);
                break;
            case 1:
                Graphics.drawText("But you're no sun golem, you're just a human. [SPACE]",51,37,18,true);
                break;
            case 2:
                Graphics.drawText("You wouldn't have happened to have seen any of them would you? [SPACE]",51,37,18,true);
                break;
            case 3:
                Graphics.drawText("They look like us regular golems, but they're a bit bigger, their flesh is a golden yellow color, [SPACE]",51,42,18,true);
                break;
            case 4:
                Graphics.drawText("and each of them had a large gem somewhere on their body. [SPACE]",51,37,18,true);
                break;
            case 6:
                Graphics.drawText("Wait a second! [SPACE]",51,35,18,true);
                break;
            case 7:
                Graphics.drawText("That glint in your eye; the evil aura radiating off of you... [SPACE]",51,37,18,true);
                break;
            case 8:
                Graphics.drawText("Human, you have the sun golems' power don't you... [SPACE]",51,37,18,true);
                break;
            case 9:
                Graphics.drawText("Well then, we'll do battle in the next chamber. Follow if you dare... [SPACE]",51,37,18,true);
                break;
        }
    }

    @Override
    public void reset() {
        cine.setFrames(ResourceHandler.getBossLoader().getCineWalk(false));
        state=0;
        x=66;
        y=11;
        speechState=0;
        cine.setCurrentFrame(0);
        sprite=cine.getCurrentFrame();
        speechDone=false;
    }
}
