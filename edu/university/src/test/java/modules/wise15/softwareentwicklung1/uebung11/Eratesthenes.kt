package modules.wise15.softwareentwicklung1.uebung11

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf.Eratosthenes
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertArrayEquals

internal class Eratesthenes {
    @Test
    fun testBis100() {
        val expected = intArrayOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)
        val actual = Eratosthenes.calcPrimesBelow(100)
        assertArrayEquals(expected, actual)
    }

    @Test
    fun testBis50() {
        val expected = intArrayOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47)
        val actual = Eratosthenes.calcPrimesBelow(50)
        assertArrayEquals(expected, actual)
    }
}
