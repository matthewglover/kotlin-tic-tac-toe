package digital.fortisgreen.kotlin.tictactoe

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PlayerTest {

    @Test
    internal fun `when player selects a valid move the first time, returns new board with move updated`() {
        val board = Board.Empty

        val ui = mockk<UI>()
        every { ui.requestMove(board, null) } returns 2

        val player = Player(ui)

        val boardAfterMove = Board(
            state = listOf(
                null, PlayerMark.X, null,
                null, null, null,
                null, null, null,
            )
        )

        assertEquals(boardAfterMove, player.move(board))

        verifyOrder {
            ui.requestMove(board, null)
        }
    }

    @Test
    internal fun `when player selects an invalid move the first time, alerts user then requests valid move`() {
        val board = Board.Empty

        val ui = mockk<UI>()
        every { ui.requestMove(board, any()) } answers { if (secondArg<InvalidMoveData?>() == null) 10 else 2 }

        val player = Player(ui)

        val boardAfterMove = Board(
            state = listOf(
                null, PlayerMark.X, null,
                null, null, null,
                null, null, null,
            )
        )

        assertEquals(boardAfterMove, player.move(board))

        verifyOrder {
            ui.requestMove(board, null)
            ui.requestMove(board, Pair(10, InvalidMoveType.OUT_OF_BOUNDS))
        }
    }
}
