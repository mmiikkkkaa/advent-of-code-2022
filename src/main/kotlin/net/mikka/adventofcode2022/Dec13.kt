package net.mikka.adventofcode2022

import java.util.*

@Suppress("unused")
class Dec13 : PuzzleDay<Int, Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }

    open class Item

    class ListItem(val children: MutableList<Item> = mutableListOf()) : Item() {
        fun next(): Item? = if (children.isNotEmpty()) children.removeFirst() else null
    }

    class ValueItem : Item() {
        private var value = ""
        fun concat(char: Char) {
            value += char
        }

        fun value() = value.toInt()
    }


    companion object {
        private fun ValueItem.asList() = ListItem(mutableListOf(this))

        private fun String.asList(): ListItem {

            val listStack = ArrayDeque<ListItem>()
            var currentValue: ValueItem? = null


            for (c in this.chars()) {
                if (c == '['.code){
                    listStack.push(ListItem())
                    continue
                }
                if (c == ']'.code){
                    if (currentValue != null) {
                        listStack.peek().children.add(currentValue)
                        currentValue = null
                    }

                    val closedItem = listStack.pop()
                    if (listStack.isEmpty())
                        return closedItem

                    listStack.peek().children.add(closedItem)
                    continue
                }
                if (c == ','.code){
                    if (currentValue != null) {
                        listStack.peek().children.add(currentValue)
                        currentValue = null
                    }
                    continue
                }
                // digit

                if (currentValue == null) {
                    currentValue = ValueItem()
                }
                currentValue.concat(c.toChar())

            }


            return listStack.pop()
        }

        private fun isCorrectOrder(left: Item?, right: Item?): Boolean {

            if (left is ValueItem && right is ValueItem) {
                return left.value() <= right.value()
            }

            if (left == null && right != null) {
                return true
            }

            if (left != null && right == null) {
                return false
            }

            val leftList = if (left is ValueItem) left.asList() else left as ListItem
            val rightList = if (right is ValueItem) right.asList() else right as ListItem

            var isCorrectOrder = true
            while (isCorrectOrder && (leftList.children.isNotEmpty() || rightList.children.isNotEmpty())) {
                isCorrectOrder = isCorrectOrder(leftList.next(), rightList.next())
            }

            return isCorrectOrder
        }

    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            var sumOfCorrectOrderIndizes = 0

            for (i in 0..input.size step 3) {
                val left = input[i].asList()
                val right = input[i + 1].asList()

                if (isCorrectOrder(left, right)) {
                    println("index ${(i / 3) + 1} is in correct order")
                    sumOfCorrectOrderIndizes += (i / 3) + 1
                }
            }
            return sumOfCorrectOrderIndizes
        }

    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            TODO()
        }
    }
}

