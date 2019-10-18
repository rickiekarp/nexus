package com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung12.aufg2

/**
 * Converts Fahrenheit to Celsius.
 * Formula: C = (F - 32) * 142/256
 */
class Fah2Cel {
    fun fah2cel(fah: Int): Int {
        return (fah - 32) * 142 shr 8
    }
}
