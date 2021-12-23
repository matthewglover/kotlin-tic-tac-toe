package digital.fortisgreen.kotlin.tictactoe

import digital.fortisgreen.kotlin.tictactoe.exceptions.LineCreationException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
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

    @Test
    internal fun `reports winner when X wins`() {
        val line = Line(listOf(PlayerMark.X, PlayerMark.X, PlayerMark.X))
        assertTrue(line.isWinner())
    }

    @Test
    internal fun `reports winner when O wins`() {
        val line = Line(listOf(PlayerMark.O, PlayerMark.O, PlayerMark.O))
        assertTrue(line.isWinner())
    }

    @Test
    internal fun `does not report winner when no winner`() {
        val line = Line(listOf(null, null, null))
        assertFalse(line.isWinner())
    }

    @Test
    internal fun `reports X when it is the winner`() {
        val line = Line(listOf(PlayerMark.X, PlayerMark.X, PlayerMark.X))
        assertEquals(PlayerMark.X, line.playerMark())
    }

    @Test
    internal fun `reports O when it is the winner`() {
        val line = Line(listOf(PlayerMark.O, PlayerMark.O, PlayerMark.O))
        assertEquals(PlayerMark.O, line.playerMark())
    }
}
