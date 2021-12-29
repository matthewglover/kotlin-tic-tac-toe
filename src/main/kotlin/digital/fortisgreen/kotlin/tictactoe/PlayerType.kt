package digital.fortisgreen.kotlin.tictactoe

sealed class PlayerType {
    abstract fun description(): String
}

object Human : PlayerType() {
    override fun description(): String = "Human Player"
}
