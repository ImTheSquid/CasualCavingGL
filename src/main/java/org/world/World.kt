package org.world

import com.jogamp.newt.event.KeyEvent
import org.engine.AudioManager
import org.engine.Main
import org.entities.Entity
import org.entities.SmartRectangle
import org.graphics.Graphics
import org.graphics.Notification
import org.graphics.Render
import org.graphics.TimedGradient
import org.input.Keyboard
import org.level.LevelController
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import java.util.concurrent.ConcurrentLinkedQueue

object World {
    var master = TimedGradient(0f, 1f, 1f, 0.02f, 35)
    private val tFade = TimedGradient(0f, 1f, 0f, 0.02f, 35) //Fade controller for level transitions
    private var level = 0
    var subLevel = 0
    var assetLoaderCounter = 0
        private set
    var latestCheckpoint = Checkpoint.start
        private set

    enum class Checkpoint {
        start, larano, laranoFinish
    }

    private var game = false
    private var pause = false
    private var levelTransition = false
    private var transitionDir = true
    var isHaroldEvil = false //Set whether in game or menu. Set pause status
    private var masterRed = 0f
    private var masterGreen = 0f
    private var masterBlue = 0f
    var gravity = 120f
    val entities = ConcurrentLinkedQueue<Entity>() //Entity registry
    private val notifications = ConcurrentLinkedQueue<Notification>()
    private val pauseReturn = SmartRectangle(Render.unitsWide / 2, 30f, 20f, 5f, true) //Button detectors
    private val pauseTitleReturn = SmartRectangle(Render.unitsWide / 2, 6.6f, 18f, 4f, true)
    private val musicControl = SmartRectangle(0.5f, 0.5f, 5f, 5f)
    fun update(deltaTime: Float) {
        Debug.update()
        handleWindowSize()
        if (Keyboard.keys.contains(KeyEvent.VK_ESCAPE) && game && !levelTransition) {
            pause = !pause
            AudioManager.handlePause(pause)
            while (Keyboard.keys.contains(KeyEvent.VK_ESCAPE)) {
            } //Wait for key release
        }
        if (level == 0) game = false
        LevelController.update(level, subLevel, deltaTime)
        //TODO implement render stages (pre-update,update,post-update)
        entities.removeIf { n: Entity -> n.health <= 0 }
        for (e in entities) {
            if (e.subLevel != subLevel) continue
            if (pause) {
                if (e.pauseUpdate) e.update(deltaTime)
            } else if (!game) {
                if (e.nonGameUpdate) e.update(deltaTime)
            } else {
                e.update(deltaTime)
            }
        }
        if (level > 0 && !pause) Main.getHarold().update(deltaTime)
        if (pause) {
            pauseReturn.isActive = true
            pauseReturn.update(deltaTime)
            pauseTitleReturn.isActive = true
            pauseTitleReturn.update(deltaTime)
            if (pauseReturn.isPressed) pause = false
            if (pauseTitleReturn.isPressed) {
                setLevel(0)
                subLevel = 1
                LevelController.resetAll()
                Main.getHarold().reset()
                pause = false
            }
            musicControl.update(deltaTime)
            if (musicControl.isPressed) {
                AudioManager.isMusicEnabled = !AudioManager.isMusicEnabled
                while (musicControl.isPressed) musicControl.update(deltaTime)
            }
        } else {
            pauseReturn.isActive = false
            pauseTitleReturn.isActive = false
        }
        levelTransUpdate()
        updateNotifications()

        //Master brightness code
        if (!pause) master.update()
        tFade.update()
    }

    private fun handleWindowSize() {
        if (Render.getWindow().width != Render.virtual_width || Render.getWindow().height != Render.virtual_height) {
            Render.getWindow().setSize(Render.virtual_width, Render.virtual_height)
            val resWarn = Notification(
                "Resolution Warning",
                "This game only supports a resolution of 1280x720",
                ResourceHandler.getMiscLoader().resolutionWarning
            )
            if (!notificationPresent(resWarn)) newNotification(resWarn)
        }
    }

