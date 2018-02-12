package com.scorsi

import com.scorsi.gouttelettes.engine.core.Input
import com.scorsi.gouttelettes.engine.rendering.Shader
import com.scorsi.gouttelettes.engine.rendering.Texture
import com.scorsi.gouttelettes.engine.rendering.Window
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryUtil

fun main(vararg arg: String) {
    val window = Window(800, 600)
    val input = Input(window.id)

    val vertices = floatArrayOf(
            0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
            0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f,
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f
    )
    val indices: IntArray = intArrayOf(
            0, 1, 3,
            1, 2, 3
    )

    val vao = glGenVertexArrays()
    glBindVertexArray(vao)

    val vbo = glGenBuffers()
    MemoryUtil.memFree(
            MemoryUtil.memAllocFloat(vertices.size).apply {
                put(vertices).flip()
                glBindBuffer(GL_ARRAY_BUFFER, vbo)
                glBufferData(GL_ARRAY_BUFFER, this, GL_STATIC_DRAW)
            })
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0)
    glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * 4, 3 * 4)
    glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * 4, 6 * 4)

    val ebo = glGenBuffers()
    MemoryUtil.memFree(
            MemoryUtil.memAllocInt(indices.size).apply {
                put(indices).flip()
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
                glBufferData(GL_ELEMENT_ARRAY_BUFFER, this, GL_STATIC_DRAW)
            })

    glBindVertexArray(0)

    val shader = Shader()
    shader.attachVertexShader("basic")
    shader.attachFragmentShader("basic")
    shader.link()
    shader.use()
    shader.addUniform("Sampler1")
    shader.addUniform("Sampler2")
    shader.setUniform("Sampler1", 0)
    shader.setUniform("Sampler2", 1)

    val texture = Texture("./res/textures/wall.jpg")
    val texture2 = Texture("./res/textures/awesomeface.png")

    while (window.isClosing().not()) {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT)

        glBindVertexArray(vao)
        shader.use()
        glActiveTexture(GL_TEXTURE0)
        texture.bind()
        glActiveTexture(GL_TEXTURE1)
        texture2.bind()
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
        glBindVertexArray(0)

        window.update()
        if (input.isKeyPressed(GLFW_KEY_ESCAPE))
            window.close()
    }
    glDeleteVertexArrays(vao)
    glDeleteBuffers(vbo)
    glDeleteBuffers(ebo)
    texture.delete()
    window.destroy()
}