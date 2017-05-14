import java.io.PrintStream
import java.util.*

/**
* Created by Yaroslav Sokolov on 12.05.17.
*/
class ProblemD(inputPath: String, outputPath: String) : Problem(inputPath, outputPath) {

    init {
        for (i in 0..T - 1) {
            Case(i + 1, input, out)
        }
    }

    class Case(numberOfCase: Int, input: Scanner, out: PrintStream) :
            ProblemCase(numberOfCase, input, out) {

        private val N: Int
        private var M: Int

        //needs for optimally place pluses
        private data class auxiliaireDiagonal(var index: Int,
                                              var isFree: Boolean,
                                              var aviableCellsAmount: Int)

        private val stage: Array<Array<Char>>
        //shows possibility to place new x or o on the row/column
        private val rows: Array<Boolean>
        private val columns: Array<Boolean>
        //shows possibility to place new + or o on the diagonal
        //and amount of free cells on main diagonal
        private val mainDiagonals: Array<Boolean>
        private val secondaryDiagonals: Array<Boolean>

        private var score = 0
        private var changesCount = 0
        //shows which cells we are changed
        private val changedCells: Array<Array<Boolean>>

        init {
            val line = input.nextLine().split(" ")
            N = line[0].toInt()
            M = line[1].toInt()

            stage = Array<Array<Char>>(N, {Array(N, {'.'})})
            rows = Array<Boolean>(N, {true})
            columns = Array<Boolean>(N, {true})
            mainDiagonals = Array<Boolean>(N + N - 1, {true})
            secondaryDiagonals = Array<Boolean>(N + N - 1, {true})
            score = 0
            changesCount = 0
            changedCells = Array<Array<Boolean>>(N, {Array(N, {false})})

            for (i in 0..M - 1) {
                val model = input.nextLine().split(" ")
                if (model[0].toCharArray()[0] == '+') {
                    placePlus(model[1].toInt() - 1, model[2].toInt() - 1, true)
                }
                if (model[0].toCharArray()[0] == 'x') {
                    placeX(model[1].toInt() - 1, model[2].toInt() - 1, true)
                }
                if (model[0].toCharArray()[0] == 'o') {
                    placeO(model[1].toInt() - 1, model[2].toInt() - 1, true)
                }
            }
            solve()
        }

        private fun solve() {
            placePluses()
            for (i in 0..N - 1) {
                for(j in 0..N - 1) {
                    if (stage[i][j] == '.') {
                        if (placeO(i, j)) {
                            continue
                        }
                        if (placeX(i, j)) {
                            continue
                        }
                    }
                    else if (stage[i][j] == '+') {
                        if (modifyPlus(i, j)) {
                            continue
                        }
                    }
                }
            }
            printCase()
        }

        //also combines x and + to o
        private fun placePluses() {
            val diagonalAviableCells = countAviableCells()
            //initialize auxiliaire diagonals
            val auxiliaireDiagonales =
                    Array<auxiliaireDiagonal>(N + N - 1, { auxiliaireDiagonal(0, true, 0) })
            for (i in 0..secondaryDiagonals.size - 1) {
                auxiliaireDiagonales[i] = auxiliaireDiagonal(i, secondaryDiagonals[i],
                        diagonalAviableCells[i])
            }
            //now choose order diagonals to optimally place pluses
            auxiliaireDiagonales.sortBy { it.aviableCellsAmount }

            //place pluses
            loop@ for (diagonal in auxiliaireDiagonales) {
                if (diagonal.isFree) {
                    //walk in diagonal and try place plus or modify x
                    if (diagonal.index <= N - 1) {
                        var i = diagonal.index
                        var j = 0
                        while (i >= 0) {
                            if (placePlus(i, j)) {
                                continue@loop
                            }
                            if (stage[i][j] == 'x' && modifyX(i, j)) {
                                continue@loop
                            }
                            i--
                            j++
                        }
                    }
                    else {
                        var i = N - 1
                        var j = diagonal.index - N + 1
                        while (j < N) {
                            if (placePlus(i, j)) {
                                continue@loop
                            }
                            if (stage[i][j] == 'x' && modifyX(i, j)) {
                                continue@loop
                            }
                            i--
                            j++
                        }
                    }
                }
            }
        }

        private fun countAviableCells(): Array<Int> {
            //aviable cells on secondary diagonals
            val aviableCells = Array<Int>(N + N - 1, {0})

            for (i in 0..N - 1) {
                for (j in 0..N - 1) {
                    if ((stage[i][j] == '.' || stage[i][j] == 'x') &&
                            mainDiagonals[N - j + i - 1] && secondaryDiagonals[i + j]) {
                        aviableCells[i + j]++
                    }
                }
            }

            return aviableCells
        }

        private fun cellIsFree(i: Int, j: Int): Boolean {
            if (stage[i][j] == '.') {
                return true
            }
            return false
        }

        //if operation will be success then returns true
        private fun placePlus(i: Int, j: Int, itIsInitialStage: Boolean = false): Boolean {
            //if can place plus
            if (cellIsFree(i, j) && mainDiagonals[N - j + i - 1]
                    && secondaryDiagonals[i + j]) {
                stage[i][j] = '+'
                if (!itIsInitialStage) {
                    changedCells[i][j] = true
                    changesCount++
                }
                mainDiagonals[N - j + i - 1] = false
                secondaryDiagonals[i + j] = false
                score++
                return true
            }
            return false
        }

        //if operation will be success then returns true
        private fun modifyPlus(i: Int, j: Int): Boolean {
            //if can modify plus
            if (rows[i] && columns[j]) {
                stage[i][j] = 'o'
                if (!changedCells[i][j]) {
                    changedCells[i][j] = true
                    changesCount++
                }
                rows[i] = false
                columns[j] = false
                score++
                return true
            }
            return false
        }

        //if operation will be success then returns true
        private fun placeX(i: Int, j: Int, itIsInitialStage: Boolean = false): Boolean {
            //if can place X
            if (cellIsFree(i, j) && rows[i] && columns[j]) {
                stage[i][j] = 'x'
                if (!itIsInitialStage) {
                    changedCells[i][j] = true
                    changesCount++
                }
                secondaryDiagonals[i + j]
                rows[i] = false
                columns[j] = false
                score++
                return true
            }
            return false
        }

        //if operation will be success then returns true
        private fun modifyX(i: Int, j: Int): Boolean {
            //if can modify X
            if (mainDiagonals[N - j + i - 1] && secondaryDiagonals[i + j]) {
                stage[i][j] = 'o'
                if (!changedCells[i][j]) {
                    changedCells[i][j] = true
                    changesCount++
                }
                mainDiagonals[N - j + i - 1] = false
                secondaryDiagonals[i + j] = false
                score++
                return true
            }
            return false
        }

        //if operation will be success then returns true
        private fun placeO(i: Int, j: Int, itIsInitialStage: Boolean = false): Boolean {
            //if can place O
            if (cellIsFree(i, j) && rows[i] && columns[j]
                    && mainDiagonals[N - j + i - 1] && secondaryDiagonals[i + j]) {
                stage[i][j] = 'o'
                if (!itIsInitialStage) {
                    changedCells[i][j] = true
                    changesCount++
                }
                rows[i] = false
                columns[j] = false
                mainDiagonals[N - j + i - 1] = false
                secondaryDiagonals[i + j] = false
                score += 2
                return true
            }
            return false
        }
        
        private fun printCase() {
            out.println("Case #${numberOfCase}: ${score} ${changesCount}")
            for (i in 0..N - 1) {
                for (j in 0..N - 1) {
                    if (changedCells[i][j]) {
                        out.println("${stage[i][j]} ${i + 1} ${j + 1}")
                    }
                }
            }
        }
    }
}