import main.AsciiMapReader
import main.AsciiMapReader.LETTERS_PLACEHOLDER
import main.AsciiMapReader.PATH_AS_CHARACTERS_PLACEHOLDER
import main.AsciiMapTestData.expectedOutputTwo
import main.AsciiMapTestData.testFilePath
import org.junit.Test
import kotlin.test.assertEquals
import java.io.PrintStream
import org.junit.Before
import java.io.ByteArrayOutputStream
import org.junit.After


class AsciiMapReaderTest {

    private val outContent = ByteArrayOutputStream()
    private val originalOut = System.out

    @Before
    fun setUpStreams() {
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun processAsciiMapFromFile() {
        AsciiMapReader.processAsciiMapFromFile(testFilePath)
        assertEquals("$LETTERS_PLACEHOLDER${expectedOutputTwo.letters}\n$PATH_AS_CHARACTERS_PLACEHOLDER${expectedOutputTwo.pathAsCharacters}", outContent.toString())
    }

    @After
    fun restoreStreams() {
        System.setOut(originalOut)
    }
}