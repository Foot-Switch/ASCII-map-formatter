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
    fun findValidAdjacentItems() {
        val previousItem = AsciiMapItem("+", 0, 6)
        val currentItem = AsciiMapItem("B", 1, 6)
        val bottomItem = AsciiMapItem("|", 2, 6)
        assertEquals(listOf(bottomItem), asciiMapNavigator.findValidAdjacentItems(previousItem, currentItem))
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

    @Test
    fun isJunction() {
        assertFalse(asciiMapNavigator.isJunction(AsciiMapItem("-", 0, 5), AsciiMapItem("+", 0, 6)))
        assertTrue(asciiMapNavigator.isJunction(AsciiMapItem("-", 6, 2), AsciiMapItem("E", 6, 3)))
    }

    @Test
    fun isSameItem() {
        val itemOne = AsciiMapItem("-", 0, 0)
        val itemTwo = AsciiMapItem("-", 1, 1)
        assertTrue(asciiMapNavigator.isSameItem(itemOne, itemOne))
        assertFalse(asciiMapNavigator.isSameItem(itemOne, itemTwo))
    }

    @Test
    fun findNextItemInJunction() {
        val previousItem = AsciiMapItem("-", 6, 2)
        val currentItem = AsciiMapItem("K", 6, 3)
        val nextItem = AsciiMapItem("-", 6, 4)
        assertEquals(nextItem, asciiMapNavigator.findNextItemInJunction(previousItem, currentItem))
    }

    @Test
    fun enteredHorizontally() {
        val previousHorizontalItem = AsciiMapItem("-", 6, 2)
        val previousVerticalItem = AsciiMapItem("|", 5, 3)
        val currentItem = AsciiMapItem("K", 6, 3)
        assertTrue(asciiMapNavigator.enteredHorizontally(previousHorizontalItem, currentItem))
        assertFalse(asciiMapNavigator.enteredHorizontally(previousVerticalItem, currentItem))
    }

    @Test
    fun findNextHorizontalItem() {
        val previousHorizontalItem = AsciiMapItem("-", 6, 2)
        val currentItem = AsciiMapItem("K", 6, 3)
        val nextHorizontalItem = AsciiMapItem("-", 6, 4)
        assertEquals(nextHorizontalItem, asciiMapNavigator.findNextHorizontalItem(previousHorizontalItem, currentItem))
    }

    @Test
    fun findNextVerticalItem() {
        val previousVerticalItem = AsciiMapItem("B", 1, 6)
        val currentItem = AsciiMapItem("|", 2, 6)
        val nextVerticalItem = AsciiMapItem("|", 3, 6)
        assertEquals(nextVerticalItem, asciiMapNavigator.findNextVerticalItem(previousVerticalItem, currentItem))
    }

    @Test
    fun getTheOnlyRemainingNextItemCandidate() {
        val onlyItem = AsciiMapItem("a", 0, 0)
        val adjacentItems = listOf(onlyItem)
        assertEquals(onlyItem, asciiMapNavigator.getTheOnlyRemainingNextItemCandidate(adjacentItems))
    }

    @Test
    fun isValidPathItem() {
        assertFalse(asciiMapNavigator.isValidPathItem(AsciiMapItem("*", 0, 0)))
        assertTrue(asciiMapNavigator.isValidPathItem(AsciiMapItem("a", 0, 0)))
        assertTrue(asciiMapNavigator.isValidPathItem(AsciiMapItem("-", 0, 0)))
        assertTrue(asciiMapNavigator.isValidPathItem(AsciiMapItem("|", 0, 0)))
        assertTrue(asciiMapNavigator.isValidPathItem(AsciiMapItem("x", 0, 0)))
        assertTrue(asciiMapNavigator.isValidPathItem(AsciiMapItem("@", 0, 0)))
    }

    @Test
    fun isPathLetterCharacter() {
        assertTrue(asciiMapNavigator.isLetterItem(AsciiMapItem("a", 0, 0)))
        assertFalse(asciiMapNavigator.isLetterItem(AsciiMapItem("-", 0, 0)))
        assertFalse(asciiMapNavigator.isLetterItem(AsciiMapItem("*", 0, 0)))
    }
}