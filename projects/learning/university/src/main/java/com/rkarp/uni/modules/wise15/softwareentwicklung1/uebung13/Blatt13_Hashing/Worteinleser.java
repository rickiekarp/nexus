package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ein Worteinleser dient zum Einlesen von Woertern aus einer Datei.
 * 
 * @author Fredrik Winkler
 * @author Petra Becker-Pechau
 * @author Axel Schmolitzky
 * @version 20. Januar 2015
 */
class Worteinleser
{
    private List<String> _text;

    /**
     * Liest einen Text aus einer Datei ein und liefert ihn als Liste von Woertern zurueck.
     */
    public static List<String> dateiAlsText(String dateiName)
    {
        return new Worteinleser().ausDatei(dateiName).fertig();
    }
    
    /**
     * Initialisiert einen neuen Wortleser.
     */
    private Worteinleser()
    {
        _text = new ArrayList<String>();
    }

    /**
     * Gibt den bisher eingelesenen Text als Liste zurueck und setzt den Worteinleser zurueck.
     * Die zurueckgegebene Liste ist damit unabhaengig vom internen Zustand des Worteinlesers.
     */
    private List<String> fertig()
    {
        List<String> result = _text;
        _text = new ArrayList<String>();
        return result;
    }
    
    /**
     * Liest eine Liste von Woertern aus einer Textdatei ein.
     * 
     * @param dateiName der Dateiname der einzulesenden Textdatei
     * @return this
     */
    private Worteinleser ausDatei(String dateiName) 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(dateiName)))
        {
            String zeile;
            while ((zeile = reader.readLine()) != null)
            {
                verarbeiteZeile(zeile);
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
        return this;
    }
    
    private static final Pattern pattern = Pattern.compile("[a-z]+");

    private void verarbeiteZeile(String zeile)
    {
        Matcher matcher = pattern.matcher(zeile.toLowerCase());
        while (matcher.find())
        {
            _text.add(matcher.group());
        }
    }
}
