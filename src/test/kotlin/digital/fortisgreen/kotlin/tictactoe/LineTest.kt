package digital.fortisgreen.kotlin.tictactoe

import digital.fortisgreen.kotlin.tictactoe.exceptions.LineCreationException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class LineTest {

    @Test
    internal fun `creates line with 3 items`() {
        assertDoesNotThrow {
            Line(listOf(null, PlayerMark.X, PlayerMark.O))
        }
    }

    @Test
    internal fun `throws LineCreationException when less than 3 items`() {
        assertThrows<LineCreationException> {
            Line(listOf(null, PlayerMark.X))
        }
    }
}
