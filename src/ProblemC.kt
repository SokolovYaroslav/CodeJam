import java.util.*

/**
 * Created by yas on 12.05.17.
 */
class ProblemC(inputPath: String, outputPath: String) : Problem(inputPath, outputPath) {

    private val N = Array<Long>(T, {0})
    private val K = Array<Long>(T, {0})

    init {
        for (i in 1..T) {
            val line = strings[i].split(" ")
            N[i - 1] = line[0].toLong()
            K[i - 1] = line[1].toLong()
        }
        solve()
    }

    private fun solve() {
        mainLoop@ for (i in 0..T - 1) {
            //tree contains row's (empty stalls) lengths as key and amount such rows as value
            val emptyStalls = TreeMap<Long, Long>()
            emptyStalls.put(N[i], 1)
            while (K[i] > 0) {
                //select rows with max lenghts
                val currentRow = emptyStalls.lastKey()
                val amount = emptyStalls[currentRow]!!
                //people choose stalls
                K[i] -= amount
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
                if (K[i] > 0) {
                    emptyStalls.remove(currentRow)
                    emptyStalls.put(leftRow, amount + (emptyStalls[leftRow]?: 0))
                    emptyStalls.put(rightRow, amount + (emptyStalls[rightRow]?: 0))
                }
                //if there are last person then just print
                else {
                    printLine(i + 1, maxOf(leftRow, rightRow), minOf(leftRow, rightRow))
                    continue@mainLoop
                }
            }
        }
    }

    private fun printLine(numberOfCase: Int, max: Long, min: Long) {
        out.println("Case #${numberOfCase}: ${max} ${min}")
    }
}