package modules.wise15.rechnerstrukturen.uebung6

import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung6.HammingDistance
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Hamming Distance Test
 */
internal class HammingDistanceTest {
    @Test
    fun testFah2CelConverter() {
        val a = 20
        val b = 10
        Assertions.assertEquals(4, HammingDistance().hamming(a, b))
    }
}
