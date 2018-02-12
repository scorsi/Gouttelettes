package com.scorsi.gouttelettes.engine.core

import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*

class Input(windowId: Long) {

    private val pressedKeys = BooleanArray(GLFW_KEY_LAST, { false })
    private val pressedMouses = BooleanArray(8, { false })

    fun isKeyPressed(key: Int) = pressedKeys[key]
    fun isMousePressed(button: Int) = pressedMouses[button]

    var mousePosition = Vector3f()
        private set

    var mouseInWindow = false
        private set

    init {
        // Set the cursor position callback
        glfwSetCursorPosCallback(windowId, { _, posX, posY ->
            mousePosition.x = posX.toFloat()
            mousePosition.y = posY.toFloat()
        })
        /** Set the cursor enter callback */
        glfwSetCursorEnterCallback(windowId, { _, entered ->
            mouseInWindow = entered
        })
        // Set the mouse button callback
        glfwSetMouseButtonCallback(windowId, { _, button, action, _ ->
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
        glfwSetKeyCallback(windowId, { _, key, _, action, _ ->
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
}