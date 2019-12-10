import main.AsciiMapFileReader
import main.AsciiMapFileReader.LETTERS_PLACEHOLDER
import main.AsciiMapFileReader.PATH_AS_CHARACTERS_PLACEHOLDER
import main.AsciiMapOutput
import main.AsciiMapTestData.expectedOutputOne
import main.AsciiMapTestData.expectedOutputThree
import main.AsciiMapTestData.expectedOutputTwo
import main.AsciiMapTestData.testFilePathOne
import main.AsciiMapTestData.testFilePathThree
import main.AsciiMapTestData.testFilePathTwo
import org.junit.Test
import kotlin.test.assertEquals
import java.io.PrintStream
import org.junit.Before
import java.io.ByteArrayOutputStream
import org.junit.After


class AsciiMapFileReaderTest {

    private val outContent = ByteArrayOutputStream()
    private val originalOut = System.out

    @Before
    fun setUpStreams() {
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun processTestMapOne() {
        AsciiMapFileReader.readAsciiMapFromFile(testFilePathOne)
        validateReaderOutput(expectedOutputOne)
    }

    @Test
    fun processTestMapTwo() {
        AsciiMapFileReader.readAsciiMapFromFile(testFilePathTwo)
        validateReaderOutput(expectedOutputTwo)
    }

    @Test
    fun processTestMapThree() {
        AsciiMapFileReader.readAsciiMapFromFile(testFilePathThree)
        validateReaderOutput(expectedOutputThree)
    }

    private fun validateReaderOutput(asciiMapOutput: AsciiMapOutput) {
        assertEquals("$LETTERS_PLACEHOLDER${asciiMapOutput.letters}\n$PATH_AS_CHARACTERS_PLACEHOLDER${asciiMapOutput.pathAsCharacters}", outContent.toString())
    }

    @After
    fun restoreStreams() {
        System.setOut(originalOut)
    }
}