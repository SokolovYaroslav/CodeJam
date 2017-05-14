import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*

/**
* Created by Yaroslav Sokolov on 09.05.17.
*/
abstract class Problem(val inputPath: String, val outputPath: String) {

    protected val T: Int
    protected val input = Scanner(File(inputPath))
    protected val out = PrintStream(FileOutputStream(outputPath))

    init {
        T = input.nextLine().toInt()
    }

    abstract class ProblemCase(protected val numberOfCase: Int,
                               protected val input: Scanner,
                               protected val out: PrintStream)
}