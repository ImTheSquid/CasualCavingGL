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

class Larano : Autonomous(2, 0f, 0f) {
    private val NORMAL = 0
    private val READY = 1
    private val ATTACK = 2
    private val CHARGE = 3
    private val DIZZY = 4
    private val CHARGERDY = 5
    private val DAMAGE = 6
    private val JUMP = 7
    private val DEFEAT = 8
    private val EXIT = 9
    private val DONE = 10
    val animator = Animator(ResourceHandler.getBossLoader().laranoReadying, 30)
    private var sprite: ImageResource? = null
    private val hitbox = SmartRectangle(x, y, width, height)
    val bossBar = BossBar(this)
    private var altAttack = false
    private var dizzyCount = 0
    private var chargeAttemptCooldown = 0
    override fun update(deltaTime: Float) {
        val h = HeightMap.onGround(hitbox)
        bossBar.update()
        if (Main.getHarold().x > 10 && state == -1) state = READY
        if (state == -1) return
        if (health == 1 && state < 8) {
            invincible = true
            state = DEFEAT
        }
        if (state == NORMAL || state == EXIT) {
            vX = if (direction) 30f else -30f
        } else if (state == CHARGE) {
            vX = if (direction) 75f else -75f
            if (attackCooldown == 0f && melee(this, 1, 0.5f)) {
                attackCooldown = 20f
            }
        } else if (state != DEFEAT) {
            vX = 0f
        }
        x += vX * deltaTime
        val l = currentLevel
        if ((x < l.leftLimit || x + width > l.rightLimit) && state < EXIT) {
            if (state != CHARGE && state != DIZZY) direction = !direction else if (state == CHARGE) {
                state = DIZZY
                x = if (x < l.leftLimit) l.leftLimit else l.rightLimit - width / 2 - 3
            }
        }
        if (direction && x < l.leftLimit && state < EXIT) {
            x = l.leftLimit + 1
        } else if (!direction && x + width > l.rightLimit && state < EXIT) {
            x = l.rightLimit - width - 1
        }
        y += vY * deltaTime
        if (state != CHARGE) vY -= gravity * deltaTime * 2
        if (h.isOnGround && vY < 0) {
            y = h.groundLevel
            vY = 0f
            if (state == JUMP && animator.onLastFrame()) {
                state = NORMAL
                if (direction && Main.getHarold().x + Main.getHarold().width < x || !direction && Main.getHarold().x > x + width) direction =
                    !direction
            }
        }
        doAttackCalc()
        doSpriteCalc()
        if (damageTakenFrame > 0) damageTakenFrame--
    }

    private fun doAttackCalc() {
        if (!(state == NORMAL || state == ATTACK)) return
        val range = 4
        if (state == ATTACK && animator.onLastFrame()) {
            state = NORMAL
            melee(this, 1, range.toFloat())
        }
        if (attackCooldown > 0) {
            attackCooldown--
            return
        }
        if (Main.getHarold().y > y + height || Main.getHarold().y + Main.getHarold().width < y) {
            state = if (checkJump()) JUMP else NORMAL
            return
        }
        if (direction) {
            if (Main.getHarold().x >= x && Main.getHarold().x <= x + width + range) {
                state = ATTACK
                animator.setCurrentFrame(0)
                attackCooldown = 100f
            } else if (Main.getHarold().x >= x) tryCharge()
        } else {
            if (Main.getHarold().x + Main.getHarold().width <= x + width && Main.getHarold().width + Main.getHarold().x >= x - range) {
                state = ATTACK
                animator.setCurrentFrame(0)
                attackCooldown = 100f
            } else if (Main.getHarold().x + Main.getHarold().width <= x + width) tryCharge()
        }
    }

    private fun checkJump(): Boolean {
        val h = HeightMap.findNextJumpPlat(hitbox, direction, height)
        return h != null && Main.getHarold().y > y + height
    }

    private fun tryCharge() {
        if (chargeAttemptCooldown > 0) {
            chargeAttemptCooldown--
            return
        }
        if (Math.random() < .85) chargeAttemptCooldown = 50 else state = CHARGERDY
    }

