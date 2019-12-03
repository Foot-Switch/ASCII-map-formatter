import main.*
import main.AsciiMapErrorFormatter.NO_END_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.NO_START_CHARACTER_ERROR_MESSAGE
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
        exceptionRule.expectMessage(NO_START_CHARACTER_ERROR_MESSAGE)
        AsciiMap(AsciiMapTestData.mapWithoutStart)
    }

    @Test
    fun throwExceptionWhenNoEndCharacterIsPresent() {
        exceptionRule.expectMessage(NO_END_CHARACTER_ERROR_MESSAGE)
        AsciiMap(AsciiMapTestData.mapWithoutEnd)
    }

    @Test
    fun throwExceptionWhenPathBreaks() {
        exceptionRule.expectMessage(formatPathBreakErrorMessage(AsciiMapItem("@", 0, 0)))
        AsciiMap(AsciiMapTestData.brokenPathMap)
    }

    @Test
    fun throwExceptionWhenPathIsAmbiguous() {
        exceptionRule.expectMessage(formatPathAmbiguityErrorMessage(AsciiMapItem("@", 0, 2)))
        AsciiMap(AsciiMapTestData.ambiguousMap)
    }

    @Test
    fun constructorShouldCallFormatter() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapOne)
        assertEquals(AsciiMapItemSerializer.serializeAsciiMapItems(AsciiMapTestData.mapOne), asciiMap.items)
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