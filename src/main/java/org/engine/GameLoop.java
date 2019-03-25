package org.engine;

public class GameLoop {
    private boolean running;
    public void start(){
        Thread game=new Thread(){
            public void run(){

            }
        };
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
