import kotlin.system.exitProcess

// Given a list of numbers and a number k, return whether any two numbers from the list add up to k

fun main(args : Array<String>) {
    if (args.size < 3) {
        println("you need at least 3 arguments")
        exitProcess(1)
    }
    val numberList = ArrayList<Int>(args.size)
    val result: Any

    for (arg in args) {
        try {
            numberList.add(arg.toInt())
        } catch (nfe: NumberFormatException) {
            println("not a number: $arg")
        }
    }

    if (numberList.size > 0) {
        val k: Int = numberList[numberList.size - 1]
        numberList.remove(numberList.last())
        result = isSumPresent(numberList.sorted(), k)
    } else {
        result = "no numbers in list"
    }

    println(result)
}

private fun isSumPresent(list : List<Int>, k: Int): Boolean {
    println(list)
    for (outerIndex in 0 until list.size) {
        for (innerIndex in 0 until list.size) {
            if (list[outerIndex] == list[innerIndex]) {
                continue
            }

            if (list[outerIndex] + list[innerIndex] == k) {
                println("result: ${list[outerIndex]} + ${list[innerIndex]} = $k")
                return true
            }
        }
    }
    return false
}
