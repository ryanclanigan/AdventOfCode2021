import java.io.File

// Mark each time depth increases
fun day1day1() {
    val depths = File("day1.txt").readLines()
    depths
        .subList(0, depths.lastIndex)
        .map { it.toInt() }
        .fold(Pair<Int, Int?>(0, null)) { current, depth ->
            if (current.second == null) {
                Pair(0, depth)
            } else if (depth > current.second!!) {
                Pair(current.first.inc(), depth)
            } else {
                Pair(current.first, depth)
            }
        }
        .let { println(it) }
}

// Mark each time rolling window of 3 depths increase
fun day1Puzzle2() {
    class Window(val one: Int?, val two: Int?, val three: Int?) {
        fun shift(newValue: Int) = Window(one = newValue, two = one, three = two)
        fun sum() = one!! + two!! + three!!
        fun valid() = one != null && two != null && three != null
    }

    val depths = File("day1.txt").readLines()
    depths
        .subList(0, depths.lastIndex)
        .map { it.toInt() }
        .fold(Pair(0, Window(null, null, null))) { current, depth ->
            val newWindow = current.second.shift(depth)
            if (current.second.valid() && newWindow.sum() > current.second.sum()) {
                Pair(current.first.inc(), newWindow)
            } else {
                Pair(current.first, newWindow)
            }
        }
        .let { println(it) }
}