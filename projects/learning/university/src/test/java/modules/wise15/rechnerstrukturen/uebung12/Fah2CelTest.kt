package modules.wise15.rechnerstrukturen.uebung12

import com.rkarp.uni.modules.wise15.rechnerstrukturen.uebung12.aufg2.Fah2Cel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Fah2CelTest {
    @Test
    fun testFah2CelConverter() {
        val converter = Fah2Cel()
        val fahrenheit = 120
        val cel = converter.fah2cel(fahrenheit)
        Assertions.assertEquals(48, cel)
    }
}
