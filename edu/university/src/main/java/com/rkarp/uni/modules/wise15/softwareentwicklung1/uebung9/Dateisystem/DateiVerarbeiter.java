package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem;

import java.io.File;

/**
 * Ein DateiVerarbeiter verarbeitet eine Datei.
 * 
 * @author SE1 Vorlesung
 * @version heute
 */
public interface DateiVerarbeiter
{
    /**
     * Diese Methode wird vom VerzeichnisWanderer fuer jede Datei aufgerufen.
     * Was konkret mit jeder einzelnen Datei passiert, legen die implementierenden Klassen fest.
     */
    public void verarbeite(File datei);
}
