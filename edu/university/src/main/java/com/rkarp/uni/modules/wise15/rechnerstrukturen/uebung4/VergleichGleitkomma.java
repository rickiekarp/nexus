package com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung4;

/**
 * Created by rickie on 12/8/2015.
 */
public class VergleichGleitkomma {

    /**
     * Geben Sie ein Verfahren an, dass bei fest gewähltem e auch für Variablen mit fast gleichen
     * Werten in sehr unterschiedlichen Wertebereichen funktioniert.
     * @param a Wert a
     * @param b Wert b
     * @param epsilon Annehmbarer Wertebereich
     * @return returns 1 if two double-precision numbers are almost equal, considering their scale, and 0 otherwise.
     */
    public static int almostEqual (double a, double b, double epsilon) {
        double diff = Math.max(Math.abs(a),Math.abs(b)) - Math.min(Math.abs(a),Math.abs(b));
        if (diff < epsilon) {
            return 1;
        } else {
            return 0;
        }
    }

}
