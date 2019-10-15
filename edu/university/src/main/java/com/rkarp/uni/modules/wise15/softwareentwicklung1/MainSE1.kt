package com.rkarp.uni.modules.wise15.softwareentwicklung1;

import com.rkarp.uni.modules.wise15.softwareentwicklung1.misc.Recursion;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.DateiListe.DateiListe;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Duplikate.DuplikatSucher;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Geburtstag.Simulation;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Girokonto.Girokonto;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Blatt11_Bildbearbeitung.SWBild;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Blatt11_Main.Histogramm;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Blatt11_TicTacToe.TicTacToeGUI;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf.Algos;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf.Eratosthenes;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf.Mirror;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung12.Blatt12_SE1Tunes.ArrayTitelListe;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung12.Blatt12_SE1Tunes.LinkedTitelListe;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung12.Blatt12_SE1Tunes.Titel;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.Delegation;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.HashWortschatz;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash.HashTable;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Postfixrechner.Postfixrechner;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Sortieren.BubbleSortierer;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Sortieren.QuickSortierer;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Sortieren.VisualIntListe;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Pythagoras.Pythagoras;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung3.Zeichnung.Dreieck;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung3.Zeichnung.Kreis;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung3.Zeichnung.Leinwand;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung3.Zeichnung.Quadrat;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung4.Imperativ;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung5.Ampeln.AmpelGUI;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung5.Ampeln.BmpelGUI;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung5.Ampeln.ZmpelGUI;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.AnalogUhr.AnalogUhr;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.Blox.Blox;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung7.Iteration.TextAnalyse;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung7.TurtleGraphics.Dompteur;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung7.TurtleGraphics.Turtle;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung8.Parser;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung8.Schreiben;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.DateiVerarbeiter;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.KurzAuflister;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.LangAuflister;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.TextdateiFilter;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Informatiker.*;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Zahlensaecke.Effizienzvergleicher;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Zahlensaecke.Lotto;

import java.util.Arrays;

/**
 * Collection of university projects.
 * Module: Software development 1
 * @author Rickie Karp
 */
public class MainSE1 {

    /**
     * Start a project here.
     * @param args Ignore. Not used.
     */
    public static void main(String[] args) {
        uebung14_3();
    }

    //Start Misc - Recursion
    public static void misc_recursive() {
        Recursion fibonacci = new Recursion();
        System.out.println(fibonacci.fibonacci(6));
    }

    //Start Uebung 14.3 - Postfixnotation
    public static void uebung14_3() {
        System.out.println(Postfixrechner.werteAus("1 2 + 3 *"));
    }

    //Start Uebung 14.2 - Sorting
    public static void uebung14_2() {
        VisualIntListe liste = new VisualIntListe();
        QuickSortierer quicksorter = new QuickSortierer();
        quicksorter.sortiere(liste);
    }

    //Start Uebung 14.1 - Sorting
    public static void uebung14_1() {
        VisualIntListe liste = new VisualIntListe();
        BubbleSortierer bubblesorter = new BubbleSortierer();
        bubblesorter.sortiere(liste);
    }

    //Start Uebung 14 - Stack
    public static void uebung14_0() {
        System.out.println(Pythagoras.distance(10, 12, 13, 16));
    }

    //Start Uebung 13 - Hashing
    public static void uebung13_1() {
        HashWortschatz hash = new HashWortschatz(new Delegation(), 10);
        hash.fuegeWortHinzu("hello"); hash.fuegeWortHinzu("world"); hash.fuegeWortHinzu("test");
        hash.schreibeAufKonsole();
    }

    //Start Uebung 13 - Hashing (Vorlesung)
    public static void uebung13_0() {
        HashTable hash = new HashTable(new com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash.Delegation());
        hash.insert("test"); hash.insert("world");
        System.out.println(hash.contains("test") && hash.contains("world"));
    }

