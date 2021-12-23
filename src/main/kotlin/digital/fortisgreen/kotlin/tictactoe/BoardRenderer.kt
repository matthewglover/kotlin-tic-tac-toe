package digital.fortisgreen.kotlin.tictactoe

class BoardRenderer {

    companion object {
        const val RowDivider = " -   -   - "
    }

    fun render(board: Board): List<String> =
        renderRows(board).intersperse(RowDivider)

    @Suppress("MagicNumber")
    private fun renderRows(board: Board): List<String> =
        board.state.chunked(3).mapIndexed { rowIndex, row ->
            row.mapIndexed { squareIndex, square -> renderSquare(rowIndex, squareIndex + 1, square) }
        }.map { row ->
            " ${row[0]} | ${row[1]} | ${row[2]} "
        }

    @Suppress("MagicNumber")
    private fun renderSquare(rowIndex: Int, squareNumber: Int, square: PlayerMark?): String =
        square?.toString() ?: (rowIndex * 3 + squareNumber).toString()

    private fun List<String>.intersperse(toIntersperse: String): List<String> =
        subList(0, size - 1).flatMap { str -> listOf(str, toIntersperse) } + last()
}
