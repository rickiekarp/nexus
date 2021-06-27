package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Blatt11_TicTacToe;

/**
 * Aufg 11.2.1
 */
public class ArraySpielfeld implements Spielfeld {

    private int[] index = new int[9];

    public int gibBesitzer(int position) {
        return index[position];
    }

    public void besetzePosition(int position, int spieler) {
        index[position] = spieler;
    }

    /**
     * Aufg 11.2.3
     * @return TRUE, wenn alle Felder (count > 0) belegt sind, andernfalls FALSE
     */
    public boolean istVoll() {
        short count = 0;
        for (int i : index) {
            System.out.println(i);
            if (i == 0) {
                count++;
            }
        }
        return count <= 0;
    }
}
