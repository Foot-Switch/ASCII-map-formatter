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
        AsciiMap(AsciiMapTestData.mapWithoutStart)
    }

    @Test
    fun throwExceptionWhenNoEndCharacterIsPresent() {
        exceptionRule.expectMessage(END_CHARACTER_ERROR_MESSAGE)
        AsciiMap(AsciiMapTestData.mapWithoutEnd)
    }

    @Test
    fun throwExceptionWhenMultipleStartCharactersArePresent() {
        exceptionRule.expectMessage(START_CHARACTER_ERROR_MESSAGE)
        AsciiMap(AsciiMapTestData.mapWithMultipleStart)
    }

    @Test
    fun throwExceptionWhenMultipleEndCharactersArePresent() {
        exceptionRule.expectMessage(END_CHARACTER_ERROR_MESSAGE)
        AsciiMap(AsciiMapTestData.mapWithMultipleEnd)
    }

    @Test
    fun throwExceptionWhenPathBreaks() {
        exceptionRule.expectMessage(formatPathBreakErrorMessage(AsciiMapItem("@", 0, 0)))
        AsciiMap(AsciiMapTestData.brokenPathMap)
    }

    @Test
    fun throwExceptionWhenStartIsAmbiguous() {
        exceptionRule.expectMessage(formatPathAmbiguityErrorMessage(AsciiMapItem("@", 0, 2)))
        AsciiMap(AsciiMapTestData.ambiguousStartMap)
    }

    @Test
    fun throwExceptionWhenJunctionIsAmbiguous() {
        exceptionRule.expectMessage(formatPathAmbiguityErrorMessage(AsciiMapItem("+", 1, 8)))
        AsciiMap(AsciiMapTestData.ambiguousJunctionMap)
    }

    @Test
    fun formatOutputWithoutLetters() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutLetters)
        assertEquals(AsciiMapTestData.expectedOutputWithoutLetters, asciiMap.output)
    }

    @Test
    fun formatOutputOne() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapOne)
        assertEquals(AsciiMapTestData.expectedOutputOne, asciiMap.output)
    }

    @Test
    fun formatOutputTwo() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapTwo)
        assertEquals(AsciiMapTestData.expectedOutputTwo, asciiMap.output)
    }

    @Test
    fun formatOutputThree() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapThree)
        assertEquals(AsciiMapTestData.expectedOutputThree, asciiMap.output)
    }
}