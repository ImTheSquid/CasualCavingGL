package org.entities.passive

import com.jogamp.newt.event.KeyEvent
import org.engine.Main
import org.entities.Entity
import org.entities.SmartRectangle
import org.graphics.Animator
import org.graphics.Graphics
import org.graphics.Render
import org.input.Keyboard
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.World

class Crowd : Entity() {
    private val crowdAnimator = Animator(ResourceHandler.getCrowdLoader().crowdWalk, 12)
    private var crowd: ImageResource? = null
    private var start = false
    private var wood = true
    private var chainsaw = false
    private var cartIntersect = false
    private var fadeDelaySet = false
    private var cartWidth = 0f
    private var cartHeight = 0f
    private val cart = SmartRectangle(x + 24, y, cartWidth, cartHeight)
    override fun update(deltaTime: Float) {
        crowd = if (vX == 0f) {
            ResourceHandler.getCrowdLoader().crowd
        } else {
            crowdAnimator.currentFrame
        }
        cartIntersect = cart.intersects(Main.getHarold().hitbox)
        when (World.subLevel) {
            1 -> {
                if (Main.getHarold().x > Render.unitsWide / 2) start = true
                if (x < 5 && start) vX = 20f
            }
            2 -> {
                if (x < 5 && subLevel == 2) vX = 20f
                if (cartIntersect && Keyboard.keys.contains(KeyEvent.VK_E) && !chainsaw) {
                    ResourceHandler.getHaroldLoader().state = HaroldLoader.CHAINSAW
                    chainsaw = true
                }
            }
            3 -> if (x < 5 && subLevel == 3) vX = 20f
            4 -> if (x < 5 && subLevel == 4) vX = 20f
            5 -> {
                if (x < 5 && subLevel == 5) vX = 20f
                if (vX == 0f && !fadeDelaySet) {
                    fadeDelaySet = true
                    World.master.setSecondDelay(4)
                    World.master.direction = false
                    World.master.isActive = true
                }
            }
        }
        x += vX * deltaTime
        if (vX - World.gravity * deltaTime < 0) vX = 0f else vX -= World.gravity * deltaTime
        crowdAnimator.update()
        cart.updateBounds(x + 24, y, cartWidth, cartHeight)
    }

    override fun render() {
        cartWidth = Graphics.toWorldWidth(ResourceHandler.getCrowdLoader().cart.texture.width.toFloat())
        cartHeight = Graphics.toWorldHeight(ResourceHandler.getCrowdLoader().cart.texture.height.toFloat())
        if (World.subLevel != subLevel) return
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        if (crowd != null && ResourceHandler.getCrowdLoader().cart != null) {
            Graphics.drawImage(crowd!!, x, y)
            Graphics.drawImage(ResourceHandler.getCrowdLoader().cart, x + 24, y)
        }
        if (subLevel == 2 && vX == 0f) {
            Graphics.setDrawColor(1f, 1f, 1f, 1f)
            Graphics.setFont(Graphics.FontType.SMALL)
            if (wood) Graphics.drawText(
                "Hey, we won't be able to get the cart over that log. You should use some tools.",
                8f,
                35f,
                20f,
                true
            )
            if (cartIntersect && !chainsaw) Graphics.drawTextWithBox("Press E to pick up chainsaw", 32f, 29f)
        }
        if (subLevel == 5 && vX == 0f) {
            Graphics.setDrawColor(1f, 1f, 1f, 1f)
            Graphics.setFont(Graphics.FontType.SMALL)
            Graphics.drawText(
                "This looks like a good place to set up camp. Let's put our stuff down.",
                8f,
                35f,
                20f,
                true
            )
        }
    }

    override fun reset() {
        subLevel = 1
        level = 1
        start = false
        wood = true
        chainsaw = false
        cartIntersect = false
        fadeDelaySet = false
        x = -55f
    }

    fun updateSublevel(newSub: Int) {
        if (newSub > subLevel) {
            subLevel = newSub
            x = -55f
        }
    }

    fun setWood(wood: Boolean) {
        this.wood = wood
    }

    init {
        subLevel = 1
        x = -55f
    }
}