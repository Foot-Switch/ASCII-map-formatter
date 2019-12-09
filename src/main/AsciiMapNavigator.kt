package main

class AsciiMapNavigator(asciiMapInput: String) {

    private val startCharacter = "@"
    private val endCharacter = "x"
    private val pathCharacterHorizontal = "-"
    private val pathCharacterVertical = "|"
    private val pathCharacterCorner = "+"

    private val unambiguousNumberOfNextItemCandidates = 1

    private val allMapItems: List<AsciiMapItem> = AsciiMapItemSerializer.serializeAsciiMapItems(asciiMapInput)
    private var pathItems = mutableListOf<AsciiMapItem>()

    fun buildItemPath() {
        when {
            !hasExactlyOneStartItem() -> throw Exception(AsciiMapErrorFormatter.START_CHARACTER_ERROR_MESSAGE)
            !hasAtLeastOneEndItem() -> throw Exception(AsciiMapErrorFormatter.END_CHARACTER_ERROR_MESSAGE)
            startIsAmbiguous() -> throw Exception(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(findStartItem()))
            else -> {
                val startItem = allMapItems.find { it.character == startCharacter }
                addNextItemToPath(null, startItem!!)
            }
        }
    }

    fun addNextItemToPath(previousItem: AsciiMapItem?, currentItem: AsciiMapItem) {
        pathItems.add(currentItem)
        if (currentItem.character == endCharacter) return
        val nextItem = findNextItem(previousItem, currentItem) ?: return
        addNextItemToPath(currentItem, nextItem)
    }

    fun findNextItem(previousItem: AsciiMapItem?, currentItem: AsciiMapItem): AsciiMapItem? {
        val nextItem: AsciiMapItem?
        val adjacentItems = findAdjacentItems(currentItem)
        val validAdjacentItems = findValidItems(previousItem, adjacentItems)
        nextItem = when {
            validAdjacentItems.isEmpty() -> throw Exception(AsciiMapErrorFormatter.formatPathBreakErrorMessage(currentItem))
            isJunction(validAdjacentItems) -> findNextItemInJunction(previousItem!!, currentItem, validAdjacentItems)
            else -> getTheOnlyRemainingNextItemCandidate(validAdjacentItems)
        }
        return nextItem
    }

    fun findAdjacentItems(currentItem: AsciiMapItem): List<AsciiMapItem?> {
        val leftItem = allMapItems.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex - 1 }
        val topItem = allMapItems.find { it.rowIndex == currentItem.rowIndex - 1 && it.columnIndex == currentItem.columnIndex }
        val rightItem = allMapItems.find { it.rowIndex == currentItem.rowIndex && it.columnIndex == currentItem.columnIndex + 1 }
        val bottomItem = allMapItems.find { it.rowIndex == currentItem.rowIndex + 1 && it.columnIndex == currentItem.columnIndex }
        return mutableListOf(leftItem, topItem, rightItem, bottomItem)
    }

    fun findValidItems(previousItem: AsciiMapItem?, items: List<AsciiMapItem?>): List<AsciiMapItem> {
        val validAdjacentItems = mutableListOf<AsciiMapItem>()
        items.forEach { adjacentItem -> if (isPathItem(adjacentItem)) validAdjacentItems.add(adjacentItem!!) }
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
            val adjacentItems = findAdjacentItems(startItem)
            val validAdjacentItems = findValidItems(startItem, adjacentItems)
            validAdjacentItems.size > unambiguousNumberOfNextItemCandidates
        } else false
    }

    fun isJunction(nextItemCandidates: List<AsciiMapItem>) = nextItemCandidates.size > unambiguousNumberOfNextItemCandidates

    fun isSameItem(itemOne: AsciiMapItem?, itemTwo: AsciiMapItem?) =
            itemOne?.rowIndex == itemTwo?.rowIndex && itemOne?.columnIndex == itemTwo?.columnIndex

    fun findNextItemInJunction(previousItem: AsciiMapItem, currentItem: AsciiMapItem, validAdjacentItems: List<AsciiMapItem>): AsciiMapItem {
        validAdjacentItems.find { it.character == endCharacter }?.let { return it }
        return if (enteredHorizontally(previousItem, currentItem))
            findNextHorizontalItem(previousItem, currentItem, validAdjacentItems)?.let { it }
                    ?: throw Exception(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(currentItem))
        else
            findNextVerticalItem(previousItem, currentItem, validAdjacentItems)?.let { it }
                    ?: throw Exception(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(currentItem))
    }

    fun enteredHorizontally(previousItem: AsciiMapItem, currentItem: AsciiMapItem) =
            currentItem.rowIndex == previousItem.rowIndex

    fun findNextHorizontalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem, validAdjacentItems: List<AsciiMapItem>): AsciiMapItem? {
        val nextHorizontalPosition = if (previousItem.columnIndex < currentItem.columnIndex) currentItem.columnIndex + 1 else currentItem.columnIndex - 1
        return validAdjacentItems.find { it.columnIndex == nextHorizontalPosition }
    }

    fun findNextVerticalItem(previousItem: AsciiMapItem, currentItem: AsciiMapItem, validAdjacentItems: List<AsciiMapItem>): AsciiMapItem? {
        val nextVerticalPosition = if (previousItem.rowIndex < currentItem.rowIndex) currentItem.rowIndex + 1 else currentItem.rowIndex - 1
        return validAdjacentItems.find { it.rowIndex == nextVerticalPosition }
    }

    fun getTheOnlyRemainingNextItemCandidate(nextItemCandidates: List<AsciiMapItem>) = nextItemCandidates[0]

    fun isPathItem(asciiMapItem: AsciiMapItem?) =
            asciiMapItem != null &&
                    (asciiMapItem.character == pathCharacterHorizontal
                            || asciiMapItem.character == pathCharacterVertical
                            || asciiMapItem.character.single().isLetter()
                            || asciiMapItem.character == pathCharacterCorner
                            || asciiMapItem.character == endCharacter)


    fun formatOutputFromItemPath(): AsciiMapOutput {
        val letterItems = mutableListOf<AsciiMapItem>()
        var pathAsCharacters = ""
        pathItems.forEach { asciiMapItem ->
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

    fun findStartItem(): AsciiMapItem {
        val startItem = allMapItems.find { it.character == startCharacter }
        if (startItem != null) return startItem
        else throw Exception(AsciiMapErrorFormatter.START_CHARACTER_ERROR_MESSAGE)
    }
}
