/**
 * Stellt Euch 100 Glühbirnen in einer langen Reihe vor. Zu jeder Glühbirne gehört ein Ein/Aus-Schalter.
 *
 * Zunächst werden alle 100 Schalter bewegt, so dass alle Lampen brennen.
 * Im nächsten Schritt wird nur jeder 2. Schalter bewegt, im 3. Schritt jeder 3., im 4. Schritt jeder 4., im 5. jeder 5. und
 * das geht so weiter bis zum 99. und zum 100. Schalter.
 *
 * Und jetzt die große Preisfrage: Wie viele Lampen brennen nach dieser Aktion noch?
 */
fun main(args : Array<String>) {
    val array = BooleanArray(100)

    //first step: set all bools to true
    for (i in array.indices) {
        array[i] = true
    }

    //second step: update bools with each iteration
    for (i in 2..100) {
        var a = 0
        while (a < array.size) {
            if (i <= a) {
                array[a] = !array[a]
            }
            a += i
        }
    }

    println("Lamps active: " + getTrueCount(array))
}

private fun getTrueCount(array: BooleanArray): Int {
    var boolCounter = 0
    for (anArray in array) {
        if (anArray) {
            boolCounter++
        }
    }
    return boolCounter
}
