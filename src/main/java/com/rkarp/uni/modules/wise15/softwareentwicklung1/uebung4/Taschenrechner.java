package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung4;

/**
 * Eine Taschenrechner, der addieren, subtrahieren, multiplizieren und dividieren kann.
 * Man kann den Wert auf 0 zurücksetzen.
 * 
 * @author Rickie, Kevin
 * @version 10.11.2015
 **/
class Taschenrechner
{
    private double _zwischenErgebnis; //aktuelles Zwischenergebnis
    
    /**
     * Initialisiert das Zwischenergebnis mit dem Wert 0.
     */
    public Taschenrechner()
    {
        _zwischenErgebnis = 0.0;
    }
    
    /**
     * Addiert den übergebenen Wert und gibt das aktuelle Zwischenergebnis zurück.
     * @param zahl Die zu addierende Zahl.
     * @return Gibt das addierte Ergebnis zurück.
     */
    public double addiere(int zahl)
    {
        _zwischenErgebnis = _zwischenErgebnis + zahl;
        return _zwischenErgebnis;
    }
    
    /**
     * Subtrahiert den übergebenen Wert und gibt das aktuelle Zwischenergebnis zurück.
     * @param zahl Die zu subtrahierende Zahl.
     * @return Gibt das subtrahierte Ergebnis zurück.
     */
    public double subtrahiere(int zahl)
    {
        _zwischenErgebnis = _zwischenErgebnis - zahl;
        return _zwischenErgebnis;
    }
    
    /**
     * Multipliziert den übergebenen Wert und gibt das aktuelle Zwischenergebnis zurück.
     * @param Die zu multiplizierende Zahl
     * @return Gibt das multiplizierte Ergebnis zurück.
     */
    public double multiplizieren(int zahl)
    {
        _zwischenErgebnis = _zwischenErgebnis * zahl;
        return _zwischenErgebnis;
    }
    
    /**
     * Dividiert den übergebenen Wert und gibt das aktuelle Zwischenergebnis zurück.
     * @param Die zu dividierende Zahl
     * @return Gibt das dividierte Ergebnis zurück.
     */
    public double dividieren(int zahl)
    {
        _zwischenErgebnis = _zwischenErgebnis / zahl;
        return _zwischenErgebnis;
    }
    
    /**
     * Gibt das aktuelle Zwischenergebnis zurück.
     */
    public double gibZwischenErgebnis()
    {
        return _zwischenErgebnis;
    }
    
    /**
     * Setzt das Zwischenergebnis auf 0 zurück.
     */
    public void loesche()
    {
        _zwischenErgebnis = 0.0;
    }
}

