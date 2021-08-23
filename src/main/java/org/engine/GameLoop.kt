package org.engine

import org.engine.AudioManager.cleanup
import org.engine.DiscordHandler.shutdown
import org.engine.DiscordHandler.updatePresence
import org.graphics.Render
import org.world.World.update

class GameLoop {
    private var lastTime = System.nanoTime()
    private var running = true
    var currentFPS = 0
        private set

    fun start() {
        val game = Thread {
            while (running) {
                val time = System.nanoTime()
                val deltaTime = (time - lastTime) / 1000000000f
                lastTime = time
                update(deltaTime)
                updatePresence()
                Render.render()
                currentFPS = if (deltaTime == 0f) {
                    0
                } else {
                    (1 / deltaTime).toInt()
                }
            }
            cleanup()
            shutdown()
        }
        game.name = "GameLoop"
        game.start()
    }

    fun setRunning(running: Boolean) {
        this.running = running
    }
}