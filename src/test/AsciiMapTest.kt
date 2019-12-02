import main.AsciiMap
import main.AsciiMapFormatter
import main.AsciiMapTestData
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
        exceptionRule.expectMessage(AsciiMapTestData.NO_START_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutStart)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenNoEndCharacterIsPresent() {
        exceptionRule.expectMessage(AsciiMapTestData.NO_END_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutEnd)
        asciiMap.getOutput()
    }

    @Test
    fun constructorShouldCallFormatter() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapOne)
        assertEquals(AsciiMapFormatter.formatAsciiMapItems(AsciiMapTestData.mapOne), asciiMap.items)
    }

    @Test
    fun getOutputOne() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapOne)
        assertEquals(AsciiMapTestData.expectedOutputOne, asciiMap.getOutput())
    }

    @Test
    fun getOutputTwo() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapTwo)
        assertEquals(AsciiMapTestData.expectedOutputTwo, asciiMap.getOutput())
    }

    @Test
    fun getOutputThree() {
        val asciiMap = AsciiMap(AsciiMapTestData.mapThree)
        assertEquals(AsciiMapTestData.expectedOutputThree, asciiMap.getOutput())
    }
}