package main


object AsciiMapTestData {

    const val emptyInput = ""

    const val mapOne =
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
            "  | C----+\n" +
                    "  A |    |\n" +
                    "  +---B--+\n" +
                    "    |      x\n" +
                    "    |      |\n" +
                    "    +---D--+"

    val expectedItemsTwo = listOf(
            AsciiMapItem("", 0, 0)
    )

    val expectedOutputTwo = AsciiMapOutput("ABCD", "@|A+---B--+|+----C|-||+---D--+|x")

    const val mapThree =
            " @---+\n" +
                    "      B\n" +
                    "K-----|--A\n" +
                    "|     |  |\n" +
                    "|  +--E  |\n" +
                    "|  |     |\n" +
                    "+--E--Ex C\n" +
                    "   |     |\n" +
                    "   +--F--+"

    val expectedItemsThree = listOf(
            AsciiMapItem("", 0, 0)
    )

    val expectedOutputThree = AsciiMapOutput("BEEFCAKE", "@---+B||E--+|E|+--F--+|C|||A--|-----K|||+--E--Ex")
}