    private fun levelTransUpdate() {
        if (levelTransition) {
            AudioManager.fadeOut()
            if (master.current > 0) {
                master.direction = false
                master.isActive = true
            } else if (transitionDir) {
                if (tFade.current == 1f && assetLoaderCounter >= (LevelController.levels[level + 2].assets.size)) {
                    transitionDir = false
                    tFade.setSecondDelay(2)
                    tFade.direction = false
                } else {
                    tFade.direction = true
                    tFade.isActive = true
                }
            } else if (tFade.current == 0f) {
                transitionDir = true
                levelTransition = false
                LevelController.cleanup(level)
                level++
                LevelController.init(level)
                subLevel = 0
                Main.getHarold().setMovement(true)
                Main.getHarold().x = 5f
                master.isActive = false
                master.current = 1f
                AudioManager.handleLevelTransition(level)
                assetLoaderCounter = 0
                entities.clear()
            }
        }
    }

    private fun updateNotifications() {
        for (n in notifications) {
            n.update()
        }
        notifications.removeIf(Notification::isDone)
    }

    fun render() {
        if (Render.getWindow().width != Render.virtual_width || Render.getWindow().height != Render.virtual_height) return
        LevelController.render(level, subLevel)
        //TODO implement render stages (pre-render,render,post-render)
        for (e in entities) {
            if (e.subLevel != subLevel) continue
            if (pause) {
                if (e.pauseRender) e.render()
            } else if (!game) {
                if (e.nonGameRender) e.render()
            } else {
                e.render()
            }
        }
        if (level > 0) Main.getHarold().render()
        LevelController.renderForeground(level, subLevel)

        //Master brightness, always do last
        Graphics.setDrawColor(masterRed, masterGreen, masterBlue, 1 - master.current)
        Graphics.setIgnoreScale(true)
        Graphics.fillRect(0f, 0f, Render.unitsWide, Render.unitsTall)
        Graphics.setIgnoreScale(false)
        Graphics.setDrawColor(1f, 1f, 1f, 1f) //Reset color

        //Special case level transition
        if (levelTransition) {
            levelTransition()
        } else Main.getHarold().renderHealth()
        if (pause) {
            Graphics.setIgnoreScale(true)
            Graphics.setFollowCamera(true)
            Graphics.setDrawColor(.25f, .25f, .25f, .4f)
            Graphics.fillRect(0f, 0f, Render.unitsWide, Render.unitsTall)
            Graphics.setDrawColor(1f, 1f, 1f, 1f)
            Graphics.setFont(Graphics.FontType.TITLE)
            Graphics.drawTextCentered("Paused", Render.unitsWide / 2, 40f)
            pauseReturn.setColor(0.721f, 0.721f, 0.721f, 1f)
            pauseReturn.render()
            Graphics.setDrawColor(1f, 1f, 1f, 1f)
            Graphics.setFont(Graphics.FontType.NORMAL)
            Graphics.drawTextCentered("Back to Game", Render.unitsWide / 2, 30f)
            pauseTitleReturn.setColor(0.6f, 0f, 0f, 1f)
            pauseTitleReturn.render()
            Graphics.setDrawColor(1f, 1f, 1f, 1f)
            Graphics.drawTextCentered("Quit to Title", Render.unitsWide / 2, 7f)
            Graphics.drawImage(
                ResourceHandler.getMiscLoader().getMusicButton(AudioManager.isMusicEnabled),
                0.5f,
                0.5f,
                5f,
                5f
            )
            Graphics.setIgnoreScale(false)
            Graphics.setFollowCamera(false)
        }
        renderNotifications()
        Debug.render()
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        if (Keyboard.keys.contains(KeyEvent.VK_F2)) {
            Graphics.takeScreenshot()
            while (Keyboard.keys.contains(KeyEvent.VK_F2)) {
            }
        }
    }

