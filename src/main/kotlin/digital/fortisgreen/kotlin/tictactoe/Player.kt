package digital.fortisgreen.kotlin.tictactoe

class Player(private val ui: UI) {

    fun move(board: Board): Board {
        return ui.requestMove(board)
    }
}
