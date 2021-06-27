package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Informatiker;

/**
 * Vergleicht zwei Personen anhand ihres Nachnamens.
 */
public class PerNachname implements Vergleicher
{
    /**
     * @see Vergleicher.vergleiche
     */
    public int vergleiche(Person a, Person b)
    {
        return a.gibNachname().compareTo(b.gibNachname());
    }
}
