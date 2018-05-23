package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Zahlensaecke;

/**
 * Write a description of class Lotto here.
 * Aufg 9.3.1
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Lotto {

    /**
     * Schreibt sechs aus 49 Zahlen auf die Konsole
     */
    public static void sechsAus49() {
        Zahlensack zs = new Naiv(49);
        for (int i = 0; i < 6; i++)
        {
            System.out.println(i+1 + ": " + (zs.entferneZahl() + 1));
        }
    }
}
