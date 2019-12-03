import main.AsciiMapErrorFormatter
import main.AsciiMapItem
import org.junit.Test

import org.junit.Assert.*

class AsciiMapErrorFormatterTest {

    @Test
    fun formatPathBreakError() {
        val asciiMapItem = AsciiMapItem("a", 0, 0)
        val errorMessage = AsciiMapErrorFormatter.formatPathBreakError(asciiMapItem)
        assertEquals("The path breaks on character [${asciiMapItem.character}] at position [${asciiMapItem.rowIndex}, ${asciiMapItem.columnIndex}] because no adjacent items are part of a valid path.", errorMessage)
    }
}