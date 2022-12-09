package net.mikka.adventofcode2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal abstract class AbstractAdventOfCodeTest<T> {

    abstract fun getInput(): String
    abstract fun getExpectedValueForPuzzle1(): T
    abstract fun getExpectedValueForPuzzle2(): T

    private val puzzle: PuzzleDay<*> = getPuzzle()

    private fun getPuzzle(): PuzzleDay<*> {
        return Class.forName(this.javaClass.name.removeSuffix("Test"))
            .getDeclaredConstructor()
            .newInstance() as PuzzleDay<*>
    }

    @Test
    fun `should solve puzzle 1`() {
        // when

        val result = puzzle.getPuzzle1().solve(getInput().trimIndent().split("\n"))

        // then
        assertThat(result).isEqualTo(getExpectedValueForPuzzle1())
    }

    @Test
    fun `should solve puzzle 2`() {
        // when
        val result = puzzle.getPuzzle2().solve(getInput().trimIndent().split("\n"))

        // then
        assertThat(result).isEqualTo(getExpectedValueForPuzzle2())
    }
}