package net.mikka.adventofcode2022

@Suppress("unused")
class Dec02 : PuzzleDay<Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            var score = 0

            for (line in input) {
                val opponent = RockPaperScissor.ofOpponent(line.substring(0, 1))
                val me = RockPaperScissor.ofMe(line.substring(2))

                score += getScoreByChosenShape(me)
                score += getScoreByResult(me, opponent)
            }

            return score
        }


    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {
            var score = 0

            for (line in input) {
                val opponent = RockPaperScissor.ofOpponent(line.substring(0, 1))
                val strategy = Strategy.of(line.substring(2))
                val me = getMoveByStrategy(opponent, strategy)

                score += getScoreByChosenShape(me)
                score += getScoreByResult(me, opponent)
            }

            return score
        }

        private fun getMoveByStrategy(opponent: RockPaperScissor, strategy: Strategy): RockPaperScissor {
            return when(strategy){
                Strategy.TIE -> return opponent
                Strategy.LOSE -> RockPaperScissor.values().single { it.loses(opponent) }
                Strategy.WIN -> RockPaperScissor.values().single { it.wins(opponent) }
            }
        }

        enum class Strategy(val code: String) {
            LOSE("X"),
            TIE("Y"),
            WIN("Z");

            companion object {
                fun of(value: String): Strategy {
                    return values().single { it.code == value }
                }
            }
        }
    }
}

enum class RockPaperScissor(val opponent: String, val me: String) {
    ROCK("A", "X") {
        override fun wins(rockPaperScissor: RockPaperScissor) = rockPaperScissor == SCISSOR
        override fun loses(rockPaperScissor: RockPaperScissor) = rockPaperScissor == PAPER
    },
    PAPER("B", "Y") {
        override fun wins(rockPaperScissor: RockPaperScissor) = rockPaperScissor == ROCK
        override fun loses(rockPaperScissor: RockPaperScissor) = rockPaperScissor == SCISSOR
    },
    SCISSOR("C", "Z") {
        override fun wins(rockPaperScissor: RockPaperScissor) = rockPaperScissor == PAPER
        override fun loses(rockPaperScissor: RockPaperScissor) = rockPaperScissor == ROCK
    };

    abstract fun wins(rockPaperScissor: RockPaperScissor): Boolean
    abstract fun loses(rockPaperScissor: RockPaperScissor): Boolean

    companion object {
        fun ofOpponent(value: String): RockPaperScissor {
            return values().single { it.opponent == value }
        }

        fun ofMe(value: String): RockPaperScissor {
            return values().single { it.me == value }
        }
    }
}

fun getScoreByResult(
    me: RockPaperScissor,
    opponent: RockPaperScissor
) = if (me.wins(opponent)) 6
else if (me == opponent) 3
else 0

private fun getScoreByChosenShape(me: RockPaperScissor) = when (me) {
    RockPaperScissor.ROCK -> 1
    RockPaperScissor.PAPER -> 2
    RockPaperScissor.SCISSOR -> 3
}
