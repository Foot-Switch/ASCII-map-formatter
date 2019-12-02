import main.AsciiMapFormatter
import main.AsciiMapTestData
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertEquals

class AsciiMapFormatterTest {

    @Rule
    @JvmField
    var exceptionRule: ExpectedException = ExpectedException.none()

    @Test
    fun throwExceptionForEmptyInput() {
        exceptionRule.expectMessage(AsciiMapFormatter.EMPTY_INPUT_ERROR_MESSAGE)
        AsciiMapFormatter.formatAsciiMapItems(AsciiMapTestData.emptyInput)
    }

    @Test
    fun formatAsciiMapItemsOne() {
        assertEquals(AsciiMapTestData.expectedItemsOne, AsciiMapFormatter.formatAsciiMapItems(AsciiMapTestData.mapOne))
    }

    @Test
    fun formatAsciiMapItemsTwo() {
        assertEquals(AsciiMapTestData.expectedItemsTwo, AsciiMapFormatter.formatAsciiMapItems(AsciiMapTestData.mapTwo))
    }

    @Test
    fun formatAsciiMapItemsThree() {
        assertEquals(AsciiMapTestData.expectedItemsThree, AsciiMapFormatter.formatAsciiMapItems(AsciiMapTestData.mapThree))
    }
}