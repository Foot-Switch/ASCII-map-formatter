package main


class AsciiMap(asciiMap: String) {

    private val startCharacter = "@"
    private val endCharacter = "x"

    val items: List<AsciiMapItem> = AsciiMapFormatter.formatAsciiMapItems(asciiMap)

    private var path = ""

    fun getOutput(): AsciiMapOutput {
        val startItem = items.find { it.character == startCharacter }
        val endItem = items.find { it.character == endCharacter }
        when {
            startItem == null -> throw Exception(AsciiMapTestData.NO_START_CHARACTER_ERROR_MESSAGE)
            endItem == null -> throw Exception(AsciiMapTestData.NO_END_CHARACTER_ERROR_MESSAGE)
            else -> buildPath(startItem)
        }
        return buildOutput()
    }

    private fun getNextCharacter(currentItem: AsciiMapItem): String {
        return ""
    }

    private fun buildPath(startItem: AsciiMapItem) {
        path += startItem
        path += getNextCharacter(startItem)
    }

    private fun buildOutput(): AsciiMapOutput {
        var uppercaseLetters = ""
        path.forEach { character ->
            if (character.isLetter() && character.toString() != endCharacter) uppercaseLetters += character
        }
        return AsciiMapOutput(uppercaseLetters, "")
    }
}