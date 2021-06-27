package com.itech.ae04.uebungsklausur.aufgabe2;

public class Aufgabe2 {

    public static void main(String[] args) {
        int a = 1;
        int summe = 0;
        while (a < 5) {
            summe += a;
            if (a > 3) {
                summe -= 1;
            }
            a++;
        }
        System.out.println("Summe: " + summe);
    }
}
