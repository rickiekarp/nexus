package com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung10.aufg1;

/******************************************************************************
 * Klasse zur Ausgabe der Collatz Sequenz einer Zahl n
 *
 *  % java Collatz 19
 *  19 58 29 88 44 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1
 ******************************************************************************/
public class Collatz {
    private static int counter;

    /**
     * Gibt die Collatz Sequenz einer Zahl n aus.
     * @param args Die übergebenen Kommandozeilenparameter
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        // Aufg 10.3 (c)
        collatz(n);
        System.out.println("Schritte bis man für " + n + " den Wert 1 erreicht: " + counter);

        // Aufg 10.3 (d)
        collatz_d();
    }

    /**
     * Gibt die Collatz Sequenz einer Zahl n aus.
     * @param n Die auszugebene Zahl.
     */
    private static void collatz(int n) {
        counter++;
        System.out.println(counter + ": " + n); // Gibt das aktuelle Ergebnis aus
        if (n == 1) {  }
        else if (n % 2 == 0) { collatz(n / 2); }
        else { collatz(3 * n + 1); }
    }

    /**
     * Aufgabe 10.3 (d)
     */
    private static void collatz_d() {
        for (int i = 1; i < 100; i++) {
            counter = 0;
            collatz(i);
            System.out.println("Schritte bis man für die Zahl " + i + " die 1 erreicht: " + counter);
        }

        //Geburtsdatum als Eingabe
        counter = 0;
        collatz(19951131);
        System.out.println("Schritte bis man für die Zahl 19951131 die 1 erreicht: " + counter);
    }
}