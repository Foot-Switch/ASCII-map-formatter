package main

import java.io.File

object AsciiMapFileReader {

    const val LETTERS_PLACEHOLDER = "Letters: "
    const val PATH_AS_CHARACTERS_PLACEHOLDER = "Path as characters: "

    fun readAsciiMapFromFile(fileName: String) {
        val mapFromFile = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
        val asciiMap = AsciiMap(mapFromFile)
        print("$LETTERS_PLACEHOLDER${asciiMap.output.letters}\n$PATH_AS_CHARACTERS_PLACEHOLDER${asciiMap.output.pathAsCharacters}")
    }
}