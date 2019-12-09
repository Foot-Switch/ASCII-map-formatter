package main

class AsciiMapNavigator(asciiMapInput: String) {

    private val startCharacter = "@"
    private val endCharacter = "x"
    private val pathCharacterHorizontal = "-"
    private val pathCharacterVertical = "|"
    private val pathCharacterCorner = "+"

    private val unambiguousNumberOfNextItemCandidates = 1

    private val allItems: List<AsciiMapItem> = AsciiMapItemSerializer.serializeAsciiMapItems(asciiMapInput)
    private var pathItems = mutableListOf<AsciiMapItem>()

    fun findNextItem(allMapItems: List<AsciiMapItem>?, previousItem: AsciiMapItem?, currentItem: AsciiMapItem): AsciiMapItem? {
        val nextItem: AsciiMapItem?
        val allAdjacentItems = findAdjacentItems(allMapItems, currentItem)
        val validAdjacentItems = removeNonPathItems(allAdjacentItems)
        removePreviousItemFromValidAdjacentItems(previousItem, validAdjacentItems)
        nextItem = when {
            validAdjacentItems.isEmpty() -> throw Exception(AsciiMapErrorFormatter.formatPathBreakErrorMessage(currentItem))
            startIsAmbiguous(currentItem, validAdjacentItems) -> throw Exception(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(currentItem))
            isJunction(validAdjacentItems) -> findNextItemInJunction(previousItem!!, currentItem, validAdjacentItems)
            else -> getTheOnlyRemainingNextItemCandidate(validAdjacentItems)
        }
        return nextItem
    }

    fun findAdjacentItems(allMapItems: List<AsciiMapItem>?, currentItem: AsciiMapItem): MutableList<AsciiMapItem?> {
        val leftItem = allMapItems?.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex - 1 }
        val topItem = allMapItems?.find { it.rowIndex == currentItem.rowIndex - 1 && it.columnIndex == currentItem.columnIndex }
        val rightItem = allMapItems?.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex + 1 }
        val bottomItem = allMapItems?.find { it.rowIndex == currentItem.rowIndex + 1 && it.columnIndex == currentItem.columnIndex }
        return mutableListOf(leftItem, topItem, rightItem, bottomItem)
    }

    fun removeNonPathItems(allAdjacentItems: List<AsciiMapItem?>): MutableList<AsciiMapItem> {
        val nextItemCandidates = mutableListOf<AsciiMapItem>()
        allAdjacentItems.forEach { adjacentItem -> if (isPathItem(adjacentItem)) nextItemCandidates.add(adjacentItem!!) }
        return nextItemCandidates
    }

    fun removePreviousItemFromValidAdjacentItems(previousItem: AsciiMapItem?, validAdjacentItems: MutableList<AsciiMapItem>) {
        validAdjacentItems.removeIf { itemsHaveSamePosition(it, previousItem) }
    }

    fun startIsAmbiguous(currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): Boolean {
        return currentItem.character == startCharacter && nextItemCandidates.size > unambiguousNumberOfNextItemCandidates
    }

    fun isJunction(nextItemCandidates: List<AsciiMapItem>) = nextItemCandidates.size > unambiguousNumberOfNextItemCandidates

    fun itemsHaveSamePosition(itemOne: AsciiMapItem?, itemTwo: AsciiMapItem?) =
            itemOne?.rowIndex == itemTwo?.rowIndex && itemOne?.columnIndex == itemTwo?.columnIndex

    fun findNextItemInJunction(previousItem: AsciiMapItem, currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): AsciiMapItem {
        nextItemCandidates.find { it.character == endCharacter }?.let { return it }
        return if (enteredHorizontally(previousItem, currentItem))
            findNextHorizontalItem(previousItem, currentItem, nextItemCandidates)?.let { it }
                    ?: throw Exception(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(currentItem))
        else
            findNextVerticalItem(previousItem, currentItem, nextItemCandidates)?.let { it }
                    ?: throw Exception(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(currentItem))
    }

    fun enteredHorizontally(previousItem: AsciiMapItem, currentItem: AsciiMapItem) =
            currentItem.rowIndex == previousItem.rowIndex

    fun findNextHorizontalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): AsciiMapItem? {
        val nextHorizontalPosition = if (previousItem.columnIndex < currentItem.columnIndex) currentItem.columnIndex + 1 else currentItem.columnIndex - 1
        return nextItemCandidates.find { it.columnIndex == nextHorizontalPosition }
    }

    fun findNextVerticalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem, nextItemCandidates: List<AsciiMapItem>): AsciiMapItem? {
        val nextVerticalPosition = if (previousItem.rowIndex < currentItem.rowIndex) currentItem.rowIndex + 1 else currentItem.rowIndex - 1
        return nextItemCandidates.find { it.rowIndex == nextVerticalPosition }
    }

    fun getTheOnlyRemainingNextItemCandidate(nextItemCandidates: List<AsciiMapItem>) = nextItemCandidates[0]

    fun isPathItem(asciiMapItem: AsciiMapItem?) =
            asciiMapItem != null &&
                    (asciiMapItem.character == pathCharacterHorizontal
                            || asciiMapItem.character == pathCharacterVertical
                            || asciiMapItem.character.single().isLetter()
                            || asciiMapItem.character == pathCharacterCorner
                            || asciiMapItem.character == endCharacter)

    fun buildItemPath() {
        val startItems = allItems?.filter { it.character == startCharacter }
        val endItems = allItems?.filter { it.character == endCharacter }
        when {
            startItems?.size != 1 -> throw Exception(AsciiMapErrorFormatter.START_CHARACTER_ERROR_MESSAGE)
            endItems?.size != 1 -> throw Exception(AsciiMapErrorFormatter.END_CHARACTER_ERROR_MESSAGE)
            else -> addNextItemToPath(null, startItems[0])
        }
    }

    fun addNextItemToPath(previousItem: AsciiMapItem?, currentItem: AsciiMapItem) {
        pathItems?.add(currentItem)
        if (currentItem.character == endCharacter) return
        val nextItem = findNextItem(allItems, previousItem, currentItem) ?: return
        addNextItemToPath(currentItem, nextItem)
    }


    fun formatOutputFromItemPath(): AsciiMapOutput {
        val letterItems = mutableListOf<AsciiMapItem>()
        var pathAsCharacters = ""
        pathItems?.forEach { asciiMapItem ->
            pathAsCharacters += asciiMapItem.character
            if (isPathLetterCharacter(asciiMapItem) && !letterItems.contains(asciiMapItem))
                letterItems.add(asciiMapItem)
        }
        var letters = ""
        letterItems.forEach { asciiMapItem -> letters += asciiMapItem.character }
        if (letters.isBlank()) letters = AsciiMapErrorFormatter.NO_LETTERS_MESSAGE
        return AsciiMapOutput(letters, pathAsCharacters)
    }

    fun isPathLetterCharacter(asciiMapItem: AsciiMapItem) =
            asciiMapItem.character.single().isLetter() && asciiMapItem.character != endCharacter

    fun buildOutput(): AsciiMapOutput {
        buildItemPath()
        return formatOutputFromItemPath()
    }
}
