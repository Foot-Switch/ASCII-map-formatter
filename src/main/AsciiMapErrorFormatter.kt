package main

object AsciiMapErrorFormatter {

    private const val CHARACTER_PLACEHOLDER = "{character}"
    private const val POSITION_PLACEHOLDER = "{position}"
    private const val PATH_BREAKS_ERROR_MESSAGE = "The path breaks on character [$CHARACTER_PLACEHOLDER] at position [$POSITION_PLACEHOLDER] because no adjacent items are part of a valid path."
    private const val PATH_AMBIGUITY_ERROR_MESSAGE = "The path breaks on character [$CHARACTER_PLACEHOLDER] at position [$POSITION_PLACEHOLDER] because adjacent items describe an ambiguous path."

    const val NO_LETTERS_MESSAGE = "The path contains no letters."
    const val EMPTY_INPUT_ERROR_MESSAGE = "Input cannot be empty"
    const val START_CHARACTER_ERROR_MESSAGE = "Input must have exactly one start character marked with \"@\"."
    const val END_CHARACTER_ERROR_MESSAGE = "Input must have at least one end character marked with \"x\"."

    fun formatPathBreakErrorMessage(asciiMapItem: AsciiMapItem): String {
        val messageWithReplacedCharacter = PATH_BREAKS_ERROR_MESSAGE.replace(CHARACTER_PLACEHOLDER, asciiMapItem.character)
        return messageWithReplacedCharacter.replace(POSITION_PLACEHOLDER, "${asciiMapItem.rowIndex}, ${asciiMapItem.columnIndex}")
    }

    fun formatPathAmbiguityErrorMessage(asciiMapItem: AsciiMapItem): String {
        val messageWithReplacedCharacter = PATH_AMBIGUITY_ERROR_MESSAGE.replace(CHARACTER_PLACEHOLDER, asciiMapItem.character)
        return messageWithReplacedCharacter.replace(POSITION_PLACEHOLDER, "${asciiMapItem.rowIndex}, ${asciiMapItem.columnIndex}")
    }
}