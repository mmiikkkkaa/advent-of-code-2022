package net.mikka.adventofcode2022

import kotlin.math.max

class Dec01 : PuzzleDay<Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }
    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {

            var max = 0
            var calories = 0
            for (entry in input) {

                if (entry.isBlank()) {
                    calories = 0
                    continue
                }

                calories+= Integer.valueOf(entry)
                max = max(calories, max)
            }
            return max
        }
    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            val max = mutableListOf(0)
            var index = 0
            for (entry in input) {

                if (entry.isBlank()) {
                    index++
                    max.add(0)
                    continue
                }
                max[index]+= Integer.valueOf(entry)
            }

            max.sortDescending()
            return max.take(3).sum()
        }
    }
}