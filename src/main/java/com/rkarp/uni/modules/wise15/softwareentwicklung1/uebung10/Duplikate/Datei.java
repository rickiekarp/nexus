package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Duplikate;

import java.io.File;

/**
 * Exemplare dieser Klasse repraesentieren Dateien im Dateisystem.
 * Ihr Zweck liegt darin, das Auffinden von Duplikaten zu erleichtern.
 *  
 * @author Fredrik Winkler
 * @version 16. Dezember 2014
 */
class Datei
{
    private final File _file;
    private final long _groesse;
    private String _fingerabdruck;

    /**
     * Initialisiert eine neue Datei und merkt sich deren Groesse.
     * 
     * @param file das einzulesende File
     */
    public Datei(File file)
    {
        _file = file;
        _groesse = file.length();
    }

    /**
     * Aufg 10.3.2
     * Wann gelten zwei Exemplare dieser Klasse als gleich?
     *
     * Zwei Exemplare werden als gleich angesehen, wenn die _groesse und fingerabdruck Zustandsfelder gleich sind.
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Datei)
        {
            Datei zweite = (Datei) obj;
            return (_groesse == zweite._groesse)
                    && fingerabdruck().equals(zweite.fingerabdruck())
                    && _file.getName().equals(zweite._file.getName()); // Aufg 10.3.6
        }
        return false;
    }

    /**
     * Aufg 10.3.2
     * Wie wird der hashCode berechnet?
     *
     * Der Wert _groesse wird zu einem Integer gecastet. (long -> int)
     */
    public int hashCode()
    {
        return (int) _groesse;
    }

    /**
     * Liefert den Dateinamen und den Fingerabdruck als String zurueck.
     * 
     * @return String im Format "DATEINAME (FINGERABDRUCK)"
     */
    public String toString()
    {
        return _file.toString() + " (" + fingerabdruck() + ")";
    }

    /**
     * Liefert den Fingerabdruck. Da der Fingerabdruck nur bei Bedarf generiert wird,
     * kann der erste Aufruf dieser Methode u.U. recht lange dauern.
     * 
     * @return der Fingerabdruck
     */
    public String fingerabdruck()
    {
        if (_fingerabdruck == null)
        {
            _fingerabdruck = Fingerabdruck.aus(_file);
        }
        return _fingerabdruck;
    }
}
