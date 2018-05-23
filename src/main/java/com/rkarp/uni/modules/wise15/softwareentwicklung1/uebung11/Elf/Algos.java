package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf;

/**
 * Klasse zur Ermittlung der kleinsten Zahl eines int-Arrays.
 */
public class Algos {
    public static int minimum(int[] a) {
        int bisherigesMinimum = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < bisherigesMinimum) {
                bisherigesMinimum = a[i];
            }
        }
        return bisherigesMinimum;
    }
}
