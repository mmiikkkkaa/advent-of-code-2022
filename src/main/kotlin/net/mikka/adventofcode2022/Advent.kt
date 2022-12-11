package net.mikka.adventofcode2022

fun main() {

    for (day in 1..24) {
        try {
            val puzzle = Class.forName("net.mikka.adventofcode2022.Dec${formatDay(day)}").getDeclaredConstructor().newInstance()
            (puzzle as PuzzleDay<*,*>).solve()
        } catch (e: Exception) {
            return
        }
    }
}

private fun formatDay(day: Int) = if (day <= 9) "0${day}" else day.toString()