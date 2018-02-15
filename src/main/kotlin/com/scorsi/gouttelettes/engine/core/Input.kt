package com.scorsi.gouttelettes.engine.core

import com.scorsi.gouttelettes.engine.rendering.Window
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW.*

class Input(private val window: Window) {

    private val keys = IntArray(GLFW_KEY_LAST, { GLFW_RELEASE })
    private val mouses = IntArray(8, { GLFW_RELEASE })

    var mousePosition = Vector2f()
        private set

    var mouseInWindow = false
        private set

    val wheel = Vector2f()

    var mouseLocked = false

    val mouseCenterPosition
        get() = Vector2f(window.width / 2, window.height / 2)

    init {
        // Set the cursor position callback
        glfwSetCursorPosCallback(window.id, { _, posX, posY ->
            mousePosition.x = posX.toFloat()
            mousePosition.y = posY.toFloat()
        })
        // Set the cursor enter callback
        glfwSetCursorEnterCallback(window.id, { _, entered ->
            mouseInWindow = entered
        })
        // Set the mouse wheel callback
        glfwSetScrollCallback(window.id, { _, xOffset, yOffset ->
            wheel.x = xOffset.toFloat()
            wheel.y = yOffset.toFloat()
        })
        // Set the mouse button callback
        glfwSetMouseButtonCallback(window.id, { _, button, action, _ ->
            mouses[button] = action
        })
        // Set the key callback
        glfwSetKeyCallback(window.id, { _, key, _, action, _ ->
            keys[key] = action
        })
    }

    fun isKeyDown(key: Int) = keys[key] == GLFW_REPEAT || keys[key] == GLFW_PRESS
    fun isKeyDownOnce(key: Int) = keys[key] == GLFW_PRESS
    fun isKeyUp(key: Int) = keys[key] == GLFW_RELEASE

    fun isMouseDown(button: Int) = mouses[button] == GLFW_REPEAT || mouses[button] == GLFW_PRESS
    fun isMouseDownOnce(button: Int) = mouses[button] == GLFW_PRESS
    fun isMouseUp(button: Int) = mouses[button] == GLFW_RELEASE

    fun centerCursor() = glfwSetCursorPos(window.id, window.width / 2.0, window.height / 2.0)

    fun setCursorPosition(pos: Vector2f) = glfwSetCursorPos(window.id, pos.x.toDouble(), pos.y.toDouble())

    fun showCursor(value: Boolean) =
            if (value) glfwSetInputMode(window.id, GLFW_CURSOR, GLFW_CURSOR_HIDDEN)
            else glfwSetInputMode(window.id, GLFW_CURSOR, GLFW_CURSOR_NORMAL)

    fun update() {
        if (mouseLocked) centerCursor()
        wheel.x = 0f
        wheel.y = 0f
    }
}