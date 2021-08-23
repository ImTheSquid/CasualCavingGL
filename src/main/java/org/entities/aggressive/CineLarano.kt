package org.entities.aggressive

import com.jogamp.newt.event.KeyEvent
import org.engine.Main
import org.entities.Autonomous
import org.graphics.Animator
import org.graphics.Graphics
import org.graphics.Graphics.drawImage
import org.graphics.Graphics.drawText
import org.graphics.Graphics.setDrawColor
import org.graphics.Graphics.setFont
import org.graphics.Graphics.toWorldWidth
import org.graphics.Render
import org.input.Keyboard
import org.loader.ImageResource
import org.loader.ResourceHandler

class CineLarano : Autonomous(1, 66f, 11f) {
    private val cine = Animator(ResourceHandler.getBossLoader().getCineWalk(false), 5)
    private var sprite: ImageResource?
    private var speechDone = false
    private var speechState = 0
    override fun update(deltaTime: Float) {
        if (Main.getHarold().x < 20 && speechState == 0) return
        if (Keyboard.keys.contains(KeyEvent.VK_SPACE) && state == 1 && Main.getHarold().areControlsLocked) {
            while (Keyboard.keys.contains(KeyEvent.VK_SPACE)) {
            }
            speechState++
        }
        if (!speechDone && speechState != 5) {
            Main.getHarold().setHarold(ResourceHandler.getHaroldLoader().harold)
            Main.getHarold().setLockControls(true)
        } else Main.getHarold().setLockControls(false)
        if (speechState == 5 && Main.getHarold().x > 30) speechState = 6
        when (state) {
            0 -> {
                vX = -0.1f
                x += vX
                sprite = cine.currentFrame
                if (x <= 50) state = 1
                cine.update()
            }
            1 -> doSpeech()
            2 -> {
                vX = 0.1f
                x += vX
                sprite = cine.currentFrame
                if (x > Render.unitsWide) {
                    state = 3
                }
                cine.update()
            }
            3 -> speechDone = true
        }
    }

    private fun doSpeech() {
        val ACCUSING = 0
        val ASKING = 1
        val DESCRIBING = 2
        val GESTURING = 3
        val INTRIGUED = 4
        val REALIZING = 5
        val STERN = 6
        when (speechState) {
            0 -> sprite = ResourceHandler.getBossLoader().cineExpression[INTRIGUED]
            1 -> sprite = ResourceHandler.getBossLoader().cineExpression[GESTURING]
            2 -> sprite = ResourceHandler.getBossLoader().cineExpression[ASKING]
            3, 4 -> sprite = ResourceHandler.getBossLoader().cineExpression[DESCRIBING]
            6 -> sprite = ResourceHandler.getBossLoader().cineExpression[REALIZING]
            7, 8 -> sprite = ResourceHandler.getBossLoader().cineExpression[ACCUSING]
            9 -> sprite = ResourceHandler.getBossLoader().cineExpression[STERN]
            10 -> {
                cine.frames = ResourceHandler.getBossLoader().getCineWalk(true)
                state = 2
            }
        }
    }

    override fun render() {
        if (sprite == null) return
        setDrawColor(1f, 1f, 1f, 1f)
        doSpeechDisplay()
        drawImage(sprite!!, x + toWorldWidth(offset), y)
    }

    private val offset: Float
        private get() = if (state != 1) 0F else when (speechState) {
            0, 6, 9, 10 -> 0
            1, 2, 3, 4, 5 -> (-41).toFloat()
            else -> (-60).toFloat()
        } as Float

    private fun doSpeechDisplay() {
        if (state != 1) return
        setFont(Graphics.FontType.SMALL)
        when (speechState) {
            0 -> drawText("Hmmm, I was told a sun golem was headed this way... [SPACE]", 51f, 37f, 18f, true)
            1 -> drawText("But you're no sun golem, you're just a human. [SPACE]", 51f, 37f, 18f, true)
            2 -> drawText("You wouldn't have happened to have seen any of them would you? [SPACE]", 51f, 37f, 18f, true)
            3 -> drawText(
                "They look like us regular golems, but they're a bit bigger, their flesh is a golden yellow color, [SPACE]",
                51f,
                42f,
                18f,
                true
            )
            4 -> drawText("and each of them had a large gem somewhere on their body. [SPACE]", 51f, 37f, 18f, true)
            6 -> drawText("Wait a second! [SPACE]", 51f, 35f, 18f, true)
            7 -> drawText("That glint in your eye; the evil aura radiating off of you... [SPACE]", 51f, 37f, 18f, true)
            8 -> drawText("Human, you have the sun golems' power don't you... [SPACE]", 51f, 37f, 18f, true)
            9 -> drawText(
                "Well then, we'll do battle in the next chamber. Follow if you dare... [SPACE]",
                51f,
                37f,
                18f,
                true
            )
        }
    }

    override fun reset() {
        cine.frames = ResourceHandler.getBossLoader().getCineWalk(false)
        state = 0
        x = 66f
        y = 11f
        speechState = 0
        cine.setCurrentFrame(0)
        sprite = cine.currentFrame
        speechDone = false
    }

    init {
        sprite = cine.currentFrame
        invincible = true
    }
}