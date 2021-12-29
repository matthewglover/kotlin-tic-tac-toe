package digital.fortisgreen.kotlin.tictactoe

import digital.fortisgreen.kotlin.tictactoe.exceptions.TicTacToeException

class TicTacToeRunner(private val ticTacToeUi: TicTacToeUI) {

    fun run() {
        try {
            val ticTacToe = TicTacToe(
                board = Board.Empty,
                playerX = Player(ticTacToeUi),
                playerO = Player(ticTacToeUi),
                ticTacToeUi = ticTacToeUi
            )

            while (ticTacToe.isActive()) {
                ticTacToe.playNextMove()
            }

            ticTacToe.finish()
        } catch (exception: TicTacToeException) {
            println("Oops, something's gone wrong: ${exception.message}".asErrorMessage())
        }
    }

    private fun String.asErrorMessage(): String = AnsiColours.RED.apply(this)
}
