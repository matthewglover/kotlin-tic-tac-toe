import digital.fortisgreen.kotlin.tictactoe.Board
import digital.fortisgreen.kotlin.tictactoe.TicTacToe
import digital.fortisgreen.kotlin.tictactoe.Player
import digital.fortisgreen.kotlin.tictactoe.UI

fun main() {

    val ui = UI()
    val game = TicTacToe(board = Board.Empty, playerX = Player(ui), playerO = Player(ui), ui = ui)

    while (game.isActive()) {
        game.playNextMove()
    }

    game.finish()
}
