package net.mikka.adventofcode2022

import kotlin.streams.toList

@Suppress("unused")
class Dec03 : PuzzleDay<Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }

    companion object {
        fun findDuplicate(compartment1: String, compartment2: String): List<Char> {
            return compartment1.chars().toList().toSet().intersect(compartment2.chars().distinct().toList().toSet())
                .map { it.toChar() }

        }
    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            var prioSum = 0

            for (line in input) {
                val compartment1 = line.substring(0, line.length / 2)
                val compartment2 = line.substring(line.length / 2)

                val code = findDuplicate(compartment1, compartment2).single().code
                if (code in 'a'.code..'z'.code) prioSum += (code - 'a'.code + 1)
                if (code in 'A'.code..'Z'.code) prioSum += (code - 'A'.code + 27)

            }

            return prioSum
        }
    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            var prioSum = 0

            var index = 0
            while (index + 2 < input.size) {
                val elf1 = input[index]
                val elf2 = input[index + 1]
                val elf3 = input[index + 2]
                index += 3

                val code = findDuplicate(findDuplicate(elf1, elf2).toString(), elf3).single().code
                if (code in 'a'.code..'z'.code) prioSum += (code - 'a'.code + 1)
                if (code in 'A'.code..'Z'.code) prioSum += (code - 'A'.code + 27)

            }

            return prioSum
        }
    }
}


