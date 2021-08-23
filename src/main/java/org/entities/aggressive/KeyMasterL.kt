package org.entities.aggressive

import com.jogamp.newt.event.KeyEvent
import org.engine.Main
import org.entities.Autonomous
import org.graphics.Animator
import org.graphics.Graphics
import org.input.Keyboard
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.world.World

//Key Master (Emerie) class for the end of the Larano boss fight
class KeyMasterL(private val larano: Larano) : Autonomous(2, 101f, 4f) {
    private val WALK_IN = 0
    private val TALK = 1
    private val WALK_OUT = 2
    private val INTO_SUN_STONE = 3
    private var convoProgress = 0
    private val animator = Animator(ResourceHandler.getBossLoader().getEmerieWalk(false), 8)
    private var emerie: ImageResource? = null
    private val conversation = arrayOf(
        "Larano! Stop this fighting!",
        "Keymaster? What are you doing here!?",
        "Oh please, look at yourself. You are far too injured to keep this up.",
        "But this human is possessed by the Sun Golems. He must be stopped!",
        "Get yourself to the medical center, let me take it from here."
    )
    private var keymasterTalking = true
    override fun update(deltaTime: Float) {
        if (larano.health == 1 && larano.getvX() == 0f && state == -1) state++
        when (state) {
            WALK_IN -> {
                vX = -0.15f
                animator.update()
                emerie = animator.currentFrame
                larano.animator.setActive(false)
                larano.animator.setCurrentFrame(0)
                if (x < 80) {
                    state++
                }
            }
            TALK -> {
                vX = 0f
                if (Keyboard.keys.contains(KeyEvent.VK_SPACE)) {
                    if (convoProgress < 4) convoProgress++ else {
                        larano.increaseState()
                        state++
                    }
                    keymasterTalking = !keymasterTalking
                    larano.animator.setCurrentFrame(convoProgress)
                    larano.updateSprite()
                    while (Keyboard.keys.contains(KeyEvent.VK_SPACE)) {
                    }
                }
                emerie = if (convoProgress == 2) ResourceHandler.getBossLoader()
                    .getEmerieGesture(true) else ResourceHandler.getBossLoader().getEmerieGesture(false)
            }
            WALK_OUT -> {
                vX = 0.15f
                animator.frames = ResourceHandler.getBossLoader().getEmerieWalk(true)
                animator.update()
                emerie = animator.currentFrame
                if (x > 101 && larano.x > 101) {
                    state++
                    World.master.direction = false
                    World.master.isActive = true
                }
            }
            INTO_SUN_STONE -> {
                World.setMasterColor(1f, 1f, 1f)
                if (World.master.current == 0f && !World.master.direction) {
                    World.subLevel = World.subLevel + 1
                    World.master.isActive = true
                    World.master.direction = true
                    Main.getHarold().x = 5f
                    Main.getHarold().y = 7f
                }
            }
        }
        x += vX
    }

    override fun render() {
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        if (emerie != null) Graphics.drawImage(emerie!!, x + calcOffset(), y)
        when (state) {
            WALK_IN -> {
            }
            TALK -> {
                Graphics.setFont(Graphics.FontType.SMALL)
                if (keymasterTalking)
                    Graphics.drawText(conversation[convoProgress], 75f, 30f, 25f, true)
                else
                    Graphics.drawText(conversation[convoProgress], 55f, 25f, 25f, true)
            }
            WALK_OUT -> {
            }
        }
    }

    private fun calcOffset(): Float {
        return if (state == TALK) {
            if (convoProgress == 2)
                -Graphics.toWorldWidth((82 - 16).toFloat())
            else
                -Graphics.toWorldWidth(16f)
        } else
            0f
    }

    override fun reset() {
        state = -1
        convoProgress = 0
        keymasterTalking = true
        vX = 0f
        x = 101f
        y = 4f
        animator.frames = ResourceHandler.getBossLoader().getEmerieWalk(false)
    }

    init {
        reset()
    }
}