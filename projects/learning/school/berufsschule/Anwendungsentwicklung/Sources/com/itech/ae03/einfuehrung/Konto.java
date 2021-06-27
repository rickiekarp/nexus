package com.itech.ae03.einfuehrung;

/**
 * Created by rickie on 12/8/16.
 */
public class Konto {
    private double kontostand;

    public void einzahlen(double betrag) {
        kontostand += betrag;
    }

    public boolean auszahlen(double betrag) {
        if (kontostand >= betrag) {
            kontostand -= betrag;
            return true;
        } else {
            System.out.println("Es ist nicht genügend Guthaben vorhanden!");
            return false;
        }
    }

    /**
     * Prints the current kontostand
     */
    public void printKontostand() {
        System.out.println("Aktueller Kontostand: " + kontostand);
    }
}
