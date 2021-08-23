package org.level.levels

import com.jogamp.newt.event.KeyEvent
import org.engine.Main
import org.entities.SmartRectangle
import org.graphics.Graphics
import org.graphics.Render
import org.graphics.TimedGradient
import org.input.Keyboard
import org.level.Level
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.HeightMap
import org.world.HeightVal
import org.world.World

class Level2(backgrounds: Array<BiplanarSceneData>) : Level(backgrounds, backgrounds.size) {
    private val subBlink = TimedGradient(0f, 4f, 0f, 1f, 3)
    private val choice = TimedGradient(0f, 1f, 0f, 0.02f, 35)
    private val sprites = ResourceHandler.getLevelLoader().level2Sprites
    private val cart = SmartRectangle(33f, 21f, 23f, 19f)
    private val edge = SmartRectangle(70f, 0f, 30f, Render.unitsTall)
    private val stoneBox = SmartRectangle(64f, 23f, 13f, 14f)
    private var choiceMade = false
    private var choiceDir = true
    private var ePressed = false
    override fun init() {
        ResourceHandler.getHaroldLoader().disableAttackPause()
    }

    override val assets: Array<ImageResource>
        get() = ResourceHandler.create1DLoadable(arrayOf(ResourceHandler.create2DLoadable(ResourceHandler.getLevelLoader().level2.map { data ->
            arrayOf(
                data.foreground,
                data.background
            )
        }.toTypedArray()), ResourceHandler.getLevelLoader().level2Sprites))

    override fun update(subLevel: Int, deltaTime: Float) {
        if (subLevel != 7) {
            Main.getHarold().setVisible(true)
            Main.getHarold().setMovement(true)
        }
        updateBounds(subLevel)
        when (subLevel) {
            0 -> update0()
            1 -> update1()
            2 -> update2()
            3 -> update3()
            6 -> if (choiceMade) update6Post() else update6Pre()
            7 -> update7()
        }
    }

    private fun updateBounds(subLevel: Int) {
        when (subLevel) {
            2, 3 -> {
                rightLimit = 80f
                leftLimit = 0f
                if (ResourceHandler.getHaroldLoader().state != HaroldLoader.ROPE) ResourceHandler.getHaroldLoader().state =
                    HaroldLoader.LANTERN
            }
            4 -> {
                leftLimit = 16f
                rightLimit = Render.unitsWide + 1
                ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN //Set Harold to lantern light mode
            }
            6, 7 -> {
                rightLimit = Render.unitsWide
                leftLimit = 0f
                ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN //Set Harold to lantern light mode
            }
            else -> {
                rightLimit = Render.unitsWide + 1
                leftLimit = -1f
                ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN //Set Harold to lantern light mode
            }
        }
    }

    private fun update0() {
        HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
    }

    private fun update1() {
        HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
        if (subBlink.current == 0f) {
            subBlink.isActive = true
            World.master.current = 0f
        } else if (subBlink.current == 4f) subBlink.isActive = false
        updateDarkness()
        subBlink.update()
    }

    private fun update2() {
        HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
        if (cart.intersects(Main.getHarold().hitbox) && ResourceHandler.getHaroldLoader().state != HaroldLoader.ROPE && Keyboard.keys.contains(
                KeyEvent.VK_E
            )
        ) ResourceHandler.getHaroldLoader().state = HaroldLoader.ROPE
        if (edge.intersects(Main.getHarold().hitbox) && Keyboard.keys.contains(KeyEvent.VK_E)) {
            while (Keyboard.keys.contains(KeyEvent.VK_E)) {
            }
            ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
            World.subLevel = World.subLevel + 1
        }
    }

    private fun update3() {
        if (edge.intersects(Main.getHarold().hitbox) && Keyboard.keys.contains(KeyEvent.VK_E)) {
            World.subLevel = World.subLevel + 1
            Main.getHarold().x = 22f
        }
    }

