package modules.wise15.softwareentwicklung1.uebung14

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_LinkedStack.Stack
import org.junit.jupiter.api.Test

/**
 * Diese Klasse testet den Stack.
 *
 * @author Fredrik Winkler
 * @version WiSe 2013/14
 */
/**
 * Jede Testmethode arbeitet auf einem frisch erzeugten Exemplar.
 */
internal class StackTest {
    private val _stack: Stack? = null

    /**
     * Stellt sicher, dass ein neuer Stack leer ist.
     */
    @Test
    fun testNeuerStackIstLeer() {
        //assertTrue(_stack.isEmpty());
    }

    /**
     * Stellt sicher, dass ein Stack nach einem Push nicht mehr leer ist.
     */
    @Test
    fun testNachPushNichtLeer() {
        //_stack.push("test");
        //assertFalse(_stack.isEmpty());
    }

    @Test
    fun testNachPushUndPopWiederLeer() {
    }

    @Test
    fun testPeekEntferntKeinElement() {
    }

    @Test
    fun testPeekAufLeeremStackLiefertNull() {
    }

    @Test
    fun testPopAufLeeremStackLiefertNull() {
    }

    @Test
    fun testLastInFirstOutReihenfolge() {
    }
}// TODO: Stack erzeugen
