package org.entities.aggressive

import org.engine.Main
import org.entities.Autonomous
import org.entities.Entity
import org.entities.SmartRectangle
import org.graphics.Animator
import org.graphics.BossBar
import org.graphics.Graphics
import org.level.LevelController.currentLevel
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.world.Attack.melee
import org.world.HeightMap
import org.world.World.gravity

class RedMajor : Autonomous(5, 75f, 7f) {
    private val NORMAL = 0
    private val READYING = 1
    private val ATTACK = 2
    private val DAMAGE = 3
    private val DEATH = 4
    private var redAnimator: Animator? = null
    private var redMajor: ImageResource? = null
    private val hitbox = SmartRectangle(x, y, width, height)
    val bossBar = BossBar(this)
    private var doStartReady = true
    private var startFight = false
    fun setStartFight(startFight: Boolean) {
        this.startFight = startFight
    }

    override fun update(deltaTime: Float) {
        bossBar.update()
        if (state == 4) {
            if (redAnimator!!.delay > 0) redAnimator!!.update() else health = 0
        }
        if (!startFight || state == 4) return
        val heightReturn = HeightMap.onGround(hitbox)
        //Determines movement and knockback
        if (damageTakenFrame == 0f) {
            vX = if (direction) {
                10f
            } else {
                -10f
            }
        } else {
            vX = if (direction && !attackerBehind || !direction && attackerBehind) -2f else 2f
            damageTakenFrame--
        }

        //Calculates the y-value and velocity
        y += vY
        vY -= gravity
        if (heightReturn.isOnGround && vY < 0) {
            y = heightReturn.groundLevel
            vY = 0f
        }

        //Calculates the x-value and velocity
        if (state != READYING && redAnimator!!.delay == 0L) {
            x += vX * deltaTime
            val l = currentLevel
            if (x < l.leftLimit || x + width > l.rightLimit) direction = !direction
        }
        if (direction && Main.getHarold().x + Main.getHarold().width < x || !direction && Main.getHarold().x > x + width) direction =
            !direction

        //Current sprite calculations
        if (damageCooldown > 0) {
            damageCooldown -= (100 * deltaTime).toInt()
            state = DAMAGE
        } else if (state == DAMAGE) {
            state = 0
        }
        doAttackCalc(deltaTime)
        if (state == ATTACK && redAnimator!!.onLastFrame()) {
            redAnimator!!.delay = 7
            state = NORMAL
            melee(this, 1, 5f)
        }
        if (vX == 0f) redAnimator!!.frames = arrayOf(ResourceHandler.getBossLoader().getRedMajorStill(direction)) else {
            doSpriteCalc()
        }
        redAnimator!!.update()
    }

    private fun doSpriteCalc() {
        if (redAnimator!!.delay == 0L) when (state) {
            NORMAL -> redAnimator!!.frames = ResourceHandler.getBossLoader().getRedMajorWalk(direction)
            READYING -> redAnimator!!.frames = ResourceHandler.getBossLoader().getRedMajorReady(direction)
            ATTACK -> redAnimator!!.frames = ResourceHandler.getBossLoader().getRedMajorAttack(direction)
            DAMAGE -> redAnimator!!.frames = arrayOf(ResourceHandler.getBossLoader().getRedMajorDamage(direction))
        }
        if (doStartReady && redAnimator!!.onLastFrame()) {
            redMajor = redAnimator!!.currentFrame
            redAnimator!!.delay = 60
            redAnimator!!.fps = 7
            doStartReady = false
            state = NORMAL
        }
        if (state == READYING && redAnimator!!.onLastFrame()) {
            redMajor = redAnimator!!.currentFrame
            redAnimator!!.delay = 1
            state = ATTACK
            redAnimator!!.setCurrentFrame(0)
        }
        redMajor = redAnimator!!.currentFrame
    }

    private fun doAttackCalc(deltaTime: Float) {
        if (attackCooldown > 0) {
            attackCooldown -= 100 * deltaTime
            return
        }
        if (Main.getHarold().y > y + height || Main.getHarold().y + Main.getHarold().width < y) {
            state = NORMAL
            return
        }
        if (direction) {
            if (Main.getHarold().x >= x && Main.getHarold().x <= x + width + 5) {
                state = READYING
                redAnimator!!.setCurrentFrame(0)
                attackCooldown = 100f
            }
        } else {
            if (Main.getHarold().x + Main.getHarold().width <= x + width && Main.getHarold().width + Main.getHarold().x >= x - 5) {
                state = READYING
                redAnimator!!.setCurrentFrame(0)
                attackCooldown = 100f
            }
        }
    }

    override fun render() {
        if (redMajor == null) return
        width = Graphics.toWorldWidth(redMajor!!.texture.width.toFloat())
        height = Graphics.toWorldHeight(redMajor!!.texture.height.toFloat())
        hitbox.updateBounds(x, y, width, height)
        if (damageTakenFrame > 0) {
            Graphics.setDrawColor(1f, 0f, 0f, 1f) //Set damage color if needed
        } else Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.drawImage(redMajor!!, x, y)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
    }

    override fun reset() {
        direction = false
        displayName = "Red Major"
        redAnimator = Animator(ResourceHandler.getBossLoader().getRedMajorReady(false), 1)
        redMajor = redAnimator!!.currentFrame
        health = 8
        maxHealth = 8
        state = READYING
        doStartReady = true
        startFight = false
        redAnimator!!.delay = 60
        x = 75f
        y = 7f
        invincible = false
    }

    override fun handleDeath() {
        bossBar.update()
    }

    override fun doDamage(attacker: Entity, damage: Int) {
        if (health > 1) super.doDamage(attacker, damage) else {
            invincible = true
            state = DEATH
            redAnimator!!.delay = 60
            redAnimator!!.frames = arrayOf(ResourceHandler.getBossLoader().getRedMajorDeath(direction))
            redMajor = redAnimator!!.currentFrame
        }
    }

    init {
        reset()
    }
}