package main


object AsciiMapTestData {

    const val emptyInput = ""

    const val mapOne =
            " " +
                    " @---A---+\n" +
                    "          |\n" +
                    "  x-B-+   C\n" +
                    "      |   |\n" +
                    "      +---+"

    val expectedItemsOne = listOf(
            AsciiMapItem("", 0, 0)
    )

    val expectedOutputOne = AsciiMapOutput("ACB", "@---A---+|C|+---+|+-B-x")

    const val mapTwo =
            "  " +
                    "@\n" +
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
            " " +
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