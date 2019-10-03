package modules.wise15.softwareentwicklung1.uebung14;

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_LinkedStack.Stack;
import org.junit.Test;

/**
 * Diese Klasse testet den Stack.
 *
 * @author Fredrik Winkler
 * @version WiSe 2013/14
 */
public class StackTest
{
    private Stack _stack;
    
    /**
     * Jede Testmethode arbeitet auf einem frisch erzeugten Exemplar.
     */
    public StackTest()
    {
        // TODO: Stack erzeugen
    }
    
    /**
     * Stellt sicher, dass ein neuer Stack leer ist.
     */
    @Test
    public void testNeuerStackIstLeer()
    {
        //assertTrue(_stack.isEmpty());
    }

    /**
     * Stellt sicher, dass ein Stack nach einem Push nicht mehr leer ist.
     */
    @Test
    public void testNachPushNichtLeer()
    {
        //_stack.push("test");
        //assertFalse(_stack.isEmpty());
    }

    @Test
    public void testNachPushUndPopWiederLeer()
    {
    }

    @Test
    public void testPeekEntferntKeinElement()
    {
    }

    @Test
    public void testPeekAufLeeremStackLiefertNull()
    {
    }

    @Test
    public void testPopAufLeeremStackLiefertNull()
    {
    }

    @Test
    public void testLastInFirstOutReihenfolge()
    {
    }
}
