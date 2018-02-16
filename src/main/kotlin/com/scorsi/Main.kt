package com.scorsi

import com.scorsi.gouttelettes.engine.core.Input
import com.scorsi.gouttelettes.engine.rendering.Camera
import com.scorsi.gouttelettes.engine.rendering.Shader
import com.scorsi.gouttelettes.engine.rendering.Texture
import com.scorsi.gouttelettes.engine.rendering.Window
import org.joml.AxisAngle4f
import org.joml.Matrix4f
import org.joml.Quaternionf
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
    val input = Input(window)

    val vertices = floatArrayOf(
            // positions          // normals           // texture coords
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f, 0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f, -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,

            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,

            -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f,

            0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,

            -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,

            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f)
    val cubePositions = arrayOf(
            Vector3f(0f, 0f, 0f),
            Vector3f(2.0f, 5.0f, -15.0f),
            Vector3f(-1.5f, -2.2f, -2.5f),
            Vector3f(-3.8f, -2.0f, -12.3f),
            Vector3f(2.4f, -0.4f, -3.5f),
            Vector3f(-1.7f, 3.0f, -7.5f),
            Vector3f(1.3f, -2.0f, -2.5f),
            Vector3f(1.5f, 2.0f, -2.5f),
            Vector3f(1.5f, 0.2f, -1.5f),
            Vector3f(-1.3f, 1.0f, -1.5f)
    )

    val pointLightPositions = arrayOf(
            Vector3f(0.7f, 0.2f, 2.0f),
            Vector3f(2.3f, -3.3f, -4.0f),
            Vector3f(-4.0f, 2.0f, -12.0f),
            Vector3f(0.0f, 0.0f, -3.0f)
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

        addUniform("dirLight.direction")
        addUniform("dirLight.ambient")
        addUniform("dirLight.diffuse")
        addUniform("dirLight.specular")

        addUniform("spotLight.position")
        addUniform("spotLight.direction")
        addUniform("spotLight.cutOff")
        addUniform("spotLight.outerCutOff")
        addUniform("spotLight.constant")
        addUniform("spotLight.linear")
        addUniform("spotLight.quadratic")
        addUniform("spotLight.ambient")
        addUniform("spotLight.diffuse")
        addUniform("spotLight.specular")

        (0 until pointLightPositions.size).forEach { i ->
            addUniform("pointLights[$i].position")
            addUniform("pointLights[$i].constant")
            addUniform("pointLights[$i].linear")
            addUniform("pointLights[$i].quadratic")
            addUniform("pointLights[$i].ambient")
            addUniform("pointLights[$i].diffuse")
            addUniform("pointLights[$i].specular")
        }

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

        glClearColor(0f, 0f, 0f, 0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        glBindVertexArray(vao)
        shader.apply {
            use()
            setUniform("view", camera.viewMatrix)
            setUniform("projection", Matrix4f().perspective(Math.toRadians(camera.zoom.toDouble()).toFloat(), window.width / window.height, 0.1f, 100f))
            setUniform("viewPos", camera.position)

            setUniform("material.shininess", 32f)

            setUniform("dirLight.direction", Vector3f(-0.2f, -1f, -0.3f))
            setUniform("dirLight.ambient", Vector3f(0.05f))
            setUniform("dirLight.diffuse", Vector3f(0.4f))
            setUniform("dirLight.specular", Vector3f(0.5f))

            (0 until pointLightPositions.size).forEach { i ->
                setUniform("pointLights[$i].position", pointLightPositions[i])
                setUniform("pointLights[$i].ambient", Vector3f(0.05f))
                setUniform("pointLights[$i].diffuse", Vector3f(0.8f))
                setUniform("pointLights[$i].specular", Vector3f(1f))
                setUniform("pointLights[$i].constant", 1f)
                setUniform("pointLights[$i].linear", 0.09f)
                setUniform("pointLights[$i].quadratic", 0.032f)
            }

            setUniform("spotLight.position", camera.position)
            setUniform("spotLight.direction", camera.front)
            setUniform("spotLight.ambient", Vector3f(0f))
            setUniform("spotLight.diffuse", Vector3f(1f))
            setUniform("spotLight.specular", Vector3f(1f))
            setUniform("spotLight.constant", 1f)
            setUniform("spotLight.linear", 0.09f)
            setUniform("spotLight.quadratic", 0.032f)
            setUniform("spotLight.cutOff", Math.cos(Math.toRadians(12.5)).toFloat())
            setUniform("spotLight.outerCutOff", Math.cos(Math.toRadians(15.0)).toFloat())
        }

        glActiveTexture(GL_TEXTURE0)
        diffuseTexture.bind()
        glActiveTexture(GL_TEXTURE1)
        specularTexture.bind()

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)
        cubePositions.forEachIndexed { index, cubePosition ->
            shader.setUniform("model", Matrix4f().translationRotateScale(cubePosition, Quaternionf(AxisAngle4f(Math.toRadians((20f * index).toDouble()).toFloat(), Vector3f(1f, 0.3f, 0.5f).normalize())), Vector3f(1f)))
            glDrawArrays(GL_TRIANGLES, 0, 36)
        }

        lightShader.apply {
            use()

            setUniform("view", camera.viewMatrix)
            setUniform("projection", Matrix4f().perspective(Math.toRadians(camera.zoom.toDouble()).toFloat(), window.width / window.height, 0.1f, 100f))
        }
        pointLightPositions.forEach { lightPosition ->
            lightShader.setUniform("model", Matrix4f().translate(lightPosition).scale(0.2f))
            glDrawArrays(GL_TRIANGLES, 0, 36)
        }

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
        glBindVertexArray(0)

        input.update()
        window.update()
        Thread.sleep(10) // TODO: Remove this
        if (input.isKeyDown(GLFW_KEY_ESCAPE))
            window.close()
    }
    glDeleteVertexArrays(vao)
    glDeleteBuffers(vbo)
//    texture.delete()
//    texture2.delete()
    window.destroy()
}