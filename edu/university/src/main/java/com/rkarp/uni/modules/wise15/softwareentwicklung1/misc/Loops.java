package com.rkarp.uni.modules.wise15.softwareentwicklung1.misc;

/**
 * Schleifen
 * Eine Schleife nennen wir abweisend, wenn es aufgrund
 * der Schleifensteuerung auch dazu kommen kann, dass
 * der Schleifenrumpf gar nicht ausgeführt wird.
 *
 * Beispielsweise sind alle Schleifen, bei denen zuerst eine
 * Schleifenbedingung geprüft wird, abweisende Schleifen;
 * denn je nach Ergebnis der ersten Auswertung der Bedingung kann der
 * Schleifenrumpf mindestens einmal ausgeführt werden oder gar nicht.
 * Abweisende Schleifen werden teilweise auch als kopfgesteuerte
 * Schleifen bezeichnet.
 *
 * Wird hingegen der Schleifenrumpf auf jeden Fall mindestens
 * einmal ausgeführt, sprechen wir von einer nicht-abweisenden
 * Schleife. Sie wird auch als fuß- oder endgesteuerte Schleife bezeichnet.
 */
public class Loops {

    private boolean bedingung = true;

    //for Schleife, abweisend
    private void schleifeFor() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }

    //while Schleife, abweisend
    private void schleifeWhile() {
        while (bedingung) {
            System.out.println(bedingung);
        }
    }

    //do-while Schleife, endgesteuert
    private void schleifeDoWhile() {
        do {
            System.out.println(bedingung);
        }
        while (bedingung);
    }

    //Rekursive Schleife
    private void schleifeRekursiv(int wert) {
        if (wert > 0) {
            wert--;
            schleifeRekursiv(wert);
        }
    }

    //Endlosschleife, Methode 1
    private void endlosschleife1() {
        while (true)
        {
            // endlos wiederholt
        }
    }

    //Endlosschleife, Methode 2
    private void endlosschleife2() {
        for ( ; ; );
    }
}
