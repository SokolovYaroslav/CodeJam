import java.io.PrintStream
import java.util.*

/**
 * Created by yas on 12.05.17.
 */
class ProblemC(inputPath: String, outputPath: String) : Problem(inputPath, outputPath) {

    init {
        for (i in 0..T - 1) {
            Case(i + 1, input, out)
        }
    }

    class Case(numberOfCase: Int, input: Scanner, out: PrintStream) :
            ProblemCase(numberOfCase, input, out) {

        private val N: Long
        private var K: Long

        init {
            val line = input.nextLine().split(" ")
            N = line[0].toLong()
            K = line[1].toLong()
            solve()
        }

        private fun solve() {
            //tree contains row's (empty stalls) lengths as key and amount such rows as value
            val emptyStalls = TreeMap<Long, Long>()
            emptyStalls.put(N, 1)
            while (K > 0) {
                //select rows with max lenghts
                val currentRow = emptyStalls.lastKey()
                val amount = emptyStalls[currentRow]!!
                //people choose stalls
                K -= amount
                val leftRow: Long
                val rightRow: Long
                if (currentRow % 2 == 0L) {
                    leftRow = currentRow / 2 - 1
                    rightRow = leftRow + 1
                }
                else {
                    leftRow = currentRow / 2
                    rightRow = leftRow
                }
                //refresh tree
                if (K > 0) {
                    emptyStalls.remove(currentRow)
                    emptyStalls.put(leftRow, amount + (emptyStalls[leftRow]?: 0))
                    emptyStalls.put(rightRow, amount + (emptyStalls[rightRow]?: 0))
                }
                //if there are last person then just print
                else {
                    printCase(maxOf(leftRow, rightRow), minOf(leftRow, rightRow))
                    return
                }
            }
        }

        private fun printCase(max: Long, min: Long) {
            out.println("Case #${numberOfCase}: ${max} ${min}")
        }
    }
}