package com.scorsi.gouttelettes.engine.rendering

import com.scorsi.gouttelettes.engine.core.Utils
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.opengl.GL11.GL_INVALID_VALUE
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.NULL

class Shader {

    private val id: Int = glCreateProgram()
    private var vertexId: Int? = null
    private var fragId: Int? = null
    private val uniforms: MutableMap<String, Int> = mutableMapOf()

    private fun attachShader(type: Int, filename: String) {
        (fun() = glCreateShader(type).also { shaderId ->
            when (shaderId) {
                NULL.toInt() -> throw Error("Can't load shader $filename")
                else -> {
                    glShaderSource(shaderId, Utils.loadResource("/shaders/" + filename))
                    glCompileShader(shaderId)
                    if (glGetShaderi(shaderId, GL_COMPILE_STATUS) != GL_TRUE)
                        throw Error("Error when compiling shader code: ${glGetShaderInfoLog(shaderId, 1024)}")
                }
            }
        }).let { createShader ->
            when (type) {
                GL_VERTEX_SHADER -> vertexId = createShader()
                GL_FRAGMENT_SHADER -> fragId = createShader()
            }
        }
    }

    fun attachVertexShader(filename: String) = attachShader(GL_VERTEX_SHADER, filename + ".vert")
    fun attachFragmentShader(filename: String) = attachShader(GL_FRAGMENT_SHADER, filename + ".frag")

    fun link() {
        vertexId?.let { glAttachShader(id, it) }
        fragId?.let { glAttachShader(id, it) }
        glLinkProgram(id)
        if (glGetProgrami(id, GL_LINK_STATUS) != GL_TRUE)
            throw Error("Error when linking shaders: ${glGetProgramInfoLog(id, 1024)}")
        vertexId?.let { glDeleteShader(it) }
        fragId?.let { glDeleteShader(it) }
    }

    fun use() = glUseProgram(id)

    fun addUniform(location: String) {
        glGetUniformLocation(id, location).also {
            when (it) {
                -1 -> throw Error("Can't find the uniform $location in the shader program.")
                else -> uniforms[location] = it
            }
        }
    }

    fun setUniform(location: String, value: Int) {
        glUniform1i(uniforms[location]!!, value)
    }

    fun setUniform(location: String, value: Float) {
        glUniform1f(uniforms[location]!!, value)
    }

    fun setUniform(location: String, value: Vector2f) {
        MemoryStack.stackPush().use {
            glUniform2fv(uniforms[location]!!, it.mallocFloat(2).apply {
                value.get(this)
            })
        }
    }

    fun setUniform(location: String, value: Vector3f) {
        MemoryStack.stackPush().use {
            glUniform3fv(uniforms[location]!!, it.mallocFloat(3).apply {
                value.get(this)
            })
        }
    }

    fun setUniform(location: String, value: Matrix4f) {
        MemoryStack.stackPush().use {
            glUniformMatrix4fv(uniforms[location]!!, false, it.mallocFloat(4 * 4).apply {
                value.get(this)
            })
        }
    }
}