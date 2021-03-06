package digital.fortisgreen.kotlin.tictactoe

import digital.fortisgreen.kotlin.tictactoe.exceptions.GameAlreadyCompleteException
import digital.fortisgreen.kotlin.tictactoe.exceptions.GameNotOverException

class Game(
    private var board: Board,
    private val playerX: Player,
    private val playerO: Player,
    private val gameUI: GameUI
) {

    fun isActive(): Boolean = board.isActive()

    fun playNextMove() {
        board.nextPlayer()?.let { playerMark ->
            board = playerForMark(playerMark).move(board)
        } ?: throw GameAlreadyCompleteException("Attempting to play next move on a completed game is not allowed.")
    }

    private fun playerForMark(playerMark: PlayerMark): Player = when (playerMark) {
        PlayerMark.X -> playerX
        PlayerMark.O -> playerO
    }

    fun finish() {
        if (isActive()) {
            throw GameNotOverException("Cannot finish game when it is still active")
        }

        gameUI.endGame(board)
    }
}
