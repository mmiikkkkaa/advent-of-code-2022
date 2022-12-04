package net.mikka.adventofcode2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Dec01Test {

    private val input =
"""1000
2000
3000

4000

5000
6000

7000
8000
9000

10000""".split("\n")

    @Test
    fun `should solve puzzle 1` () {
        // when

        val result = Dec01.Puzzle01().solve(input)

        // then
        assertThat(result).isEqualTo(24000)
    }

    @Test
    fun `should solve puzzle 2` () {
        // when
        val result = Dec01.Puzzle02().solve(input)

        // then
        assertThat(result).isEqualTo(45000)
    }
}