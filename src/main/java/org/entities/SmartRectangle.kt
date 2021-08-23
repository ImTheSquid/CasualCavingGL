package org.entities

import org.graphics.Graphics.fillRect
import org.graphics.Graphics.setDrawColor
import org.input.Mouse

open class SmartRectangle(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    centered: Boolean = false,
    onPress: (() -> Unit)? = null
) : Entity() {
    var isPressed = false
        private set
    var isActive = true
    var isHovering = false
        private set
    private var isCentered = false
    private var originX: Float = x
    private var originY: Float = y
    private val onPress: (() -> Unit)?
    private var onPressTriggered = false

    init {
        if (centered) {
            this.x = x - width / 2
            this.y = y - height / 2
            isCentered = true
        } else {
            this.x = x
            this.y = y
        }
        this.width = width
        this.height = height
        this.onPress = onPress
    }

    fun updateBounds(x: Float, y: Float, width: Float, height: Float) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    override fun update(deltaTime: Float) {
        if (isActive) {
            isPressed = contains(Mouse.getX(), Mouse.getY()) && Mouse.isMousePressed()
            isHovering = contains(Mouse.getX(), Mouse.getY())
        } else {
            isPressed = false
            onPressTriggered = false
            isHovering = false
        }
        if (isPressed && !onPressTriggered) {
            onPress?.invoke()
            onPressTriggered = true
        }
    }

    fun contains(x1: Float, y1: Float): Boolean {
        return y1 >= y && y1 <= y + height && x1 >= x && x1 <= x + width
    }

    fun intersects(r: SmartRectangle): Boolean {
        return x < r.getX() + r.getWidth() && x + width > r.getX() && y < r.getY() + r.getHeight() && y + height > r.getY()
    }

    override fun render() {
        setDrawColor(red, green, blue, alpha)
        fillRect(x, y, width, height)
    }

    override fun reset() {}
    fun setColor(r: Float, g: Float, b: Float, a: Float) {
        red = r
        green = g
        blue = b
        alpha = a
    }

    override fun setWidth(width: Float) {
        super.setWidth(width)
        if (isCentered) x = originX - width / 2
    }
}