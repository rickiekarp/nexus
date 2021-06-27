package com.itech.ae04.klausur.rickie;

import java.util.Scanner;

public class Roulette {

    public static void main(String[] args) {
        Spieler spieler = new Spieler();
        spieler.setCoins(2);

        Spiel spiel = new Spiel();
        spiel.setzeZufallszahlLimit(2);

        for (int i = 1; i <= 5; i++) {
            spiel.setzeSpielrunde(i);

            //Spieler kann auf die Zahlen 1 oder 2 setzen
            Scanner scanner = new Scanner(System.in);
            System.out.println("Runde " + spiel.getSpielrunde() + "! Setze auf eine Zahl:");
            spieler.setGesetzteZahl(scanner.nextInt());
            while (spieler.getGesetzteZahl() == -1) {
                System.out.println("Bitte gebe eine gültige Zahl ein:");
                spieler.setGesetzteZahl(scanner.nextInt());
            }

            int zahl = spiel.zieheZahl(spiel.getZufallszahllimit());
            System.out.println("Gesetzt: " + spieler.getGesetzteZahl());
            System.out.println("Zufallszahl: " + zahl);
            if (spieler.getGesetzteZahl() == zahl) {
                System.out.println("Gewonnen!");
                spieler.addCoin();
            } else {
                System.out.println("Verloren!");
                spieler.subtractCoin();
                if (spieler.getCoins() == 0) {
                    System.out.println("Du hast keine Coins mehr!");
                    break;
                }
            }
            System.out.println("Derzeitige Coins: " + spieler.getCoins());
        }

        spiel.printSpielInfos(spieler);
    }
}
