package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Blatt11_Main;

/**
 * Ein einfaches Programm, das den Umgang mit Arrays und statische Methoden demonstriert.
 * Das Programm kann ueber die Konsole gestartet und mit Argumenten gefuettert werden:
 * 
 * java Histogramm Hallo Welt
 * 
 * @author Fredrik Winkler
 * @version 6. Januar 2015
 */
public class Histogramm
{
    private static final int[] histogramm;
    
    static
    {
        histogramm = new int[26];
    }
    
    public static void main(String[] args)
    {
        for (String a : args)
        {
            analysiere(a);
        }
        zeigeVerteilung();
    }

    /**
     * Erhoeht die Zaehler fuer die im uebergebenen String enthaltenen Buchstaben.
     */
    private static void analysiere(String s)
    {
        for (int i = 0; i < s.length(); ++i)
        {
            char zeichen = s.charAt(i);
            int index = stelleImAlphabet(zeichen);

            // Aufg 11.1.2
            // Das Programm stürzt ab, wenn der index -1 ist, das Zeichen also kein Buchstabe des Alphabets ist.
            if (index > -1) {
                ++histogramm[index];
            } else {
                System.out.println(zeichen + " ist kein Zeichen des Alphabets!");
            }
        }
    }
    
    /**
     * Berechnet, an welcher Stelle im Alphabet des uebergebene Zeichen liegt.
     * A liegt an Position 0, und Z liegt an Position 25.
     * Falls das uebergebene Zeichen kein Buchstabe ist, wird -1 geliefert.
     */
    private static int stelleImAlphabet(char x)
    {
        if ((x >= 'A') && (x <= 'Z')) return x - 'A';
        if ((x >= 'a') && (x <= 'z')) return x - 'a';
        return -1;
    }

    // Aufg 11.1.3
    private static void zeigeVerteilung()
    {
        char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        for (int i = 0; i < 25; i++) {
            //Aufg 11.1.4
            if (histogramm[i] >= 1) {
                System.out.println(alphabet[i] + ": " + histogramm[i]);
            }
        }
    }
}
