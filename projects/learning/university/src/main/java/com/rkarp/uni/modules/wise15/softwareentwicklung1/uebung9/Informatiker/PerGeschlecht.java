package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Informatiker;

/**
 * Write a description of class PerGeschlecht here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PerGeschlecht implements Vergleicher
{
    /**
     * Aufg 9.2.6
     * @see Vergleicher.vergleiche
     */
    public int vergleiche(Person a, Person b)
    {
        if (a.istMaennlich() && b.istMaennlich())
        {
            return 0;
        }
        else
        {
            if (!a.istMaennlich() && b.istMaennlich())
            {
                return -1;
            }
            return 1;
        }
    }
}
