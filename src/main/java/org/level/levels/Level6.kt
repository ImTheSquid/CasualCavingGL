package org.level.levels

import com.jogamp.newt.event.KeyEvent
import org.engine.Main
import org.entities.aggressive.Swolem
import org.entities.passive.Boulder
import org.entities.passive.Isolsi
import org.graphics.Graphics
import org.input.Keyboard
import org.level.Level
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.HeightMap
import org.world.HeightVal
import org.world.World

class Level6(backgrounds: Array<ImageResource>) : Level(backgrounds, backgrounds.size) {
    /* Create objects for special entities (ones that have different member functions) */
    private val boulder = Boulder()
    private val swolem = Swolem()

    /* Minigame variable */
    private var golemPassedLava = false
    override fun init() {
        World.newCheckpoint(World.Checkpoint.laranoFinish)
        ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
    }

    override val assets: Array<ImageResource>
        get() = ResourceHandler.create1DLoadable(
            arrayOf(
                ResourceHandler.getLevelLoader().level6,
                ResourceHandler.getLevelLoader().level6Town
            )
        )

    override fun update(subLevel: Int, deltaTime: Float) {
        checkHealthVals()
        setBounds(subLevel)
        if (subLevel != 3) {
            HeightMap.setHeights(HeightVal(0f, 7f, 100f, true))
            Graphics.scaleFactor = 1f
            World.gravity = 120f
        } else {
            HeightMap.setHeights(
                HeightVal(0f, 7f, 24f, true),
                HeightVal(36f, 7f, 46f, true),
                HeightVal(60f, 7f, 66f, true),
                HeightVal(80f, 7f, 100f, true)
            )
            Graphics.scaleFactor = .75f
            if (ResourceHandler.getHaroldLoader().state == HaroldLoader.GOLEM) {
                World.gravity = 200f
                if (Main.getHarold().x > 80) {
                    ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
                    golemPassedLava = true
                }
            } else {
                World.gravity = 160f
                if (Main.getHarold().x < 20 &&
                    Keyboard.keys.contains(KeyEvent.VK_E)
                ) ResourceHandler.getHaroldLoader().state = HaroldLoader.GOLEM
            }
        }
    }

    private fun setBounds(subLevel: Int) {
        rightLimit = 100f
        leftLimit = if (subLevel == 4) if (boulder.isDone) 40f else 3f else 0f
        when (subLevel) {
            0 -> rightBound = 99f
            1 -> {
                leftBound = 1f
                rightBound = 90f
            }
            2 -> {
                leftBound = 5f
                rightBound = 95f
            }
            3 -> {
                leftBound = 3f
                rightBound = 96f
            }
            4 -> {
                leftBound = if (boulder.isDone) 0f else -1f
                rightBound = 101f
                //Skip sun stone if player pushed boulder
                if (boulder.isDone && Main.getHarold().x + Main.getHarold().width >= 99) {
                    if (boulder.isTownOK) update4() else {
                        World.subLevel = 6
                        Main.getHarold().x = 5f
                    }
                }
            }
            5 -> {
                leftBound = -1f
                rightBound = 101f
                leftBound = -1f
                rightBound = 101f
                leftBound = 0f
                rightBound = 101f
            }
            6 -> {
                leftBound = -1f
                rightBound = 101f
                leftBound = 0f
                rightBound = 101f
            }
            7 -> {
                leftBound = 0f
                rightBound = 101f
            }
        }
    }

    //Handles transition to next sublevel (5)
    private fun update4() {
        World.master.isActive = true
        World.setMasterColor(1f, 1f, 1f)
        World.master.direction = false
        if (World.master.current == 0f) {
            World.master.setSecondDelay(.5.toLong())
            World.incrementSubLevel()
            if (!boulder.isTownOK) World.incrementSubLevel()
            World.master.direction = true
        }
    }

    override fun render(subLevel: Int) {
        Graphics.setIgnoreScale(true)
        Graphics.setFollowCamera(true)
        Graphics.setDrawColor(.22f, .22f, .22f, 1f)
        Graphics.fillRect(0f, 0f, 100f, 60f)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.setFollowCamera(false)
        when (subLevel) {
            1 -> Graphics.drawImage(ResourceHandler.getBossLoader().emerieForward, 41f, 25f)
            2 -> Graphics.drawImage(ResourceHandler.getBossLoader().emerieForward, 50f, 25f)
            3 -> Graphics.drawImage(ResourceHandler.getBossLoader().emerieForward, 61f, 25f)
            4 -> {
                Graphics.drawImage(ResourceHandler.getBossLoader().emerieForward, 30f, 25f)
                Graphics.drawImage(ResourceHandler.getBossLoader().emerieForward, 65f, 20f)
                Graphics.drawImage(ResourceHandler.getBossLoader().emerieForward, 165f, -34.75f)
            }
            6 -> Graphics.drawImage(ResourceHandler.getBossLoader().emerieForward, 47f, 17f)
        }
        /* Special code for boulder minigame */if (subLevel != 4) Graphics.drawImage(
            backgrounds[subLevel],
            0f,
            0f
        ) else {
            if (!boulder.isDone) {
                Graphics.drawImage(backgrounds[subLevel], 0f, -Graphics.toWorldHeight(700f))
                Graphics.drawImage(
                    ResourceHandler.getLevelLoader().level6Town[if (boulder.isTownOK) 1 else 0],
                    10f,
                    -Graphics.toWorldHeight(700f)
                )
            } else {
                Graphics.drawImage(backgrounds[subLevel], -Graphics.toWorldWidth(1280f), 0f)
                Graphics.drawImage(
                    ResourceHandler.getLevelLoader().level6Town[if (boulder.isTownOK) 1 else 0],
                    -Graphics.toWorldWidth(1280f) + 10, 0f
                )
            }
        }
        /* Special code for golem parkour */if (subLevel == 3 && ResourceHandler.getHaroldLoader().state != HaroldLoader.GOLEM) {
            if (!golemPassedLava) {
                Graphics.setFont(Graphics.FontType.SMALL)
                Graphics.drawImage(ResourceHandler.getGolemLoader().oldGolem[0], 16f, 19f)
                if (Main.getHarold().x < 20) Graphics.drawTextWithBox("E to carry", 16f, 25f)
            } else Graphics.drawImage(ResourceHandler.getGolemLoader().oldGolem[0], 77f, 19f)
        }
        /* Render Swolem boss bar */if (subLevel == 6) swolem.bossBar.render()
        Graphics.setIgnoreScale(false)
    }

    override fun renderForeground(subLevel: Int) {}
    override fun cleanup() {}
    override fun reset() {
        boulder.reset()
        swolem.reset()
        clearEntityRegister()
        entityRegister.add(boulder)
        entityRegister.add(Isolsi(false))
        entityRegister.add(swolem)
        golemPassedLava = false
    }
}