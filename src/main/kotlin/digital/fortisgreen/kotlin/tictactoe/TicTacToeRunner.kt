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

            val ticTacToe = TicTacToe(
                board = Board.Empty,
                playerX = Player(gameUI),
                playerO = Player(gameUI),
                gameUI = gameUI
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
