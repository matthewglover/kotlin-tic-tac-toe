package digital.fortisgreen.kotlin.tictactoe

import digital.fortisgreen.kotlin.tictactoe.exceptions.GameAlreadyCompleteException
import digital.fortisgreen.kotlin.tictactoe.exceptions.GameNotOverException
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TicTacToeTest {

    @Test
    internal fun `a game is active when it has a board that is active`() {
        val board = mockk<Board>()
        every { board.isActive() } returns true

        val playerX = mockk<Player>()
        val playerO = mockk<Player>()

        val ticTacToeUi = mockk<TicTacToeUI>()

        val newGame = TicTacToe(board = board, playerX = playerX, playerO = playerO, ticTacToeUi = ticTacToeUi)

        assertTrue(newGame.isActive())
    }

    @Test
    internal fun `a game is not active when it has a board that is not active`() {
        val board = mockk<Board>()
        every { board.isActive() } returns false

        val playerX = mockk<Player>()
        val playerO = mockk<Player>()

        val ticTacToeUi = mockk<TicTacToeUI>()

        val game = TicTacToe(board = board, playerX = playerX, playerO = playerO, ticTacToeUi = ticTacToeUi)

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

        val ticTacToeUi = mockk<TicTacToeUI>()

        val game = TicTacToe(board = board, playerX = playerX, playerO = playerO, ticTacToeUi = ticTacToeUi)

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

    @Test
    internal fun `finishing a game with an active board throws a GameNotOverException`() {
        val board = mockk<Board>()
        every { board.isActive() } returns true

        val playerX = mockk<Player>()
        val playerO = mockk<Player>()

        val ticTacToeUi = mockk<TicTacToeUI>()

        val game = TicTacToe(board = board, playerX = playerX, playerO = playerO, ticTacToeUi = ticTacToeUi)

        assertThrows<GameNotOverException> {
            game.finish()
        }
    }

    @ParameterizedTest
    @MethodSource("completedBoards")
    internal fun `an inactive game calls UI endGame with current board`(board: Board) {
        val playerX = mockk<Player>()
        val playerO = mockk<Player>()

        val ticTacToeUi = mockk<TicTacToeUI>()
        every { ticTacToeUi.endGame(board) } just Runs

        val game = TicTacToe(board = board, playerX = playerX, playerO = playerO, ticTacToeUi = ticTacToeUi)

        game.finish()

        verify(exactly = 1) { ticTacToeUi.endGame(board) }
    }

    companion object {
        @JvmStatic
        fun completedBoards(): Stream<Arguments> = Stream.of(
            Arguments.of(
                Board(
                    state = listOf(
                        PlayerMark.X, PlayerMark.X, PlayerMark.O,
                        PlayerMark.O, PlayerMark.O, PlayerMark.X,
                        PlayerMark.X, PlayerMark.O, PlayerMark.X,
                    )
                )
            ),
            Arguments.of(
                Board(
                    state = listOf(
                        PlayerMark.X, PlayerMark.X, PlayerMark.X,
                        PlayerMark.O, PlayerMark.O, null,
                        null, null, null,
                    )
                )
            ),
            Arguments.of(
                Board(
                    state = listOf(
                        PlayerMark.X, PlayerMark.X, null,
                        PlayerMark.O, PlayerMark.O, PlayerMark.O,
                        PlayerMark.X, null, null,
                    )
                )
            ),
        )
    }
}
