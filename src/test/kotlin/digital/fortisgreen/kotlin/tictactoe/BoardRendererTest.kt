@file:Suppress("MaxLineLength", "MaximumLineLength")
package digital.fortisgreen.kotlin.tictactoe

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class BoardRendererTest {

    @Test
    internal fun `renders empty board`() {
        val expected = listOf(
            " ${AnsiColours.YELLOW.apply("1")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.YELLOW.apply("2")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.YELLOW.apply("3")} ",
            AnsiColours.YELLOW.apply("-----------"),
            " ${AnsiColours.YELLOW.apply("4")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.YELLOW.apply("5")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.YELLOW.apply("6")} ",
            AnsiColours.YELLOW.apply("-----------"),
            " ${AnsiColours.YELLOW.apply("7")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.YELLOW.apply("8")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.YELLOW.apply("9")} ",
        )

        assertEquals(expected, BoardRenderer.render(Board.Empty))
    }

    @Test
    internal fun `renders complete board`() {
        val completeBoard = Board(
            state = listOf(
                PlayerMark.X, PlayerMark.X, PlayerMark.O,
                PlayerMark.O, PlayerMark.O, PlayerMark.X,
                PlayerMark.X, PlayerMark.O, PlayerMark.X,
            )
        )

        val expected = listOf(
            " ${AnsiColours.GREEN.apply("X")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.GREEN.apply("X")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.BLUE.apply("O")} ",
            AnsiColours.YELLOW.apply("-----------"),
            " ${AnsiColours.BLUE.apply("O")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.BLUE.apply("O")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.GREEN.apply("X")} ",
            AnsiColours.YELLOW.apply("-----------"),
            " ${AnsiColours.GREEN.apply("X")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.BLUE.apply("O")} ${AnsiColours.YELLOW.apply("|")} ${AnsiColours.GREEN.apply("X")} ",
        )

        assertEquals(expected, BoardRenderer.render(completeBoard))
    }
}
