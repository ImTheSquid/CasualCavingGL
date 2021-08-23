package org.entities.passive

import org.entities.Autonomous
import org.graphics.Graphics
import org.loader.ResourceHandler
import org.world.World

class RockParticle(subLevel: Int, spawnX: Float) : Autonomous(subLevel, spawnX, 60f) {
    var particle = (Math.random() * 2).toInt()
    override fun update(deltaTime: Float) {
        if (y + height < 0) health = 0
        y -= World.gravity * 4
    }

    override fun render() {
        height =
            Graphics.toWorldHeight(ResourceHandler.getMiscLoader().rockParticles[particle].texture.height.toFloat())
        Graphics.drawImage(ResourceHandler.getMiscLoader().rockParticles[particle], x, y)
    }

    override fun reset() {}

    init {
        invincible = true
        displayName = "RockParticle"
    }
}