package com.scorsi.gouttelettes.engine.rendering

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.glViewport
import org.lwjgl.system.MemoryUtil.NULL

class Window(width: Int, height: Int) {

    var width: Int = width
        private set
    var height: Int = height
        private set
    val id: Long

    fun update() {
        glfwSwapBuffers(id)
        glfwPollEvents()
    }

    fun destroy() {
        glfwDestroyWindow(id)
        glfwTerminate()
    }

    fun close() {
        glfwSetWindowShouldClose(id, true)
    }

    fun isClosing() = glfwWindowShouldClose(id)

    init {
        // initialize and configure glfw
        glfwInit()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        // create glfw window
        id = glfwCreateWindow(width, height, "GouttelettesEngine", NULL, NULL)
        if (id == NULL) {
            throw Error("Can't create the window")
            glfwTerminate()
        }
        // create opengl context
        glfwMakeContextCurrent(id)
        createCapabilities()
        // set the glfw resize callback on our window
        glfwSetFramebufferSizeCallback(id, { _, newWidth, newHeight ->
            this.width = newWidth
            this.height = newHeight
            glViewport(0, 0, newWidth, newHeight)
        })
    }
}