package utils

import model.AsciiMapItem
import org.junit.Test

import org.junit.Assert.*

class AsciiMapErrorFormatterTest {

    private val asciiMapItem = AsciiMapItem("a", 0, 0)

    @Test
    fun formatPathBreakError() {
        val errorMessage = AsciiMapErrorFormatter.formatPathBreakErrorMessage(asciiMapItem)
        assertEquals("The path breaks on character [${asciiMapItem.character}] at position [${asciiMapItem.rowIndex}, ${asciiMapItem.columnIndex}] because no adjacent items are part of a valid path.", errorMessage)
    }

    @Test
    fun formatPathAmbiguityError() {
        val errorMessage = AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(asciiMapItem)
        assertEquals("The path breaks on character [${asciiMapItem.character}] at position [${asciiMapItem.rowIndex}, ${asciiMapItem.columnIndex}] because adjacent items describe an ambiguous path.", errorMessage)
    }
}