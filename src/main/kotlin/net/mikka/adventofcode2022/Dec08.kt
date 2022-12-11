package net.mikka.adventofcode2022

import java.util.*
import kotlin.streams.toList

@Suppress("unused")
class Dec08 : PuzzleDay<Int, Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }

    companion object {

        private fun buildTreeHeightMaps(input: List<String>): MutableList<List<Int>> {
            val treeHeights: MutableList<List<Int>> = mutableListOf()

            for (line in input) {
                treeHeights.add(
                    line.chars()
                        .map { it.toChar().toString().toInt() }
                        .toList())
            }

            return treeHeights
        }

        private fun MutableList<List<Int>>.toColumnList(index: Int) = this.map { it[index] }.toList()

    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            val treeHeightMap = buildTreeHeightMaps(input)

            var visibleTreeCount = 0
            treeHeightMap.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, height ->
                    run {
                        if (height.isHighestInRow(
                                row,
                                columnIndex
                            ) || height.isHighestInColumn(treeHeightMap.toColumnList(columnIndex), rowIndex)
                        ) {
                            visibleTreeCount++
                        }
                    }
                }
            }
            return visibleTreeCount
        }

        private fun Int.isHighestInRow(row: List<Int>, index: Int) =
            !(row.take(index).any { it >= this } && row.takeLast(row.size - index - 1).any { it >= this })

        private fun Int.isHighestInColumn(column: List<Int>, index: Int) =
            !(column.take(index).any { it >= this } && column.takeLast(column.size - index - 1).any { it >= this })

    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            val treeHeightMap = buildTreeHeightMaps(input)
            var maxScenicScore = 0
            treeHeightMap.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, height ->
                    run {
                        val scenicScore =
                            height.calcScenicScore(row, treeHeightMap.toColumnList(columnIndex), rowIndex, columnIndex)

                        if (scenicScore > maxScenicScore) maxScenicScore = scenicScore
                    }
                }
            }


            return maxScenicScore
        }

        private fun Int.calcScenicScore(row: List<Int>, column: List<Int>, rowIndex: Int, columnIndex: Int): Int {
            val visibleToWest = countVisibleFrom(row.take(columnIndex).reversed(), this)
            if (visibleToWest == 0) return 0
            val visibleToEast = countVisibleFrom(row.takeLast(row.size - columnIndex - 1), this)
            if (visibleToEast == 0) return 0
            val visibleToNorth = countVisibleFrom(column.take(rowIndex).reversed(), this)
            if (visibleToNorth == 0) return 0
            val visibleToSouth = countVisibleFrom(column.takeLast(column.size - rowIndex - 1), this)

            return visibleToWest * visibleToEast * visibleToNorth * visibleToSouth
        }

        private fun countVisibleFrom(heights: List<Int>, height: Int): Int {
            var visibleCount = 0
            for (currentHeight in heights) {
                visibleCount++
                if (currentHeight >= height) {
                    break
                }
            }

            return visibleCount
        }
    }
}
