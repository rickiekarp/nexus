package com.rkarp.uni.modules.wise15.rechnerstrukturen

import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung10.aufg1.Collatz
import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung12.aufg2.Fah2Cel
import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung4.VergleichGleitkomma
import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung5.ULP
import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung6.HammingDistance

/**
 * Collection of university projects.
 * Module: Technical computer science
 * @author Rickie Karp
 */
object MainRS {

    /**
     * Start a project here.
     * @param args Ignore. Not used.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        uebung12_2(120)
    }

    //Start Uebung 12 - Fahrenheit to Celsius Shift
    fun uebung12_2(input: Int) {
        println(input.toString() + "°F -> " + Fah2Cel.fah2cel(input) + "°C")
    }

    //Start Uebung 10 - Hamming Distance
    fun uebung10_1(collatz: Int) {
        val string = arrayOfNulls<String>(1)
        string[0] = collatz.toString()
        Collatz.main(string)
    }

    //Start Uebung 6 - Hamming Distance
    fun uebung06(a: Int, b: Int) {
        println("Hamming-Distance zwischen " + a + " und " + b + ": " + HammingDistance.hamming(a, b))
    }

    //Start Uebung 5 - Unit in the last place
    fun uebung05() {
        ULP.ulpNew()
    }

    //Start Uebung 4 - Comparing floating points
    fun uebung04(a: Double, b: Double, epsilon: Double) {
        println(VergleichGleitkomma.almostEqual(a, b, epsilon))
    }

}
