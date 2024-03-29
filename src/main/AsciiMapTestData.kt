package main


object AsciiMapTestData {

    const val testFilePathOne = "src/test/test_map_1.txt"
    const val testFilePathTwo = "src/test/test_map_2.txt"
    const val testFilePathThree = "src/test/test_map_3.txt"

    const val emptyMap = ""
    const val mapWithoutStart = "--x"
    const val mapWithMultipleStart = "@-@-@-x"
    const val mapWithoutEnd = "@--"
    const val mapWithMultipleEnd = "@--x--x--x"
    const val brokenPathMap = "@  --x"
    const val ambiguousStartMap = "--@--x"

    const val ambiguousJunctionMap =
            "\n" +
                    "          |  \n" +
                    "  @---A---+  \n" +
                    "          |  \n" +
                    "  x-------+   "

    const val mapWithoutLetters = "@---x"

    val expectedOutputWithoutLetters = AsciiMapOutput(AsciiMapErrorFormatter.NO_LETTERS_MESSAGE, mapWithoutLetters)

    const val mapOne =
            "\n" +
                    "  @---A---+\n" +
                    "          |\n" +
                    "  x-B-+   C\n" +
                    "      |   |\n" +
                    "      +---+"

    val expectedItemsOne = listOf(
            AsciiMapItem("@", 0, 0),
            AsciiMapItem("-", 0, 1),
            AsciiMapItem("-", 0, 2),
            AsciiMapItem("-", 0, 3),
            AsciiMapItem("A", 0, 4),
            AsciiMapItem("-", 0, 5),
            AsciiMapItem("-", 0, 6),
            AsciiMapItem("-", 0, 7),
            AsciiMapItem("+", 0, 8),
            AsciiMapItem(" ", 1, 0),
            AsciiMapItem(" ", 1, 1),
            AsciiMapItem(" ", 1, 2),
            AsciiMapItem(" ", 1, 3),
            AsciiMapItem(" ", 1, 4),
            AsciiMapItem(" ", 1, 5),
            AsciiMapItem(" ", 1, 6),
            AsciiMapItem(" ", 1, 7),
            AsciiMapItem("|", 1, 8),
            AsciiMapItem("x", 2, 0),
            AsciiMapItem("-", 2, 1),
            AsciiMapItem("B", 2, 2),
            AsciiMapItem("-", 2, 3),
            AsciiMapItem("+", 2, 4),
            AsciiMapItem(" ", 2, 5),
            AsciiMapItem(" ", 2, 6),
            AsciiMapItem(" ", 2, 7),
            AsciiMapItem("C", 2, 8),
            AsciiMapItem(" ", 3, 0),
            AsciiMapItem(" ", 3, 1),
            AsciiMapItem(" ", 3, 2),
            AsciiMapItem(" ", 3, 3),
            AsciiMapItem("|", 3, 4),
            AsciiMapItem(" ", 3, 5),
            AsciiMapItem(" ", 3, 6),
            AsciiMapItem(" ", 3, 7),
            AsciiMapItem("|", 3, 8),
            AsciiMapItem(" ", 4, 0),
            AsciiMapItem(" ", 4, 1),
            AsciiMapItem(" ", 4, 2),
            AsciiMapItem(" ", 4, 3),
            AsciiMapItem("+", 4, 4),
            AsciiMapItem("-", 4, 5),
            AsciiMapItem("-", 4, 6),
            AsciiMapItem("-", 4, 7),
            AsciiMapItem("+", 4, 8)
    )

    val expectedOutputOne = AsciiMapOutput("ACB", "@---A---+|C|+---+|+-B-x")

    const val mapTwo =
            "\n" +
                    "@\n" +
                    "| C----+\n" +
                    "A |    |\n" +
                    "+---B--+\n" +
                    "  |      x\n" +
                    "  |      |\n" +
                    "  +---D--+"

