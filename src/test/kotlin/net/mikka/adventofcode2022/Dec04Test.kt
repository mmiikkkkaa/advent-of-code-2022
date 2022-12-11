package net.mikka.adventofcode2022

internal class Dec04Test : AbstractAdventOfCodeTest<Int, Int>() {

    override fun getInput(): String {
        return """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8"""
    }

    override fun getExpectedValueForPuzzle1() = 2

    override fun getExpectedValueForPuzzle2() = 4

}