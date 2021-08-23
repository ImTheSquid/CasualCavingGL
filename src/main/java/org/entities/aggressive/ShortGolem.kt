package org.entities.aggressive

import org.engine.Main
import org.entities.Autonomous
import org.entities.Entity
import org.entities.SmartRectangle
import org.graphics.Animator
import org.graphics.Graphics
import org.level.LevelController.currentLevel
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.world.Attack.melee
import org.world.HeightMap
import org.world.World.gravity

//This class encompasses the blue, green, and red golems, as their classes are very similar
class ShortGolem(private val golemType: GolemColor, subLevel: Int, spawnX: Float, spawnY: Float) :
    Autonomous(subLevel, spawnX, spawnY) {
    private var golemAnimator: Animator? = null
    private var golem: ImageResource? = null
    private val hitbox = SmartRectangle(x, y, width, height)
    override fun update(deltaTime: Float) {
        val h = HeightMap.onGround(hitbox)

        //Action input
        if (damageTakenFrame == 0f) {
            vX = if (direction) {
                20f
            } else {
                -20f
            }
        } else {
            vX = if (direction && !attackerBehind || !direction && attackerBehind) -10f else 10f
            damageTakenFrame = maxOf(damageTakenFrame - 100 * deltaTime, 0f)
        }

        //Calculations
        y += vY * deltaTime
        vY -= gravity * deltaTime * 3

        //X-velocity stuff
        var doXCalc = true
        if (HeightMap.checkRightCollision(hitbox)) {
            val hv = HeightMap.findApplicable(hitbox, true)
            if (hv != null && x + width + 0.5 >= hv.startX) {
                if (vX < 0) x += vX * deltaTime else vX = 0f
                doXCalc = false
                direction = !direction
                x -= .25f
            }
        }
        if (HeightMap.checkLeftCollision(hitbox)) {
            val hv = HeightMap.findApplicable(hitbox, false)
            if (hv != null && x - 0.5 <= hv.endX) {
                if (vX > 0) x += vX * deltaTime else vX = 0f
                doXCalc = false
                direction = !direction
                x += .25f
            }
        }
        if (doXCalc) {
            x += vX * deltaTime
            doXCalc()
        }
        if (damageCooldown > 0) {
            damageCooldown = maxOf(damageCooldown - 100 * deltaTime, 0f)
            state = 2
        } else if (state == 2) {
            state = 0
        }

        //Y-velocity and ground calc
        if (h.isOnGround && vY < 0) {
            y = h.groundLevel
            vY = 0f
        }
        doAttackCalc(deltaTime)
        if (vX == 0f) {
            golem = when (golemType) {
                GolemColor.BLUE -> ResourceHandler.getGolemLoader().getBlueGolem(direction)
                GolemColor.GREEN -> ResourceHandler.getGolemLoader().getGreenGolem(direction)
                GolemColor.RED -> ResourceHandler.getGolemLoader().getRedGolem(direction)
                GolemColor.PURPLE -> ResourceHandler.getGolemLoader().getPurpleGolem(direction)
            }
        } else {
            doSpriteCalc()
        }
        golemAnimator!!.update()
        if (state == 1 && golemAnimator!!.onLastFrame()) {
            state = 0
            melee(this, 1, 4f)
        }
    }

    private fun doXCalc() {
        val l = currentLevel
        if (x < l.leftLimit || x + width > l.rightLimit) direction = !direction
        if (golemType == GolemColor.GREEN && HeightMap.onEdge(hitbox, direction)) direction = !direction
    }

    private fun doSpriteCalc() {
        golem = golemAnimator!!.currentFrame
        if (state == 0) {
            when (golemType) {
                GolemColor.BLUE -> golemAnimator!!.frames = ResourceHandler.getGolemLoader().getBlueGolemWalk(direction)
                GolemColor.GREEN -> golemAnimator!!.frames =
                    ResourceHandler.getGolemLoader().getGreenGolemWalk(direction)
                GolemColor.RED -> golemAnimator!!.frames = ResourceHandler.getGolemLoader().getRedGolemWalk(direction)
                GolemColor.PURPLE -> golemAnimator!!.frames =
                    ResourceHandler.getGolemLoader().getPurpleGolemWalk(direction)
            }
        } else if (state == 1) {
            when (golemType) {
                GolemColor.BLUE -> golemAnimator!!.frames =
                    ResourceHandler.getGolemLoader().getBlueGolemAttack(direction)
                GolemColor.GREEN -> golemAnimator!!.frames =
                    ResourceHandler.getGolemLoader().getGreenGolemAttack(direction)
                GolemColor.RED -> golemAnimator!!.frames = ResourceHandler.getGolemLoader().getRedGolemAttack(direction)
                GolemColor.PURPLE -> golemAnimator!!.frames =
                    ResourceHandler.getGolemLoader().getPurpleGolemAttack(direction)
            }
        } else if (state == 2) {
            when (golemType) {
                GolemColor.BLUE -> golemAnimator!!.frames =
                    arrayOf(ResourceHandler.getGolemLoader().getBlueGolemKnockback(direction))
                GolemColor.GREEN -> golemAnimator!!.frames =
                    arrayOf(ResourceHandler.getGolemLoader().getGreenGolemKnockback(direction))
                GolemColor.RED -> golemAnimator!!.frames =
                    arrayOf(ResourceHandler.getGolemLoader().getRedGolemKnockback(direction))
                GolemColor.PURPLE -> golemAnimator!!.frames =
                    arrayOf(ResourceHandler.getGolemLoader().getPurpleGolemKnockback(direction))
            }
        }
    }

    private fun doAttackCalc(deltaTime: Float) {
        if (attackCooldown > 0) {
            attackCooldown -= 100 * deltaTime
            return
        }
        if (Main.getHarold().y > y + height || Main.getHarold().y + Main.getHarold().width < y) {
            state = 0
            return
        }
        if (direction) {
            if (Main.getHarold().x >= x && Main.getHarold().x <= x + width + 4) {
                state = 1
                golemAnimator!!.setCurrentFrame(0)
                attackCooldown = 80f
            }
        } else {
            if (Main.getHarold().x + Main.getHarold().width <= x + width && Main.getHarold().width + Main.getHarold().x >= x - 4) {
                state = 1
                golemAnimator!!.setCurrentFrame(0)
                attackCooldown = 80f
            }
        }
    }

    override fun doDamage(attacker: Entity, damage: Int) {
        if (golemType != GolemColor.PURPLE) super.doDamage(attacker, damage) else {
            attackerBehind = if (direction) attacker.x < x else attacker.x > x
            if (attackerBehind) direction = !direction else if (!invincible) super.doDamage(attacker, damage)
        }
    }

    override fun render() {
        if (golem == null) return
        width = Graphics.toWorldWidth(golem!!.texture.width.toFloat())
        height = Graphics.toWorldHeight(golem!!.texture.height.toFloat())
        hitbox.updateBounds(x, y, width, height)
        if (damageTakenFrame > 0) {
            Graphics.setDrawColor(1f, 0f, 0f, 1f) //Set damage color if needed
        } else {
            Graphics.setDrawColor(1f, 1f, 1f, 1f)
        }
        Graphics.drawImage(golem!!, x, y)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
    }

    override fun reset() {}
    override fun toString(): String {
        return when (golemType) {
            GolemColor.BLUE -> "Blue Golem @ $x,$y"
            GolemColor.GREEN -> "Green Golem @ $x,$y"
            GolemColor.RED -> "Red Golem @ $x,$y"
            GolemColor.PURPLE -> "Purple Golem @ $x,$y"
            else -> super.toString()
        }
    }

    enum class GolemColor {
        BLUE, GREEN, RED, PURPLE
    }

    init {
        when (golemType) {
            GolemColor.BLUE -> {
                health = 2
                golemAnimator = Animator(ResourceHandler.getGolemLoader().getBlueGolemWalk(direction), 10)
                displayName = "Blue Golem"
            }
            GolemColor.GREEN -> {
                health = 2
                golemAnimator = Animator(ResourceHandler.getGolemLoader().getGreenGolemWalk(direction), 10)
                displayName = "Green Golem"
            }
            GolemColor.RED -> {
                health = 3
                golemAnimator = Animator(ResourceHandler.getGolemLoader().getRedGolemWalk(direction), 10)
                displayName = "Red Golem"
            }
            GolemColor.PURPLE -> {
                health = 2
                golemAnimator = Animator(ResourceHandler.getGolemLoader().getPurpleGolemWalk(direction), 10)
                displayName = "Purple Golem"
            }
        }
    }
}