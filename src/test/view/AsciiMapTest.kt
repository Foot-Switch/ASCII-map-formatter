package view

import model.AsciiMapOutput
import org.junit.After
import org.junit.Before
import utils.AsciiMapErrorFormatter
import org.junit.Test
import viewModel.AsciiMapNavigator
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class AsciiMapTest {

    private val mapWithoutLetters = "@---x"

    private val expectedOutputWithoutLetters = AsciiMapOutput(AsciiMapErrorFormatter.NO_LETTERS_MESSAGE, "@---x")

    private val mapOne =
            "\n" +
                    "  @---A---+\n" +
                    "          |\n" +
                    "  x-B-+   C\n" +
                    "      |   |\n" +
                    "      +---+"

    private val expectedOutputOne = AsciiMapOutput("ACB", "@---A---+|C|+---+|+-B-x")

    private val mapTwo =
            "\n" +
                    "@\n" +
                    "| C----+\n" +
                    "A |    |\n" +
                    "+---B--+\n" +
                    "  |      x\n" +
                    "  |      |\n" +
                    "  +---D--+"

    private val expectedOutputTwo = AsciiMapOutput("ABCD", "@|A+---B--+|+----C|-||+---D--+|x")

    private val mapThree =
            "\n" +
                    "  @---+\n" +
                    "      B\n" +
                    "K-----|--A\n" +
                    "|     |  |\n" +
                    "|  +--E  |\n" +
                    "|  |     |\n" +
                    "+--E--Ex C\n" +
                    "   |     |\n" +
                    "   +--F--+"


    private val expectedOutputThree = AsciiMapOutput("BEEFCAKE", "@---+B||E--+|E|+--F--+|C|||A--|-----K|||+--E--Ex")


    private val outContent = ByteArrayOutputStream()
    private val originalOut = System.out

    @Before
    fun setUp() {
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun formatOutputWithoutLetters() {
        val asciiMap = AsciiMap(AsciiMapNavigator(mapWithoutLetters))
        assertEquals(expectedOutputWithoutLetters, asciiMap.getOutput())
    }

    @Test
    fun formatOutputOne() {
        val asciiMap = AsciiMap(AsciiMapNavigator(mapOne))
        assertEquals(expectedOutputOne, asciiMap.getOutput())
    }

    @Test
    fun formatOutputTwo() {
        val asciiMap = AsciiMap(AsciiMapNavigator(mapTwo))
        assertEquals(expectedOutputTwo, asciiMap.getOutput())
    }

    @Test
    fun formatOutputThree() {
        val asciiMap = AsciiMap(AsciiMapNavigator(mapThree))
        assertEquals(expectedOutputThree, asciiMap.getOutput())
    }

    @Test
    fun printOutputOne() {
        val asciiMap = AsciiMap(AsciiMapNavigator(mapOne))
        validateOutput(asciiMap)
    }

    @Test
    fun printOutputTwo() {
        val asciiMap = AsciiMap(AsciiMapNavigator(mapTwo))
        validateOutput(asciiMap)
    }

    @Test
    fun printOutputThree() {
        val asciiMap = AsciiMap(AsciiMapNavigator(mapThree))
        validateOutput(asciiMap)
    }

    private fun validateOutput(asciiMap: AsciiMap) {
        asciiMap.printOutput()
        assertEquals("Letters: ${asciiMap.getOutput().letters}\nPath as characters: ${asciiMap.getOutput().pathAsCharacters}", outContent.toString())
    }

    @After
    fun tearDown() {
        System.setOut(originalOut)
    }
}