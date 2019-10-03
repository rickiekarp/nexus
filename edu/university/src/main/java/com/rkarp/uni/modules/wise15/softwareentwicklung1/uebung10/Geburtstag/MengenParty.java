package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung10.Geburtstag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Aufg 10.4.2
 * Write a description of class MengenParty here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MengenParty implements Party {

    private final Set<Tag> _originale;
    private final List<Tag> _duplikate;

    /**
     * Constructor for objects of class MengenParty
     */
    public MengenParty()
    {
        _originale = new HashSet<Tag>();
        _duplikate = new ArrayList<Tag>();
    }

    /**
     * Fügt ein Geburtstag einer Liste hinzu.
     *
     * @param geburtstag der hinzuzufügende Geburtstag
     */
    public void fuegeGeburtstagHinzu(Tag geburtstag) {
        if (!_originale.add(geburtstag) == false)
        {
            _originale.add(geburtstag);
        }
        else
        {
            _duplikate.add(geburtstag);
            //System.out.println("Duplikat gefunden...");
        }
    }

    /**
     * Prüft ob die _duplikate List mindestens einen Geburtstag enthält.
     */
    public boolean mindestensEinGeburtstagMehrfach() {
        return _duplikate.size() > 0;
    }
}
