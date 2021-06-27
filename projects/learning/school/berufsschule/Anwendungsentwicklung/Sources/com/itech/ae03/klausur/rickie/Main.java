package com.itech.ae03.klausur.rickie;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //create two Mitarbeiter objects
        Mitarbeiter mitarbeiter1 = new Mitarbeiter();
        Mitarbeiter mitarbeiter2 = new Mitarbeiter("vorname", "nachname", 1, false);

        //set all values for mitarbeiter1
        setzeDaten(mitarbeiter1);
        mitarbeiter1.setPruefer(true);

        //check all employees
        System.out.println("\nListe aller Mitarbeiter:");
        System.out.println("Mitarbeiter 1:");
        zeigeMitarbeiter(mitarbeiter1);
        istMitarbeiterPruefer(mitarbeiter1);

        System.out.println("Mitarbeiter 2:");
        zeigeMitarbeiter(mitarbeiter2);
        istMitarbeiterPruefer(mitarbeiter2);
    }

    private static void setzeDaten(Mitarbeiter mitarbeiter) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bitte gebe deinen Vornamen ein:");
        mitarbeiter.setVorname(scanner.next());

        System.out.println("Bitte gebe deinen Nachnamen ein:");
        mitarbeiter.setNachname(scanner.next());

        System.out.println("Bitte gebe deine Gehaltsstufe ein:");
        mitarbeiter.setGehaltsstufe(scanner.nextInt());
        while (mitarbeiter.getGehaltsstufe() == 0) {
            System.out.println("Bitte gebe eine gültige Gehaltsstufe ein:");
            mitarbeiter.setGehaltsstufe(scanner.nextInt());
        }
    }

    private static void zeigeMitarbeiter(Mitarbeiter mitarbeiter) {
        System.out.println(mitarbeiter);
    }

    private static void istMitarbeiterPruefer(Mitarbeiter mitarbeiter) {
        if (mitarbeiter.isPruefer()) {
            System.out.println("Mitarbeiter ist Prüfer!");
        } else {
            System.out.println("Der Mitarbeiter ist kein Prüfer!");
        }
    }
}
