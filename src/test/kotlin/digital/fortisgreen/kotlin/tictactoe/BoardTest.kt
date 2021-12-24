package digital.fortisgreen.kotlin.tictactoe

import arrow.core.left
import arrow.core.right
import digital.fortisgreen.kotlin.tictactoe.exceptions.InvalidBoardCreationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

private val completeBoard = Board(
    state = listOf(
        PlayerMark.X, PlayerMark.X, PlayerMark.O,
        PlayerMark.O, PlayerMark.O, PlayerMark.X,
        PlayerMark.X, PlayerMark.O, PlayerMark.X,
    )
)

internal class BoardTest {

    @Test
    internal fun `creating a board with less than 9 items throws InvalidBoardCreationException`() {
        assertThrows<InvalidBoardCreationException> { Board(listOf()) }

        assertThrows<InvalidBoardCreationException> {
            Board(
                state = (0 until 8).map { null }
            )
        }
    }

    @Test
    internal fun `creating a board with more than 9 items throws InvalidBoardCreationException`() {
        assertThrows<InvalidBoardCreationException> {
            Board(
                state = (0 until 10).map { null }
            )
        }
    }

    @Test
    internal fun `creating a board with more Ys than Xs throws InvalidBoardCreationException`() {
        assertThrows<InvalidBoardCreationException> {
            Board(
                state = listOf(
                    PlayerMark.O, PlayerMark.O, PlayerMark.O,
                    PlayerMark.X, PlayerMark.X, null,
                    null, null, null,
                )
            )
        }
    }

    @Test
    internal fun `creating a board with more than one extra X compared to Ys throws InvalidBoardCreationException`() {
        assertThrows<InvalidBoardCreationException> {
            Board(
                state = listOf(
                    PlayerMark.X, PlayerMark.X, PlayerMark.X,
                    PlayerMark.O, null, null,
                    null, null, null,
                )
            )
        }
    }

    @Test
    internal fun `an empty board is active`() {
        val board = Board.Empty
        assertTrue(board.isActive())
    }

    @Test
    internal fun `a board with all squares filled is not active`() {
        val board = completeBoard

        assertFalse(board.isActive())
    }

    @ParameterizedTest
    @MethodSource("provideCompleteBoards")
    internal fun `a board with a complete row or column or diagonal of Xs or Os is not active`(
        boardState: List<PlayerMark?>
    ) {
        val board = Board(state = boardState)

        assertFalse(board.isActive())
    }

    @Test
    internal fun `next player is 'X' when board is empty`() {
        val board = Board.Empty

        assertEquals(PlayerMark.X, board.nextPlayer())
    }

    @Test
    internal fun `next player is 'O' when number of squares taken is odd`() {
        val board = Board(
            state = listOf(
                null, null, null,
                null, PlayerMark.X, null,
                null, null, null
            )
        )

        assertEquals(PlayerMark.O, board.nextPlayer())
    }

    @Test
    internal fun `next player is null when board has winner`() {
        assertNull(completeBoard.nextPlayer())
    }

    @Test
    internal fun `next player is null when board is complete`() {
        val winningBoard = Board(
            state = listOf(
                null, PlayerMark.X, PlayerMark.O,
                null, PlayerMark.X, PlayerMark.O,
                null, PlayerMark.X, null,
            )
        )

        assertNull(winningBoard.nextPlayer())
    }

    @Test
    internal fun `next player is 'X' when number of squares taken is even`() {
        val board = Board(
            state = listOf(
                null, null, null,
                null, PlayerMark.X, PlayerMark.O,
                null, null, null
            )
        )

        assertEquals(PlayerMark.X, board.nextPlayer())
    }

    @Test
    internal fun `making a valid move return new board with square taken by next player`() {
        val board = Board(
            state = listOf(
                null, null, null,
                null, null, null,
                null, null, null
            )
        )

        val updatedBoard = Board(
            state = listOf(
                null, null, null,
                null, null, null,
                null, null, PlayerMark.X
            )
        )

        assertEquals(updatedBoard.right(), board.move(9))
    }

    @ParameterizedTest
    @ValueSource(ints = [5, 6])
    internal fun `making a move to a taken square returns a right of SquareTaken`(square: Int) {
        val board = Board(
            state = listOf(
                null, null, null,
                null, PlayerMark.X, PlayerMark.O,
                null, null, null
            )
        )

        assertEquals(SquareTaken(square).left(), board.move(square))
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 10, -1])
    internal fun `making a move to a square out of range returns a right of OutOfBounds`(square: Int) {
        assertEquals(OutOfBounds(square).left(), Board.Empty.move(square))
    }

    @Test
    internal fun `making a null move returns right of UnreadableInput`() {
        assertEquals(UnreadableInput.left(), Board.Empty.move(null))
    }

    @ParameterizedTest
    @MethodSource("provideWinningBoards")
    internal fun `a winning board reports winner`(expected: PlayerMark, board: Board) {
        assertEquals(expected, board.winner())
    }

    @Test
    internal fun `a drawn board does not have a winner`() {
        assertNull(completeBoard.winner())
    }

    @Test
    internal fun `an incomplete board does not have a winner`() {
        assertNull(Board.Empty.winner())
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun provideCompleteBoards() = Stream.of(
            listOf(
                PlayerMark.X, PlayerMark.X, PlayerMark.X,
                PlayerMark.O, PlayerMark.O, null,
                null, null, null,
            ),
            listOf(
                PlayerMark.O, PlayerMark.O, null,
                PlayerMark.X, PlayerMark.X, PlayerMark.X,
                null, null, null,
            ),
            listOf(
                null, null, null,
                PlayerMark.O, PlayerMark.O, null,
                PlayerMark.X, PlayerMark.X, PlayerMark.X,
            ),
            listOf(
                PlayerMark.O, null, null,
                PlayerMark.O, PlayerMark.X, null,
                PlayerMark.O, PlayerMark.X, PlayerMark.X,
            ),
            listOf(
                PlayerMark.X, PlayerMark.O, null,
                null, PlayerMark.O, PlayerMark.X,
                null, PlayerMark.O, PlayerMark.X,
            ),
            listOf(
                PlayerMark.X, PlayerMark.O, null,
                null, PlayerMark.X, PlayerMark.O,
                null, PlayerMark.O, PlayerMark.X,
            ),
            listOf(
                null, PlayerMark.O, PlayerMark.X,
                null, PlayerMark.X, PlayerMark.O,
                PlayerMark.X, PlayerMark.O, null,
            ),
        )

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun provideWinningBoards() = Stream.of(
            Arguments.of(
                PlayerMark.X,
                Board(
                    state = listOf(
                        PlayerMark.X, PlayerMark.X, PlayerMark.X,
                        PlayerMark.O, PlayerMark.O, null,
                        null, null, null,
                    )
                )
            ),
            Arguments.of(
                PlayerMark.O,
                Board(
                    state = listOf(
                        PlayerMark.O, PlayerMark.O, PlayerMark.O,
                        PlayerMark.X, PlayerMark.X, null,
                        PlayerMark.X, null, null,
                    )
                )
            ),
        )
    }
}
