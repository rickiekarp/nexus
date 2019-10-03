package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung9.Dateisystem;

import java.io.File;

/**
 * Write a description of class TextdateiFilter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextdateiFilter implements DateiVerarbeiter
{

    /**
     * Aufg. 9.1.4
     * Ermittelt die Dateiendung einer Datei
     *
     * @param  datei   die zu verarbeitende Datei
     */
    public void verarbeite(File datei) {
        String extension = datei.getName();
        String name = datei.getName();

        int pos = extension.lastIndexOf('.');
        if (pos > 0)
        {
            name = name.substring(0, pos);
            extension = extension.substring(pos);
        }

        if (extension.equals(".txt"))
        {
            System.out.println(name);
        }
    }
}