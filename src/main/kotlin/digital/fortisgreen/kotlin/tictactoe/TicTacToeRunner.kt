package digital.fortisgreen.kotlin.tictactoe

class TicTacToeRunner(private val ticTacToeUi: TicTacToeUI) {

    fun run() {
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
    }
}
