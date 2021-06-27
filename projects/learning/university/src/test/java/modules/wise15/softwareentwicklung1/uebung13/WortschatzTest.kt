package modules.wise15.softwareentwicklung1.uebung13

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.Delegation
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.HashWortschatz
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Blatt13_Hashing.Wortschatz
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals

/**
 * Diese Klasse testet den Wortschatz.
 *
 * @author Fredrik Winkler
 * @version 20. Januar 2015
 */
internal class WortschatzTest {
    private val _schatz: Wortschatz

    /**
     * Jede Testmethode arbeitet auf einem frisch erzeugten Exemplar.
     */
    init {
        _schatz = HashWortschatz(Delegation(), 10)
    }

    /**
     * Stellt sicher, dass ein neuer Wortschatz leer ist.
     */
    @Test
    fun testNeuerWortschatzIstLeer() {
        assertEquals(0, _schatz.anzahlWoerter())
    }

    /**
     * Stellt sicher, dass ein hinzugefuegtes Wort auch wirklich enthalten ist.
     */
    @Test
    fun testHinzugefuegtesWortIstEnthalten() {
        _schatz.fuegeWortHinzu("Suppenkasper")
        Assertions.assertTrue(_schatz.enthaeltWort("Suppenkasper"))
        assertEquals(1, _schatz.anzahlWoerter())
    }

    /**
     * Aufg 13.1.1
     * Stellt sicher, dass ein nicht hinzugefügtes Wort nicht enthalten ist.
     */
    @Test
    fun testNichtHinzugefuegtesWortIstNichtEnthalten() {
        _schatz.fuegeWortHinzu("hello")
        Assertions.assertFalse(_schatz.enthaeltWort("world"))
    }

    /**
     * Aufg 13.1.1
     * Stellt sicher, dass Duplikate nicht hinzugefügt werden.
     */
    @Test
    fun testDuplikateWerdenNichtHinzugefuegt() {
        _schatz.fuegeWortHinzu("hello")
        _schatz.fuegeWortHinzu("HELLO".toLowerCase())
        assertEquals(1, _schatz.anzahlWoerter())
    }
}
