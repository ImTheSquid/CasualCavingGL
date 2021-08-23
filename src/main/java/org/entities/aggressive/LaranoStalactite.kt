package org.entities.aggressive

import org.entities.Autonomous
import org.graphics.Graphics
import org.loader.ResourceHandler
import org.world.Attack
import org.world.World

class LaranoStalactite(spawnX: Float, spawnY: Float, dir: Boolean) : Autonomous(2, spawnX, spawnY) {
    override fun update(deltaTime: Float) {
        if (y + height < 0) health = 0
        y -= (World.gravity * 0.5 * deltaTime).toFloat()
        Attack.melee(this, 1, 2f)
    }

    override fun render() {
        width = Graphics.toWorldWidth(ResourceHandler.getMiscLoader().laranoStalactite.texture.width.toFloat())
        height = Graphics.toWorldHeight(ResourceHandler.getMiscLoader().laranoStalactite.texture.height.toFloat())
        Graphics.setIgnoreScale(true)
        Graphics.drawImage(ResourceHandler.getMiscLoader().laranoStalactite, x, y)
        Graphics.setIgnoreScale(false)
    }

    override fun reset() {}

    init {
        invincible = true
        direction = dir
        displayName = "Stalactite"
    }
}