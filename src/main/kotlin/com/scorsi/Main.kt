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

    val vertices = floatArrayOf(
            // positions          // normals           // texture coords
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f, 0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f, -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,

            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,

            -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f,

            0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,

            -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,

            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f)
    val cubePositions = arrayOf(
            Vector3f(0f, 0f, 0f)
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

    glBindVertexArray(0)

    val shader = Shader().apply {
        attachVertexShader("basic")
        attachFragmentShader("basic")
        link()
        use()

        addUniform("model")
        addUniform("view")
        addUniform("projection")

        addUniform("viewPos")

        addUniform("material.diffuse")
        setUniform("material.diffuse", 0)
        addUniform("material.specular")
        setUniform("material.specular", 1)
        addUniform("material.shininess")

        addUniform("light.position")
        addUniform("light.ambient")
        addUniform("light.diffuse")
        addUniform("light.specular")
    }
    val lightShader = Shader().apply {
        attachVertexShader("light")
        attachFragmentShader("light")
        link()
        use()

        addUniform("model")
        addUniform("view")
        addUniform("projection")
    }
    val lightPos = Vector3f(1.2f, 1f, 2f)


    val diffuseTexture = Texture("./res/textures/container2.png")
    val specularTexture = Texture("./res/textures/container2_specular.png")

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
        shader.apply {
            use()
            setUniform("view", camera.viewMatrix)
            setUniform("projection", Matrix4f().perspective(Math.toRadians(camera.zoom.toDouble()).toFloat(), window.width / window.height, 0.1f, 100f))
            setUniform("viewPos", camera.position)

            setUniform("material.shininess", 32f)

            setUniform("light.position", lightPos)
            setUniform("light.ambient", Vector3f(0.2f, 0.2f, 0.2f))
            setUniform("light.diffuse", Vector3f(0.5f, 0.5f, 0.5f))
            setUniform("light.specular", Vector3f(1f, 1f, 1f))
        }

        glActiveTexture(GL_TEXTURE0)
        diffuseTexture.bind()
        glActiveTexture(GL_TEXTURE1)
        specularTexture.bind()

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)
        cubePositions.forEach { cubePosition ->
            shader.setUniform("model", Matrix4f().translate(cubePosition))
            glDrawArrays(GL_TRIANGLES, 0, 36)
        }

        lightShader.apply {
            use()
            setUniform("model", Matrix4f().translate(lightPos).scale(0.2f))
            setUniform("view", camera.viewMatrix)
            setUniform("projection", Matrix4f().perspective(Math.toRadians(camera.zoom.toDouble()).toFloat(), window.width / window.height, 0.1f, 100f))
        }
        glDrawArrays(GL_TRIANGLES, 0, 36)

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
//    texture.delete()
//    texture2.delete()
    window.destroy()
}