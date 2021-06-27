package com.rkarp.uni.modules.wise15.softwareentwicklung1

import com.rkarp.uni.modules.wise15.softwareentwicklung1.misc.Recursion
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.DateiListe.DateiListe
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Duplikate.DuplikatSucher
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Geburtstag.Simulation
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Girokonto.Girokonto
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Blatt11_Bildbearbeitung.SWBild
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Blatt11_Main.Histogramm
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Blatt11_TicTacToe.TicTacToeGUI
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf.Algos
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf.Eratosthenes
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf.Mirror
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung12.Blatt12_SE1Tunes.ArrayTitelListe
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung12.Blatt12_SE1Tunes.LinkedTitelListe
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung12.Blatt12_SE1Tunes.Titel
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.Delegation
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.HashWortschatz
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash.HashTable
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Postfixrechner.Postfixrechner
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Sortieren.BubbleSortierer
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Sortieren.QuickSortierer
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Sortieren.VisualIntListe
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Pythagoras.Pythagoras
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung3.Zeichnung.Dreieck
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung3.Zeichnung.Kreis
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung3.Zeichnung.Leinwand
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung3.Zeichnung.Quadrat
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung4.Imperativ
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung5.Ampeln.AmpelGUI
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung5.Ampeln.BmpelGUI
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung5.Ampeln.ZmpelGUI
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.AnalogUhr.AnalogUhr
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.Blox.Blox
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung7.Iteration.TextAnalyse
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung7.TurtleGraphics.Dompteur
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung7.TurtleGraphics.Turtle
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung8.Parser
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung8.Schreiben
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.DateiVerarbeiter
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.KurzAuflister
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.LangAuflister
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.TextdateiFilter
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Informatiker.*
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Zahlensaecke.Effizienzvergleicher
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Zahlensaecke.Lotto

import java.util.Arrays

/**
 * Collection of university projects.
 * Module: Software development 1
 * @author Rickie Karp
 */
object MainSE1 {

