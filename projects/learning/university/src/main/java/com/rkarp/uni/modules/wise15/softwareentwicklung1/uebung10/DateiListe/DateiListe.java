package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.DateiListe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Eine Liste von Dateien, die zum Beispiel von einem VerzeichnisWanderer befuellt werden kann.
 * 
 * @author Fredrik Winkler
 * @version 16. Dezember 2014
 */
public class DateiListe implements DateiVerarbeiter
{
    // Aufg 10.1.2
    private List<File> _dateien = new ArrayList<>();
    
    /**
     * Zu Beginn ist eine DateiListe leer.
     */
    public DateiListe()
    {
    }
    
    /**
     * Fuegt die uebergebene Datei zur DateiListe hinzu.
     */
    public void verarbeite(File datei)
    {
        // Aufg 10.1.3
        _dateien.add(datei);
    }
    
    /**
     * Schreibt alle Eintraege auf die Konsole, zusammen mit ihrem Index. Beispiel:
     * 0. config.sys
     * 1. autoexec.bat
     * 2. command.com
     */
    public void schreibeAufDieKonsole()
    {
        // Aufg 10.1.4
        for (int i = 0; i < _dateien.size(); i++)
        {
            System.out.println(_dateien.get(i));
        }
    }
    
    /**
     * Loescht alle Eintraege aus der DateiListe.
     */
    public void loescheAlleEintraege()
    {
        // Aufg 10.1.5
        _dateien.clear();
    }

    /**
     * Liefert die Laenge (in Bytes) aller Dateien in der DateiListe.
     */
    public long gesamtLaenge()
    {
        // Aufg 10.1.6
        long laenge = 0;
        for (File f : _dateien)
        {
            laenge += f.length();
            System.out.println(f.getName() + " - " + f.length());
        }
        return laenge;
    }
}
