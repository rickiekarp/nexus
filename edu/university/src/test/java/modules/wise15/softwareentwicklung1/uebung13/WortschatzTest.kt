package modules.wise15.softwareentwicklung1.uebung13;

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.Delegation;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.HashWortschatz;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.Wortschatz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Diese Klasse testet den Wortschatz.
 *
 * @author Fredrik Winkler
 * @version 20. Januar 2015
 */
class WortschatzTest
{
    private final Wortschatz _schatz;
    
    /**
     * Jede Testmethode arbeitet auf einem frisch erzeugten Exemplar.
     */
    WortschatzTest()
    {
        _schatz = new HashWortschatz(new Delegation(), 10);
    }
    
    /**
     * Stellt sicher, dass ein neuer Wortschatz leer ist.
     */
    @Test
    void testNeuerWortschatzIstLeer()
    {
        assertEquals(0, _schatz.anzahlWoerter());
    }

    /**
     * Stellt sicher, dass ein hinzugefuegtes Wort auch wirklich enthalten ist.
     */
    @Test
    void testHinzugefuegtesWortIstEnthalten()
    {
        _schatz.fuegeWortHinzu("Suppenkasper");
        Assertions.assertTrue(_schatz.enthaeltWort("Suppenkasper"));
        assertEquals(1, _schatz.anzahlWoerter());
    }
    
    /**
     * Aufg 13.1.1
     * Stellt sicher, dass ein nicht hinzugefügtes Wort nicht enthalten ist.
     */
    @Test
    void testNichtHinzugefuegtesWortIstNichtEnthalten()
    {
        _schatz.fuegeWortHinzu("hello");
        Assertions.assertFalse(_schatz.enthaeltWort("world"));
    }
    
    /**
     * Aufg 13.1.1
     * Stellt sicher, dass Duplikate nicht hinzugefügt werden.
     */
    @Test
    void testDuplikateWerdenNichtHinzugefuegt()
    {
        _schatz.fuegeWortHinzu("hello");
        _schatz.fuegeWortHinzu("HELLO".toLowerCase());
        assertEquals(1, _schatz.anzahlWoerter());
    }
}
