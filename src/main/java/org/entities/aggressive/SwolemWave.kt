package org.entities.aggressive

import org.entities.Entity
import org.graphics.Graphics
import org.loader.ResourceHandler
import org.world.Attack.melee

class SwolemWave internal constructor(
    spawnX: Float, direction: Boolean, // Swolem object to keep track of distance
    private val swolem: Swolem
) : Entity() {
    // Tracks scale of entity/damage
    var scaleMultiplier = 1f
    override fun update(deltaTime: Float) {
        x += if (direction) 50 * deltaTime else -50 * deltaTime
        if (melee(this, scaleMultiplier.toInt(), 1f)) health = 0
        val distance = Math.abs(x - swolem.x)
        scaleMultiplier = 1f //(float) Math.pow(0.5, 0.1 * distance - 1.5);
        // Kill entity if offscreen
        if (x < -50 || x > 110) health = 0
    }

    override fun render() {
        Graphics.scaleFactor = scaleMultiplier
        Graphics.drawImage(ResourceHandler.getBossLoader().getSwolemWave(direction), x, 7f)
        Graphics.scaleFactor = 1f
    }

    override fun reset() {}

    init {
        this.direction = direction
        x = spawnX
        y = 7f
        subLevel = 6
        invincible = true
    }
}