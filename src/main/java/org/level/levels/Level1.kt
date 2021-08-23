package org.level.levels

import com.jogamp.newt.event.KeyEvent
import org.engine.Main
import org.entities.SmartRectangle
import org.entities.passive.Crowd
import org.graphics.Graphics
import org.graphics.Render
import org.input.Keyboard
import org.level.Level
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.HeightMap
import org.world.HeightVal
import org.world.World

class Level1(backgrounds: Array<ImageResource>) : Level(backgrounds, backgrounds.size) {
    private val sprites = ResourceHandler.getLevelLoader().level1Sprites
    private var bridge = false
    private var wood = true
    private val crowd = Crowd()
    private val log = SmartRectangle(68f, 7f, 10f, 11f)
    private val river = SmartRectangle(40f, 0f, 60f, Render.unitsTall)
    override fun init() {}

    override val assets: Array<ImageResource>
        get() = arrayOf()

    override fun update(subLevel: Int, deltaTime: Float) {
        leftLimit = -1f
        if (!World.entities.contains(crowd) && crowd.subLevel < 6) World.addEntity(crowd) else if (crowd.subLevel == 6) World.removeEntity(
            crowd
        )
        leftBound = if (subLevel == 0) {
            65f
        } else {
            0f
        }
        if (subLevel != 3 && subLevel < 5) {
            rightLimit = Render.unitsWide + 1
            crowd.updateSublevel(World.subLevel)
        } else if (subLevel >= 5) {
            rightLimit = Render.unitsWide
            leftLimit = 0f
        }
        when (subLevel) {
            0 -> update0()
            1 -> update1()
            2 -> update2()
            3 -> update3()
            4 -> update4()
            5 -> update5()
            6 -> update6()
        }
    }

    private fun update0() {
        HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
    }

    private fun update1() {
        HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
    }

    private fun update2() {
        crowd.setWood(wood)
        if (wood)
            HeightMap.setHeights(
                HeightVal(0f, 7f, 70f, true),
                HeightVal(70f, 13f, 76f, true),
                HeightVal(76f, 7f, Render.unitsWide, true)
            )
        else
            HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))

        if (ResourceHandler.getHaroldLoader().state == HaroldLoader.CHAINSAW && log.intersects(Main.getHarold().hitbox) &&
            Keyboard.keys.contains(KeyEvent.VK_E)
        ) {
            wood = false
            ResourceHandler.getHaroldLoader().state = HaroldLoader.WOOD
        }
    }

    private fun update3() {
        HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
        if (!wood && river.intersects(Main.getHarold().hitbox) && Keyboard.keys.contains(KeyEvent.VK_E)) {
            bridge = true
            ResourceHandler.getHaroldLoader().state = HaroldLoader.NORMAL
        }
        if (!bridge) {
            rightLimit = 45f
        } else {
            rightLimit = Render.unitsWide + 1
            crowd.updateSublevel(World.subLevel)
        }
    }

    private fun update4() {
        HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
        crowd.updateSublevel(World.subLevel)
    }

    private fun update5() {
        HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
        crowd.updateSublevel(World.subLevel)
        if (World.master.current == 0f) {
            World.subLevel = World.subLevel + 1
            Main.getHarold().x = 50f
        }
    }

    private fun update6() {
        HeightMap.setHeights(HeightVal(0f, 14f, 24f, true), HeightVal(24f, 7f, Render.unitsWide, true))
        if (!World.master.direction && Main.getHarold().x <= 80) {
            World.master.direction = true
            World.master.setSecondDelay(1)
        }
        if (Main.getHarold().x > 80) {
            World.setLevelTransition(true)
            Main.getHarold().setMovement(false)
        }
    }

    override fun render(subLevel: Int) {
        Graphics.drawImage(backgrounds[subLevel], 0f, 0f)
        when (subLevel) {
            0 -> render0()
            1 -> render1()
            2 -> render2()
            3 -> render3()
            6 -> render6()
        }
    }

    override fun renderForeground(subLevel: Int) {
        if (World.subLevel == 6) {
            Graphics.drawImage(sprites[7], 2f, 2f)
            Graphics.drawImage(sprites[8], 70f, 2f)
        }
    }

    override fun cleanup() {
        World.removeEntity(crowd)
    }

    private fun render0() {
        Graphics.drawImage(sprites[0], 6f, 7f)
        Graphics.drawImage(sprites[1], 40f, 7f)
        Graphics.setFont(Graphics.FontType.SMALL)
        Graphics.drawText(
            "Alright guys, you know what to do; we're looking for a precious yellow gem located in a nearby cave. Go!",
            20f,
            48f,
            22f,
            true
        )
    }

    private fun render1() {}
    private fun render2() {
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        if (wood) {
            Graphics.drawImage(sprites[2], 70f, 7f)
            Graphics.setFont(Graphics.FontType.SMALL)
            if (log.intersects(Main.getHarold().hitbox) && ResourceHandler.getHaroldLoader().state == HaroldLoader.CHAINSAW)
                Graphics.drawTextWithBox("Press E to cut", 70f, 20f)
        }
    }

    private fun render3() {
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.setFont(Graphics.FontType.SMALL)
        if (!bridge) {
            if (ResourceHandler.getHaroldLoader().state == HaroldLoader.WOOD && river.intersects(Main.getHarold().hitbox))
                Graphics.drawTextWithBox("Press E to place wood", 47f, 20f)
        } else {
            Graphics.drawImage(sprites[3], 41f, 5f)
        }
    }

    private fun render6() {
        Graphics.drawImage(sprites[5], 28f, 17f)
        Graphics.drawImage(sprites[6], 50f, -3f)
        Graphics.drawImage(sprites[4], 80f, 40f)
    }

    override fun reset() {
        crowd.reset()
        bridge = false
        wood = true
    }
}