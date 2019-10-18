package modules.wise15.rechnerstrukturen.uebung4

import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung4.VergleichGleitkomma
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CompareFloatingPointTest {
    @Test
    fun testFloatComparison() {
        val equal = VergleichGleitkomma().almostEqual(2.5, 4.0, 3.0)
        Assertions.assertEquals(1, equal)

    }
}
