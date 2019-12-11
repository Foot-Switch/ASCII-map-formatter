import view.AsciiMap
import viewModel.AsciiMapNavigator
import java.io.File

private const val defaultFilePath = "src/main/ascii_map.txt"

fun main(args: Array<String>) {
    val fileName = if (args.isNotEmpty()) args[0] else defaultFilePath
    val mapAsString = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
    val mapNavigator = AsciiMapNavigator(mapAsString)
    val asciiMap = AsciiMap(mapNavigator)
    asciiMap.printOutput()
}