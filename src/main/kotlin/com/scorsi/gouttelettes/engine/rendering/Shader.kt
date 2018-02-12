package com.scorsi.gouttelettes.engine.rendering

import com.scorsi.gouttelettes.engine.core.Utils
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryUtil.NULL

class Shader {

    private val id: Int = glCreateProgram()
    private var vertexId: Int? = null
    private var fragId: Int? = null

    private fun attachShader(type: Int, filename: String) {
        fun createShader() = glCreateShader(type).also { shaderId ->
            when (shaderId) {
                NULL.toInt() -> throw Error("Can't load shader $filename")
                else -> {
                    glShaderSource(shaderId, Utils.loadResource("/shaders/" + filename))
                    glCompileShader(shaderId)
                    if (glGetShaderi(shaderId, GL_COMPILE_STATUS) != GL_TRUE)
                        throw Error("Error when compiling shader code: ${glGetShaderInfoLog(shaderId, 1024)}")
                }
            }
        }
        when (type) {
            GL_VERTEX_SHADER -> vertexId = createShader()
            GL_FRAGMENT_SHADER -> fragId = createShader()
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
        glUseProgram(id)
        vertexId?.let { glDeleteShader(it) }
        fragId?.let { glDeleteShader(it) }
    }

    fun use() = glUseProgram(id)
}