package net.mikka.adventofcode2022

internal class Dec03Test : AbstractAdventOfCodeTest<Int, Int>() {

    override fun getInput(): String {
        return """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw"""
    }

    override fun getExpectedValueForPuzzle1() = 157

    override fun getExpectedValueForPuzzle2() = 70

}