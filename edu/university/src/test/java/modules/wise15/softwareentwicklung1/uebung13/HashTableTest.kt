package modules.wise15.softwareentwicklung1.uebung13

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash.Delegation
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash.HashTable
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals

/**
 * Diese Klasse testet den Wortschatz.
 *
 * @author Fredrik Winkler
 * @version 20. Januar 2015
 */
internal class HashTableTest {
    private val _hashTable: HashTable = HashTable(Delegation())

    /**
     * Stellt sicher, dass ein neuer Wortschatz leer ist.
     */
    @Test
    fun neueHashTabelleIstLeer() {
        assertEquals(0, _hashTable.size())
    }

    /**
     * Stellt sicher, dass ein hinzugefuegtes Wort auch wirklich enthalten ist.
     */
    @Test
    fun hinzugefuegtesWortIstEnthalten() {
        _hashTable.insert("hello")
        Assertions.assertTrue(_hashTable.contains("hello"))
    }

    /**
     * Stellt sicher, dass ein nicht hinzugefügtes Wort nicht enthalten ist.
     */
    @Test
    fun nichtHinzugefuegtesWortIstNichtEnthalten() {
        _hashTable.insert("hello")
        Assertions.assertFalse(_hashTable.contains("world"))
    }

    /**
     * Stellt sicher, dass Duplikate nicht hinzugefügt werden.
     */
    @Test
    fun duplikateWerdenNichtHinzugefuegt() {
        _hashTable.insert("hello")
        _hashTable.insert("HELLO".toLowerCase())
        assertEquals(1, _hashTable.size())
    }
}
