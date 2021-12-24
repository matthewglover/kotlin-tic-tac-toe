package digital.fortisgreen.kotlin.tictactoe

sealed class InvalidMove

data class OutOfBounds(val selectedSquare: Int) : InvalidMove()
data class SquareTaken(val selectedSquare: Int) : InvalidMove()
object UnreadableInput : InvalidMove()
