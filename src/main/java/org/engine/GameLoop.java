package org.engine;

import org.graphics.Render;
import org.world.World;

public class GameLoop {
    long last_time = System.nanoTime();
    private boolean running = true;
    private int currentFPS = 0;

    public void start() {
        Thread game = new Thread(() -> {
            while (running) {
                long time = System.nanoTime();
                float delta_time = (time - last_time) / 1000000000f;
                last_time = time;
                World.update(delta_time);
                Render.render();
                if (delta_time == 0) {
                    currentFPS = 0;
                } else {
                    currentFPS = (int) (1 / delta_time);
                }
            }
            AudioManager.cleanup();
            DiscordHandler.shutdown();
        });
        game.setName("GameLoop");
        game.start();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getCurrentFPS() {
        return currentFPS;
    }
}
