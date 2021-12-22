package digital.fortisgreen.kotlin.tictactoe

import digital.fortisgreen.kotlin.tictactoe.exceptions.GameAlreadyCompleteException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameTest {

    @Test
    internal fun `a game is active when it has a Board without a winner and with available moves`() {
        val board = mockk<Board>()
        every { board.hasWinner() } returns false
        every { board.hasAvailableMoves() } returns true

        val playerX = mockk<Player>()
        val playerO = mockk<Player>()

        val newGame = Game(board, playerX, playerO)

        assertTrue(newGame.isActive())
    }

    @Test
    internal fun `a game is not active when it has a Board with a winner`() {
        val board = mockk<Board>()
        every { board.hasWinner() } returns true

        val playerX = mockk<Player>()
        val playerO = mockk<Player>()

        val game = Game(board, playerX, playerO)

        assertFalse(game.isActive())
    }

    @Test
    internal fun `a game is not active when it has a Board without a winner or available moves`() {
        val board = mockk<Board>()
        every { board.hasWinner() } returns false
        every { board.hasAvailableMoves() } returns false

        val playerX = mockk<Player>()
        val playerO = mockk<Player>()

        val game = Game(board, playerX, playerO)

        assertFalse(game.isActive())
    }

    @Test
    @Suppress("LongMethod")
    internal fun `playing the next move runs until player wins`() {
        val board = Board.Empty
        val board2 = Board(
            state = listOf(
                null, null, null,
                null, PlayerMark.X, null,
                null, null, null,
            )
        )
        val board3 = Board(
            state = listOf(
                null, null, null,
                null, PlayerMark.X, PlayerMark.O,
                null, null, null,
            )
        )
        val board4 = Board(
            state = listOf(
                null, PlayerMark.X, null,
                null, PlayerMark.X, PlayerMark.O,
                null, null, null,
            )
        )
        val board5 = Board(
            state = listOf(
                null, PlayerMark.X, PlayerMark.O,
                null, PlayerMark.X, PlayerMark.O,
                null, null, null,
            )
        )
        val winningBoard = Board(
            state = listOf(
                null, PlayerMark.X, PlayerMark.O,
                null, PlayerMark.X, PlayerMark.O,
                null, PlayerMark.X, null,
            )
        )

        val playerX = mockk<Player>()
        val playerO = mockk<Player>()

        every { playerX.move(board) } returns board2
        every { playerX.move(board3) } returns board4
        every { playerX.move(board5) } returns winningBoard

        every { playerO.move(board2) } returns board3
        every { playerO.move(board4) } returns board5

        val game = Game(board = board, playerX = playerX, playerO = playerO)

        game.playNextMove()
        game.playNextMove()
        game.playNextMove()
        game.playNextMove()
        game.playNextMove()

        assertFalse(game.isActive())

        assertThrows<GameAlreadyCompleteException> {
            game.playNextMove()
        }

        verifyOrder {
            playerX.move(board)
            playerO.move(board2)
            playerX.move(board3)
            playerO.move(board4)
            playerX.move(board5)
        }
    }
}
