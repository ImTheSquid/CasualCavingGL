package org.graphics

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL2
import com.jogamp.opengl.util.GLBuffers
import com.jogamp.opengl.util.awt.TextRenderer
import org.loader.ImageResource
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import kotlin.experimental.and

object Graphics {
    private var red = 0f
    private var green = 0f
    private var blue = 0f
    private var alpha = 0f
    private var rotation = 0f

    var scaleFactor = 1f
    private var textSelector = FontType.NORMAL
    private var ignoreScale = false
    private var followCamera = false
    private var screenshotOut: File? = null
    private var screenshotDir: File? = null
    private val title = TextRenderer(Font("Merriweather", Font.PLAIN, 96), true, true)
    private val regular = TextRenderer(Font("Merriweather", Font.PLAIN, 36), true, true)
    private val small = TextRenderer(Font("Merriweather", Font.PLAIN, 18), true, true)
    private val smallBold = TextRenderer(Font("Merriweather", Font.BOLD, 18), true, true)
    private val debugSmall = TextRenderer(Font("Inconsolata", Font.PLAIN, 22), true, true)
    private val fontArr = arrayOf(title, regular, small, smallBold, debugSmall)
    private val fonts = FontType.values().zip(fontArr).toMap()

    fun fillRectCentered(x: Float, y: Float, width: Float, height: Float) {
        fillRect(x - width / 2f, y - height / 2f, width, height)
    }

    fun fillRect(x: Float, y: Float, width: Float, height: Float) {
        var x = x
        var y = y
        val scaleSave = scaleFactor
        if (ignoreScale) {
            scaleFactor = 1f
        }
        if (followCamera) {
            x += Render.getCameraX()
            y += Render.getCameraY()
        }
        val gl = Render.getGL2()
        gl.glTranslatef(x + width / 2, y + height / 2, 0f)
        gl.glRotatef(-rotation, 0f, 0f, 1f)
        gl.glTranslatef(-(x + width / 2), -(y + height / 2), 0f)
        gl.glColor4f(red, green, blue, alpha)
        gl.glBegin(GL2.GL_QUADS)
        gl.glVertex2f(x, y)
        gl.glVertex2f(x + width * scaleFactor, y)
        gl.glVertex2f(x + width * scaleFactor, y + height * scaleFactor)
        gl.glVertex2f(x, y + height * scaleFactor)
        gl.glEnd()
        gl.glFlush()
        gl.glTranslatef(x + width / 2, y + height / 2, 0f)
        gl.glRotatef(rotation, 0f, 0f, 1f)
        gl.glTranslatef(-(x + width / 2), -(y + height / 2), 0f)
        scaleFactor = scaleSave
    }

    //TODO Add scaling support to draw and fill circle methods
    fun drawCircle(x: Float, y: Float, radius: Float) {
        val DEG2RAD = Math.PI.toFloat() / 180
        val gl = Render.getGL2()
        gl.glBegin(GL.GL_LINE_LOOP)
        for (i in 0..359) {
            val degInRad = i * DEG2RAD
            gl.glVertex2f(
                x + Math.cos(degInRad.toDouble()).toFloat() * radius,
                y + StrictMath.sin(degInRad.toDouble()).toFloat() * radius
            )
        }
        gl.glEnd()
    }

    fun fillCircle(x: Float, y: Float, radius: Float) {
        val gl = Render.getGL2()
        gl.glBegin(GL2.GL_POLYGON)
        var angle1 = 0.0
        gl.glVertex2d(x + radius * Math.cos(0.0), y + radius * StrictMath.sin(0.0))
        var i: Int
        i = 0
        while (i < 360) {
            gl.glVertex2d(x + radius * Math.cos(angle1), y + radius * StrictMath.sin(angle1))
            angle1 += 2 * Math.PI / 360
            i++
        }
        gl.glEnd()
        gl.glFlush()
    }

    fun toWorldHeight(height: Float): Float {
        return height / (Render.getWindow().height / Render.unitsTall)
    }

    fun toWorldWidth(width: Float): Float {
        return width / (Render.getWindow().width / Render.unitsWide)
    }

    fun fromWorldWidth(width: Float): Float {
        return Render.getWindow().width * width / Render.unitsWide
    }

    fun fromWorldHeight(height: Float): Float {
        return Render.getWindow().height * height / Render.unitsTall
    }

    fun toWorldY(y: Float): Float {
        return y / (Render.getWindow().width / Render.unitsWide)
    }

    fun toWorldX(x: Float): Float {
        return x / (Render.getWindow().width / Render.unitsWide)
    }

    private fun fromWorldX(x: Float): Float {
        return Render.getWindow().width * x / Render.unitsWide
    }

