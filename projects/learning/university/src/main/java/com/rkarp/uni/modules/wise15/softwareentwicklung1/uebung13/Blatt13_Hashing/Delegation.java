package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing;

/**
 * Delegiert die Berechnung an das String-Objekt.
 * 
 * @author Fredrik Winkler, Petra Becker-Pechau, Axel Schmolitzky
 * @version September 2011
 */
public class Delegation implements HashWertBerechner
{
    /**
     * Bilde das gegebene Wort auf einen ganzzahligen Wert ab.
     */
    public int hashWert(String wort)
    {
        return wort.hashCode();
    }
    
    /**
     * Liefert eine Beschreibung fuer die Art der Berechnung.
     */
    public String gibBeschreibung()
    {
        return "Delegiert die Berechnung an das String-Objekt";
    }
}
