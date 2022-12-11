package net.mikka.adventofcode2022

internal class Dec02Test : AbstractAdventOfCodeTest<Int, Int>() {

    override fun getInput(): String {
        return """A Y
B X
C Z"""
    }

    override fun getExpectedValueForPuzzle1() = 15

    override fun getExpectedValueForPuzzle2() = 12

}