package digital.fortisgreen.kotlin.tictactoe

@Suppress("UnusedPrivateMember")
data class Board(private val state: List<PlayerMark?>) {
    fun hasWinner(): Boolean {
        TODO("Implement has winner")
    }

    fun hasAvailableMoves(): Boolean {
        TODO("Implement has available moves")
    }
}
