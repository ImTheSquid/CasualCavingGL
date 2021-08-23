package org.entities.passive

import com.jogamp.newt.event.KeyEvent
import org.engine.Main
import org.entities.Autonomous
import org.graphics.Graphics
import org.graphics.TimedGradient
import org.input.Keyboard
import org.loader.ResourceHandler
import org.world.World.gravity
import org.world.World.incrementSubLevel
import org.world.World.master
import org.world.World.setLevelTransition
import org.world.World.setMasterColor

class Isolsi(inPostLaranoScene: Boolean) : Autonomous(3, 64f, 60f) {
    private var convoState = 0
    private var crossfadeActive = false
    private val inPostLaranoScene: Boolean
    private val crossfade = TimedGradient(0f, 1f, 1f, 0.01f, 60)
    private val isolsi = ResourceHandler.getGolemLoader().isolsi
    private val postLaranoConvo = arrayOf(
        "That orange golem certainly caused you a bit of trouble didn't he.",
        "Does he not know the importance of your mission?!",
        "The nerve of those lesser golems!",
        "If anyone should try to stand in our way like that again...",
        "don't hold back."
    )
    private val postBoulderConvo = arrayOf(
        "What are you doing?",
        "You could have died doing that!",
        "And for what?",
        "The precious model town is safe...",
        "It's not real!",
        "It's not worth our time!",
        "Now stop fooling around...",
        "...and TAKE THINGS SERIOUSLY!"
    )

    override fun update(deltaTime: Float) {
        if (inPostLaranoScene)
            larano()
        else
            boulder()
        y += vY
        vY -= if (vY - gravity >= 0) gravity else vY
        crossfade.isActive = crossfadeActive
        crossfade.update()
    }

    private fun larano() {
        when (state) {
            -1 -> if (Main.getHarold().x > 15) {
                Main.getHarold().setLockControls(true)
                state++
            }
            0 -> {
                if (y > 7) vY = -0.15f else {
                    crossfadeActive = true
                }
                if (crossfade.isActive && crossfade.current == 0f) {
                    crossfade.current = 1f
                    state++
                }
            }
            1 -> if (crossfade.isActive && crossfade.current == 0f) {
                crossfadeActive = false
                crossfade.current = 1f
                state++
            }
            else -> {
                if (Keyboard.keys.contains(KeyEvent.VK_SPACE) && convoState + 1 < postLaranoConvo.size) {
                    convoState++
                } else if (Keyboard.keys.contains(KeyEvent.VK_SPACE)) {
                    setLevelTransition(true)
                }
                while (Keyboard.keys.contains(KeyEvent.VK_SPACE)) {
                }
            }
        }
    }

    private fun boulder() {
        Main.getHarold().x = 10f
        ResourceHandler.getHaroldLoader().setDirection(true)
        if (state == 2 || convoState == 0) {
            Main.getHarold().setLockControls(true)
            if (Keyboard.keys.contains(KeyEvent.VK_SPACE) && convoState + 1 < postBoulderConvo.size) {
                convoState++
            } else if (Keyboard.keys.contains(KeyEvent.VK_SPACE)) {
                state++
            }
            while (Keyboard.keys.contains(KeyEvent.VK_SPACE)) {
            }
        } else if (state == 3) {
            setMasterColor(1f, 1f, 1f)
            master.direction = false
            if (master.current == 0f) {
                incrementSubLevel()
                master.direction = true
                Main.getHarold().setLockControls(false)
            }
        }
    }

    override fun render() {
        if (state == -1) return
        if (state < 2) {
            Graphics.setDrawColor(1f, 1f, 1f, crossfade.current)
            Graphics.drawImage(
                isolsi[state], x - Graphics.toWorldWidth(calcXOffset(state)),
                y + Graphics.toWorldHeight(calcYOffset(state))
            )
            if (crossfadeActive && state + 1 < isolsi.size) {
                Graphics.setDrawColor(1f, 1f, 1f, 1 - crossfade.current)
                Graphics.drawImage(
                    isolsi[state + 1],
                    x - Graphics.toWorldWidth(calcXOffset(state + 1)),
                    y + Graphics.toWorldHeight(calcYOffset(state + 1))
                )
            }
        } else {
            Graphics.drawImage(
                isolsi[calcConvoSprite()],
                x - Graphics.toWorldWidth(calcXOffset(calcConvoSprite())),
                y + Graphics.toWorldHeight(calcYOffset(calcConvoSprite()))
            )
        }
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.setFont(Graphics.FontType.SMALL)
        if (convoState > -1) {
            if (inPostLaranoScene) {
                Graphics.drawText(postLaranoConvo[convoState], 45f, 45f, 20f, true)
            } else {
                Graphics.drawText(postBoulderConvo[convoState], 45f, 45f, 20f, true)
            }
        }
    }

    private fun calcXOffset(frame: Int): Float {
        return when (frame) {
            0 -> (-26).toFloat()
            1 -> (91 - 37).toFloat()
            2, LOOK_DOWN -> (54 - 37).toFloat()
            GESTURE -> (106 - 37).toFloat()
            SLIGHT_ANGER -> (139 - 37).toFloat()
            CLENCH_FIST -> (92 - 37).toFloat()
            POINTING -> (129 - 37).toFloat()
            ANGRY -> (113 - 37).toFloat()
            QUESTION -> (106 - 37).toFloat()
            else -> 0f
        }
    }

    private fun calcYOffset(frame: Int): Float {
        return when (frame) {
            0 -> 387f
            1 -> -35.8f
            else -> 0f
        }
    }

    private fun calcConvoSprite(): Int {
        return if (inPostLaranoScene) when (convoState) {
            4 -> CLENCH_FIST
            1 -> GESTURE
            2 -> SLIGHT_ANGER
            3, 0 -> LOOK_DOWN
            else -> LOOK_DOWN
        } else when (convoState) {
            0 -> QUESTION
            1 -> SLIGHT_ANGER
            3 -> GESTURE
            4, 5, 2 -> ANGRY
            6, 7 -> POINTING
            else -> LOOK_DOWN
        }
    }

    override fun reset() {
        state = if (inPostLaranoScene) -1 else 2
        convoState = 0
        crossfadeActive = false
        crossfade.isActive = false
        crossfade.direction = false
        crossfade.current = 1f
    }

    companion object {
        private const val LOOK_DOWN = 3
        private const val GESTURE = 4
        private const val SLIGHT_ANGER = 5
        private const val CLENCH_FIST = 6
        private const val POINTING = 7
        private const val ANGRY = 8
        private const val QUESTION = 9
    }

    init {
        if (!inPostLaranoScene) {
            y = 7f
            subLevel = 5
            state = 2
        }
        this.inPostLaranoScene = inPostLaranoScene
        reset()
    }
}