package digital.fortisgreen.kotlin.tictactoe

import digital.fortisgreen.kotlin.tictactoe.exceptions.TicTacToeException

class TicTacToeRunner(ui: TicTacToeUI) {

    companion object {

        private val gameTypes: List<GameType> = listOf(
            GameType(playerX = Human, playerO = Human)
        )
    }

    private val gameSelectionUI = ui.gameSelectionUI
    private val gameUI = ui.gameUI

    @Suppress("UnusedPrivateMember")
    fun run() {
        try {
            val gameType = gameSelectionUI.requestGameType(gameTypes)

            val game = Game(
                board = Board.Empty,
                playerX = Player(gameUI),
                playerO = Player(gameUI),
                gameUI = gameUI
            )

            while (game.isActive()) {
                game.playNextMove()
            }

            game.finish()
        } catch (exception: TicTacToeException) {
            println("Oops, something's gone wrong: ${exception.message}".asErrorMessage())
        }
    }

    private fun String.asErrorMessage(): String = AnsiColours.RED.apply(this)
}
