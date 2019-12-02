import main.AsciiMap
import main.AsciiMapFormatter
import main.AsciiMapTestData
import kotlin.test.assertEquals

class AsciiMapTest {

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