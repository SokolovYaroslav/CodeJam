import java.io.PrintStream
import java.util.*

/**
 * Created by yas on 11.05.17.
 */
class ProblemB(inputPath: String, outputPath: String) : Problem(inputPath, outputPath) {

    init {
        for (i in 0..T - 1) {
            Case(i + 1, input, out)
        }
    }

    class Case(numberOfCase: Int, input: Scanner, out: PrintStream) :
            ProblemCase(numberOfCase, input, out) {

        private val number: ArrayList<Char>

        init {
            number = ArrayList(input.nextLine().toList())
            solve()
        }

        private fun solve() {
            val firstWrongDigit = findFirstWrongDigit(number)

            if (firstWrongDigit == null) {
                //number already good
                printCase(number)
                return
            }
            else {
                for (i in firstWrongDigit - 1 downTo 0) {
                    //try borrow from previous digit with saving good order
                    if (i == 0 || number[i] > number[i - 1]) {
                        number[i]--
                        for (j in i + 1..number.size - 1) {
                            number[j] = '9'
                        }
                        //delete leading zero
                        if (number[0] == '0') {
                            number.remove('0')
                        }
                        printCase(number)
                        return
                    }
                }
            }
        }

        //find first digit with breaking order
        private fun findFirstWrongDigit(line: ArrayList<Char>): Int? {
            for (i in 1..line.size - 1) {
                if (line[i] < line[i - 1]) {
                    return i
                }
            }
            return null
        }

        private fun printCase(solution: ArrayList<Char>) {
            out.println("Case #${numberOfCase}: ${String(solution.toCharArray())}")
        }
    }
}