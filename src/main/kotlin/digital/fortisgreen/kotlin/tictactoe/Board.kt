package digital.fortisgreen.kotlin.tictactoe

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import digital.fortisgreen.kotlin.tictactoe.exceptions.InvalidBoardCreationException

@Suppress("TooManyFunctions")
data class Board(val state: List<PlayerMark?>) {

    companion object {

        const val TotalSquares: Int = 9
        val Empty: Board = Board(emptyState())

        private fun emptyState(): List<PlayerMark?> = (0 until TotalSquares).map { null }
    }

    init {
        if (state.size != TotalSquares) {
            throw InvalidBoardCreationException(
                "Incorrect number of squares in state: $state. " +
                    "Expected $TotalSquares,  received: ${state.size}"
            )
        }

        val xs = state.filter { square -> square == PlayerMark.X }
        val os = state.filter { square -> square == PlayerMark.O }

        if (os.size > xs.size) {
            throw InvalidBoardCreationException("More Os than Xs")
        }

        if (xs.size > os.size + 1) {
            throw InvalidBoardCreationException("Too many Xs (${xs.size}) compared to Os (${os.size})")
        }
    }

    fun isActive(): Boolean = hasAvailableMoves() && !hasWinner()

    fun nextPlayer(): PlayerMark? {
        if (!isActive()) {
            return null
        }

        return when (totalMoves() % 2) {
            0 -> PlayerMark.X
            else -> PlayerMark.O
        }
    }

    fun move(selectedSquare: Int?): Either<InvalidMove, Board> =
        if (selectedSquare == null) {
            UnreadableInput.left()
        } else if (!isOnBoard(selectedSquare)) {
            OutOfBounds(selectedSquare).left()
        } else if (isTaken(selectedSquare)) {
            SquareTaken(selectedSquare).left()
        } else {
            val playerMark = nextPlayer()
            val newState = state.mapIndexed { currentIndex, squareValue ->
                if (currentIndex + 1 == selectedSquare) playerMark else squareValue
            }
            Board(newState).right()
        }

    fun winner(): PlayerMark? = lines().find { it.isWinner() }?.playerMark()

    private fun isOnBoard(selectedSquare: Int): Boolean = (1..TotalSquares).contains(selectedSquare)

    private fun isTaken(selectedSquare: Int): Boolean = state[selectedSquare - 1] != null

    private fun hasWinner(): Boolean = lines().any { it.isWinner() }

    private fun hasAvailableMoves(): Boolean = totalMoves() < TotalSquares

    private fun totalMoves(): Int = state.filterNotNull().size

    private fun lines() = rows() + cols() + diagonals()

    @Suppress("MagicNumber")
    private fun rows(): List<Line> = listOf(
        state.subList(0, 3).toLine(),
        state.subList(3, 6).toLine(),
        state.subList(6, 9).toLine(),
    )

    @Suppress("MagicNumber")
    private fun cols(): List<Line> = (0 until 3).map { colOffset ->
        (0 until 9).filter { it % 3 == colOffset }.map { state[it] }.toLine()
    }

    @Suppress("MagicNumber")
    private fun diagonals(): List<Line> = listOf(
        listOf(0, 4, 8).map { state[it] }.toLine(),
        listOf(2, 4, 6).map { state[it] }.toLine()
    )

    private fun List<PlayerMark?>.toLine(): Line = Line(this)
}
