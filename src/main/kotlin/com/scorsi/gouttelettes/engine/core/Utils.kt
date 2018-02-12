package com.scorsi.gouttelettes.engine.core

import java.io.BufferedReader
import java.io.InputStreamReader

object Utils {

    fun loadResource(fileName: String): String =
//            mutableListOf<String>().also { lines ->
            BufferedReader(InputStreamReader(javaClass.getResourceAsStream(fileName))).use { reader ->
                //                    reader.forEachLine { lines.add(it) }
                reader.readText()
            }
//            }.joinToString("\n")

}