package net.mikka.adventofcode2022

internal class Dec08Test : AbstractAdventOfCodeTest<Int>() {

    override fun getInput(): String {
        return """
            30373
            25512
            65332
            33549
            35390"""
    }

    override fun getExpectedValueForPuzzle1() = 21

    override fun getExpectedValueForPuzzle2() = 8

}