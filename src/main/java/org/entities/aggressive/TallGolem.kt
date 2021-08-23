package org.entities.aggressive

import org.engine.Main
import org.entities.Autonomous
import org.entities.SmartRectangle
import org.graphics.Animator
import org.graphics.Graphics
import org.level.LevelController.currentLevel
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.world.Attack.melee
import org.world.HeightMap
import org.world.World.gravity

class TallGolem(private val golemType: Int, subLevel: Int, spawnX: Float, spawnY: Float) :
    Autonomous(subLevel, spawnX, spawnY) {
    private var golemAnimator: Animator? = null
    private val hitbox = SmartRectangle(x, y, width, height)
    private var golem: ImageResource? = null
    override fun update(deltaTime: Float) {
        //Get HeightMap information
        val h = HeightMap.onGround(hitbox)

        //Movement input
        if (damageTakenFrame == 0f) {
            vX = if (direction) {
                18f
            } else {
                -18f
            }
        } else {
            vX = if (direction && !attackerBehind || !direction && attackerBehind) -.8f else 12f
            damageTakenFrame--
        }
        if (damageCooldown > 0) {
            damageCooldown--
            state = 2
        } else if (state == 2) {
            state = 0
        }

        //Y-Velocity Calculations
        y += vY * deltaTime
        vY -= gravity * deltaTime * 2

        //X-Velocity and Jumping Calculations
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
        val wall = HeightMap.findNextWall(hitbox, direction)
        val jumpDist = 7
        if (wall != null && y + 32 >= wall.height) {
            if (direction) {
                if (wall.startX - x - width <= jumpDist) {
                    state = 3
                }
            } else {
                if (x - wall.endX <= jumpDist) {
                    state = 3
                }
            }
        }
        if (state == 3 && golemAnimator!!.currentFrameNum < golemAnimator!!.frames.size - 1) doXCalc = false
        if (doXCalc) {
            x += vX * deltaTime
            doXCalc()
        }

        //Y-velocity and ground calc
        if (h.isOnGround && vY < 0) {
            y = h.groundLevel
            vY = 0f
            if (state == 3 && golemAnimator!!.onLastFrame()) state = 0
        }
        doAttackCalc()

        //Update the frames
        if (vX == 0f) {
            when (golemType) {
                BLUE -> golemAnimator!!.frames = arrayOf(ResourceHandler.getGolemLoader().getTallBlueGolem(direction))
            }
        } else {
            doSpriteCalc()
        }
        if (state != 3 || golemAnimator!!.currentFrameNum < golemAnimator!!.frames.size - 1) {
            golemAnimator!!.update()
        }
        if (state == 1 && golemAnimator!!.onLastFrame()) {
            state = 0
            melee(this, 1, 4f)
        }
        if (state == 3 && golemAnimator!!.onLastFrame()) {
            if (vY == 0f) {
                vY = 110f
            }
        }
    }

    private fun doSpriteCalc() {
        golem = golemAnimator!!.currentFrame
        when (state) {
            0 -> when (golemType) {
                BLUE -> golemAnimator!!.frames = ResourceHandler.getGolemLoader().getTallBlueGolemWalk(direction)
            }
            1 -> when (golemType) {
                BLUE -> golemAnimator!!.frames = ResourceHandler.getGolemLoader().getTallBlueGolemAttack(direction)
            }
            2 -> when (golemType) {
                BLUE -> golemAnimator!!.frames =
                    arrayOf(ResourceHandler.getGolemLoader().getTallBlueGolemKnockback(direction))
            }
            3 -> when (golemType) {
                BLUE -> golemAnimator!!.frames = ResourceHandler.getGolemLoader().getTallBlueGolemJump(direction)
            }
        }
    }

    private fun doXCalc() {
        val l = currentLevel
        if (x < l.leftLimit || x + width > l.rightLimit) {
            direction = !direction
        }
    }

    private fun doAttackCalc() {
        if (state != 0) return
        if (attackCooldown > 0) {
            attackCooldown--
            return
        }
        if (Main.getHarold().y > y + height || Main.getHarold().y + Main.getHarold().width < y) {
            state = 0
            return
        }
        if (direction) {
            if (Main.getHarold().x >= x && Main.getHarold().x <= x + width + 6) {
                state = 1
                golemAnimator!!.setCurrentFrame(0)
                attackCooldown = 100f
            }
        } else {
            if (Main.getHarold().x + Main.getHarold().width <= x + width && Main.getHarold().width + Main.getHarold().x >= x - 6) {
                state = 1
                golemAnimator!!.setCurrentFrame(0)
                attackCooldown = 100f
            }
        }
    }

    override fun render() {
        if (golem == null) return
        //Update hitbox info
        width = Graphics.toWorldWidth(golem!!.texture.width.toFloat())
        height = Graphics.toWorldHeight(golem!!.texture.height.toFloat())
        hitbox.updateBounds(x, y, width, height)

        //Draw the golem
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
            0 -> "Tall Blue Golem @ $x,$y"
            else -> super.toString()
        }
    }

    companion object {
        const val BLUE = 0
    }

    init {
        when (golemType) {
            BLUE -> {
                health = 3
                displayName = "Tall Blue Golem"
                golemAnimator = Animator(ResourceHandler.getGolemLoader().getTallBlueGolemWalk(direction), 10)
            }
        }
    }
}