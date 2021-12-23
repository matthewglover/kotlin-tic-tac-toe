package digital.fortisgreen.kotlin.tictactoe

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class BoardRendererTest {
    @Test
    internal fun `renders empty board`() {
        val boardRenderer = BoardRenderer()

        val expected = listOf(
            " 1 | 2 | 3 ",
            " -   -   - ",
            " 4 | 5 | 6 ",
            " -   -   - ",
            " 7 | 8 | 9 ",
        )

        assertEquals(expected, boardRenderer.render(Board.Empty))
    }

    @Test
    internal fun `renders complete board`() {
        val boardRenderer = BoardRenderer()

        val completeBoard = Board(
            state = listOf(
                PlayerMark.X, PlayerMark.X, PlayerMark.O,
                PlayerMark.O, PlayerMark.O, PlayerMark.X,
                PlayerMark.X, PlayerMark.O, PlayerMark.X,
            )
        )

        val expected = listOf(
            " X | X | O ",
            " -   -   - ",
            " O | O | X ",
            " -   -   - ",
            " X | O | X ",
        )

        assertEquals(expected, boardRenderer.render(completeBoard))
    }
}
