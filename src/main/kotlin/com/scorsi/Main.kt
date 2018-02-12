package com.scorsi

import com.scorsi.gouttelettes.engine.core.Input
import com.scorsi.gouttelettes.engine.rendering.Shader
import com.scorsi.gouttelettes.engine.rendering.Window
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.glfw.GLFW.glfwGetTime
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryUtil

fun main(vararg arg: String) {
    val window = Window(800, 600)
    val input = Input(window.id)

    val vertices: FloatArray = arrayOf(
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f
    ).toFloatArray()
    val indices: IntArray = arrayOf(
            0, 1, 3,
            1, 2, 3
    ).toIntArray()

    val vao = glGenVertexArrays()
    glBindVertexArray(vao)

    val vbo = glGenBuffers()
    MemoryUtil.memFree(
            MemoryUtil.memAllocFloat(vertices.size).apply {
                put(vertices).flip()
                glBindBuffer(GL_ARRAY_BUFFER, vbo)
                glBufferData(GL_ARRAY_BUFFER, this, GL_STATIC_DRAW)
                glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
            })
    val ebo = glGenBuffers()
    MemoryUtil.memFree(
            MemoryUtil.memAllocInt(indices.size).apply {
                put(indices).flip()
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
                glBufferData(GL_ELEMENT_ARRAY_BUFFER, this, GL_STATIC_DRAW)
                glEnableVertexAttribArray(0)
            })
    glBindVertexArray(0)

    val shader = Shader()
    shader.attachVertexShader("basic")
    shader.attachFragmentShader("basic")
    shader.link()
    shader.addUniform("color")

    while (window.isClosing().not()) {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT)

        shader.use()
        shader.setUnifom("color", Vector3f(0.0f, (Math.sin(glfwGetTime()).toFloat() / 2f) + 0.5f, 0.0f))
        glBindVertexArray(vao)
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
        glBindVertexArray(0)

        window.update()
        if (input.isKeyPressed(GLFW_KEY_ESCAPE))
            window.close()
    }
    glDeleteVertexArrays(vao)
    glDeleteBuffers(vbo)
    glDeleteBuffers(ebo)
    window.destroy()
}