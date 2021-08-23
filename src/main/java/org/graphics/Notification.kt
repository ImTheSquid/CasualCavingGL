package org.graphics

import org.loader.ImageResource

class Notification : Comparable<Any?> {
    private var title: String
    private var message: String
    private var icon: ImageResource? = null
    private val timedGradient = TimedGradient(0f, 5f, 0f, 1f, 1)
    private var status = 0 //0=out,1=wait,2=in,3=done
    private var x = 100f

    constructor(title: String, message: String, icon: ImageResource?) {
        this.title = title
        this.message = message
        this.icon = icon
        init()
    }

    constructor(title: String, message: String) {
        this.title = title
        this.message = message
        init()
    }

    private fun init() {
        timedGradient.isActive = false
        timedGradient.direction = true
    }

    fun update() {
        when (status) {
            0 -> if (x > 75) {
                x -= 0.5.toFloat()
            } else {
                timedGradient.isActive = true
                status++
            }
            1 -> if (timedGradient.current == timedGradient.max) {
                timedGradient.isActive = false
                status++
            }
            2 -> if (x < 101) {
                x += 0.5.toFloat()
            } else {
                status++
            }
        }
        timedGradient.update()
    }

    fun render(y: Float) {
        Graphics.setFollowCamera(true)
        Graphics.setIgnoreScale(true)
        Graphics.setDrawColor(0.15f, 0.15f, 0.15f, 1f)
        Graphics.fillRect(x, y, width, height)
        Graphics.setDrawColor(1f, 1f, 1f, 1f)
        if (icon != null) Graphics.drawImage(icon!!, x + 1, y + 1, 4f, 4f)
        Graphics.setFont(Graphics.FontType.SMALL_BOLD)
        Graphics.drawText(title, x + 6, y + 4)
        Graphics.setFont(Graphics.FontType.SMALL)
        Graphics.drawText(message, x + 6, y + 2.5f, 20f)
        Graphics.setFollowCamera(false)
        Graphics.setIgnoreScale(true)
    }

    val isDone: Boolean
        get() = status == 3

    //Returns an integer, with values corresponding to the specific difference
    //1=title mismatch, 3=message mismatch, and 5=image mismatch, with various sums also possible
    override fun compareTo(o: Any?): Int {
        val n = o as Notification?
        var ans = 0
        if (title != n!!.title) ans++
        if (message != n.message) ans += 3
        if (icon!!.compareTo(n.icon) != 0) ans += 5
        return ans
    }

    companion object {
        const val width = 25f
        const val height = 6f
    }
}