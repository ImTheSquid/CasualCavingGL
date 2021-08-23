package org.entities.aggressive

import org.engine.Main
import org.entities.Entity
import org.entities.Harold
import org.entities.SmartRectangle
import org.entities.passive.Column
import org.graphics.Animator
import org.graphics.BossBar
import org.graphics.Graphics
import org.graphics.TimedGradient
import org.level.LevelController
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.world.Attack
import org.world.HeightMap
import org.world.World

class Swolem : Entity() {
    // Boss state
    private var swolemState = SwolemState.NONE

    // Toggles to switch directions for rock waves
    private var rockDir = false
    private var isActive = false

    // How many times ground has been pounded
    private var poundCount = 0
    val bossBar = BossBar(this)
    private val animator = Animator(arrayOf(ResourceHandler.getBossLoader().swolemCenterDown), 6)

    // How much time the swolem must wait before attempting to ground pound
    private val smashCooldown = TimedGradient(0f, 15f, 0f, 1f, 1)

    /* Since the game runs faster than the frame rate, it is possible to increment the smash counter
     * multiple times while only being on one frame. */
    private var smashAlt = true

    // Above comment, but for punching
    private val punchCooldown = TimedGradient(0f, 7f, 0f, 1f, 1)
    private val smashAnimator = TimedGradient(0f, (smashOrder.size - 1).toFloat(), 0f, 1f, 6)
    private var currentFrame: ImageResource? = null
    private val hitbox = SmartRectangle(x, y, width, height)

    // Little golem that the swolem occasionally chases
    private val column = Column(if (Math.random() > 0.5) 10f else 80f)

    // Sets entity to track and target
    private var target: Entity = Main.getHarold()
    override fun update(deltaTime: Float) {
        // Update timers, animators, etc
        column.setSwolemAggressive(target is Column)
        column.update(deltaTime)
        smashCooldown.update()
        punchCooldown.update()
        bossBar.update()
        /* Update State */isActive = y == 7f

        /* Physics and Bounds*/
        val heightMap = HeightMap.onGround(hitbox)
        // Y-calc
        if (heightMap.isOnGround) {
            y = heightMap.groundLevel
            vY = 0f
        } else {
            vY = 15 * World.gravity * deltaTime
            y -= vY * deltaTime
        }

        // X-calc
        val playerX = target.x
        val center = playerX > x && playerX < x + width
        vX = if (center || !isActive || swolemState != SwolemState.NONE) 0f else if (direction) 10f else -10f
        val currentLevel = LevelController.currentLevel
        if (x < currentLevel.leftBound || x + width > currentLevel.rightBound) {
            direction = !direction
        }
        x += vX * deltaTime

        /* Attack Coordination */if (swolemState == SwolemState.NONE && isActive) {
            if (target.x + target.width + 2 > x && target.x - 2 < x + width && !center && punchCooldown.current == punchCooldown.max) swolemState =
                SwolemState.PUNCH else if ((target.x + target.width + 2 < x && x + width < target.x - 2 || center) && smashCooldown.current == smashCooldown.max) swolemState =
                SwolemState.SMASH
        }

        /* Animation */
        // Get the player position and set a target direction
        if (isActive) {
            when (swolemState) {
                SwolemState.PUNCH -> {
                    // If player in reach, then punch to side
                    if (target is Harold) animator.frames =
                        ResourceHandler.getBossLoader().getSwolemPunch(direction) else animator.frames =
                        ResourceHandler.getBossLoader().getSwolemCrush(direction)
                    if (animator.onLastFrame()) {
                        if (target is Harold) Attack.melee(this, 1, 2f) else column.doDamage(this, 1)
                        swolemState = SwolemState.NONE
                        punchCooldown.current = punchCooldown.min
                        target = Main.getHarold()
                    }
                }
                SwolemState.SMASH -> {
                    // Ground pound independent of player position
                    smashAnimator.update()
                    if (smashOrder[smashAnimator.current.toInt()] == 2 && smashAlt) {
                        // Don't let this execute again until next frame
                        LevelController.currentLevel.entityRegister.add(
                            SwolemWave(
                                if (rockDir) x + width else x,
                                rockDir,
                                this
                            )
                        )
                        rockDir = !rockDir
                        smashAlt = false
                        poundCount++
                        if (poundCount == 6) {
                            // Switch targets after smash attack
                            target = column
                            swolemState = SwolemState.VULNERABLE
                            smashCooldown.current = smashCooldown.min
                            poundCount = 0
                        }
                    } else if (smashOrder[smashAnimator.current.toInt()] != 2) smashAlt = true
                }
                SwolemState.VULNERABLE ->                     // Allow for player to hit fist, lowering health
                    swolemState = SwolemState.NONE
                SwolemState.NONE -> if (!center) {
                    direction = playerX > x + width
                    animator.frames = ResourceHandler.getBossLoader().getSwolemWalk(direction)
                } else {
                    animator.setFrames(ResourceHandler.getBossLoader().swolemCenterDown)
                }
            }
        }
        currentFrame =
            if (swolemState != SwolemState.SMASH) animator.currentFrame else ResourceHandler.getBossLoader().swolemSmash[smashOrder[smashAnimator.current.toInt()]]
        animator.update()
    }

    override fun render() {
        column.render()
        width = Graphics.toWorldWidth(currentFrame!!.texture.width.toFloat())
        height = Graphics.toWorldHeight(currentFrame!!.texture.height.toFloat())
        val offset = Graphics.toWorldWidth(get_X_offset())
        hitbox.updateBounds(x + offset, y, width, height)
        if (currentFrame != null) Graphics.drawImage(currentFrame!!, x + offset, y)
    }

    override fun reset() {
        x = 60f
        y = 75f
        poundCount = 0
        health = 6
        isActive = false
        target = Main.getHarold()
        column.reset()
        rockDir = false
    }

    override fun handleDeath() {
        bossBar.update()
    }

    override fun doDamage(attacker: Entity, damage: Int) {
        if (swolemState != SwolemState.VULNERABLE) {
            return
        }
        super.doDamage(attacker, damage)
    }

    /* Offset functions */
    private fun get_X_offset(): Float {
        // Static facing directions
        val playerX = Main.getHarold().x
        return if (!(playerX > x && playerX < x + width)) 19f else 0f
    }

    private enum class SwolemState {
        PUNCH, SMASH, VULNERABLE, NONE
    }

    companion object {
        // Constant smash order
        private val smashOrder = intArrayOf(0, 1, 2, 4, 3, 4, 2, 1, 0)
    }

    init {
        x = 60f
        y = 75f
        subLevel = 6
        displayName = "Swolem"
        maxHealth = 6
        health = maxHealth
        animator.setWalkBack(false)
        smashCooldown.isActive = true
        punchCooldown.isActive = true
        smashAnimator.setLoop(true)
        smashAnimator.isActive = true
    }
}