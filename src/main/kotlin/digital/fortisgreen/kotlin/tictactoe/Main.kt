import digital.fortisgreen.kotlin.tictactoe.GameSelectionUI
import digital.fortisgreen.kotlin.tictactoe.GameUI
import digital.fortisgreen.kotlin.tictactoe.TicTacToeRunner
import digital.fortisgreen.kotlin.tictactoe.TicTacToeUI

fun main() {
    val gameSelectionUI = GameSelectionUI()
    val gameUi = GameUI()
    val ticTacToeUI = TicTacToeUI(gameSelectionUI, gameUi)

    val runner = TicTacToeRunner(ticTacToeUI)

    runner.run()
}
