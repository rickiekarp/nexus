package com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung10.aufg1

/******************************************************************************
 * Klasse zur Ausgabe der Collatz Sequenz einer Zahl n
 *
 * % java Collatz 19
 * 19 58 29 88 44 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1
 */
class Collatz {
    var counter: Int = 0

    /**
     * Gibt die Collatz Sequenz einer Zahl n aus.
     * @param n Die auszugebene Zahl.
     */
    fun collatz(n: Int) {
        counter++
        //println("$counter: $n") // Gibt das aktuelle Ergebnis aus
        if (n == 1) {
        }
        else if (n % 2 == 0) {
            collatz(n / 2)
        } else {
            collatz(3 * n + 1)
        }
    }
}