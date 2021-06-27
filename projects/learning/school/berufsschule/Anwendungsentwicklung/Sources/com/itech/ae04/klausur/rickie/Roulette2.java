package com.itech.ae04.klausur.rickie;

import java.util.Scanner;

public class Roulette2 {

    public static void main(String[] args) {
        Spieler spieler = new Spieler();
        spieler.setCoins(2);

        Spiel spiel = new Spiel();
        spiel.setzeZufallszahlLimit(36);

        spiel: for (int i = 1; i <= 5; i++) {
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

            switch (spieler.getGesetzteZahl()) {

                //zahl ist weder gerade noch gerade
                case 0:
                    System.out.println("Verloren!");
                    spieler.subtractCoin();
                    if (spieler.getCoins() == 0) {
                        System.out.println("Du hast keine Coins mehr!");
                        break spiel;
                    }
                    break spiel;

                //ungerade Zahl gewinnt
                case 1:
                    if ((zahl % 2) == 0) {
                        System.out.println("Verloren!");
                        spieler.subtractCoin();
                        if (spieler.getCoins() == 0) {
                            System.out.println("Du hast keine Coins mehr!");
                            break spiel;
                        }
                        break;
                    } else {
                        System.out.println("Gewonnen!");
                        spieler.addCoin();
                    }
                    break;

                //gerade Zahl gewinnt
                case 2:
                    if ((zahl % 2) == 0) {
                        System.out.println("Gewonnen!");
                        spieler.addCoin();
                    } else {
                        System.out.println("Verloren!");
                        spieler.subtractCoin();
                        if (spieler.getCoins() == 0) {
                            System.out.println("Du hast keine Coins mehr!");
                            break spiel;
                        }
                    }
                    break;
                default:
                    throw new RuntimeException("Ungültige Nummer!");
            }

            System.out.println("Derzeitige Coins: " + spieler.getCoins());
        }

        spiel.printSpielInfos(spieler);
    }
}
