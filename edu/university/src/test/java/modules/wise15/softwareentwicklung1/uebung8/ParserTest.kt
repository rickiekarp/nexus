package modules.wise15.softwareentwicklung1.uebung8;

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung8.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The test class ParserTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
class ParserTest
{
    private Parser _parser;
    /**
     * Default constructor for test class ParserTest
     */
    ParserTest()
    {
        _parser = new Parser();
        System.out.println("Neuer ParserTest initialisiert.");
    }

    @Test
    void alleEinstelligenZahlen()
    {
        assertEquals(0, _parser.parse("0"));
        assertEquals(1, _parser.parse("1"));
        assertEquals(2, _parser.parse("2"));
        assertEquals(3, _parser.parse("3"));
        assertEquals(4, _parser.parse("4"));
        assertEquals(5, _parser.parse("5"));
        assertEquals(6, _parser.parse("6"));
        assertEquals(7, _parser.parse("7"));
        assertEquals(8, _parser.parse("8"));
        assertEquals(9, _parser.parse("9"));
    }

    @Test
    void punktVorStrichRechnung()
    {
        assertEquals(26, _parser.parse("2*3+4*5"));
    }

    @Test
    void geklammerteAusdruecke()
    {
        assertEquals(21, _parser.parse("(1+2)*(3+4)"));
    }
}