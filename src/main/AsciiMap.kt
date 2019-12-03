package main

import main.AsciiMapErrorFormatter.NO_END_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.NO_START_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.formatPathBreakError


class AsciiMap(asciiMap: String) {

    private val startCharacter = "@"
    private val endCharacter = "x"
    private val pathCharacterHorizontal = "-"
    private val pathCharacterVertical = "|"
    private val pathCharacterJunction = "+"

    val items: List<AsciiMapItem> = AsciiMapItemFormatter.formatAsciiMapItems(asciiMap)

    private var path = ""

    fun getOutput(): AsciiMapOutput {
        val startItem = items.find { it.character == startCharacter }
        val endItem = items.find { it.character == endCharacter }
        when {
            startItem == null -> throw Exception(NO_START_CHARACTER_ERROR_MESSAGE)
            endItem == null -> throw Exception(NO_END_CHARACTER_ERROR_MESSAGE)
            else -> appendNextCharacter(null, startItem)
        }
        return buildOutput()
    }

    private fun appendNextCharacter(previousItem: AsciiMapItem?, currentItem: AsciiMapItem) {
        path += currentItem.character
        if (currentItem.character == endCharacter) return
        val nextItem = findNextItem(previousItem, currentItem) ?: return
        appendNextCharacter(previousItem, nextItem)
    }

    private fun findNextItem(previousItem: AsciiMapItem?, currentItem: AsciiMapItem): AsciiMapItem? {
        val nextItem: AsciiMapItem? = null
        val leftItem = items.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex - 1 }
        val topItem = items.find { it.rowIndex == currentItem.rowIndex - 1 && it.columnIndex == currentItem.columnIndex }
        val rightItem = items.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex + 1 }
        val bottomItem = items.find { it.rowIndex == currentItem.rowIndex + 1 && it.columnIndex == currentItem.columnIndex }
        val adjacentItems = mutableListOf(leftItem, topItem, rightItem, bottomItem)
        adjacentItems.removeIf { itemsAreAtTheSamePosition(it, previousItem) }
        when {
            pathBreaks(adjacentItems) -> throw Exception(formatPathBreakError(currentItem))
        }
        return nextItem
    }

    private fun itemsAreAtTheSamePosition(itemOne: AsciiMapItem?, itemTwo: AsciiMapItem?) =
            itemOne?.rowIndex == itemTwo?.rowIndex && itemOne?.columnIndex == itemTwo?.columnIndex

    private fun pathBreaks(adjacentItems: List<AsciiMapItem?>) = adjacentItems.find { isPathItem(it) } == null

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