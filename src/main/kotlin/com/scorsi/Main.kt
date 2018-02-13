package com.scorsi

import com.scorsi.gouttelettes.engine.core.Input
import com.scorsi.gouttelettes.engine.rendering.Camera
import com.scorsi.gouttelettes.engine.rendering.Shader
import com.scorsi.gouttelettes.engine.rendering.Texture
import com.scorsi.gouttelettes.engine.rendering.Window
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.glfw.GLFW.glfwGetTime
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryUtil

fun main(vararg arg: String) {
    val window = Window(800, 600)
    val input = Input(window.id)

    val vertices = floatArrayOf(-0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.5f, 0.5f, -0.5f, 1.0f, 1.0f, -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,

            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f, -0.5f, 0.5f, -0.5f, 1.0f, 1.0f, -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

            0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.5f, -0.5f, 0.5f, 1.0f, 0.0f, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,

            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, -0.5f, 0.5f, -0.5f, 0.0f, 1.0f)
    val cubePositions = arrayOf(
            Vector3f(0f, 0f, 0f),
            Vector3f(2f, 5f, -15f),
            Vector3f(-1.5f, -2.2f, -2.5f),
            Vector3f(-3.8f, -2.0f, -12.3f),
            Vector3f(2.4f, -0.4f, -3.5f),
            Vector3f(-1.7f, 3.0f, -7.5f),
            Vector3f(1.3f, -2.0f, -2.5f),
            Vector3f(1.5f, 2.0f, -2.5f),
            Vector3f(1.5f, 0.2f, -1.5f),
            Vector3f(-1.3f, 1.0f, -1.5f)
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
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0)
    glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 3 * 4)

    glBindVertexArray(0)

    val shader = Shader()
    shader.attachVertexShader("basic")
    shader.attachFragmentShader("basic")
    shader.link()
    shader.use()
    shader.addUniform("sampler1")
    shader.addUniform("sampler2")
    shader.setUniform("sampler1", 0)
    shader.setUniform("sampler2", 1)

    shader.addUniform("model")
    shader.addUniform("view")
    shader.addUniform("projection")

    val texture = Texture("./res/textures/wall.jpg")
    val texture2 = Texture("./res/textures/awesomeface.png")

    val camera = Camera(Vector3f(0f, 0f, 3f))

    glEnable(GL_DEPTH_TEST)

    var lastTime = glfwGetTime().toFloat()
    while (window.isClosing().not()) {
        val actualTime = glfwGetTime().toFloat()
        val deltaTime = actualTime - lastTime
        lastTime = actualTime

        camera.handleInput(input, deltaTime)

        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        glBindVertexArray(vao)
        shader.use()
//        shader.setUniform("view", Matrix4f().translate(0f, 0f, -3f))
        shader.setUniform("view", camera.viewMatrix)
        shader.setUniform("projection", Matrix4f().perspective(Math.toRadians(camera.zoom.toDouble()).toFloat(), window.width / window.height, 0.1f, 100f))

        glActiveTexture(GL_TEXTURE0)
        texture.bind()
        glActiveTexture(GL_TEXTURE1)
        texture2.bind()
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)
        cubePositions.forEach { cubePosition ->
            val model = Matrix4f()
                    .translate(cubePosition)
                    .rotate(glfwGetTime().toFloat(), Vector3f(1f, 0f, 0f))
                    .rotate(glfwGetTime().toFloat(), Vector3f(0f, 1f, 0f))
            shader.setUniform("model", model)
            glDrawArrays(GL_TRIANGLES, 0, 36)
        }
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
        glBindVertexArray(0)

        input.reset()
        window.update()
        if (input.isKeyPressed(GLFW_KEY_ESCAPE))
            window.close()
    }
    glDeleteVertexArrays(vao)
    glDeleteBuffers(vbo)
    texture.delete()
    window.destroy()
}