    val expectedItemsTwo = listOf(
            AsciiMapItem("@", 0, 0),
            AsciiMapItem(" ", 0, 1),
            AsciiMapItem(" ", 0, 2),
            AsciiMapItem(" ", 0, 3),
            AsciiMapItem(" ", 0, 4),
            AsciiMapItem(" ", 0, 5),
            AsciiMapItem(" ", 0, 6),
            AsciiMapItem(" ", 0, 7),
            AsciiMapItem(" ", 0, 8),
            AsciiMapItem(" ", 0, 9),
            AsciiMapItem("|", 1, 0),
            AsciiMapItem(" ", 1, 1),
            AsciiMapItem("C", 1, 2),
            AsciiMapItem("-", 1, 3),
            AsciiMapItem("-", 1, 4),
            AsciiMapItem("-", 1, 5),
            AsciiMapItem("-", 1, 6),
            AsciiMapItem("+", 1, 7),
            AsciiMapItem(" ", 1, 8),
            AsciiMapItem(" ", 1, 9),
            AsciiMapItem("A", 2, 0),
            AsciiMapItem(" ", 2, 1),
            AsciiMapItem("|", 2, 2),
            AsciiMapItem(" ", 2, 3),
            AsciiMapItem(" ", 2, 4),
            AsciiMapItem(" ", 2, 5),
            AsciiMapItem(" ", 2, 6),
            AsciiMapItem("|", 2, 7),
            AsciiMapItem(" ", 2, 8),
            AsciiMapItem(" ", 2, 9),
            AsciiMapItem("+", 3, 0),
            AsciiMapItem("-", 3, 1),
            AsciiMapItem("-", 3, 2),
            AsciiMapItem("-", 3, 3),
            AsciiMapItem("B", 3, 4),
            AsciiMapItem("-", 3, 5),
            AsciiMapItem("-", 3, 6),
            AsciiMapItem("+", 3, 7),
            AsciiMapItem(" ", 3, 8),
            AsciiMapItem(" ", 3, 9),
            AsciiMapItem(" ", 4, 0),
            AsciiMapItem(" ", 4, 1),
            AsciiMapItem("|", 4, 2),
            AsciiMapItem(" ", 4, 3),
            AsciiMapItem(" ", 4, 4),
            AsciiMapItem(" ", 4, 5),
            AsciiMapItem(" ", 4, 6),
            AsciiMapItem(" ", 4, 7),
            AsciiMapItem(" ", 4, 8),
            AsciiMapItem("x", 4, 9),
            AsciiMapItem(" ", 5, 0),
            AsciiMapItem(" ", 5, 1),
            AsciiMapItem("|", 5, 2),
            AsciiMapItem(" ", 5, 3),
            AsciiMapItem(" ", 5, 4),
            AsciiMapItem(" ", 5, 5),
            AsciiMapItem(" ", 5, 6),
            AsciiMapItem(" ", 5, 7),
            AsciiMapItem(" ", 5, 8),
            AsciiMapItem("|", 5, 9),
            AsciiMapItem(" ", 6, 0),
            AsciiMapItem(" ", 6, 1),
            AsciiMapItem("+", 6, 2),
            AsciiMapItem("-", 6, 3),
            AsciiMapItem("-", 6, 4),
            AsciiMapItem("-", 6, 5),
            AsciiMapItem("D", 6, 6),
            AsciiMapItem("-", 6, 7),
            AsciiMapItem("-", 6, 8),
            AsciiMapItem("+", 6, 9)
    )

    val expectedOutputTwo = AsciiMapOutput("ABCD", "@|A+---B--+|+----C|-||+---D--+|x")

    const val mapThree =
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

