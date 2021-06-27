package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.Uhrenanzeige;

/**
 * Die Klasse Nummernanzeige modelliert Objekte, die Werte von Null bis zu einem
 * vorgegebenen Limit anzeigen koennen. Das Limit wird definiert, wenn ein Exemplar
 * einer Nummernanzeige erzeugt wird. Die darstellbaren Werte reichen von Null
 * bis Limit-1.
 * Wenn beispielsweise eine Nummernanzeige fuer die Sekunden einer digitalen
 * Uhr verwendet werden soll, wuerde man ihr Limit auf 60 setzen, damit die
 * dargestellten Werte von 0 bis 59 reichen. Wenn der Wert einer Nummernanzeige
 * erhoeht wird, wird bei Erreichen des Limits der Wert automatisch auf Null
 * zurueckgesetzt.
 * 
 * @author Michael Kolling, David J. Barnes, Axel Schmolitzky
 * @version WiSe 2013
 */
class Nummernanzeige  
{
    private final int _limit;
    private int _wert;

    /**
     * Konstruktor fuer Exemplare der Klasse Nummernanzeige
     * @param anzeigeGrenze das Limit fuer die Anzeige
     */
    public Nummernanzeige(int anzeigeGrenze)
    {
        _limit = anzeigeGrenze;
    }

    /**
     * Liefere das Limit dieser Anzeige.
     */
    public int gibLimit()  
    {
        return _limit;
    }

    /**
     * Liefere den aktuellen Wert dieser Anzeige.
     */
    public int gibWert()  
    {
        return _wert;
    }
    
    /**
     * Liefere den Anzeigewert, also den Wert dieser Anzeige als einen String
     * mit zwei Ziffern. Wenn der Wert der Anzeige kleiner als zehn ist, wird
     * das Ergebnis mit einer fuehrenden Null versehen.
     */
    public String gibAnzeigewert()
    {
        if (_wert < 10)
        {
            return "0" + _wert;
        }
        return String.valueOf(_wert);
    }

    /**
     * Setze den Wert der Anzeige auf den angegebenen 'wert'.
     * Wenn der angegebene Wert unter Null oder groesser gleich
     * dem Limit ist, tue nichts.
     * 
     * @param 
     */
    public void setzeWert(int wert)
    {
        _wert = wert;
    }

    /**
     * Erhoehe den Wert dieser Anzeige um Eins. Wenn das Limit erreicht ist,
     * setze den Wert wieder auf Null.
     */
    public void erhoehen()
    {
        _wert++;
        if (_wert == _limit)
        {
            _wert = 0;
        }
    }
}
