package net.mikka.adventofcode2022

internal class Dec05Test : AbstractAdventOfCodeTest<String>() {

    override fun getInput(): String {
        return """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2"""
    }

    override fun getExpectedValueForPuzzle1() = "CMZ"

    override fun getExpectedValueForPuzzle2() = "MCD"

}