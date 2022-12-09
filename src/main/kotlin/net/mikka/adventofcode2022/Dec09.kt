package net.mikka.adventofcode2022

import java.util.*
import kotlin.math.abs

@Suppress("unused")
class Dec09 : PuzzleDay<Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }

    companion object {
        private fun getDirection(line: String): Direction {
            val direction = when (line.substring(0, 1)) {
                "R" -> Direction(1, 0)
                "D" -> Direction(0, -1)
                "L" -> Direction(-1, 0)
                "U" -> Direction(0, 1)
                else -> throw IllegalArgumentException()
            }
            return direction
        }

        fun moveHead(head: Position, direction: Direction): Position {
            return Position(head.x + direction.x, head.y + direction.y, "H")
        }

        fun moveTail(head: Position, tail: Position): Position {
            var x = tail.x
            var y = tail.y

            if (isMoveNeeded(head, tail)) {
                if (isStraightMove(head, tail)) {
                    if (abs(head.x - tail.x) == 2)
                        x = (head.x + tail.x) / 2
                    if (abs(head.y - tail.y) == 2)
                        y = (head.y + tail.y) / 2
                } else {
                    if (abs(head.x - tail.x) != 1 || abs(head.y - tail.y) != 1) {
                        if (abs(head.x - tail.x) == 2 && abs(head.y - tail.y) == 2) {
                            x = (head.x + tail.x) / 2
                            y = (head.y + tail.y) / 2
                        } else if (abs(head.x - tail.x) == 2) {
                            x = (head.x + tail.x) / 2
                            y = head.y
                        } else {
                            x = head.x
                            y = (head.y + tail.y) / 2
                        }
                    }
                }
            }

            return Position(x, y, tail.name)
        }

        private fun isMoveNeeded(head: Position, tail: Position): Boolean = abs(head.x - tail.x) > 1 || abs(head.y - tail.y) > 1
        private fun isStraightMove(head: Position, tail: Position): Boolean = head.x == tail.x || head.y == tail.y

        private fun print(vararg knots: Position) {
            val s = listOf(*knots).plus(Position(0, 0, "s"))

            val minX = s.minOf { it.x }
            val maxX = s.maxOf { it.x }
            val minY = s.minOf { it.y }
            val maxY = s.maxOf { it.y }


            println()
            for (y in maxY downTo minY) {
                for (x in minX..maxX) {
                    val current = s.find { it.x == x && it.y == y }

                    if (current != null) {
                        print(current.name)
                    } else {
                        print(".")
                    }
                }
                println()
            }
            println()
        }
    }

    data class Position(val x: Int, val y: Int, val name: String)
    data class Direction(val x: Int, val y: Int)

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            val visitedTailPositions = mutableSetOf<Position>()

            var head = Position(0, 0, "H")
            var tail = Position(0, 0, "T")

            for (line in input) {

                val direction = getDirection(line)
                for (i in 1..line.substringAfter(" ").toInt()) {
                    head = moveHead(head, direction)
                    tail = moveTail(head, tail)
                    visitedTailPositions.add(Position(tail.x, tail.y, "#"))

//                    print(head, tail, *visitedTailPositions.toTypedArray())
                }
            }


            return visitedTailPositions.size
        }

    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            val visitedTailPositions = mutableSetOf<Position>()

            var head = Position(0, 0, "H")
            var knot1 = Position(0, 0, "1")
            var knot2 = Position(0, 0, "2")
            var knot3 = Position(0, 0, "3")
            var knot4 = Position(0, 0, "4")
            var knot5 = Position(0, 0, "5")
            var knot6 = Position(0, 0, "6")
            var knot7 = Position(0, 0, "7")
            var knot8 = Position(0, 0, "8")
            var knot9 = Position(0, 0, "9")

            for (line in input) {
                val direction = getDirection(line)
                for (i in 1..line.substringAfter(" ").toInt()) {
                    head = moveHead(head, direction)
                    knot1 = moveTail(head, knot1)
                    knot2 = moveTail(knot1, knot2)
                    knot3 = moveTail(knot2, knot3)
                    knot4 = moveTail(knot3, knot4)
                    knot5 = moveTail(knot4, knot5)
                    knot6 = moveTail(knot5, knot6)
                    knot7 = moveTail(knot6, knot7)
                    knot8 = moveTail(knot7, knot8)
                    knot9 = moveTail(knot8, knot9)
                    visitedTailPositions.add(Position(knot9.x, knot9.y, "#"))

//                    print(head, knot1, knot2, knot3, knot4, knot5, knot6, knot7, knot8, knot9, *visitedTailPositions.toTypedArray())
                }
            }

            return visitedTailPositions.size
        }


    }
}

