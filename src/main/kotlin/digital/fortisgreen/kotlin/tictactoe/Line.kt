package digital.fortisgreen.kotlin.tictactoe

import digital.fortisgreen.kotlin.tictactoe.exceptions.LineCreationException

class Line(private val state: List<PlayerMark?>) {

    companion object {
        private const val LineSize = 3
    }

    init {
        if (state.size != LineSize) {
            throw LineCreationException(
                "Invalid line size. Expected: $LineSize, " +
                    "received: ${state.size}"
            )
        }
    }

    fun isWinner(): Boolean {
        return state.all { it != null } && state.distinct().size == 1
    }

    fun playerMark(): PlayerMark? = if (isWinner()) state.first() else null
}
