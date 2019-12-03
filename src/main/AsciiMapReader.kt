package main

import java.io.File

object AsciiMapReader {

    fun processAsciiMapFromFile(fileName: String): String {
        val fileString = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
        return fileString
    }
}