package com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung5

/**
 * Unit in the last place
 * Created by rickie on 12/8/2015.
 */
class ULP {

    /**
     * ULP (Unit in the last place)
     */
    fun ulpNew(): Double {
        var n = 0
        var sum: Double //double instead float
        val limit = 1.0E8 //double instead float
        sum = 0.0
        while (sum < limit) {
            n++
            sum += 0.1
            //            if ((n % 1000000) == 0) {
            //                System.out.println("n=" + n + " sum=" + sum + " target=" + (n*0.1));
            //            }
        }
        println("sum is " + sum + " compared to " + n * 0.1)
        return sum
    }

    /**
     * Ihr Kollege möchte die Rundungsfehler bei Gleitkomma-Arithmetik besser verstehen
     * und hat Ihnen folgendes Programm geschickt.
     *
     * (a) Das Programm besteht aus einer Schleife, die immer wieder den Wert 0.1 (der sich in
     * binärer Gleitkommadarstellung nicht exakt darstellen lässt) auf die Variable sum addiert.Was
     * erwarten Sie (ungefähr) als Ausgabewert des Programms?
     *
     * (b)Was passiert tatsächlich?Warum? (Es kann helfen, die auskommentierten Codezeilen wieder
     * zu aktivieren).
     */
    fun ulpOld() {
        // calculate the sum of 1E8 unprecise numbers
        var n = 0
        var sum: Float
        val limit = 1.0E8f
        sum = 0f
        while (sum < limit) {
            n++
            sum += 0.1f
            // if ((n % 100000) == 0) {
            // System.out.println( "n=" + n + " sum=" + sum + " target=" + (n*0.1));
            // }
        }
        println("sum is " + sum + " compared to " + n * 0.1)
    }

}
