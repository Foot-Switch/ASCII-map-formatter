import main.*
import main.AsciiMapErrorFormatter.END_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.START_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage
import main.AsciiMapErrorFormatter.formatPathBreakErrorMessage
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertEquals

class AsciiMapTest {

    @Test
    fun formatOutputWithoutLetters() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutLetters)
        assertEquals(AsciiMapTestData.expectedOutputWithoutLetters, asciiMap.getOutput())
    }

    @Test
    fun formatOutputOne() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapOne)
        assertEquals(AsciiMapTestData.expectedOutputOne, asciiMap.getOutput())
    }

    @Test
    fun formatOutputTwo() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapTwo)
        assertEquals(AsciiMapTestData.expectedOutputTwo, asciiMap.getOutput())
    }

    @Test
    fun formatOutputThree() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapThree)
        assertEquals(AsciiMapTestData.expectedOutputThree, asciiMap.getOutput())
    }
}