package digital.fortisgreen.kotlin.tictactoe

import arrow.core.Either
import arrow.core.left
import arrow.core.right

class GameSelectionUI {

    fun requestGameType(gameTypes: List<GameType>, errorMessage: String? = null): GameType {
        clearScreen()
        displayGameTypes(gameTypes)
        errorMessage?.run { displayInvalidGameTypeTip(errorMessage) }

        return toGameType(gameTypes, requestGameTypeInput())
            .fold(
                { newErrorMessage -> requestGameType(gameTypes, newErrorMessage) },
                { it }
            )
    }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    private fun toGameType(gameTypes: List<GameType>, gameTypeInput: Int?): Either<String, GameType> {
        return try {
            gameTypes[gameTypeInput!! - 1].right()
        } catch (exception: RuntimeException) {
            "select single digit".left()
        }
    }

    private fun displayGameTypes(gameTypes: List<GameType>) {
        gameTypes.forEachIndexed { index, gameType ->
            println(AnsiColours.YELLOW.apply("${index + 1}. ${gameType.description()}"))
        }
    }

    private fun displayInvalidGameTypeTip(errorMessage: String) {
        println("Oops invalid input: $errorMessage")
    }

    private fun requestGameTypeInput(): Int? {
        print("Select game type: ")

        return readln().toIntOrNull()
    }

    private fun clearScreen() {
        print("\u001b[H\u001b[2J")
    }
}
