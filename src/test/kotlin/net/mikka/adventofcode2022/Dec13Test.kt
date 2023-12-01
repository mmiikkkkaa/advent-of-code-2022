package net.mikka.adventofcode2022

internal class Dec13Test : AbstractAdventOfCodeTest<Int, Int>() {

    override fun getInput(): String {
        return """
            [1,1,3,1,1]
            [1,1,5,1,1]
            
            [[1],[2,3,4]]
            [[1],4]
            
            [9]
            [[8,7,6]]
            
            [[4,4],4,4]
            [[4,4],4,4,4]
            
            [7,7,7,7]
            [7,7,7]
            
            []
            [3]
            
            [[[]]]
            [[]]
            
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]"""
    }

    override fun getExpectedValueForPuzzle1() = 13

    override fun getExpectedValueForPuzzle2() = TODO()

}