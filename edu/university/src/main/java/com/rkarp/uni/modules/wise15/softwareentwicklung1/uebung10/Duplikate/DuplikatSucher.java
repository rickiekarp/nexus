package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Duplikate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Ein DuplikatSucher sucht Duplikate in einem Dateisystem.
 *  
 * @author Fredrik Winkler
 * @version 16. Dezember 2014
 */
public class DuplikatSucher implements DateiVerarbeiter
{
    private final Set<Datei> _originale;
    private final List<Datei> _duplikate;

    /**
     * Initialisiert den DuplikatSucher.
     */
    public DuplikatSucher()
    {
        _originale = new HashSet<Datei>();
        _duplikate = new ArrayList<Datei>();
    }
    
    /**
     * Startet die Suche nach Duplikaten.
     */
    public void start()
    {
        System.out.println("Bitte warten...\n");
        new VerzeichnisWanderer().start(this);
        System.out.println("Gefundene Duplikate: " + _duplikate.size());

        _originale.clear();
        _duplikate.clear();
    }

    /**
     * Versucht, die Datei zu den Originalen hinzuzufuegen.
     * Falls das nicht klappt, handelt es sich um ein Duplikat.
     */
    public void verarbeite(File file)
    {
        Datei datei = new Datei(file);
        if (_originale.add(datei) == false)
        {
            duplikatGefunden(datei);
        }
    }

    /**
     * Schreibt das Duplikat zusammen mit seiner Kopie auf die Konsole und merkt sich das Duplikat.
     */
    private void duplikatGefunden(Datei duplikat)
    {
        for (Datei original : _originale)
        {
            if (duplikat.equals(original))
            {
                System.out.println("Original: " + original);
                System.out.println("Duplikat: " + duplikat);
                System.out.println();
            }
        }
        _duplikate.add(duplikat);
    }
}
