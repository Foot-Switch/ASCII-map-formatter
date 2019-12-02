package main


class AsciiMap(asciiMap: String) {

    private val startCharacter = "@"
    private val endCharacter = "x"
    private val pathCharacterHorizontal = "-"
    private val pathCharacterVertical = "|"
    private val pathCharacterJunction = "+"

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

    private fun buildPath(startItem: AsciiMapItem) {
        path += startItem
        appendNextCharacter(startItem)
    }

    private fun appendNextCharacter(currentItem: AsciiMapItem) {
        if (currentItem.character == "x") return
        val nextItem = findNextItem(currentItem)
        path += nextItem.character
        appendNextCharacter(nextItem)
    }

    private fun findNextItem(currentItem: AsciiMapItem): AsciiMapItem {
        val leftItem = items.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex - 1 }
        val topItem = items.find { it.rowIndex == currentItem.rowIndex - 1 && it.columnIndex == currentItem.columnIndex }
        val righItem = items.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex + 1 }
        val bottomItem = items.find { it.rowIndex == currentItem.rowIndex + 1 && it.columnIndex == currentItem.columnIndex }
        return currentItem
    }

    private fun isPathItem(asciiMapItem: AsciiMapItem?) =
            asciiMapItem != null &&
                    (asciiMapItem.character == pathCharacterHorizontal
                            || asciiMapItem.character == pathCharacterVertical
                            || asciiMapItem.character.single().isLetter())


    private fun buildOutput(): AsciiMapOutput {
        var uppercaseLetters = ""
        path.forEach { character ->
            if (character.isLetter() && character.toString() != endCharacter) uppercaseLetters += character
        }
        return AsciiMapOutput(uppercaseLetters, "")
    }
}