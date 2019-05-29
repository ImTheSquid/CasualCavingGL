package org.graphics;

import org.loader.ImageResource;

public class Animator {
    private ImageResource[] frames;
    private int fps,currentFrame=0;
    private long lastFrameTime=0,delay=0;
    private boolean direction=true,active=true;
    public Animator(ImageResource[] frames,int fps){
        this.frames=frames;
        this.fps=fps;
    }

    public void setFrames(ImageResource[] frames) {
        if(currentFrame>=frames.length)currentFrame=0;
        this.frames = frames;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void update(){
        long currentTime=System.nanoTime();
        if(!active)return;
        if(delay>0){
            delay--;
            return;
        }
        if(currentTime>lastFrameTime+1000000000/fps){
            if(direction){
                if(currentFrame==frames.length-1){
                    direction=false;
                }else{
                    currentFrame++;
                }
            }else{
                if(currentFrame==0){
                    direction=true;
                }else{
                    currentFrame--;
                }
            }
            lastFrameTime=currentTime;
        }
    }

    public ImageResource getCurrentFrame(){
        return frames[currentFrame];
    }

    public int getCurrentFrameNum(){return currentFrame;}

    public ImageResource[] getFrames() {
        return frames;
    }

    public long getDelay() {
        return delay;
    }
}
