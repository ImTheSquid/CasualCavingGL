package org.graphics;

public class Timer {
    private float min, max, increment;
    private float current;
    private int fps;
    private long lastFrameTime = 0;
    // What direction to go in (true->forwards, false->backwards); whether to run loop; whether to loop to beginning
    private boolean direction = true, active = false, loop = false;
    private long delay = 0;

    public Timer(float min, float max, float start, float inc, int fps) {
        this.min = min;
        this.max = max;
        current = start;
        increment = inc;
        this.fps = fps;
    }

    public void update() {
        if (!active) return;
        long currentTime = System.nanoTime();
        if (currentTime > lastFrameTime + 1000000000 / fps) {
            if (delay > 0) {
                delay--;
            } else {
                if (direction) {
                    current += increment;
                    if (current > max) current = loop ? min : max;
                }
                else{
                    current -= increment;
                    if (current < min) current = loop ? max : min;
                }

            }
            lastFrameTime = currentTime;
        }
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public void setFrameDelay(long delay){
        if(delay<0)this.delay=0;
        else this.delay=delay;
    }

    public void setSecondDelay(long delay){
        if(delay>0)this.delay=delay*fps;
    }

    public void setCurrent(float c){
        current=c;
    }

    public float getCurrent(){
        return current;
    }

    public boolean getDirection(){
        return direction;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public float getFrameDelay() {
        return delay;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
