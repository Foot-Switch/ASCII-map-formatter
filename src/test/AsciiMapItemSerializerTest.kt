import main.AsciiMapErrorFormatter.EMPTY_INPUT_ERROR_MESSAGE
import main.AsciiMapItemSerializer
import main.AsciiMapTestData
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertEquals

class AsciiMapItemSerializerTest {

    @Rule
    @JvmField
    var exceptionRule: ExpectedException = ExpectedException.none()

    @Test
    fun throwExceptionForEmptyInput() {
        exceptionRule.expectMessage(EMPTY_INPUT_ERROR_MESSAGE)
        AsciiMapItemSerializer.serializeAsciiMapItems(AsciiMapTestData.emptyMap)
    }

    @Test
    fun formatAsciiMapItemsOne() {
        assertEquals(AsciiMapTestData.expectedItemsOne, AsciiMapItemSerializer.serializeAsciiMapItems(AsciiMapTestData.mapOne))
    }

    @Test
    fun formatAsciiMapItemsTwo() {
        assertEquals(AsciiMapTestData.expectedItemsTwo, AsciiMapItemSerializer.serializeAsciiMapItems(AsciiMapTestData.mapTwo))
    }

    @Test
    fun formatAsciiMapItemsThree() {
        assertEquals(AsciiMapTestData.expectedItemsThree, AsciiMapItemSerializer.serializeAsciiMapItems(AsciiMapTestData.mapThree))
    }
}