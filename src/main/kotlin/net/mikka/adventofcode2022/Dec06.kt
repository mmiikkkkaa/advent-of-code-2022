package net.mikka.adventofcode2022

@Suppress("unused")
class Dec06 : PuzzleDay<Int, Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }

    companion object {
     
    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {

            var index = 0
            var charWindow = ""
            for (cc in input.first().chars()) {
                val c = cc.toChar().toString()

                if (charWindow.length < 4) {
                    charWindow += c
                } else {
                    charWindow = charWindow.substring(1) + c

                    if (charWindow.chars().distinct().count() == charWindow.chars().count()) {
                        return index + 1
                    }
                }

                index++
            }

            throw IllegalStateException()
        }
    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            var index = 0
            var charWindow = ""
            for (cc in input.first().chars()) {
                val c = cc.toChar().toString()

                if (charWindow.length < 14) {
                    charWindow += c
                } else {
                    charWindow = charWindow.substring(1) + c

                    if (charWindow.chars().distinct().count() == charWindow.chars().count()) {
                        return index + 1
                    }
                }

                index++
            }

            throw IllegalStateException()
        }
    }
}