    private fun levelTransition() {
        setMasterColor(0f, 0f, 0f)
        Graphics.setDrawColor(1f, 1f, 1f, tFade.current)
        Graphics.setFont(Graphics.FontType.TITLE)
        Graphics.drawTextCentered("Part " + (level + 1), 50f, 35f)
        if (level == 0) ResourceHandler.getHaroldLoader().state =
            HaroldLoader.NORMAL else ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
        if (tFade.current == 1f && assetLoaderCounter < (LevelController.levels[level + 2].assets.size)) {
            LevelController.levels[level + 2].assets[assetLoaderCounter].preloadTexture()
            incrementAssetLoadCount()
            renderAssetLoadingIndicator(LevelController.levels[level + 2].assets.size)
        }
    }

    private fun renderNotifications() {
        var yOffset: Float = Render.unitsTall - Notification.height
        for (n in notifications) {
            n.render(yOffset)
            yOffset -= Notification.height
        }
    }

    fun renderAssetLoadingIndicator(numAssetsToLoad: Int) {
        Graphics.setFont(Graphics.FontType.SMALL)
        Graphics.drawText("Loading assets... ($assetLoaderCounter/$numAssetsToLoad)", 0.5f, 1f)
    }

    fun addEntity(e: Entity) {
        if (!entities.contains(e)) entities.offer(e)
    }

    fun addEntities(list: Collection<Entity>) {
        for (e in list) {
            addEntity(e)
        }
    }

    private fun newNotification(n: Notification) {
        if (!notifications.contains(n)) notifications.offer(n)
    }

    private fun notificationPresent(n: Notification): Boolean {
        for (notification in notifications) {
            if (n.compareTo(notification) == 0) return true
        }
        return false
    }

    fun newCheckpoint(checkpoint: Checkpoint) {
        if (latestCheckpoint > checkpoint) {
            return
        }
        latestCheckpoint = checkpoint
        when (latestCheckpoint) {
            Checkpoint.larano -> newNotification(
                Notification(
                    "Checkpoint Unlocked",
                    "Larano",
                    ResourceHandler.getMiscLoader().checkmark
                )
            )
            Checkpoint.laranoFinish -> newNotification(
                Notification(
                    "Checkpoint Unlocked",
                    "Larano's Defeat",
                    ResourceHandler.getMiscLoader().checkmark
                )
            )
            else -> return
        }
    }

    fun startFromCheckpoint() {
        AudioManager.setMusicGain(AudioManager.MUSIC_VOL)
        when (latestCheckpoint) {
            Checkpoint.start -> {
                setLevel(1)
                subLevel = 0
                AudioManager.setMusicPlayback(AudioManager.PLAY)
            }
            Checkpoint.larano -> {
                setLevel(5)
                subLevel = 0
                AudioManager.setMusicPlayback(AudioManager.STOP)
            }
            Checkpoint.laranoFinish -> {
                setLevel(6)
                subLevel = 0
                AudioManager.setMusicPlayback(AudioManager.STOP)
            }
        }
    }

    fun addEntities(array: Array<Entity>) {
        for (e in array) {
            addEntity(e)
        }
    }

    fun removeEntity(e: Entity) {
        entities.remove(e)
    }

    @JvmStatic
    fun clearEntites() {
        entities.clear()
    }

    fun setGame(game: Boolean) {
        World.game = game
    }

    fun setLevel(level: Int) {
        World.level = level
        LevelController.init(level)
    }

    fun getLevel(): Int {
        return level
    }

    val numLevels: Int
        get() = LevelController.getNumLevels()
    val numSubLevels: Int
        get() = LevelController.getNumSubLevels()

    fun setLevelTransition(levelTransition: Boolean) {
        World.levelTransition = levelTransition
    }

    fun setMasterColor(red: Float, green: Float, blue: Float) {
        masterRed = red
        masterBlue = blue
        masterGreen = green
    }

    fun incrementAssetLoadCount() {
        assetLoaderCounter++
    }

    fun resetAssetLoaderCounter() {
        assetLoaderCounter = 0
    }

    fun resetCheckpoints() {
        latestCheckpoint = Checkpoint.start
    }

    fun incrementSubLevel() {
        subLevel++
    }
}