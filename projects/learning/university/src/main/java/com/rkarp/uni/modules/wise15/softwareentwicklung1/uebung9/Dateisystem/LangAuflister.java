package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem;

import java.io.File;

/**
 * Schreibt den Dateinamen inklusive Pfad auf die Konsole.
 * 
 * @author Fredrik Winkler
 * @version 6. Dezember 2014
 */
public class LangAuflister implements DateiVerarbeiter
{
    /**
     * @see Dateiverarbeiter.verarbeite
     */
    public void verarbeite(File datei)
    {
        System.out.println(datei.getAbsolutePath());
    }
}
