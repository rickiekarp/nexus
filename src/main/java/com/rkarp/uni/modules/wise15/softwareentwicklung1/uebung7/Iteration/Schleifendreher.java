package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung7.Iteration;

/**
 * Halbwegs sinnvolle Beispiele fuer die Schleifenmechanismen von Java.
 * Grundlage fuer die Abnahme sind die Fragen in den Kommentaren ueber den Methoden!
 * 
 * @author Fredrik Winkler
 * @version November 2014
 */
class Schleifendreher
{
    // Beispiele fuer for-Schleifen

    /**
     * Wie oft wird der Schleifenrumpf betreten?
     * Welchen Wert hat i, wenn der Schleifenrumpf das erste Mal betreten wird? 10
     * Welchen Wert hat i, wenn der Schleifenrumpf das letzte Mal betreten wird? 9
     * Welchen Wert hat i, wenn die Schleifenbedingung false ist? 10
     */
    public void schreibeAlleZiffern()
    {
        for (int i = 0;
                 i < 10;
                 ++i)
        {
            System.out.println(i + " ist eine Ziffer.");
        }
    }

    /**
     * Wie oft wird der Schleifenrumpf betreten? 4
     * Welchen Wert hat i, wenn der Schleifenrumpf das erste Mal betreten wird? 9
     * Welchen Wert hat i, wenn der Schleifenrumpf das letzte Mal betreten wird? 0
     * Welchen Wert hat i, wenn die Schleifenbedingung false ist? -3
     */
    public void schreibeDreierZiffernRueckwaerts()
    {
        // Man kann bei beliebigen Werten anfangen
        // und beliebig grosse Schritte in beide Richtungen gehen.
        for (int i = 9;
                 i >= 0;
                 i -= 3)
        {
            System.out.println(i + " ist durch 3 teilbar.");
        }
    }

    /**
     * Wie oft wird der Schleifenrumpf betreten? 5
     * Welche Werte haben a und b, wenn der Schleifenrumpf das erste Mal betreten wird? a=0 b=9
     * Welche Werte haben a und b, wenn der Schleifenrumpf das letzte Mal betreten wird? a=4, b=5
     * Welche Werte haben a und b, wenn die Schleifenbedingung false ist? a=5, b=4
     *
     * Frage am Rande: Warum sind die Klammern um (a + b) notwendig?
     * Damit wir die Summe von a und b ausgeben anstatt der Werte der beiden Variablen
     */
    public void schreibeNeunerSummen()
    {
        // Es koennen beliebig viele Schleifenzaehler verwendet werden,
        // zum Beispiel zwei "gegeneinander laufende" Zaehler,
        // die sich in der Mitte treffen (man beachte die Schleifenbedingung).
        for (int a = 0, b = 9;
                 a < b;
                 ++a, --b)
        {
            System.out.println( a + " + " + b + " = " + (a + b));
        }
    }


    // Beispiele fuer while-Schleifen

    /**
     * Angenommen, x habe den Wert 9, und y habe den Wert 4.
     * Welche Werte nimmt x dann im Verlauf der Methode an? 1
     */
    public int langsamerDivisionsrest(int x, int y)
    {
        while (x >= y)
        {
            x -= y;
        }
        return x;
    }

    /**
     * Angenommen, x habe den Wert 10.
     * Welcher Wert wird dann zurueckgeliefert? 16
     *
     * Angenommen, x habe den Wert 4.
     * Welcher Wert wird dann zurueckgeliefert? 4
     *
     * Welchen Unterschied wuerde es fuer die Beispiele 10 und 4 machen,
     * wenn man statt einer while-Schleife eine do-while-Schleife verwenden wuerde?
     *
     * Der Inhalt der do-while-Schleife wird einmal ausgeführt bevor die while condition geprüft wird.
     * Bei der while Schleife wird die condition erst geprüft bevor der Inhalt ausgeführt wird.
     * http://stackoverflow.com/a/3625771
     * In diesem Fall würde x um 1 addiert werden bevor die condition geprüft wird.
     */
    public int findeNaechsteZweierpotenz(int x)
    {
        while (!istZweierpotenz(x))
        {
            ++x;
        }
        return x;
    }


    // Beispiel fuer do-while-Schleifen

    /**
     * Warum ist es sinnvoll, fuer die Abfrage eines Passwortes
     * eine do-while-Schleife zu verwenden anstatt einer while-Schleife?
     *
     * Der Schleifeninhalt wird erst einmal ausgeführt (Passwort wird gelesen) und dann geprüft.
     */
    public void verlangePasswort()
    {
        String zeile;
        do
        {
            System.out.print("Passwort? ");
            zeile = liesZeileVomBenutzer();
        }
        while (!"\116\151\143\141\162\141\147\165\141".equals(zeile));

        System.out.println("Sie duerfen eintreten!");
    }

    /**
     * Warum ist es sinnvoll, fuer die Generierung der Binaerdarstellung
     * eine do-while-Schleife zu verwenden anstatt einer while-Schleife?
     *
     * Weil man mit dem ersten Wert schon einmal rechnen kann.
     * Bei der while-Schleife muss erst einmal eine Bedingung erfüllt sein bevor die Schleife ausgeführt wird.
     *
     * Fuer welche Zahl wuerde das ueberhaupt einen Unterschied machen?
     *
     * Bei 0 wird entweder "0" oder "" zurückgegeben.
     */
    public String binaereDarstellung(int x)
    {
        String result = "";
        do
        {
            result = "01".charAt(x % 2) + result;
            x = x / 2;
        }
        while (x != 0);

        return result;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Die folgenden privaten Hilfsmethoden muessen nicht verstanden werden.

    /**
     * Liest eine Zeile von der Konsole ein und liefert diese zurueck.
     */
    private String liesZeileVomBenutzer()
    {
        return new java.util.Scanner(System.in).nextLine();
    }
    
    /**
     * Bestimmt, ob es sich bei der uebergebenen Zahl um eine Zweierpotenz handelt.
     */
    private boolean istZweierpotenz(int x)
    {
        return (x & (x - 1)) == 0;
    }
}
