package com.scorsi.gouttelettes.engine.rendering

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER
import org.lwjgl.opengl.GL30.glGenerateMipmap
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack

class Texture(filename: String) {

    val id: Int = glGenTextures()
    var width: Int = -1
        private set
    var height: Int = -1
        private set

    init {
        val data = MemoryStack.stackPush().use { stack ->
            val w = stack.mallocInt(1)
            val h = stack.mallocInt(1)
            val chan = stack.mallocInt(1)

            stbi_set_flip_vertically_on_load(true)
            stbi_load(filename, w, h, chan, 3).let {
                when (it) {
                    null -> throw Error("Failed to load a texture file: ${stbi_failure_reason()}")
                    else -> {
                        width = w.get()
                        height = h.get()
                        it
                    }
                }
            }
        }
        bind()
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, data)
        glGenerateMipmap(GL_TEXTURE_2D)
        stbi_image_free(data)
    }

    fun bind() = glBindTexture(GL_TEXTURE_2D, id)
    fun delete() = glDeleteTextures(id)
}