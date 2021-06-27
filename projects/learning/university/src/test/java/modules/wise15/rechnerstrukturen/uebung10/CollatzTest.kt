package modules.wise15.rechnerstrukturen.uebung10

import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung10.aufg1.Collatz
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CollatzTest {

    @Test
    fun testCollatz() {
        val collatz = Collatz()

        // Task 10.3 (c)
        collatz.collatz(100)
        println("Steps until 1 is reached: " + collatz.counter)
        Assertions.assertEquals(26, collatz.counter)

        // Task 10.3 (d)
        collatz.counter = 0
        collatz.collatz(19951131)
        println("Steps until 1 is reached: " + collatz.counter)
        Assertions.assertEquals(240, collatz.counter)
    }
}
