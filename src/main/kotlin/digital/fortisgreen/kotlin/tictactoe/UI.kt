package digital.fortisgreen.kotlin.tictactoe

typealias InvalidMoveData = Pair<Int?, InvalidMoveType>

class UI(private val boardRenderer: BoardRenderer) {

    fun requestMove(board: Board, invalidMoveData: InvalidMoveData?): Int {
        clearScreen()

        displayBoard(board)

        invalidMoveData?.run { displayInvalidMoveTip(invalidMoveData) }

        return requestInput() ?: requestMove(board, Pair(null, InvalidMoveType.UNREADABLE_INPUT))
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

    private fun requestInput(): Int? {
        print("Enter your move: ")

        return readln().toIntOrNull()
    }

    private fun displayInvalidMoveTip(invalidMoveData: InvalidMoveData) {
        val (invalidMove, invalidMoveType) = invalidMoveData

        when (invalidMoveType) {
            InvalidMoveType.OUT_OF_BOUNDS ->
                println("Oops square `$invalidMove` is not on the board. Please try again.")
            InvalidMoveType.SQUARE_TAKEN ->
                println("Oops square `$invalidMove` is already taken. Please try again.")
            InvalidMoveType.UNREADABLE_INPUT ->
                println("Oops could not read input as a square. Please try again.")
        }
    }

    private fun displayBoard(board: Board) {
        val renderedBoard = boardRenderer.render(board)

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
