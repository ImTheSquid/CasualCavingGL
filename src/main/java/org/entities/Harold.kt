package org.entities

import com.jogamp.newt.event.KeyEvent
import org.graphics.Animator
import org.graphics.Graphics
import org.input.Keyboard
import org.level.LevelController
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.Attack
import org.world.HeightMap
import org.world.World

class Harold : Entity() {
    private val haroldAnimator = Animator(ResourceHandler.getHaroldLoader().haroldWalk, 12)
    private var harold: ImageResource? = null
    private var jump = false
    private var lockControls = false
    private var followCamera = false
    var isBlocking = false
        private set
    val hitbox = SmartRectangle(x, y, width, height)
    override fun update(deltaTime: Float) {
        if (!movement) {
            return
        }
        if (health <= 0 || y + height < -10 && World.subLevel != 4 && World.getLevel() == 6) { //Am I dead?
            if (y + height < -10) health = 0
            World.clearEntites()
            World.setLevel(-1)
        }
        //Get HeightMap info package
        val h = HeightMap.onGround(hitbox)
        //Movement keys
        if (damageTakenFrame == 0f) {
            if (Keyboard.keys.contains(KeyEvent.VK_A) && !lockControls) {
                vX = -40f * Graphics.scaleFactor
            }
            if (Keyboard.keys.contains(KeyEvent.VK_D) && !lockControls) {
                vX = 40f * Graphics.scaleFactor
            }
            isBlocking = if (vX == 0f && ResourceHandler.getHaroldLoader().state == HaroldLoader.LANTERN) {
                Keyboard.keys.contains(KeyEvent.VK_C)
            } else false
        } else {
            vX =
                if (direction && !attackerBehind || !direction && attackerBehind) -1f * Graphics.scaleFactor else 1f * Graphics.scaleFactor
            damageTakenFrame--
        }
        if (Keyboard.keys.contains(KeyEvent.VK_SPACE) && !jump && !lockControls) {
            if (h.isOnGround) {
                vY = 102f
                jump = true
            }
        } else if (!Keyboard.keys.contains(KeyEvent.VK_SPACE) && jump) {
            jump = false
            if (vY < -30f) {
                vY = -30f
            }
        }

        //Attack key
        if (!lockControls && Keyboard.keys.contains(KeyEvent.VK_W) && ResourceHandler.getHaroldLoader().state == HaroldLoader.LANTERN && attackCooldown <= 0) {
            haroldAnimator.setCurrentFrame(0)
            ResourceHandler.getHaroldLoader().state = HaroldLoader.ATTACK
            attackCooldown = 100f
        }
        Keyboard.keys.remove(KeyEvent.VK_W) //Fallback if key gets stuck
        y += vY * deltaTime
        vY -= World.gravity * deltaTime * 2

        //X-velocity stuff
        var doXCalc = true

        //Deals with colliding with objects above ground level
        if (HeightMap.checkRightCollision(hitbox)) {
            val hv = HeightMap.findApplicable(hitbox, true)
            if (hv != null && x + width + 0.5 >= hv.startX) {
                if (vX < 0) x += vX * deltaTime else vX = 0f
                doXCalc = false
            }
        }
        if (HeightMap.checkLeftCollision(hitbox)) {
            val hv = HeightMap.findApplicable(hitbox, false)
            if (hv != null && x - 0.5 <= hv.endX) {
                if (vX > 0) x += vX * deltaTime else vX = 0f
                doXCalc = false
            }
        }
        if (doXCalc) {
            x += vX * deltaTime
            doXCalc(deltaTime)
        }
        if (damageCooldown > 0) damageCooldown -= (100 * deltaTime).toInt()
        if (h.isOnGround && vY < 0) {
            y = h.groundLevel
            vY = 0f
            jump = false
        }
        if (attackCooldown > 0) attackCooldown -= 100 * deltaTime
        if (damageTakenFrame == 0f) ResourceHandler.getHaroldLoader().setDirection(direction)
        if (isBlocking) ResourceHandler.getHaroldLoader().state = HaroldLoader.BLOCK
        harold =
            if ((vX == 0f || damageTakenFrame > 0) && ResourceHandler.getHaroldLoader().state != HaroldLoader.ATTACK) {
                ResourceHandler.getHaroldLoader().harold
            } else {
                haroldAnimator.currentFrame
            }

        //If on final frame of attack animation, send attack signal to Attack class
        if (ResourceHandler.getHaroldLoader().state == HaroldLoader.ATTACK && haroldAnimator.currentFrameNum == 3) {
            Attack.melee(this, 1, 5f)
            ResourceHandler.getHaroldLoader().disableAttackPause()
            ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
        }
        val currentLevel = LevelController.currentLevel
        //Deals with going between sublevels
        //Limit controls
        if (x < currentLevel.leftLimit) x = currentLevel.leftLimit
        if (x + width > currentLevel.rightLimit) x = currentLevel.rightLimit - width
        //Transferring between sublevels
        if (x < currentLevel.leftBound) {
            x = if (World.subLevel > 0) {
                World.subLevel = World.subLevel - 1
                currentLevel.rightBound - 5 - width
            } else currentLevel.leftBound
        }
        if (x + width > currentLevel.rightBound) {
            x = if (World.subLevel < currentLevel.numSublevels - 1) {
                World.incrementSubLevel()
                5f
            } else {
                currentLevel.rightBound - width
            }
        }

        //If doing the special turn animation, set it up properly
        if (ResourceHandler.getHaroldLoader().state != HaroldLoader.TURN) {
            haroldAnimator.fps = 12
            haroldAnimator.frames = ResourceHandler.getHaroldLoader().haroldWalk
            haroldAnimator.update()
        }
    }

