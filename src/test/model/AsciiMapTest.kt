package model

import main.model.AsciiMap
import main.model.AsciiMapOutput
import main.utils.AsciiMapErrorFormatter
import org.junit.Test
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

    @Test
    fun formatOutputWithoutLetters() {
        val asciiMap = AsciiMap(mapWithoutLetters)
        assertEquals(expectedOutputWithoutLetters, asciiMap.getOutput())
    }

    @Test
    fun formatOutputOne() {
        val asciiMap = AsciiMap(mapOne)
        assertEquals(expectedOutputOne, asciiMap.getOutput())
    }

    @Test
    fun formatOutputTwo() {
        val asciiMap = AsciiMap(mapTwo)
        assertEquals(expectedOutputTwo, asciiMap.getOutput())
    }

    @Test
    fun formatOutputThree() {
        val asciiMap = AsciiMap(mapThree)
        assertEquals(expectedOutputThree, asciiMap.getOutput())
    }
}