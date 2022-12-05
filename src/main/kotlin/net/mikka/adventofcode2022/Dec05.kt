package net.mikka.adventofcode2022

@Suppress("unused")
class Dec05 : PuzzleDay<String>() {

    override fun getPuzzle1(): Puzzle<String> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<String> {
        return Puzzle02()
    }

    companion object {
        private fun getCreateLetter(create: String): String? {
            return Regex("""\s?\[(.)]\s?""").find(create)?.groups?.get(1)?.value
        }

        private fun setUpInitialCrates(
            mutableInput: MutableList<String>
        ): MutableMap<Int, ArrayDeque<String?>> {
            val stacks = mutableMapOf<Int, ArrayDeque<String?>>()

            val setup = mutableListOf<String>()
            do {
                val line = mutableInput.removeFirst()
                if (line.isEmpty()) {
                    break
                }
                setup.add(line)

            } while (true)

            val stackCount = setup.removeLast().split(Regex("\\s")).filter { it.isNotBlank() }.last().toInt()
            setup.reverse()


            for (line in setup) {
                for (i in 0 until stackCount) {
                    try {
                        val crate = line.substring(i * 4, i * 4 + 3)
                        val createLetter = getCreateLetter(crate)
                        if (createLetter != null) {
                            stacks.putIfAbsent(i + 1, ArrayDeque())
                            stacks[i + 1]?.add(createLetter)
                        }
                    } catch (e: Exception) {
                        // no more boxes in the line
                    }
                }
            }

            return stacks
        }

        private fun buildCodeFromLastOnEachStack(stacks: MutableMap<Int, ArrayDeque<String?>>): String {
            return stacks.values
                .map { it.last() }
                .joinToString(separator = "")
        }
    }

    class Puzzle01 : Puzzle<String> {
        override fun solve(input: List<String>): String {
            val mutableInput = input.toMutableList()
            val stacks = setUpInitialCrates(mutableInput)

            while (mutableInput.isNotEmpty()) {
                val line = mutableInput.removeFirst()
                val matchResult = Regex("move (\\d+) from (\\d+) to (\\d+)").find(line)!!
                val (count, from, to) = matchResult.destructured

                for (i in 1..count.toInt()) {
                    stacks[to.toInt()]?.addLast(stacks[from.toInt()]?.removeLast())
                }
            }

            return buildCodeFromLastOnEachStack(stacks)
        }
    }

    class Puzzle02 : Puzzle<String> {
        override fun solve(input: List<String>): String {
            val mutableInput = input.toMutableList()

            val stacks = setUpInitialCrates(mutableInput)

            while (mutableInput.isNotEmpty()) {
                val line = mutableInput.removeFirst()
                val matchResult = Regex("move (\\d+) from (\\d+) to (\\d+)").find(line)!!
                val (count, from, to) = matchResult.destructured

                // don't just take one at a time, but all at once - so cache them,
                // to be able to add them in the correct order on the to stack
                val cache = mutableListOf<String>()
                for (i in 1..count.toInt()) {
                    cache.add(stacks[from.toInt()]?.removeLast()!!)
                }
                cache.reverse()
                for (cached in cache) {
                    stacks[to.toInt()]?.addLast(cached)
                }
            }

            return buildCodeFromLastOnEachStack(stacks)
        }
    }
}