    /**
     * Start a project here.
     * @param args Ignore. Not used.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        uebung14_3()
    }

    //Start Misc - Recursion
    fun misc_recursive() {
        val fibonacci = Recursion()
        println(fibonacci.fibonacci(6))
    }

    //Start Uebung 14.3 - Postfixnotation
    fun uebung14_3() {
        println(Postfixrechner.werteAus("1 2 + 3 *"))
    }

    //Start Uebung 14.2 - Sorting
    fun uebung14_2() {
        val liste = VisualIntListe()
        val quicksorter = QuickSortierer()
        quicksorter.sortiere(liste)
    }

    //Start Uebung 14.1 - Sorting
    fun uebung14_1() {
        val liste = VisualIntListe()
        val bubblesorter = BubbleSortierer()
        bubblesorter.sortiere(liste)
    }

    //Start Uebung 14 - Stack
    fun uebung14_0() {
        println(Pythagoras.distance(10f, 12f, 13f, 16f))
    }

    //Start Uebung 13 - Hashing
    fun uebung13_1() {
        val hash = HashWortschatz(Delegation(), 10)
        hash.fuegeWortHinzu("hello")
        hash.fuegeWortHinzu("world")
        hash.fuegeWortHinzu("test")
        hash.schreibeAufKonsole()
    }

    //Start Uebung 13 - Hashing (Vorlesung)
    fun uebung13_0() {
        val hash = HashTable(com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash.Delegation())
        hash.insert("test")
        hash.insert("world")
        println(hash.contains("test") && hash.contains("world"))
    }

    //Start Uebung 12.2 - SE1Tunes
    fun uebung12_2() {
        //new ArrayTitelListe();
        val liste = LinkedTitelListe()
        liste.fuegeEin(Titel("name", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.fuegeEin(Titel("name1", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.fuegeEin(Titel("name2", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.fuegeEin(Titel("name3", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.fuegeEin(Titel("name4", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.entferne(2)
        for (i in 0 until liste.gibLaenge()) {
            println(i.toString() + ":" + liste.gibTitel(i).gibTitelname())
        }
        println("Listen Länge: " + liste.gibLaenge())
        liste.fuegeEin(Titel("name5", "interpret", "album", 2000, "genre", 100), 3)
        for (i in 0 until liste.gibLaenge()) {
            println(i.toString() + ":" + liste.gibTitel(i).gibTitelname())
        }
        println("Listen Länge: " + liste.gibLaenge())
        liste.leere()
        println("Listen Länge: " + liste.gibLaenge())
    }

    //Start Uebung 12.1 - SE1Tunes
    fun uebung12_1() {
        val liste = ArrayTitelListe()
        liste.fuegeEin(Titel("name", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.fuegeEin(Titel("name1", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.fuegeEin(Titel("name2", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.fuegeEin(Titel("name3", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.fuegeEin(Titel("name4", "interpret", "album", 2000, "genre", 100), liste.gibLaenge())
        liste.entferne(2)
        for (i in 0 until liste.gibLaenge()) {
            println(liste.gibTitel(i).gibTitelname())
        }
        liste.fuegeEin(Titel("name5", "interpret", "album", 2000, "genre", 100), 3)
        for (i in 0 until liste.gibLaenge()) {
            println(liste.gibTitel(i).gibTitelname())
        }
        liste.leere()
    }

    //Start Uebung 11.3 - Bildbearbeitung
    fun uebung11_3() {
        val sw = SWBild()
        sw.vertikalSpiegeln()
        sw.weichzeichnen()
    }

    //Start Uebung 11.2 - TicTacToe-Spielfeld als Array
    fun uebung11_2() {
        TicTacToeGUI()
    }

    //Start Uebung 11.1 - Strings in der Kommandozeile analysieren
    fun uebung11_1() {
        val asd = arrayOf("Cheese", "PApperoni", "3")
        Histogramm.main(asd)
    }

    //Start Uebung 11.0 - Arrays (Lecture Code)
    fun uebung11_0() {
        Mirror.main(arrayOf("{}"))
        val minArray = IntArray(4)
        minArray[0] = 42
        minArray[1] = 7
        minArray[2] = 123
        minArray[3] = 3
        println("Minimum von " + Arrays.toString(minArray) + " -> " + Algos.minimum(minArray))
        println("Primes: " + Arrays.toString(Eratosthenes.calcPrimesBelow(20)))
    }

    //Start Uebung 10.4 - Das Geburtstagsparadoxon
    fun uebung10_4() {
        val sim = Simulation()
        println("Test erfolgreich: " + sim.test())
        println("Wahrscheinlichkeit einer Kollision in Prozent: " + sim.simuliere(10))
    }

    //Start Uebung 10.3 - Duplikate im Dateisystem aufspüren
    fun uebung10_3() {
        val sucher = DuplikatSucher()
        sucher.start()
    }

    //Start Uebung 10.2 - Kontobewegungen protokollieren
    fun uebung10_2() {
        val konto = Girokonto()
        println("Saldo: " + konto.gibSaldo())
        println("Kontobewegungen:")
        konto.zahleEin(100)
        konto.zahleEin(200)
        konto.zahleEin(300)
        konto.druckeKontobewegungen()
    }

    //Start Uebung 10.1 - Eine Liste von Dateien
    fun uebung10_1() {
        val vw = com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.DateiListe.VerzeichnisWanderer()
        val dl1 = DateiListe()
        println("Liste Dateien auf und schreibe sie auf die Konsole...")
        vw.start(dl1)
        dl1.schreibeAufDieKonsole()
    }

    //Start Uebung 9 - Interfaces (Zahlensaecke)
    fun uebung09_3() {
        val ef = Effizienzvergleicher()
        println("Vergleiche die Effizienz verschiedener Implementationen von Zahlensack.")
        ef.vergleiche(100)

        println("Ziehe 6 aus 49 Zahlen.")
        Lotto.sechsAus49()
    }

    //Start Uebung 9 - Interfaces (Vergleiche)
    fun uebung09_2() {
        val inf = PraegendeInformatiker()
        val nachname = PerNachname()
        println("Schreibe geordnet (Nachname)")
        inf.schreibeGeordnet(nachname)

        val alter = PerAlter()
        println("Schreibe geordnet (Alter)")
        inf.schreibeGeordnet(alter)

        val vorname = PerVorname()
        println("Schreibe geordnet (Vorname)")
        inf.schreibeGeordnet(vorname)

        val geschlecht = PerGeschlecht()
        println("Schreibe geordnet (Geschlecht)")
        inf.schreibeGeordnet(geschlecht)

        //Zweistufiger Vergleich
        val zweistufig = Zweistufig(alter, geschlecht)
        println("Schreibe geordnet (Alter, Geschlecht)")
        inf.schreibeGeordnet(zweistufig)
    }

    //Start Uebung 9 - Interfaces (Dateisystem)
    fun uebung09_1() {
        val vw = com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem.VerzeichnisWanderer()
        val dv1 = KurzAuflister()
        println("Starte KurzAuflister...")
        vw.start(dv1)

        val dv2 = LangAuflister()
        println("Starte LangAuflister...")
        vw.start(dv2)

        val dv3 = TextdateiFilter()
        println("Starte TextdateiFilter...")
        vw.start(dv3)
    }

    //Start Uebung 8.2 - Eigene Rekursionen schreiben
    fun uebung08_2() {
        val schreiben = Schreiben()
        println("fak: " + schreiben.fak(4))
        println("enthaeltVokal " + schreiben.enthaeltVokal("brei"))
        println("istPalindrom " + schreiben.istPalindrom("axa"))
        println("anzahlLeerzeichen " + schreiben.anzahlLeerzeichen("a bc d"))
        println("umgedreht " + schreiben.umgedreht("regal"))

        val parser = Parser()
        println("parse int: " + parser.parse("2*3+4"))
    }

    //Start Uebung 7.3 - Dompteur (N-Eck Zeichnen)
    fun uebung07_3() {
        val dompteur = Dompteur()
        //dompteur.nEckZeichnen(11, 18); // Aufg 7.3.2
        dompteur.nEckeVerschachtelt(50, 20, 35.0) //Aufg 7.3.3
    }

    //Start Uebung 7.2 - TextAnalyse
    fun uebung07_2() {
        println(TextAnalyse.istFrageKompakt("hallo?")) // Aufg 7.2.1
        println(TextAnalyse.zaehleVokale("hallo")) // Aufg 7.2.2
        println(TextAnalyse.zaehleVokaleSwitch("hallo")) // Aufg 7.2.2
        println(TextAnalyse.istPalindrom("Regallager")) // Aufg 7.2.3
    }

    //Start Uebung 7 - TurtleGraphics
    fun uebung07() {
        val turtle = Turtle()
        turtle.geheZu(10.0, 10.0)
    }

    //Start Uebung 6 - AnalogUhr
    fun uebung06_3() {
        AnalogUhr()
    }

    //Start Uebung 6 - Vorlesung (Game)
    fun uebung06() {
        Blox()
    }

    //Start Uebung 5 - Ampeln
    fun uebung05() {
        AmpelGUI()
        BmpelGUI()
        ZmpelGUI()
    }

    //Start Uebung 4 - Wurzel ziehen
    fun uebung04() {
        val imp = Imperativ()
        println("Wurzel 1: " + imp.root(16.0))
        println("Wurzel 2: " + imp.root(16777216.0))
    }

    //Start Uebung 3 - Create objects
    fun uebung03() {
        val kreis1 = Kreis()
        kreis1.sichtbarMachen()
        val dreieck1 = Dreieck()
        dreieck1.sichtbarMachen()
        val quadrat1 = Quadrat()
        quadrat1.sichtbarMachen()
        Leinwand.gibLeinwand()
    }
}
