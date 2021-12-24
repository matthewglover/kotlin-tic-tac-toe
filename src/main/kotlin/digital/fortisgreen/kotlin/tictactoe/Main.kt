import digital.fortisgreen.kotlin.tictactoe.TicTacToeRunner
import digital.fortisgreen.kotlin.tictactoe.TicTacToeUI

fun main() {
    val ticTacToeUi = TicTacToeUI()
    val runner = TicTacToeRunner(ticTacToeUi)

    runner.run()
}
