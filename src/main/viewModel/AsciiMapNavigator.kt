package viewModel

import utils.AsciiMapErrorFormatter
import utils.AsciiMapItemSerializer
import model.AsciiMapItem
import model.AsciiMapOutput

class AsciiMapNavigator(asciiMapInput: String) {

    private val startCharacter = "@"
    private val endCharacter = "x"
    private val pathCharacterHorizontal = "-"
    private val pathCharacterVertical = "|"
    private val pathCharacterCorner = "+"
    private val unambiguousNumberOfNextItemCandidates = 1

    private val allMapItems: List<AsciiMapItem> = AsciiMapItemSerializer.serializeAsciiMapItems(asciiMapInput)

    fun printOutput() {
        println("Letters: ${buildOutput().letters}")
        print("Path as characters: ${buildOutput().pathAsCharacters}")
    }

    fun buildOutput(): AsciiMapOutput {
        return formatOutputFromItemPath(buildItemPath())
    }

    fun buildItemPath(): List<AsciiMapItem> {
        val pathItems = mutableListOf<AsciiMapItem>()
        when {
            !hasExactlyOneStartItem() -> throw Exception(AsciiMapErrorFormatter.START_CHARACTER_ERROR_MESSAGE)
            !hasAtLeastOneEndItem() -> throw Exception(AsciiMapErrorFormatter.END_CHARACTER_ERROR_MESSAGE)
            startIsAmbiguous() -> throw Exception(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(findStartItem()))
            else -> {
                val startItem = allMapItems.find { it.character == startCharacter }
                addNextItemToPath(null, startItem!!, pathItems)
            }
        }
        return pathItems
    }

    fun addNextItemToPath(previousItem: AsciiMapItem?, currentItem: AsciiMapItem, pathItems: MutableList<AsciiMapItem>) {
        pathItems.add(currentItem)
        if (currentItem.character == endCharacter) return
        val nextItem = findNextItem(previousItem, currentItem) ?: return
        addNextItemToPath(currentItem, nextItem, pathItems)
    }

    fun formatOutputFromItemPath(pathItems: List<AsciiMapItem>): AsciiMapOutput {
        val letterItems = mutableListOf<AsciiMapItem>()
        var pathAsCharacters = ""
        pathItems.forEach { asciiMapItem ->
            pathAsCharacters += asciiMapItem.character
            if (isLetterItem(asciiMapItem) && !letterItems.contains(asciiMapItem))
                letterItems.add(asciiMapItem)
        }
        var letters = ""
        letterItems.forEach { asciiMapItem -> letters += asciiMapItem.character }
        if (letters.isBlank()) letters = AsciiMapErrorFormatter.NO_LETTERS_MESSAGE
        return AsciiMapOutput(letters, pathAsCharacters)
    }

    fun findNextItem(previousItem: AsciiMapItem?, currentItem: AsciiMapItem): AsciiMapItem? {
        val nextItem: AsciiMapItem?
        val validAdjacentItems = findValidAdjacentItems(previousItem, currentItem)
        nextItem = when {
            validAdjacentItems.isEmpty() -> throw Exception(AsciiMapErrorFormatter.formatPathBreakErrorMessage(currentItem))
            isJunction(previousItem, currentItem) -> findNextItemInJunction(previousItem!!, currentItem)
            else -> getTheOnlyRemainingNextItemCandidate(validAdjacentItems)
        }
        return nextItem
    }

    fun findAdjacentItems(item: AsciiMapItem): List<AsciiMapItem?> {
        val leftItem = allMapItems.find { it.rowIndex == item.rowIndex && it.columnIndex == item.columnIndex - 1 }
        val topItem = allMapItems.find { it.rowIndex == item.rowIndex - 1 && it.columnIndex == item.columnIndex }
        val rightItem = allMapItems.find { it.rowIndex == item.rowIndex && it.columnIndex == item.columnIndex + 1 }
        val bottomItem = allMapItems.find { it.rowIndex == item.rowIndex + 1 && it.columnIndex == item.columnIndex }
        return mutableListOf(leftItem, topItem, rightItem, bottomItem)
    }

