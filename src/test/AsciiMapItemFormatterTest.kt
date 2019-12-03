import main.AsciiMapErrorFormatter.EMPTY_INPUT_ERROR_MESSAGE
import main.AsciiMapItemFormatter
import main.AsciiMapTestData
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertEquals

class AsciiMapItemFormatterTest {

    @Rule
    @JvmField
    var exceptionRule: ExpectedException = ExpectedException.none()

    @Test
    fun throwExceptionForEmptyInput() {
        exceptionRule.expectMessage(EMPTY_INPUT_ERROR_MESSAGE)
        AsciiMapItemFormatter.formatAsciiMapItems(AsciiMapTestData.emptyMap)
    }

    @Test
    fun formatAsciiMapItemsOne() {
        assertEquals(AsciiMapTestData.expectedItemsOne, AsciiMapItemFormatter.formatAsciiMapItems(AsciiMapTestData.mapOne))
    }

    @Test
    fun formatAsciiMapItemsTwo() {
        assertEquals(AsciiMapTestData.expectedItemsTwo, AsciiMapItemFormatter.formatAsciiMapItems(AsciiMapTestData.mapTwo))
    }

    @Test
    fun formatAsciiMapItemsThree() {
        assertEquals(AsciiMapTestData.expectedItemsThree, AsciiMapItemFormatter.formatAsciiMapItems(AsciiMapTestData.mapThree))
    }
}