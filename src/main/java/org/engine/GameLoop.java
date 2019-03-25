package org.engine;

public class GameLoop {
    private boolean running=true;
    private int updates=0;
    private final int MAX_UPDATES=5;

    private long lastUpdateTime=0;

    private static int targetFPS=60,targetTime=1000000000/targetFPS;
    public void start(){
        Thread game=new Thread(){
            public void run(){
                lastUpdateTime=System.nanoTime();
                int fps=0;
                long lastFpsCheck=System.nanoTime();
                while(running){
                    long currentTime=System.nanoTime();
                    //Lag protection
                    updates=0;
                    while(currentTime-lastUpdateTime>=targetTime){
                        
                    }
                }
            }
        };
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
