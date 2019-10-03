package com.rkarp.uni.modules.wise15.softwareentwicklung1.misc;

/**
 * Rekursion tritt auf, wenn eine Methode m
 * während der Ausführung ihres Rumpfes erneut
 * aufgerufen wird. Damit dieser Prozess nicht
 * endlos läuft („nicht terminiert“), ist eine
 * Abbruchbedingung zwingend notwendig.
 *
 * Wir unterscheiden:
 * Eine Rekursion ist direkt, wenn eine
 * Methode m sich im Rumpf selbst ruft.
 * Eine Rekursion ist indirekt, wenn eine
 * Methode m1 eine andere Methode m2 ruft,
 * die aus ihrem Rumpf m1 aufruft.
 *
 * Der Grundgedanke der Rekursion ist, dass die
 * Methode einen ersten Teil eines Problems
 * selbst löst, den Rest in kleinere Probleme
 * zerlegt und sich selbst mit diesen kleineren
 * Problemen aufruft.
 *
 * Rekursion ist besonders in folgenden Fällen geeignet:
 * Wenn die Struktur, die verarbeitet wird, selbst rekursiv definiert ist;
 * darunter fallen zum Beispiel alle Baumstrukturen in der Informatik
 * (Syntaxbäume, Entscheidungsbäume, Verzeichnisbäume, etc.).
 * Viele sehr gute Sortierverfahren sind rekursiv definiert, beispielsweise
 * Quicksort und Mergesort.
 * Viele Probleme auf Graphen lassen sich elegant rekursiv lösen.
 */
public class Recursion {

    /**
     * Bsp: Fibonacci-Zahlen
     * Die Fibonacci-Zahlen sind sehr einfach definiert:
     * Die erste Fibonacci-Zahl ist 0.
     * Die zweite Fibonacci-Zahl ist 1.
     * Die n-te Fibonacci-Zahl ergibt sich aus der Summe der (n-1)ten und der (n-2)ten Fibonacci-Zahl.
     * @param n Zahl aus der die Fibonacci-Zahl bestimmt wird
     * @return Rekursiver Methodenaufruf
     */
    public int fibonacci (int n)
    {
        switch (n) {
            case 1: return 0;
            case 2: return 1;
            default: return fibonacci(n-1) + fibonacci(n-2);
        }
    }
}
