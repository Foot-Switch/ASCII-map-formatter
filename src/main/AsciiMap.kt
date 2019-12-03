package main

import main.AsciiMapErrorFormatter.NO_END_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.NO_START_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.formatPathAmbiguityError
import main.AsciiMapErrorFormatter.formatPathBreakError


class AsciiMap(asciiMap: String) {

    private val startCharacter = "@"
    private val endCharacter = "x"
    private val pathCharacterHorizontal = "-"
    private val pathCharacterVertical = "|"
    private val pathCharacterJunction = "+"

    val items: List<AsciiMapItem> = AsciiMapItemFormatter.formatAsciiMapItems(asciiMap)

    private val pathItems = mutableListOf<AsciiMapItem>()

    fun formatOutput(): AsciiMapOutput {
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
        pathItems.add(currentItem)
        if (currentItem.character == endCharacter) return
        val nextItem = findNextItem(previousItem, currentItem) ?: return
        appendNextCharacter(currentItem, nextItem)
    }

    private fun findNextItem(previousItem: AsciiMapItem?, currentItem: AsciiMapItem): AsciiMapItem? {
        val nextItem: AsciiMapItem?
        val leftItem = items.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex - 1 }
        val topItem = items.find { it.rowIndex == currentItem.rowIndex - 1 && it.columnIndex == currentItem.columnIndex }
        val rightItem = items.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex + 1 }
        val bottomItem = items.find { it.rowIndex == currentItem.rowIndex + 1 && it.columnIndex == currentItem.columnIndex }
        val adjacentItems = mutableListOf(leftItem, topItem, rightItem, bottomItem)
        adjacentItems.removeIf { itemsHaveSamePosition(it, previousItem) }
        nextItem = when {
            pathBreaks(adjacentItems) -> throw Exception(formatPathBreakError(currentItem))
            pathIsAmbiguous(currentItem, adjacentItems) -> throw Exception(formatPathAmbiguityError(currentItem))
            isPassThroughHorizontal(previousItem, leftItem, topItem, rightItem, bottomItem) -> if (itemsHaveSamePosition(previousItem, rightItem)) leftItem else rightItem
            isPassThroughVertical(previousItem, leftItem, topItem, rightItem, bottomItem) -> if (itemsHaveSamePosition(previousItem, topItem)) bottomItem else topItem
            else -> adjacentItems.find { isValidPathCharacter(it) }
        }
        return nextItem
    }

    private fun isValidPathCharacter(it: AsciiMapItem?) = it != null && it.character.isNotBlank()

    private fun itemsHaveSamePosition(itemOne: AsciiMapItem?, itemTwo: AsciiMapItem?) =
            itemOne?.rowIndex == itemTwo?.rowIndex && itemOne?.columnIndex == itemTwo?.columnIndex

    private fun pathBreaks(adjacentItems: List<AsciiMapItem?>) = adjacentItems.find { isPathItem(it) } == null

    private fun pathIsAmbiguous(currentItem: AsciiMapItem, adjacentItems: List<AsciiMapItem?>): Boolean {
        // TODO: handle ambiguous cases
        return false
    }

    private fun isPassThroughHorizontal(previousItem: AsciiMapItem?, leftItem: AsciiMapItem?, topItem: AsciiMapItem?, rightItem: AsciiMapItem?, bottomItem: AsciiMapItem?) =
            isValidPathCharacter(leftItem) && isValidPathCharacter(rightItem) &&
                    topItem?.character == pathCharacterVertical && bottomItem?.character == pathCharacterVertical &&
                    enteredHorizontally(previousItem, leftItem, rightItem)

    private fun enteredHorizontally(previousItem: AsciiMapItem?, leftItem: AsciiMapItem?, rightItem: AsciiMapItem?) =
            itemsHaveSamePosition(leftItem, previousItem) || itemsHaveSamePosition(rightItem, previousItem)

    private fun isPassThroughVertical(previousItem: AsciiMapItem?, leftItem: AsciiMapItem?, topItem: AsciiMapItem?, rightItem: AsciiMapItem?, bottomItem: AsciiMapItem?) =
            isValidPathCharacter(topItem) && isValidPathCharacter(bottomItem) &&
                    leftItem?.character == pathCharacterHorizontal && rightItem?.character == pathCharacterHorizontal &&
                    enteredVertically(previousItem, topItem, bottomItem)

    private fun enteredVertically(previousItem: AsciiMapItem?, topItem: AsciiMapItem?, bottomItem: AsciiMapItem?) =
            itemsHaveSamePosition(topItem, previousItem) || itemsHaveSamePosition(bottomItem, previousItem)

    private fun isPathItem(asciiMapItem: AsciiMapItem?) =
            asciiMapItem != null &&
                    (asciiMapItem.character == pathCharacterHorizontal || asciiMapItem.character == pathCharacterVertical
                            || asciiMapItem.character.single().isLetter()
                            || asciiMapItem.character == pathCharacterJunction
                            || asciiMapItem.character == endCharacter)


    private fun buildOutput(): AsciiMapOutput {
        var letters = ""
        var pathAsCharacters = ""
        pathItems.map { it.character }.forEach { character ->
            pathAsCharacters += character
            if (character.single().isLetter() && character != endCharacter) letters += character
        }
        return AsciiMapOutput(letters, pathAsCharacters)
    }
}