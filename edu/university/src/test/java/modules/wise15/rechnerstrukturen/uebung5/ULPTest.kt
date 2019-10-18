package modules.wise15.rechnerstrukturen.uebung5

import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung5.ULP
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ULPTest {
    @Test
    fun testULP() {
        val sum = ULP().ulpNew()
        Assertions.assertEquals(1.0000000004541774E8, sum)
    }
}
