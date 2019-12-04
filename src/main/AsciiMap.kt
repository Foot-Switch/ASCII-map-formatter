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
    private val pathCharacterCorner = "+"

    private val unambiguousNumberOfNextItemCandidates = 1

    private val allItems: List<AsciiMapItem> = AsciiMapItemSerializer.serializeAsciiMapItems(asciiMap)
    private val pathItems = mutableListOf<AsciiMapItem>()

    val output = buildOutput()

    private fun buildOutput(): AsciiMapOutput {
        buildItemPath()
        return formatOutputFromItemPath()
    }

    private fun buildItemPath() {
        val startItem = allItems.find { it.character == startCharacter }
        val endItem = allItems.find { it.character == endCharacter }
        when {
            startItem == null -> throw Exception(NO_START_CHARACTER_ERROR_MESSAGE)
            endItem == null -> throw Exception(NO_END_CHARACTER_ERROR_MESSAGE)
            else -> addNextItemToPath(null, startItem)
        }
    }

    private fun addNextItemToPath(previousItem: AsciiMapItem?, currentItem: AsciiMapItem) {
        pathItems.add(currentItem)
        if (currentItem.character == endCharacter) return
        val nextItem = findNextItem(previousItem, currentItem) ?: return
        addNextItemToPath(currentItem, nextItem)
    }

    private fun findNextItem(previousItem: AsciiMapItem?, currentItem: AsciiMapItem): AsciiMapItem? {
        val nextItem: AsciiMapItem?
        val allAdjacentItems = findAdjacentItems(currentItem)
        val validAdjacentItems = removeNonPathItems(allAdjacentItems)
        removePreviousItemFromValidAdjacentItems(previousItem, validAdjacentItems)
        nextItem = when {
            validAdjacentItems.isEmpty() -> throw Exception(formatPathBreakErrorMessage(currentItem))
            startIsAmbiguous(currentItem, validAdjacentItems) -> throw Exception(formatPathAmbiguityErrorMessage(currentItem))
            isJunction(validAdjacentItems) -> findNextItemInJunction(previousItem!!, currentItem, validAdjacentItems)
            else -> getTheOnlyRemainingNextItemCandidate(validAdjacentItems)
        }
        return nextItem
    }

    private fun findAdjacentItems(currentItem: AsciiMapItem): MutableList<AsciiMapItem?> {
        val leftItem = allItems.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex - 1 }
        val topItem = allItems.find { it.rowIndex == currentItem.rowIndex - 1 && it.columnIndex == currentItem.columnIndex }
        val rightItem = allItems.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex + 1 }
        val bottomItem = allItems.find { it.rowIndex == currentItem.rowIndex + 1 && it.columnIndex == currentItem.columnIndex }
        return mutableListOf(leftItem, topItem, rightItem, bottomItem)
    }

    private fun removeNonPathItems(allAdjacentItems: List<AsciiMapItem?>): MutableList<AsciiMapItem> {
        val nextItemCandidates = mutableListOf<AsciiMapItem>()
        allAdjacentItems.forEach { adjacentItem -> if (isPathItem(adjacentItem)) nextItemCandidates.add(adjacentItem!!) }
        return nextItemCandidates
    }

    private fun removePreviousItemFromValidAdjacentItems(previousItem: AsciiMapItem?, validAdjacentItems: MutableList<AsciiMapItem>) {
        validAdjacentItems.removeIf { itemsHaveSamePosition(it, previousItem) }
    }

    private fun startIsAmbiguous(currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): Boolean {
        return currentItem.character == startCharacter && nextItemCandidates.size > unambiguousNumberOfNextItemCandidates
    }

    private fun isJunction(nextItemCandidates: List<AsciiMapItem>) = nextItemCandidates.size > unambiguousNumberOfNextItemCandidates

    private fun itemsHaveSamePosition(itemOne: AsciiMapItem?, itemTwo: AsciiMapItem?) =
            itemOne?.rowIndex == itemTwo?.rowIndex && itemOne?.columnIndex == itemTwo?.columnIndex

    private fun findNextItemInJunction(previousItem: AsciiMapItem, currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): AsciiMapItem {
        return if (enteredHorizontally(previousItem, currentItem))
            findNextHorizontalItem(previousItem, currentItem, nextItemCandidates)?.let { it }
                    ?: throw Exception(formatPathAmbiguityErrorMessage(currentItem))
        else
            findNextVerticalItem(previousItem, currentItem, nextItemCandidates)?.let { it }
                    ?: throw Exception(formatPathAmbiguityErrorMessage(currentItem))
    }

    private fun enteredHorizontally(previousItem: AsciiMapItem, currentItem: AsciiMapItem) =
            currentItem.rowIndex == previousItem.rowIndex

    private fun findNextHorizontalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): AsciiMapItem? {
        val nextHorizontalPosition = if (previousItem.columnIndex < currentItem.columnIndex) currentItem.columnIndex + 1 else currentItem.columnIndex - 1
        return nextItemCandidates.find { it.columnIndex == nextHorizontalPosition }
    }

    private fun findNextVerticalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): AsciiMapItem? {
        val nextVerticalPosition = if (previousItem.rowIndex < currentItem.rowIndex) currentItem.rowIndex + 1 else currentItem.rowIndex - 1
        return nextItemCandidates.find { it.rowIndex == nextVerticalPosition }
    }

    private fun getTheOnlyRemainingNextItemCandidate(nextItemCandidates: List<AsciiMapItem>) = nextItemCandidates[0]

    private fun isPathItem(asciiMapItem: AsciiMapItem?) =
            asciiMapItem != null &&
                    (asciiMapItem.character == pathCharacterHorizontal
                            || asciiMapItem.character == pathCharacterVertical
                            || asciiMapItem.character.single().isLetter()
                            || asciiMapItem.character == pathCharacterCorner
                            || asciiMapItem.character == endCharacter)

    private fun formatOutputFromItemPath(): AsciiMapOutput {
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