    fun findValidAdjacentItems(previousItem: AsciiMapItem?, currentItem: AsciiMapItem): List<AsciiMapItem> {
        val adjacentItems = findAdjacentItems(currentItem)
        val validAdjacentItems = mutableListOf<AsciiMapItem>()
        adjacentItems.forEach { adjacentItem -> if (isValidPathItem(adjacentItem)) validAdjacentItems.add(adjacentItem!!) }
        validAdjacentItems.removeIf { isSameItem(it, previousItem) }
        return validAdjacentItems
    }

    fun hasExactlyOneStartItem(): Boolean {
        val startItems = allMapItems.filter { it.character == startCharacter }
        return startItems.size == 1
    }

    fun hasAtLeastOneEndItem(): Boolean {
        val startItems = allMapItems.filter { it.character == endCharacter }
        return startItems.isNotEmpty()
    }

    fun startIsAmbiguous(): Boolean {
        val startItem = allMapItems.find { it.character == startCharacter }
        return if (startItem != null) {
            val validAdjacentItems = findValidAdjacentItems(null, startItem)
            validAdjacentItems.size > unambiguousNumberOfNextItemCandidates
        } else false
    }

    fun isJunction(previousItem: AsciiMapItem?, currentItem: AsciiMapItem): Boolean {
        val validAdjacentItems = findValidAdjacentItems(previousItem, currentItem)
        return validAdjacentItems.size > unambiguousNumberOfNextItemCandidates
    }

    fun isSameItem(itemOne: AsciiMapItem?, itemTwo: AsciiMapItem?) =
            itemOne?.rowIndex == itemTwo?.rowIndex && itemOne?.columnIndex == itemTwo?.columnIndex

    fun findNextItemInJunction(previousItem: AsciiMapItem, currentItem: AsciiMapItem): AsciiMapItem {
        val validAdjacentItems = findValidAdjacentItems(previousItem, currentItem)
        validAdjacentItems.find { it.character == endCharacter }?.let { return it }
        return if (enteredHorizontally(previousItem, currentItem))
            findNextHorizontalItem(previousItem, currentItem)?.let { it }
                    ?: throw Exception(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(currentItem))
        else
            findNextVerticalItem(previousItem, currentItem)?.let { it }
                    ?: throw Exception(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(currentItem))
    }

    fun enteredHorizontally(previousItem: AsciiMapItem, currentItem: AsciiMapItem) =
            currentItem.rowIndex == previousItem.rowIndex

    fun findNextHorizontalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem): AsciiMapItem? {
        val validAdjacentItems = findValidAdjacentItems(previousItem, currentItem)
        val nextHorizontalPosition = if (previousItem.columnIndex < currentItem.columnIndex) currentItem.columnIndex + 1 else currentItem.columnIndex - 1
        return validAdjacentItems.find { it.columnIndex == nextHorizontalPosition }
    }

    fun findNextVerticalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem): AsciiMapItem? {
        val validAdjacentItems = findValidAdjacentItems(previousItem, currentItem)
        val nextVerticalPosition = if (previousItem.rowIndex < currentItem.rowIndex) currentItem.rowIndex + 1 else currentItem.rowIndex - 1
        return validAdjacentItems.find { it.rowIndex == nextVerticalPosition }
    }

    fun getTheOnlyRemainingNextItemCandidate(adjacentItems: List<AsciiMapItem>) = adjacentItems[0]

    fun isValidPathItem(asciiMapItem: AsciiMapItem?) =
            asciiMapItem != null &&
                    (asciiMapItem.character == pathCharacterHorizontal
                            || asciiMapItem.character == pathCharacterVertical
                            || asciiMapItem.character.single().isLetter()
                            || asciiMapItem.character == pathCharacterCorner
                            || asciiMapItem.character == endCharacter
                            || asciiMapItem.character == startCharacter)

    fun isLetterItem(asciiMapItem: AsciiMapItem) =
            asciiMapItem.character.single().isLetter() && asciiMapItem.character != endCharacter

    fun findStartItem(): AsciiMapItem {
        val startItem = allMapItems.find { it.character == startCharacter }
        if (startItem != null) return startItem
        else throw Exception(AsciiMapErrorFormatter.START_CHARACTER_ERROR_MESSAGE)
    }
}
