package main

import java.io.File

object AsciiMapReader {

    const val LETTERS_PLACEHOLDER = "Letters: "
    const val PATH_AS_CHARACTERS_PLACEHOLDER = "Path as characters: "

    private const val defaultFilePath = "src/main/map.txt"

    private fun processAsciiMapFromFile(fileName: String) {
        val mapFromFile = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
        val asciiMap = AsciiMap(mapFromFile)
        print("$LETTERS_PLACEHOLDER${asciiMap.getOutput().letters}\n$PATH_AS_CHARACTERS_PLACEHOLDER${asciiMap.getOutput().pathAsCharacters}")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val fileName = if (args.isNotEmpty()) args[0] else defaultFilePath
        processAsciiMapFromFile(fileName)
    }
}