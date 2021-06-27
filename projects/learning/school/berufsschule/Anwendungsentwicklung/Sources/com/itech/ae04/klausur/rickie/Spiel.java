package com.itech.ae04.klausur.rickie;

import java.util.Random;

public class Spiel {
    private Random generator;
    private int spielrunde;
    private int bounds;

    public Spiel() {
        generator = new Random();
        bounds = 2;
    }

    public void starteSpiel(Spieler spieler1) {

    }

    public int zieheZahl(int bisZahl) {
        return generator.nextInt(bisZahl);
    }

    public int getSpielrunde() {
        return spielrunde;
    }

    public void setzeSpielrunde(int runde) {
        this.spielrunde = runde;
    }

    public int getZufallszahllimit() {
        return bounds;
    }

    public void setzeZufallszahlLimit(int limit) {
        this.bounds = limit;
    }

    public void printSpielInfos(Spieler spieler) {
        System.out.println();
        System.out.println("Spiel vorbei!");
        System.out.println("Anzahl Spielrunden: " + getSpielrunde());
        System.out.println("Anzahl der Coins: " + spieler.getCoins());
    }
}
