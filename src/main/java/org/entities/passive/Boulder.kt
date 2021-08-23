package org.entities.passive

import com.jogamp.newt.event.KeyEvent
import org.engine.Main
import org.entities.Autonomous
import org.entities.Entity
import org.entities.SmartRectangle
import org.graphics.Animator
import org.graphics.Graphics
import org.graphics.Render
import org.graphics.TimedGradient
import org.input.Keyboard
import org.level.LevelController
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.World

class Boulder : Autonomous(4, 73f, 16f) {
    private var rotation = 0f
    var isDone = false
        private set
    var isTownOK = false
        private set
    private var isFaltering = false

    //Defines key that needs to be pressed during quick time events
    private var quickKey: Short = 0
    private val falterTimedGradient = TimedGradient(0f, 1f, 0f, 1f, 1)
    private val deathTimedGradient = TimedGradient(0f, 2f, 0f, 1f, 1)

    //Animator that controls Harold during this minigame
    private val haroldPuppet = Animator(ResourceHandler.getHaroldLoader().boulder, 12)
    override fun update(deltaTime: Float) {
        when (state) {
            -1 -> {
                vX = 0f
                if (Keyboard.keys.contains(KeyEvent.VK_Q)) {
                    state = 1
                } else if (Keyboard.keys.contains(KeyEvent.VK_E)) {
                    state = 2
                }
            }
            0 -> {
                vX = 0f
                if (Keyboard.keys.contains(KeyEvent.VK_Q)) {
                    state = 1
                } else if (Keyboard.keys.contains(KeyEvent.VK_E)) {
                    state = 2
                }
            }
            1 -> doMinigame(deltaTime)
            2 -> {
                vX = .2f
                if (x > 82) {
                    y -= 0.09.toFloat()
                }
                if (x > 120) {
                    state = 4
                }
            }
            3 -> {
                isDone = true
                Render.setCameraX(0f)
                Render.setCameraY(0f)
                state = -1
            }
            4 -> {
                World.master.direction = false
                World.setMasterColor(1f, 1f, 1f)
                World.master.isActive = true
                if (World.master.current == 0f) {
                    state = 5
                    Main.getHarold().x = 45f
                    isDone = true
                    isTownOK = false
                    vX = 0f
                    World.master.setSecondDelay(1)
                }
            }
            5 -> {
                World.master.direction = true
                if (World.master.current == 1f) state = -1
            }
        }
        rotation += (vX * Math.PI).toFloat() * deltaTime
        x += vX * deltaTime
    }

    private fun doMinigame(deltaTime: Float) {
        haroldPuppet.update()
        Main.getHarold().setFollowCamera(false)
        falterTimedGradient.isActive = true
        falterTimedGradient.update()
        deathTimedGradient.isActive = isFaltering
        deathTimedGradient.update()

        // Falter calculations
        if (falterTimedGradient.current == falterTimedGradient.max && !isFaltering && x > 100) {
            if (Math.random() < 0.33) {
                //Do falter
                isFaltering = true
                haroldPuppet.frames = ResourceHandler.getHaroldLoader().falter
                haroldPuppet.fps = 40
                quickKey = (Math.random() * 26).toInt().toShort()
            }
            falterTimedGradient.current = 0f
        } else if (!isFaltering) {
            haroldPuppet.frames = ResourceHandler.getHaroldLoader().boulder
            haroldPuppet.fps = 12
        }


        val width =
            Graphics.toWorldWidth(LevelController.currentLevel.backgrounds[World.subLevel].texture.width.toFloat())
        val xMovement = 12f
        if (!isFaltering) {
            Main.getHarold().setMovement(false)
            if (Render.getCameraX() < width - 100)
                Render.setCameraX(Render.getCameraX() + xMovement * deltaTime)
            if (Render.getCameraY() > -Graphics.toWorldHeight(700f)) {
                if (Render.getCameraX() > 5) {
                    Render.setCameraY(Render.getCameraY() - .09f)
                    y -= 0.09f
                }
                vX = xMovement
            } else {
                vX = xMovement / 2f
            }
        } else {
            if (deathTimedGradient.current == deathTimedGradient.max) {
                Main.getHarold().health = 0
                Main.getHarold().setMovement(true)
            }
            if (Keyboard.keys.contains((65 + quickKey).toShort())) {
                isFaltering = false
                deathTimedGradient.current = 0f
            }
            vX = 0f
        }
        Main.getHarold().setHarold(haroldPuppet.currentFrame)
        if (!isFaltering)
            Main.getHarold().x = x
        else
            Main.getHarold().x = x + offsetFumble(haroldPuppet.currentFrameNum)
        Main.getHarold().y = y - 9
        if (Render.getCameraX() >= width - 100 && Render.getCameraY() <= -Graphics.toWorldHeight(700f)) {
            Main.getHarold().setMovement(true)
            Main.getHarold().x = 50f
            x = 50f
            y = 16f
            ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
            ResourceHandler.getHaroldLoader().setDirection(true)
            vX = 0f
            state = 3
        }
    }

    private fun offsetFumble(frame: Int): Float {
        var fumble = 0f
        when (frame) {
            0 -> fumble = 2f
            1 -> fumble = 8f
            2 -> fumble = 16f
        }
        return -Graphics.toWorldWidth(fumble)
    }

    override fun render() {
        if (!isTownOK) return
        if (state == 1) Graphics.setFollowCamera(false) else Graphics.setFollowCamera(false)
        Graphics.setRotation(rotation)
        Graphics.drawImageCentered(ResourceHandler.getMiscLoader().boulder, x, y)
        Graphics.setRotation(0f)
        Graphics.setFollowCamera(false)
        Graphics.setFont(Graphics.FontType.SMALL)
        if (state == 0 && Main.getHarold().hitbox.intersects(
                SmartRectangle(x - 4.5f, y - 4.5f, 9f, 9f)
            )
        ) Graphics.drawText("'Q' to guide downwards\\n'E' to push", x - 10, 30f, 30f, true)
        if (isFaltering) {
            quickTime()
        }
    }

    private fun quickTime() {
        Graphics.drawTextWithBox(codeToKey(quickKey).toString() + " to save!", x + 6, y + 12)
    }

    private fun codeToKey(`in`: Short): Char {
        val alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return alpha[`in`.toInt()]
    }

    override fun reset() {
        x = 73f
        y = 16f
        state = 0
        isDone = false
        isTownOK = true
        isFaltering = false
        falterTimedGradient.current = 0f
        deathTimedGradient.current = 0f
    }

    override fun doDamage(attacker: Entity, damage: Int) {}

    init {
        invincible = true
    }
}