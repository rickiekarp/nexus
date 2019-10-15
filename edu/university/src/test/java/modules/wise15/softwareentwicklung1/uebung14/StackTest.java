package modules.wise15.softwareentwicklung1.uebung14;

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_LinkedStack.Stack;
import org.junit.jupiter.api.Test;

/**
 * Diese Klasse testet den Stack.
 *
 * @author Fredrik Winkler
 * @version WiSe 2013/14
 */
class StackTest
{
    private Stack _stack;
    
    /**
     * Jede Testmethode arbeitet auf einem frisch erzeugten Exemplar.
     */
    StackTest()
    {
        // TODO: Stack erzeugen
    }
    
    /**
     * Stellt sicher, dass ein neuer Stack leer ist.
     */
    @Test
    void testNeuerStackIstLeer()
    {
        //assertTrue(_stack.isEmpty());
    }

    /**
     * Stellt sicher, dass ein Stack nach einem Push nicht mehr leer ist.
     */
    @Test
    void testNachPushNichtLeer()
    {
        //_stack.push("test");
        //assertFalse(_stack.isEmpty());
    }

    @Test
    void testNachPushUndPopWiederLeer()
    {
    }

    @Test
    void testPeekEntferntKeinElement()
    {
    }

    @Test
    void testPeekAufLeeremStackLiefertNull()
    {
    }

    @Test
    void testPopAufLeeremStackLiefertNull()
    {
    }

    @Test
    void testLastInFirstOutReihenfolge()
    {
    }
}
