import java.io.PrintStream
import java.util.*

/**
* Created by Yaroslav Sokolov on 09.05.17.
*/
class ProblemA(inputPath: String, outputPath: String) : Problem(inputPath, outputPath) {

    init {
        for (i in 0..T - 1) {
            Case(i + 1, input, out)
        }
    }

    class Case(numberOfCase: Int, input: Scanner, out: PrintStream) :
            ProblemCase(numberOfCase, input, out) {

        private val line: ArrayList<Boolean>
        private val K: Int

        init {
            val string = input.nextLine().split(" ")
            line = ArrayList(string[0].map {c: Char -> charToBool(c)})
            K = string[1].toInt()
            solve()
        }

        private fun solve() {
            var flipsCounter = 0
            try {
                for (j in 0..line.size - 1) {
                    if (!line[j]) {
                        for (k in j..j + K - 1) {
                            line[k] = changeBool(line[k])
                        }
                        flipsCounter++
                    }
                }
                printCase(flipsCounter)
            }
            catch (e: java.lang.IndexOutOfBoundsException) {
                //impossible case
                printCase(null)
            }
        }

        private fun changeBool(bool: Boolean): Boolean {
            if (bool) {
                return false
            }
            return true
        }

        private fun charToBool(ch: Char): Boolean {
            if (ch.equals('+')) {
                return true
            }
            else if (ch.equals('-')){
                return false
            }
            throw ExceptionInInitializerError()
        }

        private fun printCase(flipsCounter: Int?) {
            out.println("Case #${numberOfCase}: ${if (flipsCounter == null) "IMPOSSIBLE"
                                                  else flipsCounter}")
        }
    }
}