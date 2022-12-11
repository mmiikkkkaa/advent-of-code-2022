package net.mikka.adventofcode2022

@Suppress("unused")
class Dec04 : PuzzleDay<Int, Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }

    companion object {
        fun toRange(sections: String): IntRange {
            return sections.substringBefore("-").toInt()..sections.substringAfter("-").toInt()
        }

        fun IntRange.isIn(other: IntRange): Boolean {
            return this.first in other && this.last in other
        }

        fun IntRange.overlap(other: IntRange): Boolean {
            return this.first in other || this.last in other
        }
    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            var overlappingSectionsCount = 0
            for (line in input) {
                val sections1 = toRange(line.substringBefore(","))
                val sections2 = toRange(line.substringAfter(","))

                if (sections1.isIn(sections2) || sections2.isIn(sections1))
                    overlappingSectionsCount++
            }
            return overlappingSectionsCount
        }
    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {

            var overlappingSectionsCount = 0
            for (line in input) {
                val sections1 = toRange(line.substringBefore(","))
                val sections2 = toRange(line.substringAfter(","))

                if (sections1.overlap(sections2) || sections2.overlap(sections1))
                    overlappingSectionsCount++
            }
            return overlappingSectionsCount
        }
    }
}