    val expectedItemsThree = listOf(
            AsciiMapItem(" ", 0, 0),
            AsciiMapItem(" ", 0, 1),
            AsciiMapItem("@", 0, 2),
            AsciiMapItem("-", 0, 3),
            AsciiMapItem("-", 0, 4),
            AsciiMapItem("-", 0, 5),
            AsciiMapItem("+", 0, 6),
            AsciiMapItem(" ", 0, 7),
            AsciiMapItem(" ", 0, 8),
            AsciiMapItem(" ", 0, 9),
            AsciiMapItem(" ", 1, 0),
            AsciiMapItem(" ", 1, 1),
            AsciiMapItem(" ", 1, 2),
            AsciiMapItem(" ", 1, 3),
            AsciiMapItem(" ", 1, 4),
            AsciiMapItem(" ", 1, 5),
            AsciiMapItem("B", 1, 6),
            AsciiMapItem(" ", 1, 7),
            AsciiMapItem(" ", 1, 8),
            AsciiMapItem(" ", 1, 9),
            AsciiMapItem("K", 2, 0),
            AsciiMapItem("-", 2, 1),
            AsciiMapItem("-", 2, 2),
            AsciiMapItem("-", 2, 3),
            AsciiMapItem("-", 2, 4),
            AsciiMapItem("-", 2, 5),
            AsciiMapItem("|", 2, 6),
            AsciiMapItem("-", 2, 7),
            AsciiMapItem("-", 2, 8),
            AsciiMapItem("A", 2, 9),
            AsciiMapItem("|", 3, 0),
            AsciiMapItem(" ", 3, 1),
            AsciiMapItem(" ", 3, 2),
            AsciiMapItem(" ", 3, 3),
            AsciiMapItem(" ", 3, 4),
            AsciiMapItem(" ", 3, 5),
            AsciiMapItem("|", 3, 6),
            AsciiMapItem(" ", 3, 7),
            AsciiMapItem(" ", 3, 8),
            AsciiMapItem("|", 3, 9),
            AsciiMapItem("|", 4, 0),
            AsciiMapItem(" ", 4, 1),
            AsciiMapItem(" ", 4, 2),
            AsciiMapItem("+", 4, 3),
            AsciiMapItem("-", 4, 4),
            AsciiMapItem("-", 4, 5),
            AsciiMapItem("E", 4, 6),
            AsciiMapItem(" ", 4, 7),
            AsciiMapItem(" ", 4, 8),
            AsciiMapItem("|", 4, 9),
            AsciiMapItem("|", 5, 0),
            AsciiMapItem(" ", 5, 1),
            AsciiMapItem(" ", 5, 2),
            AsciiMapItem("|", 5, 3),
            AsciiMapItem(" ", 5, 4),
            AsciiMapItem(" ", 5, 5),
            AsciiMapItem(" ", 5, 6),
            AsciiMapItem(" ", 5, 7),
            AsciiMapItem(" ", 5, 8),
            AsciiMapItem("|", 5, 9),
            AsciiMapItem("+", 6, 0),
            AsciiMapItem("-", 6, 1),
            AsciiMapItem("-", 6, 2),
            AsciiMapItem("E", 6, 3),
            AsciiMapItem("-", 6, 4),
            AsciiMapItem("-", 6, 5),
            AsciiMapItem("E", 6, 6),
            AsciiMapItem("x", 6, 7),
            AsciiMapItem(" ", 6, 8),
            AsciiMapItem("C", 6, 9),
            AsciiMapItem(" ", 7, 0),
            AsciiMapItem(" ", 7, 1),
            AsciiMapItem(" ", 7, 2),
            AsciiMapItem("|", 7, 3),
            AsciiMapItem(" ", 7, 4),
            AsciiMapItem(" ", 7, 5),
            AsciiMapItem(" ", 7, 6),
            AsciiMapItem(" ", 7, 7),
            AsciiMapItem(" ", 7, 8),
            AsciiMapItem("|", 7, 9),
            AsciiMapItem(" ", 8, 0),
            AsciiMapItem(" ", 8, 1),
            AsciiMapItem(" ", 8, 2),
            AsciiMapItem("+", 8, 3),
            AsciiMapItem("-", 8, 4),
            AsciiMapItem("-", 8, 5),
            AsciiMapItem("F", 8, 6),
            AsciiMapItem("-", 8, 7),
            AsciiMapItem("-", 8, 8),
            AsciiMapItem("+", 8, 9)
    )

    val expectedOutputThree = AsciiMapOutput("BEEFCAKE", "@---+B||E--+|E|+--F--+|C|||A--|-----K|||+--E--Ex")
}