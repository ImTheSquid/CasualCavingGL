package org.entities.passive

import org.entities.Autonomous
import org.graphics.Graphics
import org.level.LevelController
import org.loader.ResourceHandler

class LifeCrystal(subLevel: Int, spawnX: Float, spawnY: Float) : Autonomous(subLevel, spawnX, spawnY) {
    override fun update(deltaTime: Float) {
        if (damageTakenFrame > 0) damageTakenFrame--
    }

    override fun render() {
        if (damageTakenFrame > 0) Graphics.setDrawColor(1f, 0f, 0f, 1f) else Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.drawImage(ResourceHandler.getMiscLoader().lifeCrystal, x, y, 8.44f, 10f)
    }

    override fun reset() {}
    override fun handleDeath() {
        LevelController.currentLevel.entityRegister.add(Health(subLevel, x, y))
    }

    init {
        displayName = "Life Crystal"
    }
}