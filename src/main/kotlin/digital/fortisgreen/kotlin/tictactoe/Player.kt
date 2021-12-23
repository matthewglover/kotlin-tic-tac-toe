package digital.fortisgreen.kotlin.tictactoe

class Player(private val ui: UI) {

    fun move(board: Board, invalidMoveData: InvalidMoveData? = null): Board {
        val selectedSquare = ui.requestMove(board, invalidMoveData)
        val invalidMoveType = board.validateMove(selectedSquare)

        return if (invalidMoveType == null) {
            board.move(selectedSquare)
        } else {
            move(board, Pair(selectedSquare, invalidMoveType))
        }
    }
}
