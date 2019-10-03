package com.rkarp.uni.modules.wise15.rechnerstrukturen;

import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung10.aufg1.Collatz;
import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung12.aufg2.Fah2Cel;
import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung4.VergleichGleitkomma;
import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung5.ULP;
import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung6.HammingDistance;

/**
 * Collection of university projects.
 * Module: Technical computer science
 * @author Rickie Karp
 */
public class MainRS {

    /**
     * Start a project here.
     * @param args Ignore. Not used.
     */
    public static void main(String[] args) {
        uebung12_2(120);
    }

    //Start Uebung 12 - Fahrenheit to Celsius Shift
    public static void uebung12_2(int input) {
        System.out.println(input + "°F -> " + Fah2Cel.fah2cel(input) + "°C");
    }

    //Start Uebung 10 - Hamming Distance
    public static void uebung10_1(int collatz) {
        String[] string = new String[1];
        string[0] = String.valueOf(collatz);
        Collatz.main(string);
    }

    //Start Uebung 6 - Hamming Distance
    public static void uebung06(int a, int b) {
        System.out.println("Hamming-Distance zwischen " + a + " und " + b + ": " + HammingDistance.hamming(a,b));
    }

    //Start Uebung 5 - Unit in the last place
    public static void uebung05() {
        ULP.ulpNew();
    }

    //Start Uebung 4 - Comparing floating points
    public static void uebung04(double a, double b, double epsilon) {
        System.out.println(VergleichGleitkomma.almostEqual(a, b, epsilon));
    }

}
