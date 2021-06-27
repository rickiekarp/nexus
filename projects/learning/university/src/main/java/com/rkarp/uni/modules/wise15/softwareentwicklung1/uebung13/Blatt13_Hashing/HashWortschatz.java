package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing;

/**
 * Ein Wortschatz ist eine Menge von Woertern.
 * Es koennen Woerter hinzugefuegt werden, es kann abgefragt werden,
 * ob ein bestimmtes Wort im Wortschatz enthalten ist, und es kann die Anzahl
 * der gespeicherten Woerter abgefragt werden.
 * 
 * @author  (your name)
 * @version (a version number or a date)
 */
public class HashWortschatz implements Wortschatz
{
    private final HashWertBerechner _berechner;

    private WortListe[] _liste;
    private int _behaelterkapazitaet;
    private int _size;

    /**
     * Initialisiert ein neues Exemplar von HashWortschatz.
     * 
     * @param berechner der Berechner, welcher die Hashfunktion umsetzt
     * @param groesse die (initiale) Groesse der Hashtabelle
     */
    public HashWortschatz(HashWertBerechner berechner, int groesse)
    {
        _berechner = berechner;
        _liste = new WortListe[groesse];
        _behaelterkapazitaet = groesse;
        _size = 0;
    }
    
    /**
     * Fuege ein Wort zum Wortschatz hinzu, sofern es noch nicht enthalten ist.
     * 
     * @param wort das hinzuzufuegende Wort
     */
    public void fuegeWortHinzu(String wort)
    {
        if (!enthaeltWort(wort)) {
            int index = indexFuer(wort);

            // Prüfe ob eine Wortliste am index existiert.
            if (_liste[index] == null) {
                _liste[index] = new WortListe();
            }

            // Füge wort der Wortliste hinzu.
            _liste[index].fuegeWortHinzu(wort);
            _size++;
        }
    }
    
    /**
     * Gib an, ob ein Wort im Wortschatz enthalten ist.
     * 
     * @param wort das zu ueberpruefende Wort
     * @return true, falls das Wort enthalten ist, false sonst
     */
    public boolean enthaeltWort(String wort)
    {
        int index = indexFuer(wort);
        return _liste[index] != null && _liste[index].enthaeltWort(wort);
    }
    
    /**
     * Gib an, wieviele Woerter im Wortschatz enthalten sind.
     * 
     * @return die Anzahl der Woerter im Wortschatz
     */
    public int anzahlWoerter()
    {
        return _size;
    }

    /**
     * Diese Beschreibung dient zur Unterscheidung beim Messen.
     */
    public String gibBeschreibung()
    {
        return _berechner.gibBeschreibung();
    }

    /**
     * Aufg 13.2.2
     * Schreibt den Wortschatz auf die Konsole (als Debugging-Hilfe gedacht).
     */
    public void schreibeAufKonsole()
    {
        WortListe liste;
        for (int i = 0; i < _liste.length; i++) {
            liste = _liste[i];
            System.out.println("[" + i + "]: " + gibListWorte(liste));
        }
    }

    /**
     * Hilfsmethode
     * Ermittelt hash Wert eines Strings und gibt einen index zurück.
     * @param str der zu hashende String
     */
    private int indexFuer(String str) {
        int hash = _berechner.hashWert(str);
        int index = Math.abs(hash % _behaelterkapazitaet);
        //System.out.println(str + " - " + hash + " - " + index);
        return index;
    }

    /**
     * Hilfsmethode
     * Gibt alle Worte in einer Wortliste zurück.
     * @param liste die zu durchsuchende Wortliste
     */
    private String gibListWorte(WortListe liste) {
        if (liste != null) {
            String s = "";
            for (int e = 0; e < liste.anzahlWoerter(); e++) {
                s = s + " " + liste.gibWort(e);
            }
            return s;
        }
        return "";
    }
}
