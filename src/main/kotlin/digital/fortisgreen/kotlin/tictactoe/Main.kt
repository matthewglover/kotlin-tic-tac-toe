import digital.fortisgreen.kotlin.tictactoe.Board
import digital.fortisgreen.kotlin.tictactoe.BoardRenderer
import digital.fortisgreen.kotlin.tictactoe.Game
import digital.fortisgreen.kotlin.tictactoe.Player
import digital.fortisgreen.kotlin.tictactoe.UI

fun main() {

    val boardRenderer = BoardRenderer()
    val ui = UI(boardRenderer)
    val game = Game(board = Board.Empty, playerX = Player(ui), playerO = Player(ui), ui = ui)

    while (game.isActive()) {
        game.playNextMove()
    }

    game.finish()
}
