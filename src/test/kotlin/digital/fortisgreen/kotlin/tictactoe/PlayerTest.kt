package digital.fortisgreen.kotlin.tictactoe

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PlayerTest {

    @Test
    internal fun `player call ui for next move`() {
        val board = Board.Empty

        val nextBoard = Board(
            state = listOf(
                null, PlayerMark.X, null,
                null, null, null,
                null, null, null,
            )
        )

        val ticTacToeUi = mockk<TicTacToeUI>()
        every { ticTacToeUi.requestMove(board) } returns nextBoard

        val player = Player(ticTacToeUi)

        assertEquals(nextBoard, player.move(board))

        verify(exactly = 1) { ticTacToeUi.requestMove(board, null) }
    }
}
