package org.level.levels

import com.jogamp.newt.event.KeyEvent
import org.engine.AudioManager
import org.engine.Main
import org.entities.SmartRectangle
import org.graphics.Graphics
import org.graphics.Render
import org.graphics.TimedGradient
import org.input.Keyboard
import org.level.Level
import org.level.LevelController
import org.loader.ImageResource
import org.loader.ResourceHandler
import org.loader.harold.HaroldLoader
import org.world.World
import java.net.MalformedURLException
import java.net.URL

class Title(backgrounds: Array<ImageResource>) : Level(backgrounds, backgrounds.size) {
    private val logo = TimedGradient(0F, 1F, 0F, 0.01f, 40)
    private val start = SmartRectangle(Render.unitsWide / 2, 30F, 20F, 7F, true) { doStart() }
    private val restart = SmartRectangle(Render.unitsWide / 2, 23F, 11F, 3F, true) {
        LevelController.resetAll()
        World.clearEntites()
        World.resetCheckpoints()
        doStart()
    }
    private val quit = SmartRectangle(Render.unitsWide / 2, 3.5f, 7F, 4F, true) {
        Render.getGameLoop().setRunning(false)
    }
    private val controls = SmartRectangle(Render.unitsWide / 2, 8F, 12.5f, 3F, true) {
        creditsVisible = false
        controlsVisible = !controlsVisible
    }
    private val music = SmartRectangle(0.5f, 2F, 8F, 8F)
    private val credits = SmartRectangle(89F, 2F, 10F, 3F)
    private val creditFontA = SmartRectangle(44F, 30F, 8F, 1F)
    private val creditFontB = SmartRectangle(55F, 30F, 10F, 1F)
    private var controlsVisible = false
    private var creditsVisible = false
    override fun init() {
        Render.setCameraX(0f)
        Render.setCameraY(0f)
    }

    override val assets: Array<ImageResource>
        get() = arrayOf(ResourceHandler.getLevelLoader().titleLogo)

    override fun update(subLevel: Int, deltaTime: Float) {
        AudioManager.setMusicPlayback(AudioManager.STOP)
        if (subLevel == 0) {
            updateLoad(deltaTime)
        } else {
            updateTitle(deltaTime)
        }
    }

    private fun updateLoad(deltaTime: Float) {
        if (logo.direction || logo.current > 0) {
            logo.update()
        } else {
            World.master.current = 0f
            World.master.isActive = true
            World.subLevel = 1
        }
        if (logo.current == 1f) {
            if (logo.frameDelay == 0f && logo.direction) {
                logo.setFrameDelay(60)
                logo.direction = false
            }
        }
        if (Keyboard.keys.contains(KeyEvent.VK_SPACE)) {
            logo.isActive = false
            logo.current = 0f
            logo.direction = true
            World.master.current = 0f
            World.master.isActive = true
            World.subLevel = 1
        }
    }

    private fun updateTitle(deltaTime: Float) {
        start.isActive = !(controlsVisible || creditsVisible)
        restart.isActive = !(controlsVisible || creditsVisible)
        if (World.latestCheckpoint > World.Checkpoint.start) {
            start.width = 30f
        } else {
            start.width = 20f
        }
        if (World.master.current > 0.25f && (!quit.isActive || !controls.isActive)) {
            quit.isActive = true
            controls.isActive = true
        }
        quit.update(deltaTime)
        if (Keyboard.keys.contains(KeyEvent.VK_ESCAPE)) {
            Render.getGameLoop().setRunning(false)
        }
        start.update(deltaTime)
        if (Keyboard.keys.contains(KeyEvent.VK_ENTER)) {
            doStart()
        }
        restart.update(deltaTime)
        controls.update(deltaTime)
        music.update(deltaTime)
        if (music.isPressed) {
            AudioManager.isMusicEnabled = !AudioManager.isMusicEnabled
            while (music.isPressed) music.update(deltaTime)
        }
        credits.update(deltaTime)
        if (credits.isPressed) {
            controlsVisible = false
            creditsVisible = !creditsVisible
            while (credits.isPressed) credits.update(deltaTime)
        }
        if (creditsVisible) {
            creditFontA.isActive = true
            creditFontB.isActive = true
            creditFontA.update(deltaTime)
            if (creditFontA.isPressed) {
                try {
                    Main.openURL(URL("https://levien.com/type/myfonts/inconsolata.html"))
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                }
                while (creditFontA.isPressed) creditFontA.update(deltaTime)
            }
            creditFontB.update(deltaTime)
            if (creditFontB.isPressed) {
                try {
                    Main.openURL(URL("https://fonts.google.com/specimen/Merriweather"))
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                }
                while (creditFontB.isPressed) creditFontB.update(deltaTime)
            }
        } else {
            creditFontA.isActive = false
            creditFontB.isActive = false
        }
    }

    override fun render(subLevel: Int) {
        when (subLevel) {
            0 -> loadingScreen()
            1 -> titleScreen()
        }
    }

