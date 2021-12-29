package digital.fortisgreen.kotlin.tictactoe

class Player(private val gameUi: GameUI) {

    fun move(board: Board): Board {
        return gameUi.requestMove(board)
    }
}
