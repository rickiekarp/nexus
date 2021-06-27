package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung7.Iteration;

/**
 * Diese Klasse bietet die Moeglichkeit, Texte zu analysieren.
 * Sie dient als Einstieg in Schleifenkonstrukte und zeigt in
 * der Methode istFrage beispielhaft einige Methodenaufrufe an
 * einem Objekt der Klasse String.
 * 
 * @author Fredrik Winkler
 * @author Joerg Rathlev
 * @author Petra Becker-Pechau
 * @version November 2014
 */
public class TextAnalyse
{
    /**
     * Ermittelt, ob es sich bei dem uebergebenen Text um eine Frage
     * handelt. Eine Frage erkennt man am abschliessenden Fragezeichen.
     * 
     * @param text der zu analysierende Text
     * @return true, wenn es sich um eine Frage handelt, false sonst
     */
    public boolean istFrage(String text)
    {
        int anzahlZeichen = text.length();

        int letztePosition = anzahlZeichen - 1;

        char letztesZeichen = text.charAt(letztePosition);

        boolean endetAufFragezeichen = (letztesZeichen == '?');

        return endetAufFragezeichen;
    }

    /**
     * Ermittelt, ob es sich bei dem uebergebenen Text um eine Frage
     * handelt. Eine Frage erkennt man am abschliessenden Fragezeichen.
     * 
     * @param text der zu analysierende Text
     * @return true, wenn es sich um eine Frage handelt, false sonst
     */
    public static boolean istFrageKompakt(String text)
    {
        return text.charAt(text.length() - 1) == '?';
    }

    /**
     * Aufg 7.2.2
     * Ermittelt die Anzahl der Vokale.
     *
     * @param text der zu analysierende Text
     * @return Anzahl der Vokale
     */
    public static int zaehleVokale(String text)
    {
        int vokale = 0;
        for (int i = 0; i < text.length(); i++)
        {
            if (text.charAt(i) == 'a' ||
                    text.charAt(i) == 'e' ||
                    text.charAt(i) == 'i' ||
                    text.charAt(i) == 'o' ||
                    text.charAt(i) == 'u')
            {
                vokale++;
            }
        }
        return vokale;
    }

    /**
     * Aufg 7.2.2
     * Ermittelt die Anzahl der Vokale.
     *
     * @param text der zu analysierende Text
     * @return Anzahl der Vokale
     */
    public static int zaehleVokaleSwitch(String text)
    {
        int vokale = 0;
        for (int i = 0; i < text.length(); i++)
        {
            switch (text.charAt(i))
            {
                case 'a': vokale++; break;
                case 'e': vokale++; break;
                case 'i': vokale++; break;
                case 'o': vokale++; break;
                case 'u': vokale++; break;
            }
        }
        return vokale;
    }

    /**
     * Aufg 7.2.3 + 7.2.4
     * Ermittelt ob der Text ein Palindrom ist.
     *
     * @param text der zu analysierende Text
     * @return true, wenn Text ein Palindrom ist
     */
    public static boolean istPalindrom(String text)
    {
        for (int a = 0, b = text.length() - 1; a < b; ++a, --b)
        {
            if (text.toLowerCase().charAt(a) != text.toLowerCase().charAt(b))
            {
                return false;
            }
        }
        return true;
    }
}
