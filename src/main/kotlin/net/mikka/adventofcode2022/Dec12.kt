package net.mikka.adventofcode2022

import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.streams.toList

typealias Position = Pair<Int, Int>
typealias HeightMap = MutableList<List<Int>>

@Suppress("unused")
class Dec12 : PuzzleDay<Int, Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }

    companion object {

        private fun buildHeightMap(input: List<String>): HeightMap {
            val map: HeightMap = mutableListOf()
            for (line in input) {
                map.add(line.chars().toList())
            }
            return map
        }

        private fun HeightMap.findPoints(c:Char): List<Position> {
            val points = mutableListOf<Position>()

            for (row in 0 until this.size) {
                for (col in 0 until this[0].size) {
                    if (this[row][col] == c.code)
                        points.add(Pair(row, col))
                }
            }

            return points
        }

        private fun Int.isReachableFrom(previous: Int) = this.substitute() <= (previous.substitute() + 1)

        private fun Int.substitute(): Int {
            if (this == 'S'.code) return 'a'.code
            if (this == 'E'.code) return 'z'.code

            return this
        }

    }

    class ShortestPathGraphSolver(private val map: HeightMap) {

        val rowCount = map.size
        val columnCount = map[0].size

        companion object {
            private val rowDirectionVector = listOf(-1, 1, 0, 0)
            private val colDirectionVector = listOf(0, 0, 1, -1)
        }

        inner class Instance {

            private val rowQueue = ArrayDeque<Int>()
            private val colQueue = ArrayDeque<Int>()

            private var moveCount = 0
            private var nodesLeftInLayer = 1
            private var nodesInNextLayer = 0
            private var endReached = false

            private val visited = Array(rowCount) { BooleanArray(columnCount) }

            fun solve(startingPoint: Position): Int? {
                rowQueue.add(startingPoint.first)
                colQueue.add(startingPoint.second)
                visited[startingPoint.first][startingPoint.second] = true

                while (rowQueue.isNotEmpty()) {
                    val r = rowQueue.removeFirst()
                    val c = colQueue.removeFirst()

                    if (map[r][c] == 'E'.code) {
                        endReached = true
                        break
                    }

                    exploreNeighbors(r, c)
                    nodesLeftInLayer--
                    if (nodesLeftInLayer == 0) {
                        nodesLeftInLayer = nodesInNextLayer
                        nodesInNextLayer = 0
                        moveCount++
                    }
                }
                if (endReached) {
                    return moveCount
                }

                return null
            }

            private fun exploreNeighbors(r: Int, c: Int) {
                for (i in 0..3) {
                    val rr = r + rowDirectionVector[i]
                    val cc = c + colDirectionVector[i]

                    // skip out of bounds
                    if (rr < 0 || cc < 0) continue
                    if (rr >= rowCount || cc >= columnCount) continue

                    // skip visited locations
                    if (visited[rr][cc]) continue
                    // skip locations not reachable
                    if (!map[rr][cc].isReachableFrom(map[r][c])) continue

                    rowQueue.add(rr)
                    colQueue.add(cc)

                    visited[rr][cc] = true
                    nodesInNextLayer++
                }
            }
        }

        fun solve(startingPoint: Position): Int? {
            return Instance().solve(startingPoint)
        }
    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            val map: HeightMap = buildHeightMap(input)

            val startingPoint = map.findPoints('S').first()
            val shortestPath = ShortestPathGraphSolver(map).solve(startingPoint)

            if (shortestPath != null)
                return shortestPath

            throw RuntimeException("No path found")
        }


    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            val map: HeightMap = buildHeightMap(input)

            val startingPoints = map.findPoints('a')

            val shortestPathGraphSolver = ShortestPathGraphSolver(map)
            var shortestPath = Int.MAX_VALUE

            for (start in  startingPoints) {
                val pathLength = shortestPathGraphSolver.solve(start)
                if (pathLength!= null && pathLength < shortestPath) {
                    shortestPath = pathLength
                }
            }

            return shortestPath
        }

    }
}

