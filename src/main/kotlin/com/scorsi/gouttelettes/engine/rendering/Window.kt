package com.scorsi.gouttelettes.engine.rendering

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.glViewport
import org.lwjgl.system.MemoryUtil.NULL

class Window {

    companion object {
        const val WIDTH = 800
        const val HEIGHT = 600
    }

    private val windowId: Long

    init {
        // initialize and configure glfw
        glfwInit()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        // create glfw window
        windowId = glfwCreateWindow(WIDTH, HEIGHT, "GuangEngine", NULL, NULL)
        if (windowId == NULL) {
            error("Can't create the window")
            glfwTerminate()
        }
        // create opengl context
        glfwMakeContextCurrent(windowId)
        createCapabilities()
        // set the glfw resize callback on our window
        glfwSetFramebufferSizeCallback(windowId, { _, newWidth, newHeight ->
            glViewport(0, 0, newWidth, newHeight)
        })
    }

    fun getId() = windowId
}