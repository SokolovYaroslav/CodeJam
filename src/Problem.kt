import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream

/**
 * Created by yaroslav on 09.05.17.
 */
abstract class Problem(val inputPath: String, val outputPath: String) {

    protected val T: Int
    protected val strings: List<String>
    protected val out = PrintStream(FileOutputStream(outputPath))

    init {
        val input = File(inputPath)
        strings = input.readLines()
        T = strings[0].toInt()
    }
}