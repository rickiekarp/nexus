package com.itech.ae03.einfuehrung;

import java.util.Scanner;

/**
 * Created by sebastian on 08.12.16.
 */
public class Transaktionen {

    public static void doTransactions(Konto konto)
    {
        boolean moechteInteragieren = true;
        Scanner sc = new Scanner(System.in);
        do
        {
            System.out.println("Dein Kontostand ist: ");
            konto.printKontostand();
            System.out.println("Möchten Sie\n\t(1) Einzahlen\n\t(2) Auszahlen\n\t(0) Verlassen");
            try {
                int input = sc.nextInt();
                if (input == 0) {
                    System.out.println("Tschüß.");
                    moechteInteragieren = false;
                } else if(input == 1) {
                    System.out.println("Geben Sie den einzuzahlenden Betrag ein");
                    double betrag = sc.nextDouble();
                    konto.einzahlen(betrag);
                } else if (input == 2) {
                    System.out.println("Geben Sie den auszuzahlenden Betrag ein");
                    double betrag = sc.nextDouble();
                    konto.auszahlen(betrag);
                }
            }catch (Exception in) {
                moechteInteragieren = true;
                sc.nextLine();
            }
        }while(moechteInteragieren);
    }
}
