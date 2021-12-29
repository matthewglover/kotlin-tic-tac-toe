package digital.fortisgreen.kotlin.tictactoe

class GameUI {

    fun requestMove(board: Board, invalidMove: InvalidMove? = null): Board {
        clearScreen()

        displayBoard(board)

        invalidMove?.run { displayInvalidMoveTip(invalidMove) }

        val selectedSquare = requestMoveInput()

        return board.move(selectedSquare)
            .fold(
                { newInvalidMove -> requestMove(board, newInvalidMove) },
                { it }
            )
    }

    fun endGame(board: Board) {
        clearScreen()

        displayBoard(board)

        val winner = board.winner()

        if (winner == null) {
            displayDrawMessage()
        } else {
            displayWinnerMessage(winner)
        }
    }

    private fun requestMoveInput(): Int? {
        print("Enter your move: ")

        return readln().toIntOrNull()
    }

    private fun displayInvalidMoveTip(invalidMove: InvalidMove) {
        when (invalidMove) {
            is OutOfBounds ->
                println("Oops square `${invalidMove.selectedSquare}` is not on the board. Please try again.")
            is SquareTaken ->
                println("Oops square `${invalidMove.selectedSquare}` is already taken. Please try again.")
            is UnreadableInput ->
                println("Oops could not read input as a square. Please try again.")
        }
    }

    private fun displayBoard(board: Board) {
        val renderedBoard = BoardRenderer.render(board)

        renderedBoard.forEach { println(it) }
    }

    private fun clearScreen() {
        print("\u001b[H\u001b[2J")
    }

    private fun displayDrawMessage() {
        println("It's a draw!")
    }

    private fun displayWinnerMessage(winner: PlayerMark) {
        println("$winner wins!")
    }
}
