package org.graphics

import org.entities.Entity

class BossBar(  //TODO Implement smoother movement using a quadratic or logarithmic function
    private val track: Entity
) {
    private var health: Int
    private var max: Int
    fun update() {
        health = track.health
        max = track.maxHealth
    }

    fun render() {
        Graphics.setIgnoreScale(true)
        val width = (30 * health / max).toFloat()
        if (width == 0f) return
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.setFont(Graphics.FontType.NORMAL)
        val y =
            Render.unitsTall - Graphics.toWorldHeight(Graphics.currentFont.getBounds(track.displayName).height.toFloat())
        Graphics.drawTextCentered(track.displayName, 50f, y + 1f)
        Graphics.setDrawColor(0.63f, 0.53f, 0.02f, 0.5f)
        Graphics.fillRectCentered(50f, y - 2, 30f, 1f)
        Graphics.setDrawColor(0.94f, 0.8f, 0.09f, 1f)
        Graphics.fillRectCentered(50f, y - 2, width, 1f)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.setIgnoreScale(false)
    }

    init {
        health = track.health
        max = track.maxHealth
    }
}