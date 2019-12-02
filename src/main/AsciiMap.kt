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
            else -> appendNextCharacter(startItem)
        }
        return buildOutput()
    }

    private fun appendNextCharacter(currentItem: AsciiMapItem) {
        path += currentItem.character
        if (currentItem.character == endCharacter) return
        val nextItem = findNextItem(currentItem) ?: return
        appendNextCharacter(nextItem)
    }

    private fun findNextItem(currentItem: AsciiMapItem): AsciiMapItem? {
        val nextItem: AsciiMapItem? = null
        val leftItem = items.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex - 1 }
        val topItem = items.find { it.rowIndex == currentItem.rowIndex - 1 && it.columnIndex == currentItem.columnIndex }
        val rightItem = items.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex + 1 }
        val bottomItem = items.find { it.rowIndex == currentItem.rowIndex + 1 && it.columnIndex == currentItem.columnIndex }
        val adjacentItems = listOf(leftItem, topItem, rightItem, bottomItem)
        when {
            pathBreaks(adjacentItems) -> throw Exception(AsciiMapTestData.formatPositionError(currentItem))
        }
        return nextItem
    }

    private fun pathBreaks(adjacentItems: List<AsciiMapItem?>) =
            adjacentItems.find { it != null || it?.character != " " } == null

    private fun isPathItem(asciiMapItem: AsciiMapItem?) =
            asciiMapItem != null &&
                    (asciiMapItem.character == pathCharacterHorizontal || asciiMapItem.character == pathCharacterVertical
                            || asciiMapItem.character.single().isLetter()
                            || asciiMapItem.character == pathCharacterJunction
                            || asciiMapItem.character == endCharacter)


    private fun buildOutput(): AsciiMapOutput {
        var uppercaseLetters = ""
        path.forEach { character ->
            if (character.isLetter() && character.toString() != endCharacter) uppercaseLetters += character
        }
        return AsciiMapOutput(uppercaseLetters, path)
    }
}