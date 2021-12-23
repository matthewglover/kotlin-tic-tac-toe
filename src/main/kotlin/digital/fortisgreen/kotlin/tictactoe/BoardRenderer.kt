package digital.fortisgreen.kotlin.tictactoe

class BoardRenderer {

    @Suppress("MagicNumber")
    fun render(board: Board): List<String> =
        board.state.chunked(3).mapIndexed { rowIndex, row ->
            row.mapIndexed { squareIndex, square -> renderSquare(rowIndex, squareIndex + 1, square) }
        }.map { row ->
            " ${row[0]} | ${row[1]} | ${row[2]} "
        }

    @Suppress("MagicNumber")
    private fun renderSquare(rowIndex: Int, squareNumber: Int, square: PlayerMark?): String =
        square?.toString() ?: (rowIndex * 3 + squareNumber).toString()
}
