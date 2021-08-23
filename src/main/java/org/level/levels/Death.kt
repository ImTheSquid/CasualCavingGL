package org.level.levels

import com.jogamp.newt.event.KeyEvent
import org.engine.AudioManager
import org.engine.Main
import org.graphics.Graphics
import org.graphics.Render
import org.graphics.TimedGradient
import org.input.Keyboard
import org.level.Level
import org.level.LevelController
import org.loader.ImageResource
import org.world.World

class Death : Level(arrayOf<ImageResource>(), 1) {
    private val textHandler = TimedGradient(0f, 1f, 0f, 0.02f, 35)
    private var fadeDir = true
    override fun init() {
        World.isHaroldEvil = false
        Render.setCameraX(0f)
        Render.setCameraY(0f)
        if (World.master.isActive) {
            World.master.isActive = false
            World.master.current = 1f
        }
        Main.getHarold().setVisible(false)
        Main.getHarold().setMovement(false)
        textHandler.current = 0f
        textHandler.direction = true
        textHandler.isActive = true
        fadeDir = true
        AudioManager.setMusicPlayback(AudioManager.STOP)
    }

    override val assets: Array<ImageResource>
        get() = arrayOf()

    override fun update(subLevel: Int, deltaTime: Float) {
        if (fadeDir) {
            if (textHandler.current == 1f) {
                fadeDir = false
                textHandler.setSecondDelay(2)
                textHandler.direction = false
            } else {
                textHandler.direction = true
                textHandler.isActive = true
            }
        } else if (textHandler.current == 0f) {
            finish()
        }
        if (Keyboard.keys.contains(KeyEvent.VK_SPACE) && textHandler.current > 0.25f) finish()
        textHandler.update()
    }

    private fun finish() {
        fadeDir = true
        cleanup()
        World.clearEntites()
        LevelController.resetAll()
        Main.getHarold().reset()
        World.setLevel(World.getLevel() + 1)
        World.subLevel = 1
    }

    override fun render(subLevel: Int) {
        Graphics.setFont(Graphics.FontType.TITLE)
        Graphics.setDrawColor(1f, 1f, 1f, textHandler.current)
        Graphics.drawTextCentered("Game Over", Render.unitsWide / 2, 35f)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
    }

    override fun renderForeground(subLevel: Int) {}
    override fun cleanup() {
        Main.getHarold().setVisible(true)
        Main.getHarold().setMovement(true)
        Main.getHarold().reset()
    }

    override fun reset() {
        cleanup()
    }
}