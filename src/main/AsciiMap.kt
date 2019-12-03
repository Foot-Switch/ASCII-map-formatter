package main

import main.AsciiMapErrorFormatter.NO_END_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.NO_START_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage
import main.AsciiMapErrorFormatter.formatPathBreakErrorMessage


class AsciiMap(asciiMap: String) {

    private val startCharacter = "@"
    private val endCharacter = "x"
    private val pathCharacterHorizontal = "-"
    private val pathCharacterVertical = "|"
    private val pathCharacterJunction = "+"

    private val unambiguousNumberOfNextItemCandidates = 1
    private val ambiguousNumberOfNextItemCandidates = 2
    private val maximumNumberOfNextItemCandidates = 3

    val items: List<AsciiMapItem> = AsciiMapItemFormatter.formatAsciiMapItems(asciiMap)

    private val pathItems = mutableListOf<AsciiMapItem>()

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
        val allAdjacentItems = mutableListOf(leftItem, topItem, rightItem, bottomItem)
        val nextItemCandidates = removeInvalidItemsFromAdjacentItems(allAdjacentItems, previousItem)
        nextItem = when {
            nextItemCandidates.isEmpty() -> throw Exception(formatPathBreakErrorMessage(currentItem))
            junctionIsAmbiguous(currentItem, nextItemCandidates) -> throw Exception(formatPathAmbiguityErrorMessage(currentItem))
            isFullJunction(nextItemCandidates) -> findNextItemInFullJunction(previousItem!!, currentItem, nextItemCandidates)
            else -> getOnlyRemainingNextStepCandidate(nextItemCandidates)
        }
        return nextItem
    }

    private fun getOnlyRemainingNextStepCandidate(nextItemCandidates: List<AsciiMapItem>) = nextItemCandidates[0]

    private fun isFullJunction(nextItemCandidates: List<AsciiMapItem>) =
            nextItemCandidates.size == maximumNumberOfNextItemCandidates

    private fun removeInvalidItemsFromAdjacentItems(allAdjacentItems: List<AsciiMapItem?>, previousItem: AsciiMapItem?): List<AsciiMapItem> {
        val nextItemCandidates = mutableListOf<AsciiMapItem>()
        allAdjacentItems.forEach { adjacentItem -> if (isPathItem(adjacentItem)) nextItemCandidates.add(adjacentItem!!) }
        nextItemCandidates.removeIf { itemsHaveSamePosition(it, previousItem) }
        return nextItemCandidates
    }

    private fun itemsHaveSamePosition(itemOne: AsciiMapItem?, itemTwo: AsciiMapItem?) =
            itemOne?.rowIndex == itemTwo?.rowIndex && itemOne?.columnIndex == itemTwo?.columnIndex

    private fun junctionIsAmbiguous(currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): Boolean {
        return (currentItem.character == pathCharacterJunction && nextItemCandidates.size != unambiguousNumberOfNextItemCandidates)
                || (currentItem.character != pathCharacterJunction && nextItemCandidates.size == ambiguousNumberOfNextItemCandidates)
    }

    private fun findNextItemInFullJunction(previousItem: AsciiMapItem, currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): AsciiMapItem {
        return if (enteredHorizontally(currentItem, previousItem)) findNextHorizontalItem(previousItem, currentItem, nextItemCandidates)
        else findNextVerticalItem(previousItem, currentItem, nextItemCandidates)
    }

    private fun enteredHorizontally(currentItem: AsciiMapItem, previousItem: AsciiMapItem?) =
            currentItem.rowIndex == previousItem?.rowIndex

    private fun findNextHorizontalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): AsciiMapItem {
        val nextHorizontalPosition = if (previousItem.columnIndex < currentItem.columnIndex) currentItem.columnIndex + 1 else currentItem.columnIndex - 1
        return nextItemCandidates.find { it.columnIndex == nextHorizontalPosition }!!
    }

    private fun findNextVerticalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): AsciiMapItem {
        val nextVerticalPosition = if (previousItem.rowIndex < currentItem.rowIndex) currentItem.rowIndex + 1 else currentItem.rowIndex - 1
        return nextItemCandidates.find { it.rowIndex == nextVerticalPosition }!!
    }

    private fun isPathItem(asciiMapItem: AsciiMapItem?) =
            asciiMapItem != null &&
                    (asciiMapItem.character == pathCharacterHorizontal
                            || asciiMapItem.character == pathCharacterVertical
                            || asciiMapItem.character.single().isLetter()
                            || asciiMapItem.character == pathCharacterJunction
                            || asciiMapItem.character == endCharacter)


    private fun buildOutput(): AsciiMapOutput {
        val letterItems = mutableListOf<AsciiMapItem>()
        var pathAsCharacters = ""
        pathItems.forEach { asciiMapItem ->
            pathAsCharacters += asciiMapItem.character
            if (isPathLetterCharacter(asciiMapItem) && !letterItems.contains(asciiMapItem))
                letterItems.add(asciiMapItem)
        }
        var letters = ""
        letterItems.forEach { asciiMapItem -> letters += asciiMapItem.character }
        return AsciiMapOutput(letters, pathAsCharacters)
    }

    private fun isPathLetterCharacter(asciiMapItem: AsciiMapItem) =
            asciiMapItem.character.single().isLetter() && asciiMapItem.character != endCharacter
}