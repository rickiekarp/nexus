package modules.wise15.softwareentwicklung1.uebung13;

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash.Delegation;
import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash.HashTable;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Diese Klasse testet den Wortschatz.
 *
 * @author Fredrik Winkler
 * @version 20. Januar 2015
 */
public class HashTableTest
{
    private final HashTable _hashTable;

    /**
     * Jede Testmethode arbeitet auf einem frisch erzeugten Exemplar.
     */
    public HashTableTest()
    {
        _hashTable = new HashTable(new Delegation());
    }

    /**
     * Stellt sicher, dass ein neuer Wortschatz leer ist.
     */
    @Test
    public void neueHashTabelleIstLeer()
    {
        assertEquals(0, _hashTable.size());
    }

    /**
     * Stellt sicher, dass ein hinzugefuegtes Wort auch wirklich enthalten ist.
     */
    @Test
    public void hinzugefuegtesWortIstEnthalten()
    {
        _hashTable.insert("hello");
        assertTrue(_hashTable.contains("hello"));
    }

    /**
     * Stellt sicher, dass ein nicht hinzugefügtes Wort nicht enthalten ist.
     */
    @Test
    public void nichtHinzugefuegtesWortIstNichtEnthalten()
    {
        _hashTable.insert("hello");
        assertFalse(_hashTable.contains("world"));
    }

    /**
     * Stellt sicher, dass Duplikate nicht hinzugefügt werden.
     */
    @Test
    public void duplikateWerdenNichtHinzugefuegt()
    {
        _hashTable.insert("hello");
        _hashTable.insert("HELLO".toLowerCase());
        assertEquals(1, _hashTable.size());
    }
}