    private fun doXCalc(deltaTime: Float) {
        if (damageTakenFrame == 0f) {
            if (vX > 0) {
                direction = true
                if (vX - World.gravity * deltaTime < 0) vX = 0f else vX -= World.gravity * deltaTime
            } else if (vX < 0) {
                direction = false
                if (vX + World.gravity * deltaTime > 0) vX = 0f else vX += World.gravity * deltaTime
            }
        } else {
            if (direction && !attackerBehind || !direction && attackerBehind) vX += World.gravity * deltaTime else vX -= World.gravity * deltaTime
        }
    }

    override fun render() {
        Graphics.setFollowCamera(followCamera)
        width = Graphics.toWorldWidth(harold!!.texture.width.toFloat()) * Graphics.scaleFactor
        height = Graphics.toWorldHeight(harold!!.texture.height.toFloat()) * Graphics.scaleFactor
        hitbox.updateBounds(x, y, width, height)
        if (!visible) return
        if (ResourceHandler.getHaroldLoader().state == HaroldLoader.TURN) {
            haroldAnimator.fps = 3
            if (haroldAnimator.currentFrameNum > 0 && !haroldAnimator.frames.contentEquals(ResourceHandler.getHaroldLoader().turn))
                haroldAnimator.setCurrentFrame(0)
            haroldAnimator.frames = ResourceHandler.getHaroldLoader().turn
            harold = haroldAnimator.currentFrame
            if (haroldAnimator.currentFrameNum != 1) haroldAnimator.update()
        }
        if (damageTakenFrame > 0) {
            Graphics.setDrawColor(1f, .0f, .0f, 1f)
        } else Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.drawImage(harold!!, x, y)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.setFollowCamera(false)
    }

    fun renderHealth() {
        if (!visible || World.getLevel() < 1) return
        Graphics.setIgnoreScale(true)
        Graphics.setFollowCamera(true)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        if (!invincible) for (i in 0 until health) {
            val x = 0.5f + 5.5f * i
            Graphics.drawImage(ResourceHandler.getHaroldLoader().health, x, 0.5f, 5f, 5f)
        } else Graphics.drawImage(ResourceHandler.getHaroldLoader().infiniteHealth, 0.5f, 0.5f, 5f, 5f)
        Graphics.setIgnoreScale(false)
        Graphics.setFollowCamera(false)
    }

    override fun reset() {
        ResourceHandler.getHaroldLoader().disableAttackPause()
        ResourceHandler.getHaroldLoader().state = HaroldLoader.NORMAL
        movement = true
        x = if (World.latestCheckpoint > World.Checkpoint.start) 5f else 65f
        y = 7f
        health = 3
        vX = 0f
        vY = 0f
        damageTakenFrame = 0f
        damageCooldown = 0f
        lockControls = false
        followCamera = false
    }

    fun setHarold(harold: ImageResource?) {
        this.harold = harold
    }

    fun setLockControls(lockControls: Boolean) {
        this.lockControls = lockControls
    }

    val areControlsLocked: Boolean
        get() = lockControls

    fun setFollowCamera(followCamera: Boolean) {
        this.followCamera = followCamera
    }

    init {
        maxHealth = 3
        displayName = "Harold"
        reset()
    }
}