    private fun doSpriteCalc() {
        if (state != READY && state != JUMP) animator.fps = 10 else if (state == JUMP) animator.fps = 4
        when (state) {
            NORMAL -> animator.frames = ResourceHandler.getBossLoader().getLaranoWalk(direction)
            READY -> {
                animator.frames = ResourceHandler.getBossLoader().laranoReadying
                if (animator.onLastFrame()) {
                    state = NORMAL
                    animator.fps = 10
                    x = Graphics.toWorldWidth(541f)
                    y = 5f
                    animator.frames = ResourceHandler.getBossLoader().getLaranoWalk(direction)
                    attackCooldown = 100f
                }
            }
            ATTACK -> {
                altAttack = Main.getHarold().y >= y + width / 2
                if (altAttack) {
                    animator.frames = ResourceHandler.getBossLoader().getLaranoAltAttack(direction)
                } else {
                    animator.frames = ResourceHandler.getBossLoader().getLaranoAttack(direction)
                }
            }
            CHARGERDY -> {
                animator.frames = ResourceHandler.getBossLoader().getLaranoShimmer(direction)
                if (animator.onLastFrame()) state = CHARGE
            }
            CHARGE -> animator.frames = arrayOf(ResourceHandler.getBossLoader().getLaranoDash(direction))
            DIZZY -> {
                animator.frames = ResourceHandler.getBossLoader().getLaranoDizzy(direction)
                if (animator.onLastFrame()) {
                    dizzyCount++
                }
                if (dizzyCount == 60) {
                    state = NORMAL
                    dizzyCount = 0
                }
            }
            DAMAGE -> if (damageTakenFrame == 0f) state = NORMAL else animator.frames =
                arrayOf(ResourceHandler.getBossLoader().getLaranoDamage(direction))
            JUMP -> animator.frames = ResourceHandler.getBossLoader().getLaranoJump(direction)
            DEFEAT -> if (x < 48 || x > 52) {
                direction = x < 50
                animator.frames = ResourceHandler.getBossLoader().getLaranoWalk(direction)
                vX = if (direction) 10f else -10f
            } else {
                vX = 0f
                animator.frames = ResourceHandler.getBossLoader().laranoDefeat
            }
            EXIT -> {
                if (x > 101) state++
                animator.setActive(true)
                animator.frames = ResourceHandler.getBossLoader().getLaranoWalk(direction)
                animator.update()
                sprite = animator.currentFrame
                direction = true
            }
            DONE -> health = 0
        }
        sprite = animator.currentFrame
        val checkJump = state == JUMP && animator.onLastFrame()
        val checkDefeat = state == DEFEAT && x > 48 && x < 52
        if (!(checkJump || checkDefeat)) animator.update() else if (state == JUMP) if (vY == 0f) vY = 102f
    }

    override fun render() {
        if (sprite == null) return
        Graphics.setIgnoreScale(true)
        width = Graphics.toWorldWidth(sprite!!.texture.width.toFloat())
        height = Graphics.toWorldHeight(sprite!!.texture.height.toFloat())
        hitbox.updateBounds(x, y, width, height)
        if (damageTakenFrame > 0)
            Graphics.setDrawColor(1f, 0f, 0f, 1f)
        else
            Graphics.setDrawColor(1f, 1f, 1f, 1f)
        if (state == READY) Graphics.drawImage(sprite!!, 0f, 0f) else {
            if (state == DEFEAT && vX == 0f && animator.currentFrameNum == 3)
                Graphics.drawImage(sprite!!, x - doOffsetCalc(), y - 1.5f)
            else
                Graphics.drawImage(sprite!!, x - doOffsetCalc(), y)
        }
        Graphics.setIgnoreScale(false)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
    }

    private fun doOffsetCalc(): Float {
        if (direction) return 0f
        if (state == ATTACK) {
            if (altAttack) {
                when (animator.currentFrameNum) {
                    0 -> return 0f
                    1 -> return Graphics.toWorldWidth(10f)
                    2 -> return Graphics.toWorldWidth(99f)
                    3 -> return Graphics.toWorldWidth(143f)
                }
            }
        }
        return 0f
    }

    override fun reset() {
        direction = false
        animator.fps = 60
        animator.frames = ResourceHandler.getBossLoader().laranoReadying
        sprite = animator.currentFrame
        state = -1
        health = 4
        maxHealth = 4
        x = 0f
        y = 0f
        dizzyCount = 0
        invincible = false
    }

    fun updateSprite() {
        animator.update()
        sprite = animator.currentFrame
    }

    override fun doDamage(attacker: Entity, damage: Int) {
        if (attacker.displayName == "Stalactite" && state == DIZZY) {
            super.doDamage(attacker, damage)
            state = DAMAGE
            damageCooldown = 0f
        }
    }

    override fun handleDeath() {
        bossBar.update()
    }

    fun increaseState() {
        state++
    }

    init {
        displayName = "Larano"
        reset()
    }
}