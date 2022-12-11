package net.mikka.adventofcode2022

abstract class PuzzleDay<T1, T2> {

    private val fileInput = ResourceReader().readLinesFromResourceFile("/" + this::class.simpleName!!.lowercase() + ".txt")

    fun solve() {
        println("${this::class.simpleName}.Puzzle01: " + getPuzzle1().solve(input = fileInput))
        println("${this::class.simpleName}.Puzzle02: " + getPuzzle2().solve(input = fileInput))
        println("###################")
    }

    abstract fun getPuzzle1(): Puzzle<T1>
    abstract fun getPuzzle2(): Puzzle<T2>
}