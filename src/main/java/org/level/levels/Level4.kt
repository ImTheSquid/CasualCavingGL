package org.level.levels

import org.engine.Main
import org.entities.aggressive.RedMajor
import org.entities.aggressive.ShortGolem
import org.entities.aggressive.TallGolem
import org.entities.passive.LifeCrystal
import org.graphics.Graphics.drawImage
import org.graphics.Render
import org.level.Level
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.HeightMap
import org.world.HeightVal
import org.world.World

class Level4(backgrounds: Array<BiplanarSceneData>) : Level(backgrounds, backgrounds.size) {
    private val redMajor = RedMajor()
    override fun init() {
        reset()
        ResourceHandler.getHaroldLoader().disableAttackPause()
        World.clearEntites()
        World.addEntities(super.entityRegisterArray)
    }

    override val assets: Array<ImageResource>
        get() = ResourceHandler.create1DLoadable(
            arrayOf(
                ResourceHandler.getGolemLoader().purpleGolemLoadable,
                ResourceHandler.getGolemLoader().redGolemLoadable,
                ResourceHandler.getGolemLoader().tallBlueGolemLoadable
            )
        )

    override fun update(subLevel: Int, deltaTime: Float) {
        checkHealthVals()
        ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
        if (subLevel != 4) {
            rightBound = Render.unitsWide
            rightLimit = Render.unitsWide + 1
        }
        if (subLevel != 5) leftLimit = -1f
        when (subLevel) {
            0 -> HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
            1 -> HeightMap.setHeights(
                HeightVal(0f, 7f, 30f, true),
                HeightVal(34f, 29f, 72f, false),
                HeightVal(74f, 7f, Render.unitsWide, true)
            )
            2 -> HeightMap.setHeights(
                HeightVal(0f, 7f, 78f, true),
                HeightVal(20f, 30f, 58f, false),
                HeightVal(78f, 12f, 81f, true),
                HeightVal(81f, 17f, 86f, true),
                HeightVal(86f, 25f, 89f, true),
                HeightVal(89f, 30f, 94f, true),
                HeightVal(94f, 35f, 98f, true),
                HeightVal(98f, 41f, Render.unitsWide, true)
            )
            3 -> HeightMap.setHeights(
                HeightVal(0f, 20f, 24f, true),
                HeightVal(36f, 30f, 66f, false),
                HeightVal(77f, 20f, Render.unitsWide, true)
            )
            4 -> {
                HeightMap.setHeights(
                    HeightVal(0f, 32f, 26f, true),
                    HeightVal(26f, 8f, 76f, true),
                    HeightVal(76f, 14f, 84f, true)
                )
                rightBound = 83f
                rightLimit = 84f
            }
            5 -> {
                HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
                leftLimit = 0f
                if (Main.getHarold().x > 10) redMajor.setStartFight(true)
                rightLimit = if (redMajor.health > 0) Render.unitsWide else Render.unitsWide + 1
            }
            6 -> {
                HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true))
                var count = 0
                for (e in entityRegister) {
                    if (e.displayName == "Tall Blue Golem" && e.subLevel == 6) count++
                }
                if (count == 0 && Main.getHarold().x + Main.getHarold().width == Render.unitsWide) World.setLevelTransition(
                    true
                )
            }
        }
    }

    override fun render(subLevel: Int) {
        drawImage(backgrounds[subLevel], 0f, 0f)
    }

    override fun renderForeground(subLevel: Int) {
        foregrounds[subLevel]?.let { foreground ->
            drawImage(foreground, 0f, 0f)
        }
        if (subLevel == 5) redMajor.bossBar.render()
    }

    override fun cleanup() {}
    override fun reset() {
        redMajor.reset()
        clearEntityRegister()
        entityRegister.add(LifeCrystal(1, 84f, 8f))
        entityRegister.add(LifeCrystal(3, 38f, 32f))
        entityRegister.add(LifeCrystal(6, 77f, 7f))
        entityRegister.add(ShortGolem(ShortGolem.GolemColor.BLUE, 0, 25f, 7f))
        entityRegister.add(ShortGolem(ShortGolem.GolemColor.RED, 0, 50f, 7f))
        entityRegister.add(ShortGolem(ShortGolem.GolemColor.GREEN, 1, 50f, 32f))
        entityRegister.add(ShortGolem(ShortGolem.GolemColor.PURPLE, 2, 24f, 31f))
        entityRegister.add(ShortGolem(ShortGolem.GolemColor.GREEN, 3, 52f, 30f))
        entityRegister.add(TallGolem(TallGolem.BLUE, 4, 44f, 10f))
        entityRegister.add(TallGolem(TallGolem.BLUE, 6, 18f, 46f))
        entityRegister.add(TallGolem(TallGolem.BLUE, 6, 60f, 46f))
        entityRegister.add(redMajor)
    }
}