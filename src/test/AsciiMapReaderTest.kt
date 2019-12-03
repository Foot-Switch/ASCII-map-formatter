import main.AsciiMapReader
import main.AsciiMapTestData.mapTwo
import main.AsciiMapTestData.testFileName
import org.junit.Test
import kotlin.test.assertEquals

class AsciiMapReaderTest {

    @Test
    fun processAsciiMapFromFile() {
        val mapFromFile = AsciiMapReader.processAsciiMapFromFile(testFileName)
        assertEquals(mapTwo, mapFromFile)
    }
}