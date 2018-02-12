package com.scorsi

import com.scorsi.gouttelettes.engine.core.Input
import com.scorsi.gouttelettes.engine.rendering.Window
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE

fun main(vararg arg: String) {
    val window = Window(800, 600)
    val input = Input(window.id)
    while (window.isClosing().not()) {
        window.update()
        if (input.isKeyPressed(GLFW_KEY_ESCAPE))
            window.close()
    }
    window.destroy()
}