package net.mikka.adventofcode2022

import java.util.*

@Suppress("unused")
class Dec10 : PuzzleDay<Int, String>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<String> {
        return Puzzle02()
    }

    companion object {
        private val cyclesToCheck = listOf(20, 60, 100, 140, 180, 220)
        private fun isRelevantCycle(cycle: Int) = cyclesToCheck.contains(cycle)
    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {

            var combinedSignalStrength = 0
            var cycle = 0
            var register = 1

            for (line in input) {
                cycle++
                if (isRelevantCycle(cycle)) {
                    combinedSignalStrength += cycle * register
                }

                if (line == "noop") {
                    continue
                }
                cycle++
                if (isRelevantCycle(cycle)) {
                    combinedSignalStrength += cycle * register
                }

                val numberToAdd = line.substringAfter(" ").toInt()
                register += numberToAdd
            }

            return combinedSignalStrength
        }
    }

    class Puzzle02 : Puzzle<String> {
        override fun solve(input: List<String>): String {
            var displayOutput = ""

            var cycle = 0
            var register = 1
            for (line in input) {
                cycle++
                displayOutput += printPixel(cycle, register)

                if (line == "noop") {
                    continue
                }
                cycle++
                displayOutput += printPixel(cycle, register)

                val numberToAdd = line.substringAfter(" ").toInt()
                register += numberToAdd
            }

            return wrapDisplay(displayOutput)
        }

        private fun wrapDisplay(displayOutput: String): String {
            val wrappedDisplayOutput = displayOutput.mapIndexed { index, c ->
                if (listOf(40, 80, 120, 160, 200).contains(index + 1)) {
                    "$c\n"
                } else {
                    "$c"
                }
            }.joinToString(separator = "")
            return wrappedDisplayOutput
        }

        private fun printPixel(cycle: Int, register: Int): String {
            return if ((cycle - 1)%40 in register - 1..register + 1) {
                "#"
            } else {
                "."
            }
        }
    }
}

