package org.entities.passive

import org.engine.Main
import org.entities.Autonomous
import org.entities.SmartRectangle
import org.graphics.Graphics
import org.loader.ResourceHandler
import org.world.HeightMap
import org.world.World

class Health internal constructor(subLevel: Int, spawnX: Float, spawnY: Float) : Autonomous(subLevel, spawnX, spawnY) {
    private val hitbox: SmartRectangle
    private var bounceWait = 60
    override fun update(deltaTime: Float) {
        val h = HeightMap.onGround(hitbox)
        //Calculations
        if (bounceWait == 0) {
            vY = 50f
            bounceWait = 220
        } else bounceWait -= (100 * deltaTime).toInt()
        y += vY * deltaTime
        vY -= World.gravity * deltaTime * 2
        //Y-velocity and ground calc
        if (h.isOnGround && vY < 0) {
            y = h.groundLevel
            vY = 0f
        }
        if (Main.getHarold().hitbox.intersects(hitbox) && Main.getHarold().health + 1 <= Main.getHarold().maxHealth) {
            health = 0
            Main.getHarold().giveHealth(1)
        }
        hitbox.updateBounds(x, y, width, height)
    }

    override fun render() {
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.drawImage(ResourceHandler.getMiscLoader().healthHeart, x, y, width, height)
    }

    override fun reset() {}

    override fun toString(): String {
        return "Health Heart @ $x,$y"
    }

    init {
        width = 6f
        height = 6f
        health = 1
        vY = 50f
        invincible = true
        hitbox = SmartRectangle(x, y, width, height)
    }
}