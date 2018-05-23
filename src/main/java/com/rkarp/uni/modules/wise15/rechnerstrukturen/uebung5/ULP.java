package com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung5;

/**
 * Created by rickie on 12/8/2015.
 */
public class ULP {

    /**
     * ULP (Unit in the last place)
     */
    public static void ulpNew() {
        int n = 0; double sum; //double insted float
        double limit = 1.0E8f; //double instead float
        for ( sum = 0; sum < limit; sum+= 0.1) {
            n++;
            if ((n % 100000) == 0) {
                System.out.println("n=" + n + " sum=" + sum + " target=" + (n*0.1));
            }
        }
        System.out.println("sum is " + sum + " compared to " + (n*0.1));
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
        public static void ulpOld() {
            // calculate the sum of 1E8 unprecise numbers
            int n = 0;
            float sum;
            float limit = 1.0E8f;
            for( sum = 0; sum < limit; sum += 0.1 ) {
                n++;
                // if ((n % 100000) == 0) {
                // System.out.println( "n=" + n + " sum=" + sum + " target=" + (n*0.1));
                // }
            }
            System.out.println( "sum is " + sum + " compared to " + (n*0.1) );
        }

}
