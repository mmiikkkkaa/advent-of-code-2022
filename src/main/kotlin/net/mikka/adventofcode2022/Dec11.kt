package net.mikka.adventofcode2022

import java.math.BigInteger
import java.util.*

@Suppress("unused")
class Dec11 : PuzzleDay<BigInteger, BigInteger>() {

    override fun getPuzzle1(): Puzzle<BigInteger> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<BigInteger> {
        return Puzzle02()
    }

    class Monkey private constructor(
        val number: Int,
        private val items: MutableList<BigInteger>,
        private val worryLevelAdaption: (BigInteger) -> BigInteger,
        private val worryLevelManagement: ((BigInteger) -> BigInteger)?,
        private val worryCheck: (BigInteger) -> Boolean,
        private val throwTo1: Int,
        private val throwTo2: Int
    ) {

        var inspectedItems = BigInteger.ZERO

        fun hasItems() = items.isNotEmpty()

        fun catchItem(itemWithWorryLevel: BigInteger) {
            items.add(itemWithWorryLevel)
        }

        fun inspectItem(): BigInteger {

            val firstItem = items.removeFirst()
            inspectedItems++
            val newWorryLevel = worryLevelAdaption(firstItem)
            return worryLevelManagement?.invoke(newWorryLevel) ?: newWorryLevel
        }

        fun throwTo(worryLevel: BigInteger): Int {
            return if (worryCheck(worryLevel))
                throwTo1
            else
                throwTo2
        }

        data class Builder(
            var number: Int? = null,
            var items: MutableList<BigInteger>? = null,
            var worryLevelAdaption: ((BigInteger) -> BigInteger)? = null,
            var worryLevelManagement: ((BigInteger) -> BigInteger)? = null,
            var worryCheck: ((BigInteger) -> Boolean)? = null,
            var throwTo1: Int? = null,
            var throwTo2: Int? = null
        ) {

            fun number(number: Int) = apply { this.number = number }
            fun items(items: MutableList<BigInteger>) = apply { this.items = items }
            fun worryLevelAdaption(worryLevelAdaption: (BigInteger) -> BigInteger) =
                apply { this.worryLevelAdaption = worryLevelAdaption }
            fun worryLevelManagement(worryLevelManagement: ((BigInteger) -> BigInteger)?) =
                apply { this.worryLevelManagement = worryLevelManagement }

            fun worryCheck(worryCheck: (BigInteger) -> Boolean) = apply { this.worryCheck = worryCheck }
            fun throwTo1(throwTo1: Int) = apply { this.throwTo1 = throwTo1 }
            fun throwTo2(throwTo2: Int) = apply { this.throwTo2 = throwTo2 }
            fun build() = Monkey(number!!, items!!, worryLevelAdaption!!, worryLevelManagement, worryCheck!!, throwTo1!!, throwTo2!!)
        }
    }

    companion object {
        private fun buildMonkeysList(input: List<String>, worryLevelManagement: ((BigInteger) -> BigInteger)?): List<Monkey> {
            val monkeys = mutableListOf<Monkey>()
            lateinit var currentMonkey: Monkey.Builder
            for (line in input) {
                val trimmedLine = line.trim()
                if (trimmedLine.matches(monkeyRegex)) {
                    currentMonkey = Monkey.Builder()
                    currentMonkey.number(monkeyRegex.find(trimmedLine)!!.groupValues[1].toInt())
                    currentMonkey.worryLevelManagement(worryLevelManagement)
                }
                if (trimmedLine.matches(itemsRegex)) {
                    currentMonkey.items(itemsRegex.find(trimmedLine)!!.groupValues[1].split(", ").map { BigInteger.valueOf(it.toLong()) }
                        .toMutableList())
                }
                if (trimmedLine.matches(adaptionRegex)) {
                    val (operation, value) = adaptionRegex.find(trimmedLine)!!.destructured
                    currentMonkey.worryLevelAdaption(Operation(operation, value).get())
                }
                if (trimmedLine.matches(testRegex)) {
                    currentMonkey.worryCheck { i: BigInteger -> i % BigInteger.valueOf(testRegex.find(trimmedLine)!!.groupValues[1].toLong()) == BigInteger.ZERO }
                }
                if (trimmedLine.matches(trueRegex)) {
                    currentMonkey.throwTo1(trueRegex.find(trimmedLine)!!.groupValues[1].toInt())
                }
                if (trimmedLine.matches(falseRegex)) {
                    currentMonkey.throwTo2(falseRegex.find(trimmedLine)!!.groupValues[1].toInt())
                }
                if (trimmedLine.isBlank()) {
                    monkeys.add(currentMonkey.build())
                }
            }
            // also build last monkey without blank line
            monkeys.add(currentMonkey.build())
            return monkeys.sortedBy { it.number }
        }

        private val monkeyRegex = "Monkey (\\d+):".toRegex()
        private val itemsRegex = "Starting items: (.+)".toRegex()
        private val adaptionRegex = "Operation: new = old (.) (.+)".toRegex()
        private val testRegex = "Test: divisible by (\\d+)".toRegex()
        private val trueRegex = "If true: throw to monkey (\\d+)".toRegex()
        private val falseRegex = "If false: throw to monkey (\\d+)".toRegex()
    }

    class Operation(private val sign: String, private val value: String) {
        fun get(): (BigInteger) -> BigInteger {
            return when (sign) {
                "+" -> { old: BigInteger -> old + if (value == "old") old else BigInteger.valueOf(value.toLong()) }
                "*" -> { old: BigInteger -> old * if (value == "old") old else BigInteger.valueOf(value.toLong()) }
                else -> throw IllegalArgumentException()
            }
        }
    }

    class Puzzle01 : Puzzle<BigInteger> {
        override fun solve(input: List<String>): BigInteger {
        val worryLevelManagement = { i:BigInteger -> i.divide(BigInteger.valueOf(3))}
            val monkeys = buildMonkeysList(input, worryLevelManagement)

            repeat(20) {
                monkeys.forEach {
                    while (it.hasItems()) {
                        val item = it.inspectItem()
                        val numberOfCatchingMonkey = it.throwTo(item)
                        monkeys[numberOfCatchingMonkey].catchItem(item)
                    }
                }
            }

            val topTwo = monkeys.map { it.inspectedItems }.sortedDescending().take(2)
            return topTwo[0].multiply(topTwo[1])
        }
    }

    class Puzzle02 : Puzzle<BigInteger> {
        override fun solve(input: List<String>): BigInteger {
            val monkeys = buildMonkeysList(input, null)

            repeat(10_000) {
                println("iteration $it")
                monkeys.forEach { monkey ->
                    while (monkey.hasItems()) {
                        val item = monkey.inspectItem()
                        val numberOfCatchingMonkey = monkey.throwTo(item)
                        monkeys[numberOfCatchingMonkey].catchItem(item)
                    }
                }
            }

            val topTwo = monkeys.map { it.inspectedItems }.sortedDescending().take(2)
            return topTwo[0].multiply(topTwo[1])
        }
    }
}

