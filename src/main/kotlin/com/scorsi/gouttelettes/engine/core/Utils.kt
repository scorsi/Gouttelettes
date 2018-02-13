package com.scorsi.gouttelettes.engine.core

import java.io.BufferedReader
import java.io.InputStreamReader

object Utils {

    fun loadResource(fileName: String): String =
            BufferedReader(InputStreamReader(javaClass.getResourceAsStream(fileName))).use { reader ->
                reader.readText()
            }

    fun clamp(min: Float, value: Float, max: Float) = if (value < min) min else if (value > max) max else value

}