package org.engine;

import org.graphics.Render;
import org.world.World;

public class GameLoop {
    private boolean running=true;
    private int updates=0,currentFPS=0;
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
                        World.update();
                        DiscordHandler.updatePresence();
                        lastUpdateTime+=targetTime;
                        updates++;
                        if(updates>MAX_UPDATES)break;
                    }

                    long startTime=System.nanoTime();
                    Render.render();

                    //FPS counter
                    fps++;
                    if (System.nanoTime() >= lastFpsCheck + 1000000000) {
                        currentFPS=fps;
                        fps=0;
                        lastFpsCheck=System.nanoTime();
                    }

                    long timeTaken=System.nanoTime()-startTime;
                    if(timeTaken<targetTime){
                        try {
                            Thread.sleep((targetTime-timeTaken)/1000000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                AudioManager.cleanup();
                DiscordHandler.shutdown();
            }
        };
        game.setName("GameLoop");
        game.start();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getCurrentFPS() {
        return currentFPS;
    }

    public void overrideUpdateTime(){lastUpdateTime=System.nanoTime();}

    public boolean isRunning() {
        return running;
    }
}
