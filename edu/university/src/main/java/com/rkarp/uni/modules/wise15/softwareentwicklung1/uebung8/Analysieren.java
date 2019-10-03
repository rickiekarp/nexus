package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung8;

/**
 * Die Methoden in dieser Klasse haben keine besonders aussagekraeftigen Namen.
 * Schaut euch den Quelltext an, fuehrt die Methoden aus und dokumentiert sie.
 * 
 * @author Fredrik Winkler
 * @version 30. November 2015
 */
class Analysieren
{
    /**
     * Liefert die fuehrende Ziffer einer Zahl.
     * 
     *    a(5678)
     * -> a(567)
     * -> a(56)
     * -> a(5)
     * -> 5
     */
    public int a(int x)
    {
        int result;
        if (x < 10)       // wenn die Zahl nur aus einer Ziffer besteht...
        {
            result = x;   // ...dann ist diese Ziffer das Ergebnis
        }
        else
        {                 // ansonsten wird die rechte Ziffer abgeschnitten...
            result = a(x / 10);
        }                 // ...und die Funktion erneut ausgefuehrt
        return result;
    }

    /**
     * Liefert die Anzahl Ziffern einer Zahl.
     * 
     *    b(5678)
     * -> 1 + b(567)
     * -> 1 + (1 + b(56))
     * -> 1 + (1 + (1 + b(5)))
     * -> 1 + (1 + (1 + 1))
     * -> 1 + (1 + 2)
     * -> 1 + 3
     * -> 4
     */
    public int b(int x)
    {
        int result;
        if (x < 10)       // wenn die Zahl nur aus einer Ziffer besteht...
        {
            result = 1;   // ...dann ist die Anzahl Ziffern 1
        }
        else
        {                 // ansonsten ist das Ergebnis um 1 hoeher...
            int temp = b(x / 10);
            result = 1 + temp;
        }                 // ...als die Anzahl Ziffern in der Zahl ohne die rechte Ziffer
        return result;
    }

    /**
     * Liefert die Quersumme einer Zahl.
     *
     *    c(5678)
     * -> 8 + c(567)
     * -> 8 + (7 + c(56))
     * -> 8 + (7 + (6 + c(5)))
     * -> 8 + (7 + (6 +   5 ))
     * -> 8 + (7 + 11)
     * -> 8 + 18
     * -> 26
     */
    public int c(int x)
    {
        int result;
        if (x < 10)
        {
            result = x;
        }
        else
        {
            int rechteZiffer = x % 10;
            int temp = c(x / 10);
            result = rechteZiffer + temp;
        }
        return result;
    }

    /**
     * Liefert 
     * 
     *   d(5678)
     * -> d(567) + '8'
     * -> (d(56) + '7') + '8'
     * -> ((d(5) + '6') + '7') + '8'
     * -> (( "5" + '6') + '7') + '8'
     * -> (  "56        + '7') + '8'
     * ->    "567"             + '8'
     * ->    "5678"
     */
    public String d(int x)
    {
        String result;
        if (x < 10)
        {
            result = zifferAlsZeichen(x) + "";
        }
        else
        {
            int rechteZiffer = x % 10;
            String temp = d(x / 10);
            return temp + zifferAlsZeichen(rechteZiffer);
        }
        return result;
    }

    /**
     * Wandelt eine Ziffer (0 bis 9) in ein Zeichen ('0' bis '9') um.
     */
    private char zifferAlsZeichen(int x)
    {
        return "0123456789".charAt(x);
    }

    /**
     * Liefert 
     * 
     *    e(5678)
     * -> 
     */
    public int e(int x)
    {
        return e_(x, 0);
    }

    /**
     *    e_(5678, 0)
     * -> 
     */
    private int e_(int x, int y)
    {
        int result;
        if (x < 10)
        {
            result = (y * 10) + x;
        }
        else
        {
            result = e_(x / 10, (y * 10) + (x % 10));
        }
        return result;
    }

    /**
     * Liefert 
     * 
     *    f("Rosen", 'e')
     * -> 
     */
    public int f(String s, char c)
    {
        int result;
        if (s.isEmpty())
        {
            result = Integer.MIN_VALUE;
        }
        else if (s.charAt(0) == c)
        {
            result = 0;
        }
        else
        {
            result = 1 + f(s.substring(1), c);
        }
        return result;
    }

    /**
     * Liefert 
     */
    public int g(String s, char c)
    {
        return g_(s, c, 0);
    }

    /**
     *    g_("Rosen", 'e', 0)
     * -> 
     */
    private int g_(String s, char c, int i)
    {
        int result;
        if (i == s.length())
        {
            result = -1;
        }
        else if (s.charAt(i) == c)
        {
            result = i;
        }
        else
        {
            result = g_(s, c, i + 1);
        }
        return result;
    }

    /**
     * Hier finden die Aufrufe statt, die in den Diagrammen
     * auf dem Aufgabenblatt veranschaulicht werden (sollen).
     */
    public void testeAlleMethoden()
    {
        System.out.println(a(5678));
        System.out.println(b(5678));
        System.out.println(c(5678));
        System.out.println(d(5678));
        System.out.println(e(5678));
        System.out.println(f("Rosen", 'e'));
        System.out.println(g("Rosen", 'e'));
    }
}
