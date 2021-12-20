import java.io.File

class Board(linesOfBoard: List<String>) {
    class BingoNumber(val number: Int, val called: Boolean)

    private val grid: List<MutableList<BingoNumber>> = linesOfBoard.map { line ->
        line.split(" ").filter { it.isNotEmpty() }.map { BingoNumber(it.toInt(), false) }.toMutableList()
    }

    fun callNumber(number: Int) {
        grid.forEach { line ->
            val index = line.map { it.number }.indexOf(number)
            if (index > -1) line[index] = BingoNumber(number, true)
        }
    }

    fun hasWon(): Boolean {
        grid.forEach { line ->
            if (line.all { it.called }) return true
        }

        for (i in grid.indices) {
            if (grid.all { line -> line[i].called }) return true
        }

        return false
    }

    fun getGridSum(): Int = grid.fold(0) { running, line ->
        running + line.fold(0) { innerRunning, bingoNumber ->
            innerRunning + if (!bingoNumber.called) bingoNumber.number else 0
        }
    }
}

fun day4Puzzle1() {
    val lines = File("day4.txt").readLines()
    val bingoOrder = lines[0].split(",").map { it.toInt() }
    val boardsNoNewLines = lines.subList(1, lines.size)
        .filter { it.isNotEmpty() }
    val boards = mutableListOf<Board>()
    for (i in boardsNoNewLines.indices step 5) {
        boards.add(Board(boardsNoNewLines.subList(i, i + 5)))
    }

    bingoOrder.forEach { number ->
        boards.forEach { it.callNumber(number) }
        boards
            .find { it.hasWon() }
            ?.let {
                it.hasWon()
                println(it.getGridSum() * number)
                return
            }
    }
}

fun day4Puzzle2() {
    val lines = File("day4.txt").readLines()
    val bingoOrder = lines[0].split(",").map { it.toInt() }
    val boardsNoNewLines = lines.subList(1, lines.size)
        .filter { it.isNotEmpty() }
    val boards = mutableListOf<Board>()
    for (i in boardsNoNewLines.indices step 5) {
        boards.add(Board(boardsNoNewLines.subList(i, i + 5)))
    }

    bingoOrder.forEach { number ->
        val notWonBoards = boards.filterNot { it.hasWon() }
        notWonBoards.forEach { it.callNumber(number) }
        if (notWonBoards.size == 1 && notWonBoards[0].hasWon()) {
            println(notWonBoards[0].getGridSum() * number)
            return
        }
    }
}