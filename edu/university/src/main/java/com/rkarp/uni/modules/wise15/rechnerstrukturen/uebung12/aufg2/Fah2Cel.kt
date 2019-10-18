package com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung12.aufg2;

/**
 * Converts Fahrenheit to Celsius.
 * Formula: C = (F - 32) * 142/256
 */
public class Fah2Cel {
    public static int fah2cel(int fah) {
        return ((fah - 32) * 142) >> 8;
    }
}
