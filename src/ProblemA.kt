/**
 * Created by yaroslav on 09.05.17.
 */
class ProblemA(inputPath: String, outputPath: String) : Problem(inputPath, outputPath) {

    private val lines = Array<ArrayList<Boolean>>(T, {ArrayList<Boolean>()})
    private val K = Array<Int>(T, {0})

    init {
        for (i in 1..T) {
            val string = strings[i].split(" ")
            lines[i - 1] = ArrayList(string[0].map {c: Char -> charConvert(c)})
            K[i - 1] = string[1].toInt()
        }
        print(solve())
    }

    private fun solve(): Array<Int> {
        val solutions = Array(T, {0})

        for (i in 0..T - 1) {
            try {
                for (j in 0..lines[i].size - 1) {
                    if (!lines[i][j]) {
                        for (k in j..j + K[i] - 1) {
                            lines[i][k] = changeBool(lines[i][k])
                        }
                        solutions[i]++
                    }
                }
            }
            catch (e: java.lang.IndexOutOfBoundsException) {
                solutions[i] = -1
            }
        }

        return solutions
    }

    private fun charConvert(ch: Char): Boolean {
        if (ch.equals('+')) {
            return true
        }
        else if (ch.equals('-')){
            return false
        }
        throw ExceptionInInitializerError()
    }

    private fun changeBool(bool: Boolean): Boolean {
        if (bool) {
            return false
        }
        return true
    }

    private fun print(solutions: Array<Int>) {
        for (i in 0..T - 1) {
            out.println("Case #${i + 1}: ${if (solutions[i] < 0) "IMPOSSIBLE" else solutions[i]}")
        }
    }
}