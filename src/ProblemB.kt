/**
 * Created by yas on 11.05.17.
 */
class ProblemB(inputPath: String, outputPath: String) : Problem(inputPath, outputPath) {

    private val lines = Array<ArrayList<Char>>(T, {ArrayList<Char>()})

    init {
        for (i in 1..T) {
            lines[i - 1] = ArrayList(strings[i].toList())
        }
        solve()
    }

    private fun solve() {
        mainLoop@ for (i in 0..T - 1) {
            val firstWrongDigit = findFirstWrongDigit(lines[i])

            if (firstWrongDigit == null) {
                printLine(i + 1, lines[i])
                continue@mainLoop
            }
            else {
                for (j in firstWrongDigit - 1 downTo 0) {
                    //try borrow from previous digit
                    if (j == 0 || lines[i][j] > lines[i][j - 1]) {
                        lines[i][j]--
                        for (k in j + 1..lines[i].size - 1) {
                            lines[i][k] = '9'
                        }
                        //delete leading zero
                        if (lines[i][0] == '0') {
                            lines[i].remove('0')
                        }
                        printLine(i + 1, lines[i])
                        continue@mainLoop
                    }
                }
            }
        }
    }

    private fun findFirstWrongDigit(line: ArrayList<Char>): Int? {
        for (i in 1..line.size - 1) {
            if (line[i] < line[i - 1]) {
                return i
            }
        }
        return null
    }

    private fun printLine(numberOfCase: Int, solution: ArrayList<Char>) {
            out.println("Case #${numberOfCase}: ${String(solution.toCharArray())}")
    }
}