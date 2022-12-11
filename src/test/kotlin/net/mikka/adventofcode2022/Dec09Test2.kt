package net.mikka.adventofcode2022

internal class Dec09Test2 : AbstractAdventOfCodeTest<Int, Int>() {

    override fun getInput(): String {
        return """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20"""
    }

    override fun getExpectedValueForPuzzle1() = 88

    override fun getExpectedValueForPuzzle2() = 36

}