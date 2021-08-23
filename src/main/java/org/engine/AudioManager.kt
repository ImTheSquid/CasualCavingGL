package org.engine

import org.loader.AudioResource
import org.world.World
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl

object AudioManager {
    const val MUSIC_VOL = -10f
    const val PLAY = 0
    const val PAUSE = 1
    const val STOP = 2
    const val RESTART = 3
    const val RESUME = 4
    const val OVERWORLD = 0
    private var music: Clip? = null
    private val album = ArrayList<Clip>()
    private var musicPos: Long = 0
    private var musicStat = STOP
    var isMusicEnabled = true
    private var volume: FloatControl? = null
    private var currentTrack = 0
    private val musicDirectory = arrayOf(intArrayOf(1, 2))

    @JvmStatic
    fun setup() {
        loadMusic()
    }

    private fun loadMusic() {
        album.add(AudioResource("/CasualCaving/Audio/Overworld.wav").clip)
    }

    fun setMusic(clip: Int) {
        if (clip > album.size - 1) return
        currentTrack = clip
        music = album[clip]
        music!!.loop(-1)
    }

    fun setMusicGain(gain: Float) {
        if (music == null) return
        if (volume == null) volume = music!!.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
        if (gain > volume!!.maximum || gain < volume!!.minimum) return
        volume!!.value = gain
    }

    fun setMusicPlayback(type: Int) {
        if (music == null) return
        if (!isMusicEnabled) {
            music!!.stop()
            return
        }
        when (type) {
            PLAY -> {
                music!!.start()
                musicStat = PLAY
            }
            PAUSE -> {
                musicPos = music!!.microsecondPosition
                music!!.stop()
                musicStat = PAUSE
            }
            STOP -> {
                musicPos = 0
                music!!.stop()
                music!!.microsecondPosition = 0
                musicStat = STOP
            }
            RESTART -> {
                music!!.stop()
                music!!.microsecondPosition = 0
                musicPos = 0
                music!!.start()
                musicStat = PLAY
                if (musicStat == PLAY) return
                music!!.microsecondPosition = musicPos
                music!!.start()
                musicStat = PLAY
            }
            RESUME -> {
                if (musicStat == PLAY) return
                music!!.microsecondPosition = musicPos
                music!!.start()
                musicStat = PLAY
            }
        }
    }

    @JvmStatic
    fun cleanup() {
        if (music != null) music!!.close()
        for (c in album) c.close()
    }

    @JvmStatic
    fun resetGame() {
        setMusicPlayback(STOP)
        setMusicGain(MUSIC_VOL)
        setMusic(OVERWORLD)
    }

    fun handlePause(pause: Boolean) {
        if (musicStat == STOP) return
        if (pause && musicStat == PLAY) setMusicPlayback(PAUSE) else if (!pause && musicStat == PAUSE) setMusicPlayback(
            PLAY
        )
    }

    fun fadeOut() {
        val x = -80 + Math.abs(volume!!.minimum - MUSIC_VOL) * World.master.current
        setMusicGain(x)
        if (x == -80f) setMusicPlayback(PAUSE)
    }

    fun handleLevelTransition(nextLevel: Int) {
        setMusicGain(MUSIC_VOL)
        val next = findNextTrack(nextLevel)
        if (next == -1) setMusicPlayback(STOP) else {
            if (next != currentTrack) {
                setMusicPlayback(STOP)
                setMusic(next)
                setMusicPlayback(RESTART)
            } else setMusicPlayback(PLAY)
        }
    }

    private fun findNextTrack(nextLevel: Int): Int {
        for (i in musicDirectory.indices) {
            for (level in musicDirectory[i]) {
                if (nextLevel == level) {
                    return i
                }
            }
        }
        return -1
    }

    fun handleDebugSwitch(nextLevel: Int) {
        handleLevelTransition(nextLevel)
    }
}