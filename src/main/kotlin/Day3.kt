import java.io.File

class BinaryCounter(length: Int) {
    private class Counter(val zero: Int, val one: Int)

    private val binaryList = MutableList(length) { Counter(0, 0) }

    fun count(binaryString: String) {
        binaryString.forEachIndexed { index, char ->
            val counter = binaryList[index]
            if (char == '0') {
                binaryList[index] = Counter(zero = counter.zero.inc(), one = counter.one)
            } else {
                binaryList[index] = Counter(zero = counter.zero, one = counter.one.inc())
            }
        }
    }

    fun most(): Int = binaryList.joinToString("") { if (it.one > it.zero) "1" else "0" }
        .let { Integer.parseUnsignedInt(it, 2) }

    fun least(): Int = binaryList.joinToString("") { if (it.one <= it.zero) "1" else "0" }
        .let { Integer.parseUnsignedInt(it, 2) }

}

fun day3Puzzle1() {
    val counter: BinaryCounter
    File("day3.txt").readLines()
        .filter { it.isNotEmpty() }
        .let {
            counter = BinaryCounter(it[0].length)
            it
        }
        .forEach {
            counter.count(it)
        }
    println(counter.most() * counter.least())
}