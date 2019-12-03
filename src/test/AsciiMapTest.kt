import main.*
import main.AsciiMapErrorFormatter.NO_END_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.NO_START_CHARACTER_ERROR_MESSAGE
import main.AsciiMapErrorFormatter.formatPathBreakError
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
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutStart)
        asciiMap.formatOutput()
    }

    @Test
    fun throwExceptionWhenNoEndCharacterIsPresent() {
        exceptionRule.expectMessage(NO_END_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutEnd)
        asciiMap.formatOutput()
    }

    @Test
    fun throwExceptionWhenPathBreaks() {
        exceptionRule.expectMessage(formatPathBreakError(AsciiMapItem("@", 0, 0)))
        val asciiMap = AsciiMap(AsciiMapTestData.brokenPathMap)
        asciiMap.formatOutput()
    }

//    @Test
//    fun throwExceptionWhenPathIsAmbiguous() {
//        exceptionRule.expectMessage(formatPathAmbiguityError(AsciiMapItem("@", 0, 2)))
//        val asciiMap = AsciiMap(AsciiMapTestData.ambiguousMap)
//        asciiMap.formatOutput()
//    }

    @Test
    fun constructorShouldCallFormatter() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapOne)
        assertEquals(AsciiMapItemFormatter.formatAsciiMapItems(AsciiMapTestData.mapOne), asciiMap.items)
    }

    @Test
    fun formatOutputOne() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapOne)
        val output = asciiMap.formatOutput()
        assertEquals(AsciiMapTestData.expectedOutputOne, output)
    }

    @Test
    fun formatOutputTwo() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapTwo)
        val output = asciiMap.formatOutput()
        assertEquals(AsciiMapTestData.expectedOutputTwo, output)
    }

    @Test
    fun formatOutputThree() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapThree)
        val output = asciiMap.formatOutput()
        assertEquals(AsciiMapTestData.expectedOutputThree, output)
    }
}