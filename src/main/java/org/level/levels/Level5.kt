package org.level.levels

import org.engine.Main
import org.entities.HitDetector
import org.entities.aggressive.CineLarano
import org.entities.aggressive.KeyMasterL
import org.entities.aggressive.Larano
import org.entities.aggressive.LaranoStalactite
import org.entities.passive.Isolsi
import org.entities.passive.LifeCrystal
import org.graphics.Graphics
import org.graphics.Render
import org.level.Level
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.HeightMap
import org.world.HeightVal
import org.world.World

class Level5(backgrounds: Array<ImageResource>) : Level(backgrounds, 4) {
    private val cineLarano = CineLarano()
    private val larano = Larano()
    private val keyMaster = KeyMasterL(larano)
    private val stalactiteLeft = HitDetector(
        2,
        0f,
        50f,
        12f,
        Render.unitsTall - 50,
        { entityRegister.add(LaranoStalactite(0f, 50f, true)) },
        "Harold"
    )
    private val stalactiteRight = HitDetector(
        2,
        87f,
        50f,
        13f,
        Render.unitsTall - 50,
        { entityRegister.add(LaranoStalactite(95f, 50f, false)) },
        "Harold"
    )

    override fun init() {}

    override val assets: Array<ImageResource>
        get() {
            val r = ResourceHandler.getBossLoader().laranoReadying
            val sRight = ResourceHandler.getBossLoader().getLaranoShimmer(true)
            val sLeft = ResourceHandler.getBossLoader().getLaranoShimmer(false)
            return ResourceHandler.create1DLoadable(arrayOf(r, sRight, sLeft))
        }

    override fun update(subLevel: Int, deltaTime: Float) {
        checkHealthVals()
        World.newCheckpoint(World.Checkpoint.larano)
        ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
        if (subLevel < 2) {
            HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
            Graphics.scaleFactor = 1f
            leftLimit = -1f
        } else if (subLevel == 2) {
            HeightMap.setHeights(
                HeightVal(0f, 5f, Render.unitsWide, true),
                HeightVal(15f, 21f, 34f, false),
                HeightVal(64f, 21f, 84f, false),
                HeightVal(35f, 30f, 63f, false),
                HeightVal(15f, 41f, 35f, false),
                HeightVal(64f, 41f, 86f, false)
            )
            Graphics.scaleFactor = 0.75f
            leftLimit = 0f
            rightLimit = 100f
        } else {
            leftLimit = 0f
            rightLimit = 101f
            Graphics.scaleFactor = 1f
            HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
        }
    }

    override fun render(subLevel: Int) {
        Graphics.setIgnoreScale(true)
        Graphics.drawImage(backgrounds[subLevel], 0f, 0f)
        Graphics.setIgnoreScale(false)
    }

    override fun renderForeground(subLevel: Int) {
        if (subLevel == 2) {
            larano.bossBar.render()
            Graphics.setIgnoreScale(true)
            Graphics.drawImage(ResourceHandler.getMiscLoader().laranoStalactite, 0f, 50f)
            Graphics.drawImage(ResourceHandler.getMiscLoader().laranoStalactite, 95f, 50f)
            Graphics.setIgnoreScale(false)
        }
    }

    override fun cleanup() {
        Main.getHarold().setLockControls(false)
    }

    override fun reset() {
        leftLimit = -1f
        rightLimit = 101f
        cineLarano.reset()
        larano.reset()
        keyMaster.reset()
        clearEntityRegister()
        entityRegister.add(LifeCrystal(0, 65f, 7f))
        entityRegister.add(LifeCrystal(0, 85f, 7f))
        entityRegister.add(stalactiteLeft)
        entityRegister.add(stalactiteRight)
        entityRegister.add(cineLarano)
        entityRegister.add(larano)
        entityRegister.add(keyMaster)
        entityRegister.add(Isolsi(true))
    }
}