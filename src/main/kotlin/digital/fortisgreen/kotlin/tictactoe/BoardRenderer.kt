package digital.fortisgreen.kotlin.tictactoe

object BoardRenderer {

    val RowDivider = AnsiColours.YELLOW.apply("-----------")
    val ColumnDivider = AnsiColours.YELLOW.apply("|")

    fun render(board: Board): List<String> =
        renderRows(board).intersperse(RowDivider)

    @Suppress("MagicNumber")
    private fun renderRows(board: Board): List<String> =
        board.state.chunked(3).mapIndexed { rowIndex, row ->
            row.mapIndexed { squareIndex, square -> renderSquare(rowIndex, squareIndex + 1, square) }
        }.map { row ->
            " ${row[0]} $ColumnDivider ${row[1]} $ColumnDivider ${row[2]} "
        }

    @Suppress("MagicNumber")
    private fun renderSquare(rowIndex: Int, squareNumber: Int, square: PlayerMark?): String =
        square?.let(::renderPlayerMark) ?: renderSquareNumber(rowIndex * 3 + squareNumber)

    private fun renderSquareNumber(squareNumber: Int): String =
        AnsiColours.YELLOW.apply(squareNumber.toString())

    private fun renderPlayerMark(playerMark: PlayerMark): String = when (playerMark) {
        PlayerMark.X -> AnsiColours.GREEN.apply(playerMark.toString())
        PlayerMark.O -> AnsiColours.BLUE.apply(playerMark.toString())
    }

    private fun List<String>.intersperse(toIntersperse: String): List<String> =
        subList(0, size - 1).flatMap { str -> listOf(str, toIntersperse) } + last()
}
