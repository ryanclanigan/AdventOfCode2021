import java.io.File

enum class Direction {
    UP,
    DOWN,
    FORWARD
}

class SubmarineCommand(val direction: Direction, val magnitude: Int)

fun parseSubmarineCommand(command: String): SubmarineCommand {
    val pieces = command.split(" ")
    if (pieces.size != 2) throw Exception("Ruh roh")
    return SubmarineCommand(Direction.valueOf(pieces[0].uppercase()), pieces[1].toInt())
}

fun day2Puzzle1() {
    class Grid(val depth: Int, val horizontal: Int) {
        fun applyCommand(command: SubmarineCommand) =
            when (command.direction) {
                Direction.UP -> Grid(depth - command.magnitude, horizontal)
                Direction.DOWN -> Grid(depth + command.magnitude, horizontal)
                Direction.FORWARD -> Grid(depth, horizontal + command.magnitude)
            }
    }

    File("day2.txt").readLines()
        .filter { it.isNotEmpty() }
        .map { parseSubmarineCommand(it) }
        .fold(Grid(0, 0)) { current, command -> current.applyCommand(command) }
        .let { println(it.depth * it.horizontal) }
}

fun day2Puzzle2() {
    class Grid(val depth: Int, val horizontal: Int, val aim: Int) {
        fun applyCommand(command: SubmarineCommand) =
            when (command.direction) {
                Direction.UP -> Grid(depth, horizontal, aim - command.magnitude)
                Direction.DOWN -> Grid(depth, horizontal, aim + command.magnitude)
                Direction.FORWARD -> Grid(depth + aim * command.magnitude, horizontal + command.magnitude, aim)
            }
    }


    File("day2.txt").readLines()
        .filter { it.isNotEmpty() }
        .map { parseSubmarineCommand(it) }
        .fold(Grid(0, 0, 0)) { current, command -> current.applyCommand(command) }
        .let { println(it.depth * it.horizontal) }
}

fun day3Puzzle2() {
    val numbers = File("day3.txt").readLines()
        .filter { it.isNotEmpty() }
    val length = numbers[0].length

    var sublist = numbers
    (0..length).forEach {
        if (sublist.size == 1) return@forEach
        sublist = findSubListForCategory(Category.MOST, it, sublist)
    }

    val most = sublist[0].toInt(2)

    sublist = numbers
    (0..length).forEach {
        if (sublist.size == 1) return@forEach
        sublist = findSubListForCategory(Category.LEAST, it, sublist)
    }

    val least = sublist[0].toInt(2)

    println(most * least)
}

fun findSubListForCategory(category: Category, index: Int, list: List<String>): List<String> {
    var oneCount = 0
    var zeroCount = 0
    list.forEach { if (it[index] == '0') zeroCount += 1 else oneCount += 1 }
    return when (category) {
        Category.MOST -> list.filter {
            val filterFor = if (oneCount >= zeroCount) '1' else '0'
            it[index] == filterFor
        }
        Category.LEAST -> list.filter {
            val filterFor = if (oneCount < zeroCount) '1' else '0'
            it[index] == filterFor
        }
    }
}

enum class Category {
    MOST,
    LEAST
}

