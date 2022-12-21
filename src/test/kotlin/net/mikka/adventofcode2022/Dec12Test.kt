package net.mikka.adventofcode2022

internal class Dec12Test : AbstractAdventOfCodeTest<Int, Int>() {

    override fun getInput(): String {
        return """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi"""
    }

    override fun getExpectedValueForPuzzle1() = 31

    override fun getExpectedValueForPuzzle2() = 29

}