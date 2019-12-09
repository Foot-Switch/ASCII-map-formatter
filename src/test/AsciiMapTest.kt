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

    @Rule
    @JvmField
    var exceptionRule: ExpectedException = ExpectedException.none()

    @Test
    fun throwExceptionWhenNoStartCharacterIsPresent() {
        exceptionRule.expectMessage(START_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutStart)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenNoEndCharacterIsPresent() {
        exceptionRule.expectMessage(END_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutEnd)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenMultipleStartCharactersArePresent() {
        exceptionRule.expectMessage(START_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithMultipleStart)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenMultipleEndCharactersArePresent() {
        exceptionRule.expectMessage(END_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithMultipleEnd)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenPathBreaks() {
        exceptionRule.expectMessage(formatPathBreakErrorMessage(AsciiMapItem("@", 0, 0)))
        val asciiMap = AsciiMap(AsciiMapTestData.brokenPathMap)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenStartIsAmbiguous() {
        exceptionRule.expectMessage(formatPathAmbiguityErrorMessage(AsciiMapItem("@", 0, 2)))
        val asciiMap = AsciiMap(AsciiMapTestData.ambiguousStartMap)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenJunctionIsAmbiguous() {
        exceptionRule.expectMessage(formatPathAmbiguityErrorMessage(AsciiMapItem("+", 1, 8)))
        val asciiMap = AsciiMap(AsciiMapTestData.ambiguousJunctionMap)
        asciiMap.getOutput()
    }

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