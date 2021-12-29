package digital.fortisgreen.kotlin.tictactoe

data class GameType(val playerX: PlayerType, val playerO: PlayerType) {
    fun description(): String = "${playerX.description()} vs ${playerO.description()}"
}
