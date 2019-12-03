package main

private const val defaultFilePath = "src/main/map.txt"

fun main(args: Array<String>) {
    val fileName = if (args.isNotEmpty()) args[0] else defaultFilePath
    AsciiMapFileReader.readAsciiMapFromFile(fileName)
}