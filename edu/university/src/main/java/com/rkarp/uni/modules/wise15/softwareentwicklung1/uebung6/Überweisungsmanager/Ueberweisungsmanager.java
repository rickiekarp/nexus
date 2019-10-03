package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.Überweisungsmanager;

/**
 * Write a description of class Ueberweisungsmanager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ueberweisungsmanager
{
    // instance variables - replace the example below with your own
    private Konto quelle;
    private Konto ziel;
    private int betrag;
    

    /**
     * Constructor for objects of class Ueberweisungsmanager
     */
    public Ueberweisungsmanager()
    {
        //ignore
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void ueberweisen(Konto quellKonto, Konto zielKonto, int betrag)
    {
        if (quellKonto == null || zielKonto == null)
        {
            System.out.println("Konto ist null! Es wurde nichts ueberwiesen!");
            return;
        }
        quellKonto.hebeAb(betrag);
        zielKonto.zahleEin(betrag);
    }
}
