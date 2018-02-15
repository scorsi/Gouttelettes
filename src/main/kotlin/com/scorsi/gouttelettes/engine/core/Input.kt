package com.scorsi.gouttelettes.engine.core

import com.scorsi.gouttelettes.engine.rendering.Window
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW.*

class Input(private val window: Window) {

    private val pressedKeys = BooleanArray(GLFW_KEY_LAST, { false })
    private val pressedMouses = BooleanArray(8, { false })

    fun isKeyPressed(key: Int) = pressedKeys[key]
    fun isMousePressed(button: Int) = pressedMouses[button]

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
            when (action) {
                GLFW_PRESS -> {
                    pressedMouses[button] = true
                }
                GLFW_RELEASE -> {
                    pressedMouses[button] = false
                }
            }
        })
        // Set the key callback
        glfwSetKeyCallback(window.id, { _, key, _, action, _ ->
            when (action) {
                GLFW_PRESS -> {
                    pressedKeys[key] = true
                }
                GLFW_RELEASE -> {
                    pressedKeys[key] = false
                }
            }
        })
    }

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