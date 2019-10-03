package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung4;

/**
 * Eine Ratemaschine zum raten einer Zahl.
 * 
 * @author Rickie, Kevin
 * @version 10.11.2015
 **/
class Ratemaschine
{
    private final int _ZURATENDEZAHL;
    private int _counter;
    
    /**
     * Initialisiert die zu ratende Zahl.
     * @param zahl Die zu ratende Zahl.
     */
    public Ratemaschine(int zahl)
    {
        _ZURATENDEZAHL = zahl;
    }
    
    /**
     * Überprüft die zu ratende Zahl mit dem übergebenen Wert.
     * @param rateVersuch Die geratene Zahl.
     */
    public String istEsDieseZahl(int rateVersuch)
    {      
        if (rateVersuch == _ZURATENDEZAHL)
        {
            return "Stimmt! Versuche: " + _counter;
        }
        else
        {
            if (rateVersuch > _ZURATENDEZAHL)
            {
                _counter = _counter + 1;
                return "Zu hoch geraten.";
            }
            else
            {
                _counter = _counter + 1;
                return "Zu niedrig geraten!";
            }
        }
    }
}

