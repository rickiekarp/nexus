package modules.wise15.softwareentwicklung1.uebung11;

import com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung11.Elf.Eratosthenes;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class Eratesthenes {
    @Test
    public void testBis100() {
        int[] expected = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        int[] actual = Eratosthenes.calcPrimesBelow(100);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testBis50() {
        int[] expected = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
        int[] actual = Eratosthenes.calcPrimesBelow(50);
        assertArrayEquals(expected, actual);
    }
}
