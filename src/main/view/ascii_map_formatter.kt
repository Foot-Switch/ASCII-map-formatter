package view

import model.AsciiMap
import java.io.File

private const val defaultFilePath = "src/main/ascii_map.txt"

fun main(args: Array<String>) {
    val fileName = if (args.isNotEmpty()) args[0] else defaultFilePath
    val mapFromFile = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
    val asciiMap = AsciiMap(mapFromFile)
    println("Letters: ${asciiMap.getOutput().letters}")
    print("Path as characters: ${asciiMap.getOutput().pathAsCharacters}")
}