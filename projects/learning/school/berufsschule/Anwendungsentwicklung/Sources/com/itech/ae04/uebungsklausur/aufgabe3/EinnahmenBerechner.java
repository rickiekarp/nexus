package com.itech.ae04.uebungsklausur.aufgabe3;

import java.util.Arrays;

class EinnahmenBerechner {
    private final int[] einnahmen;
    private int wochenEinnahme;
    private int kaffeekasse;

    final int getWochenEinnahme() {
        return wochenEinnahme;
    }

    final int getKaffeekasse() {
        return kaffeekasse;
    }

    EinnahmenBerechner(int[] einnahmenArray) {
        this.einnahmen = einnahmenArray;
        this.wochenEinnahme = 0;
        this.kaffeekasse = 0;
    }

    void berechneEinnahmen() {
        for (int i = 0; i < einnahmen.length; i++) {
            if (einnahmen[i] > 1000) {
                einnahmen[i] -= 10;
                kaffeekasse += 10;
            }
            wochenEinnahme += einnahmen[i];
        }

        System.out.println("Tageseinnahmen: " + Arrays.toString(einnahmen));
        System.out.println("Wocheneinnahme: " + wochenEinnahme);
        System.out.println("Kaffeekasse: " + kaffeekasse);
    }
}
