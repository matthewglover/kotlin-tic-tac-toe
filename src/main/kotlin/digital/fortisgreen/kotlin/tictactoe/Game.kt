package digital.fortisgreen.kotlin.tictactoe

class Game(private val board: Board) {

    fun isActive(): Boolean = !board.hasWinner() && board.hasAvailableMoves()
}
