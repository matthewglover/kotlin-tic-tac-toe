package digital.fortisgreen.kotlin.tictactoe

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameTest {

    @Test
    internal fun `a game is active when it has a Board without a winner and with available moves`() {
        val board = mockk<Board>()
        every { board.hasWinner() } returns false
        every { board.hasAvailableMoves() } returns true
        val newGame = Game(board)

        assertTrue(newGame.isActive())
    }

    @Test
    internal fun `a game is not active when it has a Board with a winner`() {
        val board = mockk<Board>()
        every { board.hasWinner() } returns true

        val game = Game(board)

        assertFalse(game.isActive())
    }

    @Test
    internal fun `a game is not active when it has a Board without a winner or available moves`() {
        val board = mockk<Board>()
        every { board.hasWinner() } returns false
        every { board.hasAvailableMoves() } returns false

        val game = Game(board)

        assertFalse(game.isActive())
    }
}
