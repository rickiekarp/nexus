package com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung6

/**
 * Created by rickie on 12/8/2015.
 */
class HammingDistance {

    /**
     * Generator matrix for the code, multiplied with a dataword to generate a codeword.
     */
    private val sGenerator = intArrayOf(
            fromBinary("100000000000"),
            fromBinary("010000000000"),
            fromBinary("001000000000"),
            fromBinary("000100000000"),
            fromBinary("000010000000"),
            fromBinary("000001000000"),
            fromBinary("000000100000"),
            fromBinary("000000010000"),
            fromBinary("000000001000"),
            fromBinary("000000000100"),
            fromBinary("000000000010"),
            fromBinary("000000000001"),
            fromBinary("011111111111"),
            fromBinary("111011100010"),
            fromBinary("110111000101"),
            fromBinary("101110001011"),
            fromBinary("111100010110"),
            fromBinary("111000101101"),
            fromBinary("110001011011"),
            fromBinary("100010110111"),
            fromBinary("100101101110"),
            fromBinary("101011011100"),
            fromBinary("110110111000"),
            fromBinary("101101110001"))

    //Aufg:
    //Schreiben Sie eine Java-Methode (oder C-Funktion), um die Hamming-Distanz von zwei
    //Codewörtern (gegeben als 32-bit int) zu berechnen

    /**
     * Berechnet die Hamming-Distanz zwischen zwei Integern.
     *
     * @param a Integer a
     * @param b Integer b
     * @return Hamming-Distanz
     */
    fun hamming(a: Int, b: Int): Int {
        // convert to binary
        val aBinary = Integer.parseInt(Integer.toBinaryString(a))
        val bBinary = Integer.parseInt(Integer.toBinaryString(b))

        // format as string
        val aBinString = String.format("%032d", aBinary)
        val bBinString = String.format("%032d", bBinary)

        // compute hamming distance
        var distance = 0
        for (i in 0..31) {
            if (aBinString[i] != bBinString[i]) {
                distance++
            }
        }
        return distance
    }

    //Aufg:
    //Schreiben Sie ein Java-Programm (oder C), das mit dem angegebenen Verfahren alle 4096
    //Codewörter des Golay(24,12,8)-Codes berechnet und ausgibt. Als Datenstruktur eignet
    //sich zum Beispiel ein Array (der Größe 4096) oder eine ArrayList<Integer>.

    /**
     * Utility methods that converts a binary string into and int.
     *
     * @param str a string containing a binary number
     * @return the numeric value of the supplied string
     */
    private fun fromBinary(str: String): Int {
        return Integer.parseInt(str, 2)
    }

    /**
     * Generates the codewords array.
     * @return an array of codewords
     */
    private fun computeCodewords(): IntArray {
        val cws = IntArray(4096)
        //iterate over all valid datawords
        for (i in 0..4095) {
            //multiply dataword by generator matrix
            var cw = 0
            for (j in 0..23) {
                val d = i and sGenerator[j]
                val p = Integer.bitCount(d)
                cw = cw shl 1 or (p and 1)
            }
            //store resulting codeword
            cws[i] = cw
        }
        return cws
    }
}
