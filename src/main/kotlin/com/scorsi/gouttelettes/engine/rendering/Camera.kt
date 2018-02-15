package com.scorsi.gouttelettes.engine.rendering

import com.scorsi.gouttelettes.engine.core.Input
import com.scorsi.gouttelettes.engine.core.Utils.clamp
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*

class Camera constructor(
        position: Vector3f = Vector3f(0.0f),
        up: Vector3f = Vector3f(0f, 1f, 0f),
        yaw: Float = DEFAULT_YAW,
        pitch: Float = DEFAULT_PITCH
) {

    companion object {
        private const val DEFAULT_YAW = -90.0f
        private const val DEFAULT_PITCH = 0.0f
        private const val DEFAULT_SPEED = 2.5f
        private const val DEFAULT_MOUSE_SENSITIVITY = 0.1f
        private const val DEFAULT_ZOOM = 45.0f
    }

    var position = position
    var front = Vector3f(0.0f, 0.0f, -1.0f)
        private set
    var up = up
        private set
    var right = Vector3f()
        private set
    private val worldUp = up

    var pitch = pitch
        private set
    var yaw = yaw
        private set

    val viewMatrix: Matrix4f
        get() = Matrix4f().lookAt(position, Vector3f().apply { position.add(front, this) }, up)

    var movementSpeed = DEFAULT_SPEED
    var mouseSensitivity = DEFAULT_MOUSE_SENSITIVITY
    var zoom = DEFAULT_ZOOM

    init {
        updateVectors()
    }

    fun handleInput(input: Input, deltaTime: Float) {
        val velocity = movementSpeed * deltaTime
        if (input.isKeyDown(GLFW_KEY_W))
            position.add(Vector3f().apply { front.mul(velocity, this) })
        if (input.isKeyDown(GLFW_KEY_S))
            position.sub(Vector3f().apply { front.mul(velocity, this) })
        if (input.isKeyDown(GLFW_KEY_A))
            position.add(Vector3f().apply { right.mul(velocity, this) })
        if (input.isKeyDown(GLFW_KEY_D))
            position.sub(Vector3f().apply { right.mul(velocity, this) })

        if (zoom in 1.0f..50.0f)
            zoom -= input.wheel.y
        zoom = clamp(1.0f, zoom, 50.0f)

        if (input.isKeyDownOnce(GLFW_KEY_ENTER)) {
            input.mouseLocked = input.mouseLocked.not()
            input.showCursor(input.mouseLocked)
        }

        if (input.mouseLocked) {
            Vector2f().apply {
                input.mousePosition.sub(input.mouseCenterPosition, this)
                yaw += -x * mouseSensitivity
                pitch += clamp(-89f, -y * mouseSensitivity, 89f)
            }
            updateVectors()
        }
    }

    private fun updateVectors() {
        front = Vector3f(
                (Math.cos(Math.toRadians(yaw.toDouble())) * Math.cos(Math.toRadians(pitch.toDouble()))).toFloat(),
                (Math.sin(Math.toRadians(pitch.toDouble()))).toFloat(),
                (Math.sin(Math.toRadians(yaw.toDouble())) * Math.cos(Math.toRadians(pitch.toDouble()))).toFloat())
                .normalize()
        right = Vector3f().apply { front.cross(worldUp, this) }.normalize()
        up = Vector3f().apply { front.cross(right, this) }.normalize()
    }

}