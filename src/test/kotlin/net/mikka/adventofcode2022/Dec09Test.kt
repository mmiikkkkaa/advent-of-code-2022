package net.mikka.adventofcode2022

internal class Dec09Test : AbstractAdventOfCodeTest<Int, Int>() {

    override fun getInput(): String {
        return """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2"""
    }

    override fun getExpectedValueForPuzzle1() = 13

    override fun getExpectedValueForPuzzle2() = 1

}