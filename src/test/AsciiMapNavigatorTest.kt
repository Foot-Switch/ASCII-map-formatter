import main.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AsciiMapNavigatorTest {

    @Rule
    @JvmField
    var exceptionRule: ExpectedException = ExpectedException.none()

    private val asciiMapInput =
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

    private val ambiguousStartMap = "--@--x"
    private val noStartOrEndMap = "----"
    private val multipleStartMap = "x--@--@"

    private lateinit var asciiMapNavigator: AsciiMapNavigator

    @Before
    fun setUp() {
        asciiMapNavigator = AsciiMapNavigator(asciiMapInput)
    }

    @Test
    fun throwExceptionWhenNoStartCharacterIsPresent() {
        exceptionRule.expectMessage(AsciiMapErrorFormatter.START_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutStart)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenNoEndCharacterIsPresent() {
        exceptionRule.expectMessage(AsciiMapErrorFormatter.END_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithoutEnd)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenMultipleStartCharactersArePresent() {
        exceptionRule.expectMessage(AsciiMapErrorFormatter.START_CHARACTER_ERROR_MESSAGE)
        val asciiMap = AsciiMap(AsciiMapTestData.mapWithMultipleStart)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenPathBreaks() {
        exceptionRule.expectMessage(AsciiMapErrorFormatter.formatPathBreakErrorMessage(AsciiMapItem("@", 0, 0)))
        val asciiMap = AsciiMap(AsciiMapTestData.brokenPathMap)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenStartIsAmbiguous() {
        exceptionRule.expectMessage(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(AsciiMapItem("@", 0, 2)))
        val asciiMap = AsciiMap(AsciiMapTestData.ambiguousStartMap)
        asciiMap.getOutput()
    }

    @Test
    fun throwExceptionWhenJunctionIsAmbiguous() {
        exceptionRule.expectMessage(AsciiMapErrorFormatter.formatPathAmbiguityErrorMessage(AsciiMapItem("+", 1, 8)))
        val asciiMap = AsciiMap(AsciiMapTestData.ambiguousJunctionMap)
        asciiMap.getOutput()
    }

    @Test
    fun hasExactlyOneStartItem() {
        assertTrue(asciiMapNavigator.hasExactlyOneStartItem())
        val invalidMapNavigator = AsciiMapNavigator(multipleStartMap)
        assertFalse(invalidMapNavigator.hasExactlyOneStartItem())
    }

    @Test
    fun hasAtLeastOneEndItem() {
        assertTrue(asciiMapNavigator.hasAtLeastOneEndItem())
        val invalidMapNavigator = AsciiMapNavigator(noStartOrEndMap)
        assertFalse(invalidMapNavigator.hasAtLeastOneEndItem())
    }

    @Test
    fun findNextItem() {
        val previousItem = null
        val currentItem = AsciiMapItem("@", 0, 2)
        assertEquals(AsciiMapItem("-", 0, 3), asciiMapNavigator.findNextItem(previousItem, currentItem))
    }

    @Test
    fun findAdjacentItems() {
        val currentItem = AsciiMapItem("B", 1, 6)
        val leftItem = AsciiMapItem(" ", 1, 5)
        val topItem = AsciiMapItem("+", 0, 6)
        val rightItem = AsciiMapItem(" ", 1, 7)
        val bottomItem = AsciiMapItem("|", 2, 6)
        assertEquals(listOf(leftItem, topItem, rightItem, bottomItem), asciiMapNavigator.findAdjacentItems(currentItem))
    }

    @Test
    fun findValidItems() {
        val previousItem = AsciiMapItem("+", 0, 6)
        val leftItem = AsciiMapItem(" ", 1, 5)
        val topItem = AsciiMapItem("+", 0, 6)
        val rightItem = AsciiMapItem(" ", 1, 7)
        val bottomItem = AsciiMapItem("|", 2, 6)
        val items = listOf(leftItem, topItem, rightItem, bottomItem)
        assertEquals(listOf(bottomItem), asciiMapNavigator.findValidItems(previousItem, items))
    }

    @Test
    fun findStartItem() {
        val startItem = AsciiMapItem("@", 0, 2)
        assertEquals(startItem, asciiMapNavigator.findStartItem())
        exceptionRule.expectMessage(AsciiMapErrorFormatter.START_CHARACTER_ERROR_MESSAGE)
        val noStartOrEndNavigator = AsciiMapNavigator(noStartOrEndMap)
        noStartOrEndNavigator.findStartItem()
    }

    @Test
    fun startIsAmbiguous() {
        assertFalse(asciiMapNavigator.startIsAmbiguous())
        val ambiguousAsciiMapNavigator = AsciiMapNavigator(ambiguousStartMap)
        assertTrue(ambiguousAsciiMapNavigator.startIsAmbiguous())
        val noStartAsciiMapNavigator = AsciiMapNavigator(noStartOrEndMap)
        assertFalse(noStartAsciiMapNavigator.startIsAmbiguous())
    }
}