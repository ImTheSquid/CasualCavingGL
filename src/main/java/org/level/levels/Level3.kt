package org.level.levels

import org.engine.Main
import org.entities.aggressive.ShortGolem
import org.entities.passive.RockParticle
import org.graphics.Graphics
import org.graphics.Render
import org.graphics.TimedGradient
import org.level.Level
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.HeightMap
import org.world.HeightVal
import org.world.World

class Level3(backgrounds: Array<ImageResource>) : Level(backgrounds, backgrounds.size) {
    private val isolsi = TimedGradient(0f, 1f, 0f, 0.02f, 35)
    private val hematus = TimedGradient(0f, 1f, 0f, 0.02f, 35)
    private val igneox = TimedGradient(0f, 1f, 0f, 0.02f, 35)
    private val sprites = ResourceHandler.getLevelLoader().level3Sprites
    private var fadeActive = false
    private var switchFade = 0
    override fun init() {
        ResourceHandler.getHaroldLoader().disableAttackPause()
        World.clearEntites()
        World.addEntities(super.entityRegisterArray)
    }

    override val assets: Array<ImageResource>
        get() = ResourceHandler.create1DLoadable(
            arrayOf(
                ResourceHandler.getGolemLoader().bLueGolemLoadable,
                ResourceHandler.getGolemLoader().greenGolemLoadable
            )
        )

    override fun update(subLevel: Int, deltaTime: Float) {
        checkHealthVals()
        if (subLevel != 7) HeightMap.setHeights(HeightVal(0f, 7f, Render.unitsWide, true)) //Set heights
        else HeightMap.setHeights(
            HeightVal(0f, 7f, 87f, true),
            HeightVal(63f, 29f, Render.unitsWide, false),
            HeightVal(87f, 29f, Render.unitsWide, true)
        )
        if (subLevel != 1) ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
        leftLimit = if (subLevel != 2) -1f else 70f
        when (subLevel) {
            1 -> update1()
            2, 3 -> rockAnimation(subLevel)
            7 -> update7()
        }
    }

    private fun update1() {
        if (Main.getHarold().x > 50 && igneox.current < 1) fadeActive = true
        if (fadeActive) {
            Main.getHarold().setLockControls(true)
            ResourceHandler.getHaroldLoader().state = HaroldLoader.TURN
            when (switchFade) {
                0 -> if (isolsi.current < 1) {
                    isolsi.isActive = true
                    isolsi.update()
                } else {
                    isolsi.isActive = false
                    switchFade++
                }
                1 -> if (hematus.current < 1) {
                    hematus.isActive = true
                    hematus.update()
                } else {
                    hematus.isActive = false
                    switchFade++
                }
                2 -> if (igneox.current < 1) {
                    igneox.isActive = true
                    igneox.update()
                } else {
                    igneox.isActive = false
                    fadeActive = false
                    World.setMasterColor(1f, 1f, 1f)
                    World.master.direction = false
                    World.master.isActive = true
                }
            }
        } else {
            if (switchFade > 0 && World.master.current == 0f) {
                World.subLevel = World.subLevel + 2
                World.master.direction = true
                Main.getHarold().setLockControls(false)
                ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
            }
        }
    }

    private fun rockAnimation(subLevel: Int) {
        if ((Math.random() * 1000).toInt() >= 25) return
        entityRegister.add(RockParticle(subLevel, ((Math.random() * 90).toInt() + 5).toFloat()))
    }

    private fun update7() {
        var count = 0
        for (e in entityRegister) if (e.subLevel == 6 && e.displayName == "Green Golem") count++
        if (count == 0 && Main.getHarold().width + Main.getHarold().x == Render.unitsWide) World.setLevelTransition(true)
    }

    override fun render(subLevel: Int) {
        Graphics.drawImage(backgrounds[subLevel], 0f, 0f)
        if (subLevel == 1) {
            render1()
        }
    }

    private fun render1() {
        if (isolsi.current > 0) {
            Graphics.setDrawColor(1f, 1f, 1f, isolsi.current)
            Graphics.drawImage(sprites[0], 0f, 0f)
        }
        if (hematus.current > 0) {
            Graphics.setDrawColor(1f, 1f, 1f, hematus.current)
            Graphics.drawImage(sprites[1], 0f, 0f)
        }
        if (igneox.current > 0) {
            Graphics.setDrawColor(1f, 1f, 1f, igneox.current)
            Graphics.drawImage(sprites[igneoxCalc() + 1], 0f, 0f)
        }
    }

    private fun igneoxCalc(): Int {
        return if (igneox.current == 1f) {
            4
        } else if (igneox.current > .75f) {
            3
        } else if (igneox.current > .5f) {
            2
        } else {
            1
        }
    }

    override fun renderForeground(subLevel: Int) {}
    override fun cleanup() {}
    override fun reset() {
        isolsi.isActive = false
        isolsi.current = 0f
        hematus.isActive = false
        hematus.current = 0f
        igneox.isActive = false
        igneox.current = 0f
        switchFade = 0
        clearEntityRegister()
        entityRegister.add(ShortGolem(ShortGolem.GolemColor.BLUE, 5, 20f, 7f))
        entityRegister.add(ShortGolem(ShortGolem.GolemColor.GREEN, 7, 79f, 34f))
    }

    init {
        reset()
    }
}