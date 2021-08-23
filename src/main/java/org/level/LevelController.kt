package org.level

import org.engine.AudioManager.resetGame
import org.graphics.Graphics.scaleFactor
import org.level.levels.*
import org.loader.ResourceHandler
import org.world.World.getLevel
import org.world.World.setMasterColor

object LevelController {
    val levels = arrayOf(
        Death(),
        Title(ResourceHandler.getLevelLoader().title),
        Level1(ResourceHandler.getLevelLoader().level1),
        Level2(ResourceHandler.getLevelLoader().level2),
        Level3(ResourceHandler.getLevelLoader().level3),
        Level4(ResourceHandler.getLevelLoader().level4),
        Level5(ResourceHandler.getLevelLoader().level5),
        Level6(ResourceHandler.getLevelLoader().level6)
    )

    fun update(level: Int, subLevel: Int, deltaTime: Float) {
        levels[level + 1].update(subLevel, deltaTime)
    }

    fun render(level: Int, subLevel: Int) {
        levels[level + 1].render(subLevel)
    }

    fun renderForeground(level: Int, subLevel: Int) {
        levels[level + 1].renderForeground(subLevel)
    }

    fun cleanup(level: Int) {
        levels[level + 1].cleanup()
    }

    fun init(level: Int) {
        levels[level + 1].init()
    }

    fun loadAssets(level: Int) {
        levels[level + 1].assets
    }

    val currentLevel: Level
        get() = levels[getLevel() + 1]

    fun resetAll() {
        scaleFactor = 1f
        setMasterColor(0f, 0f, 0f)
        resetGame()
        for (l in levels) {
            l.reset()
        }
    }

    fun getNumLevels(): Int {
        return levels.size
    }

    fun getNumSubLevels(): Int {
        return levels[getLevel() + 1].numSublevels
    }
}