    //Start Uebung 12.2 - SE1Tunes
    public static void uebung12_2() {
        //new ArrayTitelListe();
        LinkedTitelListe liste = new LinkedTitelListe();
        liste.fuegeEin(new Titel("name", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.fuegeEin(new Titel("name1", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.fuegeEin(new Titel("name2", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.fuegeEin(new Titel("name3", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.fuegeEin(new Titel("name4", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.entferne(2);
        for (int i =0; i<liste.gibLaenge(); i++) {
            System.out.println(i + ":" + liste.gibTitel(i).gibTitelname());
        }
        System.out.println("Listen Länge: " + liste.gibLaenge());
        liste.fuegeEin(new Titel("name5", "interpret","album", 2000, "genre", 100), 3);
        for (int i =0; i<liste.gibLaenge(); i++) {
            System.out.println(i + ":" + liste.gibTitel(i).gibTitelname());
        }
        System.out.println("Listen Länge: " + liste.gibLaenge());
        liste.leere();
        System.out.println("Listen Länge: " + liste.gibLaenge());
    }

    //Start Uebung 12.1 - SE1Tunes
    public static void uebung12_1() {
        ArrayTitelListe liste = new ArrayTitelListe();
        liste.fuegeEin(new Titel("name", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.fuegeEin(new Titel("name1", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.fuegeEin(new Titel("name2", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.fuegeEin(new Titel("name3", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.fuegeEin(new Titel("name4", "interpret","album", 2000, "genre", 100), liste.gibLaenge());
        liste.entferne(2);
        for (int i =0; i<liste.gibLaenge(); i++) {
            System.out.println(liste.gibTitel(i).gibTitelname());
        }
        liste.fuegeEin(new Titel("name5", "interpret","album", 2000, "genre", 100), 3);
        for (int i =0; i<liste.gibLaenge(); i++) {
            System.out.println(liste.gibTitel(i).gibTitelname());
        }
        liste.leere();
    }

    //Start Uebung 11.3 - Bildbearbeitung
    public static void uebung11_3() {
        SWBild sw = new SWBild();
        sw.vertikalSpiegeln();
        sw.weichzeichnen();
    }

    //Start Uebung 11.2 - TicTacToe-Spielfeld als Array
    public static void uebung11_2() {
        new TicTacToeGUI();
    }

    //Start Uebung 11.1 - Strings in der Kommandozeile analysieren
    public static void uebung11_1() {
        String[] asd = {"Cheese", "PApperoni", "3"};
        Histogramm.main(asd);
    }

    //Start Uebung 11.0 - Arrays (Lecture Code)
    public static void uebung11_0() {
        Mirror.main(new String[]{"{}"});
        int[] minArray = new int[4];
        minArray[0] = 42; minArray[1] = 7; minArray[2] = 123; minArray[3] = 3;
        System.out.println("Minimum von " + Arrays.toString(minArray) + " -> " + Algos.minimum(minArray));
        System.out.println("Primes: " + Arrays.toString(Eratosthenes.calcPrimesBelow(20)));
    }

    //Start Uebung 10.4 - Das Geburtstagsparadoxon
    public static void uebung10_4() {
        Simulation sim = new Simulation();
        System.out.println("Test erfolgreich: " + sim.test());
        System.out.println("Wahrscheinlichkeit einer Kollision in Prozent: " + sim.simuliere(10));
    }

    //Start Uebung 10.3 - Duplikate im Dateisystem aufspüren
    public static void uebung10_3() {
        DuplikatSucher sucher = new DuplikatSucher();
        sucher.start();
    }

    //Start Uebung 10.2 - Kontobewegungen protokollieren
    public static void uebung10_2() {
        Girokonto konto = new Girokonto();
        System.out.println("Saldo: " + konto.gibSaldo());
        System.out.println("Kontobewegungen:");
        konto.zahleEin(100); konto.zahleEin(200); konto.zahleEin(300);
        konto.druckeKontobewegungen();
    }

    //Start Uebung 10.1 - Eine Liste von Dateien
    public static void uebung10_1() {
        com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.DateiListe.VerzeichnisWanderer vw = new com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.DateiListe.VerzeichnisWanderer();
        DateiListe dl1 = new DateiListe();
        System.out.println("Liste Dateien auf und schreibe sie auf die Konsole...");
        vw.start(dl1);
        dl1.schreibeAufDieKonsole();
    }

    //Start Uebung 9 - Interfaces (Zahlensaecke)
    public static void uebung09_3() {
        Effizienzvergleicher ef = new Effizienzvergleicher();
        System.out.println("Vergleiche die Effizienz verschiedener Implementationen von Zahlensack.");
        ef.vergleiche(100);

        System.out.println("Ziehe 6 aus 49 Zahlen.");
        Lotto.sechsAus49();
    }

    //Start Uebung 9 - Interfaces (Vergleiche)
    public static void uebung09_2() {
        PraegendeInformatiker inf = new PraegendeInformatiker();
        PerNachname nachname = new PerNachname();
        System.out.println("Schreibe geordnet (Nachname)");
        inf.schreibeGeordnet(nachname);

        PerAlter alter = new PerAlter();
        System.out.println("Schreibe geordnet (Alter)");
        inf.schreibeGeordnet(alter);

        PerVorname vorname = new PerVorname();
        System.out.println("Schreibe geordnet (Vorname)");
        inf.schreibeGeordnet(vorname);

        PerGeschlecht geschlecht = new PerGeschlecht();
        System.out.println("Schreibe geordnet (Geschlecht)");
        inf.schreibeGeordnet(geschlecht);

        //Zweistufiger Vergleich
        Zweistufig zweistufig = new Zweistufig(alter, geschlecht);
        System.out.println("Schreibe geordnet (Alter, Geschlecht)");
        inf.schreibeGeordnet(zweistufig);
    }

    //Start Uebung 9 - Interfaces (Dateisystem)
    public static void uebung09_1() {
        com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.VerzeichnisWanderer vw = new com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.VerzeichnisWanderer();
        DateiVerarbeiter dv1 = new KurzAuflister();
        System.out.println("Starte KurzAuflister...");
        vw.start(dv1);

        DateiVerarbeiter dv2 = new LangAuflister();
        System.out.println("Starte LangAuflister...");
        vw.start(dv2);

        DateiVerarbeiter dv3 = new TextdateiFilter();
        System.out.println("Starte TextdateiFilter...");
        vw.start(dv3);
    }

    //Start Uebung 8.2 - Eigene Rekursionen schreiben
    public static void uebung08_2() {
        Schreiben schreiben = new Schreiben();
        System.out.println("fak: " + schreiben.fak(4));
        System.out.println("enthaeltVokal " + schreiben.enthaeltVokal("brei"));
        System.out.println("istPalindrom " + schreiben.istPalindrom("axa"));
        System.out.println("anzahlLeerzeichen " + schreiben.anzahlLeerzeichen("a bc d"));
        System.out.println("umgedreht " + schreiben.umgedreht("regal"));

        Parser parser = new Parser();
        System.out.println("parse int: " + parser.parse("2*3+4"));
    }

    //Start Uebung 7.3 - Dompteur (N-Eck Zeichnen)
    public static void uebung07_3() {
        Dompteur dompteur = new Dompteur();
        //dompteur.nEckZeichnen(11, 18); // Aufg 7.3.2
        dompteur.nEckeVerschachtelt(50, 20, 35.0); //Aufg 7.3.3
    }

    //Start Uebung 7.2 - TextAnalyse
    public static void uebung07_2() {
        System.out.println(TextAnalyse.istFrageKompakt("hallo?")); // Aufg 7.2.1
        System.out.println(TextAnalyse.zaehleVokale("hallo")); // Aufg 7.2.2
        System.out.println(TextAnalyse.zaehleVokaleSwitch("hallo")); // Aufg 7.2.2
        System.out.println(TextAnalyse.istPalindrom("Regallager")); // Aufg 7.2.3
    }

    //Start Uebung 7 - TurtleGraphics
    public static void uebung07() {
        Turtle turtle = new Turtle();
        turtle.geheZu(10,10);
    }

    //Start Uebung 6 - AnalogUhr
    public static void uebung06_3() {
        new AnalogUhr();
    }

    //Start Uebung 6 - Vorlesung (Game)
    public static void uebung06() {
        new Blox();
    }

    //Start Uebung 5 - Ampeln
    public static void uebung05() {
        new AmpelGUI();
        new BmpelGUI();
        new ZmpelGUI();
    }

    //Start Uebung 4 - Wurzel ziehen
    public static void uebung04() {
        Imperativ imp = new Imperativ();
        System.out.println("Wurzel 1: " + imp.root(16));
        System.out.println("Wurzel 2: " + imp.root(16777216));
    }

    //Start Uebung 3 - Create objects
    public static void uebung03() {
        Kreis kreis1 = new Kreis(); kreis1.sichtbarMachen();
        Dreieck dreieck1 = new Dreieck(); dreieck1.sichtbarMachen();
        Quadrat quadrat1 = new Quadrat(); quadrat1.sichtbarMachen();
        Leinwand.gibLeinwand();
    }
}
