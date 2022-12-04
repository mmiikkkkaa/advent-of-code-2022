package net.mikka.adventofcode2022

interface Puzzle<T> {
    fun solve(input:List<String>): T
}