    private fun update6Pre() {
        if (stoneBox.intersects(Main.getHarold().hitbox) && Keyboard.keys.contains(KeyEvent.VK_E)) {
            while (Keyboard.keys.contains(KeyEvent.VK_E)) {
            }
            World.subLevel = World.subLevel + 1
        }
    }

    private var fadeTime = false
    private fun update6Post() {
        World.master.direction = false
        World.master.isActive = true
        if (!fadeTime) {
            fadeTime = true
            World.master.setSecondDelay(4)
        }
        if (World.master.current == 0f) {
            World.setLevel(-1)
            fadeTime = false
        }
    }

    private fun update7() {
        Main.getHarold().setVisible(false)
        Main.getHarold().setMovement(false)
        choiceFade()
    }

    private fun choiceFade() {
        if (choiceDir) {
            if (Keyboard.keys.contains(KeyEvent.VK_E) || Keyboard.keys.contains(KeyEvent.VK_Q)) {
                choiceDir = false
                choice.direction = false
                ePressed = Keyboard.keys.contains(KeyEvent.VK_E)
            } else {
                choice.direction = true
                choice.isActive = true
            }
        } else if (choice.current == 0f) {
            choice.isActive = false
            choiceMade = true
            if (ePressed) {
                World.setLevelTransition(true)
            } else {
                World.subLevel = 6
            }
        }
        choice.update()
    }

    private fun updateDarkness() {
        when (subBlink.current.toInt()) {
            0, 1, 3 -> World.master.current = 0f
            4, 2 -> World.master.current = 1f
        }
    }

    override fun render(subLevel: Int) {
        Graphics.drawImage(backgrounds[subLevel], 0f, 0f)
        Graphics.setFont(Graphics.FontType.SMALL)
        when (subLevel) {
            1 -> render1()
            2 -> render2()
            3 -> render3()
            6 -> if (choiceMade) render6Post() else render6Pre()
            7 -> render7()
        }
    }

    private fun render1() {
        Graphics.drawImage(sprites[1], 0f, 0f)
        if (subBlink.current == 0f) Graphics.drawImage(sprites[0], 0f, 0f)
    }

    private fun render2() {
        Graphics.setFont(Graphics.FontType.SMALL)
        if (cart.intersects(Main.getHarold().hitbox) && ResourceHandler.getHaroldLoader().state != HaroldLoader.ROPE)
            Graphics.drawTextWithBox("Press E to pick up rope", 33f, 42f)
        else if (edge.intersects(Main.getHarold().hitbox) && ResourceHandler.getHaroldLoader().state == HaroldLoader.ROPE)
            Graphics.drawTextWithBox("Press E to place rope", 81f, 24f)
    }

    private fun render3() {
        if (edge.intersects(Main.getHarold().hitbox)) Graphics.drawTextWithBox("Press E to descend", 81f, 24f)
    }

    private fun render6Pre() {
        if (stoneBox.intersects(Main.getHarold().hitbox)) Graphics.drawTextWithBox("Press E to interact", 64f, 39f)
    }

    private fun render6Post() {
        Graphics.drawImage(sprites[2], 0f, 0f)
        Graphics.drawText("Good thing you called. I heard that stone is cursed!", 35f, 40f, 20f, true)
    }

    private fun render7() {
        Graphics.setDrawColor(1f, 1f, 1f, choice.current)
        Graphics.drawImage(sprites[3], 0f, 0f)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
    }

    override fun renderForeground(subLevel: Int) {
        if (foregrounds[subLevel] != null) Graphics.drawImage(foregrounds[subLevel]!!, 0f, 0f)
    }

    override fun cleanup() {
        Main.getHarold().setVisible(true)
        Main.getHarold().setMovement(true)
        choiceDir = true
    }

    override fun reset() {
        subBlink.current = 0f
        fadeTime = false
        choiceMade = false
        choiceDir = true
        ePressed = false
    }
}