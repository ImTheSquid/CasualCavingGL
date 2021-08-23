package org.world

import com.jogamp.newt.event.KeyEvent
import org.engine.AudioManager
import org.engine.Main
import org.graphics.Graphics
import org.graphics.Render
import org.input.Keyboard
import org.input.Mouse
import org.level.LevelController
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import javax.swing.JOptionPane
import kotlin.math.pow

internal object Debug {
    private var show = false
    private var cheatsUsed = false
    private var assetLoadFinished = true

    @JvmStatic
    fun update() {
        if (Keyboard.keys.contains(KeyEvent.VK_F3)) {
            show = !show
            while (Keyboard.keys.contains(KeyEvent.VK_F3)) {
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_V)) {
            Render.enableVsync = !Render.enableVsync
            while (Keyboard.keys.contains(KeyEvent.VK_V)) {
            }
        }
        //Except for when boulder minigame is playing
        if (World.getLevel() == 6 && World.subLevel == 4) return
        if (Keyboard.keys.contains(KeyEvent.VK_L) && World.getLevel() > 0) {
            while (Keyboard.keys.contains(KeyEvent.VK_L)) {
            }
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                val levels = arrayOfNulls<Int>(World.numLevels - 2)
                for (i in levels.indices) {
                    levels[i] = i + 1
                }
                Render.getWindow().title = "Casual Caving - Dialog Open"
                val x = JOptionPane.showInputDialog(
                    null,
                    "Select Level",
                    "Level Selector",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    levels,
                    World.getLevel()
                ) as Int
                if (x != null) {
                    Graphics.scaleFactor = 1f
                    World.subLevel = 0
                    World.setLevel(x)
                    World.clearEntites()
                    AudioManager.handleDebugSwitch(x)
                    Main.getHarold().setFollowCamera(false)
                    if (LevelController.currentLevel.assets != null && LevelController.currentLevel.assets.isNotEmpty()) assetLoadFinished =
                        false
                    if (World.getLevel() > 1) ResourceHandler.getHaroldLoader().state = HaroldLoader.LANTERN
                }
                Render.getWindow().title = "Casual Caving"
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_SEMICOLON) && World.getLevel() > 0) {
            while (Keyboard.keys.contains(KeyEvent.VK_SEMICOLON)) {
            }
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                val sublevels = arrayOfNulls<Int>(World.numSubLevels)
                for (i in sublevels.indices) {
                    sublevels[i] = i
                }
                Render.getWindow().title = "Casual Caving - Dialog Open"
                val x = JOptionPane.showInputDialog(
                    null,
                    "Select Sublevel",
                    "Level Selector",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    sublevels,
                    World.subLevel
                ) as Int
                if (x != null) {
                    Graphics.scaleFactor = 1f
                    World.subLevel = x
                    Main.getHarold().setFollowCamera(false)
                }
                Render.getWindow().title = "Casual Caving"
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_H) && World.getLevel() > 0) {
            while (Keyboard.keys.contains(KeyEvent.VK_H)) {
            }
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Main.getHarold().isInvincible = !Main.getHarold().isInvincible
            }
        }
        checkCam()
    }

    private fun checkCam() {
        if (Keyboard.keys.contains(KeyEvent.VK_UP)) {
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Render.setCameraY(Render.getCameraY() + 1)
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_DOWN)) {
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Render.setCameraY(Render.getCameraY() - 1)
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_LEFT)) {
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Render.setCameraX(Render.getCameraX() - 1)
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_RIGHT)) {
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Render.setCameraX(Render.getCameraX() + 1)
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_R)) {
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Render.setCameraX(0f)
                Render.setCameraY(0f)
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_HOME)) {
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Render.setCameraX(0f)
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_PAGE_DOWN)) {
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Render.setCameraY(0f)
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_END)) {
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Render.setCameraX(Graphics.toWorldWidth(LevelController.currentLevel.backgrounds[World.subLevel].texture.width.toFloat()) - 100)
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_PAGE_UP)) {
            if (!cheatsUsed) cheatsUsed = firstRunEvent()
            if (cheatsUsed) {
                Render.setCameraY(Graphics.toWorldHeight(LevelController.currentLevel.backgrounds[World.subLevel].texture.height.toFloat()) - Render.unitsTall)
            }
        }
    }

    //Returns true if going ahead with cheats, returns false to exit
    private fun firstRunEvent(): Boolean {
        Render.getWindow().title = "Casual Caving - Dialog Open"
        val x = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to enable cheats?\nThe game may become unstable.",
            "Are you sure?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        )
        Render.getWindow().title = "Casual Caving"
        return x != JOptionPane.NO_OPTION
    }

    @JvmStatic
    fun render() {
        if (!assetLoadFinished) {
            if (World.assetLoaderCounter < (if (LevelController.levels[World.getLevel() + 1].assets == null) 0 else LevelController.levels[World.getLevel() + 1].assets.size)) {
                LevelController.levels[World.getLevel() + 1].assets[World.assetLoaderCounter].preloadTexture()
                World.incrementAssetLoadCount()
                World.renderAssetLoadingIndicator(LevelController.levels[World.getLevel() + 1].assets.size)
            } else {
                assetLoadFinished = true
                World.resetAssetLoaderCounter()
            }
        }
        if (!show) return
        Graphics.setFollowCamera(true)
        Graphics.setIgnoreScale(true)
        Graphics.setFont(Graphics.FontType.DEBUG_SMALL)
        Graphics.setDrawColor(.1f, .1f, .1f, .3f)
        Graphics.fillRect(0f, Render.unitsTall - 10f, 20f, 11f)
        val memory = "Memory:" + inUseMemoryMB + "/" + maxMemoryMB + "MB"
        val charHeight = Graphics.toWorldHeight(Graphics.currentFont.getBounds("TEST").height.toFloat())
        val memWidth = Graphics.toWorldHeight(Graphics.currentFont.getBounds(memory).width.toFloat()) - .1f
        Graphics.fillRect(99 - memWidth, Render.unitsTall - charHeight - 1, memWidth + 1, charHeight + 1)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.drawText("FPS: " + Render.getGameLoop().currentFPS, .5f, Render.unitsTall - charHeight - .5f)
        Graphics.drawText(
            "X,Y: " + Math.round(Main.getHarold().x * 100) / 100 + "," + Math.round(Main.getHarold().y * 100) / 100,
            .5f,
            Render.unitsTall - 2 * charHeight - 1
        )
        Graphics.drawText(
            "Lvl,Sublvl: " + World.getLevel() + "," + World.subLevel,
            .5f,
            Render.unitsTall - 3 * charHeight - 1.5f
        )
        Graphics.drawText(
            "Mouse X,Y: " + Math.round(Mouse.getX()) + "," + Math.round(Mouse.getY()),
            .5f,
            Render.unitsTall - 4 * charHeight - 2f
        )
        Graphics.drawText(
            "VSync: " + if (Render.enableVsync) "ENABLED" else "DISABLED",
            .5f,
            Render.unitsTall - 5 * charHeight - 2.5f
        )
        Graphics.drawText(memory, 99.5f - memWidth, Render.unitsTall - charHeight - .5f)
        Graphics.setIgnoreScale(false)
        Graphics.setFollowCamera(false)
    }

    private val inUseMemoryMB: Long
        get() = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024.0.pow(2.0).toLong()
    private val maxMemoryMB: Long
        get() = Runtime.getRuntime().maxMemory() / 1024.0.pow(2.0).toLong()
}