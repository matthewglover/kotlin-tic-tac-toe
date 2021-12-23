package digital.fortisgreen.kotlin.tictactoe

enum class AnsiColours(private val ansi: String) {

    BLUE("\u001B[34m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    WHITE("\u001B[37m");

    companion object {
        private const val Reset = "\u001B[0m"
    }

    fun apply(str: String): String = "$ansi$str$Reset"
}
