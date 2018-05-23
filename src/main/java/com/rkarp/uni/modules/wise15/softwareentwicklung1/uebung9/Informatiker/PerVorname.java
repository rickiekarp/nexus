package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Informatiker;

/**
 * Write a description of class PerVorname here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PerVorname implements Vergleicher
{
    /**
     * Aufg 9.2.5
     * @see Vergleicher.vergleiche
     */
    public int vergleiche(Person a, Person b)
    {
        return a.gibVorname().compareTo(b.gibVorname());
    }
}
