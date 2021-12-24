package digital.fortisgreen.kotlin.tictactoe

class Player(private val ticTacToeUi: TicTacToeUI) {

    fun move(board: Board): Board {
        return ticTacToeUi.requestMove(board)
    }
}
