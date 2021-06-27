package com.itech.ae04.klausur.rickie;

public class Spieler {
    private int coins;
    private int gesetzteZahl;

    public int getCoins() {
        return coins;
    }

    /**
     * Adds a coin to the current player
     */
    public void addCoin() {
        this.coins += 1;
    }

    /**
     * Subtracts a coin of the current player
     */
    public void subtractCoin() {
        this.coins -= 1;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getGesetzteZahl() {
        return gesetzteZahl;
    }

    public void setGesetzteZahl(int gesetzteZahl) {

        switch (gesetzteZahl) {
            case 1:
            case 2:
                this.gesetzteZahl = gesetzteZahl;
                break;
            default:
                this.gesetzteZahl = 0;
                System.out.println("Ungültig");
        }
    }
}
