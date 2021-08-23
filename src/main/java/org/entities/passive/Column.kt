package org.entities.passive

import org.entities.Autonomous
import org.entities.Entity
import org.entities.aggressive.Swolem
import org.graphics.Animator
import org.graphics.Graphics
import org.graphics.TimedGradient
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.world.World

class Column(spawnX: Float) : Autonomous(6, spawnX, 7f) {
    // State variable
    private var cState = ColumnState.TREMBLE

    // Texture holder variable
    private var texture: ImageResource? = null

    // Animator
    private val animator = Animator(ResourceHandler.getGolemLoader().columnWorried, 12)

    // Variable to track Swolem aggression
    private var swolemAggressive = false

    // Timer for game kill time
    private val deathTimedGradient = TimedGradient(0f, 3f, 0f, 1f, 1)
    override fun update(deltaTime: Float) {
        // Update stuff
        animator.update()
        deathTimedGradient.update()
        // If death timer finishes, kill player
        if (deathTimedGradient.current == deathTimedGradient.max) {
            World.clearEntites()
            World.setLevel(-1)
        }
        // State calculation
        if (health <= 1) {
            deathTimedGradient.isActive = true
            cState = ColumnState.CRUSHED
        } else if (swolemAggressive) cState = ColumnState.TREMBLE else cState = ColumnState.WORRY
        when (cState) {
            ColumnState.CRUSHED -> {
                animator.setFrames(ResourceHandler.getGolemLoader().columnCrushed)
                animator.frames = ResourceHandler.getGolemLoader().columnTremble
                animator.frames = ResourceHandler.getGolemLoader().columnWorried
            }
            ColumnState.TREMBLE -> {
                animator.frames = ResourceHandler.getGolemLoader().columnTremble
                animator.frames = ResourceHandler.getGolemLoader().columnWorried
            }
            ColumnState.WORRY -> animator.frames = ResourceHandler.getGolemLoader().columnWorried
        }
        texture = animator.currentFrame
    }

    override fun render() {
        texture?.also { texture ->
            Graphics.drawImage(texture, x, y)
        } ?: run {
            println("TEX NULL")
        }
    }

    override fun reset() {
        health = maxHealth
        deathTimedGradient.isActive = false
        deathTimedGradient.current = 0f
    }

    override fun doDamage(attacker: Entity, damage: Int) {
        if (attacker !is Swolem) return
        if (health > 1) super.doDamage(attacker, damage)
    }

    fun setSwolemAggressive(swolemAggressive: Boolean) {
        this.swolemAggressive = swolemAggressive
    }

    private enum class ColumnState {
        TREMBLE, WORRY, CRUSHED
    }

    init {
        maxHealth = 2
        health = maxHealth
        animator.setWalkBack(false)
    }
}