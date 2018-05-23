package modules.wise15.softwareentwicklung1.uebung13;

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.Delegation;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.HashWortschatz;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.Wortschatz;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Diese Klasse testet den Wortschatz.
 *
 * @author Fredrik Winkler
 * @version 20. Januar 2015
 */
public class WortschatzTest
{
    private final Wortschatz _schatz;
    
    /**
     * Jede Testmethode arbeitet auf einem frisch erzeugten Exemplar.
     */
    public WortschatzTest()
    {
        _schatz = new HashWortschatz(new Delegation(), 10);
    }
    
    /**
     * Stellt sicher, dass ein neuer Wortschatz leer ist.
     */
    @Test
    public void testNeuerWortschatzIstLeer()
    {
        assertEquals(0, _schatz.anzahlWoerter());
    }

    /**
     * Stellt sicher, dass ein hinzugefuegtes Wort auch wirklich enthalten ist.
     */
    @Test
    public void testHinzugefuegtesWortIstEnthalten()
    {
        _schatz.fuegeWortHinzu("Suppenkasper");
        assertTrue(_schatz.enthaeltWort("Suppenkasper"));
        assertEquals(1, _schatz.anzahlWoerter());
    }
    
    /**
     * Aufg 13.1.1
     * Stellt sicher, dass ein nicht hinzugefügtes Wort nicht enthalten ist.
     */
    @Test
    public void testNichtHinzugefuegtesWortIstNichtEnthalten()
    {
        _schatz.fuegeWortHinzu("hello");
        assertFalse(_schatz.enthaeltWort("world"));
    }
    
    /**
     * Aufg 13.1.1
     * Stellt sicher, dass Duplikate nicht hinzugefügt werden.
     */
    @Test
    public void testDuplikateWerdenNichtHinzugefuegt()
    {
        _schatz.fuegeWortHinzu("hello");
        _schatz.fuegeWortHinzu("HELLO".toLowerCase());
        assertEquals(1, _schatz.anzahlWoerter());
    }
}
