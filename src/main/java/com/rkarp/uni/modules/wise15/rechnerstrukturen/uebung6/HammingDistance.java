package com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung6;

/**
 * Created by rickie on 12/8/2015.
 */
public class HammingDistance {

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
    public static int hamming(int a, int b) {
        // convert to binary
        int aBinary = Integer.parseInt(Integer.toBinaryString(a));
        int bBinary = Integer.parseInt(Integer.toBinaryString(b));

        // format as string
        String aBinString = String.format("%032d", aBinary);
        String bBinString = String.format("%032d", bBinary);

        // compute hamming distance
        int distance = 0; for (int i = 0; i < 32; i++) {
            if (aBinString.charAt(i) != bBinString.charAt(i)) {
                distance++;
            }
        }
        return distance;
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
    private static int fromBinary(final String str) {
        return Integer.parseInt(str, 2);
    }

    /**
     * Generator matrix for the code, multiplied with a dataword to generate a codeword.
     */
    private static final int[] sGenerator = {

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
            fromBinary("101101110001"),
    };

    /**
     * Generates the codewords array.
     * @return an array of codewords
     */
    private static int[] computeCodewords() {
        int[] cws = new int[4096];
        //iterate over all valid datawords
        for (int i = 0; i < 4096; i++) {
            //multiply dataword by generator matrix
            int cw = 0;
            for (int j = 0; j < 24; j++) {
                int d = i & sGenerator[j];
                int p = Integer.bitCount(d);
                cw = (cw << 1) | (p & 1);
            }
            //store resulting codeword
            cws[i] = cw;
        }
        return cws;
    }
}