    private fun doStart() {
        ResourceHandler.getHaroldLoader().state = HaroldLoader.NORMAL
        LevelController.resetAll()
        World.clearEntites()
        Main.getHarold().reset()
        World.setGame(true)
        World.master.isActive = false
        World.master.current = 1f
        World.startFromCheckpoint()
    }

    override fun renderForeground(subLevel: Int) {}
    override fun cleanup() {}
    override fun reset() {
        World.setGame(false)
        quit.isActive = false
        controls.isActive = false
        World.master.current = 0f
        World.master.direction = true
        World.master.isActive = true
    }

    private fun loadingScreen() {
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.drawImage(backgrounds[0], 0f, 0f, Render.unitsWide, Render.unitsTall)
        Graphics.setDrawColor(1f, 1f, 1f, logo.current)
        Graphics.drawImageCentered(ResourceHandler.getLevelLoader().titleLogo, Render.unitsWide / 2, 55f)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.setFont(Graphics.FontType.SMALL)
        Graphics.drawText("SPACE to skip", 0.1f, 0.7f)
    }

    private fun titleScreen() {
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.drawImage(backgrounds[1], 0f, 0f)
        start.setColor(0f, 0.5f, 0f, 1f)
        start.render()
        restart.setColor(0.8f, 0f, 0f, 1f)
        restart.render()
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.setFont(Graphics.FontType.TITLE)
        if (World.latestCheckpoint == World.Checkpoint.start)
            Graphics.drawTextCentered("Start", Render.unitsWide / 2, 30f)
        else
            Graphics.drawTextCentered("Resume", Render.unitsWide / 2, 30f)
        quit.setColor(0.5f, 0f, 0f, 1f)
        quit.render()
        controls.setColor(0.8f, 0.74f, 0.03f, 1f)
        controls.render()
        credits.setColor(0.8f, 0.74f, 0.03f, 1f)
        credits.render()
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        Graphics.setFont(Graphics.FontType.NORMAL)
        Graphics.drawTextCentered("Quit", Render.unitsWide / 2, 4f)
        Graphics.drawTextCentered("Controls", Render.unitsWide / 2, 8.3f)
        Graphics.drawTextCentered("Restart", Render.unitsWide / 2, 23f)
        Graphics.drawText("Credits", 89f, 2.4f)
        Graphics.setFont(Graphics.FontType.SMALL)
        Graphics.drawText("Casual Caving 0.5.4", 0.1f, 0.7f)
        Graphics.drawText(
            "Lunan Productions",
            Render.unitsWide - Graphics.toWorldWidth(Graphics.currentFont.getBounds("Lunan Productions").width.toFloat()) - .1f,
            .7f
        )
        Graphics.drawImage(
            ResourceHandler.getMiscLoader().getMusicButton(AudioManager.isMusicEnabled),
            0.5f,
            2f,
            5f,
            5f
        )
        if (controlsVisible) {
            Graphics.setDrawColor(0.3f, 0.3f, 0.3f, .7f)
            Graphics.fillRectCentered(Render.unitsWide / 2, Render.unitsTall / 2, 50f, 35f)
            Graphics.setDrawColor(1f, 1f, 1f, 1f)
            Graphics.setFont(Graphics.FontType.NORMAL)
            Graphics.drawTextCentered("Controls", Render.unitsWide / 2, Render.unitsTall / 2 + 15)
            Graphics.setFont(Graphics.FontType.SMALL)
            Graphics.drawTextCentered("W: Attack (part 2 and above)", Render.unitsWide / 2, Render.unitsTall / 2 + 11)
            Graphics.drawTextCentered("A/D: Left/right", Render.unitsWide / 2, Render.unitsTall / 2 + 8)
            Graphics.drawTextCentered("Space: Jump", Render.unitsWide / 2, Render.unitsTall / 2 + 5)
            Graphics.drawTextCentered("V: Toggle VSync", Render.unitsWide / 2, Render.unitsTall / 2 + 2)
        }
        if (creditsVisible) {
            Graphics.setDrawColor(0.3f, 0.3f, 0.3f, .7f)
            Graphics.fillRectCentered(Render.unitsWide / 2, Render.unitsTall / 2, 50f, 35f)
            Graphics.setDrawColor(1f, 1f, 1f, 1f)
            Graphics.setFont(Graphics.FontType.NORMAL)
            Graphics.drawTextCentered("Credits", Render.unitsWide / 2, Render.unitsTall / 2 + 15)
            Graphics.setFont(Graphics.FontType.SMALL)
            Graphics.drawTextCentered(
                "Programming and Game Design: Jack Hogan",
                Render.unitsWide / 2,
                Render.unitsTall / 2 + 11
            )
            Graphics.drawTextCentered(
                "Artwork and Game Design: Stuart Lunn",
                Render.unitsWide / 2,
                Render.unitsTall / 2 + 8
            )
            Graphics.drawTextCentered("Music: Chris Hall", Render.unitsWide / 2, Render.unitsTall / 2 + 5)
            Graphics.drawTextCentered(
                "Fonts (click): Inconsolata and Merriweather",
                Render.unitsWide / 2,
                Render.unitsTall / 2 + 2
            )
        }
    }

    init {
        super.foregrounds = foregrounds
        logo.isActive = true
    }
}