    private fun fromWorldY(y: Float): Float {
        return Render.getWindow().height * y / Render.unitsTall
    }

    //Draw centered image
    fun drawImageCentered(image: ImageResource, x: Float, y: Float) {
        drawImage(
            image,
            x - toWorldWidth(image.texture.width.toFloat()) / 2f,
            y - toWorldHeight(image.texture.width.toFloat()) / 2f
        )
    }

    //Draw centered image with preselected width and height
    fun drawImageCentered(image: ImageResource, x: Float, y: Float, width: Float, height: Float) {
        drawImage(image, x - width / 2f, y - height / 2f, width, height)
    }

    //Draws an image
    //Draw an image with width and height of original image
    @JvmOverloads
    fun drawImage(
        image: ImageResource,
        x: Float,
        y: Float,
        width: Float = toWorldWidth(image.texture.width.toFloat()),
        height: Float = toWorldHeight(image.texture.height.toFloat())
    ) {
        var x = x
        var y = y
        val scaleSave = scaleFactor
        if (ignoreScale) {
            scaleFactor = 1f
        }
        if (followCamera) {
            x += Render.getCameraX()
            y += Render.getCameraY()
        }
        val gl = Render.getGL2()
        gl.glTranslatef(x + width / 2, y + height / 2, 0f)
        gl.glRotatef(-rotation, 0f, 0f, 1f)
        gl.glTranslatef(-(x + width / 2), -(y + height / 2), 0f)
        val tex = image.texture
        if (tex != null) gl.glBindTexture(GL2.GL_TEXTURE_2D, tex.textureObject)
        gl.glColor4f(red, green, blue, alpha)
        gl.glBegin(GL2.GL_QUADS)
        gl.glTexCoord2f(0f, 1f)
        gl.glVertex2f(x, y)
        gl.glTexCoord2f(1f, 1f)
        gl.glVertex2f(x + width * scaleFactor, y)
        gl.glTexCoord2f(1f, 0f)
        gl.glVertex2f(x + width * scaleFactor, y + height * scaleFactor)
        gl.glTexCoord2f(0f, 0f)
        gl.glVertex2f(x, y + height * scaleFactor)
        gl.glEnd()
        gl.glFlush()
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0)
        gl.glTranslatef(x + width / 2, y + height / 2, 0f)
        gl.glRotatef(rotation, 0f, 0f, 1f)
        gl.glTranslatef(-(x + width / 2), -(y + height / 2), 0f)
        scaleFactor = scaleSave
    }

    //Draws text with specified wrapping width
    @JvmOverloads
    fun drawText(text: String, x: Float, y: Float, wrapWidth: Float, box: Boolean = false) {
        var currentString = StringBuilder()
        val strings = ArrayList<String>()
        val lines = ArrayList<Int>() //Lines to remove a character from in the front of the string
        lines.add(0)
        var newlineActivated = false
        var i = 0
        while (i < text.length) {
            val testString = StringBuilder()
            var s: String
            if (text[i] == ' ' || i == 0 || newlineActivated) {
                s = if (i == 0 || newlineActivated) text.substring(i) else text.substring(i + 1)
                var index = 0
                while (index < s.length && s[index] != ' ') {
                    testString.append(s[index])
                    index++
                }
                //TODO Fix bug with strings with no spaces not getting split properly
                val widthTest = toWorldWidth(
                    (fonts[textSelector]!!.getBounds(testString.toString()).width + fonts[textSelector]!!.getBounds(
                        currentString.toString()
                    ).width).toFloat()
                )
                if (widthTest < wrapWidth) {
                    currentString.append(" ").append(testString)
                } else {
                    strings.add(currentString.toString())
                    currentString = testString
                }
                newlineActivated = false
            } else if (i + 1 < text.length && text.substring(
                    i,
                    i + 2
                ) == "\\n"
            ) { //Deals with newline character (\\n) and adds following string to another line
                strings.add(currentString.substring(0, i + 1))
                currentString = StringBuilder()
                i += 1
                newlineActivated = true
                lines.add(strings.size)
            }
            i++
        }
        strings.add(currentString.toString()) //Clears buffer after loop
        for (i in lines) {
            if (strings[i].length > 0) strings[i] = strings[i].substring(1)
        } //Gets rid of space at beginning of first line
        //Draw the text in the array
        if (box) {
            var lengthMax = 0f
            for (s in strings) {
                val newL = toWorldWidth(fonts[textSelector]!!.getBounds(s).width.toFloat())
                lengthMax = Math.max(lengthMax, newL)
            }
            setIgnoreScale(true)
            setDrawColor(0f, 0f, 0f, 0.5f)
            val yAdjustment = toWorldHeight(fonts[textSelector]!!.getBounds(text).height.toFloat() * (strings.size - 1))
            fillRect(
                x,
                y - yAdjustment,
                lengthMax,
                toWorldHeight(fonts[textSelector]!!.getBounds(text).height.toFloat() * strings.size)
            )
            setDrawColor(1f, 1f, 1f, 1f)
            setIgnoreScale(false)
        }
        for ((iteration, s) in strings.withIndex()) {
            drawText(s, x, y - toWorldY((fonts[textSelector]!!.font.size * iteration).toFloat()))
        }
    }

    fun drawTextCentered(text: String?, x: Float, y: Float) {
        drawText(
            text,
            x - toWorldWidth(fonts[textSelector]!!.getBounds(text).width.toFloat()) / 2f,
            y - toWorldHeight(fonts[textSelector]!!.getBounds(text).height.toFloat()) / 2f
        )
    }

    //Draws text
    fun drawText(text: String?, x: Float, y: Float) {
        var x = x
        var y = y
        if (!followCamera) {
            x -= Render.getCameraX()
            y -= Render.getCameraY()
        }
        fonts[textSelector]!!.beginRendering(Render.getWindow().width, Render.getWindow().height)
        fonts[textSelector]!!.setColor(red, green, blue, alpha)
        fonts[textSelector]!!.draw(text, fromWorldX(x).toInt(), fromWorldY(y).toInt())
        fonts[textSelector]!!.endRendering()
    }

    fun drawTextWithBox(text: String?, x: Float, y: Float) {
        setDrawColor(0f, 0f, 0f, 0.5f)
        val bounds = fonts[textSelector]!!.getBounds(text)
        fillRect(x, y - 0.1f, toWorldWidth(bounds.width.toFloat()), toWorldHeight(bounds.height.toFloat()) + 0.1f)
        setDrawColor(1f, 1f, 1f, 1f)
        drawText(text, x, y)
    }

    fun takeScreenshot() {
        val gl = Render.getGL2()
        //Prepare an image object and read pixels from the currently bound texture
        val screenshot = BufferedImage(Render.virtual_width, Render.virtual_height, BufferedImage.TYPE_INT_RGB)
        val graphics = screenshot.graphics
        val buffer =
            GLBuffers.newDirectByteBuffer(Render.virtual_width * Render.virtual_height * 4) //Creates byte buffer with a size of the screen with 4 color slots each (RGBA)
        gl.glReadBuffer(GL.GL_BACK)
        gl.glReadPixels(0, 0, Render.virtual_width, Render.virtual_height, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buffer)
        for (h in 0 until Render.virtual_height) {
            for (w in 0 until Render.virtual_width) {
                graphics.color = Color(
                    (buffer.get() and 0xff.toByte()).toInt(),
                    (buffer.get() and 0xff.toByte()).toInt(),
                    (buffer.get() and 0xff.toByte()).toInt()
                )
                buffer.get() //Discard alpha
                graphics.drawRect(w, Render.virtual_height - h, 1, 1) //Fill one pixel of output image
            }
        }
        //Clear the buffer
        while (buffer.hasRemaining()) buffer.get()
        //Format and save the image
        val dmy = Instant.now().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).toString()
        var hms =
            Instant.now().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS).toString().replace(':', '-')
        hms = hms.substring(hms.indexOf("T") + 1, hms.indexOf("["))
        if (screenshotOut == null) {
            screenshotDir = selectDirectory()
        }
        screenshotOut = File(
            screenshotDir!!.path + "\\screenshot-" + dmy.substring(0, dmy.indexOf("T")) + "_" + hms.substring(
                0,
                hms.length - 6
            ) + ".png"
        )
        try {
            ImageIO.write(screenshot, "png", screenshotOut)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun selectDirectory(): File {
        val chooser = JFileChooser()
        chooser.dialogTitle = "Select screenshot output path (persistent)"
        chooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        chooser.showOpenDialog(null)
        return chooser.selectedFile
    }

    fun setDrawColor(red: Float, green: Float, blue: Float, alpha: Float) {
        Graphics.red = red
        Graphics.green = green
        Graphics.blue = blue
        Graphics.alpha = alpha
    }

    fun setRotation(rotation: Float) {
        Graphics.rotation = rotation
    }

    fun setFont(text: FontType) {
        textSelector = text
    }

    val currentFont: TextRenderer
        get() = fonts[textSelector]!!

    fun setIgnoreScale(ignore: Boolean) {
        ignoreScale = ignore
    }

    fun setFollowCamera(followCamera: Boolean) {
        Graphics.followCamera = followCamera
    }

    //Font vars
    enum class FontType {
        TITLE, NORMAL, SMALL, DEBUG_SMALL, SMALL_BOLD
    }
}